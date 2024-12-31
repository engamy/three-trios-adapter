import adapter.CardColorAdapter;
import adapter.ControllerAdapterInterface;
import adapter.ModelAdapter;
import adapter.ControllerAdapter;
import adapter.PlayerAdapter;
import controller.CornerStrat;
import controller.FlipStrat;
import controller.Strategies;
import controller.TTController;
import controller.TTControllerInterface;
import model.AIPlayer;
import model.HumanPlayer;
import model.Player;
import model.ThreeTriosModel;
import model.ThreeTriosModelInterface;
import provider.view.ThreeTriosView;
import view.TTSwingView;
import view.TTSwingViewInterface;
import model.CardColor;
import provider.view.TripleTriadView;

/**
 * The class that runs the Three Trios game.
 */
public final class ThreeTrios {

  /**
   * The main method that runs the Three Trios game using the Graphical User Interface view.
   *
   * @param args the arguments passed to main that determine whether the game is
   *             player vs. player, or player vs. ai.
   */
  public static void main(String[] args) {
    String gridPath = "GridConfigs/ConnectedCellsBoard";
    String cardPath = "CardConfigs/AllCards";

    ThreeTriosModelInterface modelAdaptee = new ThreeTriosModel();
    /*
    * Providers have no way to config board or cards, so we *have* to take in the
    * paths as parameters in the constructor.
    */
    ModelAdapter model = new ModelAdapter(modelAdaptee, gridPath, cardPath);
    model.startGame();

    Player playerRed = createPlayer(args, CardColor.R, modelAdaptee);
    PlayerAdapter adapterRed = new PlayerAdapter(CardColorAdapter.RED, model);
    Player playerBlue = createPlayer(args, CardColor.B, modelAdaptee);
    PlayerAdapter adapterBlue = new PlayerAdapter(CardColorAdapter.BLUE, model,
            adaptStrat(args, CardColor.B, modelAdaptee)); // player 2 will always be blue

    TTSwingViewInterface viewPlayer1 = new TTSwingView(modelAdaptee, 1);
    ThreeTriosView providerViewPlayer1 = new TripleTriadView(model);
    TTControllerInterface player1ControllerAdaptee =
            new TTController(modelAdaptee, playerRed, viewPlayer1);
    ControllerAdapterInterface player1Controller = new ControllerAdapter(player1ControllerAdaptee,
            modelAdaptee, model, providerViewPlayer1, adapterRed);
    providerViewPlayer1.setListener(player1Controller);

    TTSwingViewInterface viewPlayer2Adaptee = new TTSwingView(modelAdaptee, 2);
    TTControllerInterface player2ControllerAdaptee =
            new TTController(modelAdaptee, playerBlue, viewPlayer2Adaptee);
    ThreeTriosView viewPlayer2 = new TripleTriadView(model);
    ControllerAdapterInterface player2Controller =
            new ControllerAdapter(player2ControllerAdaptee, modelAdaptee, model, viewPlayer2,
                    adapterBlue);
    viewPlayer2.setListener(player2Controller);

    player1ControllerAdaptee.startGame(gridPath, cardPath);

    viewPlayer1.setVisible(true);
    viewPlayer2.makeVisible();

    // See "HW8 VIEW FUNCTIONALITY" > "What is not working & why:" in our README file to
    // read why this while loop is here!
    while (!model.isGameOver()) {
      viewPlayer1.refresh();
      viewPlayer2.refresh();
    }
  }
  
  /**
   * Mini factory method that creates and returns a player based on the given arg configurations.
   *
   * @param args      the given configurations that should be one of "human" "strategy1"
   *                  or "strategy2"
   * @param playerColor the color of the player we are creating for (Red-1, or Blue-2)
   * @param model     the model we are creating players for
   * @return the player created based on the given arg configurations
   */
  private static Player createPlayer(String[] args, CardColor playerColor,
                                     ThreeTriosModelInterface model) {

    int playerNum = 0;
    if (CardColor.B == playerColor) {
      playerNum = 1;
    }

    try {
      String config = args[playerNum].toLowerCase();
      switch (config) {
        case "human":
          return new HumanPlayer(playerColor);
        case "strategy1":
          return new AIPlayer(playerColor, new FlipStrat(model));
        case "strategy2":
          return new AIPlayer(playerColor, new CornerStrat(model));
        default:
          throw new IllegalArgumentException("Invalid argument type: " + config);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid main args");
    }
  }

  private static Strategies adaptStrat(String[] args, CardColor playerColor,
                                       ThreeTriosModelInterface model) {
    int playerNum = 0;
    if (CardColor.B == playerColor) {
      playerNum = 1;
    }

    try {
      String config = args[playerNum].toLowerCase();
      switch (config) {
        case "strategy1":
          return new FlipStrat(model);
        case "strategy2":
          return new CornerStrat(model);
        default:
          throw new IllegalArgumentException("Invalid argument type: " + config);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid main args");
    }
  }
}