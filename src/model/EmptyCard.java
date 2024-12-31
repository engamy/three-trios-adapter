package model;

import java.util.List;

/**
 * Represents a cell that is empty and can have a card placed in it.
 */
public class EmptyCard implements Cell {

  @Override
  public boolean validAdjacent(Cell that) {
    return false;
  }

  @Override
  public CardValue getCardValue(String direction) {
    throw new UnsupportedOperationException("Cannot get card value at: " + direction + " from an "
            + "empty card cell.");
  }

  @Override
  public CardColor getCardColor() {
    throw new UnsupportedOperationException("Cannot get card color from an empty card cell.");
  }

  @Override
  public void changeColor() {
    throw new UnsupportedOperationException("Cannot change color from an empty card cell.");
  }

  @Override
  public boolean isFreeSpace() {
    return true;
  }

  @Override
  public boolean isInactiveSpace() {
    return false;
  }

  @Override
  public void compareCard(Cell that, String direction) {
    throw new UnsupportedOperationException("Cannot compare cards with an empty cell.");
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
    return "_";
  }

  @Override
  public String getName() {
    throw new UnsupportedOperationException("Cannot get name from an empty card cell.");
  }

}
