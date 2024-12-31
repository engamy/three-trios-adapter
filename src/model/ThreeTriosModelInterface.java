package model;

import java.util.List;

/**
 * Represents an interface for the Three Trios model
 * that outlines all the available public methods.
 */
public interface ThreeTriosModelInterface extends ReadonlyThreeTriosModel {

  /**
   * Initializes the game by creating the grid and dealing cards to the players.
   * @throws IllegalArgumentException if the game has already started
   * @throws IllegalArgumentException if either input is null
   */
  void initGame();

  /**
   * Plays the card at the given row and column location, where (0,0) is in the top left.
   * Prevents players from playing to the given row/col if the row/col is a Hole or a PlayingCard.
   * @param handIdx the hand index of the card the player wants to play
   * @param row the row to place the card (0-based index)
   * @param col the col to place the card (0-based index)
   * @throws IllegalArgumentException if the game hasn't started
   */
  void playCard(int handIdx, int row, int col);

  /**
   * Creates the deck using the given configuration.
   * @param config the given configuration
   */
  void createDeck(List<String> config);

  /**
   * Initializes the grid based on the number of rows, cols, and given grid config.
   * @param rows the number of rows
   * @param cols the number of cols
   * @param config the config of the grid
   */
  void createGrid(int rows, int cols, List<String> config);

  /**
   * Adds the given listener.
   * @param listener the listener to add
   */
  void addListeners(ModelFeatures listener);

  /**
   * Automatically starts a game if both listeners have an AI Player.
   */
  void aiWarBehavior();
}
