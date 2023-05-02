package controller;


import java.awt.Color;
import histogram.HistogramDrawerImpl;
import histogram.IHistogramDrawer;
import model.ImageProcessorModelV2;
import model.image.IImage;
import utils.CommonImageUtil;
import utils.PPMUtil;
import view.ImageGUIView;
import view.ViewListener;

/**
 * The Processor Controller class that supports a gui. This class supports all the
 * functionalities of the model, and updates the histogram in the gui.
 */
public class ImageProcessorControllerGUI implements ImageProcessorController, ViewListener {
  private final ImageProcessorModelV2 model;
  private final ImageGUIView view;
  private final int width;
  private final int height;

  /**
   * The constructor that sets the default width and height of the histogram image as 255.
   *
   * @param model the given model
   * @param view  the given view
   */
  public ImageProcessorControllerGUI(ImageProcessorModelV2 model, ImageGUIView view) {
    this(model, view, 255, 255);
  }

  /**
   * The constructor that constructs a gui controller given the model, view, height, and width.
   *
   * @param model  the given model
   * @param view   the given view
   * @param width  the given width
   * @param height the given height
   */
  public ImageProcessorControllerGUI(ImageProcessorModelV2 model, ImageGUIView view, int width,
                                     int height) {
    if (model == null || view == null || width < 0 || height < 0) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.model = model;
    this.view = view;
    this.width = width;
    this.height = height;
    this.view.addListener(this);
  }

  @Override
  public void run() {
    this.view.showGUI(true);
  }

  /**
   * Creates a list of lines in the histogram.
   *
   * @param image  the given image to be created into a histogram
   * @param width  the width of the histogram
   * @param height the height of the histogram
   */
  protected void createHistogram(IImage image, int width, int height) {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(width, height);

    this.view.addLines(Color.red, histogramDrawer.getRedLines(image));
    this.view.addLines(Color.green, histogramDrawer.getGreenLines(image));
    this.view.addLines(Color.blue, histogramDrawer.getBlueLines(image));
    this.view.addLines(Color.GRAY, histogramDrawer.getIntensityLines(image));
  }

  @Override
  public void loadImageEvent() {
    IImage image = null;
    String filepath = this.view.getLoadFilePath();
    if (filepath == null) {
      this.view.displayErrorMessage("Bad arguments");
    }
    try {
      image = CommonImageUtil.readImage(filepath);
    } catch (IllegalArgumentException e) {
      try {
        image = PPMUtil.readPPM(filepath);
      } catch (IllegalArgumentException i) {
        this.view.displayErrorMessage("Invalid Filepath");
        return;
      }
    }
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    this.view.addImage(imageName);
    this.view.clearLines();
    this.view.displayImage(image);
    createHistogram(image, this.width, this.height);
    this.model.loadImage(image, imageName);
  }

  @Override
  public void saveImageEvent() {
    String imageName = this.view.getCurrentImage();
    String imageFilePath = this.view.getSaveFilePath();
    if (imageName == null || imageFilePath == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      CommonImageUtil.writeImage(this.model.saveImage(imageName), imageFilePath);
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("Invalid file format")) {
        try {
          PPMUtil.writePPMFile(imageFilePath, this.model.saveImage(imageName));
        } catch (IllegalArgumentException i) {
          this.view.displayErrorMessage("Not a supported filetype");
        }
      } else {
        this.view.displayErrorMessage("Bad arguments");
      }
    }
  }

  @Override
  public void changeImageEvent(String imageName) {
    if (imageName == null) {
      this.view.displayErrorMessage("Invalid image name");
    } else {
      try {
        IImage image = this.model.saveImage(imageName);
        this.view.clearLines();
        this.view.displayImage(image);
        createHistogram(image, this.width, this.height);
        this.model.loadImage(image, imageName);
      } catch (IllegalArgumentException e) {
        this.view.displayErrorMessage("Bad arguments");
      }
    }
  }

  @Override
  public void horizontalFlip() {
    String imageName = this.view.getUserInput("Please put in the name of the image");

    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.horizontalFlip(this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void verticalFlip() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.verticalFlip(this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void redComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("red", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void greenComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("green", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void blueComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("blue", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void valueComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("value", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void intensityComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("intensity", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void lumaComponent() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.getComponent("luma", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void changeBrightness() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      int delta = Integer.parseInt(
              this.view.getUserInput("How much do you want to increase it by?"));
      this.model.changeBrightness(delta, this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }

  }

  @Override
  public void blur() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.applyFilter("blur", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void sharpen() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.applyFilter("sharpen", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void sepia() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.applyColorTransformation("sepia", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }

  @Override
  public void greyscale() {
    String imageName = this.view.getUserInput("Please put in the name of the image");
    if (imageName == null || imageName.equals("")) {
      this.view.displayErrorMessage("Bad arguments");
      return;
    }
    try {
      this.model.applyColorTransformation("greyscale", this.view.getCurrentImage(), imageName);
      IImage image = this.model.saveImage(imageName);
      this.view.clearLines();
      this.view.displayImage(image);
      createHistogram(image, this.width, this.height);
      this.view.addImage(imageName);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Bad arguments");
    }
  }
}
