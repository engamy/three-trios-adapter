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
import provider.model.enums.PlayerColor;
import javax.swing.JPanel;
import java.util.List;

/**
 * Creates a panel representing a view of a hand of cards belonging to a player in Triple Triad.
 * The cards are numbered starting at 0 and in order. When displayed the card closest to the top
 * of the panel is the 0th card, the one directly below that is the 1st card, and so on until you
 * reach the end of the hand. If a hand belongs to the current player in the game it can also
 * have one "selected" or highlighted card at a time.
 */
public class TTHandView extends JPanel implements HandPanel {
  private final ReadonlyThreeTriosModel model;
  private final PlayerColor handColor;
  private int highlightedIndex;
  private int cardHeight;
  private int cardWidth;
  private ThreeTriosController listener;
  private static final Color CUSTOM_RED = new Color(255, 104, 104);
  private static final Color CUSTOM_BLUE = new Color(104, 104, 255);

  /**
   * Creates a new Triple Triad (TT) hand view for the given player. Adds a component listener to
   * resize the hand when the frame is resizes and a mouse listener to select a card.
   *
   * @param model     the view only model representing the current triple triad game for the
   *                  model to read from.
   * @param handColor the color representing the player whose hand is being drawn.
   */
  public TTHandView(ReadonlyThreeTriosModel model, PlayerColor handColor) {
    this.model = model;
    this.handColor = handColor;
    this.highlightedIndex = -1;

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateCardDimensions();
        repaint();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e.getX(), e.getY());
      }
    });
  }

  public void setListener(ThreeTriosController listener) {
    this.listener = listener;
  }

  /**
   * Update cardHeight and cardWidth based on the dimensions of the frame.
   * CardHeight should be the maximum it can be while still fitting all cards in the screen.
   * CardWidth should be the
   */
  private void updateCardDimensions() {
    cardWidth = getWidth();
    int numCards = model.getPlayerHand(handColor).size();
    if (numCards > 0) {
      cardHeight = getHeight() / numCards;
    } else {
      cardHeight = 0;
    }
  }

  private void handleCardClick(int mouseX, int mouseY) {
    int numCards = model.getPlayerHand(handColor).size();
    int clickedIndex = mouseY / cardHeight;
    if (clickedIndex == highlightedIndex || !listener.isPlayerTurn()
            || !listener.getPlayer().equals(handColor)) {
      highlightedIndex = -1;
    } else if (clickedIndex >= 0 && clickedIndex < numCards) {
      highlightedIndex = clickedIndex;
    }
    this.listener.selectCard(highlightedIndex);
    refresh();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Color baseColor = (handColor == PlayerColor.Red) ? CUSTOM_RED : CUSTOM_BLUE;

    List<GameCard> cards = this.model.getPlayerHand(handColor);

    for (int i = 0; i < cards.size(); i++) {
      int y = cardHeight * i;
      int x = (getWidth() - cardWidth) / 2;
      CardShape cardShape = new CardShape(cardWidth, cardHeight, cards.get(i),
              (i == highlightedIndex));
      cardShape.draw(g2, x, y, baseColor);
    }
  }

  /**
   * Refreshes the view by updating the highlighted card index.
   */
  public void refresh() {
    if (this.listener != null) {
      highlightedIndex = this.listener.getHandIndex();
    }
    updateCardDimensions();
    repaint();
  }
}
