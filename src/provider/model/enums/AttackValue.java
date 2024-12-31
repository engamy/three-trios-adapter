package provider.model.enums;

/**
 * Enum represents the attack values for a card in the game.
 * Attack values range between 1-9 and the letter A.
 * Each attack value has an integer value and a string representation.
 */
public enum AttackValue {
  One(1, "1"),
  Two(2, "2"),
  Three(3, "3"),
  Four(4, "4"),
  Five(5, "5"),
  Six(6, "6"),
  Seven(7, "7"),
  Eight(8, "8"),
  Nine(9, "9"),
  A(10, "A");

  final int value;
  final String sValue;


  /**
   * Constructor for AttackValue that takes a specific integer and string.
   *
   * @param value the integer representation of the attack value
   * @param sValue the string representation of the attack value
   */
  AttackValue(int value, String sValue) {
    this.value = value;
    this.sValue = sValue;
  }

  /**
   * Returns the integer representation of the attack value.
   *
   * @return integer attack value
   */
  public int getValue() {

    return value;
  }

  /**
   * Returns the string representation of the attack value.
   *
   * @return string attack value
   */
  public String getSValue() {

    return sValue;
  }
}
