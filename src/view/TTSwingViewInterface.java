package view;

import model.PlayingCard;

/**
 * Represents the interface for the Java Swing GUI view in Three Trios model.
 */
public interface TTSwingViewInterface {
  /**
   * Set up the controller to handle click events in this view.
   *
   */
  void addClickListener();

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Change visibility of the view based on the given boolean.
   *
   * @param b whether the view is visible or not
   */
  void setVisible(boolean b);

  void setCurrentSelectedCard(PlayingCard card);

  void showMessage(String message);

  void addListener(ViewFeatures listener);
}