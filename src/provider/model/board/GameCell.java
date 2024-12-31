package provider.model.board;

import provider.model.enums.CellType;
import provider.model.enums.PlayerColor;

/**
 * Represents a cell in the Three Trios Game. We had to add this Javadoc for style points.
 */
public interface GameCell {
  /**
   * return the type of card.
   *
   * @return the card type
   */
  public CellType getType();

  /**
   * Finds if the given cell can have a card placed in it. If the cell type is Card Cell and the
   * card is null this means the cell is empty and can have a card placed in it. If a cell is a
   * hole the cell is not empty.
   *
   * @return true if the cell does not have a card and is a card cell.
   */
  public boolean isEmpty();

  /**
   * Puts the card in the cell if it is empty (is a card cell with a null card).
   *
   * @param card the card to put in the cell.
   */
  public void placeCardInCell(GameCard card);

  /**
   * Finds and returns the card in this cell, if there is a card in the cell.
   *
   * @return a copy of the card in this cell or throw an illegal state exception if the cell is a
   *         hole or does not have a card placed in it.
   */

  public GameCard getCard();

  /**
   * If there is a card in the cell, changes the owner of the card to the given owner.
   *
   * @param col the color representing the new owner.
   */
  public void changeOwner(PlayerColor col);
}
