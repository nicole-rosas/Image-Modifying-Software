package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import model.images.image.InterfaceImages;
import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;

/**
 * Represents a model that takes in multiple layers of images to be edited and exported.
 */
public class MultiLayerEnhanceModel implements MultiLayeredEnhanceInterface {

  // The last layer in the list is physically the top most layer.
  private List<InterfaceLayers> layers;
  // The basic model to be used at delegate.
  private final ImageEnhanceModelInterface delegate;
  // The object representing the current Layer.
  private InterfaceLayers currentLayer;
  // The stack that keeps track of operations, something like a move history, and it saves the
  // previous model state.
  private Stack<List<InterfaceLayers>> programHistory;
  private int firstWidth;
  private int firstHeight;

  /**
   * constructor that takes in no fields.
   */
  public MultiLayerEnhanceModel() {
    this(new ArrayList<>(), new ImageEnhanceBasicModel());
  }

  /**
   * Constructor that takes in a list of layers and a {@code ImageEnhanceBasicModel}.
   */
  public MultiLayerEnhanceModel(List<InterfaceLayers> layers, ImageEnhanceBasicModel model) {
    if (layers == null || model == null) {
      throw new IllegalArgumentException("list of layers and model cannot be null.");
    }

    this.layers = layers;
    this.delegate = model;
    if (!layers.isEmpty()) {
      this.currentLayer = layers.get(layers.size() - 1);
    } else {
      this.currentLayer = new Layer2DMatrix();
    }
    this.programHistory = new FixedSizeStack<>(50);

    this.firstHeight = 0;
    this.firstWidth = 0;
  }

  /**
   * set name will be setting the name of the current layer.
   *
   * @param name new file name
   */
  @Override
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null.");
    }
    if (layers.isEmpty() || currentLayer == null) {
      throw new IllegalArgumentException("Cannot set the name of a null layer.");
    }

    this.save();

    currentLayer.setName(name);
  }

  @Override
  public String getName() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot get the name of the null layer.");
    }

    return currentLayer.getLayerName();
  }

  @Override
  public void setImage(InterfaceImages image) {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot set image onto no layers.");
    }
    if (image == null) {
      throw new IllegalArgumentException("Cannot set a null image into a layer.");
    }

    this.save();

    if (this.layers.size() == 1 || this.firstWidth == 0 || this.firstHeight == 0) {
      this.firstWidth = image.getWidth();
      this.firstHeight = image.getHeight();
      currentLayer.setImageInLayer(image);
    } else {
      currentLayer.setImageInLayer(image.crop(this.firstWidth, this.firstHeight));
    }
  }

  @Override
  public InterfaceImages getImage() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot get a null image from this layer.");
    }

    return currentLayer.getLayerImage();
  }

  /**
   * All processes transform the current image layer. Will pass in the currentLayer as a delegate
   * and have the delegate do the transform process to make the image sepia. Delegate will return
   * the new image and apply it to the currentLayer and corespondent layer in list.
   */
  @Override
  public void transformSepia() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot transform a null image.");
    }

    this.save();

    InterfaceImages currentImage = currentLayer.getLayerImage();
    delegate.setImage(currentImage);
    delegate.transformSepia();
    InterfaceImages newImage = delegate.getImage();

    currentLayer.setImageInLayer(newImage);
  }

  /**
   * All processes transform the current image layer. Will pass in the currentLayer as a delegate
   * and have the delegate do the transform process to blur the image. Delegate will return the new
   * image and apply it to the currentLayer and corespondent layer in list.
   */
  @Override
  public void convolutionBlur() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot convolve a null image.");
    }

    this.save();

    InterfaceImages currentImage = currentLayer.getLayerImage();
    delegate.setImage(currentImage);
    delegate.convolutionBlur();
    InterfaceImages newImage = delegate.getImage();

    currentLayer.setImageInLayer(newImage);
  }

  /**
   * All processes transform the current image layer. Will pass in the currentLayer as a delegate
   * and have the delegate do the transform process to greyscale the image. Delegate will return the
   * new image and apply it to the currentLayer and corespondent layer in list.
   */
  @Override
  public void transformGreyscale() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot transform a null image.");
    }

    this.save();

    InterfaceImages currentImage = currentLayer.getLayerImage();
    delegate.setImage(currentImage);
    delegate.transformGreyscale();
    InterfaceImages newImage = delegate.getImage();

    currentLayer.setImageInLayer(newImage);
  }

  /**
   * All processes transform the current image layer. Will pass in the currentLayer as a delegate
   * and have the delegate do the transform process to sharpen the image. Delegate will return the
   * new image and apply it to the currentLayer and corespondent layer in list.
   */
  @Override
  public void convolutionSharpen() {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot convolve a null image.");
    }

    this.save();

    InterfaceImages currentImage = currentLayer.getLayerImage();
    delegate.setImage(currentImage);
    delegate.convolutionSharpen();
    InterfaceImages newImage = delegate.getImage();

    currentLayer.setImageInLayer(newImage);
  }

  @Override
  public void generateCheckerBoardImage(int width, int height, Double interval, List<Color> color) {
    if (currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Current Layer is null, cannot generate image.");
    }

    this.save();

    delegate.generateCheckerBoardImage(width, height, interval, color);
    InterfaceImages image = delegate.getImage();

    if (this.firstWidth == 0 || this.firstHeight == 0) {
      this.firstWidth = image.getWidth();
      this.firstHeight = image.getHeight();

      currentLayer.setImageInLayer(image);
    }
    else {
      currentLayer.setImageInLayer(image.crop(firstWidth, firstHeight));
    }
  }

  @Override
  public void setIntoLayers(List<InterfaceLayers> loLayers) {
    if (loLayers == null || loLayers.isEmpty()) {
      throw new IllegalArgumentException("List of Layers provided cannot be null or empty.");
    }
    this.layers = new ArrayList<>();

    this.save();

    for (InterfaceLayers l : loLayers) {
      this.layers.add(l.getDeepCopy());
    }

    this.currentLayer = this.layers.get(this.layers.size() - 1);
  }

  @Override
  public void addLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }

    this.save();

    if (layers.isEmpty()) {
      InterfaceLayers layer = new Layer2DMatrix();
      layer.setName(name);
      currentLayer = layer;
      layers.add(layer);
    } else {
      InterfaceLayers layer = new Layer2DMatrix();

      List<String> listOfNames = new ArrayList<>();
      for (InterfaceLayers l : layers) {
        if (!listOfNames.contains(l.getLayerName())) {
          listOfNames.add(l.getLayerName());
        }
      }

      int counter = 1;
      String temp = name;
      while (listOfNames.contains(name)) {
        name = "" + temp + " " + String.valueOf(counter);
        counter++;
      }

      layer.setName(name);
      layers.add(layer);
    }
  }

  @Override
  public void removeLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name provided cannot be null.");
    }

    this.save();

    if (currentLayer.getLayerName().equals(name)) {
      layers.remove(this.getLayer(name));

      if (layers.isEmpty()) {
        currentLayer = null;
      } else {
        currentLayer = layers.get(layers.size() - 1);
      }
    } else {
      layers.remove(this.getLayer(name));
    }

    if (layers.isEmpty()) {
      this.firstHeight = 0;
      this.firstWidth = 0;
    }
  }

  @Override
  public void changeCurrentLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name provided cannot be null.");
    }

    currentLayer = this.getLayer(name);
  }

  @Override
  public void changeVisibility(String name, boolean visible) {
    if (name == null) {
      throw new IllegalArgumentException("Name provided cannot be null.");
    }
    if (this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot change visibility of empty layers.");
    }

    this.save();

    this.getLayer(name).setVisibility(visible);
  }

  @Override
  public InterfaceLayers getTopMostVisibleLayer() {
    if (this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot get top most visible layer of no layers.");
    }

    for (int i = layers.size() - 1; i >= 0; i--) {
      if (layers.get(i).getVisibilityStatus() && !layers.get(i).isImageEmpty()) {
        return layers.get(i).getDeepCopy();
      }
    }

    throw new IllegalArgumentException("There is no visible layer in the program.");
  }

  @Override
  public InterfaceImages getTopVisLayerImage() {
    return this.getTopMostVisibleLayer().getLayerImage();
  }

  @Override
  public InterfaceLayers getCurrentLayer() {
    if (currentLayer == null) {
      throw new IllegalArgumentException("Cannot get name of null current layer.");
    }
    return currentLayer.getDeepCopy();
  }

  @Override
  public int getNumLayers() {
    return this.layers.size();
  }

  @Override
  public int getCurrentLayerPos() {
    return this.layers.indexOf(currentLayer);
  }

  @Override
  public void switchLayerPosition(String name, int place) {
    if (name == null) {
      throw new IllegalArgumentException("Name provided cannot be null.");
    }
    if (place < 0 || place > this.layers.size()) {
      throw new IllegalArgumentException("Index provided is out of bounds.");
    }

    if (this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot switch layer position of no layers.");
    }

    this.save();

    this.swap(name, place);
  }

  @Override
  public List<InterfaceLayers> getAllLayers() {
    if (this.layers.isEmpty()) {
      return new ArrayList<>();
    }
    List<InterfaceLayers> copyLayers = new ArrayList<>();

    for (InterfaceLayers l : layers) {
      copyLayers.add(l.getDeepCopy());
    }

    return copyLayers;
  }

  @Override
  public void undo() {
    List<InterfaceLayers> undoLayers = new ArrayList<>();

    try {
      undoLayers = programHistory.pop();
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot undo when there is no action saved.");
    }

    if (layers.isEmpty()) {
      this.layers = new ArrayList<>();
    } else {
      int index = this.layers.indexOf(currentLayer);

      this.layers = new ArrayList<>();

      for (InterfaceLayers l : undoLayers) {
        this.layers.add(l.getDeepCopy());
      }

      if (this.layers.isEmpty()) {
        this.currentLayer = null;
      } else {
        this.currentLayer = this.layers.get(index);
      }
    }
  }

  @Override
  public void removeImage() {
    if (this.currentLayer == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Cannot perform remove Image on empty layer.");
    }

    this.save();

    this.currentLayer.setImageInLayer(null);

    if (this.getNumLayers() == 1) {
      this.firstWidth = 0;
      this.firstHeight = 0;
    }
  }

  /**
   * moves the layer of the given name to the place of the given int.
   *
   * @param name  of the layer.
   * @param place where the layer will be moved.
   */
  private void swap(String name, int place) {
    InterfaceLayers layer = this.getLayer(name);
    int index = this.layers.indexOf(layer);

    Collections.swap(layers, place, index);
  }

  /**
   * gets the layer from the given name.
   *
   * @param name of the layer to be returned.
   * @return an InterfaceLayers
   */
  private InterfaceLayers getLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name given cannot be null.");
    }

    for (InterfaceLayers l : layers) {
      if (l.getLayerName().equals(name)) {
        return l;
      }
    }
    throw new IllegalArgumentException("layer with given name does not exist.");
  }

  /**
   * saves the current MultiLayerEnhanceModel and deep copies all of its layers.
   */
  private void save() {
    List<InterfaceLayers> layersCopy = new ArrayList<>();

    for (InterfaceLayers l : this.layers) {
      layersCopy.add(l.getDeepCopy());
    }

    this.programHistory.add(layersCopy);
  }
}
