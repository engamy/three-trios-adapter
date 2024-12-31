package adapter;

import controller.TTControllerInterface;
import model.ThreeTriosModelInterface;
import provider.model.enums.PlayerColor;
import provider.view.ThreeTriosView;

/**
 * Represents an adapter that adapts a TTController Interface to a ThreeTriosController (Provider).
 */
public class ControllerAdapter implements ControllerAdapterInterface {

  private final TTControllerInterface adaptee;
  private final ThreeTriosModelInterface model;
  private final ThreeTriosView view;
  private int indexOfCard = -1;
  private final PlayerAdapter player;
  //private final ModelAdapter modelAdapter;

  /**
   * Constructor for the ControllerAdapter.
   * @param adaptee the controller to adapt
   * @param model the model whose information we are controlling
   * @param modelAdapter the provider's model we are adapting
   * @param viewProvider the provider's view
   * @param player the player who owns this controller
   */
  public ControllerAdapter(TTControllerInterface adaptee, ThreeTriosModelInterface model,
                           ModelAdapter modelAdapter,
                           ThreeTriosView viewProvider, PlayerAdapter player) {
    this.adaptee = adaptee;
    this.model = model;
    //this.modelAdapter = modelAdapter;
    this.view = viewProvider;
    //this.view.setListener(this);
    modelAdapter.addListener(this);
    model.addListeners(adaptee);
    this.player = player;
  }

  @Override
  public void notifyPlayerOfTurn() {
    // did not adapt this because when we tried putting a pop-up dialog that says whose turn
    // it is, it never pops up at the right time according to the view, and it does not affect
    // gameplay to not have this method implemented

    /*if (this.isPlayerTurn()) {
      String playerName = "RED";
      if (this.model.getCurrentPlayer().equals("B")) {
        playerName = "BLUE";
      }
      this.view.showError("Player " + playerName + ": it's your turn!");
    }*/
  }

  @Override
  public void refreshView() {
    this.adaptee.refreshViews();
    this.view.refresh();
  }

  @Override
  public void resetPlayerMove() {
    this.indexOfCard = -1;
  }

  @Override
  public int getHandIndex() {
    return this.indexOfCard;
  }

  @Override
  public void handleError(String msg) {
    // This method is never used
    /*this.view.showError(msg);
    this.view.refresh(); */
  }

  @Override
  public PlayerColor getPlayer() {
    if (this.adaptee.getPlayer().getPlayerColor().equals("R")) {
      return PlayerColor.Red;
    }
    else {
      return PlayerColor.Blue;
    }
  }

  @Override
  public void handleGameOver() {
    /* Documentation says this method handles a tie case, but the game can never tie, so
    * this method only handles blue win or red win cases when the game ends.*/
    if (this.model.isGameOver()) {
      String winmsg = "Blue wins!";
      if (this.model.getWinner().equals("R")) {
        winmsg = "Red wins!";
      }
      this.view.showError(winmsg);
    }
    refreshView();
  }

  @Override
  public boolean isPlayerTurn() {
    return this.model.getCurrentPlayer().equals(
            this.adaptee.getPlayer().getPlayerColor());
  }

  @Override
  public void playTurn() {
    // We didn't end up using this implementation
    /*if (this.player.getPlayerStrat() != null && this.isPlayerTurn()) {
      StratMove temp = this.player.getPlayerStrat().strategize();
      this.indexOfCard = temp.stratCard();
      this.playingCard = this.model.getHand(this.player.toString()).get(temp.stratCard());
      selectGridPosition(new Position(temp.stratRow(), temp.stratCol()));
      this.model.playCard(this.indexOfCard, this.gridPosition.getRow(), this.gridPosition.getCol());
    }
    refreshView(); */
  }

  @Override
  public void selectCard(int handIndex) {
    // make sure player clicked in their own panel
    if (this.player.toString().equals(this.model.getCurrentPlayer())) {
      this.resetPlayerMove();
      this.handleGameOver();
    }
    // otherwise, the player clicked in the wrong panel
    else {
      this.view.showError("Play in your own panel.");
    }
  }

  @Override
  public void selectGridPosition(Position position) {
    if (!this.player.toString().equals(this.model.getCurrentPlayer())) {
      this.view.showError("Play in your own panel.");
    }
  }
}
