package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents taking in a batch file that is to be read and made into string to be used as commands
 * for image processing.
 */
public class ImageEnhanceCommandImport implements InterfaceScripting {

  @Override
  public String importBatchFile(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename provided cannot be null.");
    }

    Scanner sc;
    Scanner perWord;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();

    while (sc.hasNext()) {
      String s = sc.next();

      if (s.charAt(0) == '#') {
        sc.nextLine();
      }
      if (s.charAt(0) != '#') {
        builder.append(s).append(" ");
      }
    }

    return builder.toString();
  }
}
