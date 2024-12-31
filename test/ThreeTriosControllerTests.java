import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.ConfigureGame;
import controller.CornerStrat;
import controller.FlipStrat;
import controller.Strategies;
import controller.TTControllerInterface;
import controller.TTController;
import model.CardColor;
import model.CardValue;
import model.HumanPlayer;
import model.Player;
import model.PlayingCard;
import model.ThreeTriosModel;
import model.AIPlayer;
import model.ThreeTriosModelInterface;
import view.TTSwingView;
import view.TTSwingViewInterface;

/**
 * The test class for the Three Trios controller package.
 */
public class ThreeTriosControllerTests {

  private ThreeTriosModelInterface gameNotStarted;
  private TTControllerInterface gameNotStartedController;
  private TTControllerInterface gameAlmostOverController;

  private ThreeTriosMock testCardsNoHoles;
  private ThreeTriosMock mock;
  private ThreeTriosMock allCardsNoHolesMock;

  private Player aiPlayerFlipStrat;

  private TTControllerInterface playerVsFlipStratController;

  private ThreeTriosModelInterface playerVsFlipStratModel;


  @Before
  public void setup() {
    gameNotStarted = new ThreeTriosModel();
    gameNotStartedController = new TTController(gameNotStarted);

    ThreeTriosModelInterface allCardsNoHoles = new ThreeTriosModel();
    TTControllerInterface allCardsNoHolesController = new TTController(allCardsNoHoles);
    allCardsNoHolesController.startGame(
            "GridConfigs/NoHolesBoard",
            "CardConfigs/AllCards");

    ThreeTriosModelInterface fewCardsConnectedCells = new ThreeTriosModel();
    TTControllerInterface fewCardsConnectedCellsController =
            new TTController(fewCardsConnectedCells);
    fewCardsConnectedCellsController.startGame(
            "GridConfigs/ConnectedCellsBoard",
            "CardConfigs/FewCards");

    ThreeTriosModelInterface gameAlmostOver = new ThreeTriosModel();
    gameAlmostOverController = new TTController(gameAlmostOver);
    gameAlmostOverController.startGame(
            "GridConfigs/EndGameBoardForTesting",
            "CardConfigs/FewCards");

    testCardsNoHoles = new ThreeTriosMock();
    TTControllerInterface testCardsNoHolesController = new TTController(testCardsNoHoles);
    testCardsNoHolesController.startGame(
            "GridConfigs/NoHolesBoard",
            "CardConfigs/TestingCards");

    ThreeTriosModelInterface testPlayerPlayCard = new ThreeTriosModel();
    TTSwingViewInterface testHandleGridClickView = new TTSwingView(testPlayerPlayCard, 1);
    TTControllerInterface testHandleGridClickController = new TTController(testPlayerPlayCard);
    testHandleGridClickController.startGame(
            "GridConfigs/NoHolesBoard",
            "CardConfigs/TestingCards");

    mock = new ThreeTriosMock();
    ConfigureGame.initGrid("GridConfigs/StrategyTranscriptBoard", mock);
    ConfigureGame.initDeck("CardConfigs/AllCards", mock);
    mock.initGame();

    allCardsNoHolesMock = new ThreeTriosMock();
    ConfigureGame.initGrid("GridConfigs/NoHolesBoard", allCardsNoHolesMock);
    ConfigureGame.initDeck("CardConfigs/AllCards", allCardsNoHolesMock);
    allCardsNoHolesMock.initGame();

    playerVsFlipStratModel = new ThreeTriosModel();

    Player humanPlayer1 = new HumanPlayer(CardColor.R);
    TTSwingViewInterface viewHuman1 = new TTSwingView(playerVsFlipStratModel, 1);
    playerVsFlipStratController = new TTController(playerVsFlipStratModel, humanPlayer1,
            viewHuman1);

    TTSwingViewInterface flipStratView = new TTSwingView(playerVsFlipStratModel, 2);
    Strategies flipStrat = new FlipStrat(playerVsFlipStratModel);
    this.aiPlayerFlipStrat = new AIPlayer(CardColor.B, flipStrat);
    TTController controllerFlipStrat = new TTController(playerVsFlipStratModel,
            this.aiPlayerFlipStrat, flipStratView);

    playerVsFlipStratController.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");
    controllerFlipStrat.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");

    ThreeTriosModelInterface cornerStratVsHumanModel = new ThreeTriosModel();

    Player humanPlayer2 = new HumanPlayer(CardColor.B);
    TTSwingViewInterface viewHuman2 = new TTSwingView(cornerStratVsHumanModel, 2);
    TTController humanPlayer2Controller = new TTController(cornerStratVsHumanModel,
            humanPlayer2, viewHuman2);

    Strategies cornerStrat = new CornerStrat(cornerStratVsHumanModel);
    Player aiPlayerCornerStrat = new AIPlayer(CardColor.R, cornerStrat);
    TTSwingViewInterface viewCornerStrat = new TTSwingView(cornerStratVsHumanModel, 1);
    TTController controllerCornerStrat = new TTController(cornerStratVsHumanModel,
            aiPlayerCornerStrat, viewCornerStrat);

    humanPlayer2Controller.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");
    controllerCornerStrat.startGame("GridConfigs/ConnectedCellsBoard",
            "CardConfigs/AllCards");
  }

  // TESTING STARTGAME()

  @Test
  public void testStartGameValidPath() {
    // grid is null
    Assert.assertEquals(null, this.gameNotStarted.getGrid());
    // grid is initialized in startGame
    this.gameNotStartedController.startGame(
            "GridConfigs/NoHolesBoard",
            "CardConfigs/AllCards");
    // grid is no longer null
    Assert.assertNotEquals(null, this.gameNotStarted.getGrid());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    this.gameNotStartedController.startGame(
            "GridConfigs/NoHolesBoard",
            "CardConfigs/NotEnoughCardsForTesting");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidPaths() {
    this.gameNotStartedController.startGame(
            "invalid path la lala lala", "blalalala");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidEmptyCardCells() {
    this.gameNotStartedController.startGame(
            "GridConfigs/InvalidNumHolesBoardForTesting",
            "CardConfigs/FewCards");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerSelectedCardInvalidArg() {
    this.gameAlmostOverController.playerSelectedCard("PURPLEEE", new PlayingCard("test",
            CardValue.A, CardValue.A, CardValue.A, CardValue.A, CardColor.B));
    this.gameAlmostOverController.playerSelectedCard("B", new PlayingCard("test",
            CardValue.A, CardValue.A, CardValue.A, CardValue.A, CardColor.B));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerSelectedGridInvalidArgs() {
    this.playerVsFlipStratController.playerPlayCard(new PlayingCard("test",
            CardValue.A, CardValue.A, CardValue.A, CardValue.A, CardColor.B), 0, 1232);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerSelectedGridNullArgs() {
    this.playerVsFlipStratController.playerPlayCard(null, 0, 1232);
  }

  @Test
  public void testHumanPlayerPlayCardValidArgs() {
    PlayingCard handCardToPlay = this.playerVsFlipStratModel.getHand("R").get(0);
    Assert.assertEquals(10, this.playerVsFlipStratModel.getHand("R").size());
    Assert.assertEquals("_", this.playerVsFlipStratModel.getGrid().get(0).get(0).toString());
    playerVsFlipStratController.playerPlayCard(handCardToPlay, 0, 0);
    Assert.assertEquals(9, this.playerVsFlipStratModel.getHand("R").size());
    Assert.assertEquals(handCardToPlay, this.playerVsFlipStratModel.getGrid().get(0).get(0));
  }

  @Test
  public void testAIPlayerPlaysCard() {
    Assert.assertEquals(10, this.playerVsFlipStratModel.getHand("B").size());
    // red player (human) playing a card should prompt blue player (AI) to play a card, reducing
    // the number of cards in the blue player's hand to 9.
    PlayingCard handCardToPlay = this.playerVsFlipStratModel.getHand("R").get(0);
    playerVsFlipStratController.playerPlayCard(handCardToPlay, 0, 0);
    Assert.assertEquals(9, this.playerVsFlipStratModel.getHand("B").size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayerSelectedCardNullArgs() {
    this.playerVsFlipStratController.playerSelectedCard("", null);
    this.playerVsFlipStratController.playerSelectedCard("purple",
            new PlayingCard("FalseProphet", CardValue.A, CardValue.A,
            CardValue.A, CardValue.A, CardColor.B));
  }

  // NOTE: playerSelectedCard()'s only real effect is that it
  // changes selectedCard in the view (TTPanel)

  // NOTE: Similarly, we can't test gameOver() because all it does is display the game over
  // message dialog in hte view (TTPanel)

  @Test
  public void testTurnHasCompleted() {
    // testing that the blue player in playerVsFlipStratModel is an AI Player
    Assert.assertTrue(this.aiPlayerFlipStrat.isAIPlayer());
    Assert.assertEquals(10, this.playerVsFlipStratModel.getHand("B").size());
    // red player (human) playing a card should prompt blue player (AI) to play a card using
    // turnHasCompleted(), reducing the number of cards in the blue player's hand to 9.
    PlayingCard handCardToPlay = this.playerVsFlipStratModel.getHand("R").get(0);
    playerVsFlipStratController.playerPlayCard(handCardToPlay, 0, 0);
    Assert.assertEquals(9, this.playerVsFlipStratModel.getHand("B").size());
  }

  // USING MOCKS TO TEST OUR STRATEGIES -- please look at the
  // "TESTING STRATEGIES USING ThreeTriosMock" section of our README file!

  // TESTING FLIP STRATEGY
  @Test
  public void testFlipStrat() {
    // testing that FlipStrat plays to the origin when there's nothing on the board
    FlipStrat flipStrat = new FlipStrat(this.allCardsNoHolesMock);
    Assert.assertEquals("Card Idx: 0, Row: 0, Col: 0", flipStrat.strategize().toString());
    // setting up the board so four blue cards can be flipped to red
    this.allCardsNoHolesMock.playCard(0, 1, 1); // can be flipped
    this.allCardsNoHolesMock.playCard(0, 2, 3);
    this.allCardsNoHolesMock.playCard(2, 3, 2);
    this.allCardsNoHolesMock.playCard(4, 3, 1);
    this.allCardsNoHolesMock.playCard(0, 0, 2); // can be flipped
    this.allCardsNoHolesMock.playCard(0, 4, 0);
    this.allCardsNoHolesMock.playCard(5, 1, 3); // can be flipped
    this.allCardsNoHolesMock.playCard(0, 4, 1);
    this.allCardsNoHolesMock.playCard(2, 2, 2); // can be flipped
    // the strat should determine that it can flip four cards at (1, 2) and places
    // the correct card (hand idx 7) at (1, 2)
    Assert.assertEquals("Card Idx: 0, Row: 1, Col: 2", flipStrat.strategize().toString());
  }

  @Test
  public void testFlipStratTie() {
    // In this test, the FlipStrat makes it decide between two positions that allow
    // two cards to flip a high number of cards, the Strat ultimately decide on the
    // position closer to the origin and the card closer to hand index 0
    FlipStrat flipStrat = new FlipStrat(this.testCardsNoHoles);
    this.testCardsNoHoles.playCard(0, 2, 1);
    this.testCardsNoHoles.playCard(0, 4, 4);
    this.testCardsNoHoles.playCard(0, 1, 2);
    Assert.assertEquals("Card Idx: 0, Row: 1, Col: 1",
            flipStrat.strategize().toString());
  }

  @Test
  public void testCornerStrat() {
    // In this test, the CornerStrat chooses the corner that allows a card
    // to have the highest defense, in this case, its the bottom right corner
    CornerStrat cornerStrat = new CornerStrat(this.allCardsNoHolesMock);
    Assert.assertEquals("Card Idx: 11, Row: 4, Col: 4",
            cornerStrat.strategize().toString());
  }

  @Test
  public void testCornerStratTie() {
    // In this test, the CornerStrat chooses between two cards with the highest
    // defense in all corners, ultimately it chooses the card closest to hand idx
    // 0 and the corner at the origin
    CornerStrat cornerStrat = new CornerStrat(this.testCardsNoHoles);
    Assert.assertEquals("Card Idx: 3, Row: 0, Col: 0",
            cornerStrat.strategize().toString());
  }

  @Test
  public void testCornerStratThreeCornersNotFree() {
    CornerStrat cornerStrat = new CornerStrat(this.testCardsNoHoles);
    // setting up the board so there is only one free corner to go to
    this.testCardsNoHoles.playCard(0, 0, 0);
    this.testCardsNoHoles.playCard(0, 4, 4);
    this.testCardsNoHoles.playCard(0, 0, 4);
    // places the strongest card in the last available corner
    Assert.assertEquals("Card Idx: 1, Row: 4, Col: 0",
            cornerStrat.strategize().toString());
  }

  /*@Test
  public void testCornerStratCornersNotFree() {
    CornerStrat cornerStrat = new CornerStrat(this.testCardsNoHoles);
    // setting up the board so there are no available corners to defend at
    this.testCardsNoHoles.playCard(0, 0, 0);
    this.testCardsNoHoles.playCard(0, 4, 4);
    this.testCardsNoHoles.playCard(0, 0, 4);
    this.testCardsNoHoles.playCard(0, 4, 0);
  }*/

  @Test
  public void testCornerStratAllCornersChecked() {
    ThreeTriosMock.CornerStrat cornerStrat = mock.new CornerStrat();
    cornerStrat.strategize();
    String expected = "\n\n"
            + "Checking hand idx: 0\n"
            + "Row: 0\n"
            + "Col: 0\n"
            + "Row: 0\n"
            + "Col: 2\n"
            + "Row: 2\n"
            + "Col: 0\n"
            + "Row: 2\n"
            + "Col: 2\n"
            + "\n"
            + "Checking hand idx: 1\n"
            + "Row: 0\n"
            + "Col: 0\n"
            + "Row: 0\n"
            + "Col: 2\n"
            + "Row: 2\n"
            + "Col: 0\n"
            + "Row: 2\n"
            + "Col: 2\n"
            + "\n"
            + "Checking hand idx: 2\n"
            + "Row: 0\n"
            + "Col: 0\n"
            + "Row: 0\n"
            + "Col: 2\n"
            + "Row: 2\n"
            + "Col: 0\n"
            + "Row: 2\n"
            + "Col: 2\n"
            + "\n"
            + "Checking hand idx: 3\n"
            + "Row: 0\n"
            + "Col: 0\n"
            + "Row: 0\n"
            + "Col: 2\n"
            + "Row: 2\n"
            + "Col: 0\n"
            + "Row: 2\n"
            + "Col: 2\n"
            + "\n"
            + "Checking hand idx: 4\n"
            + "Row: 0\n"
            + "Col: 0\n"
            + "Row: 0\n"
            + "Col: 2\n"
            + "Row: 2\n"
            + "Col: 0\n"
            + "Row: 2\n"
            + "Col: 2";
    Assert.assertEquals(expected, mock.getTranscript());
  }
}
