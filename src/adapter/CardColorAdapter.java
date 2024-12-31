package adapter;

import model.CardColor;
import provider.model.enums.PlayerColor;

/**
 * Represents an adapter that adapts a PlayerColor (provider) to a CardColor.
 */
public enum CardColorAdapter {

  BLUE(CardColor.B, PlayerColor.Blue),
  RED(CardColor.R, PlayerColor.Red);

  private final CardColor target;
  private final PlayerColor adaptee;

  /**
   * A constructor that takes in CardColor and PlayerColor to adapt.
   * @param target the CardColor to adapt
   * @param adaptee the PlayerColor to adapt
   */
  CardColorAdapter(CardColor target, PlayerColor adaptee) {
    this.target = target;
    this.adaptee = adaptee;
  }

  /**
   * Observer for the CardColor version of the color.
   * @return a CardColor
   */
  public CardColor getCardColor() {
    return this.target;
  }

  /**
   * Observer for the PlayerColor version of this color.
   * @return a PlayerColor
   */
  public PlayerColor getPlayerColor() {
    return this.adaptee;
  }

  /**
   * Converts this CardColorAdapter to a PlayerColor.
   * @param newColor the color to adapt
   * @return this adapter as a PlayerColor
   */
  public static PlayerColor convert(CardColor newColor) {
    if (newColor == CardColor.B) {
      return CardColorAdapter.BLUE.getPlayerColor();
    }
    else {
      return CardColorAdapter.RED.getPlayerColor();
    }
  }

  @Override
  public String toString() {
    return this.target.toString();
  }

}
