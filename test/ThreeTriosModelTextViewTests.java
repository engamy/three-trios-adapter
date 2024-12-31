import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import controller.ConfigureGame;
import model.CardColor;
import model.PlayingCard;
import model.ThreeTriosModel;
import model.ThreeTriosModelInterface;
import view.TTTextView;

/**
 * Test class for all public methods in ThreeTriosModel and ThreeTriosModelInterface.
 */
public class ThreeTriosModelTextViewTests {

  private ThreeTriosModelInterface allCardsNoHoles;
  private ThreeTriosModelInterface fewCardsConnectedCells;
  private ThreeTriosModelInterface gameNotStarted;
  private ThreeTriosModelInterface gameAlmostOver;
  private ThreeTriosModelInterface gameNotInitialized;

  @Before
  public void setup() {
    gameNotStarted = new ThreeTriosModel();
    ConfigureGame.initGrid("GridConfigs/NoHolesBoard", gameNotStarted);
    ConfigureGame.initDeck("CardConfigs/AllCards", gameNotStarted);

    allCardsNoHoles = new ThreeTriosModel();
    ConfigureGame.initGrid("GridConfigs/NoHolesBoard", allCardsNoHoles);
    ConfigureGame.initDeck("CardConfigs/AllCards", allCardsNoHoles);
    allCardsNoHoles.initGame();

    fewCardsConnectedCells = new ThreeTriosModel();
    ConfigureGame.initGrid("GridConfigs/ConnectedCellsBoard", fewCardsConnectedCells);
    ConfigureGame.initDeck("CardConfigs/FewCards", fewCardsConnectedCells);
    fewCardsConnectedCells.initGame();

    gameAlmostOver = new ThreeTriosModel();
    ConfigureGame.initGrid("GridConfigs/EndGameBoardForTesting", gameAlmostOver);
    ConfigureGame.initDeck("CardConfigs/FewCards", gameAlmostOver);
    gameAlmostOver.initGame();

    gameNotInitialized = new ThreeTriosModel();
  }

  @Test
  public void testGetHand() {
    Assert.assertEquals("[LuisLunge 4 4 3 2, NervousNatalia 3 5 7 8, "
                    + "MatteMatt 4 9 6 5, LeftyLauren 3 6 2 3, AbsurdAtlas 2 A 2 4, "
                    + "SealedCece 4 3 2 2, JujuJulian 2 A 4 6, AloofAidan 4 6 2 A, "
                    + "AngstyAlex 2 9 5 2, MightyMay 5 2 A 6, FlamingFelix 2 6 3 7, "
                    + "ShimmyStephen A 6 5 8, MachoMonica 8 3 7 A]",
            this.allCardsNoHoles.getHand("R").toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetHandInvalidArg() {
    List<PlayingCard> thisIsNeverAssigned = this.allCardsNoHoles.getHand("KDJFNKSJDF");
  }

  @Test
  public void testGetCurrentPlayer() {
    Assert.assertEquals("R", this.allCardsNoHoles.getCurrentPlayer());
    // playCard changes who the current player is
    this.allCardsNoHoles.playCard(3, 2, 2);
    Assert.assertEquals("B", this.allCardsNoHoles.getCurrentPlayer());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetWinnerGameNotStarted() {
    String thisIsNeverAssigned = this.gameNotStarted.getWinner();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetWinnerGameNotOver() {
    String thisIsNeverAssigned = this.gameAlmostOver.getWinner();
  }

  @Test
  public void testGetWinnerBlueWin() {
    // create blue win case (2 blue, 1 red)
    // blue player play to (0,1)
    this.gameAlmostOver.playCard(0, 0, 1);
    // red player play to (0,3)
    this.gameAlmostOver.playCard(0, 0, 3);
    // blue player play to (3,1)
    this.gameAlmostOver.playCard(0, 1, 1);
    Assert.assertEquals(true, this.gameAlmostOver.isGameOver());
    Assert.assertEquals("Red wins!", this.gameAlmostOver.getWinner());
  }

  @Test
  public void testGetWinnerRedWin() {
    // create red win case (2 red, 1 blue)
    // blue play hand idx to (0,1) & make sure it's blue when placed
    this.gameAlmostOver.playCard(1, 0, 1);
    Assert.assertEquals(CardColor.R, this.gameAlmostOver.getGrid().get(0).get(1).getCardColor());
    // red play hand idx 0 to (1,1), make sure the card is red, and turn Blue player's card to R
    this.gameAlmostOver.playCard(0, 1, 1);
    Assert.assertEquals(CardColor.B,  this.gameAlmostOver.getGrid().get(0).get(1).getCardColor());
    // blue play handIdx to (0,3)
    this.gameAlmostOver.playCard(0, 0, 3);
    Assert.assertEquals(true, this.gameAlmostOver.isGameOver());
    Assert.assertEquals("Blue wins!", this.gameAlmostOver.getWinner());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIsGameOverGameNotStarted() {
    boolean thisIsNeverAssigned = this.gameNotStarted.isGameOver();
  }

  @Test
  public void testIsGameOverGameIsOver() {
    // blue player play to (0,1)
    this.gameAlmostOver.playCard(0, 0, 1);
    // red player play to (0,3)
    this.gameAlmostOver.playCard(0, 0, 3);
    // blue player play to (1,1)
    this.gameAlmostOver.playCard(0, 1, 1);
    Assert.assertEquals(true, this.gameAlmostOver.isGameOver());
  }

  @Test
  public void testIsGameOverGameNotOver() {
    Assert.assertEquals(false, this.gameAlmostOver.isGameOver());
  }

  @Test
  public void testGetDeck() {
    /* the FewCards file has the configuration for 25 cards total, but six cards are dealt from
     the deck into the player hands when the game is started because there are six EmptyCard cells
     in the board */
    Assert.assertEquals(20, this.fewCardsConnectedCells.getDeck().size());
  }

  @Test
  public void testGetGrid() {
    String expected = "[[_, _,  , _, _], "
            + "[_,  , _, _, _], "
            + "[_,  , _,  , _], "
            + "[_, _, _, _,  ], "
            + "[_, _,  , _, _]]";
    Assert.assertEquals(expected, this.fewCardsConnectedCells.getGrid().toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayCardGameNotStarted() {
    this.gameNotStarted.playCard(3, 3, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayCardInvalidHandIdx() {
    this.allCardsNoHoles.playCard(-123, 3, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayCardInvalidRowCol() {
    this.allCardsNoHoles.playCard(3, 3, -23);
  }

  @Test
  public void testPlayCardToHoleDoesNotRemoveFromDeck() {
    Assert.assertEquals(2, this.gameAlmostOver.getHand("B").size());
    this.gameAlmostOver.playCard(0, 3, 0); // this cell is a hole
    Assert.assertEquals(2, this.gameAlmostOver.getHand("B").size());
  }

  @Test
  public void testPlayCardRemoveCardFromDeck() {
    Assert.assertEquals(13, this.allCardsNoHoles.getHand("R").size());
    this.allCardsNoHoles.playCard(0, 0, 2);
    Assert.assertEquals(12,  this.allCardsNoHoles.getHand("R").size());

  }

  @Test
  public void testPlayCardToHoleCell() {
    // make sure there's a hole here
    Assert.assertEquals(" ", this.fewCardsConnectedCells.getGrid().get(0).get(2).toString());
    // try to play a card to the hole
    this.fewCardsConnectedCells.playCard(3, 0, 2);
    // there should not be a card placed here, and it should remain a hole
    Assert.assertEquals(" ", this.fewCardsConnectedCells.getGrid().get(0).get(2).toString());

  }

  @Test
  public void testPlayCardToPlayingCardCell() {
    // make sure the cell is empty at (2,2)
    Assert.assertEquals("_",
            this.fewCardsConnectedCells.getGrid().get(2).get(2).toString());
    // place a card at (2,2) & make sure it's there
    this.fewCardsConnectedCells.playCard(3, 2, 2);
    Assert.assertEquals("LeftyLauren 3 6 2 3",
            this.fewCardsConnectedCells.getGrid().get(2).get(2).toString());
    // try to play another card
    this.fewCardsConnectedCells.playCard(2, 2, 2);
    // the new card should not have placed, and it should remain the card that is first placed
    Assert.assertEquals("LeftyLauren 3 6 2 3",
            this.fewCardsConnectedCells.getGrid().get(2).get(2).toString());
  }

  @Test
  public void testPlayCardValidArgs() {
    // make sure no card or hole at (2, 2)
    Assert.assertEquals("_", this.allCardsNoHoles.getGrid().get(2).get(2).toString());
    // make sure the right card is at index 3 in blue player's hand
    Assert.assertEquals("GorgeousGianna 7 5 6 2",
            this.allCardsNoHoles.getHand("B").get(3).toString());
    // add card to (2, 2)
    this.allCardsNoHoles.playCard(3, 2, 2);
    // make sure the third card from blue player's hand is placed in (2,2)
    Assert.assertEquals("LeftyLauren 3 6 2 3",
            this.allCardsNoHoles.getGrid().get(2).get(2).toString());
  }

  @Test
  public void testPlayCardColorChanges() {
    // blue player places card at (2,2)
    this.allCardsNoHoles.playCard(3, 2, 2);
    // ensure the blue player's card is blue
    Assert.assertEquals(CardColor.R, this.allCardsNoHoles.getGrid().get(2).get(2).getCardColor());
    // red player places card at (1,2), which changes the color of the last card from blue to red
    this.allCardsNoHoles.playCard(8, 1, 2);
    // ensure the red player's card is red, and the blue player's card is also now red
    Assert.assertEquals(CardColor.B, this.allCardsNoHoles.getGrid().get(1).get(2).getCardColor());
    Assert.assertEquals(CardColor.R, this.allCardsNoHoles.getGrid().get(2).get(2).getCardColor());
  }

  @Test
  public void testInitGame() {
    this.gameNotStarted.initGame();
    Assert.assertNotEquals(0, this.gameNotStarted.getGrid().size());
    Assert.assertNotEquals(0, this.gameNotStarted.getDeck().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInitGameGameAlreadyStarted() {
    this.allCardsNoHoles.initGame();
  }

  @Test
  public void testIsCellFree() {
    // is an empty card cell free (yes)
    Assert.assertEquals(true, this.allCardsNoHoles.isCellFree(0, 0));
    // is a playing card cell free (no)
    this.allCardsNoHoles.playCard(0, 0, 0);
    Assert.assertEquals(false, this.allCardsNoHoles.isCellFree(0, 0));
    // is a hole cell free (no)
    Assert.assertEquals(false, this.gameAlmostOver.isCellFree(3, 3));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testIsCellFreeInvalidArg() {
    this.allCardsNoHoles.isCellFree(123123, 1);
  }

  @Test
  public void testGetScore() {
    // no one has played (+0 for both, 13 in both hands)
    Assert.assertEquals(13, this.allCardsNoHoles.getScore("B"));
    Assert.assertEquals(13, this.allCardsNoHoles.getScore("R"));
    // both players play
    this.allCardsNoHoles.playCard(0, 1, 1); // blue play -1 +1 (+0 B)
    this.allCardsNoHoles.playCard(0, 2, 3); // red play -1 +1 (+0 R)
    this.allCardsNoHoles.playCard(0, 3, 2); // blue play -1 +1 (+0 B)
    this.allCardsNoHoles.playCard(0, 3, 3); // red play, turn (3,2) blue card red
    // -1 +2 (+1 R) (-1 B)
    Assert.assertEquals(14, this.allCardsNoHoles.getScore("B"));
    Assert.assertEquals(12, this.allCardsNoHoles.getScore("R"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetScoreInvalidArg() {
    this.allCardsNoHoles.getScore("errrrm");
  }

  @Test
  public void testNumCardsFlipped() {
    // setting up the board
    // CURRENT PLAYER: BLUE
    this.allCardsNoHoles.playCard(0, 1, 1);
    // CURRENT PLAYER: RED
    this.allCardsNoHoles.playCard(0, 2, 3); // red play
    // CURRENT PLAYER: BLUE | Playing card 7 5 6 2
    // test that 0 cards can be flipped for playing a blue card to a card not adjacent
    Assert.assertEquals(0, this.allCardsNoHoles.numCardsFlipped(2,3, 2));
    // play that card
    this.allCardsNoHoles.playCard(2, 3, 2); // blue play
    // CURRENT PLAYER: RED | Playing card 3 5 7 8
    // test that 1 card can be flipped for playing a red card to an adjacent card that wins
    // the number battle
    Assert.assertEquals(1, this.allCardsNoHoles.numCardsFlipped(0, 3, 3));
    // test that 0 cards can be flipped for playing a red card to an adjacent card that does not
    // win the number battle
    Assert.assertEquals(1, this.allCardsNoHoles.numCardsFlipped(4, 3, 1));
    // testing that it returns multiple flipped cards when appropriate
    // add losing blue cards around (1, 2)
    this.allCardsNoHoles.playCard(4, 3, 1);
    // CURRENT PLAYER: BLUE | Playing card JumpyJulia 4 *2* 2 A, CAN BE FLIPPED
    this.allCardsNoHoles.playCard(0, 0, 2);
    // CURRENT PLAYER: RED play to space separate from this case
    this.allCardsNoHoles.playCard(0, 4, 0);
    // CURRENT PLAYER: BLUE | Playing card MemorableMimi 2 3 A *2*, CAN BE FLIPPED
    this.allCardsNoHoles.playCard(5, 1, 3);
    // CURRENT PLAYER: RED play to space separate from this case
    this.allCardsNoHoles.playCard(0, 4, 1);
    // CURRENT PLAYER: BLUE | Playing card AngryAlison *2* 3 4 5, CAN BE FLIPPED
    this.allCardsNoHoles.playCard(2, 2, 2);
    // CURRENT PLAYER: RED | ShimmyStephen A 6 5 8 checking that you can see you can flip three
    // of the surrounding four blue cards by placing red card in center of them
    Assert.assertEquals(3, this.allCardsNoHoles.numCardsFlipped(7, 1, 2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNumCardsFlippedIllegalArg() {
    this.allCardsNoHoles.numCardsFlipped(0, 3, -3);
    this.allCardsNoHoles.numCardsFlipped(-123, 3, -3);

  }
  
  @Test
  public void testCreateDeckCreateGrid() {
    // createGrid() and createDeck() are called in initGrid() and initDeck() with the list
    // of cards in the deck configured, so once this is called, the deck should have cards in it
    // and the grid should not be empty
    ConfigureGame.initGrid("GridConfigs/EndGameBoardForTesting", this.gameNotInitialized);
    ConfigureGame.initDeck("CardConfigs/AllCards", this.gameNotInitialized);
    this.gameNotInitialized.initGame();
    Assert.assertEquals(4, this.gameNotInitialized.getDeck().size());
    Assert.assertEquals(4, this.gameNotInitialized.getGrid().size());
  }
  
  @Test
  public void testCountFreeSpace() {
    // no holes or playing card cells
    Assert.assertEquals(25, this.allCardsNoHoles.countFreeSpace());
    // some holes and some playing card cells
    this.gameAlmostOver.playCard(0, 0, 1);
    Assert.assertEquals(2, this.gameAlmostOver.countFreeSpace());
  }

  // TESTING TEXT VIEW TOSTRING()

  @Test
  public void testViewToString() {
    TTTextView view = new TTTextView(this.allCardsNoHoles);
    String expected = "Player: R\n"
            + "_____\n"
            + "_____\n"
            + "_____\n"
            + "_____\n"
            + "_____\n"
            + "Hand:\n"
            + "LuisLunge 4 4 3 2\n"
            + "NervousNatalia 3 5 7 8\n"
            + "MatteMatt 4 9 6 5\n"
            + "LeftyLauren 3 6 2 3\n"
            + "AbsurdAtlas 2 A 2 4\n"
            + "SealedCece 4 3 2 2\n"
            + "JujuJulian 2 A 4 6\n"
            + "AloofAidan 4 6 2 A\n"
            + "AngstyAlex 2 9 5 2\n"
            + "MightyMay 5 2 A 6\n"
            + "FlamingFelix 2 6 3 7\n"
            + "ShimmyStephen A 6 5 8\n"
            + "MachoMonica 8 3 7 A\n";
    Assert.assertEquals(expected, view.toString());
  }
}
