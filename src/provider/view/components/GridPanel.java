package provider.view.components;

import provider.controller.ThreeTriosController;

/**
 * Interface for a grid panel in the game view.
 * It is responsible for drawing the game grid and listening for clicks.
 */
public interface GridPanel {
  // draw grid
  // listen for clicks + transform coordinates
  public void setListener(ThreeTriosController listener);
}
