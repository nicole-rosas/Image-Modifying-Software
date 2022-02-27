package controller;

import java.io.File;

/**
 * Represents the creating a directory of a given path.
 */
public class CreateDirectoryConcrete {

  /**
   * creates a directory at the given string which is path.
   * @param directory string which is path to be created
   */
  public static void createDirectory(String directory) {
    if (directory == null) {
      throw new IllegalArgumentException("Directory cannot be null.");
    }

    File file = new File(directory);
    boolean bool = file.mkdirs();

    if (!bool) {
      throw new IllegalArgumentException("Cannot create the specified directory.");
    }
  }
}
