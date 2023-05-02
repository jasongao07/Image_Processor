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
 * The sharpen filter test.
 */
public class SharpenFilterTest {
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
  List<IPixel> row4;
  List<IPixel> row5;
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
    this.row4 = new ArrayList<>();
    this.row5 = new ArrayList<>();
    Collections.addAll(this.row1, this.black, this.yellow, this.red, this.white, this.blue);
    Collections.addAll(this.row2, this.grey, this.orange, this.blue, this.black, this.turquoise);
    Collections.addAll(this.row3, this.white, this.turquoise, this.pink, this.yellow, this.grey);
    Collections.addAll(this.row4, this.pink, this.blue, this.white, this.red, this.red);
    Collections.addAll(this.row5, this.orange, this.orange, this.orange, this.black, this.white);
    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);
    this.filter = new SharpenFilter();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullImage() {
    IImage filtered = filter.filter(null);
  }

  @Test
  public void testFilter() {
    IImage filtered = filter.filter(this.image);
    assertEquals(filtered.getPixelAt(0, 0).getRed(), 43);
    assertEquals(filtered.getPixelAt(0, 1).getRed(), 255);
    assertEquals(filtered.getPixelAt(0, 2).getRed(), 255);
    assertEquals(filtered.getPixelAt(0, 3).getRed(), 192);
    assertEquals(filtered.getPixelAt(0, 4).getRed(), 0);
    assertEquals(filtered.getPixelAt(1, 0).getRed(), 110);
    assertEquals(filtered.getPixelAt(1, 1).getRed(), 255);
    assertEquals(filtered.getPixelAt(1, 2).getRed(), 212);
    assertEquals(filtered.getPixelAt(1, 3).getRed(), 98);
    assertEquals(filtered.getPixelAt(1, 4).getRed(), 0);
    assertEquals(filtered.getPixelAt(2, 0).getRed(), 167);
    assertEquals(filtered.getPixelAt(2, 1).getRed(), 69); // could be floating point error
    assertEquals(filtered.getPixelAt(2, 2).getRed(), 156);
    assertEquals(filtered.getPixelAt(2, 3).getRed(), 255);
    assertEquals(filtered.getPixelAt(2, 4).getRed(), 42);
    assertEquals(filtered.getPixelAt(3, 0).getRed(), 255);
    assertEquals(filtered.getPixelAt(3, 1).getRed(), 255);
    assertEquals(filtered.getPixelAt(3, 2).getRed(), 255);
    assertEquals(filtered.getPixelAt(3, 3).getRed(), 255);
    assertEquals(filtered.getPixelAt(3, 4).getRed(), 255);
    assertEquals(filtered.getPixelAt(4, 0).getRed(), 250); // could be floating point error
    assertEquals(filtered.getPixelAt(4, 1).getRed(), 255);
    assertEquals(filtered.getPixelAt(4, 2).getRed(), 225);
    assertEquals(filtered.getPixelAt(4, 3).getRed(), 221);
    assertEquals(filtered.getPixelAt(4, 4).getRed(), 255);

    assertEquals(filtered.getPixelAt(0, 0).getGreen(), 55);
    assertEquals(filtered.getPixelAt(0, 1).getGreen(), 183);
    assertEquals(filtered.getPixelAt(0, 2).getGreen(), 39);  // could be floating point error 38.5
    assertEquals(filtered.getPixelAt(0, 3).getGreen(), 191);
    assertEquals(filtered.getPixelAt(0, 4).getGreen(), 87);
    assertEquals(filtered.getPixelAt(1, 0).getGreen(), 236);
    assertEquals(filtered.getPixelAt(1, 1).getGreen(), 255);
    assertEquals(filtered.getPixelAt(1, 2).getGreen(), 202); // could be floating point error
    assertEquals(filtered.getPixelAt(1, 3).getGreen(), 85);
    assertEquals(filtered.getPixelAt(1, 4).getGreen(), 255);
    assertEquals(filtered.getPixelAt(2, 0).getGreen(), 241);
    assertEquals(filtered.getPixelAt(2, 1).getGreen(), 255);
    assertEquals(filtered.getPixelAt(2, 2).getGreen(), 17);
    assertEquals(filtered.getPixelAt(2, 3).getGreen(), 196); // could be floating point error
    assertEquals(filtered.getPixelAt(2, 4).getGreen(), 39); // could be floating point error
    assertEquals(filtered.getPixelAt(3, 0).getGreen(), 158);
    assertEquals(filtered.getPixelAt(3, 1).getGreen(), 255);
    assertEquals(filtered.getPixelAt(3, 2).getGreen(), 255);
    assertEquals(filtered.getPixelAt(3, 3).getGreen(), 143);
    assertEquals(filtered.getPixelAt(3, 4).getGreen(), 46);
    assertEquals(filtered.getPixelAt(4, 0).getGreen(), 127);
    assertEquals(filtered.getPixelAt(4, 1).getGreen(), 255);
    assertEquals(filtered.getPixelAt(4, 2).getGreen(), 146);
    assertEquals(filtered.getPixelAt(4, 3).getGreen(), 82);
    assertEquals(filtered.getPixelAt(4, 4).getGreen(), 160);

    assertEquals(filtered.getPixelAt(0, 0).getBlue(), 0);
    assertEquals(filtered.getPixelAt(0, 1).getBlue(), 0);
    assertEquals(filtered.getPixelAt(0, 2).getBlue(), 0);
    assertEquals(filtered.getPixelAt(0, 3).getBlue(), 255);
    assertEquals(filtered.getPixelAt(0, 4).getBlue(), 255);
    assertEquals(filtered.getPixelAt(1, 0).getBlue(), 11);
    assertEquals(filtered.getPixelAt(1, 1).getBlue(), 192);
    assertEquals(filtered.getPixelAt(1, 2).getBlue(), 255);
    assertEquals(filtered.getPixelAt(1, 3).getBlue(), 215);
    assertEquals(filtered.getPixelAt(1, 4).getBlue(), 252);
    assertEquals(filtered.getPixelAt(2, 0).getBlue(), 255);
    assertEquals(filtered.getPixelAt(2, 1).getBlue(), 255);
    assertEquals(filtered.getPixelAt(2, 2).getBlue(), 255);
    assertEquals(filtered.getPixelAt(2, 3).getBlue(), 71);
    assertEquals(filtered.getPixelAt(2, 4).getBlue(), 0);
    assertEquals(filtered.getPixelAt(3, 0).getBlue(), 255);
    assertEquals(filtered.getPixelAt(3, 1).getBlue(), 255);
    assertEquals(filtered.getPixelAt(3, 2).getBlue(), 255);
    assertEquals(filtered.getPixelAt(3, 3).getBlue(), 81);
    assertEquals(filtered.getPixelAt(3, 4).getBlue(), 0);
    assertEquals(filtered.getPixelAt(4, 0).getBlue(), 81);
    assertEquals(filtered.getPixelAt(4, 1).getBlue(), 202);
    assertEquals(filtered.getPixelAt(4, 2).getBlue(), 48);
    assertEquals(filtered.getPixelAt(4, 3).getBlue(), 43);
    assertEquals(filtered.getPixelAt(4, 4).getBlue(), 180);
  }
}