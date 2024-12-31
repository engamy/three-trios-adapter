package provider.model.player;

import java.util.ArrayList;

import provider.model.board.GameCard;
import provider.model.board.Grid;
import adapter.Position;

/**
 * Represents a Player in the game of triple triad.
 * The Player can place a card to board and has a hand of cards to play.
 */
public interface Player {
  /**
   * Places the card at the given index to the coordinates on the board. Should set every card
   * given's owner to player's color.
   *
   * @param board     represents the cards played so far.
   * @param handIndex the index of the card in hand.
   * @param posn      the position on the board to play to.
   */
  public Position playerPlaceCard(Grid board, int handIndex, Position posn);

  /**
   * Get a copy of the current hand the player has.
   *
   * @return a copy of the current hand.
   */
  public ArrayList<GameCard> getHand();
}
