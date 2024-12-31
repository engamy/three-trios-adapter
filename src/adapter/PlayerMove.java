package adapter;

import controller.StratMove;

/**
 * Represents an adapter for a StratMove, needed for the Strategies package from the provider
 * to comply.
 */
public class PlayerMove extends StratMove {

  /**
   * Constructor for a PlayerMove.
   * @param handIdx the hand index of a card to play
   * @param row     the row to play the card to on the grid
   * @param col     the col to play the card to on the grid
   */
  public PlayerMove(int handIdx, int row, int col) {
    super(handIdx, row, col);
  }
}
