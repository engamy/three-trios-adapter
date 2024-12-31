package view;

import java.util.List;

import model.Cell;
import model.PlayingCard;
import model.ReadonlyThreeTriosModel;

/**
 * A class that displays the game status' through text.
 */
public class TTTextView implements TTTextViewInterface {

  private ReadonlyThreeTriosModel model;

  /**
   * An instance of a textview that takes in a model.
   * @param model an instance of ThreeTriosModel that is not mutable
   */
  public TTTextView(ReadonlyThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    String result = "Player: " + model.getCurrentPlayer() + "\n";

    for (List<Cell> row : model.getGrid()) {
      for (Cell c : row) {
        if (c.toString().equals("_") || c.toString().equals(" ")) {
          result = result + c.toString();
        } else {
          result = result + c.getCardColor().toString();
        }
      }
      result = result + "\n";
    }

    result = result + "Hand:\n";

    for (PlayingCard pc : model.getHand(model.getCurrentPlayer())) {
      result = result + pc.toString() + "\n";
    }

    return result;
  }

}
