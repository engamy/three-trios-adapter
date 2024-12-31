package provider.controller;

import provider.controller.ModelStatus;
import provider.controller.PlayerAction;
import provider.model.enums.PlayerColor;

/**
 * The Controller interface provides methods for managing the game's flow by communicating
 * between the view and model.
 */
public interface ThreeTriosController extends ModelStatus, PlayerAction {
  /**
   * Notifies the player that it is their turn by attempting to play a turn. If the player is not
   * ready to play a turn i.e. hand and card index have not been selected, or it is not actually
   * the player's turn, nothing happens.
   */
  public void notifyPlayerOfTurn();

  /**
   * Refreshes the game view.
   */
  public void refreshView();

  /**
   * Resets the player's move, clearing the selected card and position by setting them back to
   * default values.
   */
  public void resetPlayerMove();

  /**
   * Retrieves the index of the selected card in the player's hand if it is the player's turn.
   *
   * @return the selected card index, or -1 if none is selected.
   */
  public int getHandIndex();

  /**
   * Handles errors by displaying the specified message in the view.
   *
   * @param msg the error message to be displayed.
   */
  public void handleError(String msg);

  /**
   * Retrieves the player's color.
   *
   * @return the color of the player associated with this controller.
   */
  public PlayerColor getPlayer();

  /**
   * Tells the view to display an end of game message containing information on the winner and
   * their score. Handles Tie exceptions by displaying a tie game message.
   */
  public void handleGameOver();
}
