package adapter;

import model.Cell;
import model.Hole;
import model.EmptyCard;
import provider.model.board.GameCard;
import provider.model.enums.CellType;
import provider.model.enums.PlayerColor;

/**
 * Represents an adapter class that adapts a GameCell (Provider) to a Cell.
 */
public class CellAdapter implements CellAdapterInterface {

  private final CellType type;
  private PlayerColor player;
  private CardAdapterInterface card;

  /**
   * Constructor for the cell adapter.
   * @param type the type of cell this cell is
   */
  public CellAdapter(CellType type) {
    this.type = type;
    this.player = null;
    this.card = null;
  }

  @Override
  public CellType getType() {
    return this.type;
  }

  @Override
  public Cell convertToCell() {
    if (this.type == CellType.Hole) {
      return new Hole();
    }
    if ((this.type == CellType.CardCell) && (this.card == null)) {
      return new EmptyCard();
    }
    if ((this.type == CellType.CardCell) && (this.card != null)) {
      return this.card.convertToPlayingCard();
    }
    throw new IllegalStateException("Cannot convert this CellAdapter to a Cell.");
  }

  @Override
  public boolean isEmpty() {
    return (this.card == null);
  }

  @Override
  public void placeCardInCell(GameCard newCard) {
    this.card = (CardAdapter) newCard;
  }

  @Override
  public GameCard getCard() {
    return card;
  }

  @Override
  public void changeOwner(PlayerColor col) {
    this.player = col;
  }

}
