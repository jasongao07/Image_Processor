package model.image;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test for IImages.
 */
public class IImageTest {
  IImage image;
  List<List<IPixel>> imageList;
  List<IPixel> row1;
  List<IPixel> row2;
  IPixel black;
  IPixel yellow;
  IPixel blue;
  IPixel white;

  @Before
  public void setUp() {
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.blue = new PixelImpl(0, 0, 255);
    this.white = new PixelImpl(255, 255, 255);
    this.imageList = new ArrayList<>();
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNullImage() {
    this.image = new ImageImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullRows1() {
    this.row1.add(this.black);
    this.imageList.add(this.row1);
    this.imageList.add(null);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullRows2() {
    this.row1.add(this.black);
    this.imageList.add(null);
    this.imageList.add(this.row1);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullPixel1() {
    this.row1.add(this.black);
    this.row1.add(null);
    this.imageList.add(this.row1);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullPixel2() {
    this.row1.add(null);
    this.row1.add(this.black);
    this.imageList.add(this.row1);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullPixel3() {
    this.row1.add(this.yellow);
    this.row1.add(this.black);
    this.row2.add(null);
    this.row2.add(this.yellow);
    this.imageList.add(this.row1);
    this.imageList.add(this.row2);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageWithNullPixel4() {
    this.row1.add(this.yellow);
    this.row1.add(this.black);
    this.row2.add(this.blue);
    this.row2.add(null);
    this.imageList.add(this.row1);
    this.imageList.add(this.row2);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageNoRows() {
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructImageNoCols() {
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNonSquareImage() {
    Collections.addAll(this.row1, this.black, this.black, this.black, this.black);
    Collections.addAll(this.row2, this.black, this.black, this.black);

    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
  }

  @Test
  public void ConstructImageCheckAliasing() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);

    this.imageList.get(0).clear();

    for (int row = 0; row < 2; row += 1) {
      for (int col = 0; col < 4; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(this.image.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(this.image.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test
  public void getWidth() {
    Collections.addAll(this.row1, this.black, this.black, this.black, this.black);
    Collections.addAll(this.row2, this.black, this.black, this.black, this.black);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    assertEquals(4, this.image.getWidth());
  }

  @Test
  public void getHeight() {
    Collections.addAll(this.row1, this.black, this.black, this.black, this.black);
    Collections.addAll(this.row2, this.black, this.black, this.black, this.black);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    assertEquals(2, this.image.getHeight());
  }

  @Test
  public void getPixelAt() {
    Collections.addAll(this.row1, this.black, this.blue);
    Collections.addAll(this.row2, this.yellow, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    assertTrue(this.black.equals(this.image.getPixelAt(0, 0)));
    assertTrue(this.blue.equals(this.image.getPixelAt(0, 1)));
    assertTrue(this.yellow.equals(this.image.getPixelAt(1, 0)));
    assertTrue(this.white.equals(this.image.getPixelAt(1, 1)));
  }

  @Test
  public void getImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    List<List<IPixel>> imageCopy = this.image.getImage();

    for (int row = 0; row < imageCopy.size(); row += 1) {
      for (int col = 0; col < imageCopy.get(0).size(); col += 1) {
        assertTrue(imageCopy.get(row).get(col).equals(this.imageList.get(row).get(col)));
      }
    }
  }

  @Test
  public void testEquals() {
    Collections.addAll(this.row1, this.black, this.yellow);
    Collections.addAll(this.imageList, this.row1);

    List<List<IPixel>> imageList2 = new ArrayList<>();
    Collections.addAll(this.row2, this.black, this.yellow);
    Collections.addAll(imageList2, this.row2);

    this.image = new ImageImpl(this.imageList);
    IImage image2 = new ImageImpl(imageList2);

    assertTrue(this.image.equals(image2));
  }

  @Test
  public void testHashCode() {
    Collections.addAll(this.row1, this.black, this.yellow);
    Collections.addAll(this.imageList, this.row1);

    this.image = new ImageImpl(this.imageList);

    assertEquals(this.imageList.hashCode(), this.image.hashCode());
  }
}