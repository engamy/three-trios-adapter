package provider.view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import provider.controller.ThreeTriosController;
import provider.model.ReadonlyThreeTriosModel;
import provider.model.enums.PlayerColor;
import provider.view.ThreeTriosView;
import provider.view.components.TTGridView;
import provider.view.components.TTHandView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;



/**
 * Represents the main view for the Triple Triad game.
 * displays the hands of both players and the game grid and adjusts the size of
 * components when the window is resized.
 */
public class TripleTriadView extends JFrame implements ThreeTriosView {
  private final TTHandView red;
  private final TTHandView blue;
  private final TTGridView grid;

  /**
   * Constructs the three trios view with the provided game model.
   * It also initializes the hand and the grid for the players.
   *
   * @param model the read-only game model used to initialize components for the view
   */
  public TripleTriadView(ReadonlyThreeTriosModel model) {
    red = new TTHandView(model, PlayerColor.Red);
    blue = new TTHandView(model, PlayerColor.Blue);
    grid = new TTGridView(model);

    grid.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, Color.WHITE));
    blue.setPreferredSize(new Dimension(100, 400));
    red.setPreferredSize(new Dimension(100, 400));
    grid.setPreferredSize(new Dimension(520, 400));

    add(red, BorderLayout.WEST);
    add(blue, BorderLayout.EAST);
    add(grid, BorderLayout.CENTER);

    pack();
    setLocationRelativeTo(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int width = getWidth() - 20;
        int height = getHeight();
        blue.setPreferredSize(new Dimension(width / 7, height));
        red.setPreferredSize(new Dimension(width / 7, height));
        grid.setPreferredSize(new Dimension(width * 5 / 7, height));
        refresh();
      }
    });

  }

  /**
   * Sets the controller as the listener for the view.
   *
   * @param listener the listener is the controller responsible for managing game interactions
   */
  public void setListener(ThreeTriosController listener) {
    setTitle(listener.getPlayer().toString());
    this.blue.setListener(listener);
    this.red.setListener(listener);
    this.grid.setListener(listener);
    listener.notifyPlayerOfTurn();
  }

  @Override
  public void refresh() {
    red.refresh();
    blue.refresh();
    grid.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void showError(String msg) {
    JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void gameOver(String msg) {
    JOptionPane.showMessageDialog(this, msg, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
    this.dispose();
  }

}
