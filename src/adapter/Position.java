package adapter;

/**
 * Represents a position on a 0-based grid in a row-major gird.
 */
public class Position {

  private final int row;
  private final int col;

  /**
   * Constructor for a position.
   * @param row the row of the position
   * @param col the col of the position
   */
  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }
}
