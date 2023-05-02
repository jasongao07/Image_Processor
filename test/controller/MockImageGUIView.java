package controller;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.List;

import model.image.IImage;
import view.ImageGUIView;
import view.ViewListener;

/**
 * Mock GUI view used for testing.
 */
public class MockImageGUIView implements ImageGUIView {
  private final Appendable out;
  private final String loadFilePath;
  private final String saveFilePath;
  private final String userInput;
  private final String currentImage;
  private final String userInput2;
  private int counter = 0;

  /**
   * Default constructor that sets everything as empty.
   *
   * @param out the appendable out
   */
  public MockImageGUIView(Appendable out) {
    this(out, "", "", "", "", "");
  }

  /**
   * Constructor for a GUIVIEW.
   *
   * @param out          the given output stream
   * @param loadFilePath the given loadfile path
   * @param saveFilePath the given save filepath
   * @param userInput    the given user input
   * @param currentImage the given image
   */
  public MockImageGUIView(Appendable out, String loadFilePath, String saveFilePath,
                          String userInput, String currentImage) {
    this(out, loadFilePath, saveFilePath, userInput, "", currentImage);
  }

  /**
   * The constructor for a gui view.
   *
   * @param out          the given output stream
   * @param loadFilePath the given loadfilepath
   * @param saveFilePath the given savefilepath
   * @param userInput    the user input
   * @param userInput2   the second user input
   * @param currentImage the current image
   */
  public MockImageGUIView(Appendable out, String loadFilePath, String saveFilePath,
                          String userInput, String userInput2, String currentImage) {
    this.out = out;
    this.loadFilePath = loadFilePath;
    this.saveFilePath = saveFilePath;
    this.userInput = userInput;
    this.currentImage = currentImage;
    this.userInput2 = userInput2;
  }

  @Override
  public void displayImage(IImage image) {
    try {
      this.out.append("Received displayImage command!\n");
      this.out.append("Image:\n");
      for (int row = 0; row < image.getHeight(); row += 1) {
        for (int col = 0; col < image.getWidth(); col += 1) {
          this.out.append(String.format("Row %d Col %d - Red: %d\n", row, col,
                  image.getPixelAt(row, col).getRed()));
          this.out.append(String.format("Row %d Col %d - Blue: %d\n", row, col,
                  image.getPixelAt(row, col).getBlue()));
          this.out.append(String.format("Row %d Col %d - Green: %d\n", row, col,
                  image.getPixelAt(row, col).getGreen()));
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public void displayErrorMessage(String message) {
    try {
      this.out.append("Received displayMessage command!\n");
      this.out.append(String.format("Message: %s\n", message));
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public void addImage(String name) {
    try {
      this.out.append("Received addImage command!\n");
      this.out.append(String.format("Name: %s\n", name));
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public void showGUI(boolean show) {
    try {
      this.out.append("Received show command!\n");
      this.out.append(String.format("show: %b\n", show));
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public void addLines(Color color, List<Line2D> lines) {
    try {
      this.out.append("Received addLines command!\n");
      this.out.append(String.format("Color: Red: %d Green: %d Blue: %d\n", color.getRed(),
              color.getGreen(), color.getBlue()));
      this.out.append("Lines:\n");
      for (Line2D line : lines) {
        this.out.append(String.format("(%d, %d) to (%d, %d)\n", (int) line.getX1(),
                (int) line.getY1(), (int) line.getX2(), (int) line.getY2()));
      }
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public void clearLines() {
    try {
      this.out.append("Removed lines\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }

  @Override
  public String getLoadFilePath() {
    try {
      this.out.append("Received getLoadFilePath command!\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
    return this.loadFilePath;
  }

  @Override
  public String getSaveFilePath() {
    try {
      this.out.append("Received getSaveFilePath command!\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
    return this.saveFilePath;
  }

  @Override
  public String getUserInput(String message) {
    try {
      this.out.append("Received getUserInput command!\n");
      this.out.append(String.format("Message: %s\n", message));
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }

    if (counter == 0) {
      counter++;
      return userInput;
    } else {
      return userInput2;
    }
  }

  @Override
  public String getCurrentImage() {
    try {
      this.out.append("Received getCurrentImage command!\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
    return this.currentImage;
  }

  @Override
  public void addListener(ViewListener viewListener) {
    try {
      this.out.append("Received addListener command!\n");
    } catch (IOException e) {
      throw new IllegalStateException("appendable no gud");
    }
  }
}
