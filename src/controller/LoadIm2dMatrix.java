package controller;

import controller.LoadMultiInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;

/**
 * Represents a way to take in a list of layers from a file.
 */
public class LoadIm2dMatrix implements LoadMultiInterface {

  @Override
  public List<InterfaceLayers> importLayer(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("file name given cannot be null.");
    }

    Map<Integer, InterfaceLayers> positionToLayer = new HashMap<>();

    Scanner sc;
    List<InterfaceLayers> layers = new ArrayList<>();
    List<List<IPixels>> image;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (IOException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    String layerLocation;
    if (sc.hasNextLine()) {
      layerLocation = sc.nextLine();
    }
    else {
      throw new IllegalArgumentException("Location file does not contain any contents.");
    }

    File folder = new File(layerLocation);
    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
      File file = listOfFiles[i];
      if (file.isFile() && file.getName().endsWith(".matrixcustom")) {
        try {
          sc = new Scanner(file);
        }
        catch (IOException e) {
          throw new IllegalArgumentException("Cannot read the given file.");
        }
        image = new ArrayList<>();

        String name = sc.nextLine();
        String vis = sc.nextLine();
        int pos = sc.nextInt();
        int width = sc.nextInt();
        int height = sc.nextInt();

        for (int x = 0; x < height; x++) {
          image.add(new ArrayList<>());
          for (int y = 0; y < width; y++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            IPixels pixel = new PixelsRGB(r, g, b);
            image.get(x).add(pixel);
          }
        }
        InterfaceImages imageFinal = ImageCreator.create(ImageType.MATRIX2D);
        if (image.size() == 0) {
          imageFinal = null;
        }
        else {
          imageFinal.initiateImage(image);
        }

        InterfaceLayers layer = new Layer2DMatrix();
        layer.setImageInLayer(imageFinal);
        layer.setName(name);
        layer.setVisibility(vis.equalsIgnoreCase("visible"));

        positionToLayer.put(pos, layer);
      }
    }

    int counter = 0;

    while (counter < positionToLayer.size()) {
      layers.add(positionToLayer.get(counter));
      counter++;
    }

    return layers;
  }
}
