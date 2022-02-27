README: IMAGE ENHANCER/EDITOR

About/Overview:

The image editor is created with the core principle of Model-View-Controller in mind. The project tackles the multitude of design choices that a programmer or software engineer has to tackle when starting a project. Our program uses many design patterns to due with this problem, including the Command Design Pattern to reduce the bulk of adding switch statements to our controller, as well as observers and indivdual interface that disallow - say a view - access the model's mutable functionality. On every assignment, whenever we need to implement functionality, we will extend the interface of the created project to simulate a programmer writing onto a large code base.

List of Features:

- Scripting mode. You can run our application from the Command line, specify that we are running in scripting mode, and enter the script location. The program will run all the commands in the script, and exit upon finishing.

- Text mode. Run the program with an interactive text command line. Users can enter their commands related to the program into the command line. 

- Interactive mode. Run the program with a full-fledged GUI. The user can have an experience like other editors.

- Blur/Sharpen/Sepia/Greyscale processes available as image editing and processing.

- Layers. The users have access to layers with functionalities such as: moving the layer up and down; hiding and making layers visible; renaming layers; saving the project into a directory as layers; loading saved layers project; deleting and adding layers.

- Importing and Exporting images in the following supported format: PPM, PNG, JPEG

- Importing and running a script.

- Undo operations.

- Creating a checkerboard image with these editable parameters: width, height, interval of color usage, and two colors from a list of color presets.


How To Run:

To run the jar file, have your jar file in the run directory. For example, if your command line is in the /home/programming/ directory, put the jar file in the programming directory. We have 3 run modes:

- java -jar program.jar -text : This will take you to the text editor of the program, which will run on the command line.

- java -jar program.jar -interactive : This will open a GUI for the application, and users will be able to run the program like any image editing software.

- java -jar program.jar -script /path/to/script/file : This will run the program by loading in the script, running the script, and will close the program upon completition.

- Example Usage:

if the script file is in /home/programming/script/, and its name is runme.txt, the command line operation is
 "java -jar program.jar -script /home/programming/script/runme.txt"


Functionality Usage:

Our text-view as well as GUI view are straightforward to use. In our text view, we print out instructions available to use in the current program state. For example, if we have not created any layers, we will not be able to import an image, as there is no current layer to store the image. 

Our GUI is built to be foolproof. Anything that the user do that will create an error in the program will be displayed as a message at the bottom of the GUI.

Note: When exporting, the user must include the file format that is supported, which is .ppm, .png, .jpeg.

Note: To save layers, just go into the wanted directory, and type in the desired directory name that you want the program to save your layers into.

Example:

to save to directory Assignment/help/me/too/program/, we go to the directory
Assignment/help/me/too/, and then input the folder "program".

Example: export filename.<desired format here>

Example Run:

text:

add layer1
add layer2
change current layer2
change name layer3
import image.ppm
blur
export image-blur.ppm
q

"add" adds a layer, named by the following string input.
"change" change state. It can either change the current layer or the current layer's name.
"import" imports image into the current layer.
"blur" blurs the current layer's image.
"export" exports an image. The top most image.
q is the exit key.


Design/Model Changes:

- We added in an automatic crop; It was a functionality that we did not know we need to implement. (cropping an image when importing an image that is bigger than the first image imported.)

- We changed the model to include a "get Current Layer's position" for ease of implementation of rearranging layers, which was not a requirement in the assignment.

- We added in an Image functionality to suppor the automatic crop above.

Assumptions:

- We assumed that future assignment will make us implement an undo feature, which is so critical in any editor program. We designed the undo functionality ahead of time.

- We assumed that we would never need to dynamically script a script file. We did not write any methods or class that will be able to support it in the future. Image editing software like Gimp or photoshop does not support dynamic scripting natively.

- We assumed that we would not need to implement the "redo" functionality. Implementing "redo" can just be adding the current state to the undo, and pop the second item. However, we feel like that will slow our program significantly.

Limitations:

- We did not fully test the image generation. Because we allowed many changeable parameters, there can be some case where the image produced will not be the one desired.

Previous Readme:

Image Enhancer/Editor


This project is an image enhancer/editor. It supports a few color transformations, as well as some convolution or filtering. 


TABLE OF CONTENTS:


1. Model
   1. Pixels Representation
   2. Image Representation
   3. Importing
      1. Import format support
      2. PPM Images
      3. PPM Image Conversion
   4. Exporting
      1. Exporting format support
      2. PPM Exporting
   5. Generating Image Programmatically
      1. Builder Design Pattern
      2. Parameters for Generating Image
      3. Presets
   6. Processes
      1. Process Abstraction
      2. Creator Design
      3. Image Editing
         1. Filtering
         2. Color Transform
   7. Main Model


2. View
   1. Text View Image Enhance


























MODEL


1. Pixels Representation


Our pixel interface and implementation is very similar to how pixels are in real life. It contains three values for Red, Green, and Blue. It also has a clamping functionality, where users can specify how much to clamp their pixel colors.


Our PixelsRGB constructor is public, and takes in 3 values representing red, green and blue. We assign these values individually to the color values, and then clamp to a default value of min = 0, max = 255;


void getRed()   -- Returns the value of the red color. Does not create a new instance to return, as int is immutable.
void getBlue()  -- Returns the value of the blue color. Does not create a new instance to return, as int is immutable.
void getGreen() -- Returns the value of the green color. Does not create a new instance to return, as int is immutable.


void clampAll(int min, int max) -- given a minimum clamp value and a max clamp value, clamps all colors to the given min-max.Specified that the min and max provided cannot be out of the range of [0, 255].
         
IPixels copyPixel() -- Returns a copy of a pixel. Creates a new instance of pixels with the same color value. Does not need to create a new instance of color values, as int is not mutable.
         
boolean equals() -- We override equals to provide a pixel comparison that is not based on intensional equality, but on the color values itself.
int Hashcode() -- We also override hashcode because it is important to override the equals.
















2. Image Representation


Our Image representation is similar to that of an actual image. It is a matrix of our pixel implementation. In the image representation, we have methods that supply our model, or its processes, information of the image, as well as modifying the image. It also provides a method for deep copy, so we do not inadvertently alias our objects. The interface allows for future Image implementation, and has a creator class that creates the image given an enumeration.


The Image2DMatrix constructor initializes an empty Arraylist. If the List of List of arrayList is empty when the image class is called to do any of its image-dependent operations, it will throw an exception.


(Methods in InterfaceImages) 


Void initiateImage(List<List<IPixels>> loloPixels) -- This method takes in a list of list of pixels, and assigns it as the image. We deep copy the pixels in fear of aliasing.


InterfaceImages filterImage(List<List<Double>> matrix, int min, int max) -- This method is performed on <this object> image, filter it based on the matrix given, and then clamp the image pixels to the given min and max values. The process will be covered in part (fiii).


InterfaceImages transformImage(List<List<Double>> matrix, int min, int max) --
This method is performed on <this object> image, transform its colors based on the matrix given, and then clamp the image pixels to the given min and max values. The process will be covered in part (fiii).


int matrixMultiply(IPixels pixel, List<List<Double>> matrix, int index) -- This method is a helper method that helps do linear combination on the matrix given and the rgb values of the pixel given. This is linear algebra, and multiplies the 3x3 matrix with the 3 x 1 vector. So [(a,b,c), (d,e,f), (g,h,i)] x [r,G,B] will make: r = ar + bG + cB, g = dr + eG + fB, B = gr + hG + iB.


InterfaceImages getDeepCopy() -- This method returns an image that is a deep copy of <this object> image. We deep copy every single pixel as well, to prevent aliasing.


int getWidth() -- This method returns the width of the image.


int getHeight() -- This method returns the height of the image.


IPixels getPixel(int rows, int cols) -- This method returns a deep copy of a pixel at the given row and given column.


(Methods in ImageCreator)


InterfaceImages create(ImageType type) -- This method creates the correct image implementation given the Image type enumeration. This allows the image class to have a default visibility, and not allow the client to directly create an image, but instead should use the creator class.




















































3. Importing


Our import interface acts as a function for our main model, and therefore does not implement any constructors. It has a simple method that imports an image given a file name.


i) Import format supports the future creation of more import file format. It has an enum for Import Type (file extension), and a creator class that creates the correct object depending on the enum. 


ii) PPM images are plain text image files that are encoded in ASCII or plain text. It has a format specification that we follow to convert the PPM file into our own specific image implementation.


iii) Image file conversion: 


void importImage(String filename) -- Given a string that represents the target file name, we import an image. Using a scanner, we first scan the file. Then, in our plain text image file implementation, we scan for each line. We disregard any comment line. Then from the String builder, we process the inputs. PPM file format arranges it in RGB, so we convert the String values into our pixel implementation. Finally, we generate an image according to the specification of either JPEG, PPM or PNG.


(Import Model Creator)


ImportInterface create(ImportType type) -- Given an enum file type, it returns the class that does the import of the file. In one case, an enum of PPM will return the PPMImport class, which will import a PPM file specifically.




















4. Exporting


Our export interface acts as a function for our main model, and therefore does not implement any constructors. It has a simple method that exports an image with either a custom file name, or its own file name, essentially overwriting its previous file state.


i) Export format supports the future creation of more export file formats. It has an enum for Export Type (file extension), and a creator class that creates the correct object depending on the enum. 


ii) Image file exporting:


void export(InterfaceImage images, String filename) -- This method tries to export the given image with its given filename. In PPM exporting, we first append our new String with P3, which signifies the plain text PPM format. Then we append the size, as well as its max color value. We then get each color from each pixel of the image, and append it, separating each integer value with a space. Finally, we use PrintWriter and FileWriter to write the String into the file, and write the file onto our machine.








































5. Generating Image Programmatically


Overview of class usage: Main model creates a builder using a builderCreator class, that creates a builder based on the given preset enum. This builder will build the height, width, color, and interval of the class that is stored by the builder. The builder then builds the class. The class then is called to generate an Image with its specifications.


Generating Image with code is something that is hard because of the amount of things that can be done with it. Our program relies on the Builder pattern and creator pattern to support different “presets” in the future. Specifically, coders will be able to add different kinds of programmatically drawn images, and have the creator create the necessary class to draw such an image. We also have an enum that will signify the image that will be generated.


i) Builder Design pattern: This allows the user to specify the specifics of the image preset that they want to create. Allowed specifications are: width and height of image, colors of image and color usage interval. If any of these are left unchanged by the builder, the class has its own default values, and will generate its image according to those values.


ii) Parameters for Generating Image: width, height, list of colors are self-explanatory. Color usage interval is scaled up by 100 pixels (in the checkerboard implementation) so that patterns can be seen correctly. The class will generate a pixel using the current color that the interval is on, once the color has been used the interval indicated amount of time, the next pixel will be of a different color. 


Any of these values, if left at -1, or empty in the case of list of colors, will use its default values. 


















(For Checkerboard generation)


We have 4 customizations. The width and height of the image, the interval of color use, and the list of colors to be used. If left unchanged, or given values that are negative or empty (in the case of the list of colors), it will default to a size of 500x500, interval of 1, and colors of black and white.


To scale up for the users, we multiply this interval by 100 (really it should be the width/100), and will only change color use after this scaled Interval.


InterfaceImages generateImage() -- Returns an image in the pattern of a checkerboard using the specifications. 


void setWidth(int width) -- Given an int, set it as the width of the image. If the given width is less than 1, it is not accepted, and the original/default width value is kept.


void setHeight(int height) -- Given an int, set it as the height of the image. If the given height is less than 1, it is not accepted, and the original/default height value is kept.


void setColors(List<Color> colors) -- Given a list of colors, the program will use it to generate the pattern. If the list of color is empty or null, then the list of colors is not accepted, and it will use its default colors.


void setAlternatingInterval(double interval) -- given a double, set it as the interval. If the interval is 0, the program will default to its set interval.


(Checkerboard builder)


void reset() -- When called, it will restore the Image generator object that it is storing to its default.


void setInterval(Double interval) -- Given a double interval, set the class to be built with the given custom interval.


IGenerateImage generatePresetClass() -- Calling this class will return the Image generator class. 


void setWidth(int x) -- given an int x, set the image generator class’ width to that int.


Void setHeight(int x) -- given an int x, set the image generator class’s height to that int.


void setColor(List<Color> colors) -- given a list of colors, set the image generator class’s list of colors to it.


(BuilderCreator class)


static ImageBuilderInterface create(GeneratePresets presets) --
Given an enum of the different presets representing the different patterns that the program can create, will create the necessary builder that will build the image.
























































6. Processes


i) Process Abstraction: 


To alleviate clutterness, we do our processes outside of the model. Our process structure is in the shape of a process interface, with two abstract classes that represent two two features that we support so far. These two classes have a method that calls the image object’s method to filter and transform the image, and produce a new image. 


ii) Creator Design:


We use a creator to disallow the client to access these functions directly. The creator will take in an enum that represents the different functions that the model wants to do, and create the class that has the desired functionality.


iii) Image editing:


Convolution/filter -- this process is done by overlapping matrices. We use relative positions to do the calculations. We first do a double for loop that will visit every pixel. Then for each pixel, we go through each of the matrix values. We convert the position of the matrix to a position on the image, and if this position is “relatively” out of bounds of the image, it will not be added to the final sum. 


The final sums will be converted back to a pixel. We then add the pixel to the new image, in which it will be returned. 


Transform -- this process is done via matrix multiplication. The matrix in a transform process is guaranteed to be 3x3, to represent the 3 colors of a pixel. Each pixel is then linearly combined on each row of the matrix. We combine the colors with the values of the matrix, create a new pixel with the new color values, and add them to the new image. 


Both of these operations are then clamped to the given min and max color values.






(IProcesses)


InterfaceImage apply(InterfaceImages image, List<List<Double>> matrix, int min, int max) -- calling this method will call the respective image method that is specific to the process that is desired.


(KernelCreator)


IProcesses create(KernelType type) -- Given a correct kernel type(blur or sharpen), will create the process class that will apply the desired process.


(TransformCreator)


IProcesses create(TransformType type) -- Given a correct transform type (sepia or greyscale), will create the process class that will apply the desired process.


















































7. Main Model:


The main model is what will mainly interact with the View and Controller. The other process is not used outside of the main model.


The goal of the main model is not to have to deal with any input, and have the controller handle user inputs and actions.


The model stores a list of layers, the basic model to be used as a delegate, a layer which is the current layer, and a stack of program history.


void setName(String name) -- sets the name for the file, throws an error if the file name given is null, or gives a default name “image” if it is a name with 0 length.


String getName() -- gets the name of the file.


void transformSepia() -- transform the stored image into a sepia. Because the process returns an InterfaceImage, we do not fear any aliasing or mutation.


void transformGreyscale() -- transform the stored image into a grayscale image. Because the process returns an InterfaceImage, we do not fear any aliasing or mutation.


void convolutionBlur() -- blurs the stored image. Because the process returns an InterfaceImage, we do not fear any aliasing or mutation.


void convolutionSharpen() -- sharpens the stored image. Because the process returns an InterfaceImage, we do not fear any aliasing or mutation.


We also have private methods for these, which will allow the user to input a custom min value and max value for clamping. The methods above have a clamp value of 0, 255.


void setImage(InterfaceImages image) -- We deep copy the given image, and then set it and store it. By deep copying, we prevent any mutation.




InterfaceImage getImage() -- We deep copy our stored image, and then return that version. 


void GenerateCheckerBoardImage(int width, int height, Double interval, List<Color> color) -- Given the width, height, interval, and list of colors, we generate a checkerboard image, and then store it into our image.




View


The text view is what will appear on the screen of the user. There are three different outputs that the user can receive depending on the state of the image processing program. One) If there are no layers in the user’s program then it will show several methods to allow new layers to be added or upload already created layers from a file. Two) if the current layer is empty and the layer does not contain an image then other methods will be allowed to import or generate images into the layer as well as several others. Three) If there is an image in the layer more methods will appear to edit/filter/transform the image as well as save or change the state of the model. After each command the user enters, one of the three lists of commands will appear and on top there will be a table showing the list of created layers which includes the width and height, the name, if it's the current file, and if it is visible to the user. 


Controller


Controller controls what happens to the model and takes in the user’s inputs. It takes in a string of commands from the user and parses it to be read and sent as methods to the model. Our main void runProgram() allows the image processing program to run and change the model in whatever way the user desires. Classes inside the controller are commands that take in fields provided by the parsed input from the user. The controller inside has a command interface that takes care of the commands. Commands such as undo, sepia, save, export, import and others are all created here and sent to be performed by the model. The InterfaceScripting allows for a batch file to make multiple commands inside the image processing program. 




Citation


Henrietta.ppm - personally generated.
Checkerboard.ppm - personally generated.
Macaron.ppm - Barnes, Heather. "macarons on table." Unsplash, May 20, 2019, https://unsplash.com/photos/WbZesfqwR-A. June 11, 2021.
