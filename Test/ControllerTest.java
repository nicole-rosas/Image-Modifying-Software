import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.EnhanceControllerBasicMock;
import controller.ImageEnhanceControllerInterface;
import java.io.IOException;
import java.io.StringReader;
import model.CheckInputModel;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods inside Controller.
 */
public class ControllerTest {

  private String batch;
  private MultiLayeredEnhanceInterface model;
  private CheckInputModel mock;
  private StringBuilder log;


  @Before
  public void setup() {
    batch = "add layer1 add layer2 change current layer2 #comment section\nhere hide layer1 "
        + "import ppm henrietta.ppm blur export png henrietta-blur newDir batchTest save batchTest";
    model = new MultiLayerEnhanceModel();
    this.log = new StringBuilder();
    mock = new CheckInputModel(log);
  }

  @Test
  public void testDeleteLayer() throws IOException {
    testRunMock(model,
        inputs("add layer1"),
        prints("Layer names | is current? | Image Size | Visible "
            + "Status\nNo layers are currently available.\nCommands are case"
            + "-dependent. Will all be converted to lowercase.\nInvalid commands "
            + "or file names or file locations will be ignored instead of "
            + "crashing the program.\nYou do not have any layers. "
            + "These are your commands:\nload <String> <Location"
            + "> - load followed by \'layers\' will load the layer "
            + "at the location provided.\nadd <String> - add "
            + "followed by a string name will add a visible layer to "
            + "the top of the program, with the given name.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nrun <String> - run followed "
            + "by the file location will run the script.\n\n"
            + "Layer names | is current? | Image Size | Visible "
            + "Status\nlayer1 | current | 0 | 0 | visible"
            + "\nCommands are case-dependent. Will all be converted to "
            + "lowercase.\nInvalid commands or file names or file locations will "
            + "be ignored instead of crashing the program.\nYou are on "
            + "an empty layer without an image. These are your commands"
            + ":\nimport <Type> <String> - import followed by "
            + "the type and the string will import a file in the "
            + "given format. PPM, JPEG, PNG.\nsave <"
            + "String> - save followed by the location name will save "
            + "all layers as files and a file that contains the location "
            + "of the layer files. It willautomatically create new folders for "
            + "the user in the user\'s desired location.\nload "
            + "<String> <Location> - load followed by \'layers"
            + "\' will load the layer at the location provided.\ngenerate <"
            + "String> <int> <int> <double> "
            + "- generate the image pattern that is specified and load it onto "
            + "the current layer. The three follow up ints and double "
            + "can be entered, or leave it as -1 to "
            + "use default values.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nchange "
            + "<String> <String> - change followed by the change "
            + "command and name. If the process is name, then "
            + "the second string is the new name of the layer."
            + "If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state."));

    assertEquals(1, model.getNumLayers());

    testRunModel(mock,
        inputs("add layer1"),
        prints("addLayer(layer1)"));

    testRunMock(model,
        inputs("delete layer1"),
        prints("Layer names | is current? | Image Size | Visible "
            + "Status\nlayer1 | current | 0 | 0 | visible"
            + "\nCommands are case-dependent. Will all be converted to "
            + "lowercase.\nInvalid commands or file names or file locations will "
            + "be ignored instead of crashing the program.\nYou are on "
            + "an empty layer without an image. These are your commands"
            + ":\nimport <Type> <String> - import followed by "
            + "the type and the string will import a file in the "
            + "given format. PPM, JPEG, PNG.\nsave <"
            + "String> - save followed by the location name will save "
            + "all layers as files and a file that contains the location "
            + "of the layer files. It willautomatically create new folders for "
            + "the user in the user\'s desired location.\nload "
            + "<String> <Location> - load followed by \'layers"
            + "\' will load the layer at the location provided.\ngenerate <"
            + "String> <int> <int> <double> "
            + "- generate the image pattern that is specified and load it onto "
            + "the current layer. The three follow up ints and double "
            + "can be entered, or leave it as -1 to "
            + "use default values.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nchange "
            + "<String> <String> - change followed by the change "
            + "command and name. If the process is name, then "
            + "the second string is the new name of the layer."
            + "If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state.\n\nLayer names "
            + "| is current? | Image Size | Visible Status\nNo "
            + "layers are currently available.\nCommands are case-dependent. "
            + "Will all be converted to lowercase.\nInvalid commands or file "
            + "names or file locations will be ignored instead of crashing the "
            + "program.\nYou do not have any layers. These are "
            + "your commands:\nload <String> <Location> - "
            + "load followed by \'layers\' will load the layer at "
            + "the location provided.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nnewDir "
            + "<String> - Will create a new directory at the given "
            + "path.\nrun <String> - run followed by the "
            + "file location will run the script."));

    testRunModel(mock,
        inputs("delete layer1"),
        prints("addLayer(layer1)\n"
            + "removeLayer(layer1)"));

    assertEquals(0, model.getNumLayers());

    assertEquals(new StringBuilder("Place Holder").toString(), "Place Holder");
  }

  @Test
  public void testAddLayer() throws IOException {
    testRunMock(model,
        inputs("add layer1"),
        prints("Layer names | is current? | Image Size | Visible "
            + "Status\nNo layers are currently available.\nCommands are case"
            + "-dependent. Will all be converted to lowercase.\nInvalid commands "
            + "or file names or file locations will be ignored instead of "
            + "crashing the program.\nYou do not have any layers. "
            + "These are your commands:\nload <String> <Location"
            + "> - load followed by \'layers\' will load the layer "
            + "at the location provided.\nadd <String> - add "
            + "followed by a string name will add a visible layer to "
            + "the top of the program, with the given name.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nrun <String> - run followed "
            + "by the file location will run the script.\n\n"
            + "Layer names | is current? | Image Size | Visible "
            + "Status\nlayer1 | current | 0 | 0 | visible"
            + "\nCommands are case-dependent. Will all be converted to "
            + "lowercase.\nInvalid commands or file names or file locations will "
            + "be ignored instead of crashing the program.\nYou are on "
            + "an empty layer without an image. These are your commands"
            + ":\nimport <Type> <String> - import followed by "
            + "the type and the string will import a file in the "
            + "given format. PPM, JPEG, PNG.\nsave <"
            + "String> - save followed by the location name will save "
            + "all layers as files and a file that contains the location "
            + "of the layer files. It willautomatically create new folders for "
            + "the user in the user\'s desired location.\nload "
            + "<String> <Location> - load followed by \'layers"
            + "\' will load the layer at the location provided.\ngenerate <"
            + "String> <int> <int> <double> "
            + "- generate the image pattern that is specified and load it onto "
            + "the current layer. The three follow up ints and double "
            + "can be entered, or leave it as -1 to "
            + "use default values.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nchange "
            + "<String> <String> - change followed by the change "
            + "command and name. If the process is name, then "
            + "the second string is the new name of the layer."
            + "If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state."));

    assertEquals(1, model.getNumLayers());
    assertEquals("layer1", model.getCurrentLayer().getLayerName());

    testRunModel(mock,
        inputs("add layer1"),
        prints("addLayer(layer1)"));

    testRunMock(model,
        inputs("add layer2"),
        prints("Layer names | is current? | Image Size | Visible "
            + "Status\nlayer1 | current | 0 | 0 | visible"
            + "\nCommands are case-dependent. Will all be converted to "
            + "lowercase.\nInvalid commands or file names or file locations will "
            + "be ignored instead of crashing the program.\nYou are on "
            + "an empty layer without an image. These are your commands"
            + ":\nimport <Type> <String> - import followed by "
            + "the type and the string will import a file in the "
            + "given format. PPM, JPEG, PNG.\nsave <"
            + "String> - save followed by the location name will save "
            + "all layers as files and a file that contains the location "
            + "of the layer files. It willautomatically create new folders for "
            + "the user in the user\'s desired location.\nload "
            + "<String> <Location> - load followed by \'layers"
            + "\' will load the layer at the location provided.\ngenerate <"
            + "String> <int> <int> <double> "
            + "- generate the image pattern that is specified and load it onto "
            + "the current layer. The three follow up ints and double "
            + "can be entered, or leave it as -1 to "
            + "use default values.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nchange "
            + "<String> <String> - change followed by the change "
            + "command and name. If the process is name, then "
            + "the second string is the new name of the layer."
            + "If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state.\n\nLayer names "
            + "| is current? | Image Size | Visible Status\nlayer2 "
            + "|  | 0 | 0 | visible\nlayer1 | current | "
            + "0 | 0 | visible\nCommands are case-dependent"
            + ". Will all be converted to lowercase.\nInvalid commands or file "
            + "names or file locations will be ignored instead of crashing the "
            + "program.\nYou are on an empty layer without an image"
            + ". These are your commands:\nimport <Type> <String"
            + "> - import followed by the type and the string will import "
            + "a file in the given format. PPM, JPEG, "
            + "PNG.\nsave <String> - save followed by the "
            + "location name will save all layers as files and a file "
            + "that contains the location of the layer files. It willautomatically "
            + "create new folders for the user in the user\'s "
            + "desired location.\nload <String> <Location> - "
            + "load followed by \'layers\' will load the layer at "
            + "the location provided.\ngenerate <String> <int> "
            + "<int> <double> - generate the image pattern that "
            + "is specified and load it onto the current layer. The "
            + "three follow up ints and double can be entered, or "
            + "leave it as -1 to use default values.\nadd "
            + "<String> - add followed by a string name will add "
            + "a visible layer to the top of the program, with "
            + "the given name.\nchange <String> <String> "
            + "- change followed by the change command and name. If the "
            + "process is name, then the second string is the new "
            + "name of the layer.If the process is change \'"
            + "current\' layer, then the second string is the name "
            + "of the layer to be current. If the process is "
            + "\'visibility\', then the layer will be hidden if the second "
            + "input is \'hidden\' and the layer will be visible "
            + "if the second layer is \'visible\'.\ndelete <String"
            + "> - delete the layer that is the given name. If "
            + "incorrect name is provided, then does not do anything.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nswitch <String> <int> "
            + "- changes the position of the layer by the place.\nhide "
            + "<String> - change the visibility of the layer by the "
            + "name to hidden.\nvisible <String> - change the "
            + "visibility of the layer by the name to visible.\nundo "
            + "- undo will go back to the previous layers state."));

    testRunModel(mock,
        inputs("add layer2"),
        prints("addLayer(layer1)\n"
            + "addLayer(layer2)"));

    assertEquals(2, model.getNumLayers());
    model.changeCurrentLayer("layer2");
    assertEquals("layer2", model.getCurrentLayer().getLayerName());
  }

  @Test
  public void testSampleRun() throws IOException {
    testRunMock(model,
        inputs("add layer1 "),
        inputs("add layer2 "),
        inputs("change current layer2 "),
        inputs("hide layer2 "),
        inputs("switch layer2 0"),
        prints("Layer names | is current? | Image Size | Visible "
            + "Status\nNo layers are currently available.\nCommands are case"
            + "-dependent. Will all be converted to lowercase.\nInvalid commands "
            + "or file names or file locations will be ignored instead of "
            + "crashing the program.\nYou do not have any layers. "
            + "These are your commands:\nload <String> <Location"
            + "> - load followed by \'layers\' will load the layer "
            + "at the location provided.\nadd <String> - add "
            + "followed by a string name will add a visible layer to "
            + "the top of the program, with the given name.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nrun <String> - run followed "
            + "by the file location will run the script.\n\n"
            + "Layer names | is current? | Image Size | Visible "
            + "Status\nlayer1 | current | 0 | 0 | visible"
            + "\nCommands are case-dependent. Will all be converted to "
            + "lowercase.\nInvalid commands or file names or file locations will "
            + "be ignored instead of crashing the program.\nYou are on "
            + "an empty layer without an image. These are your commands"
            + ":\nimport <Type> <String> - import followed by "
            + "the type and the string will import a file in the "
            + "given format. PPM, JPEG, PNG.\nsave <"
            + "String> - save followed by the location name will save "
            + "all layers as files and a file that contains the location "
            + "of the layer files. It willautomatically create new folders for "
            + "the user in the user\'s desired location.\nload "
            + "<String> <Location> - load followed by \'layers"
            + "\' will load the layer at the location provided.\ngenerate <"
            + "String> <int> <int> <double> "
            + "- generate the image pattern that is specified and load it onto "
            + "the current layer. The three follow up ints and double "
            + "can be entered, or leave it as -1 to "
            + "use default values.\nadd <String> - add followed "
            + "by a string name will add a visible layer to the "
            + "top of the program, with the given name.\nchange "
            + "<String> <String> - change followed by the change "
            + "command and name. If the process is name, then "
            + "the second string is the new name of the layer."
            + "If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state.\nLayer names | is "
            + "current? | Image Size | Visible Status\nlayer2 |  "
            + "| 0 | 0 | visible\nlayer1 | current | 0 "
            + "| 0 | visible\nCommands are case-dependent. Will "
            + "all be converted to lowercase.\nInvalid commands or file names "
            + "or file locations will be ignored instead of crashing the program"
            + ".\nYou are on an empty layer without an image. These "
            + "are your commands:\nimport <Type> <String> "
            + "- import followed by the type and the string will import a "
            + "file in the given format. PPM, JPEG, PNG"
            + ".\nsave <String> - save followed by the location name "
            + "will save all layers as files and a file that contains "
            + "the location of the layer files. It willautomatically create new "
            + "folders for the user in the user\'s desired location"
            + ".\nload <String> <Location> - load followed by "
            + "\'layers\' will load the layer at the location provided.\n"
            + "generate <String> <int> <int> <"
            + "double> - generate the image pattern that is specified and "
            + "load it onto the current layer. The three follow up "
            + "ints and double can be entered, or leave it as "
            + "-1 to use default values.\nadd <String> - "
            + "add followed by a string name will add a visible layer "
            + "to the top of the program, with the given name"
            + ".\nchange <String> <String> - change followed by "
            + "the change command and name. If the process is name"
            + ", then the second string is the new name of the layer"
            + ".If the process is change \'current\' layer, then "
            + "the second string is the name of the layer to be "
            + "current. If the process is \'visibility\', then the "
            + "layer will be hidden if the second input is \'hidden"
            + "\' and the layer will be visible if the second layer is "
            + "\'visible\'.\ndelete <String> - delete the layer that "
            + "is the given name. If incorrect name is provided, "
            + "then does not do anything.\nnewDir <String> - "
            + "Will create a new directory at the given path.\nswitch "
            + "<String> <int> - changes the position of the "
            + "layer by the place.\nhide <String> - change "
            + "the visibility of the layer by the name to hidden.\n"
            + "visible <String> - change the visibility of the layer "
            + "by the name to visible.\nundo - undo will go "
            + "back to the previous layers state.\nLayer names | is "
            + "current? | Image Size | Visible Status\nlayer2 | "
            + "current | 0 | 0 | visible\nlayer1 |  | "
            + "0 | 0 | visible\nCommands are case-dependent"
            + ". Will all be converted to lowercase.\nInvalid commands or file "
            + "names or file locations will be ignored instead of crashing the "
            + "program.\nYou are on an empty layer without an image"
            + ". These are your commands:\nimport <Type> <String"
            + "> - import followed by the type and the string will import "
            + "a file in the given format. PPM, JPEG, "
            + "PNG.\nsave <String> - save followed by the "
            + "location name will save all layers as files and a file "
            + "that contains the location of the layer files. It willautomatically "
            + "create new folders for the user in the user\'s "
            + "desired location.\nload <String> <Location> - "
            + "load followed by \'layers\' will load the layer at "
            + "the location provided.\ngenerate <String> <int> "
            + "<int> <double> - generate the image pattern that "
            + "is specified and load it onto the current layer. The "
            + "three follow up ints and double can be entered, or "
            + "leave it as -1 to use default values.\nadd "
            + "<String> - add followed by a string name will add "
            + "a visible layer to the top of the program, with "
            + "the given name.\nchange <String> <String> "
            + "- change followed by the change command and name. If the "
            + "process is name, then the second string is the new "
            + "name of the layer.If the process is change \'"
            + "current\' layer, then the second string is the name "
            + "of the layer to be current. If the process is "
            + "\'visibility\', then the layer will be hidden if the second "
            + "input is \'hidden\' and the layer will be visible "
            + "if the second layer is \'visible\'.\ndelete <String"
            + "> - delete the layer that is the given name. If "
            + "incorrect name is provided, then does not do anything.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nswitch <String> <int> "
            + "- changes the position of the layer by the place.\nhide "
            + "<String> - change the visibility of the layer by the "
            + "name to hidden.\nvisible <String> - change the "
            + "visibility of the layer by the name to visible.\nundo "
            + "- undo will go back to the previous layers state.\nLayer "
            + "names | is current? | Image Size | Visible Status"
            + "\nlayer2 |  | 0 | 0 | hidden\nlayer1 |  "
            + "| 0 | 0 | visible\nCommands are case-dependent"
            + ". Will all be converted to lowercase.\nInvalid commands or file "
            + "names or file locations will be ignored instead of crashing the "
            + "program.\nYou are on an empty layer without an image"
            + ". These are your commands:\nimport <Type> <String"
            + "> - import followed by the type and the string will import "
            + "a file in the given format. PPM, JPEG, "
            + "PNG.\nsave <String> - save followed by the "
            + "location name will save all layers as files and a file "
            + "that contains the location of the layer files. It willautomatically "
            + "create new folders for the user in the user\'s "
            + "desired location.\nload <String> <Location> - "
            + "load followed by \'layers\' will load the layer at "
            + "the location provided.\ngenerate <String> <int> "
            + "<int> <double> - generate the image pattern that "
            + "is specified and load it onto the current layer. The "
            + "three follow up ints and double can be entered, or "
            + "leave it as -1 to use default values.\nadd "
            + "<String> - add followed by a string name will add "
            + "a visible layer to the top of the program, with "
            + "the given name.\nchange <String> <String> "
            + "- change followed by the change command and name. If the "
            + "process is name, then the second string is the new "
            + "name of the layer.If the process is change \'"
            + "current\' layer, then the second string is the name "
            + "of the layer to be current. If the process is "
            + "\'visibility\', then the layer will be hidden if the second "
            + "input is \'hidden\' and the layer will be visible "
            + "if the second layer is \'visible\'.\ndelete <String"
            + "> - delete the layer that is the given name. If "
            + "incorrect name is provided, then does not do anything.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nswitch <String> <int> "
            + "- changes the position of the layer by the place.\nhide "
            + "<String> - change the visibility of the layer by the "
            + "name to hidden.\nvisible <String> - change the "
            + "visibility of the layer by the name to visible.\nundo "
            + "- undo will go back to the previous layers state.\nLayer "
            + "names | is current? | Image Size | Visible Status"
            + "\nlayer1 |  | 0 | 0 | visible\nlayer2 |  "
            + "| 0 | 0 | hidden\nCommands are case-dependent"
            + ". Will all be converted to lowercase.\nInvalid commands or file "
            + "names or file locations will be ignored instead of crashing the "
            + "program.\nYou are on an empty layer without an image"
            + ". These are your commands:\nimport <Type> <String"
            + "> - import followed by the type and the string will import "
            + "a file in the given format. PPM, JPEG, "
            + "PNG.\nsave <String> - save followed by the "
            + "location name will save all layers as files and a file "
            + "that contains the location of the layer files. It willautomatically "
            + "create new folders for the user in the user\'s "
            + "desired location.\nload <String> <Location> - "
            + "load followed by \'layers\' will load the layer at "
            + "the location provided.\ngenerate <String> <int> "
            + "<int> <double> - generate the image pattern that "
            + "is specified and load it onto the current layer. The "
            + "three follow up ints and double can be entered, or "
            + "leave it as -1 to use default values.\nadd "
            + "<String> - add followed by a string name will add "
            + "a visible layer to the top of the program, with "
            + "the given name.\nchange <String> <String> "
            + "- change followed by the change command and name. If the "
            + "process is name, then the second string is the new "
            + "name of the layer.If the process is change \'"
            + "current\' layer, then the second string is the name "
            + "of the layer to be current. If the process is "
            + "\'visibility\', then the layer will be hidden if the second "
            + "input is \'hidden\' and the layer will be visible "
            + "if the second layer is \'visible\'.\ndelete <String"
            + "> - delete the layer that is the given name. If "
            + "incorrect name is provided, then does not do anything.\n"
            + "newDir <String> - Will create a new directory at "
            + "the given path.\nswitch <String> <int> "
            + "- changes the position of the layer by the place.\nhide "
            + "<String> - change the visibility of the layer by the "
            + "name to hidden.\nvisible <String> - change the "
            + "visibility of the layer by the name to visible.\nundo "
            + "- undo will go back to the previous layers state."));

    testRunModel(mock,
        inputs("add layer1 "),
        inputs("add layer2 "),
        inputs("change current layer2 "),
        inputs("hide layer2 "),
        inputs("switch layer2 0"),
        prints("addLayer(layer1)\n"
            + "addLayer(layer2)\n"
            + "changeCurrentLayer(layer2)\n"
            + "changeVisiblity(layer2)\n"
            + "switchLayerPosition(layer2, 0)"));

    assertFalse(model.getCurrentLayer().getVisibilityStatus());
    model.changeCurrentLayer("layer1");
    assertTrue(model.getCurrentLayer().getVisibilityStatus());

    this.log = new StringBuilder();
  }

  private void testRunMock(MultiLayeredEnhanceInterface model, Interaction... interactions)
      throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    ImageEnhanceControllerInterface controller =
        new EnhanceControllerBasicMock(model, input, actualOutput, this.batch);

    controller.runProgram();

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  private void testRunModel(CheckInputModel model, Interaction... interactions)
      throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder dontCareOutput = new StringBuilder();

    ImageEnhanceControllerInterface controller = new EnhanceControllerBasicMock(model, input,
        dontCareOutput, this.batch);

    controller.runProgram();

    assertEquals(expectedOutput.toString(), log.toString());
  }

  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append("\n");
      }
    };
  }

  static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in);
    };
  }
}
