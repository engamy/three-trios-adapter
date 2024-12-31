package provider.model.enums;


/**
 * Enum represents two types of cells in the game.
 * Cells can be holes or card cells.
 * Holes are unusable spaces where a card cannot be played.
 * CardCell is a cell that can hold a card.
 */
public enum CellType {
  Hole(),
  CardCell();
}
