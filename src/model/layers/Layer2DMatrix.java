package model.layers;

import java.util.Objects;
import model.images.image.InterfaceImages;

/**
 * Represents a layer in a MultiLayerModel that can take in an image, has a name, and
 * has a boolean to see if visible or not.
 */
public class Layer2DMatrix implements InterfaceLayers {
  private String name;
  private boolean isVisible;
  private InterfaceImages image;

  /**
   * Empty constructor for Layer2DMatrix. Returns a empty layer with no image.
   */
  public Layer2DMatrix() {
    name = "untitled layer";
    isVisible = true;
    image = null;
  }

  /**
   * Constructor that takes in a name, isVisible, and image and returns it as a layer in model.
   * @param name of the layer.
   * @param isVisible boolean to express visibility.
   * @param image what the layer shows/looks like.
   */
  public Layer2DMatrix(String name, boolean isVisible,
      InterfaceImages image) {
    this.name = name;
    this.isVisible = isVisible;
    this.image = image;
  }

  @Override
  public void setName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name of layer cannot be null.");
    }
    this.name = name;
  }

  @Override
  public void setVisibility(boolean v) {
    this.isVisible = v;
  }

  @Override
  public void setImageInLayer(InterfaceImages image) {
    if (image == null) {
      this.image = null;
    }
    else {
      this.image = image.getDeepCopy();
    }
  }

  @Override
  public String getLayerName() {
    return this.name;
  }

  @Override
  public boolean getVisibilityStatus() {
    return this.isVisible;
  }

  @Override
  public InterfaceImages getLayerImage() {
    if (this.isImageEmpty()) {
      return null;
    }
    return this.image.getDeepCopy();
  }

  @Override
  public InterfaceLayers getDeepCopy() {
    if (this.image == null) {
      return new Layer2DMatrix(this.name, this.isVisible, null);
    }

    return new Layer2DMatrix(this.name, this.isVisible,
        this.image.getDeepCopy());
  }

  @Override
  public boolean equals(Object c) {
    if (this == c) {
      return true;
    }

    if (!(c instanceof Layer2DMatrix)) {
      return false;
    }

    Layer2DMatrix that = (Layer2DMatrix) c;

    if (this.image == null && that.image != null) {
      return false;
    }
    if (that.image == null && this.image != null) {
      return false;
    }

    if (this.image == null && that.image == null) {
      return this.getLayerName().equals(that.getLayerName())
          && (this.getVisibilityStatus() && that.getVisibilityStatus());
    }

    return this.getLayerName().equals(that.getLayerName())
        && (this.getVisibilityStatus() && that.getVisibilityStatus())
        && this.image.equals(that.image);
  }

  @Override
  public int hashCode() {
    int ascii = 0;

    for (int x = 0; x < this.name.length(); x++) {
      char c = this.name.charAt(x);
      ascii += (int) c;
    }

    if (this.image == null) {
      if (this.isVisible) {
        return Objects.hash(ascii + 1);
      }
      else {
        return Objects.hash(ascii);
      }
    }
    else {
      if (this.isVisible) {
        return Objects.hash(ascii + 1 + this.image.hashCode());
      }
      else {
        return Objects.hash(ascii + this.image.hashCode());
      }
    }
  }

  @Override
  public boolean isImageEmpty() {
    return image == null;
  }
}
