package controller;

import controller.LoadMultiInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;

/**
 * Mock for Loadlm2dMatrix to be used for testing.
 */
public class LoadLayerMock implements LoadMultiInterface {

  String[] files;

  /**
   * constructor for LoadLayerMock that takes in an array of String.
   * @param files String that represent file layers.
   */
  public LoadLayerMock(String... files) {
    if (files == null) {
      throw new IllegalArgumentException("Files provided cannot be null.");
    }
    this.files = files;
  }

  @Override
  public List<InterfaceLayers> importLayer(String dontCare) {

    List<InterfaceLayers> layers = new ArrayList<>();
    List<List<IPixels>> imagePixels;

    for (int i = 0; i < files.length; i++) {
      String current = files[i];

      Scanner sc = new Scanner(current);

      imagePixels = new ArrayList<>();

      String name = sc.nextLine();
      String vis = sc.nextLine();
      int pos = Integer.parseInt(sc.next());
      int width = Integer.parseInt(sc.next());
      int height = Integer.parseInt(sc.next());

      for (int x = 0; x < height; x++) {
        imagePixels.add(new ArrayList<>());
        for (int y = 0; y < width; y++) {
          int r = Integer.parseInt(sc.next());
          int g = Integer.parseInt(sc.next());
          int b = Integer.parseInt(sc.next());
          IPixels pixel = new PixelsRGB(r, g, b);
          imagePixels.get(x).add(pixel);
        }
      }
      InterfaceImages imageFinal = ImageCreator.create(ImageType.MATRIX2D);
      if (imagePixels.size() == 0) {
        imageFinal = null;
      }
      else {
        imageFinal.initiateImage(imagePixels);
      }

      InterfaceLayers layer = new Layer2DMatrix();
      layer.setImageInLayer(imageFinal);
      layer.setName(name);
      layer.setVisibility(vis.equalsIgnoreCase("visible"));

      layers.add(pos, layer);
    }

    return layers;
  }
}
