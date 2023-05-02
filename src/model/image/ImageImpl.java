package model.image;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an image. The image is represented as a 2D list of IPixels.
 */
public class ImageImpl implements IImage {
  private final List<List<IPixel>> image;

  /**
   * The constructor for an image.
   *
   * @param image the image
   */
  public ImageImpl(List<List<IPixel>> image) {
    if (image == null || image.isEmpty()) {
      throw new IllegalArgumentException("image cannot be null or empty");
    }
    for (int i = 0; i < image.size(); i++) {
      if (image.get(i) == null || image.get(i).isEmpty()) {
        throw new IllegalArgumentException("row of image cannot be null or empty");
      }
    }
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(i).size(); j++) {
        if (image.get(i).get(j) == null) {
          throw new IllegalArgumentException("pixels cannot be null");
        }
      }
    }
    for (int i = 0; i < image.size() - 1; i++) {
      if (image.get(i).size() != image.get(i + 1).size()) {
        throw new IllegalArgumentException("pixels cannot be null");
      }
    }
    this.image = cloneImage(image);
  }

  @Override
  public int getWidth() {
    return image.get(0).size();
  }

  @Override
  public int getHeight() {
    return image.size();
  }

  @Override
  public IPixel getPixelAt(int row, int col) {
    return image.get(row).get(col);
  }

  @Override
  public List<List<IPixel>> getImage() {
    return cloneImage(this.image);
  }

  /**
   * Clones an image and returns the cloned image.
   *
   * @param image the image to be cloned
   * @return the cloned image
   */
  private static List<List<IPixel>> cloneImage(List<List<IPixel>> image) {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null.");
    }
    List<List<IPixel>> newImage = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      newImage.add(new ArrayList<>(image.get(i)));
    }
    return newImage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IImage)) {
      return false;
    }
    IImage other = (IImage) o;
    return this.getImage().equals(other.getImage());
  }

  @Override
  public int hashCode() {
    return this.getImage().hashCode();
  }
}
