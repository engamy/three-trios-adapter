import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.CornerStrat;
import controller.FlipStrat;
import controller.StratMove;
import controller.Strategies;
import controller.TTController;
import model.AIPlayer;
import model.CardColor;
import model.HumanPlayer;
import model.Player;
import model.ThreeTriosModel;
import model.ThreeTriosModelInterface;
import view.TTSwingView;
import view.TTSwingViewInterface;

/**
 * Test class for all public methods in Strategies.
 */
public class ThreeTriosPlayerStratMoveTests {

  private Player humanPlayer1;
  private Player aiPlayerFlipStrat;
  private Strategies flipStrat;

  private Player humanPlayer2;
  private Player aiPlayerCornerStrat;
  private Strategies cornerStrat;

  private StratMove sm;

  @Before
  public void setup() {
    ThreeTriosModelInterface playerVsFlipStratModel = new ThreeTriosModel();

    this.humanPlayer1 = new HumanPlayer(CardColor.R);
    TTSwingViewInterface viewHuman1 = new TTSwingView(playerVsFlipStratModel, 1);
    TTController playerVsFlipStratController = new TTController(playerVsFlipStratModel,
            humanPlayer1, viewHuman1);

    TTSwingViewInterface flipStratView = new TTSwingView(playerVsFlipStratModel, 2);
    this.flipStrat = new FlipStrat(playerVsFlipStratModel);
    this.aiPlayerFlipStrat = new AIPlayer(CardColor.B, this.flipStrat);
    TTController controllerFlipStrat = new TTController(playerVsFlipStratModel,
            this.aiPlayerFlipStrat, flipStratView);

    playerVsFlipStratController.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");
    controllerFlipStrat.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");

    ThreeTriosModelInterface cornerStratVsHumanModel = new ThreeTriosModel();

    this.humanPlayer2 = new HumanPlayer(CardColor.B);
    TTSwingViewInterface viewHuman2 = new TTSwingView(cornerStratVsHumanModel, 2);
    TTController humanPlayer2Controller = new TTController(cornerStratVsHumanModel,
            humanPlayer2, viewHuman2);

    this.cornerStrat = new CornerStrat(cornerStratVsHumanModel);
    this.aiPlayerCornerStrat = new AIPlayer(CardColor.R, this.cornerStrat);
    TTSwingViewInterface viewCornerStrat = new TTSwingView(cornerStratVsHumanModel, 1);
    TTController controllerCornerStrat = new TTController(cornerStratVsHumanModel,
            this.aiPlayerCornerStrat, viewCornerStrat);

    humanPlayer2Controller.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");
    controllerCornerStrat.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");

    sm = new StratMove(10, 3, 4);
  }

  // TESTING PLAYERS

  @Test
  public void testGetPlayerColor() {
    Assert.assertEquals("R", this.humanPlayer1.getPlayerColor());
    Assert.assertEquals("B", this.aiPlayerFlipStrat.getPlayerColor());
    Assert.assertEquals("B", this.humanPlayer2.getPlayerColor());
    Assert.assertEquals("R", this.aiPlayerCornerStrat.getPlayerColor());
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testGetPlayerStratHuman() {
    Strategies erm = this.humanPlayer1.getPlayerStrat();
  }

  @Test
  public void testGetPlayerStratAI() {
    Assert.assertEquals(this.flipStrat, this.aiPlayerFlipStrat.getPlayerStrat());
    Assert.assertEquals(this.cornerStrat, this.aiPlayerCornerStrat.getPlayerStrat());
  }

  @Test
  public void testIsAIPlayer() {
    Assert.assertEquals(true, this.aiPlayerFlipStrat.isAIPlayer());
    Assert.assertEquals(true, this.aiPlayerCornerStrat.isAIPlayer());
    Assert.assertEquals(false, this.humanPlayer1.isAIPlayer());
    Assert.assertEquals(false, this.humanPlayer2.isAIPlayer());
  }

  // TESTING STRATMOVE

  @Test
  public void testStratCard() {
    Assert.assertEquals(10, this.sm.stratCard());
  }

  @Test
  public void testStratRow() {
    Assert.assertEquals(3, this.sm.stratRow());
  }

  @Test
  public void testStratCol() {
    Assert.assertEquals(4, this.sm.stratCol());
  }

  @Test
  public void testOriginDistance() {
    Assert.assertEquals(7, this.sm.originDistance());
  }

  @Test
  public void testToString() {
    Assert.assertEquals("Card Idx: 10, Row: 3, Col: 4", this.sm.toString());
  }
}
