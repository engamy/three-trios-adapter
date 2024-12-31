package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the model for the Three Trios game that implements the game rules.
 */
public class ThreeTriosModel implements ThreeTriosModelInterface {

  private List<List<Cell>> grid;
  private List<PlayingCard> deck = new ArrayList<>();
  private CardColor currPlayer = CardColor.R;
  private List<PlayingCard> redPlayerHand;
  private List<PlayingCard> bluePlayerHand;
  private List<ModelFeatures> listeners = new ArrayList<ModelFeatures>();
  private boolean gameStarted = false;

  /**
   * Creates an instance of the ThreeTriosModel class.
   */
  public ThreeTriosModel() {
    // empty because all information is instantiated when controller's startGame() method is called
  }

  @Override
  public void initGame() {
    if ((grid == null) || (deck == null)) {
      throw new IllegalArgumentException("GridPath or CardPath cannot be null.");
    }
    if (!this.gameStarted) {
      // fills both players' hands
      this.dealCards(this.deck);
      this.gameStarted = true;
    }

    else {
      throw new IllegalArgumentException("Game already started");
    }
  }

  @Override
  public void playCard(int handIdx, int row, int col) {
    Ensure.checkGameStarted(this.gameStarted);
    PlayingCard cardToPlay;
    try {
      if (currPlayer.equals(CardColor.B)) {
        cardToPlay = this.bluePlayerHand.get(handIdx);
      }
      else if (currPlayer.equals(CardColor.R)) {
        cardToPlay = this.redPlayerHand.get(handIdx);
      }
      else {
        throw new IllegalArgumentException("This exception is never thrown due to the nature of "
                + "enums in Java :)");
      }
    } catch (IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException("Hand index " + handIdx + " to draw from "
              + currPlayer + " is out of bounds");

    }
    int rowBound = this.grid.size();
    int colBound = this.grid.get(Ensure.checkIdxBounds(row, 0, rowBound)).size();

    if (this.grid.get(row).get(Ensure.checkIdxBounds(col, 0, colBound)).isFreeSpace()) {
      this.grid.get(row).set(col, cardToPlay);
      this.commenceNumberBattle(cardToPlay, row, col);

      Cell currentCell = this.grid.get(row).get(col);
      if (!(currentCell.isFreeSpace() || currentCell.isInactiveSpace())) {
        if (currPlayer.equals(CardColor.B)) {
          this.bluePlayerHand.remove(handIdx);
        }
        else if (currPlayer.equals(CardColor.R)) {
          this.redPlayerHand.remove(handIdx);
        }
        turnChange();
      }
    }
  }

  @Override
  public String getCurrentPlayer() {
    return this.currPlayer.toString();
  }

  /**
   * Changes who the current player is.
   */
  private void turnChange() {
    if (this.currPlayer == CardColor.B) {
      this.currPlayer = CardColor.R;
    }
    else {
      this.currPlayer = CardColor.B;
    }

    for (ModelFeatures listener : this.listeners) {
      listener.turnHasCompleted();
    }
  }

  /**
   * Deals the cards from the given deck to the players' hands.
   * @param deck the deck of cards to deal
   */
  private void dealCards(List<PlayingCard> deck) {
    ArrayList<PlayingCard> blueHand = new ArrayList<>();
    ArrayList<PlayingCard> redHand = new ArrayList<>();
    for (int cardIdx = 1; cardIdx <= deck.size(); cardIdx++) {
      if ((cardIdx % 2) != 0) {
        PlayingCard redCard = deck.get(cardIdx - 1);
        // need to call this because cards are default blue, so blue cards added to red deck
        // must be changed to red
        redCard.changeColor();
        redHand.add(redCard);
      }
      if ((cardIdx % 2) == 0) {
        blueHand.add(deck.get(cardIdx - 1));
      }
    }
    this.redPlayerHand = redHand;
    this.bluePlayerHand = blueHand;
  }

  /**
   * Changes all the card colors according to the number battle rules.
   * @param card represents the card the player had just plated
   * @param row represents the row coordinate of the played card
   * @param col represents the col coordinate of the played card
   */
  private void commenceNumberBattle(Cell card, int row, int col) {
    Map<String, Cell> opposingCards = findAdjacentPlayedCards(row, col);
    CardColor color = card.getCardColor();
    Ensure.checkGameStarted(this.gameStarted);
    for (Map.Entry<String,Cell> mapElement : opposingCards.entrySet()) {
      card.compareCard(mapElement.getValue(), mapElement.getKey());
      if (mapElement.getValue().getCardColor().equals(color)) {
        commenceNumberBattle(mapElement.getValue(),
                gridNav(mapElement.getKey(), "row", row),
                gridNav(mapElement.getKey(), "col", col));
      }
    }
  }

  /**
   * Navigates the given value to find a new grid coordinate.
   * @param direction represents which direction from the current coordinates to move
   * @param coord represents if we're looking for the new row or column coordinate
   * @param value represents the current row or col coordinate
   * @return a new current coordinate value in the given direction
   */
  private int gridNav(String direction, String coord, int value) {
    int result = value;
    if (direction.equals("north") && coord.equals("row")) {
      result -= 1;
    }
    if (direction.equals("south") && coord.equals("row")) {
      result += 1;
    }
    if (direction.equals("west") && coord.equals("col")) {
      result -= 1;
    }
    if (direction.equals("east") && coord.equals("col")) {
      result += 1;
    }
    return result;
  }

  /**
   * Finds all the adjacent cells that are not empty or holes.
   * @param row the row of the center cell
   * @param col the col of the center cell
   * @return a map of all adjacent playing cards and their location relative to the center
   *         card (North, South, East, or West)
   */
  private Map<String, Cell> findAdjacentPlayedCards(int row, int col) {
    Ensure.checkGameStarted(this.gameStarted);

    Map<String, Cell> result = new HashMap<>();
    Cell playedCard = this.grid.get(row).get(col);
    // Only used for Strat calculations
    if (playedCard.isFreeSpace()) {
      playedCard = new PlayingCard("FutureSense",
              CardValue.A, CardValue.A, CardValue.A, CardValue.A, this.currPlayer);
    }
    //
    int rowBound = this.grid.size();
    int colBound = this.grid.get(row).size();

    if (Ensure.checkBounds(row + 1, 0, rowBound)) {
      if (this.grid.get(row + 1).get(col).validAdjacent(playedCard)) {
        result.put("south",(this.grid.get(row + 1).get(col)));
      }
    }

    if (Ensure.checkBounds(row - 1, 0, rowBound)) {
      if (this.grid.get(row - 1).get(col).validAdjacent(playedCard)) {
        result.put("north", (this.grid.get(row - 1).get(col)));
      }
    }

    if (Ensure.checkBounds(col + 1, 0, colBound)) {
      if (this.grid.get(row).get(col + 1).validAdjacent(playedCard)) {
        result.put("east", (this.grid.get(row).get(col + 1)));
      }
    }

    if (Ensure.checkBounds(col - 1, 0, colBound)) {
      if (this.grid.get(row).get(col - 1).validAdjacent(playedCard)) {
        result.put("west", (this.grid.get(row).get(col - 1)));
      }
    }

    return result;
  }

  @Override
  public boolean isGameStarted() {

    return this.gameStarted;
  }

  @Override
  public void checkGameEnded() {
    for (ModelFeatures listener: this.listeners) {
      listener.gameOver();
    }
  }

  @Override
  public boolean isGameOver() {
    Ensure.checkGameStarted(this.gameStarted);
    boolean finishedGame = true;
    for (int i = 0; i < this.grid.size(); i++) {
      for (int j = 0; j < this.grid.get(i).size(); j++) {
        if (this.grid.get(i).get(j).isFreeSpace()) {
          finishedGame = false;
        }
      }
    }
    refreshListeners();
    return finishedGame;
  }

  @Override
  public String getWinner() {
    Ensure.checkGameStarted(this.gameStarted);
    if (this.isGameOver()) {
      int numRedCards = 0;
      int numBlueCards = 0;
      for (List<Cell> row : this.grid) {
        for (Cell c : row) {
          CardColor color;
          try {
            color = c.getCardColor();
            if (color.equals(CardColor.B)) {
              numBlueCards++;
            }
            if (color.equals(CardColor.R)) {
              numRedCards++;
            }
          } catch (UnsupportedOperationException ex) {
            // do nothing if you try to get the color of a Hole or EmptyCard
          }
        }
      }
      if (numRedCards > numBlueCards) {
        return "Red wins!\nRedScore: " + numRedCards + "\nBlueScore: " + numBlueCards;
      }
      if (numRedCards < numBlueCards) {
        return "Blue wins!\nRedScore: " + numRedCards + "\nBlueScore: " + numBlueCards;
      }
      else {
        throw new IllegalArgumentException("Error: there are equal blue and red cards, meaning"
                + " that there is an even number of EmptyCard Cells");
      }
    } else {
      throw new IllegalArgumentException("Game is not over. "
              + "Cannot find winner of an incomplete game.");
    }
  }

  @Override
  public List<PlayingCard> getHand(String player) {
    if (player.equals("B")) {
      return this.bluePlayerHand;
    } else if (player.equals("R")) {
      return this.redPlayerHand;
    }
    else {
      throw new IllegalArgumentException("Cannot find the hand of the player: " + player);
    }
  }

  @Override
  public List<List<Cell>> getGrid() {
    List<List<Cell>> gridCopy = this.grid;
    return gridCopy;
  }

  @Override
  public List<PlayingCard> getDeck() {
    List<PlayingCard> deckCopy = this.deck;
    return deckCopy;
  }

  @Override
  public int getScore(String player) {
    if (!((player.equals("B")) || (player.equals("R")))) {
      throw new IllegalArgumentException("Invalid player " + player + " to retrieve score of.");
    }
    int blueScore = this.redPlayerHand.size();
    int redScore = this.bluePlayerHand.size();
    for (List<Cell> row : this.grid) {
      for (Cell c : row) {
        try {
          if (c.getCardColor().equals(CardColor.B)) {
            blueScore = blueScore + 1;
          }
          if (c.getCardColor().equals(CardColor.R)) {
            redScore = redScore + 1;
          }
        } catch (UnsupportedOperationException ex) {
          // trying to get card color of holes/empty cards
          // throws ex, so do nothing when counting score
        }
      }
    }
    if (player.equals("B")) {
      return blueScore;
    } else { // already checked that this can only be "R" in the beginning of the method
      return redScore;
    }
  }

  @Override
  public int numCardsFlipped(int handIdx, int row, int col) {
    Ensure.checkGameStarted(this.gameStarted);
    Ensure.checkIdxBounds(row, 0, this.grid.size());
    Ensure.checkIdxBounds(col, 0, this.grid.get(row).size());
    Ensure.checkIdxBounds(handIdx, 0,
            this.getHand(this.getCurrentPlayer().toString()).size());

    Map<String, Cell> opposingCards = findAdjacentPlayedCards(row, col);
    Cell testCard = this.grid.get(row).get(col);

    if (currPlayer.equals(CardColor.B)) {
      testCard = this.bluePlayerHand.get(handIdx);
    }
    if (currPlayer.equals(CardColor.R)) {
      testCard = this.redPlayerHand.get(handIdx);
    }
    int result = 0;
    for (Map.Entry<String,Cell> mapElement : opposingCards.entrySet()) {
      if (testCard.flipCount(mapElement.getValue(), mapElement.getKey())) {
        result += 1;
      }
    }
    return result;
  }

  private int flipCounter(Cell playingCard, int row, int col) {
    int result = 0;
    Map<String,Cell> opposingCards = findAdjacentPlayedCards(row, col);
    for (Map.Entry<String,Cell> mapElement : opposingCards.entrySet()) {
      if (playingCard.flipCount(mapElement.getValue(), mapElement.getKey())) {
        result += 1 + flipCounter(mapElement.getValue(),
                gridNav(mapElement.getKey(), "row", row),
                gridNav(mapElement.getKey(), "col", col));
      }
    }
    return result;
  }

  @Override
  public boolean isCellFree(int row, int col) {
    Ensure.checkIdxBounds(row, 0, this.grid.size());
    Ensure.checkIdxBounds(col, 0, this.grid.get(row).size());
    return this.grid.get(row).get(col).isFreeSpace();
  }

  /**
   * Calculates the number of free spaces in the grid.
   */
  public int countFreeSpace() {
    int num = 0;
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.get(i).size(); j++) {
        if (grid.get(i).get(j).isFreeSpace()) {
          num++;
        }
      }
    }
    return num;
  }

  @Override
  public void createDeck(List<String> config) {
    if ((countFreeSpace()) > config.size()) {
      throw new IllegalArgumentException("Too many spaces for the number of cards in the deck.");
    }
    ArrayList<PlayingCard> tempDeck = new ArrayList<>();
    int cardspaces = countFreeSpace();
    for (int curr = 0 ; curr < config.size() && curr < cardspaces + 1; curr++ ) {
      String[] list = config.get(curr).split(" ");
      tempDeck.add(new PlayingCard(list[0], parseCardValue(list[1]), parseCardValue(list[2]),
              parseCardValue(list[3]), parseCardValue(list[4]), CardColor.B));
    }
    this.deck = tempDeck;
  }

  /**
   * Converts the given string into a CardValue Enum.
   * @param value the value of the card
   * @return the appropriate CardValue
   */
  private CardValue parseCardValue(String value) {
    value = value.replace(" ", "");
    switch (value) {
      case "1":
        return CardValue.ONE;
      case "2":
        return CardValue.TWO;
      case "3":
        return CardValue.THREE;
      case "4":
        return CardValue.FOUR;
      case "5":
        return CardValue.FIVE;
      case "6":
        return CardValue.SIX;
      case "7":
        return CardValue.SEVEN;
      case "8":
        return CardValue.EIGHT;
      case "9":
        return CardValue.NINE;
      case "A":
        return CardValue.A;
      default:
        throw new IllegalArgumentException("Invalid card value: " + value);
    }
  }

  /**
   * Initializes the grid based on the number of rows, cols, and given grid config.
   * @param rows the number of rows
   * @param cols the number of cols
   * @param config the config of the grid
   */
  public void createGrid(int rows, int cols, List<String> config) {
    if ((rows * cols) < 3) {
      throw new IllegalArgumentException("Not enough rows/cols for a game! Make sure there are at "
              + "least three cells in the game.");
    }

    List<List<Cell>> newGrid = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      newGrid.add(new ArrayList<>());
      List<Cell> currList = newGrid.get(i);
      for (int j = 0; j < cols; j++) {
        String currChar = config.get(i).substring(j, j + 1);
        if (currChar.equals("X")) {
          currList.add(new Hole());
        }
        if (currChar.equals("C")) {
          currList.add(new EmptyCard());
        }
      }
    }
    this.grid = newGrid;
  }

  @Override
  public void aiWarBehavior() {
    if (this.listeners.size() == 2) {
      if (this.listeners.get(0).getPlayer().isAIPlayer()
              && this.listeners.get(1).getPlayer().isAIPlayer()) {
        // We use turnHasCompleted in order to jumpstart the AI vs AI games
        this.listeners.get(0).turnHasCompleted();
      }
    }
  }

  @Override
  public void addListeners(ModelFeatures listener) {
    this.listeners.add(listener);
  }

  private void refreshListeners() {
    for (ModelFeatures listener : this.listeners) {
      listener.refreshViews();
    }
  }
}


