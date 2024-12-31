package provider.view.components;

import provider.controller.ThreeTriosController;

/**
 * Interface for the hand panel in the game view.
 * It is responsible for the clicking events
 * such as selecting and deselecting the cards.
 */
public interface HandPanel {
  // listen for clicks -> select/deselect a card + output hand index
  public void setListener(ThreeTriosController listener);
}

