package controller;

import controller.InterfaceScripting;
import java.util.Scanner;

/**
 * the mock model for scripting used for tests.
 */
public class ScriptingMock implements InterfaceScripting {

  @Override
  public String importBatchFile(String command) {
    if (command == null) {
      throw new IllegalArgumentException("Commands cannot be null.");
    }

    Scanner sc = new Scanner(command);

    StringBuilder builder = new StringBuilder();

    while (sc.hasNext()) {
      String s = sc.next();

      if (s.charAt(0) == '#') {
        sc.nextLine();
      }
      else {
        builder.append(s).append(" ");
      }
    }

    return builder.toString();
  }
}
