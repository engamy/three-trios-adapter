package controller;

import model.ModelFeatures;
import view.ViewFeatures;

/**
 * Represents a controller for the Three Trios Game that registers itself as a listener.
 */
public interface TTControllerInterface extends ModelFeatures, ViewFeatures {

  /**
   * Initializes a single game of the Three Trios game given a Three Trios model.
   * @param gridPath represents a model
   * @param cardPath represents a model
   * @throws IllegalArgumentException if the given model is null
   */
  void startGame(String gridPath, String cardPath);
}
