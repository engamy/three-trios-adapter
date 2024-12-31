package model;

import controller.Strategies;

/**
 * Represents a player who is played by a human.
 */
public class HumanPlayer implements Player {

  private final CardColor color;

  /**
   * Constructor for the Human Player.
   * @param color the color that the Human player is playing for
   */
  public HumanPlayer(CardColor color) {
    this.color = color;
  }

  @Override
  public String getPlayerColor() {
    return color.toString();
  }

  @Override
  public Strategies getPlayerStrat() {
    throw new UnsupportedOperationException("Human Player doesn't use strategies");
  }

  @Override
  public boolean isAIPlayer() {
    return false;
  }
}