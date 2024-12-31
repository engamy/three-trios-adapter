package model;

import java.util.List;

/**
 * Represents a cell where a card cannot be placed.
 */
public class Hole implements Cell {

  @Override
  public boolean validAdjacent(Cell that) {
    return false;
  }


  @Override
  public CardValue getCardValue(String direction) {
    throw new UnsupportedOperationException("Cannot get card value at: " + direction + " from a "
            + "hole cell.");
  }

  @Override
  public void compareCard(Cell that, String direction) {
    throw new UnsupportedOperationException("Cannot compare cards with an hole cell.");
  }

  @Override
  public CardColor getCardColor() {
    throw new UnsupportedOperationException("Cannot get card color from a hole cell.");
  }

  @Override
  public void changeColor() {
    throw new UnsupportedOperationException("Cannot change color from a hole cell.");
  }

  @Override
  public boolean isFreeSpace() {
    return false;
  }

  @Override
  public boolean isInactiveSpace() {
    return true;
  }

  @Override
  public boolean flipCount(Cell opposing, String direction) {
    return false;
  }

  @Override
  public int flipDifficulty(List<String> directions) {
    return 0;
  }

  @Override
  public String toString() {
    return " ";
  }

  @Override
  public String getName() {
    throw new UnsupportedOperationException("Cannot get name of a Hole Cell.");
  }

}
