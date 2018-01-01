package edu.nyu.cs.pqs.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import edu.nyu.cs.pqs.canvas.CanvasModel;
import edu.nyu.cs.pqs.canvas.CanvasView;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import java.util.logging.Logger;


public class CanvasModelTest {
  private static final Logger logger = Logger.getLogger("CanvasTest.CanvasModelTest");
  private CanvasModel model;

  @Before
  public void setUp() throws Exception {
    model = new CanvasModel(500, 500);
  }

  @Test
  public void testBasic() {
    assertEquals(500, model.getWidth());
    assertEquals(500, model.getLength());

    model.clearBoard();
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());
    assertEquals(0, model.getNewY());
    assertEquals(0, model.getNewY());
    logger.info("testBasic is complete.\n");
  }
  
  @Test
  public void testCreateModelWithIllegalArguments() {
    CanvasModel anotherModel;
    try {
      anotherModel = new CanvasModel(100, 500);
      fail("Exception not thrown, window size should be within the range 300~1000.");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      logger.info(e.toString());
    }
    try {
      anotherModel = new CanvasModel(5000, 500);
      fail("Exception not thrown, window size should be within the range 300~1000.");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      logger.info(e.toString());
    }
    try {
      anotherModel = new CanvasModel(500, 100);
      fail("Exception not thrown, window size should be within the range 300~1000.");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      logger.info(e.toString());
    }
    try {
      anotherModel = new CanvasModel(500, 5000);
      fail("Exception not thrown, window size should be within the range 300~1000.");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      logger.info(e.toString());
    }
    logger.info("testCreateModelWithIllegalArguments is complete.\n");
  }

  @Test
  public void testRegisterCanvasListener() {
    // Register a listener
    new CanvasView(model);

    try {
      model.registerCanvasListener(null);
      fail("Exception not thrown, Listener can't be null.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      logger.info(e.toString());
    }
    logger.info("testRegisterCanvasListener is complete.\n");
  }

  @Test
  public void testRemoveCanvasListener() {
    boolean success = false;
    CanvasView anchorView = new CanvasView(model);

    // Remove a listener in the list 
    success = model.removeCanvasListener(anchorView);
    assertTrue(success);

    // Remove a listener that is not in the list 
    CanvasModel anotherModel = new CanvasModel(500,500);
    CanvasView anotherViewNotInList = new CanvasView(anotherModel);
    success = model.removeCanvasListener(anotherViewNotInList);
    assertFalse(success);

    // Remove a null listener
    try {
      model.removeCanvasListener(null);
      fail("Exception not thrown, Listener can't be null.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      logger.info(e.toString());
    }
    logger.info("testRemoveCanvasListener is complete.\n");
  }

  @Test
  public void testSetPaintStartPositions() {

    //test valid (x, y), should be set accordingly
    model.setPaintStartPositions(20, 20);
    assertEquals(20, model.getOldX());
    assertEquals(20, model.getOldY());

    //test invalid (x, y), should be reset to 0
    model.setPaintStartPositions(-1, 0);
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());

    model.setPaintStartPositions(20, 20);
    model.setPaintStartPositions(0, -1);
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());

    model.setPaintStartPositions(20, 20);
    model.setPaintStartPositions(600, 0);
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());

    model.setPaintStartPositions(20, 20);
    model.setPaintStartPositions(0, 600);
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());

    logger.info("testSetPaintStartPositions is complete.\n");
  }

  @Test
  public void testSetPaintEndPositions() {
    //test valid (x, y, color), (x, y) should be set accordingly
    model.setPaintEndPositions(20, 20, Color.BLUE);
    assertEquals(20, model.getNewX());
    assertEquals(20, model.getNewY());
    assertEquals(20, model.getOldX());
    assertEquals(20, model.getOldY());

    //test invalid (x, y), (x, y) should be reset to 0
    model.setPaintEndPositions(-1, -1, Color.BLUE);
    assertEquals(0, model.getNewX());
    assertEquals(0, model.getNewY());
    assertEquals(0, model.getOldX());
    assertEquals(0, model.getOldY());

    //test invalid color(null), should throw exception
    try {
      model.setPaintEndPositions(20, 20, null);
      fail("Exception not thrown, Color can't be null.");
    } catch (Exception e) {
      assertTrue(e instanceof NullPointerException);
      logger.info(e.toString());
    }
    logger.info("testSetPaintEndPositions is complete.\n");
  }
}
