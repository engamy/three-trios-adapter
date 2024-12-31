package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.PlayingCard;
import model.Cell;
import model.ThreeTriosModelInterface;

/**
 * Represents the Corner strategy:
 * Go for the corners: cards in corners only expose two of their attack values
 * instead of all 4, making them harder to flip. Then consider which card is
 * hardest to flip in that corner.
 */
public class CornerStrat implements Strategies {
  ThreeTriosModelInterface model;

  /**
   * The constructor for the corner strategy that takes in a model.
   * @param model the model we are strategizing on
   */
  public CornerStrat(ThreeTriosModelInterface model) {
    this.model = model;
  }

  @Override
  public StratMove strategize()  {
    Map<StratMove, Integer> moveSet = allMoves();
    List<StratMove> bestMoves = findStrongest(moveSet);
    return findClosest(bestMoves);
  }

  private Map<StratMove, Integer> allMoves() {
    Map<StratMove, Integer> result = new HashMap<>();
    List<PlayingCard> currHand = this.model.getHand(this.model.getCurrentPlayer());

    for (int handIdx = 0; handIdx < currHand.size(); handIdx++) {
      StratMove corner = null;
      int difficulty = 0;
      List<List<Cell>> grid = model.getGrid();

      if (grid.get(0).get(0).isFreeSpace()) {
        corner = new StratMove(handIdx, 0, 0);
        difficulty = currHand.get(handIdx).flipDifficulty(
                new ArrayList<String>(Arrays.asList("east", "south")));
        result.put(corner, difficulty);
      }
      if (grid.get(0).get(grid.size() - 1).isFreeSpace()) {
        corner = new StratMove(handIdx, 0, grid.size() - 1);
        difficulty = currHand.get(handIdx).flipDifficulty(
                new ArrayList<String>(Arrays.asList("west", "south")));
        result.put(corner, difficulty);
      }

      if (grid.get(grid.get(0).size() - 1).get(0).isFreeSpace()) {
        corner = new StratMove(handIdx, grid.get(0).size() - 1, 0);
        difficulty = currHand.get(handIdx).flipDifficulty(
                new ArrayList<String>(Arrays.asList("east", "north")));
        result.put(corner, difficulty);
      }

      if (grid.get(grid.get(0).size() - 1).get(grid.size() - 1).isFreeSpace()) {
        corner = new StratMove(handIdx, grid.get(0).size() - 1, grid.size() - 1);
        difficulty = currHand.get(handIdx).flipDifficulty(
                new ArrayList<String>(Arrays.asList("west", "north")));
        result.put(corner, difficulty);
      }
    }

    return result;
  }

  private List<StratMove> findStrongest(Map<StratMove, Integer> moveSet) {
    List<StratMove> result = new ArrayList<>();

    if (moveSet.isEmpty()) {
      FlipStrat flip = new FlipStrat(this.model);
      result.add(flip.strategize());
      return result;
    }

    int maxValue = Collections.max(moveSet.values());

    for (Map.Entry<StratMove, Integer> mapElement : moveSet.entrySet()) {
      if (mapElement.getValue() == maxValue) {
        result.add(mapElement.getKey());
      }
    }
    return result;
  }

  private StratMove findClosest(List<StratMove> moves) {
    StratMove bestMove = moves.get(0);
    for (int strat = 1; strat < moves.size(); strat++) {
      if (moves.get(strat).originDistance() < bestMove.originDistance()) {
        bestMove = moves.get(strat);
      }

      if (moves.get(strat).originDistance() == bestMove.originDistance()) {
        if (bestMove.stratCard() > moves.get(strat).stratCard()) {
          bestMove = moves.get(strat);
        }
      }
    }

    return bestMove;
  }
}
