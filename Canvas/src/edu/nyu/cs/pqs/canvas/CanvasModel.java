package edu.nyu.cs.pqs.canvas;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

/**
 * This class implements the logic of Canvas. Canvas application can register 
 * multiple CanvasView sharing the same model. In each window (CanvasView), 
 * user can paint on the board and all the windows will reflect the same paint board result.
 * The default paint color is set to black. Paint color selected in each window will be hold 
 * differently by window.
 * @author Hsin-Pei Lin
 */
public class CanvasModel {
  private List<CanvasListener> listeners = new ArrayList<CanvasListener>();
  private final int length;
  private final int width;
  private int oldX;
  private int oldY;
  private int newX;
  private int newY;

  /**
   * This constructor takes the width and length for window (CanvasView) set up.
   * @param width
   * @param length
   * @throws IllegalArgumentException width and length should be within the range 300~1000
   */
  public CanvasModel(int width, int length) throws IllegalArgumentException {
    if (width < 300 || length < 300 || width > 1000 || length > 1000) {
      throw new IllegalArgumentException("width and length must be within 300-1000");
    }
    this.width = width;
    this.length = length;
    resetPaintTrack();
  }

  /**
   * Once reading the click signal from mouse, CanvasView can set the start position for painting
   * @param x
   * @param y
   */
  public void setPaintStartPositions(int x, int y) {
    if (isWithinPaintBoard(x, y)) {
      oldX = x;
      oldY = y;
    }
    else {
      resetPaintTrack();
    }
  }

  /**
   * Once reading the drag signal from mouse, this method updates the new position and do painting
   * with the given color on all the registered CanvasViews. Afterwards, reset the old position to 
   * the ending position made last time. If the given (x, y) is not within the board, no paint will
   * be made.
   * @param x
   * @param y
   * @param color use this color for painting
   * @throws NullPointerException color can't be null
   */
  public void setPaintEndPositions(int x, int y, Color color) throws NullPointerException {
    if (color == null) {
      throw new NullPointerException();
    }
    if (isWithinPaintBoard(x, y)) {
      newX = x;
      newY = y;
      firePaintEvent(color);
      oldX = x;
      oldY = y;
    } 
    else {
      resetPaintTrack();
    }
  }

  /**
   * Erase all the paint on paint board and reset paint track position values to 0
   */
  public void clearBoard() {
    fireClearPaintBoardEvent();
    resetPaintTrack();
  }

  private boolean isWithinPaintBoard(int x, int y) {
	return x >= 0 && x < width && y >= 0 && y < length;
  }

  private void firePaintEvent(Color color) {
    for (CanvasListener listener : listeners) {
      listener.paintMade(oldX, oldY, newX, newY, color);
    }
  }

  private void fireClearPaintBoardEvent() {
    for (CanvasListener listener : listeners) {
      listener.clearPaintBoard();
    }
  }

  private void resetPaintTrack() {
    oldX = 0;
    oldY = 0;
    newX = 0;
    newY = 0;
  }

  /**
   * Registers CanvasView as a listener to this model
   * @param listener
   * @throws NullPointerException
   */
  public void registerCanvasListener(CanvasListener listener) throws NullPointerException {
    if (listener == null) {
      throw new NullPointerException();
    }
    listeners.add(listener);
  }

  /**
   * Removes the CanvasView from the listener list of this model
   * @param listener
   * @throws NullPointerException
   */
  public boolean removeCanvasListener(CanvasListener listener) throws NullPointerException {
    if (listener == null) {
      throw new NullPointerException();
    }
    return listeners.remove(listener);
  }
  
  public int getOldX() {
    return oldX;
  }

  public int getOldY() {
    return oldY;
  }

  public int getNewX() {
    return newX;
  }
  
  public int getNewY() {
    return newY;
  }

  public int getWidth() {
    return width;  
  }
  
  public int getLength() {
    return length;  
  }
}