package controller;

/**
  * An interface that represents different strategies to be used by a player.
  */
public interface Strategies {

  /**
   * Determines the best StratMove to make.
   * @return the best StratMove to make, which is a hand card to choose from, and the row/col in
   *         the grid to play to
   */
  StratMove strategize();
}
