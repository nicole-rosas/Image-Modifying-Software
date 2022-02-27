package model.processes.kernelconvolution;

import model.images.image.InterfaceImages;
import model.processes.IProcesses;
import java.util.List;

/**
 * represents abstract of clarity transformations.
 */
abstract class AbstractKernelConvolution implements IProcesses {

  /**
   * empty constructor for {@code AbstractKernelConvolution}.
   */
  protected AbstractKernelConvolution() {
    // Does not have to do anything.
  }

  @Override
  public InterfaceImages apply(InterfaceImages image, List<List<Double>> matrix, int min, int max) {
    if (image == null || matrix == null) {
      throw new IllegalArgumentException("Image or matrix cannot be null.");
    }

    return image.filterImage(matrix, min, max);
  }
}
