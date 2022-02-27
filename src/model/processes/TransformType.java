package model.processes;

import java.util.ArrayList;
import java.util.List;

/**
 * enum of color transformations:
 *  SEPIA - transforms colors of image into a reddish brown tone.
 *  GREYSCALE - transforms colors of image into black and white
 */
public enum TransformType {
  SEPIA(TransformType.createMatrixSepia()), GREYSCALE(TransformType.createMatrixGreyScale());

  private final List<List<Double>> matrix;

  /**
   * Constructor of {@code TransformType} that has fields of a matrix.
   * @param matrix lists of lists of double.
   */
  TransformType(List<List<Double>> matrix) {
    this.matrix = matrix;
  }

  /**
   * Creates a matrix that will be applied to create sepia effect.
   * @return matrix of sepia filter.
   */
  private static List<List<Double>> createMatrixGreyScale() {
    List<Double> array1 = new ArrayList<>();
    array1.add(0.2126);
    array1.add(0.7152);
    array1.add(0.0722);

    List<List<Double>> matrix = new ArrayList<>();
    matrix.add(array1);
    matrix.add(array1);
    matrix.add(array1);

    return matrix;
  }

  /**
   * Creates a matrix that will be applied to create greyscale effect.
   * @return matrix of greyscale filter.
   */
  private static List<List<Double>> createMatrixSepia() {
    List<Double> array1 = new ArrayList<>();
    array1.add(0.393);
    array1.add(0.769);
    array1.add(0.189);
    List<Double> array2 = new ArrayList<>();
    array2.add(0.349);
    array2.add(0.686);
    array2.add(0.168);
    List<Double> array3 = new ArrayList<>();
    array3.add(0.272);
    array3.add(0.534);
    array3.add(0.131);

    List<List<Double>> matrix = new ArrayList<>();
    matrix.add(array1);
    matrix.add(array2);
    matrix.add(array3);

    return matrix;
  }

  /**
   * Outputs the matrix created depending on the {@code TransformType}.
   * @return list of list of double that is matrix of filter.
   */
  public List<List<Double>> getMatrix() {
    return this.matrix;
  }
}
