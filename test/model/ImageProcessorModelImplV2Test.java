package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;

import static org.junit.Assert.assertEquals;

/**
 * The tests for modelV2.
 */
public class ImageProcessorModelImplV2Test extends ImageProcessorModelImplTest {
  ImageProcessorModelV2 modelV2;
  IPixel turquoise;
  IPixel pink;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    this.modelV2 = new ImageProcessorModelImplV2();
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);
    this.white = new PixelImpl(255, 255, 255);
    this.turquoise = new PixelImpl(12, 250, 210);
    this.pink = new PixelImpl(223, 22, 245);
  }

  @Override
  protected ImageProcessorModel constructModel() {
    return new ImageProcessorModelImplV2();
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNullTransformation() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation(null, "3x3", "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNullTargetName() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("greyscale", null, "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNullName() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("greyscale", "3x3", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNonExistentTargetImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("greyscale", "aioejwfois", "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNonExistentTransformation() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("wahoifedns", "3x3", "greyscale3x3");
  }

  @Test
  public void applyGreyscale() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("greyscale", "3x3", "greyscale3x3");
    IImage outImage = this.modelV2.saveImage("greyscale3x3");

    IPixel blackGreyscale = new PixelImpl(0,0,0);
    IPixel yellowGreyscale = new PixelImpl(237,237,237);
    IPixel redGreyscale = new PixelImpl(54,54,54);
    IPixel greyGreyscale = new PixelImpl(35,35,35);
    IPixel orangeGreyscale = new PixelImpl(199,199,199);
    IPixel blueGreyscale = new PixelImpl(18,18,18);
    IPixel whiteGreyscale = new PixelImpl(255, 255,255);
    IPixel turquoiseGreyscale = new PixelImpl(197,197,197);
    IPixel pinkGreyscale = new PixelImpl(81,81,81);
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackGreyscale, yellowGreyscale, redGreyscale);
    Collections.addAll(this.row2, greyGreyscale, orangeGreyscale, blueGreyscale);
    Collections.addAll(this.row3, whiteGreyscale, turquoiseGreyscale, pinkGreyscale);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);

    assertEquals(outImage, this.image);
  }

  @Test
  public void applySepia() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyColorTransformation("sepia", "3x3", "sepia3x3");
    IImage outImage = this.modelV2.saveImage("sepia3x3");

    IPixel blackSepia = new PixelImpl(0,0,0);
    IPixel yellowSepia = new PixelImpl(255, 255, 206);
    IPixel redSepia = new PixelImpl(100, 89, 69);
    IPixel greySepia = new PixelImpl(47, 42, 33);
    IPixel orangeSepia = new PixelImpl(255,234,182);
    IPixel blueSepia = new PixelImpl(48,43,33);
    IPixel whiteSepia = new PixelImpl(255,255,239);
    IPixel turquoiseSepia = new PixelImpl(237,211,164);
    IPixel pinkSepia = new PixelImpl(151,134,104);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackSepia, yellowSepia, redSepia);
    Collections.addAll(this.row2, greySepia, orangeSepia, blueSepia);
    Collections.addAll(this.row3, whiteSepia, turquoiseSepia, pinkSepia);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);

    assertEquals(outImage, this.image);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNullTransformation() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter(null, "3x3", "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNullTargetName() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter("blur", null, "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNullName() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter("blur", "3x3", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNonExistentTargetImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter("blur", "aioejwfois", "greyscale3x3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterNonExistentTransformation() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter("wahoifedns", "3x3", "greyscale3x3");
  }

  @Test
  public void applyBlur() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "3x3");
    this.modelV2.applyFilter("blur", "3x3", "blur3x3");
    IImage outImage = this.modelV2.saveImage("blur3x3");

    IPixel blackBlur = new PixelImpl(52,48,9);
    IPixel yellowBlur = new PixelImpl(130,90,27);
    IPixel redBlur = new PixelImpl(112,44,36);
    IPixel greyBlur = new PixelImpl(89,97,62);
    IPixel orangeBlur = new PixelImpl(147,134,111);
    IPixel blueBlur = new PixelImpl(108,59,116);
    IPixel whiteBlur = new PixelImpl(86,112,99);
    IPixel turquoiseBlur = new PixelImpl(97,124,142);
    IPixel pinkBlur = new PixelImpl(73,49,124);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, blackBlur, yellowBlur, redBlur);
    Collections.addAll(this.row2, greyBlur, orangeBlur, blueBlur);
    Collections.addAll(this.row3, whiteBlur, turquoiseBlur, pinkBlur);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);

    assertEquals(outImage, this.image);
  }

  @Test
  public void applySharpening() {
    Collections.addAll(this.row1, this.black, this.yellow, this.red, this.white, this.blue);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue, this.black, this.turquoise);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink, this.yellow, this.grey);
    Collections.addAll(this.row4, this.pink, this.blue, this.white, this.red, this.red);
    Collections.addAll(this.row5, this.orange, this.orange, this.orange, this.black, this.white);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);
    this.modelV2.loadImage(this.image, "5x5");
    this.modelV2.applyFilter("sharpen", "5x5", "sharpen5x5");
    IImage outImage = this.modelV2.saveImage("sharpen5x5");

    IPixel row0col0 = new PixelImpl(43,55,0);
    IPixel row0col1 = new PixelImpl(255,183,0);
    IPixel row0col2 = new PixelImpl(255,39,0);
    IPixel row0col3 = new PixelImpl(192,191,255);
    IPixel row0col4 = new PixelImpl(0,87,255);
    IPixel row1col0 = new PixelImpl(110,236,11);
    IPixel row1col1 = new PixelImpl(255,255,192);
    IPixel row1col2 = new PixelImpl(212,202,255);
    IPixel row1col3 = new PixelImpl(98,85,215);
    IPixel row1col4 = new PixelImpl(0,255,252);
    IPixel row2col0 = new PixelImpl(167,241,255);
    IPixel row2col1 = new PixelImpl(69,255,255);
    IPixel row2col2 = new PixelImpl(156,17,255);
    IPixel row2col3 = new PixelImpl(255,196,71);
    IPixel row2col4 = new PixelImpl(42,39,0);
    IPixel row3col0 = new PixelImpl(255,158,255);
    IPixel row3col1 = new PixelImpl(255,255,255);
    IPixel row3col2 = new PixelImpl(255,255,255);
    IPixel row3col3 = new PixelImpl(255,143,81);
    IPixel row3col4 = new PixelImpl(255,46,0);
    IPixel row4col0 = new PixelImpl(250,127,81);
    IPixel row4col1 = new PixelImpl(255,255,202);
    IPixel row4col2 = new PixelImpl(225,146,48);
    IPixel row4col3 = new PixelImpl(221,82,43);
    IPixel row4col4 = new PixelImpl(255,160,180);

    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    this.row4 = new ArrayList<>();
    this.row5 = new ArrayList<>();
    this.imageList = new ArrayList<>();
    Collections.addAll(this.row1, row0col0,row0col1,row0col2,row0col3,row0col4);
    Collections.addAll(this.row2, row1col0,row1col1,row1col2,row1col3,row1col4);
    Collections.addAll(this.row3, row2col0,row2col1,row2col2,row2col3,row2col4);
    Collections.addAll(this.row4, row3col0,row3col1,row3col2,row3col3,row3col4);
    Collections.addAll(this.row5, row4col0,row4col1,row4col2,row4col3,row4col4);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);

    assertEquals(outImage, this.image);
  }
}