package model.processes;

import model.images.image.InterfaceImages;
import java.util.List;

/**
 * Interface of the image processes that will apply a filter or transformation onto an image.
 */
public interface IProcesses {

  /**
   * given the image and matrix, it will apply matrix to image and edit it.
   * @param image 2DMatrix that represents image.
   * @param matrix that will be applied onto image.
   */
  public InterfaceImages apply(InterfaceImages image, List<List<Double>> matrix, int min, int max);
}
