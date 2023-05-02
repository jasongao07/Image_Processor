package histogram;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.components.ComponentGetter;
import model.components.GetBlue;
import model.components.GetGreen;
import model.components.GetIntensity;
import model.components.GetRed;
import model.image.IImage;
import model.image.IPixel;

/**
 * This class represents a histogram drawer that supports a single functionality: creating a
 * histogram.
 */
public class HistogramDrawerImpl implements IHistogramDrawer {
  private final int width;
  private final int height;

  /**
   * The constructor for a histogram drawing impl with the dimensions: (value, frequency).
   */
  public HistogramDrawerImpl(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("arguments cannot be negative");
    }
    this.width = width;
    this.height = height;
  }

  /**
   * populates the hashmaps with the frequencies of each value.
   *
   * @param image the given image
   */
  protected List<Integer> populateData(IImage image, ComponentGetter getter) {
    if (image == null || getter == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel pixel = image.getPixelAt(i, j);
        data.add(getter.componentValue(pixel));
      }
    }

    return data;
  }

  /**
   * Calculates the maximum frequency count from the all the pixel's red, green, and blue
   * components of an image.
   * @param image the given image
   * @return the maximum frequency
   */
  protected int getMaxFrequency(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    List<Integer> redValues = populateData(image, new GetRed());
    List<Integer> greenValues = populateData(image, new GetGreen());
    List<Integer> blueValues = populateData(image, new GetBlue());
    List<Integer> intensityValues = populateData(image, new GetIntensity());

    Map<Integer, Integer> redData = initializeMaps(redValues);
    Map<Integer, Integer> greenData = initializeMaps(greenValues);
    Map<Integer, Integer> blueData = initializeMaps(blueValues);
    Map<Integer, Integer> intensityData = initializeMaps(intensityValues);

    int maxRed = 0;
    int maxGreen = 0;
    int maxBlue = 0;
    int maxIntensity = 0;

    for (int i = 0; i < 256; i++) {
      maxRed = Math.max(redData.get(i), maxRed);
      maxGreen = Math.max(greenData.get(i), maxGreen);
      maxBlue = Math.max(blueData.get(i), maxBlue);
      maxIntensity = Math.max(intensityData.get(i), maxIntensity);
    }

    return Math.max(maxRed, Math.max(maxGreen, Math.max(maxBlue, maxIntensity)));
  }

  /**
   * Initializes the maps with data of the images, with the values as keys and the frequencies as
   * values.
   * @param componentData the component of the pixel that the map is created from
   * @return the map of value, frequency
   */
  protected Map<Integer, Integer> initializeMaps(List<Integer> componentData) {
    if (componentData == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    Map<Integer, Integer> data = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      data.put(i, 0);
    }
    for (Integer componentDatum : componentData) {
      data.put(componentDatum, data.get(componentDatum) + 1);
    }
    return data;
  }

  /**
   * Creates the list of scaled lines (according to the height and width of the histogram size)
   * of a histogram given the specific component of a pixel.
   * @param image the given image
   * @param component the given component
   * @return the list of lines of a histogram
   */
  protected List<Line2D> getHistogramLines(IImage image, ComponentGetter component) {
    if (image == null || component == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    List<Integer> data = populateData(image, component);
    Map<Integer, Integer> mappedData = initializeMaps(data);
    int frequency = getMaxFrequency(image);
    List<Line2D> lines = new ArrayList<>();
    for (int i = 0; i < 255; i++) {
      double width = (i * this.width) / 255.0;
      double nextWidth = ((i + 1) * this.width) / 255.0;
      double height = this.height - 1.0 * mappedData.get(i) * this.height / frequency;
      double nextHeight = this.height - 1.0 * mappedData.get(i + 1) * this.height / frequency;
      Line2D line = new Line2D.Double(width, height, nextWidth, nextHeight);
      lines.add(line);
    }
    return lines;
  }

  @Override
  public List<Line2D> getRedLines(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    ComponentGetter component = new GetRed();
    return getHistogramLines(image, component);
  }

  @Override
  public List<Line2D> getGreenLines(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    ComponentGetter component = new GetGreen();
    return getHistogramLines(image, component);
  }

  @Override
  public List<Line2D> getBlueLines(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    ComponentGetter component = new GetBlue();
    return getHistogramLines(image, component);
  }

  @Override
  public List<Line2D> getIntensityLines(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    ComponentGetter component = new GetIntensity();
    return getHistogramLines(image, component);
  }
}
