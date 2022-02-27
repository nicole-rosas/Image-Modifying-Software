package model.processes.colortransform;

import model.processes.IProcesses;
import model.processes.TransformType;

/**
 * creates an image color transformation.
 */
public class TransformCreator {

  /**
   * creates a new color transformation depending on the given {@code TransformType}.
   * @param type of given color transformation type.
   * @return a new color filter.
   * @throws IllegalArgumentException if it is a null or invalid {@code TransformType}.
   */
  public static IProcesses create(TransformType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("TransformType is null!");
    }
    switch (type) {
      case SEPIA: return new TransformSepia();
      case GREYSCALE: return new TransformGreyscale();
      default: throw new IllegalArgumentException("Incorrect Transform process.");
    }
  }
}
