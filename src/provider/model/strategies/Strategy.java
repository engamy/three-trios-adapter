package provider.model.strategies;

import java.util.HashMap;
import java.util.List;

import adapter.PlayerMove;

/**
 * Represents the strategies used in the three trios game
 * for selecting and scoring moves.
 */
public interface Strategy {

  public List<PlayerMove> validMoves();

  public int scoreMove(PlayerMove move);

  public HashMap<PlayerMove, Integer> scoreMoves();

  public PlayerMove bestMove();
}
