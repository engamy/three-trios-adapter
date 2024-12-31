package provider.view.components;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import provider.controller.ThreeTriosController;
import provider.model.board.GameCard;
import java.awt.Graphics2D;
import java.awt.Graphics;
import provider.model.ReadonlyThreeTriosModel;
import provider.model.board.GameCell;
import provider.model.board.Grid;
import adapter.Position;
import provider.model.enums.CellType;
import provider.model.enums.PlayerColor;
import javax.swing.JPanel;


/**
 * The TTGridView class represents the grid panel in the game view.
 * It draws the grid with color coded cells representing holes and card cells.
 * It also handles clicking cells to get the index as well as updating the dimsensions.
 */
public class TTGridView extends JPanel implements GridPanel {
  private final ReadonlyThreeTriosModel model;

  int cellHeight;
  int cellWidth;
  private ThreeTriosController listener;

  private static final Color CUSTOM_RED = new Color(255, 104, 104);
  private static final Color CUSTOM_BLUE = new Color(104, 104, 255);
  private static final Color EMPTY_CELL = Color.YELLOW;
  private static final Color HOLE_CELL = Color.GRAY;

  /**
   * constructs the TTGridView.
   * Initializes it with the game model and sets up a listener component
   * which allows resizing of the cell dimensions.
   *
   * @param model the readonly model of the game which provides
   *              the grid specifics for rendering
   */
  public TTGridView(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateCellDimensions();
        repaint();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCellClick(e.getX(), e.getY());
      }
    });
  }

  public void setListener(ThreeTriosController listener) {
    this.listener = listener;
  }

  private void updateCellDimensions() {
    int numRows = model.getGrid().getNumRows();
    int numCols = model.getGrid().getNumCols();
    int availableHeight = getHeight();
    int availableWidth = getWidth();

    cellHeight = availableHeight / numRows;
    cellWidth = availableWidth / numCols;
  }

  private void handleCellClick(int mouseX, int mouseY) {
    if (mouseX <= 5 || mouseX >= getWidth() - 5) {
      //System.out.println("Out of bounds.");
    } else {
      int col = mouseX / cellWidth;
      int row = mouseY / cellHeight;
      this.listener.selectGridPosition(new Position(row, col));
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Grid grid = model.getGrid();
    for (int r = 0; r < grid.getNumRows(); r++) {
      for (int c = 0; c < grid.getNumCols(); c++) {
        int x = cellWidth * c;
        int y = cellHeight * r;
        drawCell(g2, grid.getCell(new Position(r, c)), x, y, new Position(r, c));
      }
    }
  }

  private void drawCell(Graphics2D g2, GameCell cell, int x, int y, Position p) {
    GameCard card = null;
    if (cell.getType() == CellType.Hole) {
      g2.setColor(HOLE_CELL);
    } else if (cell.isEmpty()) {
      g2.setColor(EMPTY_CELL);
    } else {
      card = cell.getCard();
    }

    if (card != null) {
      Color baseColor = (card.getOwner() == PlayerColor.Red) ? CUSTOM_RED : CUSTOM_BLUE;
      CardShape cardShape = new CardShape(cellWidth, cellHeight, card, false);
      cardShape.draw(g2, x, y, baseColor);
    } else {
      g2.fillRect(x, y, cellWidth, cellHeight);
      g2.setColor(Color.BLACK);
      g2.drawRect(x, y, cellWidth, cellHeight);
    }
  }
}
