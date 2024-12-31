package provider.model.enums;

/**
 * Enum represents the current state of the game.
 * NotStarted, means game hasn't started.
 * InProgress means game is in progress.
 * GameOver means game is over.
 */
public enum GameState {
  NotStarted(),
  InProgress(),
  GameOver();
}
