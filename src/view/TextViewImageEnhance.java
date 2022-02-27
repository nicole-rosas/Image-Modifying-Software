package view;

import java.util.Collections;
import java.util.List;
import java.io.IOException;
import model.MultiLayerModelState;
import model.images.image.InterfaceImages;
import model.layers.InterfaceLayers;

/**
 * Represents the text view for image processing. Where commands are printed out to let user know
 * how to edit an image layers.
 */
public class TextViewImageEnhance implements InterfaceImageEnhanceView {

  private final MultiLayerModelState model;
  private final Appendable ap;

  /**
   * constructor for TextViewImageEnhance that takes in a MultiLayerModelState.
   *
   * @param model MultiLayerModelState that will be viewed and edited by user.
   */
  public TextViewImageEnhance(MultiLayerModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    this.model = model;
    ap = System.out;
  }

  /**
   * constructor for TextViewImageEnhance that takes in MultiLayerModelState and Appendable.
   *
   * @param model MultilayerModelState that will be viewed and edited by user.
   * @param ap    is an Appendable.
   */
  public TextViewImageEnhance(MultiLayerModelState model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model or appendable cannot be null.");
    }

    this.model = model;
    this.ap = ap;
  }

  /**
   * Get the current state of the model. It will display the name of each layer, the visibility of
   * each layer, and the current layer. It will also display the size of each image layer. Will give
   * a set of commands that the layer with image can perform
   *
   * @throws IOException if the text render fails.
   */
  private void availCommandsNormalLayer() throws IOException {
    StringBuilder finalString = new StringBuilder();

    finalString.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    finalString.append("Invalid commands or file names or file locations will be ignored instead"
        + " of crashing the program.\n");
    finalString.append("You are on a normal layer with an image. These are your commands:\n");
    // Quit
    finalString.append("You can quit by entering 'q' or 'Q'\n");
    // Sepia
    finalString.append("sepia - transform the image in the current layer "
        + "with the sepia process.\n");
    // Greyscale
    finalString.append("greyscale - transform the image in the current layer"
        + "with the greyscale process.\n");
    // Blur
    finalString.append("blur - filter the image in the current layer with the "
        + "blur process.\n");
    // Sharpen
    finalString.append("sharpen - filter the image in the current layer with the"
        + " sharpen process.\n");
    // Import
    finalString.append("import <Type> <String> - import followed by the type and"
        + " the string will import a file in the given format. PPM, JPEG, PNG.\n");
    // Export
    finalString.append("export <Type> <String> - export followed by the type and"
        + " the string will export a file in the given format and name. PPM, JPEG, PNG\n");
    // Save
    finalString.append("save <String> - save followed by the location name will save all "
        + "layers as files and a file that contains the location of the layer files. It will"
        + "automatically create new folders for the user in the user's desired location.\n");
    // Load
    finalString.append("load <String> <Location> - load followed by "
        + "'layers' will load the layer at the location provided.\n");
    // Generate
    finalString.append("generate <String> <int> <int> <double>"
        + " - generate the image pattern that is specified and "
        + "load it onto the current layer. The three follow up ints and double can be entered,"
        + " or leave it as -1 to use default values.\n");
    // Add
    finalString.append("add <String> - add followed by a string name will add a visible layer to "
        + "the top of the program, with the given name.\n");
    // Change
    finalString.append("change <String> <String> - change followed by the change "
        + "command and name, and optional status. "
        + "If the process is 'name', then the second string is the new name of the layer.\n"
        + "If the process is change 'current' layer, then the second string is the name of the "
        + "layer to be current.\n");
    // Change Vis
    finalString.append("hide <String> - change the visibility of the "
        + "layer by the name to hidden.\n");
    finalString.append("visible <String> - change the visibility of the "
        + "layer by the name to visible.\n");
    // Switch Place
    finalString.append("switch <String> <int> - changes the position of the "
        + "layer by the place.\n");
    // Undo
    finalString.append("undo - undo will go back to the previous layers state.\n");
    // Remove
    finalString.append("removeImage - remove will remove the current image in the layer.\n");
    // Delete
    finalString.append("delete <String> - delete the layer that is the given name. If incorrect"
        + " name is provided, then does not do anything.\n");
    // Create
    finalString.append("newDir <String> - Will create a new directory at the given path.\n");
    // Run Script
    finalString.append("run <String> - run followed by the file location will run the script.\n");

    String fin = finalString.toString();

    this.renderMessage(fin);
  }

  /**
   * Gets the current state of the model. It will display the name of each layer, the visibility of
   * each layer, and the current layer. It will displace the size of each image layer. Will give a
   * set of commands that the empty layer can perform.
   *
   * @throws IOException if the text render fails.
   */
  private void availCommandsEmptyLayer() throws IOException {
    StringBuilder finalString = new StringBuilder();

    finalString.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    finalString.append("Invalid commands or file names or file locations will be ignored instead"
        + " of crashing the program.\n");
    finalString.append("You are on an empty layer without an image. These are your commands:\n");
    // Import
    finalString.append("import <Type> <String> - import followed by the type and"
        + " the string will import a file in the given format. PPM, JPEG, PNG.\n");
    // Save
    finalString.append("save <String> - save followed by the location name will save all "
        + "layers as files and a file that contains the location of the layer files. It will"
        + "automatically create new folders for the user in the user's desired location.\n");
    // Load
    finalString.append("load <String> <Location> - load followed by "
        + "'layers' will load the layer at the location provided.\n");
    // Generate
    finalString.append("generate <String> <int> <int> <double>"
        + " - generate the image pattern that is specified and "
        + "load it onto the current layer. The three follow up ints and double can be entered,"
        + " or leave it as -1 to use default values.\n");
    // Add
    finalString.append("add <String> - add followed by a string name will add a visible layer to "
        + "the top of the program, with the given name.\n");
    // Change
    finalString.append("change <String> <String> - change followed by the change command and "
        + "name. If the process is name, then the second string is the new name of the layer."
        + "If the process is change 'current' layer, then the second string is the name of the "
        + "layer to be current. If the process is 'visibility', then the layer will be hidden "
        + "if the second input is 'hidden' and the layer will be visible if the "
        + "second layer is 'visible'.\n");
    // Delete
    finalString.append("delete <String> - delete the layer that is the given name. If incorrect"
        + " name is provided, then does not do anything.\n");
    // Create
    finalString.append("newDir <String> - Will create a new directory at the given path.\n");
    // Switch Place
    finalString.append("switch <String> <int> - changes the position of the "
        + "layer by the place.\n");
    // Change Vis
    finalString.append("hide <String> - change the visibility of the "
        + "layer by the name to hidden.\n");
    finalString.append("visible <String> - change the visibility of the "
        + "layer by the name to visible.\n");
    // Undo
    finalString.append("undo - undo will go back to the previous layers state.\n");

    String fin = finalString.toString();

    this.renderMessage(fin);
  }

  /**
   * Only prints the header of our Image Processing and will output a set of commands that the new
   * image editor file can perform.
   *
   * @throws IOException if the text render fails.
   */
  private void availCommandsNoLayer() throws IOException {
    StringBuilder finalString = new StringBuilder();

    finalString.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    finalString.append("Invalid commands or file names or file locations will be ignored instead"
        + " of crashing the program.\n");
    finalString.append("You do not have any layers. These are your commands:\n");
    // Load
    finalString.append("load <String> <Location> - load followed by "
        + "'layers' will load the layer at the location provided.\n");
    // Add
    finalString.append("add <String> - add followed by a string name will add a visible layer to "
        + "the top of the program, with the given name.\n");
    // Create
    finalString.append("newDir <String> - Will create a new directory at the given path.\n");
    // Run Script
    finalString.append("run <String> - run followed by the file location will run the script.\n");

    String fin = finalString.toString();

    this.renderMessage(fin);
  }

  @Override
  public void renderCurrentState() throws IOException {
    StringBuilder build = new StringBuilder();

    int numLayers = model.getNumLayers();
    String state;

    build.append("Layer names | is current? | Image Size | Visible Status\n");

    if (numLayers == 0) {
      build.append("No layers are currently available.\n");
      this.renderMessage(build.toString());
      this.availCommandsNoLayer();
    } else {
      List<InterfaceLayers> allLayers = model.getAllLayers();
      Collections.reverse(allLayers);

      for (InterfaceLayers l : allLayers) {
        int width;
        int height;

        if (l.isImageEmpty()) {
          width = 0;
          height = 0;
        }
        else {
          InterfaceImages img = l.getLayerImage();
          width = img.getWidth();
          height = img.getHeight();
        }

        String isCurrent = "";
        String visStat;
        if (model.getCurrentLayer().equals(l)) {
          isCurrent = "current";
        }
        if (l.getVisibilityStatus()) {
          visStat = "visible";
        } else {
          visStat = "hidden";
        }

        build.append(l.getLayerName()).append(" | ").append(isCurrent).append(" | ");
        build.append(width).append(" | ").append(height).append(" | ").append(visStat);
        build.append("\n");
      }

      this.renderMessage(build.toString());

      InterfaceLayers curLayer = model.getCurrentLayer();

      if (curLayer.isImageEmpty()) {
        this.availCommandsEmptyLayer();
      }
      else {
        this.availCommandsNormalLayer();
      }
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message);
  }
}
