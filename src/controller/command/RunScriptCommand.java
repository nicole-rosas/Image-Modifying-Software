package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.ImageEnhanceCommandImport;
import controller.InterfaceScripting;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents making the model run using commands and outputing text for user to see.
 */
public class RunScriptCommand implements EnhanceCommandInterface {

  private final String filename;
  private final StringBuilder log;

  /**
   * constructor for RunScriptCommand that takes in String and StringBuilder used by user.
   * @param filename String that is a name of the file.
   * @param log to be built by user.
   */
  public RunScriptCommand(String filename, StringBuilder log) {
    if (filename == null || log == null) {
      throw new IllegalArgumentException("File name or command log provided cannot be null.");
    }

    this.filename = filename;
    this.log = log;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    InterfaceScripting scripter = new ImageEnhanceCommandImport();
    log.append(scripter.importBatchFile(filename));
  }
}
