package model;

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
import static org.junit.Assert.assertTrue;

/**
 * Test class for ImageProcessorModelImpl.
 */
public class ImageProcessorModelImplTest {
  ImageProcessorModel model;
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
  List<IPixel> row3;
  List<IPixel> row4;
  List<IPixel> row5;
  List<IPixel> row6;
  IImage image;

  @Before
  public void setUp() {
    this.model = constructModel();
    this.black = new PixelImpl(0, 0, 0);
    this.yellow = new PixelImpl(255, 255, 0);
    this.red = new PixelImpl(255, 0, 0);
    this.grey = new PixelImpl(35, 35, 35);
    this.orange = new PixelImpl(255, 195, 68);
    this.blue = new PixelImpl(0, 0, 255);
    this.white = new PixelImpl(255, 255, 255);
    this.imageList = new ArrayList<>();
    this.row1 = new ArrayList<>();
    this.row2 = new ArrayList<>();
    this.row3 = new ArrayList<>();
    this.row4 = new ArrayList<>();
    this.row5 = new ArrayList<>();
    this.row6 = new ArrayList<>();
  }

  protected ImageProcessorModel constructModel() {
    return new ImageProcessorModelImpl();
  }

  @Test
  public void loadImage() {
    Collections.addAll(this.row1, this.black, this.black, this.black);
    Collections.addAll(this.row2, this.black, this.black, this.black);
    Collections.addAll(this.row3, this.black, this.black, this.black);
    Collections.addAll(this.row4, this.black, this.black, this.black);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");
    assertEquals(this.image, this.model.saveImage("blackImage"));
  }

  @Test
  public void saveImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row3, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row4, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row5, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");

    IImage savedImage = this.model.saveImage("blackImage");
    for (int row = 0; row < 5; row += 1) {
      for (int col = 0; col < 4; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(savedImage.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(savedImage.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImageNonExistentImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row3, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row4, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row5, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");

    IImage savedImage = this.model.saveImage("whiteImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveImageNonExistentImageCaseSensitiveCheck() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row3, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row4, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row5, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);
    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");

    IImage savedImage = this.model.saveImage("BLACKIMAGE");
  }

  @Test
  public void saveImageCheckAliasing() {
    Collections.addAll(this.row1, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row2, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row3, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row4, this.black, this.yellow, this.black, this.yellow);
    Collections.addAll(this.row5, this.black, this.yellow, this.black, this.yellow);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3, this.row4, this.row5);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "blackImage");

    this.imageList.get(0).clear();

    IImage savedImage = this.model.saveImage("blackImage");
    for (int row = 0; row < 5; row += 1) {
      for (int col = 0; col < 4; col += 1) {
        if (col % 2 == 0) {
          assertTrue(this.black.equals(savedImage.getPixelAt(row, col)));
        } else {
          assertTrue(this.yellow.equals(savedImage.getPixelAt(row, col)));
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void horizontalFlipNonexistentImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.horizontalFlip("colorfulFood", "horizontallyFlipped");
  }

  @Test
  public void horizontalFlipOdd() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.horizontalFlip("colorfulImage", "horizontallyFlipped");
    IImage savedImage = this.model.saveImage("horizontallyFlipped");

    assertTrue(this.orange.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(this.yellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.blue.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(this.red.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void horizontalFlipEven() {
    Collections.addAll(this.row1, this.black, this.yellow);
    Collections.addAll(this.row2, this.grey, this.red);
    Collections.addAll(this.row3, this.orange, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.horizontalFlip("colorfulImage", "horizontallyFlipped");
    IImage savedImage = this.model.saveImage("horizontallyFlipped");

    assertTrue(this.yellow.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(this.red.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.blue.equals(savedImage.getPixelAt(2, 0)));
    assertTrue(this.orange.equals(savedImage.getPixelAt(2, 1)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void verticalFlipNonexistentImage() {
    Collections.addAll(this.row1, this.black, this.yellow);
    Collections.addAll(this.row2, this.grey, this.red);
    Collections.addAll(this.row3, this.orange, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.verticalFlip("hello grader how is your day doing", "verticallyFlipped");
  }

  @Test
  public void verticalFlipOdd() {
    Collections.addAll(this.row1, this.black, this.yellow);
    Collections.addAll(this.row2, this.grey, this.red);
    Collections.addAll(this.row3, this.orange, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2, this.row3);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.verticalFlip("colorfulImage", "verticallyFlipped");
    IImage savedImage = this.model.saveImage("verticallyFlipped");

    assertTrue(this.orange.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(this.blue.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(this.red.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.black.equals(savedImage.getPixelAt(2, 0)));
    assertTrue(this.yellow.equals(savedImage.getPixelAt(2, 1)));
  }

  @Test
  public void verticalFlipEven() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.blue);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.verticalFlip("colorfulImage", "verticallyFlipped");
    IImage savedImage = this.model.saveImage("verticallyFlipped");

    assertTrue(this.grey.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(this.red.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(this.blue.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.black.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(this.yellow.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.orange.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeBrightnessNonexistentImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.changeBrightness(10, "jolly ranchers",
            "brightenedImage");
  }

  @Test
  public void changeBrightnessPositive() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.changeBrightness(10, "colorfulImage", "brightenedImage");
    IImage savedImage = this.model.saveImage("brightenedImage");

    IPixel brightenedBlack = new PixelImpl(10, 10, 10);
    IPixel brightenedYellow = new PixelImpl(255, 255, 10);
    IPixel brightenedOrange = new PixelImpl(255, 205, 78);
    IPixel brightenedGrey = new PixelImpl(45, 45, 45);
    IPixel brightenedRed = new PixelImpl(255, 10, 10);
    assertTrue(brightenedBlack.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(brightenedYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(brightenedOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(brightenedGrey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(brightenedRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void changeBrightnessNegative() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.changeBrightness(-20, "colorfulImage", "darkenedImage");
    IImage savedImage = this.model.saveImage("darkenedImage");


    IPixel darkenedYellow = new PixelImpl(235, 235, 0);
    IPixel darkenedOrange = new PixelImpl(235, 175, 48);
    IPixel darkenedGrey = new PixelImpl(15, 15, 15);
    IPixel darkenedRed = new PixelImpl(235, 0, 0);
    IPixel darkenedWhite = new PixelImpl(235, 235, 235);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(darkenedYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(darkenedOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(darkenedGrey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(darkenedRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(darkenedWhite.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getComponentNonExistentImage() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("red", "meow", "woof");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getComponentNonExistentComponent() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("purple", "colorfulImage", "outputImage");
  }

  @Test
  public void getComponentRed() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("red", "colorfulImage", "redImage");
    IImage savedImage = this.model.saveImage("redImage");

    IPixel redOfYellow = new PixelImpl(255, 255, 255);
    IPixel redOfOrange = new PixelImpl(255, 255, 255);
    IPixel redOfRed = new PixelImpl(255, 255, 255);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(redOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(redOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(redOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void getComponentGreen() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("green", "colorfulImage", "greenImage");
    IImage savedImage = this.model.saveImage("greenImage");

    IPixel greenOfYellow = new PixelImpl(255, 255, 255);
    IPixel greenOfOrange = new PixelImpl(195, 195, 195);
    IPixel greenOfRed = new PixelImpl(0, 0, 0);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(greenOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(greenOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(greenOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void getComponentBlue() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("blue", "colorfulImage", "blueImage");
    IImage savedImage = this.model.saveImage("blueImage");

    IPixel blueOfYellow = new PixelImpl(0, 0, 0);
    IPixel blueOfOrange = new PixelImpl(68, 68, 68);
    IPixel blueOfRed = new PixelImpl(0, 0, 0);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(blueOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(blueOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(blueOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void getComponentValue() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("value", "colorfulImage", "valueImage");
    IImage savedImage = this.model.saveImage("valueImage");

    IPixel valueOfYellow = new PixelImpl(255, 255, 255);
    IPixel valueOfOrange = new PixelImpl(255, 255, 255);
    IPixel valueOfRed = new PixelImpl(255, 255, 255);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(valueOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(valueOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(valueOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void getComponentIntensity() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("intensity", "colorfulImage", "intensityImage");
    IImage savedImage = this.model.saveImage("intensityImage");

    IPixel intensityOfYellow = new PixelImpl(170, 170, 170);
    IPixel intensityOfOrange = new PixelImpl(173, 173, 173);
    IPixel intensityOfRed = new PixelImpl(85, 85, 85);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(intensityOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(intensityOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(intensityOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }

  @Test
  public void getComponentLuma() {
    Collections.addAll(this.row1, this.black, this.yellow, this.orange);
    Collections.addAll(this.row2, this.grey, this.red, this.white);

    Collections.addAll(this.imageList, this.row1, this.row2);

    this.image = new ImageImpl(this.imageList);
    this.model.loadImage(this.image, "colorfulImage");
    this.model.getComponent("luma", "colorfulImage", "lumaImage");
    IImage savedImage = this.model.saveImage("lumaImage");

    IPixel lumaOfYellow = new PixelImpl(237, 237, 237);
    IPixel lumaOfOrange = new PixelImpl(199, 199, 199);
    IPixel lumaOfRed = new PixelImpl(54, 54, 54);
    assertTrue(this.black.equals(savedImage.getPixelAt(0, 0)));
    assertTrue(lumaOfYellow.equals(savedImage.getPixelAt(0, 1)));
    assertTrue(lumaOfOrange.equals(savedImage.getPixelAt(0, 2)));
    assertTrue(this.grey.equals(savedImage.getPixelAt(1, 0)));
    assertTrue(lumaOfRed.equals(savedImage.getPixelAt(1, 1)));
    assertTrue(this.white.equals(savedImage.getPixelAt(1, 2)));
  }
}