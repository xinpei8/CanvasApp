package edu.nyu.cs.pqs.canvas;

import java.awt.Color;

/**
 * This class defines the interface of GUI(CanvasView) for Canvas model
 * @author Hsin-Pei Lin
 */
public interface CanvasListener {
  /**
   * Paint the track with given start and end positions (from reading the mouse motion)
   * and with the given color
   * @param oldX
   * @param oldY
   * @param newX
   * @param newY
   * @param color should not be null, if color is illegal, default color is black
   */
  void paintMade(int oldX, int oldY, int newX, int newY, Color color);

  /**
   * Erase all the paint on paint board and reset position to (0, 0)
   */
  void clearPaintBoard(); 

}
