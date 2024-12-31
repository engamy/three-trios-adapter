package adapter;

import java.util.ArrayList;

import controller.Strategies;
import provider.model.board.GameCard;
import provider.model.board.Grid;
import provider.model.player.Player;

/**
 * Represents an adapter that adapts Player to Player (provider).
 */
public class PlayerAdapter implements Player {

  private final CardColorAdapter color;
  private final ModelAdapter model;
  private Strategies strat;

  /**
   * Constructor for a human player.
   * @param color the color the player is playing for
   * @param model the model the player is playing on
   */
  public PlayerAdapter(CardColorAdapter color, ModelAdapter model) {
    this.color = color;
    this.model = model;
    this.strat = null;
  }

  /**
   * Constructor for an AI player.
   * @param color the color the player is playing for
   * @param model the model the player is playing on
   * @param strat the strategy this AI is using to play
   */
  public PlayerAdapter(CardColorAdapter color, ModelAdapter model, Strategies strat) {
    this.color = color;
    this.model = model;
    this.strat = strat;
  }

  /**
   * Observer for this player's strategy.
   * @return null if it is a human player, some kind of Strategies if it is an AI player
   */
  public Strategies getPlayerStrat() {
    return this.strat;
  }

  @Override
  public Position playerPlaceCard(Grid board, int handIndex, Position posn) {
    return null;
  }

  @Override
  public ArrayList<GameCard> getHand() {
    return this.model.getPlayerHand(this.color.getPlayerColor());
  }

  @Override
  public String toString() {
    return this.color.toString();
  }
}
