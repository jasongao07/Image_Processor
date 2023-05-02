package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;

/**
 * This class supports reading and writing using Java's ImageIO class from common file image
 * formats including jpg, jpeg, bmp, and png.
 */
public class CommonImageUtil {

  /**
   * Reads an image from a given file name.
   *
   * @param filename the file being read
   * @return the image as an IImage
   */
  public static IImage readImage(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("null filename");
    }
    List<List<IPixel>> pixels = new ArrayList<>();
    BufferedImage image;
    try {
      image = ImageIO.read(new FileInputStream(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid Filepath");
    }
    if (image == null) {
      throw new IllegalArgumentException("Filepath is not an image");
    }
    for (int i = 0; i < image.getHeight(); i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < image.getWidth(); j++) {
        Color color = new Color(image.getRGB(j, i));
        pixels.get(i).add(new PixelImpl(color.getRed(), color.getGreen(), color.getBlue()));
      }
    }

    return new ImageImpl(pixels);
  }

  /**
   * Writes the image to the given destination.
   *
   * @param image       the given image
   * @param destination the given destination
   */
  public static void writeImage(IImage image, String destination) {
    if (image == null || destination == null) {
      throw new IllegalArgumentException("Image is null!");
    }
    if (destination.isEmpty()) {
      throw new IllegalArgumentException("destination is empty");
    }
    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);
    Color color;
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int red = image.getPixelAt(i, j).getRed();
        int green = image.getPixelAt(i, j).getGreen();
        int blue = image.getPixelAt(i, j).getBlue();
        color = new Color(red, green, blue);
        newImage.setRGB(j, i, color.getRGB());
      }
    }
    boolean validFileFormat;
    String[] potentialFormat = destination.split("\\.");
    if (potentialFormat.length <= 1) {
      throw new IllegalArgumentException("Invalid destination");
    }
    String fileFormat = potentialFormat[potentialFormat.length - 1];
    File out = new File(destination);
    try {
      validFileFormat = ImageIO.write(newImage, fileFormat, out);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot write file");
    }
    if (!validFileFormat) {
      throw new IllegalArgumentException("Invalid file format");
    }
  }

  /**
   * Creates a buffered image given an IIMage.
   * @param image the given iimage
   * @return the created buffered image
   */
  public static BufferedImage toBufferedImage(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    BufferedImage bImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);
    Color color;
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int red = image.getPixelAt(i, j).getRed();
        int green = image.getPixelAt(i, j).getGreen();
        int blue = image.getPixelAt(i, j).getBlue();
        color = new Color(red, green, blue);
        bImage.setRGB(j, i, color.getRGB());
      }
    }
    return bImage;
  }

  /**
   * Creates an IImage given a buffered image.
   * @param bImage the bimage
   * @return the iimage
   */
  public static IImage toIImage(BufferedImage bImage) {
    if (bImage == null) {
      throw new IllegalArgumentException("buffered image cannot be null");
    }
    List<List<IPixel>> pixels = new ArrayList<>();
    for (int i = 0; i < bImage.getHeight(); i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < bImage.getWidth(); j++) {
        Color color = new Color(bImage.getRGB(j, i));
        pixels.get(i).add(new PixelImpl(color.getRed(), color.getGreen(), color.getBlue()));
      }
    }
    return new ImageImpl(pixels);
  }
}
