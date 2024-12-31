package controller;

/**
 * Represents a move determined by a strategy, which includes a hand index of a card to play,
 * as well as the row and column index on the grid to play a card to.
 */
public class StratMove {
  private int handIdx;
  private int row;
  private int col;

  /**
   * Constructor for a StratMove.
   * @param handIdx the hand index of a card to play
   * @param row the row to play the card to on the grid
   * @param col the col to play the card to on the grid
   */
  public StratMove(int handIdx, int row, int col) {
    this.handIdx = handIdx;
    this.row = row;
    this.col = col;
  }

  public int stratCard() {
    return handIdx;
  }

  public int stratRow() {
    return row;
  }

  public int stratCol() {
    return col;
  }

  public int originDistance() {
    return row + col;
  }

  public String toString() {
    return "Card Idx: " + handIdx + ", Row: " + row + ", Col: " + col;
  }
}
