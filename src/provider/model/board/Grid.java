package provider.model.board;

import provider.model.enums.Direction;
import provider.model.enums.PlayerColor;
import adapter.Position;

/**
 * Represents a grid where cards can be placed by players.
 */
public interface Grid {
  /**
   * Get number of rows in Grid.
   *
   * @return NumRows.
   */
  public int getNumRows();

  /**
   * Get number of columns in Grid.
   *
   * @return NumCols.
   */
  public int getNumCols();

  /**
   * Checks if the given position is possible on the board.
   *
   * @param posn the given position
   * @return true if the position is invalid and false otherwise
   */
  public boolean invalidPosition(Position posn);

  /**
   * Checks if there is a card at the given position.
   *
   * @param posn the position to check
   * @return false if there is no card, a hole, or the position is invalid and true otherwise.
   */
  public boolean hasCardAt(Position posn);

  /**
   * Returns a copy of the card at the given position.
   *
   * @param posn the given position
   * @return a copy of the card specified
   */
  public GameCard getCard(Position posn);

  /**
   * Calculates the number of given color cards.
   *
   * @return the number of given colored cards placed on the board.
   */
  public int getNumCardsByPlayer(PlayerColor pc);

  /**
   * Places the given card at the location on the board if possible.
   *
   * @param card the card to place.
   * @param posn the position the place the card.
   */
  public void placeCardOnBoard(GameCard card, Position posn);

  /**
   * Calculates the size of the board, accounting for holes.
   *
   * @return the total number of possible spots cards can be placed on the board.
   */
  public int size();

  /**
   * Calculates if the maximum amount of cards that can be placed have been placed on the board.
   *
   * @return true if the board is full and false otherwise.
   */
  public boolean isFull();

  /**
   * Compares the attack values of the current card and the card in that direction.
   *
   * @param posn the position of the current card
   * @param dir  the direction of the card to compare
   * @return true if the card at the given position is greater, false if the card in the given
   *         direction is greater or there is no card in that direction.
   */
  public boolean compareValues(Position posn, Direction dir);

  /**
   * Flips the color of the card at the given position.
   *
   * @param posn the position of the card to flip
   * @param col  the color to change the card to
   * @return true if the color was flippes, false if the color was already the gievn color
   */
  public boolean flipColor(Position posn, PlayerColor col);

  /**
   * Create a return a copy of the grid.
   *
   * @return a copy of the grid.
   */
  public Grid copy();

  /**
   * Return the cell at the given position.
   *
   * @param p the position to check.
   * @return the cell at the given position.
   */
  public GameCell getCell(Position p);

}
