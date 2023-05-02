package view;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * The interface for a histogram panel.
 */
public interface IHistogramPanel {

  /**
   * Adds lines to the histogram.
   * @param color the given color
   * @param lines the given lines
   */
  void addLines(Color color, List<Line2D> lines);

  /**
   * clears the lines in the histogram.
   */
  void clearLines();
}
