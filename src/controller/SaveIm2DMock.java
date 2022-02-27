package controller;

import controller.SaveMultiInterface;
import java.util.List;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.layers.InterfaceLayers;

/**
 * The mock for saving layers.
 */

public class SaveIm2DMock implements SaveMultiInterface {

  private StringBuilder log;

  public SaveIm2DMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void saveMultiLayersImage(List<InterfaceLayers> loLayers, String location) {
    if (loLayers == null || location == null) {
      throw new IllegalArgumentException("Location provided and layers "
          + "provided cannot be null.");
    }
    if (loLayers.isEmpty()) {
      throw new IllegalArgumentException("Layers provided cannot be empty.");
    }

    int counter = 0;

    for (InterfaceLayers l : loLayers) {

      String layerName = l.getLayerName();
      InterfaceImages image;
      try {
        image = l.getLayerImage();
      }
      catch (Exception e) {
        image = null;
      }
      boolean visiblityStatus = l.getVisibilityStatus();

      log.append(layerName).append("\n");
      if (visiblityStatus) {
        log.append("visible").append("\n");
      }
      else {
        log.append("hidden").append("\n");
      }

      log.append(counter).append("\n");
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


      log.append(width).append(" ").append(height);

      for (int x = 0; x < height; x++) {
        for (int y = 0; y < width; y++) {
          IPixels pixel = image.getPixel(x, y);
          log.append("\n");

          log.append(pixel.getRed()).append(" ");
          log.append(pixel.getGreen()).append(" ");
          log.append(pixel.getBlue());
        }
      }
    }

    log.append(location).append("/").append("layersLocation");
  }
}
