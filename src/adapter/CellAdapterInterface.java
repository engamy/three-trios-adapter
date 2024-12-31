package adapter;

import model.Cell;
import provider.model.board.GameCell;

/**
 * Interface for a cell adapter that adapts a GameCell (Provider) to a Cell.
 */
public interface CellAdapterInterface extends GameCell {

  /**
   * Converts this CellAdapter to a Cell.
   * @return this CellAdapter as a Cell.
   */
  Cell convertToCell();
}
