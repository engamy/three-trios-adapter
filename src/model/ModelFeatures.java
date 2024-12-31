package model;

/**
 * Represents the model behaviors related to the status of the game.
 */
public interface ModelFeatures {
  /**
   * Notifies listeners when the game is over.
   */
  void gameOver();

  /**
   * Notifies listeners that a turn has just been completed.
   */
  void turnHasCompleted();

  /**
   * Observes the status of players in this listener.
   * @return this controller's player
   */
  Player getPlayer();

  /**
   * Refreshes both players' views.
   */
  void refreshViews();
}
