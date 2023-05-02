package model.colortransformation;

import org.junit.Before;
import org.junit.Test;

import model.image.IPixel;
import model.image.PixelImpl;

import static org.junit.Assert.assertEquals;

/**
 * The greyscale transformation test.
 */
public class GreyscaleTransformationTest {
  IColorTransformation transformation;
  IPixel black;
  IPixel yellow;
  IPixel red;
  IPixel grey;
  IPixel orange;
  IPixel blue;
  IPixel white;
  IPixel turquoise;
  IPixel pink;

  @Before
  public void setUp() {
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);
    this.white = new PixelImpl(255, 255, 255);
    this.turquoise = new PixelImpl(12, 250, 210);
    this.pink = new PixelImpl(223, 22, 245);
    this.transformation = new GreyscaleTransformation();
  }

  @Test(expected = IllegalArgumentException.class)
  public void transformNull() {
    this.transformation.transform(null);
  }

  @Test
  public void transform() {
    assertEquals(0, this.transformation.transform(this.black).getRed());
    assertEquals(0, this.transformation.transform(this.black).getGreen());
    assertEquals(0, this.transformation.transform(this.black).getBlue());

    assertEquals(237, this.transformation.transform(this.yellow).getRed());
    assertEquals(237, this.transformation.transform(this.yellow).getGreen());
    assertEquals(237, this.transformation.transform(this.yellow).getBlue());

    assertEquals(54, this.transformation.transform(this.red).getRed());
    assertEquals(54, this.transformation.transform(this.red).getGreen());
    assertEquals(54, this.transformation.transform(this.red).getBlue());

    assertEquals(35, this.transformation.transform(this.grey).getRed());
    assertEquals(35, this.transformation.transform(this.grey).getGreen());
    assertEquals(35, this.transformation.transform(this.grey).getBlue());

    assertEquals(199, this.transformation.transform(this.orange).getRed());
    assertEquals(199, this.transformation.transform(this.orange).getGreen());
    assertEquals(199, this.transformation.transform(this.orange).getBlue());

    assertEquals(18, this.transformation.transform(this.blue).getRed());
    assertEquals(18, this.transformation.transform(this.blue).getGreen());
    assertEquals(18, this.transformation.transform(this.blue).getBlue());

    assertEquals(255, this.transformation.transform(this.white).getRed());
    assertEquals(255, this.transformation.transform(this.white).getGreen());
    assertEquals(255, this.transformation.transform(this.white).getBlue());

    assertEquals(197, this.transformation.transform(this.turquoise).getRed());
    assertEquals(197, this.transformation.transform(this.turquoise).getGreen());
    assertEquals(197, this.transformation.transform(this.turquoise).getBlue());

    assertEquals(81, this.transformation.transform(this.pink).getRed());
    assertEquals(81, this.transformation.transform(this.pink).getGreen());
    assertEquals(81, this.transformation.transform(this.pink).getBlue());
  }
}