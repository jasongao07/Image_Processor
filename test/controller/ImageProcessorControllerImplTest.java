package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ImageProcessorModelV2;
import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import model.image.PixelImpl;
import utils.PPMUtil;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;
import view.ImageProcessorViewV2;

import static org.junit.Assert.assertEquals;

/**
 * Test class for controller.
 */
public class ImageProcessorControllerImplTest {
  Readable in;
  Appendable out;
  ImageProcessorModel model;
  ImageProcessorView view;
  ImageProcessorController controller;
  IPixel black;
  IPixel yellow;
  IPixel red;
  IPixel grey;
  IPixel orange;
  IPixel blue;
  List<IPixel> row1;
  List<IPixel> row2;
  List<List<IPixel>> imageList;
  IImage image;
  int lastWelcomeMessageIdx;

  @Before
  public void setUp() {
    // test/testColorfulImage.ppm:
    // Black Yellow Red
    // Grey  Orange Blue
    this.model = constructModel();
    this.out = new StringBuilder();
    this.view = constructView(this.model, this.out);
    this.in = new StringReader("");
    this.controller = constructController(this.in, this.model, this.view);
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);

    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);

    ImageProcessorModel model2 = constructModel();
    Appendable out2 = new StringBuilder();
    ImageProcessorView view2 = constructView(model2, out2);
    Readable in2 = new StringReader("q");
    ImageProcessorController controller2 = constructController(in2, model2, view2);
    controller2.run();
    String[] msgs = out2.toString().split("\n");
    this.lastWelcomeMessageIdx = msgs.length - 2;
  }

  protected ImageProcessorModel constructModel() {
    return new ImageProcessorModelImpl();
  }

  protected ImageProcessorView constructView(ImageProcessorModel model, Appendable out) {
    return new ImageProcessorViewImpl(model, out);
  }

  protected ImageProcessorController constructController(Readable in,
                                                         ImageProcessorModel model,
                                                         ImageProcessorView view) {
    return new ImageProcessorControllerImpl(in, model, view);
  }

  protected ImageProcessorController constructController(Readable in,
                                                         ImageProcessorModelV2 model,
                                                         ImageProcessorViewV2 view) {
    return new ImageProcessorControllerImpl(in, model, view);
  }

  protected ImageProcessorController constructController(ImageProcessorModel model,
                                                         ImageProcessorView view) {
    return new ImageProcessorControllerImpl(model, view);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructControllerNullModel2ArgumentConstructor() {
    this.controller = constructController(null, this.view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructControllerNullView2ArgumentConstructor() {
    this.controller = constructController(this.model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructControllerNullModel3ArgumentConstructor() {
    this.controller = constructController(this.in, null, this.view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructControllerNullView3ArgumentConstructor() {
    this.controller = constructController(this.in, this.model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructControllerNullReadable3ArgumentConstructor() {
    this.controller = constructController(null, this.model, this.view);
  }

  @Test
  public void testWelcomeMessage() {
    this.in = new StringReader("q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Welcome to the Image Processing Program! Here are a list of supported " +
            "commands:", output[0]);
    assertEquals("load image-path image-name", output[1]);
    assertEquals("Load an image from the specified path and refer it to henceforth in the " +
            "program by the given image name.", output[2]);
    assertEquals("save image-path image-name", output[3]);
    assertEquals("Save the image with the given name to the specified path", output[4]);
    assertEquals("(component-name)-component image-name dest-image-name", output[5]);
    assertEquals("Create a greyscale image with the given component of the image with the " +
            "given name, and refer to it henceforth in the program by the given " +
            "destination name.", output[6]);
    assertEquals("Supported components:", output[7]);
    assertEquals("red, green, blue, value, intensity, luma", output[8]);
    assertEquals("horizontal-flip image-name dest-image-name", output[9]);
    assertEquals("Flip an image horizontally to create a new image, referred to henceforth by " +
            "the given destination name.", output[10]);
    assertEquals("vertical-flip image-name dest-image-name", output[11]);
    assertEquals("Flip an image vertically to create a new image, referred to henceforth by " +
            "the given destination name.", output[12]);
    assertEquals("brighten increment image-name dest-image-name", output[13]);
    assertEquals("brighten the image by the given increment to create a new image, referred to " +
            "henceforth by the given destination name. The increment may be positive " +
            "(brightening) or negative (darkening)", output[14]);
  }

  @Test
  public void testQuitImmediately() {
    this.in = new StringReader("q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Quitting the program.", output[this.lastWelcomeMessageIdx + 1]);
  }

  @Test
  public void testInvalidCommand() {
    this.in = new StringReader("lod ./test/testColorfulImage.ppm colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid command.", output[this.lastWelcomeMessageIdx + 1]);
    assertEquals("Invalid command.", output[this.lastWelcomeMessageIdx + 2]);
    assertEquals("Invalid command.", output[this.lastWelcomeMessageIdx + 3]);
  }

  @Test
  public void testNonPPMImage() {
    this.in = new StringReader("load ./README.md colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Given file is not a PPM file", output[this.lastWelcomeMessageIdx + 1]);
  }

  @Test
  public void testLoadInvalidImagePath() {
    this.in = new StringReader("load somethingsomething colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image path.", output[this.lastWelcomeMessageIdx + 1]);
    assertEquals("Quitting the program.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testLoad() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Image successfully loaded as colorfulImage",
            output[this.lastWelcomeMessageIdx + 1]);
    IImage img = this.model.saveImage("colorfulImage");
    assertEquals(this.image, img);
  }

  @Test
  public void testSaveInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "save ./testImage.ppm nontexitentImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid arguments.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testSaveInvalidImagePath() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "save ./notexistentdirectory/something colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid arguments.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testSaveImage() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "save ./testImage.ppm colorfulImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully saved colorfulImage to ./testImage.ppm",
            output[this.lastWelcomeMessageIdx + 2]);
    IImage img = PPMUtil.readPPM("./testImage.ppm");
    assertEquals(img, this.image);
  }

  @Test
  public void testComponentInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "red-component somethingImage redImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testRedComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "red-component colorfulImage redImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created redImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage redImg = this.model.saveImage("redImage");
    for (int row = 0; row < redImg.getHeight(); row += 1) {
      for (int col = 0; col < redImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getRed(),
                redImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getRed(),
                redImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getRed(),
                redImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testGreenComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "green-component colorfulImage greenImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created greenImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage greenImg = this.model.saveImage("greenImage");
    for (int row = 0; row < greenImg.getHeight(); row += 1) {
      for (int col = 0; col < greenImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getGreen(),
                greenImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getGreen(),
                greenImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getGreen(),
                greenImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testBlueComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "blue-component colorfulImage blueImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created blueImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage blueImg = this.model.saveImage("blueImage");
    for (int row = 0; row < blueImg.getHeight(); row += 1) {
      for (int col = 0; col < blueImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getBlue(),
                blueImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getBlue(),
                blueImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getBlue(),
                blueImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testValueComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "value-component colorfulImage valueImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created valueImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage valueImg = this.model.saveImage("valueImage");
    for (int row = 0; row < valueImg.getHeight(); row += 1) {
      for (int col = 0; col < valueImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getValue(),
                valueImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getValue(),
                valueImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getValue(),
                valueImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testIntensityComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "intensity-component colorfulImage intensityImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created intensityImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage intensityImg = this.model.saveImage("intensityImage");
    for (int row = 0; row < intensityImg.getHeight(); row += 1) {
      for (int col = 0; col < intensityImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getIntensity(),
                intensityImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getIntensity(),
                intensityImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getIntensity(),
                intensityImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testLumaComponent() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "luma-component colorfulImage lumaImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created lumaImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage lumaImg = this.model.saveImage("lumaImage");
    for (int row = 0; row < lumaImg.getHeight(); row += 1) {
      for (int col = 0; col < lumaImg.getWidth(); col += 1) {
        assertEquals(this.image.getPixelAt(row, col).getLuma(),
                lumaImg.getPixelAt(row, col).getRed());
        assertEquals(this.image.getPixelAt(row, col).getLuma(),
                lumaImg.getPixelAt(row, col).getBlue());
        assertEquals(this.image.getPixelAt(row, col).getLuma(),
                lumaImg.getPixelAt(row, col).getGreen());
      }
    }
  }

  @Test
  public void testHorizontalFlipInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "horizontal-flip whatImage hFlippedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testHorizontalFlip() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "horizontal-flip colorfulImage hFlippedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created hFlippedImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage hFlippedImg = this.model.saveImage("hFlippedImage");
    assertEquals(this.red, hFlippedImg.getPixelAt(0, 0));
    assertEquals(this.yellow, hFlippedImg.getPixelAt(0, 1));
    assertEquals(this.black, hFlippedImg.getPixelAt(0, 2));
    assertEquals(this.blue, hFlippedImg.getPixelAt(1, 0));
    assertEquals(this.orange, hFlippedImg.getPixelAt(1, 1));
    assertEquals(this.grey, hFlippedImg.getPixelAt(1, 2));
  }

  @Test
  public void testVerticalFlipInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "vertical-flip whatImage vFlippedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testVerticalFlip() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "vertical-flip colorfulImage vFlippedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created vFlippedImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage vFlippedImg = this.model.saveImage("vFlippedImage");
    assertEquals(this.grey, vFlippedImg.getPixelAt(0, 0));
    assertEquals(this.orange, vFlippedImg.getPixelAt(0, 1));
    assertEquals(this.blue, vFlippedImg.getPixelAt(0, 2));
    assertEquals(this.black, vFlippedImg.getPixelAt(1, 0));
    assertEquals(this.yellow, vFlippedImg.getPixelAt(1, 1));
    assertEquals(this.red, vFlippedImg.getPixelAt(1, 2));
  }

  @Test
  public void testBrightenInvalidIncrement() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "brighten meow colorfulImage brightenedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid arguments.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testBrightenInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "brighten 10 what brightenedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid arguments.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testBrightenPositive() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "brighten 10 colorfulImage brightenedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created brightenedImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage brightenedImg = this.model.saveImage("brightenedImage");

    assertEquals(new PixelImpl(10, 10, 10), brightenedImg.getPixelAt(0, 0));
    assertEquals(new PixelImpl(255, 255, 10), brightenedImg.getPixelAt(0, 1));
    assertEquals(new PixelImpl(255, 10, 10), brightenedImg.getPixelAt(0, 2));
    assertEquals(new PixelImpl(45, 45, 45), brightenedImg.getPixelAt(1, 0));
    assertEquals(new PixelImpl(255, 205, 78), brightenedImg.getPixelAt(1, 1));
    assertEquals(new PixelImpl(10, 10, 255), brightenedImg.getPixelAt(1, 2));
  }

  @Test
  public void testBrightenNegative() {
    this.in = new StringReader("load ./test/testColorfulImage.ppm colorfulImage\n" +
            "brighten -10 colorfulImage darkenedImage q");
    this.controller = constructController(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created darkenedImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage darkenedImg = this.model.saveImage("darkenedImage");

    assertEquals(new PixelImpl(0, 0, 0), darkenedImg.getPixelAt(0, 0));
    assertEquals(new PixelImpl(245, 245, 0), darkenedImg.getPixelAt(0, 1));
    assertEquals(new PixelImpl(245, 0, 0), darkenedImg.getPixelAt(0, 2));
    assertEquals(new PixelImpl(25, 25, 25), darkenedImg.getPixelAt(1, 0));
    assertEquals(new PixelImpl(245, 185, 58), darkenedImg.getPixelAt(1, 1));
    assertEquals(new PixelImpl(0, 0, 245), darkenedImg.getPixelAt(1, 2));
  }
}