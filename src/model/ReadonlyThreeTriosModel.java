package model;

import java.util.List;

/**
 * The read-only interface for the Three Trios model that only contains observations.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Returns the score of the given player, which is the number of cards that player owns
   * in their hand plus the number of cards that player owns on the grid.
   * the color of the player.
   * @param player the player, which must be either "R" or "B"
   * @return the score of the given player
   */
  int getScore(String player);

  /**
   * Determines how many cards a player flips by playing at the given coordinate.
   * @param row the row of the cell the player wants to play to
   * @param col the col of the cell the player wants to play to
   * @return the number of cards a player can flip at the given coordinate
   * @throws IllegalArgumentException if the given row/col is out of the grid boudns
   * @throws IllegalArgumentException if the given row/col is not a free cell
   */
  int numCardsFlipped(int handIdx, int row, int col);

  /**
   * Determines if the cell is free at the given location, which is only true if the cell is an
   * EmptyCard, NOT a Hole or PlayingCard.
   * @param row the row of the cell we are observing
   * @param col the col of the cell we are observing
   * @return true if the cell is free at the given location, false if not
   * @throws IllegalArgumentException if the given row/col is out of the grid bounds
   */
  boolean isCellFree(int row, int col);

  /**
   * An observer method that returns the current grid.
   * @return the current grid in the model
   */
  List<List<Cell>> getGrid();

  /**
   * An observer that returns the list of all the playing cards in the game.
   * @return the list of all playing cards in the game
   */
  List<PlayingCard> getDeck();

  /**
   * Determines if the game is over (i.e. none of the cells is an EmptyCard)
   * @return true if the game is over, false if not
   * @throws IllegalArgumentException if the game is not started
   */
  boolean isGameOver();

  /**
   * Determines if the game has started or not.
   */
  boolean isGameStarted();

  /**
   * Determines the winner and prints out a winning message or tie message appropriately.
   * @return the winner message or tie message
   * @throws IllegalArgumentException if the game is not started
   */
  String getWinner();

  /**
   * Observer that returns the current player.
   * - "B" represents the Blue player
   * - "R" represents the Red player
   * @return the current player
   */
  String getCurrentPlayer();

  /**
   * Observer that returns the hand of the given player.
   * @param player the player whose hand is being observed
   * @return the hand of the given player
   * @throws IllegalArgumentException if the given string is not "R" or "B"
   */
  List<PlayingCard> getHand(String player);

  /**
   * Calculates the number of free spaces in the grid of a model.
   */
  int countFreeSpace();

  void checkGameEnded();
}
