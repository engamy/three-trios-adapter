package model;

/**
 * Represents the color of a card that is either Red or Blue.
 */
public enum CardColor {

  R("R"),
  B("B");

  private final String color;

  /**
   * Constructor for a model.CardColor.
   * @param color Represents the color
   */
  CardColor(String color) {
    this.color = color;
  }

  /**
   * Returns the model.CardColor as a String.
   * @return the String color
   */
  @Override
  public String toString() {
    return color;
  }
}
