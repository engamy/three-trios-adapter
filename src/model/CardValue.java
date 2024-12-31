package model;

/**
 * Represents the value of a card that is either Red or Blue.
 */
public enum CardValue {

  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  A(10);

  private final int value;

  /**
   * Constructor for a model.CardValue.
   * @param value Represents the value of hte card.
   */
  CardValue(int value) {
    this.value = value;
  }

  /**
   * Returns the model.CardValue as a String.
   * @return the String value
   */
  @Override
  public String toString() {

    if (value == 10) {
      return "A";
    }
    else {
      return Integer.toString(value);
    }

  }

  /**
   * An observation of this model.CardValue.
   * @return this card value
   */
  public int getValue() {
    return this.value;
  }
}
