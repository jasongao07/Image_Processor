package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import model.colortransformation.GreyscaleTransformation;
import model.colortransformation.IColorTransformation;
import model.colortransformation.SepiaTransformation;
import model.filter.GaussianBlurFilter;
import model.filter.IFilter;
import model.filter.SharpenFilter;
import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;

/**
 * This class represents the second version of the model, which supports all the previous
 * functionality through a delegate, and includes new functionality such as applying filters and
 * color transformations.
 */
public class ImageProcessorModelImplV2 implements ImageProcessorModelV2 {
  private final ImageProcessorModel delegate;
  private final Map<String, Supplier<IFilter>> filters;
  private final Map<String, Supplier<IColorTransformation>> transformations;

  /**
   * The model constructor that takes in a modelImpl by default.
   */
  public ImageProcessorModelImplV2() {
    this(new ImageProcessorModelImpl());
  }

  /**
   * The constructor that takes in a delegate and initializes the all the possible filters and
   * transformations.
   * @param delegate the Image model delegate
   */
  protected ImageProcessorModelImplV2(ImageProcessorModel delegate) {
    if (delegate == null) {
      throw new IllegalArgumentException("Invalid model");
    }
    this.delegate = delegate;
    this.filters = new HashMap<>();
    this.transformations = new HashMap<>();
    this.filters.putIfAbsent("blur", () -> new GaussianBlurFilter());
    this.filters.putIfAbsent("sharpen", () -> new SharpenFilter());
    this.transformations.putIfAbsent("sepia", () -> new SepiaTransformation());
    this.transformations.putIfAbsent("greyscale", () -> new GreyscaleTransformation());
  }

  @Override
  public void loadImage(IImage image, String name) {
    this.delegate.loadImage(image, name);
  }

  @Override
  public void horizontalFlip(String targetImage, String name) throws IllegalArgumentException {
    this.delegate.horizontalFlip(targetImage, name);
  }

  @Override
  public void verticalFlip(String targetImage, String name) throws IllegalArgumentException {
    this.delegate.verticalFlip(targetImage, name);
  }

  @Override
  public void changeBrightness(int delta, String targetImage, String name)
          throws IllegalArgumentException {
    this.delegate.changeBrightness(delta, targetImage, name);
  }

  @Override
  public void getComponent(String componentName, String targetImage, String name)
          throws IllegalArgumentException {
    this.delegate.getComponent(componentName, targetImage, name);
  }

  @Override
  public IImage saveImage(String targetImage) throws IllegalArgumentException {
    return this.delegate.saveImage(targetImage);
  }

  @Override
  public void applyFilter(String filterName, String targetImage, String name)
          throws IllegalArgumentException {
    if (filterName == null || targetImage == null || name == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if (!this.filters.containsKey(filterName)) {
      throw new IllegalArgumentException("Filter name does not exist");
    }
    this.delegate.saveImage(targetImage);
    IImage image = this.delegate.saveImage(targetImage);
    IFilter filterImage = this.filters.get(filterName).get();
    List<List<IPixel>> newImage = filterImage.filter(image).getImage();
    this.delegate.loadImage(new ImageImpl(newImage), name);
  }

  @Override
  public void applyColorTransformation(String transformationName, String targetImage, String name)
          throws IllegalArgumentException {
    if (transformationName == null || targetImage == null || name == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if (!this.transformations.containsKey(transformationName)) {
      throw new IllegalArgumentException("Transformation name does not exist");
    }
    IImage image = this.delegate.saveImage(targetImage);
    IColorTransformation transformation = this.transformations.get(transformationName).get();
    List<List<IPixel>> newImage = image.getImage();
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel currentPixel = newImage.get(i).get(j);
        newImage.get(i).set(j, transformation.transform(currentPixel));
      }
    }
    this.delegate.loadImage(new ImageImpl(newImage), name);
  }
}
