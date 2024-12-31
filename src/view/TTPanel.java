package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JPanel;
import javax.swing.JOptionPane;

import model.CardColor;
import model.Cell;
import model.EmptyCard;
import model.Hole;
import model.PlayingCard;
import model.ReadonlyThreeTriosModel;

/**
 * Represents the JPanel used to display the GUI for the Three Trios game.
 */
public class TTPanel extends JPanel {

  private final ReadonlyThreeTriosModel model;
  private PlayingCard currentCardSelected;

  private int panelWidth;
  private int panelHeight;
  private final int handWidth = 100;
  private final Color borderColor = new Color(0x151515);
  private final Color redCardColor = new Color(0xFAA0A0);
  private final Color blueCardColor = new Color(0xA7D1F8);
  private final Color holeColor = new Color(0xAEACAC);
  private final Color emptyCardColor = new Color(0xD2D86F);

  /**
   * The constructor for the Three Trios Panel.
   * @param model the model to create the GUI of
   */
  public TTPanel(ReadonlyThreeTriosModel model) {
    super();
    this.model = model;
    this.setSize(1210, 810);
    this.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
  }

  @Override
  protected void paintComponent(Graphics g) {
    this.panelWidth = this.getWidth();
    this.panelHeight = this.getHeight();
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    this.paintHands(g2d);
    this.paintGrid(g2d);
  }

  /**
   * Adds the given listener to our MouseListener.
   * @param listener the given listener to add
   */
  public void addClickListener(ViewFeatures listener) {
    this.addMouseListener(new TTClickListener(listener));
  }

  /**
   * Paints the blue and red players' hands on the left (red) and right (blue) sides of the
   * canvas.
   * @param graphic the canvas we are drawing on
   */
  private void paintHands(Graphics2D graphic) {
    if (currentCardSelected == null) {
      currentCardSelected = this.model.getHand(this.model.getCurrentPlayer()).get(0);
    }
    // draw the red hands
    for (int redHandIdx = 0; redHandIdx < this.model.getHand("R").size(); redHandIdx++) {
      this.paintHandCard(this.model.getHand("R").get(redHandIdx),
              graphic, redHandIdx,
              (this.model.getHand("R").get(redHandIdx).toString()
                      .equals(currentCardSelected.toString())));
    }
    // draw the blue hands
    for (int blueHandIdx = 0; blueHandIdx < this.model.getHand("B").size(); blueHandIdx++) {
      this.paintHandCard(this.model.getHand("B").get(blueHandIdx),
              graphic, blueHandIdx,
              (this.model.getHand("B").get(blueHandIdx).toString()
                      .equals(currentCardSelected.toString())));
    }
  }

  /**
   * Paints the current card in a hand. Different from the PlayingCard paintCell() method
   * in that it takes in the index of the hand to paint so the hand is painted in order.
   * @param cell the cell
   * @param graphic the graphic we are painting on
   * @param handIdx the index of the card in the hand we are painting
   */
  private void paintHandCard(PlayingCard cell, Graphics2D graphic,
                             int handIdx, boolean isSelected) {
    if (cell.getCardColor().equals(CardColor.R)) {
      int cellHeight = ((this.panelHeight / this.model.getHand("R").size()));
      int cellY = ((this.panelHeight / this.model.getHand("R").size()) * handIdx);
      this.paintCell(cell, graphic, handWidth, cellHeight, 0, cellY, isSelected);
    }
    else if (cell.getCardColor().equals(CardColor.B)) {
      int cellHeight = ((this.panelHeight / this.model.getHand("B").size()));
      int cellY = ((this.panelHeight / this.model.getHand("B").size()) * handIdx);
      this.paintCell(cell, graphic, handWidth, cellHeight,
              this.panelWidth - this.handWidth, cellY, isSelected);
    }
    else {
      throw new IllegalArgumentException("Cannot paint the hand card because paintHandCard() "
              + "was given an invalid color.");
    }
  }

  /**
   * Paints the grid based on the current model information.
   * @param graphic the canvas we are drawing on
   */
  private void paintGrid(Graphics2D graphic) {
    final int gridWidth = this.panelWidth - (this.handWidth * 2) + 3;
    final int cellWidth = (gridWidth / this.model.getGrid().size());
    final int cellHeight = (panelHeight / this.model.getGrid().get(0).size());

    for (int rowIdx = 0; rowIdx < this.model.getGrid().size(); rowIdx++) {
      for (int colIdx = 0; colIdx < this.model.getGrid().get(rowIdx).size(); colIdx++) {

        Cell currentCell = this.model.getGrid().get(colIdx).get(rowIdx);

        try {
          /* getCardColor() throws an UnsupportedOperationException if it is a Hole or EmptyCard,
           * so if this try does not throw exception, it is definitely a PlayingCard */
          CardColor findCellType = currentCell.getCardColor();
          // cast cell to PlayingCard because we know for certain it is now a PlayingCard
          this.paintCell((PlayingCard)(currentCell), graphic,
                  cellWidth, cellHeight,
                  (rowIdx * cellWidth) + this.handWidth,
                  (colIdx * cellHeight), false);
        } catch (UnsupportedOperationException ex) {
          // if this is Cell is an EmptyCard
          if (ex.getMessage().equals("Cannot get card color from an empty card cell.")) {
            this.paintCell((EmptyCard)(currentCell), graphic,
                    cellWidth, cellHeight,
                    (rowIdx * cellWidth) + this.handWidth,
                    (colIdx * cellHeight), false);
          }
          // if this Cell is a Hole
          else if (ex.getMessage().equals("Cannot get card color from a hole cell.")) {
            this.paintCell((Hole)(currentCell), graphic,
                    cellWidth, cellHeight,
                    (rowIdx * cellWidth) + this.handWidth,
                    (colIdx * cellHeight), false);
          }
          else {
            throw new IllegalStateException("Cannot paint a cell at row " + rowIdx + " col "
                    + colIdx + " because the Cell is somehow not a Hole, "
                    + "EmptyCard, or PlayingCard");
          }
        }
      }
    }
  }

  /**
   * Paints the given Cell based on the given type of cell, width, and height.
   * @param cell the given cell, which in this case is a PlayingCard and will paint a
   *             PlayingCard
   */
  private void paintCell(PlayingCard cell, Graphics2D graphic,
                         int width, int height, int x, int y, boolean isSelected) {
    // determine the color of this PlayingCard
    Color paintCardColor = blueCardColor;
    if (cell.getCardColor().equals(CardColor.R)) {
      paintCardColor = redCardColor;
    }
    graphic.setColor(paintCardColor);
    // paint the cell itself
    graphic.fillRect(x, y, width, height);
    // paint the border around the cell
    graphic.setColor(borderColor);
    graphic.setStroke(new BasicStroke(1));
    // if this cell is selected, change the stroke weight to indicate it is
    if (isSelected) {
      graphic.setStroke(new BasicStroke(8));
    }
    // drawing the black border outline
    graphic.drawRect(x, y, width, height);

    // paint the values on the cell
    // draw north value
    graphic.drawString(cell.getCardValue("north").toString(),
            (int)(width * 0.5) + x,
            (int)(0.25 * height) + y);
    // draw south value
    graphic.drawString(cell.getCardValue("south").toString(),
            (int)(width * 0.5) + x,
            (int)(0.75 * height) + y);
    // draw east value
    graphic.drawString(cell.getCardValue("east").toString(),
            (int)(width * 0.75) + x,
            (int)(height * 0.5) + y);
    // draw west value
    graphic.drawString(cell.getCardValue("west").toString(),
            (int)(width * 0.25) + x,
            (int)(height * 0.5) + y);
  }

  /**
   * Paints the given Cell based on the given type of cell, width, and height.
   * @param cell the given cell, which in this case is an EmptyCard and will paint an EmptyCard
   */
  private void paintCell(EmptyCard cell, Graphics2D graphic,
                         int width, int height, int x, int y, boolean isSelected) {
    graphic.setStroke(new BasicStroke(1));
    graphic.setColor(this.emptyCardColor);
    // paint the cell itself
    graphic.fillRect(x, y, width, height);
    graphic.setColor(borderColor);
    // drawing the black border outline
    graphic.drawRect(x, y, width, height);
  }

  /**
   * Paints the given Cell based on the given type of cell, width, and height.
   * @param cell the given cell, which in this case is a Hole and will paint a Hole
   */
  private void paintCell(Hole cell, Graphics2D graphic,
                         int width, int height, int x, int y, boolean isSelected) {
    graphic.setStroke(new BasicStroke(1));
    graphic.setColor(this.holeColor);
    // paint the cell itself
    graphic.fillRect(x, y, width, height);
    graphic.setColor(borderColor);
    // drawing the black border outline
    graphic.drawRect(x, y, width, height);
  }

  /**
   * Observer that returns the pixel hand width used to draw the hands.
   * @return the hand width
   */
  public int getHandWidth() {
    return this.handWidth;
  }

  /**
   * Observer that returns the model.
   * @return the model.
   */
  public ReadonlyThreeTriosModel getModel() {
    return this.model;
  }

  /**
   * Changes the current selected card to the given one.
   * @param card the card to change the selected card to
   */
  public void setCurrentSelectedCard(PlayingCard card) {
    // ensure that the hand card that's clicked on is from the current player's hand
    if (card.getCardColor().toString().equals(this.model.getCurrentPlayer())) {
      this.currentCardSelected = card;
    }
  }

  /**
   * Displays a message dialog with the given message.
   * @param message the message to display
   */
  public void showDialog(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Represents the Mouse/Click Listener for the Three Trios Game.
   */
  class TTClickListener implements MouseListener {

    private final ViewFeatures features;

    public TTClickListener(ViewFeatures features) {
      this.features = features;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
      // make sure player clicked in their own panel
      this.features.checkPlayerPanel();
      // if the user clicks in the red hand
      if (event.getX() <= TTPanel.this.getHandWidth()) {
        int handIdx = (event.getY()
                / (TTPanel.this.getHeight() / TTPanel.this.getModel().getHand("R").size()));
        try {
          this.features.playerSelectedCard("R", TTPanel.this.getModel().getHand("R").get(handIdx));
        } catch (IllegalArgumentException ex) {
          String playerName = "RED";
          if (TTPanel.this.getModel().getCurrentPlayer().equals("B")) {
            playerName = "BLUE";
          }
          TTPanel.this.showDialog("Player " + playerName + ": Select a card first.");
        }
      }
      // if the user clicks in the blue hand
      else if (event.getX() >= TTPanel.this.getWidth() - TTPanel.this.getHandWidth()) {
        int handIdx = (event.getY()
                / (TTPanel.this.getHeight() / TTPanel.this.getModel().getHand("B").size()));
        try {
          this.features.playerSelectedCard("B", TTPanel.this.getModel().getHand("B").get(handIdx));
        } catch (IllegalArgumentException ex) {
          // do nothing if you try to select a card when the player is an AI
        }
      }
      // otherwise, the user has clicked in the grid
      else {
        int row = (event.getY()
                / (TTPanel.this.getHeight() / TTPanel.this.getModel().getGrid().size()));
        int col = ((event.getX() - TTPanel.this.getHandWidth())
                / ((TTPanel.this.getWidth() - (2 * TTPanel.this.getHandWidth()))
                / TTPanel.this.getModel().getGrid().get(0).size()));
        this.features.playerPlayCard(currentCardSelected, row, col);
      }
    }

    @Override
    public void mousePressed(MouseEvent event) {
      // do not act during this event
    }

    @Override
    public void mouseReleased(MouseEvent event) {
      // do not act during this event
    }

    @Override
    public void mouseEntered(MouseEvent event) {
      // do not act during this event
    }

    @Override
    public void mouseExited(MouseEvent event) {
      // do not act during this event
    }
  }

}
