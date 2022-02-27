package view;

import controller.guicontroller.ControllerSubscriberInterface;

/**
 * Interface will draw a GUI of the image processor.
 * It will listen for action provided from GUIView and publish the action to the subscriber.
 * The subscriber will then do a specific method depending on the given action published.
 */
public interface ViewPublisherInterface {
  // Update View
  public void addSubscriber(ControllerSubscriberInterface subscriber);

  public void displayGUI();

  public void writeMessage(String message);

  public void updateImage();

  public void updateLayers();
}
