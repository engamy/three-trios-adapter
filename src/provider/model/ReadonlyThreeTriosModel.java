package provider.model;

import java.util.ArrayList;

import provider.model.board.GameCard;
import provider.model.board.GameCell;
import provider.model.board.Grid;
import adapter.Position;
import provider.model.enums.GameState;
import provider.model.enums.PlayerColor;

/**
 * This interface represents the read only model of the three trios model.
 * It provides access to the players hands, game state and game board.
 */
public interface ReadonlyThreeTriosModel {
  /**
   * Checks if the board is full and ends the game.
   *
   * @return true if the game is over.
   */
  public boolean isGameOver();

  /**
   * Calculates which player has more cards on the board and in their hand.
   *
   * @return the color of the winner of the game.
   */
  public PlayerColor gameWon();

  /**
   * Gets the current board.
   *
   * @return the board.
   */
  public Grid getGrid();

  /**
   * Gets the current player who will play next turn.
   *
   * @return the current player.
   */
  public PlayerColor getCurrentPlayer();

  /**
   * Gets the game state of the game.
   *
   * @return the current game state of the game.
   */
  public GameState getGamestate();

  /**
   * Gets a copy of the given player's hand.
   *
   * @param p the player's color.
   * @return a copy of the player's hand.
   */
  public ArrayList<GameCard> getPlayerHand(PlayerColor p);

  /**
   * Gets the current number of cards the given player has.
   */
  public int getPlayerScore(PlayerColor p);

  /**
   * Gets a copy of the cell at the given position.
   *
   * @param p the position to check on the board.
   * @return the cell at the given position.
   */
  public GameCell getCell(Position p);

}
