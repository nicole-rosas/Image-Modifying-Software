package model.processes;

import java.util.ArrayList;
import java.util.List;

/**
 * enum of filters:
 *  BLUR - blurs an image.
 *  SHARPEN - sharpens an image.
 */
public enum KernelType {
  SHARPEN(KernelType.createMatrixSharpen()), BLUR(KernelType.createMatrixBlur());

  private final List<List<Double>> matrix;

  /**
   * Constructor of {@code KernelType} that has fields of a matrix.
   * @param matrix lists of lists of double
   */
  KernelType(List<List<Double>> matrix) {
    this.matrix = matrix;
  }

  /**
   * Creates a matrix that will be applied to sharpen image.
   * @return matrix of sharpen filter.
   */
  private static List<List<Double>> createMatrixSharpen() {
    List<Double> array1 = new ArrayList<>();
    array1.add(-1.0 / 8.0);
    array1.add(-1.0 / 8.0);
    array1.add(-1.0 / 8.0);
    array1.add(-1.0 / 8.0);
    array1.add(-1.0 / 8.0);
    List<Double> array2 = new ArrayList<>();
    array2.add(-1.0 / 8.0);
    array2.add(1.0 / 4.0);
    array2.add(1.0 / 4.0);
    array2.add(1.0 / 4.0);
    array2.add(-1.0 / 8.0);
    List<Double> array3 = new ArrayList<>();
    array3.add(-1.0 / 8.0);
    array3.add(1.0 / 4.0);
    array3.add(1.0);
    array3.add(1.0 / 4.0);
    array3.add(-1.0 / 8.0);
    
    List<List<Double>> matrix = new ArrayList<>();
    matrix.add(array1);
    matrix.add(array2);
    matrix.add(array3);
    matrix.add(array2);
    matrix.add(array1);

    return matrix;
  }

  /**
   * Creates matrix that will be applied to blur image.
   * @return matrix of blur filter.
   */
  private static List<List<Double>> createMatrixBlur() {
    List<Double> array1 = new ArrayList<>();
    array1.add(1.0 / 16.0);
    array1.add(1.0 / 8.0);
    array1.add(1.0 / 16.0);
    List<Double> array2 = new ArrayList<>();
    array2.add(1.0 / 8.0);
    array2.add(1.0 / 4.0);
    array2.add(1.0 / 8.0);
    List<Double> array3 = new ArrayList<>();
    array3.add(1.0 / 16.0);
    array3.add(1.0 / 8.0);
    array3.add(1.0 / 16.0);

    List<List<Double>> matrix = new ArrayList<>();
    matrix.add(array1);
    matrix.add(array2);
    matrix.add(array3);

    return matrix;
  }

  /**
   * Outputs the matrix created depending on the {@code KernelType}.
   * @return list of list of double that is matrix of filter.
   */
  public List<List<Double>> getMatrix() {
    return this.matrix;
  }
}
