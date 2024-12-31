package provider.model;

import provider.controller.ThreeTriosController;
import provider.model.ReadonlyThreeTriosModel;
import provider.model.strategies.Strategy;
import adapter.Position;

/**
 * Represents a model for the game of Triple Triad.
 */
public interface Model extends ReadonlyThreeTriosModel {

  /**
   * Add the given controller as a listener.
   *
   * @param controller the controller to set as a listener.
   */
  public void addListener(ThreeTriosController controller);

  /**
   * Plays a turn of triple triad for the current player.
   * Plays the card at the given index from the current player's hand
   * to the given position on the board. Battles the cards on the board.
   * Sets current player to the other player. Ends the game when the board is full.
   *
   * @param handIndex the index of the card to play from the player's hand.
   * @param posn      the position on the board to play to.
   */
  public void playTurn(int handIndex, Position posn);

  /**
   * Starts the game by initializing each of the players hands and switching the game state to
   * in progress.
   */
  public void startGame();

  public void setPlayerTypes(Strategy red, Strategy blue);

  // NOTE: We needed to delete all the observer methods from the provider's Model because they were
  // duplicates of observer methods in ReadonlyThreeTriosModel, which this Model interface
  // extends. We couldn't just comment it out because of the Java style points.
}
