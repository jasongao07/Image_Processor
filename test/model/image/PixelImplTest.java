package model.image;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Test class for PixelImpl.
 */
public class PixelImplTest {
  IPixel redPixel;
  IPixel greyPixel;
  IPixel orangePixel;

  @Before
  public void setUp() {
    redPixel = new PixelImpl(255, 0, 0);
    greyPixel = new PixelImpl(35, 35, 35);
    orangePixel = new PixelImpl(255, 195, 68);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelNegativeRed() {
    redPixel = new PixelImpl(-1, 30, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelNegativeGreen() {
    redPixel = new PixelImpl(30, -5, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelNegativeBlue() {
    redPixel = new PixelImpl(1, 30, -300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelRedTooLarge() {
    redPixel = new PixelImpl(256, 30, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelGreenTooLarge() {
    redPixel = new PixelImpl(20, 727, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPixelBlueTooLarge() {
    redPixel = new PixelImpl(20, 30, 9001);
  }

  @Test
  public void getRed() {
    assertEquals(255, this.redPixel.getRed());
    assertEquals(35, this.greyPixel.getRed());
    assertEquals(255, this.orangePixel.getRed());
  }

  @Test
  public void getGreen() {
    assertEquals(0, this.redPixel.getGreen());
    assertEquals(35, this.greyPixel.getGreen());
    assertEquals(195, this.orangePixel.getGreen());
  }

  @Test
  public void getBlue() {
    assertEquals(0, this.redPixel.getBlue());
    assertEquals(35, this.greyPixel.getBlue());
    assertEquals(68, this.orangePixel.getBlue());
  }

  @Test
  public void getValue() {
    assertEquals(255, this.redPixel.getValue());
    assertEquals(35, this.greyPixel.getValue());
    assertEquals(255, this.orangePixel.getValue());
  }

  @Test
  public void getIntensity() {
    assertEquals(85, this.redPixel.getIntensity());
    assertEquals(35, this.greyPixel.getIntensity());
    assertEquals(173, this.orangePixel.getIntensity());
  }

  @Test
  public void getLuma() {
    assertEquals(54, this.redPixel.getLuma());
    assertEquals(35, this.greyPixel.getLuma());
    assertEquals(199, this.orangePixel.getLuma());
  }

  @Test
  public void testEquals() {
    IPixel redPixel2 = new PixelImpl(255, 0, 0);
    IPixel greenPixel = new PixelImpl(0, 255, 0);
    assertTrue(this.redPixel.equals(this.redPixel));
    assertTrue(this.redPixel.equals(redPixel2));
    assertFalse(this.redPixel.equals(greenPixel));
    assertFalse(this.redPixel.equals(this.greyPixel));
    assertFalse(this.redPixel.equals(this.orangePixel));
  }

  @Test
  public void testHashCode() {
    assertEquals(Integer.hashCode(255) + Integer.hashCode(0) + Integer.hashCode(0),
            this.redPixel.hashCode());
    assertEquals(Integer.hashCode(35) + Integer.hashCode(35) + Integer.hashCode(35),
            this.greyPixel.hashCode());
    assertEquals(Integer.hashCode(255) + Integer.hashCode(195) + Integer.hashCode(68),
            this.orangePixel.hashCode());

  }
}