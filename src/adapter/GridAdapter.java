package adapter;

import java.util.List;

import model.Cell;
import model.EmptyCard;
import model.Hole;
import model.PlayingCard;
import model.ThreeTriosModelInterface;
import provider.model.board.GameCard;
import provider.model.board.GameCell;
import provider.model.board.Grid;
import provider.model.enums.CellType;
import provider.model.enums.Direction;
import provider.model.enums.PlayerColor;

/**
 * Represents an adapter class that adapts a Grid (Provider) to the grid
 * field in the ThreeTriosModel.
 */
public class GridAdapter implements Grid {

  private final ThreeTriosModelInterface model;
  private final List<List<Cell>> grid;

  /**
   * Constructor for GridAdapter.
   * @param model the model whose grid field we are adapting to.
   */
  public GridAdapter(ThreeTriosModelInterface model) {
    this.model = model;
    this.grid = this.model.getGrid();
  }

  @Override
  public int getNumRows() {
    return this.grid.size();
  }

  @Override
  public int getNumCols() {
    return this.grid.get(0).size();
  }

  @Override
  public boolean invalidPosition(Position posn) {
    // did not need to adapt this
    return false;
  }

  @Override
  public boolean hasCardAt(Position posn) {
    return (!this.grid.get(posn.getRow()).get(posn.getCol()).toString().equals(" "))
            && !this.grid.get(posn.getRow()).get(posn.getCol()).toString().equals("_");
  }

  @Override
  public GameCard getCard(Position posn) {
    // did not need to adapt this
    /*if (this.hasCardAt(posn)) {
      PlayingCard pc = (PlayingCard) this.grid.get(posn.getRow()).get(posn.getCol());
      return new CardAdapter(pc.getName(), pc.getCardValue("north"),
              pc.getCardValue("south"), pc.getCardValue("south"),
              pc.getCardValue("south"),
              pc.getCardColor());
    } else {
      throw new UnsupportedOperationException("No card to return at row " + posn.getRow() + " col "
              + posn.getCol());
    }*/
    return null;
  }

  @Override
  public int getNumCardsByPlayer(PlayerColor pc) {
    if (pc.equals(PlayerColor.Blue)) {
      return this.model.getScore("B");
    } else {
      return this.model.getScore("R");
    }
  }

  @Override
  public void placeCardOnBoard(GameCard card, Position posn) {
    if (this.model.isCellFree(posn.getRow(), posn.getCol())) {
      PlayingCard pc = (PlayingCard) this.grid.get(posn.getRow()).get(posn.getCol());
      CardAdapter adaptedCard =  new CardAdapter(pc.getName(), pc.getCardValue("north"),
              pc.getCardValue("south"), pc.getCardValue("south"),
              pc.getCardValue("south"),
              pc.getCardColor());
      this.grid.get(posn.getRow()).set(posn.getCol(), adaptedCard); // TODO: this may not work :3
    }
  }

  @Override
  public int size() {
    int result = 0;
    for (List<Cell> row : this.grid) {
      for (Cell cell : row) {
        if (cell.isFreeSpace()) {
          result++;
        }
      }
    }
    return result;
  }

  @Override
  public boolean isFull() {
    return (this.size() == 0);
  }

  @Override
  public boolean compareValues(Position posn, Direction dir) {
    // did not need to adapt this
    return false;
  }

  @Override
  public boolean flipColor(Position posn, PlayerColor col) {
    // did not need to adapt this
    return false;
  }

  @Override
  public Grid copy() {
    // did not need to adapt this
    return this;
  }

  @Override
  public GameCell getCell(Position p) {
    Cell cellAtPos = this.grid.get(p.getRow()).get(p.getCol());

    if (cellAtPos instanceof EmptyCard) {
      return new CellAdapter(CellType.CardCell);
    }
    if (cellAtPos instanceof Hole) {
      return new CellAdapter(CellType.Hole);
    }
    if (cellAtPos instanceof PlayingCard) {
      CellAdapterInterface card = new CellAdapter(CellType.CardCell);
      card.placeCardInCell(new CardAdapter(cellAtPos.getName(),
              cellAtPos.getCardValue("north"), cellAtPos.getCardValue("south"),
              cellAtPos.getCardValue("east"), cellAtPos.getCardValue("west"),
              cellAtPos.getCardColor()));
      return card;
    }
    throw new IllegalStateException("The cell at row " + p.getRow() + " col " + p.getCol() + " is"
            + " somehow not a Hole, EmptyCard, or PlayingCard");
  }
}
