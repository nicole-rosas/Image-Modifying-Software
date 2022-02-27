package controller;

import controller.SaveMultiInterface;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.layers.InterfaceLayers;

/**
 * Represents the function to save layers and its order of a model.
 */
public class SaveIm2DMatrix implements SaveMultiInterface {

  @Override
  public void saveMultiLayersImage(List<InterfaceLayers> loLayers, String location) {
    if (loLayers == null || location == null) {
      throw new IllegalArgumentException("Location provided and layers "
          + "provided cannot be null.");
    }
    if (loLayers.isEmpty()) {
      throw new IllegalArgumentException("Layers provided cannot be empty.");
    }

    File directory = new File(location);
    if (!directory.exists()) {
      boolean b = directory.mkdirs();

      if (!b) {
        throw new IllegalArgumentException("Location provided cannot be used.");
      }
    }

    StringBuilder finalBuild;
    FileWriter fileWriter;
    PrintWriter printWriter;
    int counter = 0;

    for (InterfaceLayers l : loLayers) {
      finalBuild = new StringBuilder();
      String layerName = l.getLayerName();
      InterfaceImages image;
      try {
        image = l.getLayerImage();
      }
      catch (Exception e) {
        image = null;
      }
      boolean visiblityStatus = l.getVisibilityStatus();

      finalBuild.append(layerName).append("\n");
      if (visiblityStatus) {
        finalBuild.append("visible").append("\n");
      }
      else {
        finalBuild.append("hidden").append("\n");
      }

      finalBuild.append(counter).append("\n");
      counter++;

      int width;
      int height;

      try {
        width = image.getWidth();
        height = image.getHeight();
      }
      catch (Exception e) {
        width = 0;
        height = 0;
      }


      finalBuild.append(width).append(" ").append(height);

      for (int x = 0; x < height; x++) {
        for (int y = 0; y < width; y++) {
          IPixels pixel = image.getPixel(x, y);
          finalBuild.append("\n");

          finalBuild.append(pixel.getRed()).append(" ");
          finalBuild.append(pixel.getGreen()).append(" ");
          finalBuild.append(pixel.getBlue());
        }
      }

      String text = finalBuild.toString();

      try {
        fileWriter = new FileWriter(location + "/"
            + layerName + ".matrixcustom");
        printWriter = new PrintWriter(fileWriter);
        printWriter.print(text);
        printWriter.close();
      }
      catch (IOException e) {
        throw new IllegalArgumentException("layer " + layerName + " cannot be saved.");
      }
    }

    try {
      fileWriter = new FileWriter(location + "/" + "layersLocation.layerlocat");
      printWriter = new PrintWriter(fileWriter);
      printWriter.print(location);
      printWriter.close();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Main file cannot be written.");
    }
  }
}
