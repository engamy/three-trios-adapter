package controller;

import model.PlayingCard;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import model.ThreeTriosModelInterface;

/**
 * Represents the Flip strategy:
 * Flip as many cards on this turn as possible. This means choosing a position and card together.
 */
public class FlipStrat implements Strategies {
  ThreeTriosModelInterface model;

  public FlipStrat(ThreeTriosModelInterface model) {
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
      for (int row = 0; row < this.model.getGrid().size(); row++) {
        for (int col = 0; col < this.model.getGrid().get(row).size(); col++) {
          if (this.model.getGrid().get(row).get(col).isFreeSpace()) {
            StratMove curr = new StratMove(handIdx, row, col);
            int flippedCard = this.model.numCardsFlipped(handIdx, row, col);
            result.put(curr, flippedCard);
          }
        }
      }
    }
    return result;
  }

  private List<StratMove> findStrongest(Map<StratMove, Integer> moveSet) {
    List<StratMove> result = new ArrayList<>();
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
        if (bestMove.stratRow() > moves.get(strat).stratRow()) {
          bestMove = moves.get(strat);
        }

        else {
          if (bestMove.stratCard() > moves.get(strat).stratCard()) {
            bestMove = moves.get(strat);
          }
        }
      }
    }
    return bestMove;
  }
}







