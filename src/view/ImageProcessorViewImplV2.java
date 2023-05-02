package view;

import model.ImageProcessorModel;
import utils.CommonImageUtil;

/**
 * This class represents the second version of the view, which supports all the previous
 * functionalities through a delegate and the new functionality toImage which handles writing to
 * common image formats such as jpg, bmp, jpeg, and png.
 */
public class ImageProcessorViewImplV2 implements ImageProcessorViewV2 {
  private final ImageProcessorView delegate;
  private final ImageProcessorModel model;

  /**
   * The constructor for the view.
   * @param model the given model
   * @param out the output stream
   */
  public ImageProcessorViewImplV2(ImageProcessorModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.delegate = new ImageProcessorViewImpl(model, out);
    this.model = model;
  }

  /**
   * The constructor that takes in only 1 input: model.
   * @param model the given input
   */
  public ImageProcessorViewImplV2(ImageProcessorModel model) {
    this(model, System.out);
  }

  @Override
  public void toImage(String targetName, String destination) throws IllegalArgumentException {
    if (targetName == null || destination == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    CommonImageUtil.writeImage(this.model.saveImage(targetName), destination);
  }

  @Override
  public void toPPM(String targetName, String destination) throws IllegalArgumentException {
    this.delegate.toPPM(targetName, destination);
  }

  @Override
  public void renderMessage(String message) {
    this.delegate.renderMessage(message);
  }
}
