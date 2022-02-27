package controller;

import controller.command.AddLayerCommand;
import controller.command.ChangeCurAndNameCommands;
import controller.command.ChangeVisCommand;
import controller.command.CreateDirCommand;
import controller.command.DeleteLayerCommand;
import controller.command.EnhanceCommandInterface;
import controller.command.ExportFileCommand;
import controller.command.FilterCommand;
import controller.command.GenerateImageCommand;
import controller.command.ImportFileCommand;
import controller.command.RemoveImageCommand;
import controller.command.RunScriptCommand;
import controller.command.SaveCommand;
import controller.command.SwitchLayerPosCommand;
import controller.command.TransformCommand;
import controller.command.UndoCommand;
import controller.export.ExportType;
import controller.importing.ImportType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import model.generateimage.GeneratePresets;
import model.processes.KernelType;
import model.processes.TransformType;
import view.InterfaceImageEnhanceView;
import view.TextViewImageEnhance;

/**
 * controls how the image file is enhanced.
 */
public class ImageEnhanceBasicController implements ImageEnhanceControllerInterface {

  private final MultiLayeredEnhanceInterface model;
  private final InterfaceImageEnhanceView view;
  private final Scanner scan;
  private boolean endProgram;
  private StringBuilder log;
  private boolean runScriptOnly;
  private String filePath;

  /**
   * Empty constructor for ImageEnhanceBasicController.
   */
  public ImageEnhanceBasicController() {
    this.scan = new Scanner(System.in);
    this.model = new MultiLayerEnhanceModel();
    this.view = new TextViewImageEnhance(this.model, System.out);
    this.endProgram = false;
    this.log = new StringBuilder();
    this.runScriptOnly = false;
    this.filePath = "";
  }

  /**
   * Constructor for ImageEnhanceBasicController that takes in a model and a filepath.
   * @param model a MultiLayerEnhanceModel
   * @param filePath a filepath
   */
  public ImageEnhanceBasicController(MultiLayerEnhanceModel model, String filePath) {
    if (model == null || filePath == null) {
      throw new IllegalArgumentException("Model or filepath cannot be null.");
    }

    this.scan = new Scanner(System.in);
    this.model = model;
    this.view = new TextViewImageEnhance(this.model, System.out);
    this.endProgram = false;
    this.log = new StringBuilder();
    this.runScriptOnly = true;
    this.filePath = filePath;
  }

  /**
   * Constructor for ImageEnhanceBasicController that takes in a MultiLayeredEnhanceInterface, a
   * readable, and an appendable.
   *
   * @param model an MultiLayeredEnhanceInterface
   * @param in    a Readable
   * @param out   an Appendable
   */
  public ImageEnhanceBasicController(MultiLayeredEnhanceInterface model,
      Readable in, Appendable out) {
    if (model == null || in == null || out == null) {
      throw new IllegalArgumentException("The arguments cannot be null.");
    }

    this.model = model;
    this.scan = new Scanner(in);
    this.view = new TextViewImageEnhance(this.model, out);
    this.endProgram = false;
    this.log = new StringBuilder();
    this.runScriptOnly = false;
    this.filePath = "";
  }

  @Override
  public void runProgram() throws IllegalArgumentException, IllegalStateException {
    this.renderCurrentState();
    this.writeMessage("\n");

    Map<String, Function<String, EnhanceCommandInterface>> knownCommands;
    knownCommands = this.generateKnownCommands();

    if (runScriptOnly) {
      EnhanceCommandInterface cmd = new RunScriptCommand(filePath, log);
      cmd.apply(this.model);

      String batchCommands = log.toString();
      log = new StringBuilder();
      this.runBatch(batchCommands);
    }
    else {
      while (!endProgram) {
        String input = this.receiveStringInput();
        EnhanceCommandInterface c;

        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
          endProgram = true;
          break;
        }

        Function<String, EnhanceCommandInterface> cmd =
            knownCommands.getOrDefault(input, null);

        if (cmd == null) {
          this.writeMessage("Command is invalid and will produce an error. Try again.\n");
        } else {
          try {
            c = cmd.apply("placeholder");
            c.apply(this.model);

            if (input.equalsIgnoreCase("run")) {
              String batchCommands = log.toString();
              log = new StringBuilder();
              this.runBatch(batchCommands);
            }

            this.renderCurrentState();
          } catch (Exception e) {
            this.writeMessage(e.getMessage());
          }
        }
      }
    }
  }

  /**
   * runs all commands inside batch file for ImageEnhanceBasicController.
   *
   * @param input content of batch file.
   */
  private void runBatch(String input) {
    Map<String, Function<Scanner, EnhanceCommandInterface>> knownCommands;
    Scanner sc = new Scanner(input);
    knownCommands = this.commandsAsScanner();

    EnhanceCommandInterface c;

    while (sc.hasNext()) {
      String input2 = sc.next();

      if (input2.equalsIgnoreCase("q") || input2.equalsIgnoreCase("quit")) {
        return;
      }

      Function<Scanner, EnhanceCommandInterface> cmd =
          knownCommands.getOrDefault(input2, null);

      if (cmd == null) {
        if (runScriptOnly) {
          return;
        }
        this.writeMessage("Command is invalid and will produce an error. Try again.\n");
      } else {
        try {
          c = cmd.apply(sc);
          c.apply(this.model);

          if (input2.equalsIgnoreCase("run")) {
            String batchCommands = log.toString();
            log = new StringBuilder();
            this.runBatch(batchCommands);
          }
        } catch (Exception e) {
          this.writeMessage(e.getMessage());
        }
      }
    }
  }

  /**
   * gets the ChangeType of the Model by using the given string.
   *
   * @param type string that will determine the ChangeType.
   * @return a ChangeType
   */
  private ChangeType getChangeType(String type) {
    switch (type) {
      case "current":
        return ChangeType.CURRENT;
      case "name":
        return ChangeType.NAME;
      default:
        throw new IllegalArgumentException("Not a valid change type.");
    }
  }

  /**
   * Gets the ExportType of Model by using the given string.
   *
   * @param type string that determines ExportType
   * @return an ExportType
   */
  private ExportType getExportType(String type) {
    switch (type) {
      case "ppm":
        return ExportType.PPM;
      case "png":
        return ExportType.PNG;
      case "jpeg":
        return ExportType.JPEG;
      default:
        throw new IllegalArgumentException("Not a valid export type.");
    }
  }

  /**
   * Gets the ImportType of Model by using the given string.
   *
   * @param type string that determines ImportType.
   * @return an ImportType
   */
  private ImportType getImportType(String type) {
    switch (type) {
      case "ppm":
        return ImportType.PPM;
      case "png":
        return ImportType.PNG;
      case "jpeg":
        return ImportType.JPEG;
      default:
        throw new IllegalArgumentException("Not a valid import type.");
    }
  }

  /**
   * Gets the KernelType of Model by using the given string.
   *
   * @param type string that determines KernelType
   * @return an KernelType
   */
  private KernelType getKernelType(String type) {
    switch (type) {
      case "blur":
        return KernelType.BLUR;
      case "sharpen":
        return KernelType.SHARPEN;
      default:
        throw new IllegalArgumentException("Not a valid kernel/filter type.");
    }
  }

  /**
   * Gets the TransformType of Model by using the given string.
   *
   * @param type string that determines TransformType
   * @return an TransformType
   */
  private TransformType getTransformType(String type) {
    switch (type) {
      case "sepia":
        return TransformType.SEPIA;
      case "greyscale":
        return TransformType.GREYSCALE;
      default:
        throw new IllegalArgumentException("Not a valid kernel/filter type.");
    }
  }

  /**
   * Gets the GeneratePresets of Model by using the given string.
   *
   * @param type string that determines GeneratePresets
   * @return GeneratePresets
   */
  private GeneratePresets getPreset(String type) {
    if (type.equals("checkerboard")) {
      return GeneratePresets.CHECKERBOARD;
    } else {
      throw new IllegalArgumentException("Invalid preset type.");
    }
  }

  /**
   * Generates a Map which maps a string to the command that will be used to generate the specific
   * commands for view.
   *
   * @return Map of controlls available
   */
  private Map<String, Function<String, EnhanceCommandInterface>> generateKnownCommands() {
    Map<String, Function<String, EnhanceCommandInterface>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("add",
        s -> new AddLayerCommand(this.receiveStringInput()));
    knownCommands.put("change",
        s -> new ChangeCurAndNameCommands(this.getChangeType(this.receiveStringInput()),
            this.receiveStringInput()));
    knownCommands.put("hide",
        s -> new ChangeVisCommand(this.receiveStringInput(), "hidden"));
    knownCommands.put("visible",
        s -> new ChangeVisCommand(this.receiveStringInput(), "visible"));
    knownCommands.put("newDir", s -> new CreateDirCommand(this.receiveStringInput()));
    knownCommands.put("delete", s -> new DeleteLayerCommand(this.receiveStringInput()));
    knownCommands.put("export",
        s -> new ExportFileCommand(this.getExportType(this.receiveStringInput()),
            this.receiveStringInput()));
    knownCommands.put("blur", s -> new FilterCommand(this.getKernelType("blur")));
    knownCommands.put("sharpen", s -> new FilterCommand(this.getKernelType("sharpen")));
    knownCommands.put("sepia", s -> new TransformCommand(this.getTransformType("sepia")));
    knownCommands.put("greyscale", s -> new TransformCommand(this.getTransformType("greyscale")));
    knownCommands.put("generate",
        s -> new GenerateImageCommand(this.getPreset(this.receiveStringInput()),
            this.receiveIntInput(), this.receiveIntInput(), this.receiveDoubleInput()));
    knownCommands.put("import",
        s -> new ImportFileCommand(this.getImportType(this.receiveStringInput()),
            this.receiveStringInput()));
    knownCommands.put("load", s -> new LoadLayerCommand(this.receiveStringInput()));
    knownCommands.put("removeImage", s -> new RemoveImageCommand());
    knownCommands.put("save", s -> new SaveCommand(this.receiveStringInput()));
    knownCommands.put("undo", s -> new UndoCommand());
    knownCommands.put("switch", s -> new SwitchLayerPosCommand(this.receiveStringInput(),
        this.receiveIntInput()));
    knownCommands.put("run", s -> {
      log = new StringBuilder();
      return new RunScriptCommand(this.receiveStringInput(), log);
    });

    return knownCommands;
  }

  /**
   * Generates Map of Scanned and EnhanceCommandInterface which will determine when methods appear.
   *
   * @return map of Scanned and EnhanceCommandsModelinterface
   */
  private Map<String, Function<Scanner, EnhanceCommandInterface>> commandsAsScanner() {
    Map<String, Function<Scanner, EnhanceCommandInterface>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("add",
        s -> new AddLayerCommand(s.next()));
    knownCommands.put("change", s ->
        new ChangeCurAndNameCommands(this.getChangeType(s.next()),
            s.next()));
    knownCommands.put("hide", s ->
        new ChangeVisCommand(s.next(), "hidden"));
    knownCommands.put("visible", s ->
        new ChangeVisCommand(s.next(), "visible"));
    knownCommands.put("newDir", s -> new CreateDirCommand(s.next()));
    knownCommands.put("delete", s -> new DeleteLayerCommand(s.next()));
    knownCommands.put("export", s ->
        new ExportFileCommand(this.getExportType(s.next()),
            s.next()));
    knownCommands.put("blur", s -> new FilterCommand(this.getKernelType("blur")));
    knownCommands.put("sharpen", s -> new FilterCommand(this.getKernelType("sharpen")));
    knownCommands.put("sepia", s -> new TransformCommand(this.getTransformType("sepia")));
    knownCommands.put("greyscale", s -> new TransformCommand(this.getTransformType("greyscale")));
    knownCommands.put("generate", s ->
        new GenerateImageCommand(this.getPreset(s.next()),
            s.nextInt(), s.nextInt(), s.nextDouble()));
    knownCommands.put("import", s ->
        new ImportFileCommand(this.getImportType(s.next()),
            s.next()));
    knownCommands.put("load", s -> new LoadLayerCommand(s.next()));
    knownCommands.put("removeImage", s -> new RemoveImageCommand());
    knownCommands.put("save", s -> new SaveCommand(s.next()));
    knownCommands.put("undo", s -> new UndoCommand());
    knownCommands.put("switch", s -> new SwitchLayerPosCommand(s.next(),
        s.nextInt()));
    knownCommands.put("run", s -> new RunScriptCommand(s.next(), log));

    return knownCommands;
  }

  /**
   * Writes out the given message as a string to the view.
   *
   * @param message String that will be shown.
   * @throws IllegalStateException if message cannot be rendered
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Message could not be written.");
    }
  }

  /**
   * Return the current state of the model as a text.
   *
   * @throws IllegalStateException if message cannot be rendered and fails.
   */
  private void renderCurrentState() throws IllegalStateException {
    try {
      view.renderCurrentState();
    } catch (IOException e) {
      throw new IllegalStateException("Current state of program cannot be rendered.");
    }
  }

  /**
   * Takes in a string input that is the next string to be taken in by controller.
   *
   * @return String which is next command or move to be taken in.
   * @throws IllegalStateException if message fails to be rendered
   */
  private String receiveStringInput() throws IllegalStateException {
    try {
      return scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No such element in the scanner.");
    }
  }

  /**
   * Takes in a integer input that is the next int to be taken in by controller.
   *
   * @return integer which is to be used in next command or move
   * @throws IllegalStateException if message fails to be rendered
   */
  private int receiveIntInput() throws IllegalStateException {
    try {
      String intS = scan.next();
      return Integer.parseInt(intS);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No such element in the scanner.");
    }
  }

  /**
   * Takes in a integer input that is the next double to be taken in by controller.
   *
   * @return double which is to be used in next command or move
   * @throws IllegalStateException if message fails to be rendered
   */
  private double receiveDoubleInput() throws IllegalStateException {
    try {
      String doubleS = scan.next();
      return Double.parseDouble(doubleS);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No such element in the scanner.");
    }
  }
}
