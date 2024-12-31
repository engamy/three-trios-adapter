package provider.view.components;

import java.awt.geom.Path2D;
import java.awt.Color;
import provider.model.board.GameCard;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * Represents the shape of the card that is drawn to the game board.
 * has functionalities for drawing the card and drawing the values on the card.
 */
public class CardShape extends Path2D.Double {
  private final int width;
  private final int height;
  private final GameCard card;
  private final boolean isHighlighted;

  /**
   * Constructor for the card shape.
   * Represents the shape of the card using width, height, the card
   *
   * @param card the card represented by this card shape
   * @param height the height of the card shape
   * @param width the width of the card shape
   * @param isHighlighted whether the card is highlighted or not
   */
  public CardShape(int width, int height, GameCard card, boolean isHighlighted) {
    this.width = width;
    this.height = height;
    this.card = card;
    this.isHighlighted = isHighlighted;

    moveTo(0, 0);
    lineTo(width, 0);
    lineTo(width, height);
    lineTo(0, height);
    closePath();
  }

  /**
   * draws the card shape at a specific location with the color
   * and adjusts if it is highlighted.
   * Draws the shape and then adds the card values.
   *
   * @param g2 the graphics used for drawing
   * @param x the x-coordinate to draw the card
   * @param y the y-coordinate to draw the card
   * @param baseColor the main color of the card if not highlighted
   */
  public void draw(Graphics2D g2, int x, int y, Color baseColor) {
    g2.translate(x, y);

    if (isHighlighted) {
      g2.setColor(Color.GRAY);
    } else {
      g2.setColor(baseColor);
    }

    g2.fill(this);
    g2.setColor(Color.BLACK);
    g2.draw(this);

    g2.translate(-x, -y);
    drawCardValues(g2, x, y);
  }

  private void drawCardValues(Graphics2D g2, int x, int y) {
    int fontSize = (int) (height * 0.15);
    g2.setFont(new Font("SansSerif", Font.BOLD, fontSize));

    FontMetrics fm = g2.getFontMetrics();
    int ascent = fm.getAscent();
    int descent = fm.getDescent();
    int fontHeight = ascent + descent;

    int midX = x + width / 2;
    int midY = y + height / 2;

    g2.setColor(Color.BLACK);

    String northValue = card.getNorth().getSValue();
    int northWidth = fm.stringWidth(northValue);
    g2.drawString(northValue, midX - northWidth / 2, y + ascent + 5);

    String southValue = card.getSouth().getSValue();
    int southWidth = fm.stringWidth(southValue);
    g2.drawString(southValue, midX - southWidth / 2, y + height - descent - 5);

    String westValue = card.getWest().getSValue();
    int westWidth = fm.stringWidth(westValue);
    g2.drawString(westValue, x + 12, midY + ascent / 2);

    String eastValue = card.getEast().getSValue();
    int eastWidth = fm.stringWidth(eastValue);
    g2.drawString(eastValue, x + width - eastWidth - 10, midY + ascent / 2);
  }
}

