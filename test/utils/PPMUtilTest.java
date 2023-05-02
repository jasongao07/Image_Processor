package utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for ImageUtils.
 */
public class PPMUtilTest {
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

  @Before
  public void setUp() {
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMNullFilename() {
    this.image = PPMUtil.readPPM(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMNonExistentFilename() {
    this.image = PPMUtil.readPPM("fjioawefhiewalfhizfvj;odjiv;fh");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMNotPPMFile() {
    this.image = PPMUtil.readPPM("./README.md");
  }

  @Test
  public void readPPM() {
    // Black Yellow Red
    // Grey  Orange Blue
    this.image = PPMUtil.readPPM("./test/testColorfulImage.ppm");
    assertTrue(this.black.equals(this.image.getPixelAt(0, 0)));
    assertTrue(this.yellow.equals(this.image.getPixelAt(0, 1)));
    assertTrue(this.red.equals(this.image.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(this.image.getPixelAt(1, 0)));
    assertTrue(this.orange.equals(this.image.getPixelAt(1, 1)));
    assertTrue(this.blue.equals(this.image.getPixelAt(1, 2)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void writePPMNullImage() {
    PPMUtil.writePPM(null);
  }

  @Test
  public void writePPM() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);

    assertEquals(
            "P3\n" +
                    "3 2\n" +
                    "255\n" +
                    "0\n0\n0\n" +
                    "255\n255\n0\n" +
                    "255\n0\n0\n" +
                    "35\n35\n35\n" +
                    "255\n195\n68\n" +
                    "0\n0\n255\n",
            PPMUtil.writePPM(this.image));
  }

  @Test
  public void writePPMFile() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl(model, new StringBuilder());
    model.loadImage(this.image, "colorfulImage");
    view.toPPM("colorfulImage", "./test/testImg.ppm");

    IImage fromPPM = PPMUtil.readPPM("./test/testImg.ppm");
    for (int row = 0; row < fromPPM.getHeight(); row += 1) {
      for (int col = 0; col < fromPPM.getWidth(); col += 1) {
        assertTrue(this.image.getPixelAt(row, col).equals(fromPPM.getPixelAt(row, col)));
      }
    }
  }
}