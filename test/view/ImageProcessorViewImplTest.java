package view;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import model.image.PixelImpl;
import utils.PPMUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for View.
 */
public class ImageProcessorViewImplTest {
  ImageProcessorModel model;
  ImageProcessorView view;
  Appendable errorOut;
  Appendable messageOut;
  IImage image;
  List<List<IPixel>> imageList;
  List<IPixel> row1;
  List<IPixel> row2;
  IPixel black;
  IPixel yellow;
  IPixel red;
  IPixel grey;
  IPixel orange;
  IPixel blue;

  @Before
  public void setUp() {
    this.model = new ImageProcessorModelImpl();
    this.messageOut = new StringBuilder();
    this.errorOut = new IOExceptionAppendable();
    this.view = constructView(this.model, this.messageOut);

    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();

    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
  }

  protected ImageProcessorView constructView(ImageProcessorModel model) {
    return new ImageProcessorViewImpl(model);
  }

  protected ImageProcessorView constructView(ImageProcessorModel model, Appendable out) {
    return new ImageProcessorViewImpl(model, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullModelOneArgumentConstructor() {
    this.view = constructView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullModelTwoArgumentConstructor() {
    this.view = constructView(null, this.messageOut);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullMessageOutTwoArgumentConstructor() {
    this.view = constructView(this.model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toPPMInvalidImage() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toPPM("notColorfulImage", "./colorfulImage2.ppm");
  }

  @Test(expected = IllegalStateException.class)
  public void toPPMInvalidDestination() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toPPM("colorfulImage", "./meow/meow");
  }

  @Test(expected = IllegalArgumentException.class)
  public void toPPMNullTargetName() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toPPM(null, "./colorfulImage2.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void toPPMNullDestination() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toPPM("colorfulImage", null);
  }

  @Test
  public void toPPM() {
    this.model.loadImage(this.image, "colorfulImage");
    this.view.toPPM("colorfulImage", "./test/testImg.ppm");

    IImage fromPPM = PPMUtil.readPPM("./test/testImg.ppm");
    for (int row = 0; row < fromPPM.getHeight(); row += 1) {
      for (int col = 0; col < fromPPM.getWidth(); col += 1) {
        assertTrue(this.image.getPixelAt(row, col).equals(fromPPM.getPixelAt(row, col)));
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void renderMessageIOExceptionAppendable() {
    this.view = constructView(this.model, this.errorOut);
    this.view.renderMessage("meow");
  }

  @Test
  public void renderMessage() {
    this.view.renderMessage("hello");
    assertEquals("hello", this.messageOut.toString());
    this.view.renderMessage("how are you");
    assertEquals("hellohow are you", this.messageOut.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void renderMessageNull() {
    this.view.renderMessage(null);
  }
}