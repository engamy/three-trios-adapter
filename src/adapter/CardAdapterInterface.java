package adapter;

import model.PlayingCard;
import provider.model.board.GameCard;

/**
 * Represents the interface for a card adapter, which adapts a GameCard (provider) to a PlayingCard.
 */
public interface CardAdapterInterface extends GameCard {

  /**
   * Observer for the name of this card.
   * @return the name of this card
   */
  String getName();

  /**
   * Turns this card into a PlayingCard.
   * @return the version of this card as a PlayingCard
   */
  PlayingCard convertToPlayingCard();
}
