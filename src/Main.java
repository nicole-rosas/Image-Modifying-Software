import controller.ImageEnhanceBasicController;
import controller.ImageEnhanceControllerInterface;
import controller.guicontroller.GUIController;
import model.MultiLayerEnhanceModel;

/**
 * The main class to test Image generation and editing.
 */
public class Main {

  /**
   * Running the program.
   *
   * @param args Arguments of the main program.
   */
  public static void main(String[] args) {

    MultiLayerEnhanceModel model = new MultiLayerEnhanceModel();

    if (args[0].equals("-script")) {
      ImageEnhanceControllerInterface controller = new ImageEnhanceBasicController(model, args[1]);
      controller.runProgram();
    }
    else if (args[0].equals("-text")) {
      ImageEnhanceControllerInterface controller =
          new ImageEnhanceBasicController();
      controller.runProgram();
    }
    else if (args[0].equals("-interactive")) {
      ImageEnhanceControllerInterface controller = new GUIController();
      controller.runProgram();
    }
  }
}
