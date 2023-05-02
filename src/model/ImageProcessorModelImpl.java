package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import model.components.ComponentGetter;
import model.components.GetBlue;
import model.components.GetGreen;
import model.components.GetIntensity;
import model.components.GetLuma;
import model.components.GetRed;
import model.components.GetValue;
import model.image.IImage;
import model.image.ImageImpl;
import model.image.IPixel;
import model.image.PixelImpl;

/**
 * This class is used as the model for an image processor. It supports the following
 * functionalities: loadImage, saveImage, flipping the image horizontally and vertically,
 * changing brightness, and converting to greyscale given the component.
 */
public class ImageProcessorModelImpl implements ImageProcessorModel {
  // INVARIANT: each image in the hashmap must be rectangular,
  // nonempty, nonnull, and not contain any nulls.
  private final Map<String, IImage> images;
  private final Map<String, Supplier<ComponentGetter>> components;

  /**
   * The constructor for an image processor model.
   */
  public ImageProcessorModelImpl() {
    this.images = new HashMap<>();
    this.components = new HashMap<>();
    this.components.put("red", () -> new GetRed());
    this.components.put("green", () -> new GetGreen());
    this.components.put("blue", () -> new GetBlue());
    this.components.put("value", () -> new GetValue());
    this.components.put("intensity", () -> new GetIntensity());
    this.components.put("luma", () -> new GetLuma());
  }

  @Override
  public void loadImage(IImage image, String name) {
    this.images.put(name, image);
  }

  @Override
  public IImage saveImage(String targetImage) throws IllegalArgumentException {
    if (!this.images.containsKey(targetImage)) {
      throw new IllegalArgumentException("Target image does not exits.");
    }
    return this.images.get(targetImage);
  }

  @Override
  public void horizontalFlip(String targetImage, String name) throws IllegalArgumentException {
    if (!this.images.containsKey(targetImage)) {
      throw new IllegalArgumentException("Target image does not exist");
    }
    if (this.images.containsKey(targetImage)) {
      IImage image = this.images.get(targetImage);
      List<List<IPixel>> newImage = image.getImage();
      for (int i = 0; i < image.getHeight(); i++) {
        Collections.reverse(newImage.get(i));
      }
      images.put(name, new ImageImpl(newImage));
    }
  }

  @Override
  public void verticalFlip(String targetImage, String name) throws IllegalArgumentException {
    if (!this.images.containsKey(targetImage)) {
      throw new IllegalArgumentException("Target image does not exist");
    }
    if (this.images.containsKey(targetImage)) {
      IImage image = this.images.get(targetImage);
      List<List<IPixel>> newImage = image.getImage();
      Collections.reverse(newImage);
      images.put(name, new ImageImpl(newImage));
    }
  }

  @Override
  public void changeBrightness(int delta, String targetImage, String name)
          throws IllegalArgumentException {
    if (!this.images.containsKey(targetImage)) {
      throw new IllegalArgumentException("Target Image does not exist or Invalid new name");
    } else {
      IImage image = images.get(targetImage);
      List<List<IPixel>> newImage = image.getImage();
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          IPixel pixel = image.getPixelAt(i, j);
          int red = Math.min(Math.max(pixel.getRed() + delta, 0), 255);
          int green = Math.min(Math.max(pixel.getGreen() + delta, 0), 255);
          int blue = Math.min(Math.max(pixel.getBlue() + delta, 0), 255);
          newImage.get(i).set(j, new PixelImpl(red, green, blue));
        }
      }
      this.images.put(name, new ImageImpl(newImage));
    }
  }

  @Override
  public void getComponent(String componentName, String targetImage, String name)
          throws IllegalArgumentException {
    if (!this.images.containsKey(targetImage)) {
      throw new IllegalArgumentException("Target Image does not exist");
    }
    if (!this.components.containsKey(componentName)) {
      throw new IllegalArgumentException("Component does not exist.");
    }
    ComponentGetter component = this.components.get(componentName).get();
    IImage image = this.images.get(targetImage);
    List<List<IPixel>> newImage = image.getImage();
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel pixel = image.getPixelAt(i, j);
        int value = component.componentValue(pixel);
        newImage.get(i).set(j, new PixelImpl(value, value, value));
      }
    }
    this.images.put(name, new ImageImpl(newImage));
  }
}
