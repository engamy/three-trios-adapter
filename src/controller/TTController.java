package controller;

import java.util.NoSuchElementException;

import model.CardColor;
import model.CardValue;
import model.Ensure;
import model.Player;
import model.PlayingCard;
import model.ThreeTriosModelInterface;
import view.TTSwingViewInterface;

/**
 * Represents the Three Trios controller used for the Graphical User Interface (implemented
 * using Java Swing).
 */
public class TTController implements TTControllerInterface {

  private TTSwingViewInterface view;
  private ThreeTriosModelInterface model;
  private Player player;

  /**
   * Constructor for controller that only takes in model, used only for testing.
   * @param model the model to control
   */
  public TTController(ThreeTriosModelInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  /**
   * The constructor for an abstract player controller.
   * @param model the model to control
   * @param player the player who owns this controller
   * @param view the view to display the information in the model
   */
  public TTController(ThreeTriosModelInterface model, Player player,
                      TTSwingViewInterface view) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    this.view = view;
    this.model = model;
    this.player = player;
  }

  /**
   * Constructor for the GUI controller for the Three Trios game.
   * @param view the view
   */
  public TTController(TTSwingViewInterface view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    this.view = view;
  }

  @Override
  public void startGame(String gridPath, String cardPath) {
    if (!(this.model.isGameStarted())) {
      ConfigureGame.initGrid(gridPath, model);
      ConfigureGame.initDeck(cardPath, model);
      model.initGame();
    }
    if (view != null) {
      view.addListener(this);
      view.addClickListener();
      view.setVisible(true);
      this.model.addListeners(this);
      this.model.aiWarBehavior();
    }
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public void playerSelectedCard(String color, PlayingCard selectedCard) {
    if ((selectedCard == null) || (color == null)) {
      throw new IllegalArgumentException("Color or selectedCard cannot be null");
    }
    // you can only select a card if you're a human player
    if ((color.equals("R") || color.equals("B")) && !this.player.isAIPlayer()) {
      if (this.model.getCurrentPlayer().equals(color)) {
        this.view.setCurrentSelectedCard(selectedCard);
      }
    } else {
      throw new IllegalArgumentException("Given color " + color + " is not "
              + "R or B or AI Player tried to select a card");
    }
    this.view.refresh();
  }

  @Override
  public void playerPlayCard(PlayingCard selectedCard, int row, int col) {
    if (selectedCard == null) {
      throw new IllegalArgumentException("selectedCard cannot be null");
    }
    if (this.player.getPlayerColor().equals(this.model.getCurrentPlayer())) {
      Ensure.checkIdxBounds(row, 0, this.model.getGrid().size());
      Ensure.checkIdxBounds(col, 0, this.model.getGrid().get(0).size());
      if (!player.isAIPlayer()) {
        //System.out.println("Cell at row " + row + ", col " + col + " clicked in the GRID.");
        try {
          this.model.playCard(this.findIdxOfHandCard(selectedCard), row, col);
        } catch (IllegalStateException ex) {
          // if you try to play without selecting a card, tell player!
          String playerName = "RED";
          if (this.model.getCurrentPlayer().equals("B")) {
            playerName = "BLUE";
          }
          this.view.showMessage("Player " + playerName + ": Select a card to play in your"
                  + "\nhand before clicking on the grid.");
        }
      }
      else if (player.isAIPlayer()) {
        StratMove move = null;
        Strategies scheming = player.getPlayerStrat();
        try {
          move = scheming.strategize();
          this.model.playCard(move.stratCard(), move.stratRow(), move.stratCol());
        }
        catch (NoSuchElementException | StackOverflowError e) {
          // Game should be finished if no valid moves are available
        }
      } else {
        throw new UnsupportedOperationException("Player is not a human or AI player");
      }
      this.turnHasCompleted();
      this.view.refresh();
    }
  }

  /**
   * Finds the index of the card in the current player's hand.
   * @param handCard the given card to find the index of
   * @return the index of the given card in the current player's hand
   * @throws IllegalStateException if the given card is not in the current player's hand
   */
  private int findIdxOfHandCard(PlayingCard handCard) {
    int result = -1;
    for (int handIdx = 0; handIdx < this.model.getHand(this.model.getCurrentPlayer()).size();
         handIdx++) {
      if (handCard.equals(this.model.getHand(this.model.getCurrentPlayer()).get(handIdx))) {
        result = handIdx;
      }
    }
    if (result == -1) {
      throw new IllegalStateException("Card " + handCard.toString() + " is not in "
              + this.model.getCurrentPlayer() + "'s hand.");
    }
    return result;
  }

  @Override
  public void checkPlayerPanel() {
    if (!(this.player.getPlayerColor().equals(this.model.getCurrentPlayer()))) {
      String playerName = "RED";
      if (this.model.getCurrentPlayer().equals("B")) {
        playerName = "BLUE";
      }
      this.view.showMessage("Player " + playerName + ": Play in your own player panel.");
    }
  }

  @Override
  public void gameOver() {
    try {
      this.view.showMessage(this.model.getWinner());
    }
    catch (IllegalArgumentException ex) {
      // A winner has not been decided yet, continue game
    }
    
  }

  @Override
  public void turnHasCompleted() {
    if (this.player.isAIPlayer() && !this.model.isGameOver()) {
      this.playerPlayCard(new PlayingCard("FalseProphet", CardValue.A, CardValue.A,
              CardValue.A, CardValue.A, CardColor.B), 0, 0);
    }
    if (this.player.getPlayerColor().equals("B")) {
      this.model.checkGameEnded();
    }
  }

  @Override
  public void refreshViews() {
    this.view.refresh();
  }
}
