package provider.controller;

/**
 * The ModelStatus interface defines methods for interacting with and retrieving
 * the current state of the game model. It includes functionality to check if it
 * is the player's turn, play a turn, and determine if the game is over.
 */
public interface ModelStatus {
  /**
   * Checks if it is the player's turn.
   *
   * @return true if it is the player's turn; false otherwise.
   */
  public boolean isPlayerTurn();

  /**
   * Executes the logic to play the current turn.
   */
  public void playTurn();

}
