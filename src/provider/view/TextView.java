package provider.view;

import java.util.ArrayList;

import provider.model.Model;
import provider.model.board.GameCard;

/**
 * Takes in a three trios model and turns it into a simple text view representing the current state
 * of the game.
 */
public class TextView {
  private Model game;

  /**
   * Create a test view of a model of three trios.
   *
   * @param game the model to use to represent as text.
   */
  public TextView(Model game) {
    this.game = game;
  }

  /**
   * Put the current information of the model into a String representation.
   *
   * @return a String representation of the current model.
   */
  public String toString() {
    String result = "";
    result += "Player: " + game.getCurrentPlayer().toString().toUpperCase() + "\n";
    result += game.getGrid().toString();
    result += "Hand:\n";
    ArrayList<GameCard> hand = this.game.getPlayerHand(game.getCurrentPlayer());
    for (GameCard c : hand) {
      result += c.toString() + "\n";
    }
    return result;
  }
}

