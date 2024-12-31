package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.PlayingCard;
import model.ReadonlyThreeTriosModel;

/**
 * Represents the view for the Three Trios game.
 */
public class TTSwingView extends JFrame implements TTSwingViewInterface {

  private final TTPanel panel; // declared as concrete class because TTPanel extends JPanel
  private final List<ViewFeatures> listeners = new ArrayList<>();

  /**
   * Constructor for the view.
   * @param model the model with all the information for the view
   */
  public TTSwingView(ReadonlyThreeTriosModel model, int playerNum) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (!(playerNum == 1 || playerNum == 2)) {
      throw new IllegalArgumentException("playerNum must be 1 or 2.");
    }
    this.panel = new TTPanel(model);
    this.setTitle("Player " + playerNumToColor(playerNum));
    this.setSize(700, 900);
    this.panel.setSize(700, 900);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.add(panel);
    this.refresh();
  }

  /**
   * Converts the given player num to the color in order to display the right title on each panel.
   * @param playerNum the player number, which is either 1 or 2
   * @return "RED" if playerNum == 1, "BLUE" if playerNum == 2
   * @throws IllegalArgumentException if the given num is not 1 or 2
   */
  private String playerNumToColor(int playerNum) {
    if (playerNum == 1) {
      return "RED";
    } else if (playerNum == 2) {
      return "BLUE";
    } else {
      throw new IllegalArgumentException("playerNum must be 1 or 2.");
    }
  }

  @Override
  public void addClickListener() {
    // passes through to the panel
    for (ViewFeatures listener : listeners) {
      this.panel.addClickListener(listener);
    }

  }

  @Override
  public void setCurrentSelectedCard(PlayingCard card) {
    this.panel.setCurrentSelectedCard(card);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void showMessage(String message) {
    this.panel.showDialog(message);
  }

  @Override
  public void addListener(ViewFeatures listener) {
    this.listeners.add(listener);
  }
}
