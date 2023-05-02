package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * A panel that draws the histogram.
 */
public class HistogramPanel extends JPanel implements IHistogramPanel {
  private final Map<Color, List<Line2D>> lines;

  /**
   * Constructor for a histogram panel.
   *
   * @param height the height
   * @param width  the width
   */
  public HistogramPanel(int height, int width) {
    super();

    this.setBackground(Color.white);
    this.setPreferredSize(new Dimension(width, height));
    this.lines = new HashMap<>();
  }

  @Override
  public void addLines(Color color, List<Line2D> lines) {
    List<Line2D> newLines = new ArrayList<>(lines);
    this.lines.put(color, newLines);
    this.repaint();
  }

  @Override
  public void clearLines() {
    this.lines.clear();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (Map.Entry<Color, List<Line2D>> lines : this.lines.entrySet()) {
      g.setColor(lines.getKey());
      for (Line2D line : lines.getValue()) {
        int x1 = (int) Math.round(line.getX1());
        int y1 = (int) Math.round(line.getY1());
        int x2 = (int) Math.round(line.getX2());
        int y2 = (int) Math.round(line.getY2());
        g.drawLine(x1, y1, x2, y2);
      }
    }
  }
}
