package controller;

/**
 * The interface for a controller for future usage.
 */
public interface ImageEnhanceControllerInterface {

  /**
   * runs the image processing program.
   * @throws IllegalArgumentException if a parameter taken in is not expected or invalid.
   * @throws IllegalStateException if command can not be
   */
  void runProgram() throws IllegalArgumentException, IllegalStateException;
}
