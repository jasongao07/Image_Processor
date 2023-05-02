package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;


/**
 * This class contains methods for writing a PPM string given an IImage, and reading a PPM file
 * from a given file name.
 */
public class PPMUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static IImage readPPM(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null!");
    }
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.length() < 1) {
        throw new IllegalArgumentException("Not a PPM file!");
      }
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin " +
              "with P3");
    }
    int width = sc.nextInt();
    //System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    //System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);
    List<List<IPixel>> imageList = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<IPixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        //System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + ","
        // + b);
        row.add(new PixelImpl(r, g, b));
      }
      imageList.add(row);
    }
    return new ImageImpl(imageList);
  }

  /**
   * Converts an image to a ppm file.
   *
   * @param image the given image to be converted
   * @return the contents of the ppm file
   */
  public static String writePPM(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image is null!");
    }
    String token = "P3";
    String width = Integer.toString(image.getWidth());
    String height = Integer.toString(image.getHeight());
    String maxValue = Integer.toString(255);
    String finalString = token + "\n" + width + " " + height + "\n" + maxValue + "\n";
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel pixel = image.getPixelAt(i, j);
        finalString += pixel.getRed() + "\n" + pixel.getGreen() + "\n" + pixel.getBlue() + "\n";
      }
    }
    return finalString;
  }

  /**
   * Writes a ppm file.
   * @param filepath the given filepath
   * @param image the given image
   */
  public static void writePPMFile(String filepath, IImage image) {
    if (image == null || filepath == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    String ppmString = PPMUtil.writePPM(image);
    try {
      FileOutputStream fileOut = new FileOutputStream(filepath);
      fileOut.write(ppmString.getBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Was not able to write to file");
    }
  }
}

