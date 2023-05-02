package utils;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for CommonImageUtil.
 */
public class CommonImageUtilTest {
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
  BufferedImage bImage;
  Color blackColor;
  Color yellowColor;
  Color redColor;
  Color greyColor;
  Color orangeColor;
  Color blueColor;

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
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    this.bImage = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);
    this.blackColor = new Color(0, 0, 0);
    this.yellowColor = new Color(255, 255, 0);
    this.redColor = new Color(255, 0, 0);
    this.greyColor = new Color(35, 35, 35);
    this.orangeColor = new Color(255, 195, 68);
    this.blueColor = new Color(0, 0, 255);
    this.bImage.setRGB(0, 0, this.blackColor.getRGB());
    this.bImage.setRGB(1, 0, this.yellowColor.getRGB());
    this.bImage.setRGB(2, 0, this.redColor.getRGB());
    this.bImage.setRGB(0, 1, this.greyColor.getRGB());
    this.bImage.setRGB(1, 1, this.orangeColor.getRGB());
    this.bImage.setRGB(2, 1, this.blueColor.getRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void readImageNullFilename() {
    IImage readPNG = CommonImageUtil.readImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readImageNonExistentFilename() {
    IImage readPNG = CommonImageUtil.readImage("./test/geqiuralihfzd.jpg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readImageNonImageFile() {
    IImage readPNG = CommonImageUtil.readImage("./README.md");
  }

  @Test
  public void readPNG() {
    IImage readPNG = CommonImageUtil.readImage("./test/testColorfulImage.png");
    assertEquals(this.image, readPNG);
  }

  @Test
  public void readJPG() {
    IImage readImg = CommonImageUtil.readImage("./test/testColorfulImage.jpg");
    assertNotNull(readImg);
  }

  @Test
  public void readJPEG() {
    IImage readImg = CommonImageUtil.readImage("./test/testColorfulImage.jpeg");
    assertNotNull(readImg);
  }

  @Test
  public void readBMP() {
    IImage readImg = CommonImageUtil.readImage("./test/testColorfulImage.bmp");
    assertEquals(this.image, readImg);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageNullImage() {
    CommonImageUtil.writeImage(null, "./test/utils/testImg.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageNullDestination() {
    CommonImageUtil.writeImage(this.image, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageNonExistentDestination() {
    CommonImageUtil.writeImage(this.image, "./test/utils/what/testImg.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageNoPeriods1() {
    CommonImageUtil.writeImage(this.image, "testImgpng");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageNoPeriods2() {
    CommonImageUtil.writeImage(this.image, "jewaoifhaewoihf");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImagePPM() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageRandomFileFormat() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.woijefdklm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageEmptyDestinationString() {
    CommonImageUtil.writeImage(this.image, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeImageSinglePeriodDestinationString() {
    CommonImageUtil.writeImage(this.image, ".");
  }

  @Test
  public void writePNG() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.png");
    IImage readImg = CommonImageUtil.readImage("./test/utils/testImg.png");
    assertEquals(this.image, readImg);
  }

  @Test
  public void writeJPG() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.jpg");
    IImage readImg = CommonImageUtil.readImage("./test/utils/testImg.jpg");
    assertNotNull(readImg);
  }

  @Test
  public void writeJPEG() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.jpeg");
    IImage readImg = CommonImageUtil.readImage("./test/utils/testImg.jpeg");
    assertNotNull(readImg);
  }

  @Test
  public void writeBMP() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.bmp");
    IImage readImg = CommonImageUtil.readImage("./test/utils/testImg.bmp");
    assertEquals(this.image, readImg);
  }

  @Test
  public void writeImageMultiplePeriods() {
    CommonImageUtil.writeImage(this.image, "./test/utils/testImg.idk.png");
    IImage readImg = CommonImageUtil.readImage("./test/utils/testImg.png");
    assertEquals(this.image, readImg);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toBufferedImageNullIImage() {
    CommonImageUtil.toBufferedImage(null);
  }

  @Test
  public void testToBufferedImage() {
    BufferedImage fromImg = CommonImageUtil.toBufferedImage(this.image);
    assertEquals(this.blackColor.getRGB(), fromImg.getRGB(0,0));
    assertEquals(this.yellowColor.getRGB(), fromImg.getRGB(1,0));
    assertEquals(this.redColor.getRGB(), fromImg.getRGB(2,0));
    assertEquals(this.greyColor.getRGB(), fromImg.getRGB(0,1));
    assertEquals(this.orangeColor.getRGB(), fromImg.getRGB(1,1));
    assertEquals(this.blueColor.getRGB(), fromImg.getRGB(2,1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void toIImageNullImage() {
    CommonImageUtil.toIImage(null);
  }

  @Test
  public void testToIImage() {
    IImage fromImg = CommonImageUtil.toIImage(this.bImage);
    assertEquals(this.black, fromImg.getPixelAt(0,0));
    assertEquals(this.yellow, fromImg.getPixelAt(0,1));
    assertEquals(this.red, fromImg.getPixelAt(0,2));
    assertEquals(this.grey, fromImg.getPixelAt(1,0));
    assertEquals(this.orange, fromImg.getPixelAt(1,1));
    assertEquals(this.blue, fromImg.getPixelAt(1,2));
  }
}