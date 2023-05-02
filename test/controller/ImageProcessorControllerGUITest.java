package controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import histogram.HistogramDrawerImpl;
import histogram.IHistogramDrawer;
import model.ImageProcessorModelImplV2;
import model.ImageProcessorModelV2;
import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;
import utils.CommonImageUtil;
import utils.PPMUtil;
import view.ImageGUIView;
import view.ViewListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Controller GUI.
 */
public class ImageProcessorControllerGUITest {

  ImageProcessorModelV2 model;
  ImageGUIView view;
  Appendable out;
  ImageProcessorController controller;
  ViewListener controller2;
  List<IPixel> row1;
  List<IPixel> row2;
  List<List<IPixel>> imageList;
  IPixel black;
  IPixel yellow;
  IImage image;

  @Before
  public void setUp() {
    this.model = new ImageProcessorModelImplV2();
    this.out = new StringBuilder();
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png", "meow",
            "blackImage");
    this.controller = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
  }

  @Test
  public void run() {
    this.controller.run();
    String[] msgs = this.out.toString().split("\n");
    // Add listener added twice due to 2 controllers
    assertEquals("Received addListener command!", msgs[0]);
    assertEquals("Received addListener command!", msgs[1]);
    assertEquals("Received show command!", msgs[2]);
    assertEquals("show: true", msgs[3]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorWidth() {
    controller2 = new ImageProcessorControllerGUI(this.model, this.view, -10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorHeight() {
    controller2 = new ImageProcessorControllerGUI(this.model, this.view, 10, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorBoth() {
    controller2 = new ImageProcessorControllerGUI(this.model, this.view, -10, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorModel() {
    controller2 = new ImageProcessorControllerGUI(null, this.view, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorModel2() {
    controller2 = new ImageProcessorControllerGUI(null, this.view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorView() {
    controller2 = new ImageProcessorControllerGUI(this.model, null, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorView2() {
    controller2 = new ImageProcessorControllerGUI(this.model, null);
  }

  @Test
  public void testBadArguments() {
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png", "",
            "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.loadImageEvent();
    this.controller2.horizontalFlip();
    this.controller2.verticalFlip();
    this.controller2.redComponent();
    this.controller2.greenComponent();
    this.controller2.blueComponent();
    this.controller2.valueComponent();
    this.controller2.intensityComponent();
    this.controller2.lumaComponent();
    this.controller2.blur();
    this.controller2.sharpen();
    this.controller2.sepia();
    this.controller2.greyscale();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Message: Bad arguments", msgs[7]);
    assertEquals("Message: Bad arguments", msgs[11]);
    assertEquals("Message: Bad arguments", msgs[15]);
    assertEquals("Message: Bad arguments", msgs[19]);
    assertEquals("Message: Bad arguments", msgs[23]);
    assertEquals("Message: Bad arguments", msgs[27]);
    assertEquals("Message: Bad arguments", msgs[31]);
    assertEquals("Message: Bad arguments", msgs[35]);
    assertEquals("Message: Bad arguments", msgs[39]);
    assertEquals("Message: Bad arguments", msgs[43]);
    assertEquals("Message: Bad arguments", msgs[47]);
    assertEquals("Message: Bad arguments", msgs[51]);
    assertEquals("Message: Bad arguments", msgs[55]);
  }

  @Test
  public void testNullBadArguments() {
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png", null,
            "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.loadImageEvent();
    this.controller2.horizontalFlip();
    this.controller2.verticalFlip();
    this.controller2.redComponent();
    this.controller2.greenComponent();
    this.controller2.blueComponent();
    this.controller2.valueComponent();
    this.controller2.intensityComponent();
    this.controller2.lumaComponent();
    this.controller2.blur();
    this.controller2.sharpen();
    this.controller2.sepia();
    this.controller2.greyscale();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Message: Bad arguments", msgs[7]);
    assertEquals("Message: Bad arguments", msgs[11]);
    assertEquals("Message: Bad arguments", msgs[15]);
    assertEquals("Message: Bad arguments", msgs[19]);
    assertEquals("Message: Bad arguments", msgs[23]);
    assertEquals("Message: Bad arguments", msgs[27]);
    assertEquals("Message: Bad arguments", msgs[31]);
    assertEquals("Message: Bad arguments", msgs[35]);
    assertEquals("Message: Bad arguments", msgs[39]);
    assertEquals("Message: Bad arguments", msgs[43]);
    assertEquals("Message: Bad arguments", msgs[47]);
    assertEquals("Message: Bad arguments", msgs[51]);
    assertEquals("Message: Bad arguments", msgs[55]);
  }

  @Test
  public void testBadCurrentArguments() {
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png", "image",
            "orange");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    this.controller2.horizontalFlip();
    this.controller2.verticalFlip();
    this.controller2.redComponent();
    this.controller2.greenComponent();
    this.controller2.blueComponent();
    this.controller2.valueComponent();
    this.controller2.intensityComponent();
    this.controller2.lumaComponent();
    this.controller2.blur();
    this.controller2.sharpen();
    this.controller2.sepia();
    this.controller2.greyscale();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Message: Bad arguments", msgs[6]);
    assertEquals("Message: Bad arguments", msgs[11]);
    assertEquals("Message: Bad arguments", msgs[15]);
    assertEquals("Message: Bad arguments", msgs[19]);
    assertEquals("Message: Bad arguments", msgs[23]);
    assertEquals("Message: Bad arguments", msgs[27]);
    assertEquals("Message: Bad arguments", msgs[31]);
    assertEquals("Message: Bad arguments", msgs[35]);
    assertEquals("Message: Bad arguments", msgs[39]);
    assertEquals("Message: Bad arguments", msgs[43]);
    assertEquals("Message: Bad arguments", msgs[47]);
    assertEquals("Message: Bad arguments", msgs[51]);
    assertEquals("Message: Bad arguments", msgs[55]);
  }

  @Test
  public void loadImageEvent() {
    this.controller2.loadImageEvent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getLoadFilePath command!", msgs[2]);
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received addImage command!", msgs[5]);
    assertEquals("Name: meow", msgs[6]);
    assertEquals("Removed lines", msgs[7]);
    assertEquals("Received displayImage command!", msgs[8]);
    assertEquals("Image:", msgs[9]);

    int count = 10;
    IImage image = CommonImageUtil.readImage("testImage.png");
    for (int row = 0; row < 2; row += 1) {
      for (int col = 0; col < 3; col += 1) {
        assertEquals(String.format("Row %d Col %d - Red: %d", row, col,
                image.getPixelAt(row, col).getRed()), msgs[count++]);
        assertEquals(String.format("Row %d Col %d - Blue: %d", row, col,
                image.getPixelAt(row, col).getBlue()), msgs[count++]);
        assertEquals(String.format("Row %d Col %d - Green: %d", row, col,
                image.getPixelAt(row, col).getGreen()), msgs[count++]);
      }
    }

    assertEquals("Received addLines command!", msgs[count++]);
    assertEquals("Color: Red: 255 Green: 0 Blue: 0", msgs[count++]);
    assertEquals("Lines:", msgs[count++]);

    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(255, 255);
    List<Line2D> allLines = new ArrayList<>();
    allLines.addAll(histogramDrawer.getRedLines(image));


    for (Line2D line : allLines) {
      assertEquals(String.format("(%d, %d) to (%d, %d)", (int) line.getX1(),
              (int) line.getY1(), (int) line.getX2(), (int) line.getY2()), msgs[count++]);
    }

    allLines = new ArrayList<>();
    allLines.addAll(histogramDrawer.getGreenLines(image));
    assertEquals("Received addLines command!", msgs[count++]);
    assertEquals("Color: Red: 0 Green: 255 Blue: 0", msgs[count++]);
    assertEquals("Lines:", msgs[count++]);

    for (Line2D line : allLines) {
      assertEquals(String.format("(%d, %d) to (%d, %d)", (int) line.getX1(),
              (int) line.getY1(), (int) line.getX2(), (int) line.getY2()), msgs[count++]);
    }

    assertEquals("Received addLines command!", msgs[count++]);
    assertEquals("Color: Red: 0 Green: 0 Blue: 255", msgs[count++]);
    assertEquals("Lines:", msgs[count++]);
    allLines = new ArrayList<>();
    allLines.addAll(histogramDrawer.getBlueLines(image));

    for (Line2D line : allLines) {
      assertEquals(String.format("(%d, %d) to (%d, %d)", (int) line.getX1(),
              (int) line.getY1(), (int) line.getX2(), (int) line.getY2()), msgs[count++]);
    }

    assertEquals("Received addLines command!", msgs[count++]);
    assertEquals("Color: Red: 128 Green: 128 Blue: 128", msgs[count++]);
    assertEquals("Lines:", msgs[count++]);
    allLines = new ArrayList<>();
    allLines.addAll(histogramDrawer.getIntensityLines(image));

  }

  @Test
  public void loadImagePPM() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);

    this.model.loadImage(this.image, "blackImage");
    PPMUtil.writePPMFile("test/testBlackImage.ppm", this.image);
    this.view = new MockImageGUIView(this.out, "test/testBlackImage.ppm", "test/testOutBlackImage" +
            ".ppm", "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.loadImageEvent();
    String[] msgs = this.out.toString().split("\n");

    assertEquals("Received getLoadFilePath command!", msgs[3]);
    assertEquals("Received getUserInput command!", msgs[4]);
    assertEquals("Message: Please put in the name of the image", msgs[5]);
    assertEquals("Received addImage command!", msgs[6]);
    assertEquals("Name: meow", msgs[7]);
    assertEquals("Removed lines", msgs[8]);
    assertEquals("Received displayImage command!", msgs[9]);
    assertEquals("Image:", msgs[10]);
  }

  @Test
  public void loadImageErrorPPM() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);

    this.model.loadImage(this.image, "blackImage");
    PPMUtil.writePPMFile("test/testBlackOutImage.ppm", this.image);
    this.view = new MockImageGUIView(this.out, "test/fdsfd/image.ppm", "test/testOutBlackImage" +
            ".ppm", "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.loadImageEvent();
    String[] msgs = this.out.toString().split("\n");

    assertEquals("Received getLoadFilePath command!", msgs[3]);
    assertEquals("Received displayMessage command!", msgs[4]);
    assertEquals("Message: Invalid Filepath", msgs[5]);
  }

  @Test
  public void loadImageJPG() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);

    this.model.loadImage(this.image, "blackImage");
    PPMUtil.writePPMFile("test/testBlackImage.jpg", this.image);
    this.view = new MockImageGUIView(this.out, "test/testBlackImage.jpg", "test/testOutBlackImage" +
            ".jpg", "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.loadImageEvent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getLoadFilePath command!", msgs[3]);
    assertEquals("Received getUserInput command!", msgs[4]);
    assertEquals("Message: Please put in the name of the image", msgs[5]);
    assertEquals("Received addImage command!", msgs[6]);
    assertEquals("Name: meow", msgs[7]);
    assertEquals("Removed lines", msgs[8]);
    assertEquals("Received displayImage command!", msgs[9]);
    assertEquals("Image:", msgs[10]);

  }

  // This does not error as an invalid image format will just be written as a ppm string.
  @Test
  public void saveImageEventInvalidImageFormat() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.png",
            "test/testOutBlackImage.random", "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals(5, msgs.length);
  }

  @Test
  public void saveImageEventInvalidImagePath() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.png",
            "test/wat/testOutBlackImage.png", "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received displayMessage command!", msgs[5]);
    assertEquals("Message: Bad arguments", msgs[6]);
  }

  @Test
  public void saveImageEventPNG() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.png", "test/testOutBlackImage.png",
            "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();

    CommonImageUtil.writeImage(this.model.saveImage("blackImage"), "test/testOutBlackImage.png");
    IImage savedImage = CommonImageUtil.readImage("test/testOutBlackImage.png");
    for (int row = 0; row < 2; row += 1) {
      for (int col = 0; col < 3; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(savedImage.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(savedImage.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test
  public void saveImageEventBMP() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.bmp", "test/testOutBlackImage.bmp",
            "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    CommonImageUtil.writeImage(this.model.saveImage("blackImage"), "test/testOutBlackImage.bmp");
    IImage savedImage = CommonImageUtil.readImage("test/testOutBlackImage.bmp");
    for (int row = 0; row < 2; row += 1) {
      for (int col = 0; col < 3; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(savedImage.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(savedImage.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test
  public void saveImageEventPPM() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.ppm", "test/testOutBlackImage.ppm",
            "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();

    PPMUtil.writePPMFile("test/testOutBlackImage.ppm", this.image);
    IImage savedImage = PPMUtil.readPPM("test/testOutBlackImage.ppm");
    for (int row = 0; row < 2; row += 1) {
      for (int col = 0; col < 3; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(savedImage.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(savedImage.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test
  public void saveImageEventJPG() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.jpg", "test/testOutBlackImage.jpg",
            "meow", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    CommonImageUtil.writeImage(this.model.saveImage("blackImage"), "test/testOutBlackImage.jpg");
    IImage savedImage = CommonImageUtil.readImage("test/testOutBlackImage.jpg");
    assertNotNull(savedImage);
  }

  @Test
  public void saveImageEventMessages() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testBlackImage.png", "test/testOutBlackImage.png",
            "meow", "blackImage");
    CommonImageUtil.writeImage(this.model.saveImage("blackImage"), "test/testOutBlackImage.png");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.saveImageEvent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getCurrentImage command!", msgs[3]);
    assertEquals("Received getSaveFilePath command!", msgs[4]);
  }

  @Test
  public void changeImageEventNullImageName() {
    this.controller2.changeImageEvent(null);
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received displayMessage command!", msgs[2]);
    assertEquals("Message: Invalid image name", msgs[3]);
  }

  @Test
  public void changeImageEvent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.changeImageEvent("blackImage");
    String[] msgs = this.out.toString().split("\n");

    assertEquals("Removed lines", msgs[3]);
    assertEquals("Received displayImage command!", msgs[4]);
    assertEquals("Image:", msgs[5]);
    assertEquals("Received addLines command!", msgs[24]);
    assertEquals("Lines:", msgs[26]);
  }

  @Test
  public void horizontalFlip() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);
    this.controller2.horizontalFlip();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);

  }

  @Test
  public void verticalFlip() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.verticalFlip();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void redComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.redComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void greenComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.greenComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void blueComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.blueComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void valueComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.valueComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void intensityComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.intensityComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void lumaComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.lumaComponent();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void changeBrightness() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "10", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.changeBrightness();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image",
            msgs[4]);
    assertEquals("Message: How much do you want to increase it by?", msgs[6]);
    assertEquals("Received getCurrentImage command!", msgs[7]);
    assertEquals("Removed lines", msgs[8]);
    assertEquals("Received displayImage command!", msgs[9]);
    assertEquals("Image:", msgs[10]);
    assertEquals("Received addLines command!", msgs[29]);
    assertEquals("Lines:", msgs[31]);
  }

  @Test
  public void blur() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.blur();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void sharpen() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.sharpen();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void sepia() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.sepia();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }

  @Test
  public void greyscale() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black);
    Collections.addAll(this.row2, this.black, this.yellow, this.black);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    this.view = new MockImageGUIView(this.out, "testImage.png", "test/testOutImage.png",
            "blackImage", "blackImage");
    this.controller2 = new ImageProcessorControllerGUI(this.model, this.view);

    this.controller2.greyscale();
    String[] msgs = this.out.toString().split("\n");
    assertEquals("Received getUserInput command!", msgs[3]);
    assertEquals("Message: Please put in the name of the image", msgs[4]);
    assertEquals("Received getCurrentImage command!", msgs[5]);
    assertEquals("Removed lines", msgs[6]);
    assertEquals("Received displayImage command!", msgs[7]);
    assertEquals("Image:", msgs[8]);
    assertEquals("Received addLines command!", msgs[27]);
    assertEquals("Lines:", msgs[29]);
  }
}