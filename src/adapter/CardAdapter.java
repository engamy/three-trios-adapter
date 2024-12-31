package adapter;

import model.CardColor;
import model.CardValue;
import model.PlayingCard;
import provider.model.board.GameCard;
import provider.model.enums.AttackValue;
import provider.model.enums.PlayerColor;

/**
 * Represents an adapter class that adapts a GameCard (provider) to a PlayingCard.
 */
public class CardAdapter extends PlayingCard implements CardAdapterInterface {
  /**
   * Constructor used to create a CardAdapter.
   *
   * @param name  the name of the card
   * @param north the north value of the card
   * @param south the south value of the card
   * @param east  the east value of the card
   * @param west  the west value of the card
   * @param color the color of the card
   */
  public CardAdapter(String name, CardValue north, CardValue south, CardValue east,
                     CardValue west, CardColor color) {
    super(name, north, south, east, west, color);
  }

  @Override
  public String getName() {
    return super.getName();
  }

  @Override
  public PlayingCard convertToPlayingCard() {
    return new PlayingCard(this.getName(), this.getCardValue("north"),
            this.getCardValue("south"), this.getCardValue("east"),
            this.getCardValue("west"), this.getCardColor());
  }

  @Override
  public AttackValue getNorth() {
    return CardNumberAdapter.convert(this.getCardValue("north"));
  }

  @Override
  public AttackValue getEast() {
    return CardNumberAdapter.convert(this.getCardValue("east"));
  }

  @Override
  public AttackValue getSouth() {
    return CardNumberAdapter.convert(this.getCardValue("south"));
  }

  @Override
  public AttackValue getWest() {
    return CardNumberAdapter.convert(this.getCardValue("west"));
  }

  @Override
  public void setOwner(PlayerColor col) {
    super.changeColor();
  }

  @Override
  public PlayerColor getOwner() {
    return CardColorAdapter.convert(super.getCardColor());
  }

  @Override
  public String ownerToString() {
    return super.getCardColor().toString();
  }

  @Override
  public GameCard getCopy() {
    return new CardAdapter(getName(),
            getCardValue("north"),
            getCardValue("south"),
            getCardValue("east"),
            getCardValue("west"),
            super.getCardColor());
  }
}
