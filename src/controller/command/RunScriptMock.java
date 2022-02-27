package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.InterfaceScripting;
import controller.ScriptingMock;
import model.MultiLayeredEnhanceInterface;

/**
 * Mock for RunScript that will be used for testing.
 */
public class RunScriptMock implements EnhanceCommandInterface {

  private final String command;
  private final StringBuilder log;

  /**
   * Constructor for RunScriptMock that will be used for testing.
   * takes in a String and a StringBuilder.
   * @param commands String that is a command to be used in image processing.
   * @param log StringBuilder
   */
  public RunScriptMock(String commands, StringBuilder log) {
    if (commands == null || log == null) {
      throw new IllegalArgumentException("Commands or log provided cannot be null.");
    }

    this.command = commands;
    this.log = log;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    InterfaceScripting mock = new ScriptingMock();
    log.append(mock.importBatchFile(command));
  }
}
