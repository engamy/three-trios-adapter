package provider.controller;

import adapter.Position;

/**
 * The PlayerAction interface defines the actions a player can take in a game. It provides methods
 * to select a card from the player's hand and to select a position on the game board.
 */
public interface PlayerAction {

  /**
   * Selects a card from the player's hand.
   *
   * @param handIndex the index of the card in the player's hand to be selected.
   */
  public void selectCard(int handIndex);

  /**
   * Selects a position on the game board.
   *
   * @param position the position on the game board to be selected.
   */
  public void selectGridPosition(Position position);
}
