package view;

import com.formdev.flatlaf.FlatDarculaLaf;
import controller.guicontroller.ControllerSubscriberInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.MultiLayerModelState;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.layers.InterfaceLayers;

/**
 * Represents the current view of the image processor that allows the user to interact with layers
 * and images.
 */
public class GUIView extends JFrame implements ViewPublisherInterface {

  // List of subscriber that will listen to all the events of the GUI View.
  // In this case, there are only one subscriber, because we are only are implementing
  // One controller.
  List<ControllerSubscriberInterface> loSubscriber;
  private final MultiLayerModelState model;

  private JPanel window;

  private JPanel operationPanel;
  private JPanel generatePanel;
  private JPanel scriptingUndoPanel;
  private JPanel importExportPanel;

  private JPanel middleTopPanel;
  // The controller, when writing a message to be displayed in the view, will have its
  // text/message be written here.
  private JPanel middleBotPanel;

  private JPanel layerPanel;
  private JPanel layerOpPanel;
  private JPanel saveLoadLayer;

  private JSpinner width;
  private JSpinner height;
  private JSpinner interval;
  private JComboBox<String> color1;
  private JComboBox<String> color2;

  private JScrollPane imagePane;
  private JLabel text;

  private JTextField name;

  /**
   * Constructor for GUIView that takes in a model.
   *
   * @param model a MultiLayerModelState.
   */
  public GUIView(MultiLayerModelState model) {
    super("Image Editor");

    FlatDarculaLaf.setup();

    this.loSubscriber = new ArrayList<>();
    this.model = model;

    this.setupFrame();
    this.setupBase();
    this.buttonsForLeft();
    this.middleImageSetUp();
    this.bottomMiddleSetup();
    this.setLayerPanel();
    this.setLayerOPButtons();
    this.setSaveLoadLayer();
    this.setupMenu();

    add(window);
  }

  private void setupFrame() {
    UIManager.getLookAndFeelDefaults()
        .put("defaultFont", new Font("Source Code Pro", Font.PLAIN, 13));

    setSize(1280, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
  }

  private void setupBase() {
    window = new JPanel();
    window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
    window.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

    // The left section of the panel of the editing software.
    // It will have a panel for Operations, Generating Images, Scripting,
    // Importing and exporting images, and undoing.
    JPanel leftWin = new JPanel();
    leftWin.setLayout(new BoxLayout(leftWin, BoxLayout.Y_AXIS));
    leftWin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    leftWin.setMaximumSize(new Dimension(200, getHeight()));
    leftWin.setMinimumSize(new Dimension(200, getHeight()));

    TitledBorder opBorder = BorderFactory
        .createTitledBorder(new LineBorder(Color.gray), "Operations");
    opBorder.setTitleJustification(TitledBorder.CENTER);
    operationPanel = new JPanel(new FlowLayout());
    operationPanel.setBorder(opBorder);
    operationPanel.setPreferredSize(new Dimension(200, 200));

    TitledBorder gBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Generate Image");
    gBorder.setTitleJustification(TitledBorder.CENTER);
    generatePanel = new JPanel();
    generatePanel.setBorder(gBorder);
    generatePanel.setLayout(new GridLayout(7, 1));
    generatePanel.setPreferredSize(new Dimension(200, 245));

    TitledBorder scBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Script / Undo");
    scBorder.setTitleJustification(TitledBorder.CENTER);
    scriptingUndoPanel = new JPanel(new GridLayout(1, 2, 2, 2));
    scriptingUndoPanel.setBorder(scBorder);
    scriptingUndoPanel.setPreferredSize(new Dimension(200, 40));

    TitledBorder imBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Import / Export");
    imBorder.setTitleJustification(TitledBorder.CENTER);
    importExportPanel = new JPanel(new GridLayout(1, 2, 2, 2));
    importExportPanel.setBorder(imBorder);
    importExportPanel.setPreferredSize(new Dimension(200, 40));

    // The middle section, will have a top panel for the image itself, and
    // A bottom section where controller can send message to the view, as well as
    // operation indicators.
    JPanel middleWin = new JPanel();
    middleWin.setPreferredSize(new Dimension(870, getHeight()));
    middleWin.setLayout(new BoxLayout(middleWin, BoxLayout.Y_AXIS));
    middleWin.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));

    middleTopPanel = new JPanel();
    LineBorder imageBorder = new LineBorder(Color.gray);
    middleTopPanel.setBorder(imageBorder);
    middleTopPanel.setPreferredSize(new Dimension(870, 640));
    middleTopPanel.setLayout(new BorderLayout());

    middleBotPanel = new JPanel();
    TitledBorder textBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Message");
    textBorder.setTitleJustification(TitledBorder.CENTER);
    middleBotPanel.setBorder(textBorder);
    middleBotPanel.setPreferredSize(new Dimension(870, 80));

    // The right section will have a panel that contains three panels.
    // A panel dedicated to layers. A panel dedicated to operations on that panel.
    // (Changing name, deleting, adding layers).
    // And a panel dedicated to saving and loading layers.
    JPanel rightWin = new JPanel();
    rightWin.setPreferredSize(new Dimension(210, getHeight()));
    rightWin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    rightWin.setLayout(new BoxLayout(rightWin, BoxLayout.Y_AXIS));

    layerPanel = new JPanel();
    layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.Y_AXIS));
    layerPanel.setMaximumSize(new Dimension(210, 400));

    JScrollPane layerPane = new JScrollPane(layerPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    TitledBorder layerBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Layers");
    layerBorder.setTitleJustification(TitledBorder.CENTER);
    layerPane.setBorder(layerBorder);
    layerPane.setPreferredSize(new Dimension(210, 400));
    layerPane.setLayout(new ScrollPaneLayout());
    layerPane.add(Box.createRigidArea(new Dimension(210, 0)));

    layerOpPanel = new JPanel();
    layerOpPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    layerOpPanel.setPreferredSize(new Dimension(210, 120));
    layerOpPanel.setLayout(new GridLayout(4, 1, 2, 2));

    saveLoadLayer = new JPanel();
    TitledBorder saveLoadBorder = BorderFactory.createTitledBorder(new LineBorder(Color.gray),
        "Save / Load Layers");
    saveLoadBorder.setTitleJustification(TitledBorder.CENTER);
    saveLoadLayer.setBorder(saveLoadBorder);
    saveLoadLayer.setPreferredSize(new Dimension(210, 36));
    saveLoadLayer.setLayout(new GridLayout(1, 2, 2, 2));

    leftWin.add(operationPanel);
    leftWin.add(generatePanel);
    leftWin.add(Box.createRigidArea(new Dimension(0, 20)));
    leftWin.add(scriptingUndoPanel);
    leftWin.add(importExportPanel);

    middleWin.add(middleTopPanel);
    middleWin.add(middleBotPanel);

    rightWin.add(layerPane);
    rightWin.add(layerOpPanel);
    rightWin.add(saveLoadLayer);

    window.add(leftWin);
    window.add(middleWin);
    window.add(rightWin);
  }

  private void setupMenu() {
    // drop down menu
    JMenuBar menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(getWidth(), 20));

    JMenu aMenu = new JMenu("File");
    aMenu.setMnemonic('F');
    menuBar.add(aMenu);
    JMenuItem item = new JMenuItem("Load in Layer(s)");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("load layer");
      if (f != null) {
        sub.onLoadLayer(f);
      } else {
        this.writeMessage("Did not manage to load the layers at desired location.");
      }
    }));
    aMenu.add(item);
    item = new JMenuItem("Save Layer(s)");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("save layer");
      if (f != null) {
        sub.onSaveLayer(f);
      } else {
        this.writeMessage("Did not manage to save the layers at desired location.");
      }
    }));
    aMenu.add(item);
    item = new JMenuItem("Import an image");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("import");
      if (f != null) {
        sub.onImport(f);
      } else {
        this.writeMessage("Did not import the image.");
      }
    }));
    aMenu.add(item);
    item = new JMenuItem("Export image as");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("export");
      if (f != null) {
        sub.onExport(f);
      } else {
        this.writeMessage("Cannot export the image as an image.");
      }
    }));
    aMenu.add(item);
    item = new JMenuItem("Import script");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("script");
      if (f != null) {
        sub.onRunScript(f);
      } else {
        this.writeMessage("Did not import and run script file.");
      }
    }));
    aMenu.add(item);

    aMenu = new JMenu("Processes");
    aMenu.setMnemonic('P');
    menuBar.add(aMenu);

    item = new JMenuItem("Make image sepia");
    item.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onSepiaListener));
    aMenu.add(item);

    item = new JMenuItem("Make image greyscale");
    item.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onGreyscaleListener));
    aMenu.add(item);

    item = new JMenuItem("Sharpen Image");
    item.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onSharpenListener));
    aMenu.add(item);

    item = new JMenuItem("Blur image");
    item.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onBlurListener));
    aMenu.add(item);

    item = new JMenuItem("Undo move");
    item.addActionListener(evt -> loSubscriber.forEach(ControllerSubscriberInterface::onUndo));
    aMenu.add(item);

    aMenu = new JMenu("Layers");
    aMenu.setMnemonic('L');
    menuBar.add(aMenu);

    item = new JMenuItem("Rename");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      sub.onChangeName(name.getText());
    }));
    aMenu.add(item);

    item = new JMenuItem("Add layer");
    item.addActionListener(evt -> loSubscriber.forEach(
        ControllerSubscriberInterface::onAddLayer));
    aMenu.add(item);

    item = new JMenuItem("Delete layer");
    item.addActionListener(evt -> loSubscriber.forEach(
        ControllerSubscriberInterface::onDeleteLayer));
    aMenu.add(item);

    item = new JMenuItem("Make layer invisible");
    item.addActionListener(evt -> loSubscriber.forEach(s -> {
      s.onHide(this.model.getName());
      s.onChangeCurLayer(this.model.getName());
    }));
    aMenu.add(item);

    item = new JMenuItem("Make layer visible");
    item.addActionListener(evt -> loSubscriber.forEach(s -> {
      s.onVisible(this.model.getName());
      s.onChangeCurLayer(this.model.getName());
    }));
    aMenu.add(item);

    item = new JMenuItem("Move layer up");
    item.addActionListener(evt -> loSubscriber.forEach(s -> {
      s.onChangeLayerUp(this.model.getName());
      s.onChangeCurLayer(this.model.getName());
    }));
    aMenu.add(item);

    item = new JMenuItem("Move layer down");
    item.addActionListener(evt -> loSubscriber.forEach(s -> {
      s.onChangeLayerDown(this.model.getName());
      s.onChangeCurLayer(this.model.getName());
    }));
    aMenu.add(item);

    aMenu = new JMenu("Generate");
    aMenu.setMnemonic('G');
    menuBar.add(aMenu);
    item = new JMenuItem("Create checkerboard(Default)");
    item.addActionListener(evt -> loSubscriber.forEach(sub -> {
      sub.generateCheckerBoardImage(500, 500,
          0, "Black", "White");
    }));
    aMenu.add(item);

    setJMenuBar(menuBar);
  }

  private void buttonsForLeft() {
    // Operations
    Dimension standard = new Dimension(86, 30);
    Font standardFont = new Font("Source Code Pro", Font.PLAIN, 11);

    // buttons that will edit the image
    JButton sepia = new JButton("Sepia");
    sepia.setFont(standardFont);
    sepia.setPreferredSize(standard);
    sepia.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onSepiaListener));
    operationPanel.add(sepia);

    JButton greyscale = new JButton("Greyscale");
    greyscale.setFont(new Font("Source Code Pro", Font.PLAIN, 9));
    greyscale.setPreferredSize(standard);
    greyscale.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onGreyscaleListener));
    operationPanel.add(greyscale);

    JButton blur = new JButton("Blur");
    blur.setFont(standardFont);
    blur.setPreferredSize(standard);
    blur.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onBlurListener));
    operationPanel.add(blur);

    JButton sharpen = new JButton("Sharpen");
    sharpen.setFont(standardFont);
    sharpen.setPreferredSize(standard);
    sharpen.addActionListener(evt ->
        loSubscriber.forEach(ControllerSubscriberInterface::onSharpenListener));
    operationPanel.add(sharpen);

    // Image Generation

    String[] generatePresetString = {
        "checkerboard"
    };
    // generate elements
    JComboBox<String> presets = new JComboBox<>(generatePresetString);
    presets.setSelectedIndex(0);
    presets.setPreferredSize(new Dimension(130, 30));

    SpinnerModel widthValue = new SpinnerNumberModel(1, 1, 99999, 1);
    SpinnerModel heightValue = new SpinnerNumberModel(1, 1, 99999, 1);
    width = new JSpinner(widthValue);

    height = new JSpinner(heightValue);

    SpinnerModel intervalVal = new SpinnerNumberModel(0.0, 0.0, 40, .1);
    interval = new JSpinner(intervalVal);

    String[] possibleColors = {
        "Black", "White", "Blue", "Red", "Green", "Purple", "Grey", "Yellow", "Orange", "Pink"
    };

    color1 = new JComboBox<>(possibleColors);
    color1.setSelectedIndex(0);
    color2 = new JComboBox<>(possibleColors);
    color2.setSelectedIndex(1);

    JPanel widthPanel = new JPanel();
    widthPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    widthPanel.setLayout(new FlowLayout());
    widthPanel.add(new JLabel("Width:"));
    widthPanel.add(width);

    JPanel heightPanel = new JPanel();
    heightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    heightPanel.setLayout(new FlowLayout());
    heightPanel.add(new JLabel("Height:"));
    heightPanel.add(height);

    JPanel intervalPanel = new JPanel();
    intervalPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    intervalPanel.setLayout(new FlowLayout());
    intervalPanel.add(new JLabel("Interval:"));
    intervalPanel.add(interval);

    JPanel color1Panel = new JPanel();
    color1Panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    color1Panel.setLayout(new FlowLayout());
    color1Panel.add(new JLabel("Color 1:"));
    color1Panel.add(color1);

    JPanel color2Panel = new JPanel();
    color2Panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    color2Panel.setLayout(new FlowLayout());
    color2Panel.add(new JLabel("Color 2:"));
    color2Panel.add(color2);

    JButton generateImage = new JButton("Generate");
    generateImage.setFont(standardFont);
    generateImage.addActionListener(evt -> loSubscriber.forEach(sub -> {
      sub.generateCheckerBoardImage((int) width.getValue(), (int) height.getValue(),
          (double) interval.getValue(), (String) color1.getSelectedItem(),
          (String) color2.getSelectedItem());
    }));

    generatePanel.add(presets);
    generatePanel.add(widthPanel);
    generatePanel.add(heightPanel);
    generatePanel.add(intervalPanel);
    generatePanel.add(color1Panel);
    generatePanel.add(color2Panel);
    generatePanel.add(generateImage);

    // Load Script and Undo
    // script + undo buttons
    JButton script = new JButton("Run Script");
    script.setFont(new Font("Source Code Pro", Font.PLAIN, 9));
    JButton undo = new JButton("Undo");
    undo.setFont(standardFont);
    script.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("script");
      if (f != null) {
        sub.onRunScript(f);
      } else {
        this.writeMessage("Did not import and run script file.");
      }
    }));
    undo.addActionListener(evt -> loSubscriber.forEach(ControllerSubscriberInterface::onUndo));

    scriptingUndoPanel.add(script);
    scriptingUndoPanel.add(undo);

    // Importing and Exporting Buttons
    JButton importIm = new JButton("Import");
    JButton export = new JButton("Export");
    importIm.setFont(standardFont);
    export.setFont(standardFont);

    importIm.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("import");
      if (f != null) {
        sub.onImport(f);
      } else {
        this.writeMessage("Did not import the image.");
      }
    }));

    export.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("export");
      if (f != null) {
        sub.onExport(f);
      } else {
        this.writeMessage("Cannot export the image as an image.");
      }
    }));

    importExportPanel.add(importIm);
    importExportPanel.add(export);
  }

  private void middleImageSetUp() {
    ImageIcon icon;
    InterfaceImages matrixImage;

    try {
      matrixImage = model.getTopVisLayerImage();
      icon = new ImageIcon(getBufferedImage(matrixImage));

      JLabel label = new JLabel(icon);

      imagePane = new JScrollPane(label,
          ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      middleTopPanel.add(imagePane, BorderLayout.CENTER);
    } catch (Exception e) {
      // Does not do anything, as there is no images to draw.
    }
  }

  private void bottomMiddleSetup() {
    String msg = "Nothing to see here.";
    text = new JLabel(msg);
    text.setMaximumSize(new Dimension(860, 80));
    text.setFont(new Font("Source Code Pro", Font.BOLD, 16));
    middleBotPanel.add(text);
  }

  private void setLayerPanel() {
    List<InterfaceLayers> loLayers = this.model.getAllLayers();
    int size = loLayers.size();
    for (int x = size - 1; x >= 0; x--) {
      InterfaceLayers layer = loLayers.get(x);
      boolean isCurrent = layer.equals(this.model.getCurrentLayer());
      JPanel custom = new IndividualLayerPanel(x, size, loLayers.get(x),
          isCurrent, this.loSubscriber, this.model);

      layerPanel.add(custom);
      layerPanel.add(Box.createRigidArea(new Dimension(0, 2)));
    }
  }

  private void setLayerOPButtons() {
    name = new JTextField();
    name.setFont(new Font("Source Code Pro", Font.PLAIN, 12));

    JButton setName = new JButton("Change Name");
    setName.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
    setName.addActionListener(evt -> loSubscriber.forEach(sub -> {
      sub.onChangeName(name.getText());
    }));

    JButton addLayer = new JButton("Add Layer");
    addLayer.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
    addLayer.addActionListener(evt -> loSubscriber.forEach(
        ControllerSubscriberInterface::onAddLayer));

    JButton deleteLayer = new JButton("Delete Layer");
    deleteLayer.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
    deleteLayer.addActionListener(evt -> loSubscriber.forEach(
        ControllerSubscriberInterface::onDeleteLayer));

    layerOpPanel.add(name);
    layerOpPanel.add(setName);
    layerOpPanel.add(addLayer);
    layerOpPanel.add(deleteLayer);
  }

  private void setSaveLoadLayer() {
    Font standard = new Font("Source Code Pro", Font.PLAIN, 9);

    JButton saveLayers = new JButton("Save Layers");
    saveLayers.setFont(standard);
    saveLayers.setMaximumSize(new Dimension(100, 34));
    saveLayers.setMinimumSize(new Dimension(100, 34));
    saveLayers.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("save layer");
      if (f != null) {
        sub.onSaveLayer(f);
      } else {
        this.writeMessage("Did not manage to save the layers at desired location.");
      }
    }));

    JButton loadLayers = new JButton("Load Layers");
    loadLayers.setFont(standard);
    loadLayers.addActionListener(evt -> loSubscriber.forEach(sub -> {
      File f = this.actionOnFileChooser("load layer");
      if (f != null) {
        sub.onLoadLayer(f);
      } else {
        this.writeMessage("Did not manage to load the layers at desired location.");
      }
    }));

    saveLoadLayer.add(loadLayers);
    saveLoadLayer.add(saveLayers);
  }

  private BufferedImage getBufferedImage(InterfaceImages images) {
    if (images == null) {
      throw new IllegalArgumentException("Images provided cannot be null.");
    }

    int width = images.getWidth();
    int height = images.getHeight();

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        IPixels pixel = images.getPixel(x, y);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        int col = (r << 16) | (g << 8) | b;
        img.setRGB(y, x, col);
      }
    }

    return img;
  }

  private File actionOnFileChooser(String type) {
    JFileChooser chooser = new JFileChooser(".");
    FileNameExtensionFilter filter;
    int retValue;

    switch (type) {
      case "import":
        filter = new FileNameExtensionFilter("Image formats",
            "ppm", "png", "jpeg");
        chooser.setFileFilter(filter);

        retValue = chooser.showOpenDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          return chooser.getSelectedFile();
        } else {
          return null;
        }
      case "export":
        retValue = chooser.showSaveDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          return chooser.getSelectedFile();
        } else {
          return null;
        }
      case "script":
        filter = new FileNameExtensionFilter("Script Text",
            "txt");
        chooser.setFileFilter(filter);

        retValue = chooser.showOpenDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          return chooser.getSelectedFile();
        } else {
          return null;
        }
      case "load layer":
        filter = new FileNameExtensionFilter("Layers",
            "layerlocat");
        chooser.setFileFilter(filter);

        retValue = chooser.showOpenDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          return chooser.getSelectedFile();
        } else {
          return null;
        }
      case "save layer":
        chooser.setFileFilter(new FolderFilter());

        retValue = chooser.showOpenDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          return chooser.getSelectedFile();
        } else {
          return null;
        }
      default:
        return null;
    }
  }

  @Override
  public void addSubscriber(ControllerSubscriberInterface subscriber) {
    this.loSubscriber.add(subscriber);
  }

  @Override
  public void displayGUI() {
    setVisible(true);
  }

  @Override
  public void writeMessage(String message) {
    this.middleBotPanel.invalidate();
    this.middleBotPanel.removeAll();
    text.setText(message);
    this.middleBotPanel.add(text);
    this.validate();
    this.repaint();
  }

  @Override
  public void updateImage() {
    middleTopPanel.invalidate();
    ImageIcon icon;
    InterfaceImages matrixImage;

    try {
      matrixImage = model.getTopVisLayerImage();
      icon = new ImageIcon(getBufferedImage(matrixImage));

      JLabel label = new JLabel(icon);
      imagePane = new JScrollPane(label);
      middleTopPanel.removeAll();
      middleTopPanel.add(imagePane, BorderLayout.CENTER);
    } catch (Exception e) {
      middleTopPanel.removeAll();
    }

    middleTopPanel.validate();
    this.repaint();
  }

  @Override
  public void updateLayers() {
    this.layerPanel.invalidate();
    this.layerPanel.removeAll();

    this.setLayerPanel();

    this.layerPanel.validate();
    this.repaint();
  }

  private static class FolderFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
      return file.isDirectory();
    }

    @Override
    public String getDescription() {
      return "We only allow you to save layers into directories.";
    }
  }

  /*
  This is the panel that the GUI use. This is set as static and inside the class of the GUI,
  because we want it to be hidden from the user. This class does not have any methods, and
  therefore suitable to be a static class.
   */
  static class IndividualLayerPanel extends JPanel {
    // Each layer have these functionalities:
    // Checkbox representing whether it is hidden or visible and the layer's name
    // current button to make a layer current (if the layer is current,
    // does not display this button)
    // button up representing to move the layer up (if its top layer does not display this button)
    // button down representing to move the layer down (if its bottom layer does not display)
    // delete button

    InterfaceLayers layer;

    JCheckBox visibilityAndName;
    JButton current;

    JPanel wrapper1;
    JPanel wrapper2;
    JPanel wrapper3;

    JButton moveUp;
    JButton moveDown;
    JButton delete;

    IndividualLayerPanel(int pos, int size, InterfaceLayers layer, boolean isCurrent,
        List<ControllerSubscriberInterface> sub, MultiLayerModelState model) {

      if (layer == null || sub == null || model == null) {
        throw new IllegalArgumentException("Cannot create layer.");
      }

      setMaximumSize(new Dimension(200, 84));
      setBorder(BorderFactory.createLineBorder(Color.gray));
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      this.layer = layer;

      wrapper1 = new JPanel();
      wrapper1.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
      visibilityAndName = new JCheckBox(this.layer.getLayerName(),
          this.layer.getVisibilityStatus());
      visibilityAndName.setFont(new Font("Source Code Pro", Font.PLAIN, 10));
      visibilityAndName.addActionListener(evt -> sub.forEach(s -> {
        if (visibilityAndName.isSelected()) {
          s.onVisible(this.layer.getLayerName());
        } else {
          s.onHide(this.layer.getLayerName());
        }
      }));

      current = new JButton("current");
      current.setFont(new Font("Source Code Pro", Font.PLAIN, 9));
      current.addActionListener(evt -> sub.forEach(s -> {
        s.onChangeCurLayer(this.layer.getLayerName());
      }));

      wrapper1.add(visibilityAndName);

      if (!isCurrent) {
        wrapper1.add(current);
      }

      delete = new JButton("Remove");
      delete.setFont(new Font("Source Code Pro", Font.PLAIN, 8));
      delete.addActionListener(evt -> sub.forEach(s -> {
        String name = this.layer.getLayerName();
        s.onChangeCurLayer(name);
        s.onRemoveImage();
        s.onChangeCurLayer(model.getName());
      }));

      moveUp = new JButton("Up");
      moveUp.setFont(new Font("Source Code Pro", Font.PLAIN, 8));
      moveUp.addActionListener(evt -> sub.forEach(s -> {
        s.onChangeCurLayer(this.layer.getLayerName());
        s.onChangeLayerUp(this.layer.getLayerName());
        s.onChangeCurLayer(model.getName());
      }));

      moveDown = new JButton("Down");
      moveDown.setFont(new Font("Source Code Pro", Font.PLAIN, 8));
      moveDown.addActionListener(evt -> sub.forEach(s -> {
        s.onChangeCurLayer(this.layer.getLayerName());
        s.onChangeLayerDown(this.layer.getLayerName());
        s.onChangeCurLayer(model.getName());
      }));

      wrapper2 = new JPanel();
      wrapper2.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
      wrapper2.setMaximumSize(new Dimension(200, 30));
      wrapper2.setLayout(new BoxLayout(wrapper2, BoxLayout.X_AXIS));
      wrapper2.add(delete);

      wrapper3 = new JPanel();
      wrapper3.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
      wrapper3.setLayout(new GridLayout(2, 1, 1, 0));

      if (pos != 0 && pos != size - 1) {
        wrapper3.add(moveDown);
        wrapper3.add(moveUp);
      } else if (pos == 0) {
        wrapper3.add(moveUp);
      } else {
        wrapper3.add(moveDown);
      }

      wrapper2.add(wrapper3);

      add(wrapper1);
      add(wrapper2);
    }
  }
}