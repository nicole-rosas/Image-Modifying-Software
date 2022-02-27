package view;

import controller.guicontroller.ControllerSubscriberInterface;
import java.io.File;
import java.util.Objects;

/**
 * Mock for view for testing GUI.
 */
public class MockView implements ViewPublisherInterface {

  private ControllerSubscriberInterface listener;
  private File file;

  @Override
  public void addSubscriber(ControllerSubscriberInterface subscriber) {
    this.listener = Objects.requireNonNull(subscriber);

    try {
      file = new File("file.txt");
      file.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void displayGUI() {
    // this method will be empty because we are just testing certain aspects of GUI.
  }

  @Override
  public void writeMessage(String message) {
    // this method will be empty because we are just testing certain aspects of GUI.
  }

  @Override
  public void updateImage() {
    // this method will be empty because we are just testing certain aspects of GUI.
  }

  @Override
  public void updateLayers() {
    // this method will be empty because we are just testing certain aspects of GUI.
  }

  public void fireBlurEvent() {
    this.listener.onBlurListener();
  }

  public void fireSharpenEvent() {
    this.listener.onSharpenListener();
  }

  public void fireSepiaEvent() {
    this.listener.onSepiaListener();
  }

  public void fireGreyscaleEvent() {
    this.listener.onGreyscaleListener();
  }

  public void fireImportEvent() {
    this.listener.onImport(file);
  }

  public void fireExportEvent() {
    this.listener.onExport(file);
  }

  public void fireSaveEvent() {
    this.listener.onSaveLayer(file);
  }

  public void fireLoadEvent() {
    this.listener.onLoadLayer(file);
  }

  public void fireGenerateEvent() {
    this.listener.generateCheckerBoardImage(1,1,1, "red","blue");
  }

  public void fireAddLayerEvent() {
    this.listener.onAddLayer();
  }

  public void fireChangeCurEvent() {
    this.listener.onChangeCurLayer("hi");
  }

  public void fireChangeNameEvent() {
    this.listener.onChangeName("hi");
  }

  public void fireChangeLayerUpEvent() {
    this.listener.onChangeLayerUp("hi");
  }

  public void fireChangeLayerDownEvent() {
    this.listener.onChangeLayerDown("hi");
  }

  public void fireHideEvent() {
    this.listener.onHide("hi");
  }

  public void fireVisibleEvent() {
    this.listener.onVisible("hi");
  }

  public void fireUndoEvent() {
    this.listener.onUndo();
  }

  public void fireRemoveEvent() {
    this.listener.onRemoveImage();
  }

  public void fireDeleteEvent() {
    this.listener.onDeleteLayer();
  }

  public void fireRunScript() {
    this.listener.onRunScript(file);
  }
}
