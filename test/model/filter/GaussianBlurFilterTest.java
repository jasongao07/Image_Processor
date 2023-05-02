package model.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.image.IImage;
import model.image.ImageImpl;
import model.image.IPixel;
import model.image.PixelImpl;

import static org.junit.Assert.assertEquals;

/**
 * The gaussian blur filter tests.
 */
public class GaussianBlurFilterTest {
  IFilter filter;
  IPixel black;
  IPixel yellow;
  IPixel red;
  IPixel grey;
  IPixel orange;
  IPixel blue;
  IPixel white;
  IPixel turquoise;
  IPixel pink;
  List<IPixel> row1;
  List<IPixel> row2;
  List<IPixel> row3;
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
    this.white = new PixelImpl(255, 255, 255);
    this.turquoise = new PixelImpl(12, 250, 210);
    this.pink = new PixelImpl(223, 22, 245);
    this.imageList = new ArrayList<>();
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    Collections.addAll(this.row1, this.black, this.yellow, this.red);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);
    this.image = new ImageImpl(this.imageList);
    this.filter = new GaussianBlurFilter();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFilterNullImage() {
    IImage filtered = filter.filter(null);
  }

  @Test
  public void testFilter() {
    IImage filtered = filter.filter(this.image);
    assertEquals(filtered.getPixelAt(0, 0).getRed(), 52);
    assertEquals(filtered.getPixelAt(0, 1).getRed(), 130);
    assertEquals(filtered.getPixelAt(0, 2).getRed(), 112);
    assertEquals(filtered.getPixelAt(1, 0).getRed(), 89);
    assertEquals(filtered.getPixelAt(1, 1).getRed(), 147);
    assertEquals(filtered.getPixelAt(1, 2).getRed(), 108);
    assertEquals(filtered.getPixelAt(2, 0).getRed(), 86);
    assertEquals(filtered.getPixelAt(2, 1).getRed(), 97);
    assertEquals(filtered.getPixelAt(2, 2).getRed(), 73);

    assertEquals(filtered.getPixelAt(0, 0).getGreen(), 48);
    assertEquals(filtered.getPixelAt(0, 1).getGreen(), 90);
    assertEquals(filtered.getPixelAt(0, 2).getGreen(), 44);
    assertEquals(filtered.getPixelAt(1, 0).getGreen(), 97);
    assertEquals(filtered.getPixelAt(1, 1).getGreen(), 134);
    assertEquals(filtered.getPixelAt(1, 2).getGreen(), 59);
    assertEquals(filtered.getPixelAt(2, 0).getGreen(), 112);
    assertEquals(filtered.getPixelAt(2, 1).getGreen(), 124);
    assertEquals(filtered.getPixelAt(2, 2).getGreen(), 49);

    assertEquals(filtered.getPixelAt(0, 0).getBlue(), 9);
    assertEquals(filtered.getPixelAt(0, 1).getBlue(), 27);
    assertEquals(filtered.getPixelAt(0, 2).getBlue(), 36);
    assertEquals(filtered.getPixelAt(1, 0).getBlue(), 62);
    assertEquals(filtered.getPixelAt(1, 1).getBlue(), 111);
    assertEquals(filtered.getPixelAt(1, 2).getBlue(), 116);
    assertEquals(filtered.getPixelAt(2, 0).getBlue(), 99);
    assertEquals(filtered.getPixelAt(2, 1).getBlue(), 142);
    assertEquals(filtered.getPixelAt(2, 2).getBlue(), 124);
  }
}