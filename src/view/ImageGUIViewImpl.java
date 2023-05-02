package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.WindowConstants;
import javax.swing.ListSelectionModel;
import javax.swing.ListModel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.image.IImage;
import utils.CommonImageUtil;

/**
 * The ImageGUI View for the image processor.
 */
public class ImageGUIViewImpl extends JFrame implements ImageGUIView, ActionListener,
        ListSelectionListener {
  protected JPanel mainPanel;
  protected JScrollPane mainScrollPane;
  protected JPanel imagePanel;
  protected JLabel imageLabel;
  protected JScrollPane imageScrollPane;
  protected HistogramPanel histogramPanel;
  protected JScrollPane histogramScrollPane;
  protected JPanel buttonContainer;
  protected JScrollPane buttonScrollPane;
  protected JPanel imageNameListPanel;
  protected JScrollPane imageNameScrollPane;
  protected JList<String> imageNameList;
  protected final JButton loadButton;
  protected final JButton saveButton;
  protected final JButton horizFlipButton;
  protected final JButton vertFlipButton;
  protected final JButton redComponentButton;
  protected final JButton greenComponentButton;
  protected final JButton blueComponentButton;
  protected final JButton valueComponentButton;
  protected final JButton intensityComponentButton;
  protected final JButton lumaComponentButton;
  protected final JButton changeBrightnessButton;
  protected final JButton blurButton;
  protected final JButton sharpenButton;
  protected final JButton sepiaButton;
  protected final JButton greyscaleButton;
  protected final List<ViewListener> listenerList;
  protected final Map<String, Consumer<ViewListener>> commands;
  protected String currentImageName;

  /**
   * Default constructor for the image gui view.
   */
  public ImageGUIViewImpl() {
    super();
    this.commands = new HashMap<>();
    this.listenerList = new ArrayList<>();

    this.setSize(1000, 700);
    this.setTitle("Image Processor");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BorderLayout());

    this.mainScrollPane = new JScrollPane(this.mainPanel);
    this.add(this.mainScrollPane);

    this.imagePanel = new JPanel();
    this.imagePanel.setLayout(new BorderLayout());
    this.imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    this.imagePanel.setPreferredSize(new Dimension(500, 500));

    this.mainPanel.add(this.imagePanel, BorderLayout.CENTER);

    this.imageLabel = new JLabel();
    this.imageScrollPane = new JScrollPane(this.imageLabel);
    this.imagePanel.add(this.imageScrollPane, BorderLayout.CENTER);

    this.histogramPanel = new HistogramPanel(255, 255);

    this.histogramScrollPane = new JScrollPane(this.histogramPanel);
    this.histogramScrollPane.setBorder(BorderFactory.createTitledBorder("Histogram"));
    this.histogramScrollPane.setPreferredSize(new Dimension(300, 300));

    this.mainPanel.add(this.histogramScrollPane, BorderLayout.EAST);

    this.imageNameListPanel = new JPanel();
    this.imageNameListPanel.setBorder(BorderFactory.createTitledBorder("Images"));

    this.imageNameList = new JList<>();
    this.imageNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.imageNameList.setVisibleRowCount(5);
    this.imageNameList.addListSelectionListener(this);

    this.imageNameScrollPane = new JScrollPane(this.imageNameList);
    this.imageNameListPanel.add(this.imageNameScrollPane);
    this.imageNameListPanel.setPreferredSize(new Dimension(700, 130));
    this.mainPanel.add(this.imageNameListPanel, BorderLayout.SOUTH);

    this.buttonContainer = new JPanel();
    this.buttonContainer.setBorder(BorderFactory.createTitledBorder("Operations"));
    this.buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.PAGE_AXIS));

    this.buttonScrollPane = new JScrollPane(this.buttonContainer);
    this.buttonScrollPane.setPreferredSize(new Dimension(250, 300));
    this.mainPanel.add(this.buttonScrollPane, BorderLayout.WEST);

    this.loadButton = new JButton("Load Image");
    this.loadButton.addActionListener(this);
    this.loadButton.setActionCommand("load");
    this.commands.put("load", (ViewListener listener) -> listener.loadImageEvent());

    this.saveButton = new JButton("Save Image");
    this.saveButton.addActionListener(this);
    this.saveButton.setActionCommand("save");
    this.commands.put("save", (ViewListener listener) -> listener.saveImageEvent());

    this.horizFlipButton = new JButton("Flip Horizontally");
    this.horizFlipButton.addActionListener(this);
    this.horizFlipButton.setActionCommand("horizontal-flip");
    this.commands.put("horizontal-flip", (ViewListener listener) -> listener.horizontalFlip());

    this.vertFlipButton = new JButton("Flip Vertically");
    this.vertFlipButton.addActionListener(this);
    this.vertFlipButton.setActionCommand("vertical-flip");
    this.commands.put("vertical-flip", (ViewListener listener) -> listener.verticalFlip());

    this.redComponentButton = new JButton("Red Component");
    this.redComponentButton.addActionListener(this);
    this.redComponentButton.setActionCommand("red-component");
    this.commands.put("red-component", (ViewListener listener) -> listener.redComponent());

    this.greenComponentButton = new JButton("Green Component");
    this.greenComponentButton.addActionListener(this);
    this.greenComponentButton.setActionCommand("green-component");
    this.commands.put("green-component", (ViewListener listener) -> listener.greenComponent());

    this.blueComponentButton = new JButton("Blue Component");
    this.blueComponentButton.addActionListener(this);
    this.blueComponentButton.setActionCommand("blue-component");
    this.commands.put("blue-component", (ViewListener listener) -> listener.blueComponent());

    this.valueComponentButton = new JButton("Value Component");
    this.valueComponentButton.addActionListener(this);
    this.valueComponentButton.setActionCommand("value-component");
    this.commands.put("value-component", (ViewListener listener) -> listener.valueComponent());

    this.intensityComponentButton = new JButton("Intensity Component");
    this.intensityComponentButton.addActionListener(this);
    this.intensityComponentButton.setActionCommand("intensity-component");
    this.commands.put("intensity-component",
        (ViewListener listener) -> listener.intensityComponent());

    this.lumaComponentButton = new JButton("Luma Component");
    this.lumaComponentButton.addActionListener(this);
    this.lumaComponentButton.setActionCommand("luma-component");
    this.commands.put("luma-component", (ViewListener listener) -> listener.lumaComponent());

    this.changeBrightnessButton = new JButton("Change Brightness");
    this.changeBrightnessButton.addActionListener(this);
    this.changeBrightnessButton.setActionCommand("brighten");
    this.commands.put("brighten", (ViewListener listener) -> listener.changeBrightness());

    this.blurButton = new JButton("Blur");
    this.blurButton.addActionListener(this);
    this.blurButton.setActionCommand("blur");
    this.commands.put("blur", (ViewListener listener) -> listener.blur());

    this.sharpenButton = new JButton("Sharpen");
    this.sharpenButton.addActionListener(this);
    this.sharpenButton.setActionCommand("sharpen");
    this.commands.put("sharpen", (ViewListener listener) -> listener.sharpen());

    this.sepiaButton = new JButton("Sepia");
    this.sepiaButton.addActionListener(this);
    this.sepiaButton.setActionCommand("sepia");
    this.commands.put("sepia", (ViewListener listener) -> listener.sepia());

    this.greyscaleButton = new JButton("Greyscale");
    this.greyscaleButton.addActionListener(this);
    this.greyscaleButton.setActionCommand("greyscale");
    this.commands.put("greyscale", (ViewListener listener) -> listener.greyscale());

    this.addButton(this.loadButton);
    this.addButton(this.saveButton);
    this.addButton(this.horizFlipButton);
    this.addButton(this.vertFlipButton);
    this.addButton(this.redComponentButton);
    this.addButton(this.greenComponentButton);
    this.addButton(this.blueComponentButton);
    this.addButton(this.valueComponentButton);
    this.addButton(this.intensityComponentButton);
    this.addButton(this.lumaComponentButton);
    this.addButton(this.changeBrightnessButton);
    this.addButton(this.blurButton);
    this.addButton(this.sharpenButton);
    this.addButton(this.sepiaButton);
    this.addButton(this.greyscaleButton);

    this.setVisible(false);
  }

  protected void addButton(JButton button) {
    this.buttonContainer.add(button);
  }

  @Override
  public void displayImage(IImage image) {
    Image img = CommonImageUtil.toBufferedImage(image);
    this.imageLabel.setIcon(new ImageIcon(img));
  }

  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(ImageGUIViewImpl.this, message,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addImage(String name) {
    ListModel<String> imgNameList = this.imageNameList.getModel();
    DefaultListModel<String> newImgNameList = new DefaultListModel<>();
    for (int i = 0; i < imgNameList.getSize(); i += 1) {
      String imgName = imgNameList.getElementAt(i);
      if (!imgName.equals(name)) {
        newImgNameList.addElement(imgName);
      }
    }
    newImgNameList.addElement(name);
    this.imageNameList.setModel(newImgNameList);
    this.currentImageName = name;
  }

  @Override
  public void showGUI(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void addLines(Color color, List<Line2D> lines) {
    this.histogramPanel.addLines(color, lines);
  }

  @Override
  public void clearLines() {
    this.histogramPanel.clearLines();
  }

  /**
   * Prompts the user for the filepath of a file to load. If the user does not provide a file,
   * returns an empty string.
   *
   * @return Either the filepath or an empty string.
   */
  @Override
  public String getLoadFilePath() {
    final JFileChooser fileChooser = new JFileChooser(".");
    int returnValue = fileChooser.showOpenDialog(ImageGUIViewImpl.this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      return "";
    }
  }

  @Override
  public String getSaveFilePath() {
    final JFileChooser fileChooser = new JFileChooser(".");
    int returnValue = fileChooser.showSaveDialog(ImageGUIViewImpl.this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      return "";
    }
  }

  @Override
  public String getUserInput(String message) {
    return JOptionPane.showInputDialog(message);
  }

  /**
   * Gets the name of the current image. If no image is currently selected, returns null.
   *
   * @return name of the current image or null
   */
  @Override
  public String getCurrentImage() {
    return this.currentImageName;
  }

  @Override
  public void addListener(ViewListener viewListener) {
    this.listenerList.add(viewListener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (commands.containsKey(command)) {
      emitEvent(commands.get(command));
    } else {
      this.displayErrorMessage("Invalid action performed!");
    }
  }

  /**
   * Emits an event to all of this view's listeners.
   *
   * @param command the command to emit
   */
  protected void emitEvent(Consumer<ViewListener> command) {
    for (ViewListener listener : this.listenerList) {
      command.accept(listener);
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    String changedImage = this.imageNameList.getSelectedValue();
    // If no value is selected, we should not be sending value changed events.
    // This often happens when a value is added to the list, which updates the list and calls
    // this method.
    if (changedImage == null) {
      return;
    }
    this.currentImageName = changedImage;
    emitEvent((ViewListener listener) -> listener.changeImageEvent(changedImage));
  }
}
