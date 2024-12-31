package provider.model.board;

import provider.model.enums.AttackValue;
import provider.model.enums.PlayerColor;

/**
 * Represents a card in the Three Trios Game. We had to add this Javadoc for style points.
 */
public interface GameCard {
  /**
   * Get the card's name.
   *
   * @return the card's name.
   */
  public String getName();

  /**
   * Get the north attack value.
   *
   * @return the north attack value.
   */
  public AttackValue getNorth();

  /**
   * Get the east attack value.
   *
   * @return the east attack value.
   */
  public AttackValue getEast();

  /**
   * Get the south attack value.
   *
   * @return the south attack value.
   */
  public AttackValue getSouth();

  /**
   * Get the west attack value.
   *
   * @return the west attack value.
   */
  public AttackValue getWest();

  /**
   * Set the owner of the card to the given color.
   *
   * @param col the color to set the card to.
   */
  public void setOwner(PlayerColor col);

  /**
   * Return the owner of the card.
   *
   * @return the owner of the card's color
   */
  public PlayerColor getOwner();

  /**
   * Returns the owner of the card in string form.
   *
   * @return the first letter of the card's owner's color.
   */
  public String ownerToString();

  /**
   * Get a copy of the card.
   *
   * @return a copy of the card.
   */
  public GameCard getCopy();
}
