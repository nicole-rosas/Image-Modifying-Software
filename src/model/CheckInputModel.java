package model;

import java.awt.Color;
import java.util.List;

/**
 * Mock for the Models to be used to testing.
 */
public class CheckInputModel extends MultiLayerEnhanceModel {

  private final StringBuilder log;

  /**
   * Constructor for CheckInputModel that is to be used for testing takes in a StringBuilder.
   * @param log a StringBuuilder.
   */
  public CheckInputModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setName(String name) {
    log.append(String.format("setName(%s)\n", name));
  }

  @Override
  public void generateCheckerBoardImage(int width, int height, Double interval, List<Color> color) {
    log.append(String.format("generateCheckerBoardImage(%d, %d, %f, new ArrayList<>()",
        width, height, interval, color));
  }

  @Override
  public void addLayer(String name) {
    log.append(String.format("addLayer(%s)\n", name));
  }

  @Override
  public void removeLayer(String name) {
    log.append(String.format("removeLayer(%s)\n", name));
  }

  @Override
  public void changeCurrentLayer(String name) {
    log.append(String.format("changeCurrentLayer(%s)\n", name));
  }

  @Override
  public void changeVisibility(String name, boolean visible) {
    log.append(String.format("changeVisiblity(%s)\n", name));
  }

  @Override
  public void switchLayerPosition(String name, int place) {
    log.append(String.format("switchLayerPosition(%s, %d)\n", name, place));
  }
}
