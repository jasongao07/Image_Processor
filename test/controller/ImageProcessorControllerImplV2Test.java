package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import model.ImageProcessorModel;
import model.ImageProcessorModelImplV2;
import model.ImageProcessorModelV2;
import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;
import utils.CommonImageUtil;
import view.ImageProcessorView;
import view.ImageProcessorViewImplV2;
import view.ImageProcessorViewV2;

import static org.junit.Assert.assertEquals;

/**
 * Test class for controllerv2.
 */
public class ImageProcessorControllerImplV2Test extends ImageProcessorControllerImplTest {
  ImageProcessorModelV2 model;
  ImageProcessorViewV2 view;
  ImageProcessorController controller;
  int lastWelcomeMessageIdx;

  @Before
  public void setUp() {
    // test/testColorfulImage.png:
    // Black Yellow Red
    // Grey  Orange Blue
    super.setUp();
    this.model = new ImageProcessorModelImplV2();
    this.view = new ImageProcessorViewImplV2(this.model, this.out);
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);

    ImageProcessorModelV2 model2 = new ImageProcessorModelImplV2();
    Appendable out2 = new StringBuilder();
    ImageProcessorViewV2 view2 = new ImageProcessorViewImplV2(model2, out2);
    Readable in2 = new StringReader("q");
    ImageProcessorController controller2 = constructController(in2, model2, view2);
    controller2.run();
    String[] msgs = out2.toString().split("\n");
    this.lastWelcomeMessageIdx = msgs.length - 2;
  }

  protected ImageProcessorModel constructModel() {
    return new ImageProcessorModelImplV2();
  }

  protected ImageProcessorView constructView(ImageProcessorModel model, Appendable out) {
    return new ImageProcessorViewImplV2(model, out);
  }

  protected ImageProcessorController constructController(Readable in,
                                                         ImageProcessorModelV2 model,
                                                         ImageProcessorViewV2 view) {
    return new ImageProcessorControllerImplV2(in, model, view);
  }

  @Override
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
    assertEquals("blur image-name dest-image-name", output[15]);
    assertEquals("blurs an image using Gaussian blur to create a new image, referred to " +
            "henceforth by the given destination name.", output[16]);
    assertEquals("sharpen image-name dest-image-name", output[17]);
    assertEquals("Sharpens an image to create a new image, referred to henceforth by the" +
            " given destination name.", output[18]);
    assertEquals("sepia image-name dest-image-name", output[19]);
    assertEquals("Transforms an image by using a Sepia transformation to create a new image, " +
            "referred to henceforth by the given destination name.", output[20]);
    assertEquals("greyscale image-name dest-image-name", output[21]);
    assertEquals("Transforms an image by using a greyscale " +
            "transformation to create a new image, " +
            "referred to henceforth by the given destination name.", output[22]);
  }

  @Test
  public void testRunOutOfArgument() {
    this.in = new StringReader("load ./README.md");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Scanner ran out of inputs", output[this.lastWelcomeMessageIdx + 1]);
  }

  @Override
  @Test
  public void testNonPPMImage() {
    this.in = new StringReader("load ./README.md colorfulImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("not a valid image file", output[this.lastWelcomeMessageIdx + 1]);
  }

  @Test
  public void testLoadPNG() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Image successfully loaded as colorfulImage",
            output[this.lastWelcomeMessageIdx + 1]);
    IImage img = this.model.saveImage("colorfulImage");
    assertEquals(this.image, img);
  }

  @Test
  public void testSaveImagePNG() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "save ./testImage.png colorfulImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully saved colorfulImage to ./testImage.png",
            output[this.lastWelcomeMessageIdx + 2]);
    IImage img = CommonImageUtil.readImage("./testImage.png");
    assertEquals(img, this.image);
  }

  @Test
  public void testGreyscaleInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "greyscale somethingImage greyscaleImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testGreyscale() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "greyscale colorfulImage greyscaleImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created greyscaleImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage greyscaleImage = this.model.saveImage("greyscaleImage");

    IPixel blackGreyscale = new PixelImpl(0, 0, 0);
    IPixel yellowGreyscale = new PixelImpl(237, 237, 237);
    IPixel redGreyscale = new PixelImpl(54, 54, 54);
    IPixel greyGreyscale = new PixelImpl(35, 35, 35);
    IPixel orangeGreyscale = new PixelImpl(199, 199, 199);
    IPixel blueGreyscale = new PixelImpl(18, 18, 18);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackGreyscale, yellowGreyscale, redGreyscale);
    Collections.addAll(this.row2, greyGreyscale, orangeGreyscale, blueGreyscale);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    assertEquals(greyscaleImage, this.image);
  }

  @Test
  public void testSepiaInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "sepia somethingImage sepiaImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testSepia() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "sepia colorfulImage sepiaImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created sepiaImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage sepiaImage = this.model.saveImage("sepiaImage");

    IPixel blackSepia = new PixelImpl(0, 0, 0);
    IPixel yellowSepia = new PixelImpl(255, 255, 206);
    IPixel redSepia = new PixelImpl(100, 89, 69);
    IPixel greySepia = new PixelImpl(47, 42, 33);
    IPixel orangeSepia = new PixelImpl(255, 234, 182);
    IPixel blueSepia = new PixelImpl(48, 43, 33);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackSepia, yellowSepia, redSepia);
    Collections.addAll(this.row2, greySepia, orangeSepia, blueSepia);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    assertEquals(sepiaImage, this.image);
  }

  @Test
  public void testBlurInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "blur somethingImage blurImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testBlur() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "blur colorfulImage blurImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created blurImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage blurImage = this.model.saveImage("blurImage");

    IPixel blackBlur = new PixelImpl(52, 48, 9);
    IPixel yellowBlur = new PixelImpl(130, 90, 27);
    IPixel redBlur = new PixelImpl(112, 44, 36);
    IPixel greyBlur = new PixelImpl(57, 49, 17);
    IPixel orangeBlur = new PixelImpl(116, 85, 53);
    IPixel blueBlur = new PixelImpl(80, 40, 72);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackBlur, yellowBlur, redBlur);
    Collections.addAll(this.row2, greyBlur, orangeBlur, blueBlur);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    assertEquals(blurImage, this.image);
  }

  @Test
  public void testSharpenInvalidImageName() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "sharpen somethingImage sharpenImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Invalid image name.", output[this.lastWelcomeMessageIdx + 2]);
  }

  @Test
  public void testSharpen() {
    this.in = new StringReader("load ./test/testColorfulImage.png colorfulImage\n" +
            "sharpen colorfulImage sharpenImage q");
    this.controller = new ImageProcessorControllerImplV2(this.in, this.model, this.view);
    this.controller.run();
    String[] output = this.out.toString().split("\n");
    assertEquals("Successfully created sharpenImage", output[this.lastWelcomeMessageIdx + 2]);
    IImage sharpenImage = this.model.saveImage("sharpenImage");

    IPixel blackSharpen = new PixelImpl(104, 121, 0);
    IPixel yellowSharpen = new PixelImpl(255, 255, 90);
    IPixel redSharpen = new PixelImpl(255, 108, 76);
    IPixel greySharpen = new PixelImpl(131, 148, 20);
    IPixel orangeSharpen = new PixelImpl(255, 255, 141);
    IPixel blueSharpen = new PixelImpl(187, 108, 255);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackSharpen, yellowSharpen, redSharpen);
    Collections.addAll(this.row2, greySharpen, orangeSharpen, blueSharpen);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    assertEquals(sharpenImage, this.image);
  }
}