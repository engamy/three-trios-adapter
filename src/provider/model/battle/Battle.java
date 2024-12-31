package provider.model.battle;

import adapter.Position;
import provider.model.board.Grid;


/**
 * Represents the battle phase on a grid, flipping card colors according to the rules of the game.
 */
public interface Battle {
  /**
   * Executes the battle phase by flipping card ownership for cards whose attack values in the
   * corresponding direction are less than the card at the given position. The battle phase
   * continues with flipped cards and has a cascading effect. Holes and empty cells are not
   * impacted by the battle phase.
   *
   * @param board the Grid representing the current state of the game
   * @param pos   the position to initate the battle from
   */
  void executeBattle(Grid board, Position pos);

}
