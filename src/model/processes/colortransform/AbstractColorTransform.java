package model.processes.colortransform;

import model.images.image.InterfaceImages;
import model.processes.IProcesses;
import java.util.List;

/**
 * represents abstract of color transformation.
 */
abstract class AbstractColorTransform implements IProcesses {

  /**
   * empty constructor for {@code AbstractColorTransform}.
   */
  protected AbstractColorTransform() {
    // Does not have to do anything.
  }

  @Override
  public InterfaceImages apply(InterfaceImages image, List<List<Double>> matrix, int min, int max) {
    if (image == null || matrix == null) {
      throw new IllegalArgumentException("Image or matrix cannot be null.");
    }

    return image.transformImage(matrix, min, max);
  }
}
