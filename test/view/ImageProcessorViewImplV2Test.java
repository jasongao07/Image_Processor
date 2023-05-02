package view;

import org.junit.Before;
import org.junit.Test;

import model.ImageProcessorModel;
import model.image.IImage;
import utils.CommonImageUtil;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for view v2.
 */
public class ImageProcessorViewImplV2Test extends ImageProcessorViewImplTest {
  ImageProcessorViewV2 view;

  @Before
  public void setUp() {
    super.setUp();
    this.view = new ImageProcessorViewImplV2(this.model, this.messageOut);
  }

  protected ImageProcessorView constructView(ImageProcessorModel model) {
    return new ImageProcessorViewImplV2(model);
  }

  protected ImageProcessorView constructView(ImageProcessorModel model, Appendable out) {
    return new ImageProcessorViewImplV2(model, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toImageInvalidImage() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("notColorfulImage", "./colorfulImage2.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void toImageInvalidDestination() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", "./meow/meow");
  }

  @Test(expected = IllegalArgumentException.class)
  public void toImageNullTargetName() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage(null, "./colorfulImage2.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void toImageNullDestination() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", null);
  }

  @Test
  public void toImagePNG() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", "./test/testImg.png");

    IImage fromImage = CommonImageUtil.readImage("./test/testImg.png");
    for (int row = 0; row < fromImage.getHeight(); row += 1) {
      for (int col = 0; col < fromImage.getWidth(); col += 1) {
        assertTrue(this.image.getPixelAt(row, col).equals(fromImage.getPixelAt(row, col)));
      }
    }
  }

  @Test
  public void toImageJPG() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", "./test/testImg.jpg");

    IImage fromImage = CommonImageUtil.readImage("./test/testImg.jpg");
    assertNotNull(fromImage);
  }

  @Test
  public void toImageJPEG() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", "./test/testImg.jpeg");

    IImage fromImage = CommonImageUtil.readImage("./test/testImg.jpeg");
    assertNotNull(fromImage);
  }

  @Test
  public void toImageBMP() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toImage("colorfulImage", "./test/testImg.bmp");

    IImage fromImage = CommonImageUtil.readImage("./test/testImg.bmp");
    for (int row = 0; row < fromImage.getHeight(); row += 1) {
      for (int col = 0; col < fromImage.getWidth(); col += 1) {
        assertTrue(this.image.getPixelAt(row, col).equals(fromImage.getPixelAt(row, col)));
      }
    }
  }
}