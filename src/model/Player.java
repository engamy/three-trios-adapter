package model;

import controller.Strategies;

/**
 * Represents a player in the Three Trios Model that is a Human Player or AI Player.
 */
public interface Player {

  /**
   * Observer that returns the color this Player is playing for.
   * @return this player's color
   */
  String getPlayerColor();

  /**
   * Observer that returns the strat this Player is playing by.
   * @return this player's strat
   * @throws UnsupportedOperationException if getting a player's Strategy from a human player.
   */
  Strategies getPlayerStrat();

  /**
   * Observer that returns whether this Player is an AI Player or not.
   * @return true if this player is an AIPlayer, false if not.
   */
  boolean isAIPlayer();
}
