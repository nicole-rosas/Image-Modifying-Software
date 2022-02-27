package controller;

import controller.guicontroller.ControllerSubscriberInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import model.MultiLayeredEnhanceInterface;
import view.ViewPublisherInterface;

/**
 * Mock for controller to test for GUI.
 */
public class MockControllerView implements ControllerSubscriberInterface,
    ImageEnhanceControllerInterface {
  private final Appendable out;
  private StringBuilder log;

  /**
   * Mock constructor for testing GUI.
   * @param view a ViewPublisherInterface
   * @param modell a MultiLayeredInterface
   * @param out an Appendable
   */
  public MockControllerView(ViewPublisherInterface view, MultiLayeredEnhanceInterface modell,
      Appendable out) {
    MultiLayeredEnhanceInterface model = Objects.requireNonNull(modell);
    ViewPublisherInterface mockView = Objects.requireNonNull(view);
    mockView.addSubscriber(this);
    this.out = Objects.requireNonNull(out);
  }

  /**
   * Mock constructor for testing GUI that takes in a view, model, appendable, and stringBuilder.
   * @param view a ViewPublisherInterface.
   * @param modell a MultiLayeredEnhanceInterface.
   * @param out an Appendable.
   * @param log a StringBuilder.
   */
  public MockControllerView(ViewPublisherInterface view, MultiLayeredEnhanceInterface modell,
      Appendable out, StringBuilder log) {
    MultiLayeredEnhanceInterface model = Objects.requireNonNull(modell);
    ViewPublisherInterface mockView = Objects.requireNonNull(view);
    mockView.addSubscriber(this);
    this.out = Objects.requireNonNull(out);
    this.log = log;
  }

  private void write(String message) {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed test.");
    }
  }

  @Override
  public void runProgram() throws IllegalArgumentException, IllegalStateException {
    // It is empty because this class is to test for mock and does not need to have
    // anything inside this method
  }

  @Override
  public void onBlurListener() {
    write("handle blur event");
  }

  @Override
  public void onSharpenListener() {
    write("handle sharpen event");
  }

  @Override
  public void onSepiaListener() {
    write("handle sepia event");
  }

  @Override
  public void onGreyscaleListener() {
    write("handle greyscale event");
  }

  @Override
  public void onImport(File file) {
    write("handle import event");
  }

  @Override
  public void onExport(File file) {
    write("handle export event");
  }

  @Override
  public void onSaveLayer(File file) {
    write("handle save event");
  }

  @Override
  public void onLoadLayer(File file) {
    write("handle load layer event");
  }

  @Override
  public void generateCheckerBoardImage(int width, int height, double interval, String color1,
      String color2) {
    write("handle generate event");
  }

  @Override
  public void onAddLayer() {
    write("handle add layer event");
  }

  @Override
  public void onChangeCurLayer(String name) {
    write("handle change current event");
  }

  @Override
  public void onChangeName(String name) {
    write("handle change name event");
  }

  @Override
  public void onChangeLayerUp(String name) {
    write("handle change layer up event");
  }

  @Override
  public void onChangeLayerDown(String name) {
    write("handle change layer down event");
  }

  @Override
  public void onHide(String layerName) {
    write("handle hide event");
  }

  @Override
  public void onVisible(String layerName) {
    write("handle visible event");
  }

  @Override
  public void onUndo() {
    write("handle undo event");
  }

  @Override
  public void onRemoveImage() {
    write("handle remove event");
  }

  @Override
  public void onDeleteLayer() {
    write("handle delete event");
  }

  @Override
  public void onRunScript(File file) {

    String filename = file.getName();
    write("handle run script event");
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }


    while (sc.hasNext()) {
      String s = sc.next();

      if (s.charAt(0) == '#') {
        sc.nextLine();
      }
      if (s.charAt(0) != '#') {
        log.append(s).append(" ");
      }
    }
  }
}
