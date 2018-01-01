package edu.nyu.cs.pqs.canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class creates and manages the GUI (view) of Canvas
 *@author Hsin-Pei Lin
 */
public class CanvasView implements CanvasListener {
  private static final Color[] colors = { Color.BLACK, Color.GRAY, Color.CYAN, Color.BLUE, 
               Color.GREEN, Color.RED, Color.ORANGE, Color.YELLOW, Color.PINK, Color.WHITE };

  private CanvasModel model;
  private JFrame frame = new JFrame("Let's Paint!");
  private JPanel paintPanel;
  private Graphics2D graphics;
  private Image image;
  private Color currentColor;
  private final int width;
  private final int length;

  /**
   * The constructor takes the shared model and registers itself as a listener to the model
   * @param model
   * @throws NullPointerException model can't be null
   */
  public CanvasView(CanvasModel model) throws NullPointerException {
    if (model == null) {
      throw new NullPointerException();
    }
    this.model = model;
    model.registerCanvasListener(this);
    width = model.getWidth();
    length = model.getLength();
    currentColor = Color.BLACK;

    JPanel mainPanel = new JPanel(new BorderLayout());
    setupColorButtons(mainPanel);
    setupClearButton(mainPanel);
    setupDrawingPanel(mainPanel);
    setupMouseListeners();

    frame.setContentPane(mainPanel);
    frame.setSize(width, length);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true); 
  }

  private void setupColorButtons(JPanel mainPanel) {
    JPanel buttonPanel = new JPanel();
    for (int i = 0; i < colors.length; i++) {
      JButton colorButton = new JButton();
      colorButton.setActionCommand("" + i);
      colorButton.addActionListener(generateColorButtonActionListerner());
      colorButton.setPreferredSize(new Dimension(30, 30));
      colorButton.setBackground(colors[i]);
      colorButton.setOpaque(true);
      colorButton.setBorderPainted(false);
      buttonPanel.add(colorButton);
    }
    mainPanel.add(buttonPanel, BorderLayout.NORTH);
  }

  private void setupClearButton(JPanel mainPanel) {
    JButton clearButton = new JButton("Clear Board");
    mainPanel.add(clearButton, BorderLayout.SOUTH);
    clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        model.clearBoard();
      }
    });
  }

  /**
   * This method creates the drawing panel and override the paintComponent to include the graphic 
   * component
   */
  private void setupDrawingPanel(JPanel mainPanel) {
    paintPanel = new JPanel() {
      private static final long serialVersionUID = 1L;

      protected void paintComponent(Graphics g) {
        if (image == null) {
          image = createImage(getSize().width, getSize().height);
          graphics = (Graphics2D)image.getGraphics();
          graphics.setStroke(new BasicStroke(2));
          clearPaintBoard();
        }
        g.drawImage(image, 0, 0, null);
      }
    };
    mainPanel.add(paintPanel, BorderLayout.CENTER);
  }

  /**
   * This method set up the mouse listeners for detecting mouse pressed and dragged event.
   * Once received the mouse events, update the position information to CanvasModel. 
   */
  public void setupMouseListeners() {
    paintPanel.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        model.setPaintStartPositions(e.getX(), e.getY());
      }
    });

    paintPanel.addMouseMotionListener(new MouseAdapter() {
      public void mouseDragged(MouseEvent e) {
        model.setPaintEndPositions(e.getX(), e.getY(), currentColor);
      }
    });
  }

  private ActionListener generateColorButtonActionListerner() {
    ActionListener colorButtonsActionListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int id = Integer.parseInt(e.getActionCommand());
        currentColor = colors[id];
      }
    };
    return colorButtonsActionListener;
  }

  @Override
  public void paintMade(int oldX, int oldY, int newX, int newY, Color color) {
    if (color == null){
    	graphics.setPaint(Color.BLACK);
    }
    else {
      graphics.setPaint(color);
    }
    graphics.drawLine(oldX, oldY, newX, newY);
    paintPanel.repaint();
  }

  @Override
  public void clearPaintBoard() {
    graphics.setPaint(Color.white);
    graphics.fillRect(0, 0, paintPanel.getSize().width, paintPanel.getSize().height);
    paintPanel.repaint();
    graphics.setPaint(currentColor);
  }
}
