package view;

import java.io.IOException;
import model.ImageProcessorModelReadOnly;
import utils.PPMUtil;
import model.ImageProcessorModel;

/**
 * This class represents the image processor view impl, which supports functionalities such as
 * converting an image to ppm given the target name and rendering messages.
 */
public class ImageProcessorViewImpl implements ImageProcessorView {
  private final ImageProcessorModelReadOnly model;
  private final Appendable out;

  /**
   * Constructor that takes in a model and sets the out to System.out
   *
   * @param model the given model
   */
  public ImageProcessorViewImpl(ImageProcessorModel model) {
    this(model, System.out);
  }

  /**
   * Constructor that takes in a model, and an appendable out.
   *
   * @param model the model
   * @param out   the output stream for a message
   */
  public ImageProcessorViewImpl(ImageProcessorModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public void toPPM(String targetName, String destination) {
    PPMUtil.writePPMFile(destination, this.model.saveImage(targetName));
  }

  @Override
  public void renderMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("message is null");
    }
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Was not able to write to messageOut");
    }
  }
}
