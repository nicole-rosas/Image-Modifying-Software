package controller;

/**
 * Interface that allows for a file to do multiple commands in image processing.
 */
public interface InterfaceScripting {

  /**
   * imports a BatchFile that has a series/several commands that can be run in image processing.
   * @param filename name of the file with the commands.
   * @return the text of the file as a string to be made as commands for image processing.
   */
  public String importBatchFile(String filename);
}
