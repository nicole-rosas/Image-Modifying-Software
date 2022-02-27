package controller;

import model.ImageEnhanceModelInterface;

/**
 * Interface that commands how to enhance image.
 */

public interface ImageEnhanceCommandInterface {

  /**
   * runs the command for the given model.
   * @param model an ImageEnhanceModelInterface.
   */
  void commandGo(ImageEnhanceModelInterface model);
}
