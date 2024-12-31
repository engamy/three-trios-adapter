package adapter;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

import controller.ConfigureGame;
import model.PlayingCard;
import model.ThreeTriosModelInterface;
import provider.controller.ThreeTriosController;
import provider.model.Model;
import provider.model.board.GameCard;
import provider.model.board.GameCell;
import provider.model.board.Grid;
import provider.model.enums.CellType;
import provider.model.enums.GameState;
import provider.model.enums.PlayerColor;
import provider.model.strategies.Strategy;
import model.Cell;

/**
 * Represents an adapter that adapts a ThreeTriosModelInterface to a Model (Provider).
 */
public class ModelAdapter implements Model {

  private final ThreeTriosModelInterface adaptee;
  private final String gridPath;
  private final String cardPath;
  private final List<ControllerAdapterInterface> listeners = new ArrayList<>();

  /**
   * Constructor for the ModelAdapter.
   * @param adaptee the model to adapt to
   * @param gridPath the String representing the file path to the grid configuration
   * @param cardPath the String representing the file path to the card configuration
   */
  public ModelAdapter(ThreeTriosModelInterface adaptee, String gridPath, String cardPath) {
    this.adaptee = adaptee;
    this.gridPath = gridPath;
    this.cardPath = cardPath;
  }

  @Override
  public void addListener(ThreeTriosController controller) {
    listeners.add((ControllerAdapterInterface) controller);
  }

  @Override
  public void playTurn(int handIndex, Position posn) {
    // Attempt at synchronizing the views failed
    /*for (ControllerAdapterInterface listener : listeners) {
      listener.refreshView();
    } */
  }

  @Override
  public void startGame() {
    if (!(this.adaptee.isGameStarted())) {
      ConfigureGame.initGrid(this.gridPath, this.adaptee);
      ConfigureGame.initDeck(this.cardPath, this.adaptee);
      this.adaptee.initGame();
    }
  }

  @Override
  public void setPlayerTypes(Strategy red, Strategy blue) {
    // Not needed as we are adapting for a human vs ai game not ai vs ai
  }

  @Override
  public boolean isGameOver() {
    return this.adaptee.isGameOver();
  }

  @Override
  public PlayerColor gameWon() {
    if (this.adaptee.getWinner().equals("Red wins!")) {
      return PlayerColor.Red;
    }
    else {
      return PlayerColor.Blue;
    }
  }

  @Override
  public Grid getGrid() {
    return new GridAdapter(this.adaptee);
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    if (this.adaptee.getCurrentPlayer().equals("R")) {
      return PlayerColor.Red;
    }
    else {
      return PlayerColor.Blue;
    }
  }

  @Override
  public GameState getGamestate() {
    if (!this.adaptee.isGameStarted()) {
      return GameState.NotStarted;
    }
    if (this.adaptee.isGameOver()) {
      return GameState.GameOver;
    }
    else {
      return GameState.InProgress;
    }
  }

  @Override
  public ArrayList<GameCard> getPlayerHand(PlayerColor p) {
    ArrayList<GameCard> result = new ArrayList<>();
    List<PlayingCard> hand;
    if (p.equals(PlayerColor.Blue)) {
      hand = this.adaptee.getHand("B");
    } else {
      hand = this.adaptee.getHand("R");
    }
    try {
      for (PlayingCard pc : hand) {
        result.add(new CardAdapter(pc.getName(), pc.getCardValue("north"),
                pc.getCardValue("south"), pc.getCardValue("east"),
                pc.getCardValue("west"), pc.getCardColor()));
      }
    } catch (ConcurrentModificationException | NoSuchElementException ex) {
      // catching these specific exceptions because the view has a bug where it's trying to update
      // the card sizes for cards that don't exist (they were previously placed)
      // we don't do anything about it because it doesn't cause any issues with the view
    }
    return result;
  }

  @Override
  public int getPlayerScore(PlayerColor p) {
    if (p.equals(PlayerColor.Blue)) {
      return this.adaptee.getScore("B");
    }
    return this.adaptee.getScore("R");
  }

  @Override
  public GameCell getCell(Position p) {
    Cell tempCell = this.adaptee.getGrid().get(p.getRow()).get(p.getCol());
    if (tempCell.toString().equals(" ")) {
      return new CellAdapter(CellType.Hole);
    }
    if (tempCell.toString().equals("_")) {
      return new CellAdapter(CellType.CardCell);
    }
    else {
      CellAdapter temp = new CellAdapter(CellType.CardCell);
      temp.changeOwner(CardColorAdapter.convert(tempCell.getCardColor()));
      temp.placeCardInCell(new CardAdapter(tempCell.getName(),
              tempCell.getCardValue("north"),
              tempCell.getCardValue("south"),
              tempCell.getCardValue("east"),
              tempCell.getCardValue("west"),
              tempCell.getCardColor()));
      return temp;
    }
  }
}
