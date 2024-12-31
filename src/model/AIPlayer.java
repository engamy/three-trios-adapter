package model;

import controller.Strategies;

/**
 * Represents an AI Player that simulates a human player's moves by strategizing.
 */
public class AIPlayer implements Player {

  private final CardColor color;
  private final Strategies strat;

  /**
   * Constructor for the AI Player.
   * @param color the color that the AI Player is playing for.
   * @param strat the color that the AI Player is playing for.
   */
  public AIPlayer(CardColor color, Strategies strat) {
    this.color = color;
    this.strat = strat;
  }

  public String getPlayerColor() {
    return this.color.toString();
  }

  public Strategies getPlayerStrat() {
    Strategies stratCopy = this.strat;
    return stratCopy;
  }

  @Override
  public boolean isAIPlayer() {
    return true;
  }
}
