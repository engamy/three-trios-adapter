package view;

import model.PlayingCard;

/**
 * Represents the player behaviors the view supports.
 */
public interface ViewFeatures {

  /**
   * If a player has NOT clicked in their own panel, show a dialog message that
   * tells them to play in their own panel.
   */
  void checkPlayerPanel();

  /**
   * Notifies all listeners that the player has selected a card, and sets the new selected card
   * as the given card if it belongs to the right player and the right player selected it.
   * @param color the color of the player who selected it
   * @param selectedCard the potential card to be selected
   */
  void playerSelectedCard(String color, PlayingCard selectedCard);

  /**
   * Notifies all listeners that the player has played the given selected card to the given row/col
   * location.
   * @param selectedCard the card to play
   * @param row the row to play the card to
   * @param col the col to play the car dto
   */
  void playerPlayCard(PlayingCard selectedCard, int row, int col);
}
