package model.processes.kernelconvolution;

import model.processes.IProcesses;
import model.processes.KernelType;

/**
 * creates an image clarity transformation.
 */
public class KernelCreator {

  /**
   * creates a new clarity transformation depending on the given {@code KernelType}.
   * @param type of given clarity transformation type.
   * @return a new clarity filter.
   * @throws IllegalArgumentException if it is a null or invalid {@code KernelType}.
   */
  public static IProcesses create(KernelType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("KernelType is null");
    }
    switch (type) {
      case BLUR: return new ConvolutionBlur();
      case SHARPEN: return new ConvolutionSharpen();
      default: throw new IllegalArgumentException("Incorrect Transform process.");
    }
  }
}
