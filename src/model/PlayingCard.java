package model;

import java.util.List;

/**
 * Represents a cell that contains a card.
 */
public class PlayingCard implements Cell {

  private final String name;

  private final CardValue north;
  private final CardValue south;
  private final CardValue east;
  private final CardValue west;

  private CardColor color;

  /**
   * Constructor used to create a PlayingCard.
   * @param name the name of the card
   * @param north the north value of the card
   * @param south the south value of the card
   * @param east the east value of the card
   * @param west the west value of the card
   * @param color the color of the card
   */
  public PlayingCard(String name, CardValue north, CardValue south, CardValue east,
                    CardValue west, CardColor color) {
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.color = color;
  }

  @Override
  public boolean validAdjacent(Cell that) {
    try {
      return (!(this.color.equals(that.getCardColor())));
    } catch (UnsupportedOperationException ex) {
      return false;
    }
  }

  @Override
  public CardValue getCardValue(String direction) {
    switch (direction) {
      case "north":
        return north;
      case "south":
        return south;
      case "east":
        return east;
      case "west":
        return west;
      default:
        throw new IllegalArgumentException("Cannot get the value at the following direction: "
                + direction);
    }
  }

  @Override
  public void changeColor() {
    if (this.color.equals(CardColor.R)) {
      this.color = CardColor.B;
    } else if (this.color.equals(CardColor.B)) {
      this.color = CardColor.R;
    }
  }

  @Override
  public boolean isFreeSpace() {
    return false;
  }

  @Override
  public boolean isInactiveSpace() {
    return false;
  }

  // If this val > that val, call changeColor on that Card
  @Override
  public void compareCard(Cell opposing, String direction) {
    switch (direction) {
      case "north":
        if (this.north.getValue() > opposing.getCardValue("south").getValue()) {
          opposing.changeColor();
        }
        break;
      case "south":
        if (this.south.getValue() > opposing.getCardValue("north").getValue()) {
          opposing.changeColor();
        }
        break;
      case "east":
        if (this.east.getValue() > opposing.getCardValue("west").getValue()) {
          opposing.changeColor();
        }
        break;
      case "west":
        if (this.west.getValue() > opposing.getCardValue("east").getValue()) {
          opposing.changeColor();
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid direction to compare cards: " + direction);
    }
  }

  @Override
  public boolean flipCount(Cell opposing, String direction) {
    boolean result = false;
    try {
      switch (direction) {
        case "north":
          if (this.north.getValue() > opposing.getCardValue("south").getValue()) {
            return true;
          }
          break;
        case "south":
          if (this.south.getValue() > opposing.getCardValue("north").getValue()) {
            return true;
          }
          break;
        case "east":
          if (this.east.getValue() > opposing.getCardValue("west").getValue()) {
            return true;
          }
          break;
        case "west":
          if (this.west.getValue() > opposing.getCardValue("east").getValue()) {
            return true;
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid direction " + direction);
      }
    }
    catch (UnsupportedOperationException ex) {
      // do nothing when you try to get value from hole or empty card cell (return 0)
    }
    return result;
  }



  @Override
  public CardColor getCardColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return this.name + " " + this.north + " " + this.south + " " + this.east + " " + this.west;
  }

  @Override
  public int flipDifficulty(List<String> directions) {
    int total = 0;
    for (int i = 0; i < directions.size(); i++) {
      total += getCardValue(directions.get(i)).getValue();
    }
    return total;
  }

  @Override
  public String getName() {
    return this.name;
  }

}
