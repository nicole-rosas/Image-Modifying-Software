import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import org.junit.Before;
import org.junit.Test;
import view.TextViewImageEnhance;

/**
 * Tests the methods inside TextViewImageEnhance class.
 */
public class TextViewImageEnhanceTest {

  TextViewImageEnhance view;
  MultiLayeredEnhanceInterface model;
  StringBuilder log;

  @Before
  public void setup() {
    model = new MultiLayerEnhanceModel();
    log = new StringBuilder();
    view = new TextViewImageEnhance(model, log);
  }

  @Test
  public void testViewCurrentStateForNoLayers() {
    StringBuilder s = new StringBuilder();
    s.append("Layer names | is current? | Image Size | Visible Status\n");
    s.append("No layers are currently available.\n");
    s.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    s.append("Invalid commands or file names or file locations will be ignored "
        + "instead of crashing the program.\n");
    s.append("You do not have any layers. These are your commands:\n");
    s.append("load <String> <Location> - load followed by 'layers' will load the layer "
        + "at the location provided.\n");
    s.append("add <String> - add followed by a string name will add a visible layer to the "
        + "top of the program, with the given name.\n");
    s.append("newDir <String> - Will create a new directory at the given path.\n");
    s.append("run <String> - run followed by the file location will run the script.\n");
    String text = s.toString();
    try {
      view.renderCurrentState();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(text, log.toString());
  }

  @Test
  public void testViewCurrentStateForEmptyLayer() {
    model.addLayer("ff");
    StringBuilder s = new StringBuilder();
    s.append("Layer names | is current? | Image Size | Visible Status\n");
    s.append("ff | current | 0 | 0 | visible\n");
    s.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    s.append("Invalid commands or file names or file locations will be ignored instead"
        + " of crashing the program.\n");
    s.append("You are on an empty layer without an image. These are your commands:\n");
    s.append("import <Type> <String> - import followed by the type and"
        + " the string will import a file in the given format. PPM, JPEG, PNG.\n");
    s.append("save <String> - save followed by the location name will save all "
        + "layers as files and a file that contains the location of the layer files. It will"
        + "automatically create new folders for the user in the user's desired location.\n");
    s.append("load <String> <Location> - load followed by "
        + "'layers' will load the layer at the location provided.\n");
    s.append("generate <String> <int> <int> <double>"
        + " - generate the image pattern that is specified and "
        + "load it onto the current layer. The three follow up ints and double can be entered,"
        + " or leave it as -1 to use default values.\n");
    s.append("add <String> - add followed by a string name will add a visible layer to "
        + "the top of the program, with the given name.\n");
    s.append("change <String> <String> - change followed by the change command and "
        + "name. If the process is name, then the second string is the new name of the layer."
        + "If the process is change 'current' layer, then the second string is the name of the "
        + "layer to be current. If the process is 'visibility', then the layer will be hidden "
        + "if the second input is 'hidden' and the layer will be visible if the "
        + "second layer is 'visible'.\n");
    s.append("delete <String> - delete the layer that is the given name. If incorrect"
        + " name is provided, then does not do anything.\n");
    s.append("newDir <String> - Will create a new directory at the given path.\n");
    s.append("switch <String> <int> - changes the position of the "
        + "layer by the place.\n");
    s.append("hide <String> - change the visibility of the "
        + "layer by the name to hidden.\n");
    s.append("visible <String> - change the visibility of the "
        + "layer by the name to visible.\n");
    s.append("undo - undo will go back to the previous layers state.\n");
    String text = s.toString();
    try {
      view.renderCurrentState();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(text, log.toString());
  }

  @Test
  public void testRenderMessage() throws IOException {
    view.renderMessage("Hello");

    assertEquals("Hello", log.toString());

    view.renderMessage(" World");

    assertEquals("Hello World", log.toString());
  }

  @Test
  public void testViewCurrentStateForLayerWithImage() {
    InterfaceImages blue = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pixel = new PixelsRGB(0, 0, 255);
    List<IPixels> listOfB = new ArrayList<>();
    listOfB.add(pixel);
    listOfB.add(pixel);
    List<List<IPixels>> bSquare = new ArrayList<>();
    bSquare.add(listOfB);
    bSquare.add(listOfB);
    blue.initiateImage(bSquare);
    model.addLayer("four");
    model.setImage(blue);
    StringBuilder s = new StringBuilder();
    s.append("Layer names | is current? | Image Size | Visible Status\n");
    s.append("four | current | 2 | 2 | visible\n");
    s.append("Commands are case-dependent. Will all be converted to lowercase.\n");
    s.append("Invalid commands or file names or file locations will be ignored instead"
        + " of crashing the program.\n");
    s.append("You are on a normal layer with an image. These are your commands:\n");
    // Quit
    s.append("You can quit by entering 'q' or 'Q'\n");
    // Sepia
    s.append("sepia - transform the image in the current layer "
        + "with the sepia process.\n");
    // Greyscale
    s.append("greyscale - transform the image in the current layer"
        + "with the greyscale process.\n");
    // Blur
    s.append("blur - filter the image in the current layer with the "
        + "blur process.\n");
    // Sharpen
    s.append("sharpen - filter the image in the current layer with the"
        + " sharpen process.\n");
    // Import
    s.append("import <Type> <String> - import followed by the type and"
        + " the string will import a file in the given format. PPM, JPEG, PNG.\n");
    // Export
    s.append("export <Type> <String> - export followed by the type and"
        + " the string will export a file in the given format and name. PPM, JPEG, PNG\n");
    // Save
    s.append("save <String> - save followed by the location name will save all "
        + "layers as files and a file that contains the location of the layer files. It will"
        + "automatically create new folders for the user in the user's desired location.\n");
    // Load
    s.append("load <String> <Location> - load followed by "
        + "'layers' will load the layer at the location provided.\n");
    // Generate
    s.append("generate <String> <int> <int> <double>"
        + " - generate the image pattern that is specified and "
        + "load it onto the current layer. The three follow up ints and double can be entered,"
        + " or leave it as -1 to use default values.\n");
    // Add
    s.append("add <String> - add followed by a string name will add a visible layer to "
        + "the top of the program, with the given name.\n");
    // Change
    s.append("change <String> <String> - change followed by the change "
        + "command and name, and optional status. "
        + "If the process is 'name', then the second string is the new name of the layer.\n"
        + "If the process is change 'current' layer, then the second string is the name of the "
        + "layer to be current.\n");
    // Change Vis
    s.append("hide <String> - change the visibility of the "
        + "layer by the name to hidden.\n");
    s.append("visible <String> - change the visibility of the "
        + "layer by the name to visible.\n");
    // Switch Place
    s.append("switch <String> <int> - changes the position of the "
        + "layer by the place.\n");
    // Undo
    s.append("undo - undo will go back to the previous layers state.\n");
    // Remove
    s.append("removeImage - remove will remove the current image in the layer.\n");
    // Delete
    s.append("delete <String> - delete the layer that is the given name. If incorrect"
        + " name is provided, then does not do anything.\n");
    // Create
    s.append("newDir <String> - Will create a new directory at the given path.\n");
    // Run Script
    s.append("run <String> - run followed by the file location will run the script.\n");
    String text = s.toString();
    try {
      view.renderCurrentState();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(text, log.toString());
  }
}