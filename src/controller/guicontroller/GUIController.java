package controller.guicontroller;

import controller.ChangeType;
import controller.ImageEnhanceControllerInterface;
import controller.LoadLayerCommand;
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
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import model.generateimage.GeneratePresets;
import model.processes.KernelType;
import model.processes.TransformType;
import view.GUIView;
import view.ViewPublisherInterface;

/**
 * Represents the controller that makes all GUI features interactive for User when pressed.
 */
public class GUIController implements ControllerSubscriberInterface,
    ImageEnhanceControllerInterface {

  private final MultiLayeredEnhanceInterface model;
  private final ViewPublisherInterface view;
  private StringBuilder log;

  /**
   * empty constructor for GUIController.
   */
  public GUIController() {
    this.model = new MultiLayerEnhanceModel();
    this.log = new StringBuilder();
    this.view = new GUIView(this.model);
  }

  /**
   * Constructor for GUIController that takes in a model.
   * @param model a MultiLayeredEnhanceInterface.
   */
  public GUIController(MultiLayeredEnhanceInterface model) {
    this.model = model;
    this.log = new StringBuilder();
    this.view = new GUIView(this.model);
  }

  @Override
  public void runProgram() throws IllegalArgumentException, IllegalStateException {
    this.view.addSubscriber(this);
    this.view.displayGUI();
  }

  @Override
  public void onBlurListener() {
    EnhanceCommandInterface cmd = new FilterCommand(KernelType.BLUR);
    try {
      cmd.apply(this.model);
      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Blur effect has been applied to the Image.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onSharpenListener() {
    EnhanceCommandInterface cmd = new FilterCommand(KernelType.SHARPEN);
    try {
      cmd.apply(this.model);
      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Sharpen effect has been applied to the Image.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }

  }

  @Override
  public void onSepiaListener() {
    EnhanceCommandInterface cmd = new TransformCommand(TransformType.SEPIA);
    try {
      cmd.apply(this.model);
      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Sepia transform has been applied to the Image.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }

  }

  @Override
  public void onGreyscaleListener() {
    EnhanceCommandInterface cmd = new TransformCommand(TransformType.GREYSCALE);
    try {
      cmd.apply(this.model);
      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Greyscale transform has been applied to the Image.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onImport(File file) {
    String filepath = file.getAbsolutePath();
    String type;
    try {
      type = file.getAbsolutePath().substring(filepath.lastIndexOf(".") + 1);
    }
    catch (Exception e) {
      type = "none";
      this.writeMessage(e.getMessage());
    }

    ImportType enumType;

    switch (type) {
      case "ppm" : enumType = ImportType.PPM;
        break;
      case "png" : enumType = ImportType.PNG;
        break;
      case "jpeg" : enumType = ImportType.JPEG;
        break;
      default:
        enumType = null;
    }

    try {
      EnhanceCommandInterface cmd = new ImportFileCommand(enumType, filepath);
      cmd.apply(this.model);
      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Successfully imported an image onto a layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onExport(File file) {

    String filepath;
    String type;
    ExportType enumType;

    try {
      filepath = file.getAbsolutePath();
      type = filepath.substring(filepath.lastIndexOf(".") + 1);
    }
    catch (Exception e) {
      type = "none";
      filepath = "none";
      this.writeMessage("Cannot read the file given.");
      return;
    }

    switch (type) {
      case "ppm" : enumType = ExportType.PPM;
        break;
      case "png" : enumType = ExportType.PNG;
        break;
      case "jpeg" : enumType = ExportType.JPEG;
        break;
      default: enumType = null;
    }

    try {
      EnhanceCommandInterface cmd = new ExportFileCommand(enumType,
          filepath.substring(filepath.lastIndexOf(".")));
      cmd.apply(this.model);
      this.writeMessage("Successfully exported the Image as a " + type + " file.");
    }
    catch (Exception e) {
      this.writeMessage("Successfully imported an image onto a layer.");
    }
  }

  @Override
  public void onSaveLayer(File file) {

    try {
      String filepath = file.getAbsolutePath();
      EnhanceCommandInterface cmd = new SaveCommand(filepath);

      cmd.apply(this.model);
      this.writeMessage("Successfully save the layers into the directory.");
    }
    catch (Exception e) {
      this.writeMessage("Failed to save the layers.");
    }
  }

  @Override
  public void onLoadLayer(File file) {

    try {
      String filepath = file.getAbsolutePath();
      EnhanceCommandInterface cmd = new LoadLayerCommand(filepath);
      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully load the layers into the program.");
    }
    catch (Exception e) {
      this.writeMessage("Failed to load the layers.");
    }
  }

  @Override
  public void generateCheckerBoardImage(int width, int height, double interval,
      String color1, String color2) {

    try {
      List<Color> loColors = new ArrayList<>();
      loColors.add(this.getColorFromString(color1));
      loColors.add(this.getColorFromString(color2));

      this.model.generateCheckerBoardImage(width, height, interval, loColors);

      this.updateView("image");
      this.updateView("layers");
      this.writeMessage("Successfully generated a checkerboard image, "
          + "and add it into the current layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  private Color getColorFromString(String colorString) {
    switch (colorString) {
      case "Black" : return Color.BLACK;
      case "White" : return Color.WHITE;
      case "Blue" : return Color.BLUE;
      case "Red" : return Color.RED;
      case "Green" : return Color.GREEN;
      case "Purple" : return new Color(128, 0, 128);
      case "Grey" : return Color.GRAY;
      case "Yellow" : return Color.YELLOW;
      case "Orange" : return Color.ORANGE;
      case "Pink" : return Color.PINK;
      default: throw new IllegalArgumentException("Does not contain the preset colors.");
    }
  }

  @Override
  public void onAddLayer() {
    EnhanceCommandInterface cmd = new AddLayerCommand("new layer");

    try {
      cmd.apply(this.model);
      this.updateView("layers");
      this.writeMessage("Successfully added an image.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onChangeCurLayer(String name) {

    try {
      EnhanceCommandInterface cmd = new ChangeCurAndNameCommands(ChangeType.CURRENT, name);

      cmd.apply(this.model);
      this.updateView("layers");
      this.writeMessage("Successfully change the current layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onChangeName(String name) {

    try {
      EnhanceCommandInterface cmd = new ChangeCurAndNameCommands(ChangeType.NAME, name);

      cmd.apply(this.model);
      this.updateView("layers");
      this.writeMessage("Successfully changed the name of the current layer to " + name + ".");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onChangeLayerUp(String name) {


    try {
      int curIndex = this.model.getCurrentLayerPos();
      EnhanceCommandInterface cmd = new SwitchLayerPosCommand(name, curIndex + 1);

      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully moved the layer " + name + " up one layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onChangeLayerDown(String name) {


    try {
      int curIndex = this.model.getCurrentLayerPos();
      EnhanceCommandInterface cmd = new SwitchLayerPosCommand(name, curIndex - 1);

      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully moved the layer " + name + " down one layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onHide(String layerName) {

    try {
      EnhanceCommandInterface cmd = new ChangeVisCommand(layerName, "hidden");

      cmd.apply(this.model);
      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully hide layer " + layerName + ".");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onVisible(String layerName) {

    try {
      EnhanceCommandInterface cmd = new ChangeVisCommand(layerName, "visible");

      cmd.apply(this.model);
      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully make layer " + layerName + " visible.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onUndo() {

    try {
      EnhanceCommandInterface cmd = new UndoCommand();

      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully undo operation.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onRemoveImage() {
    try {
      EnhanceCommandInterface cmd = new RemoveImageCommand();
      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully removed image from the layer.");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onDeleteLayer() {

    try {
      String name = model.getName();
      EnhanceCommandInterface cmd = new DeleteLayerCommand(name);
      cmd.apply(this.model);

      this.updateView("layers");
      this.updateView("image");
      this.writeMessage("Successfully deleted layer " + name + ".");
    }
    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  @Override
  public void onRunScript(File file) {

    try {

      String filepath = file.getAbsolutePath();

      EnhanceCommandInterface cmd = new RunScriptCommand(filepath, log);
      cmd.apply(this.model);

      String commands = log.toString();
      log = new StringBuilder();

      this.runBatch(commands);
      this.updateView("layers");
      this.updateView("image");

      this.writeMessage("Successfully ran batch script.");
    }

    catch (Exception e) {
      this.writeMessage(e.getMessage());
    }
  }

  /**
   * runs all commands inside batch file for ImageEnhanceBasicController.
   *
   * @param input content of batch file.
   */
  protected void runBatch(String input) {
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
        this.writeMessage("Command is invalid and will produce an error. Try again.\n");
      }
      else {
        try {
          c = cmd.apply(sc);
          c.apply(this.model);

          if (input2.equalsIgnoreCase("run")) {
            String batchCommands = log.toString();
            log = new StringBuilder();
            this.runBatch(batchCommands);
          }

          this.updateView("layers");
          this.updateView("image");
          this.writeMessage("test");
        } catch (Exception e) {
          this.writeMessage(e.getMessage());
        }
      }
    }
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
        this.writeMessage("Not a valid change type.");
        return null;
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
        this.writeMessage("Not a valid export type.");
        return null;
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
        this.writeMessage("Not a valid import type.");
        return null;
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
        this.writeMessage("Not a valid kernel/filter type.");
        return null;
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
        this.writeMessage("Not a valid kernel/filter type.");
        return null;
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
      this.writeMessage("Invalid preset type.");
      return null;
    }
  }

  private void writeMessage(String message) {
    this.view.writeMessage(message);
  }

  private void updateView(String type) {

    switch (type) {
      case "image": this.view.updateImage();
      break;
      case "layers": this.view.updateLayers();
      break;
      default: this.writeMessage("Incorrect refresh type.");
    }
  }
}
