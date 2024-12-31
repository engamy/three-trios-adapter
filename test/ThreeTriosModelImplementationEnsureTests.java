import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import model.CardColor;
import model.Ensure;
import model.Hole;
import model.PlayingCard;
import model.CardValue;
import model.EmptyCard;

/**
 * Test class for public methods in the following classes/interfaces.
 * - CardColor
 * - CardValue
 * - EmptyCard
 * - Hole
 * - PlayingCard
 * - Ensure
 */
public class ThreeTriosModelImplementationEnsureTests {

  private EmptyCard emptyCard;
  private PlayingCard plumpPumpkin;
  private PlayingCard funkyFella;
  private PlayingCard heroicHare;
  private Hole hole;

  @Before
  public void setup() {

    emptyCard = new EmptyCard();
    funkyFella = new PlayingCard("FunkyFella", CardValue.A, CardValue.TWO, CardValue.TWO,
            CardValue.THREE, CardColor.B);
    plumpPumpkin = new PlayingCard("PlumpPumpkin", CardValue.FOUR, CardValue.SIX,
            CardValue.SEVEN, CardValue.A, CardColor.B);
    heroicHare = new PlayingCard("HeroicHare", CardValue.TWO, CardValue.NINE, CardValue.TWO,
            CardValue.TWO, CardColor.R);
    hole = new Hole();
  }

  /*
  @Test // playing around with fileReader
  public void testFileReading() throws IOException {
    String path = ".idea/testFileReading";
    File file = new File(path);
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
      sb.append(System.lineSeparator());
    }
    String fileContent = sb.toString();
    System.out.println(fileContent);
    // should be: "These are the contents in the file."
  }*/

  // TESTS FOR CARDCOLOR AND CARDVALUE

  @Test
  public void testCardColorToString() {
    Assert.assertEquals("B", CardColor.B.toString());
    Assert.assertEquals("R", CardColor.R.toString());
  }

  @Test
  public void testGetCardValue() {
    Assert.assertEquals(10, CardValue.A.getValue());
    Assert.assertEquals(5, CardValue.FIVE.getValue());
  }

  @Test
  public void testCardValueToString() {
    Assert.assertEquals("A", CardValue.A.toString());
    Assert.assertEquals("4", CardValue.FOUR.toString());
  }


  // TESTS FOR EMPTYCARD

  @Test
  public void testEmptyCardValidAdjacent() {
    Assert.assertEquals(false, this.emptyCard.validAdjacent(this.funkyFella));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyCardGetCardValue() {
    CardValue thisIsNeverAssigned = this.emptyCard.getCardValue("north");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyCardGetCardColor() {
    CardColor thisIsNeverAssigned = this.emptyCard.getCardColor();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyCardChangeColor() {
    this.emptyCard.changeColor();
  }

  @Test
  public void testEmptyCardIsFreeSpace() {
    Assert.assertEquals(true, this.emptyCard.isFreeSpace());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyCardCompareCard() {
    this.emptyCard.compareCard(this.funkyFella, "north");
  }

  @Test
  public void testEmptyCardToString() {
    Assert.assertEquals("_", this.emptyCard.toString());
  }

  @Test
  public void testEmptyCardIsInactiveSpace() {
    Assert.assertEquals(false, this.emptyCard.isInactiveSpace());
  }

  @Test
  public void testEmptyCardFlipCount() {
    Assert.assertEquals(false, this.emptyCard.flipCount(this.funkyFella, "north"));
    Assert.assertEquals(false, this.emptyCard.flipCount(this.funkyFella, "south"));
    Assert.assertEquals(false, this.emptyCard.flipCount(this.funkyFella, "east"));
    Assert.assertEquals(false, this.emptyCard.flipCount(this.funkyFella, "west"));
    Assert.assertEquals(false, this.emptyCard.flipCount(this.hole, "west"));
  }

  @Test
  public void testEmptyCardFlipDifficulty() {
    Assert.assertEquals(0, this.emptyCard.flipDifficulty(Arrays.asList("west", "east",
            "north", "south")));
  }

  // TESTS FOR HOLE

  @Test
  public void testHoleValidAdjacent() {
    Assert.assertEquals(false, this.hole.validAdjacent(this.funkyFella));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHoleGetCardValue() {
    CardValue thisIsNeverAssigned = this.hole.getCardValue("north");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHoleGetCardColor() {
    CardColor thisIsNeverAssigned = this.hole.getCardColor();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHoleChangeColor() {
    this.hole.changeColor();
  }

  @Test
  public void testHoleIsFreeSpace() {
    Assert.assertEquals(false, this.hole.isFreeSpace());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHoleCompareCard() {
    this.hole.compareCard(this.funkyFella, "north");
  }

  @Test
  public void testHoleToString() {
    Assert.assertEquals(" ", this.hole.toString());
  }

  @Test
  public void testHoleIsInactiveSpace() {
    Assert.assertEquals(true, this.hole.isInactiveSpace());
  }

  @Test
  public void testHoleFlipCount() {
    Assert.assertEquals(false, this.hole.flipCount(this.funkyFella, "north"));
    Assert.assertEquals(false, this.hole.flipCount(this.funkyFella, "south"));
    Assert.assertEquals(false, this.hole.flipCount(this.funkyFella, "east"));
    Assert.assertEquals(false, this.hole.flipCount(this.funkyFella, "west"));
    Assert.assertEquals(false, this.hole.flipCount(this.emptyCard, "west"));

  }

  @Test
  public void testHoleFlipDifficulty() {
    Assert.assertEquals(0, this.hole.flipDifficulty(Arrays.asList("west", "east")));
  }

  // TESTS FOR PLAYINGCARD

  @Test
  public void testPCValidAdjacent() {
    // same colors
    Assert.assertEquals(false, this.funkyFella.validAdjacent(this.plumpPumpkin));
    // that is a hole
    Assert.assertEquals(false, this.funkyFella.validAdjacent(this.hole));
    // that is an empty color
    Assert.assertEquals(false, this.funkyFella.validAdjacent(this.emptyCard));
    // different colors
    Assert.assertEquals(true, this.funkyFella.validAdjacent(this.heroicHare));

  }

  @Test
  public void testPCGetCardValue() {
    Assert.assertEquals(CardValue.A, this.funkyFella.getCardValue("north"));
    Assert.assertEquals(CardValue.TWO, this.funkyFella.getCardValue("south"));
    Assert.assertEquals(CardValue.TWO, this.funkyFella.getCardValue("east"));
    Assert.assertEquals(CardValue.THREE, this.funkyFella.getCardValue("west"));
  }

  @Test
  public void testPCCardColor() {
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    Assert.assertEquals(CardColor.R, this.heroicHare.getCardColor());
  }

  @Test
  public void testPCChangeColor() {
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    this.funkyFella.changeColor();
    Assert.assertEquals(CardColor.R, this.funkyFella.getCardColor());

    Assert.assertEquals(CardColor.R, this.heroicHare.getCardColor());
    this.heroicHare.changeColor();
    Assert.assertEquals(CardColor.B, this.heroicHare.getCardColor());
  }

  @Test
  public void testPCIsFreeSpace() {
    Assert.assertEquals(false, this.funkyFella.isFreeSpace());
  }

  @Test
  public void testPCCompareCardChangeColor() {
    // FunkyFella(Blue) should beat HeroicHare(Red) and turn it Blue
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    Assert.assertEquals(CardColor.R, this.heroicHare.getCardColor());
    this.funkyFella.compareCard(this.heroicHare, "north");
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    Assert.assertEquals(CardColor.B, this.heroicHare.getCardColor());
  }

  @Test
  public void testPCCompareCardDontChangeColor() {
    // FunkyFella(Blue) should NOT beat HeroicHare(Red) and colors should remain the same
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    Assert.assertEquals(CardColor.R, this.heroicHare.getCardColor());
    this.funkyFella.compareCard(this.heroicHare, "south");
    Assert.assertEquals(CardColor.B, this.funkyFella.getCardColor());
    Assert.assertEquals(CardColor.R, this.heroicHare.getCardColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPCCompareCardIllegalArg() {
    this.funkyFella.compareCard(this.heroicHare, "kdsjfnajsdfn");
  }

  @Test
  public void testPCToString() {
    Assert.assertEquals("FunkyFella A 2 2 3", this.funkyFella.toString());
    Assert.assertEquals("HeroicHare 2 9 2 2", this.heroicHare.toString());
    Assert.assertEquals("PlumpPumpkin 4 6 7 A", this.plumpPumpkin.toString());
  }

  @Test
  public void testPCIsInactiveSpace() {
    Assert.assertEquals(false, this.funkyFella.isInactiveSpace());
  }

  @Test
  public void testPCFlipCount() {
    Assert.assertEquals(true, this.funkyFella.flipCount(this.heroicHare, "north"));
    Assert.assertEquals(true, this.plumpPumpkin.flipCount(this.heroicHare, "south"));
    Assert.assertEquals(true, this.plumpPumpkin.flipCount(this.funkyFella, "east"));
    Assert.assertEquals(true, this.funkyFella.flipCount(this.heroicHare, "west"));
    Assert.assertEquals(false, this.funkyFella.flipCount(this.heroicHare, "east"));
    Assert.assertEquals(false, this.funkyFella.flipCount(this.emptyCard, "north"));
    Assert.assertEquals(false, this.funkyFella.flipCount(this.hole, "north"));
  }

  @Test
  public void testPCFlipDifficulty() {
    Assert.assertEquals(27, this.plumpPumpkin.flipDifficulty(Arrays.asList("west", "east",
            "north", "south")));
    Assert.assertEquals(10, this.plumpPumpkin.flipDifficulty(Arrays.asList(
            "north", "south")));
  }

  // TESTING ENSURE

  @Test(expected = IllegalArgumentException.class)
  public void testEnsureCheckIdxBadIdx() {
    int numTooGreat = Ensure.checkIdxBounds(10000, 0, 2);
    int numTooLittle = Ensure.checkIdxBounds(-23, 0, 3);
    int numLowBound = Ensure.checkIdxBounds(2, 2, 5);
    int numHighBound = Ensure.checkIdxBounds(5, 2, 5);
  }

  @Test
  public void testEnsureCheckIdxGoodIdx() {
    Assert.assertEquals(14, Ensure.checkIdxBounds(14, 0, 20));
    Assert.assertEquals(-123, Ensure.checkIdxBounds(-123, -200, 20));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEnsureGameStartedGameNotStarted() {
    Ensure.checkGameStarted(false);
  }


}
