package model;

import java.util.List;

/**
 * Represents a cell inside a grid in the Three Trios Game.
 */
public interface Cell {

  /**
   * Checks if an adjacent cell is not a hole or empty card and that it isn't the same color.
   * @param that the given cell
   * @return true if the given cell is adjacent to this cell, false if not
   */
  boolean validAdjacent(Cell that);

  /**
   * An observer that returns the card value of a PlayingCard.
   * @return the card value
   * @throws IllegalStateException if you call this on an EmptyCard or Hole,
   *                               since they don't have values
   * @throws IllegalArgumentException if the direction is not one of
   *                                  "north", "south", "east", or "west"
   */
  CardValue getCardValue(String direction);

  /**
   * An observer that returns the card color of a PlayingCard.
   * @return the cardColor
   * @throws IllegalStateException if you call this on an EmptyCard or Hole,
   *                               since they don't have colors
   */
  CardColor getCardColor();

  /**
   * Changes the card color from red to blue or blue to red.
   * @throws IllegalStateException if you call this on an EmptyCard or Hole,
   *                               since they don't have colors
   */
  void changeColor();

  /**
   * Determines if this cell is a free space (i.e. an EmptyCard).
   * @return true if a card can be placed here, false if not
   */
  boolean isFreeSpace();

  /**
   * Determines if this cell is a inactive space (i.e. a Hole).
   * @return true if a card cannot interact, false if it can
   */
  boolean isInactiveSpace();

  /**
   * Compares this Cell with that Cell in the given direction.
   * @param that the opposing Cell that this cell is being compared to
   * @param direction the direction, relative to this cell, that you are comparing the cells in
   * @throws IllegalArgumentException if direction is not one of:
   *                                  "north", "south", "east", or "west"
   */
  void compareCard(Cell that, String direction);

  /**
   * Determines if this Cell can flip a given Cell in the given direction.
   *
   * @param opposing  the Cell to potentially flip
   * @param direction the direction to compare
   * @return 1 if this Cell can flip the given Cell, 0 if not
   * @throws IllegalArgumentException if direction is not one of "north", "south", "east", or "west"
   */
  boolean flipCount(Cell opposing, String direction);

  /**
   * Make tests in ThreeTriosModelImplementationEnsureTest.
   * @param directions represents the sides being exposed of the card
   * @return total of the card value being exposed
   */
  int flipDifficulty(List<String> directions);

  @Override
  String toString();

  /**
   * Observation for the name of this cell.
   * @return the name of this cell.
   * @throws UnsupportedOperationException if this cell is NOT a PlayingCard.
   */
  String getName();

}
