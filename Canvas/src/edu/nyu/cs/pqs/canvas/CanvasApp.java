package edu.nyu.cs.pqs.canvas;

public class CanvasApp {

  public static void main (String[] args) {
    new CanvasApp().startGame(3);
  }

  private void startGame(int windows) {
    CanvasModel model = new CanvasModel(500, 500);
    for (int i = 0; i < windows; i++) {
      new CanvasView(model);
    }
  }
}
