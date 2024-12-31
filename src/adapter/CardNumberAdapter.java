package adapter;

import model.CardValue;
import provider.model.enums.AttackValue;

/**
 * Represents an adapter that adapts an AttackValue (provider) to a CardValue.
 */
public enum CardNumberAdapter {

  ONE(CardValue.ONE, AttackValue.One),
  TWO(CardValue.TWO, AttackValue.Two),
  THREE(CardValue.THREE, AttackValue.Three),
  FOUR(CardValue.FOUR, AttackValue.Four),
  FIVE(CardValue.FIVE, AttackValue.Five),
  SIX(CardValue.SIX, AttackValue.Six),
  SEVEN(CardValue.SEVEN, AttackValue.Seven),
  EIGHT(CardValue.EIGHT, AttackValue.Eight),
  NINE(CardValue.NINE, AttackValue.Nine),
  A(CardValue.A, AttackValue.A);

  private final AttackValue adaptee;

  /**
   * Constructor for this card number adapter.
   * @param target the CardValue to adapt
   * @param adaptee the AttackValue to adapt
   */
  CardNumberAdapter(CardValue target, AttackValue adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Observer for the AttackValue.
   * @return the number of this adapter as an AttackValue.
   */
  public AttackValue getAttackValue() {
    return adaptee;
  }

  /**
   * Converts this adapter into an AttackValue.
   * @param newValue the value to adapt
   * @return the value to adapt as an AttackValue
   */
  public static AttackValue convert(CardValue newValue) {
    switch (newValue) {
      case A:
        return CardNumberAdapter.A.getAttackValue();
      case ONE:
        return CardNumberAdapter.ONE.getAttackValue();
      case TWO:
        return CardNumberAdapter.TWO.getAttackValue();
      case THREE:
        return CardNumberAdapter.THREE.getAttackValue();
      case FOUR:
        return CardNumberAdapter.FOUR.getAttackValue();
      case FIVE:
        return CardNumberAdapter.FIVE.getAttackValue();
      case SIX:
        return CardNumberAdapter.SIX.getAttackValue();
      case SEVEN:
        return CardNumberAdapter.SEVEN.getAttackValue();
      case EIGHT:
        return CardNumberAdapter.EIGHT.getAttackValue();
      case NINE:
        return CardNumberAdapter.NINE.getAttackValue();
      default:
        return null;
    }
  }



}
