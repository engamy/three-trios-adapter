package provider.view;

import provider.controller.ThreeTriosController;

/**
 * The ThreeTriosView interface defines the methods used for displaying the game.
 * Has functionality to refresh the game and make it visible.
 */
public interface ThreeTriosView {
  //void addClickListener();
  void setListener(ThreeTriosController listener);

  void refresh();

  void makeVisible();

  void showError(String msg);

  public void gameOver(String msg);
}
