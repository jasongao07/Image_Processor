package histogram;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.image.IImage;
import model.image.IPixel;
import model.image.ImageImpl;
import model.image.PixelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the histogram drawer impl.
 */
public class HistogramDrawerImplTest {
  List<List<IPixel>> imageList;
  IPixel black;
  IPixel yellow;
  IPixel red;
  IPixel grey;
  IPixel orange;
  IPixel blue;
  IPixel white;
  List<IPixel> row1;
  List<IPixel> row2;
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
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.imageList = new ArrayList<>();

  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeWidth() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(-1, 31);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeHeight() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(21, -31);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNullRedLines() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(10, 10);
    histogramDrawer.getRedLines(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNullGreenLines() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(10, 10);
    histogramDrawer.getGreenLines(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNullBlueLines() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(10, 10);
    histogramDrawer.getBlueLines(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNullIntensityLines() {
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(10, 10);
    histogramDrawer.getIntensityLines(null);
  }

  @Test
  public void getRedLines() {
    Collections.addAll(this.row1, this.black, this.black, this.red);
    Collections.addAll(this.row2, this.white, this.black, this.grey);
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(255, 255);

    assertEquals(new Line2D.Double(0, 63.75, 1, 255).x1,
            histogramDrawer.getRedLines(this.image).get(0).getX1(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255).x2,
            histogramDrawer.getRedLines(this.image).get(0).getX2(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255).y1,
            histogramDrawer.getRedLines(this.image).get(0).getY1(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255).y2,
            histogramDrawer.getRedLines(this.image).get(0).getY2(), 0.01);

    for (int i = 1; i < 254; i++) {
      if (i == 34) {
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX1(), new Line2D.Double(34
                , 255.0, 35, 191.25).x1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX2(), new Line2D.Double(34
                , 255.0, 35, 191.25).x2, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY1(), new Line2D.Double(34
                , 255.0, 35, 191.25).y1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY2(), new Line2D.Double(34
                , 255.0, 35, 191.25).y2, 0.01);
      } else if (i == 35) {
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX1(), new Line2D.Double(35
                , 191.25, 36, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX2(), new Line2D.Double(35
                , 191.25, 36, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY1(), new Line2D.Double(35
                , 191.25, 36, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY2(), new Line2D.Double(35
                , 191.25, 36, 255.0).y2, 0.01);
      } else {
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX1(), new Line2D.Double(i
                , 255., i + 1, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getX2(), new Line2D.Double(i
                , 255., i + 1, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY1(), new Line2D.Double(i
                , 255., i + 1, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getRedLines(this.image).get(i).getY2(), new Line2D.Double(i
                , 255., i + 1, 255.0).y2, 0.01);
      }
    }

    assertEquals(new Line2D.Double(254, 255.0, 255, 127.5).x1,
            histogramDrawer.getRedLines(this.image).get(254).getX1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 127.5).x2,
            histogramDrawer.getRedLines(this.image).get(254).getX2(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 127.5).y1,
            histogramDrawer.getRedLines(this.image).get(254).getY1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 127.5).y2,
            histogramDrawer.getRedLines(this.image).get(254).getY2(), 0.01);
  }

  @Test
  public void getGreenLines() {
    Collections.addAll(this.row1, this.black, this.black, this.red);
    Collections.addAll(this.row2, this.white, this.black, new PixelImpl(1, 1, 1));
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(255, 255);

    assertEquals(new Line2D.Double(0, 0, 1, 191.25).x1,
            histogramDrawer.getGreenLines(this.image).get(0).getX1(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).x2,
            histogramDrawer.getGreenLines(this.image).get(0).getX2(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).y1,
            histogramDrawer.getGreenLines(this.image).get(0).getY1(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).y2,
            histogramDrawer.getGreenLines(this.image).get(0).getY2(), 0.01);

    for (int i = 1; i < 254; i++) {
      if (i == 1) {
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getX1(), new Line2D.Double(1
                , 191.25, 2, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getX2(), new Line2D.Double(1
                , 191.25, 2, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getY1(), new Line2D.Double(1
                , 191.25, 2, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getY2(), new Line2D.Double(1
                , 191.25, 2, 255.0).y2, 0.01);
      } else {
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getX1(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getX2(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getY1(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getGreenLines(this.image).get(i).getY2(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).y2, 0.01);
      }
    }

    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x1,
            histogramDrawer.getGreenLines(this.image).get(254).getX1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x2,
            histogramDrawer.getGreenLines(this.image).get(254).getX2(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y1,
            histogramDrawer.getGreenLines(this.image).get(254).getY1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y2,
            histogramDrawer.getGreenLines(this.image).get(254).getY2(), 0.01);
  }

  @Test
  public void getBlueLines() {
    Collections.addAll(this.row1, this.black, this.black, this.red);
    Collections.addAll(this.row2, this.white, this.black, new PixelImpl(1, 1, 1));
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(255, 255);

    assertEquals(new Line2D.Double(0, 0, 1, 191.25).x1,
            histogramDrawer.getBlueLines(this.image).get(0).getX1(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).x2,
            histogramDrawer.getBlueLines(this.image).get(0).getX2(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).y1,
            histogramDrawer.getBlueLines(this.image).get(0).getY1(), 0.01);
    assertEquals(new Line2D.Double(0, 0, 1, 191.25).y2,
            histogramDrawer.getBlueLines(this.image).get(0).getY2(), 0.01);

    for (int i = 1; i < 254; i++) {
      if (i == 1) {
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getX1(), new Line2D.Double(1
                , 191.25, 2, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getX2(), new Line2D.Double(1
                , 191.25, 2, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getY1(), new Line2D.Double(1
                , 191.25, 2, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getY2(), new Line2D.Double(1
                , 191.25, 2, 255.0).y2, 0.01);
      } else {
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getX1(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getX2(), new Line2D.Double(i
                , 255.0, i + 1, 255.00).x2, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getY1(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getBlueLines(this.image).get(i).getY2(), new Line2D.Double(i
                , 255.0, i + 1, 255.0).y2, 0.01);
      }
    }

    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x1,
            histogramDrawer.getBlueLines(this.image).get(254).getX1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x2,
            histogramDrawer.getBlueLines(this.image).get(254).getX2(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y1,
            histogramDrawer.getBlueLines(this.image).get(254).getY1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y2,
            histogramDrawer.getBlueLines(this.image).get(254).getY2(), 0.01);
  }

  @Test
  public void getIntensityLines() {
    Collections.addAll(this.row1, this.black, this.black, this.red);
    Collections.addAll(this.row2, this.white, this.black, new PixelImpl(85, 85, 85));
    Collections.addAll(this.imageList, this.row1, this.row2);
    this.image = new ImageImpl(this.imageList);
    IHistogramDrawer histogramDrawer = new HistogramDrawerImpl(255, 255);

    assertEquals(new Line2D.Double(0, 63.75, 1, 255.0).x1,
            histogramDrawer.getIntensityLines(this.image).get(0).getX1(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255.0).x2,
            histogramDrawer.getIntensityLines(this.image).get(0).getX2(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255.0).y1,
            histogramDrawer.getIntensityLines(this.image).get(0).getY1(), 0.01);
    assertEquals(new Line2D.Double(0, 63.75, 1, 255.0).y2,
            histogramDrawer.getIntensityLines(this.image).get(0).getY2(), 0.01);

    for (int i = 1; i < 254; i++) {
      if (i == 84) {
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX1(),
                new Line2D.Double(84
                        , 255.0, 85, 127.5).x1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX2(),
                new Line2D.Double(84
                        , 255.0, 85, 127.5).x2, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY1(),
                new Line2D.Double(84
                        , 255.0, 85, 127.5).y1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY2(),
                new Line2D.Double(84
                        , 255.0, 85, 127.5).y2, 0.01);
      } else if (i == 85) {
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX1(),
                new Line2D.Double(85
                        , 127.5, 86, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX2(),
                new Line2D.Double(85
                        , 127.5, 86, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY1(),
                new Line2D.Double(85
                        , 127.5, 86, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY2(),
                new Line2D.Double(85
                        , 127.5, 86, 255.0).y2, 0.01);
      } else {
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX1(),
                new Line2D.Double(i
                        , 255., i + 1, 255.0).x1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getX2(),
                new Line2D.Double(i
                        , 255., i + 1, 255.0).x2, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY1(),
                new Line2D.Double(i
                        , 255., i + 1, 255.0).y1, 0.01);
        assertEquals(histogramDrawer.getIntensityLines(this.image).get(i).getY2(),
                new Line2D.Double(i
                        , 255., i + 1, 255.0).y2, 0.01);
      }
    }

    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x1,
            histogramDrawer.getIntensityLines(this.image).get(254).getX1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).x2,
            histogramDrawer.getIntensityLines(this.image).get(254).getX2(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y1,
            histogramDrawer.getIntensityLines(this.image).get(254).getY1(), 0.01);
    assertEquals(new Line2D.Double(254, 255.0, 255, 191.25).y2,
            histogramDrawer.getIntensityLines(this.image).get(254).getY2(), 0.01);
  }
}