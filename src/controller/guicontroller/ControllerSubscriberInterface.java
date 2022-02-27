package controller.guicontroller;

import java.io.File;

/**
 * Interface for setting up the listeners for the GUI that the user will interact with.
 */
public interface ControllerSubscriberInterface {

  /**
   * Allows for blur button to use blur command.
   */
  public void onBlurListener();

  /**
   * Allows for sharpen button to use sharpen command.
   */
  public void onSharpenListener();

  /**
   * Allows for sepia button to use sepia command.
   */
  public void onSepiaListener();

  /**
   * Allows for greyscale button to use greyscale command.
   */
  public void onGreyscaleListener();

  /**
   * Allows for import button to use import command.
   */
  public void onImport(File file);

  /**
   * Allows for export button to use export command.
   */
  public void onExport(File file);

  /**
   * Allows for save button to use save command.
   */
  public void onSaveLayer(File file);

  /**
   * Allows for load button to use load command.
   */
  public void onLoadLayer(File file);

  /**
   * Allows for generate button to use generate command.
   */
  public void generateCheckerBoardImage(int width, int height, double interval,
      String color1, String color2);

  /**
   * Allows for add button to use add command.
   */
  public void onAddLayer();

  /**
   * Allows for change current button to use change current command.
   */
  public void onChangeCurLayer(String name);

  /**
   * Allows for change name to use change name command.
   */
  public void onChangeName(String name);

  /**
   * Allows for change layer up button to use change layer up command.
   */
  public void onChangeLayerUp(String name);

  /**
   * Allows for change layer down button to use change layer down command.
   */
  public void onChangeLayerDown(String name);

  /**
   * Allows for hide button to use hide command.
   */
  public void onHide(String layerName);

  /**
   * Allows for visible button to use visble command.
   */
  public void onVisible(String layerName);

  /**
   * Allows for undo button to use undo command.
   */
  public void onUndo();

  /**
   * Allows for remove button to use remove command.
   */
  public void onRemoveImage();

  /**
   * Allows for delete button to use delete command.
   */
  public void onDeleteLayer();

  /**
   * Allows for run script button to use run script command.
   */
  public void onRunScript(File file);
}
