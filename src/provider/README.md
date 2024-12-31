
OVERVIEW:

This code is an implementation of the game Three Trios. Three Trios is a card game that involves two players who take turns placing cards on a board. Each card has 4 values ranging from 1-9 and A and which represent its attack values. The cards will battle and the overall objective of the game is to have the most cards by the end of the game.

Gameplay: the two players will alternate turns and place cards on the board where they are allowed. Cards can be placed on an empty card cell and cannot be placed on a hole.

Battle: after a card is placed, it can flip the adjacent card if it is the opponent’s and if it has a higher attack value. A combo battle may also be triggered if a flipped card has a higher attack value than another adjacent opponent card.

Board: the board can be customized but must contain an odd number of card cells. A board consists of holes and card cells to create complicated board shapes.
Reachable board: the reachable board is a board with holes where all card cells can reach each other and that two groups is a board with holes where at least two groups of card cells cannot reach each other.
Cards: 10 cards is enough to play the two groups board but not enough to play the reachable board and that the 30 cards file is enough cards to play any board.


Assumptions made: Players start with an equal number of cards and the game will end after the board is full.

QUICK START:

The following code snippet is from the ModelTests class and it represents a whole run through of the game.
In this snippet the board is initialized as a 3x3 and it has two holes.
The game is then started and it alternates turns between both players.
Once the game board is full it checks to see who has the most cards.

@Test   
public void testFullGame() {   
ArrayList<Position> holes = new ArrayList<>();
holes.add(new Position(1, 1));
holes.add(new Position(2, 0));
game = new ThreeTriosModel(3, 3, deck, holes);
game.startGame();
game.playTurn(0, new Position(0, 0));
game.playTurn(0, new Position(0, 1));
game.playTurn(0, new Position(0, 2));
game.playTurn(0, new Position(1, 0));
game.playTurn(0, new Position(1, 2));
game.playTurn(0, new Position(2, 1));
game.playTurn(0, new Position(2, 2));
Assert.assertTrue("Game should be over", game.isGameOver());
PlayerColor winner = game.gameWon();
Assert.assertNotNull("A winner should be determined", winner);
Assert.assertEquals("Winner is blue", PlayerColor.Blue, winner);
}

KEY COMPONENTS: Model, Board, Card, Player, Battle, and TextView

Model: The model is the game controller and it manages the game state, the players and whose turn it is. The model will also initialize the game board and players.

Board: The board represents the game board. it manages the grid of cells which includes card placements, card cells and holes. It also provides the methods to place cards, check for valid positions and check whether the board is full.

Card: The card represents the individual cards along with their attack values and who owns the cards. The  attributes for a card include the attack values for North, South, East, and West and also the owner of the card.

Player: the player represents a player of the game. The player has a hand of cards and is able to place cards on the board during the turn. The player class is also able to manage the player’s hand.

Battle: The battle represents the battle logic when a card is placed. It is able to compare adjacent opponent’s cards’ attack values. It can also flip ownership of opponent’s cards if the proper conditions are met. it will also recursively call combo battles if needed.

KEY SUBCOMPONENTS: Enums, Board Cells and Positions

Enums: the enums include Attackvalue, CellType, Direction, GameState and PlayerColor

AttackValue: represents the possible attack values a card can have

CellType: Represents a cell type whether thats Hole or CardCell

Direction: Represents the possible directions

GameState: Represents the game state whether it has not started, is in progress or is over.

PlayerColor: Represents the color of the player, red or blue.

Board Cells: Board cells represents an individual cell on the board. It also manages the card placement and retrival.

Positions: Positions shows the row and column coordinates on the board and it is used to specify locations for card placement

SOURCE ORGANIZATION:

DOCS/CONFIG:

Board: this directory contains configuration files defining different board layouts, such as: EvenBoard, NoHoles, Reachable, SimpleBoardWithHole, and TwoGroups.

Cards: this directory also contains configuration files but these ones define the different cards.

SRC:

model package: the main directory containing all of the components related to the games model.

    Model interface: the interface defines the structure and actions in the Three Trios game.

    ThreeTriosModel: a class that implements the model to represent a single game of Three Trios.

    Player Interface: This interface represents a player in the game.

    RegularPlayer: a class that implements the player interface to represent a standard player in the Three Trios game.

    Reader: a class that is responsible for reading the configuration files to set up the board and deck of cards.


battle package: contains classes related to the battles between players and their cards.

    Battle interface: defines the structure for battle relation classes and actions.

    SimpleBattle: a class implementing battle that provides specific logic for executing battle.

board package: contains classes and interfaces for representing the game board.

    Grid interface: an interface which represents a grid where cards can be placed.

    Board: a class that represents the game board itself.

    Card: a class that represents the individual cards used in the game.

    Cell: a class that represents a single cell on the board. this can either be a hole or a card cell.

    Position: a class that represents coordinated or a specific location on the board.

enums package: contains enums defining the specific constants that are used throughout the game.

    AttackValue: defines the different attack values a card can have.

    CellType: defines the different cell types a board can have.

    Direction: defines the possible directions.

    GameState: represents the different states of the game.

    PlayerColor: represents the colors used to differentiate each player.

view package: contains classes related to the view of the game.

    GridPanel: An interface that defines actions specific to the grid like drawing the board.
    
    HandPanel: an interface that defines actions that are specific to the hand like selecting and deselecting

    TextView: a class that is responsible for displaying the game through text.
    ThreeTriosView: This is an extension of the user interface that provides the main frame of the game.
                    It also provides the organized layout for the TTHandView and TTGridView.
    IThreeTriosView: An interface that defines view functions like refreshing and making the GUI visible.
    TTGridView: This class implements the Grid Panel and it renders the main game board by drawing the cards, cells and holes
                The card cells are yellow and the holes are gray.
    TTHandView: this class implements the Hand panel and it is a representation of the players hand of cards. It has a selection functionality 
                which allows users to select a card if it is their turn.
    CardShape: this is a representation of the card which handles highlighting and drawing the card.
                If a card is clicked it will highlight light gray.

Strategy component: a package containing classes and interfaces for the implementation of the strategy

    LeastLikelyToBeFlipped Strategy: not implemented
    FlipMostCards: this class is a strategy that evaluates potential moves by looking at the maximum number of opponents cards that it can flip.
    PlayerMove: This class represents a move that is made by a player. It specifies the players color, index of card, and grid. 
    Corners: this class evaluates the cards and checks how stable it is to place cards in the corner position.
            It checks if this is the best move and will score the moce and calculate the opponents potential to flip the card.
    AbstractStrategy: the abstract class that has the shared functionality between the different strategis in the game. It has methods to generate valid moves and score them to find the best move.
    Strategy: this is an interface that defines the required methods for any of the strategies in the game. For example generating a list of valid moves, scoring moves, and picking the best one.


test package: a package containing all of the tests of the game.

    model package: a package containing all model tests.
        
        ModelTests: a class containing tests related to the Model class.

        ReaderTests: a class containing tests related to the Reader class.

        RegularPlayerTests: a class containing tests related to the RegularPlayer class.

    board package: a package containing all tests related to the board.
    
        BoardTests: a class containing tests for the Board class.

        CardTests: a class containing tests for the Card class.

        CellTests: a class containing tests for the Cell class.

        PositionTests: a class containing tests for the Position class.

    battle package: a package containing all tests related to the battle.

        SimpleBattleTests: a class containing tests related to the SimpleBattle class.

    view package: a package containing tests related to the view.

        TextViewTests: a class containing tests related to the TextView class.

    Strategy package: A package containing he tests related to the strategies
        
        CornerTest: tests for the corner strategy
        
        FlipMostCardsTest: tests for flipping most card strategy

        PlayerMoveTest: player move test class

Class Invariant: board must have an odd number of card cells. This is an invariant because the constructors and methods all ensure that you cannot have an even number of cells otherwise an exception will be thrown.

Changes for part 2:

    Model: getPlayerScore was added so that we could get the current score of each player. 
           We implemented this method so that we could use it for the strategies and the scoring mechanism.

           we also updated the getBoard method in the model so that we can return a copy of the board instead of the original board itself. This allows the external components and strategies hat interact with the board wont modify the state of the board.
    
            we also added a getCell method to the model. We did this so that we can properly figure out which cells were cardcells and which were holes when rendering the game board.
    
    Board class: 
            in the board class we added a copy method that will add a copy of he board. This is needed for strategies so that we can simulat the move and we also used this functionality when it comes to drawing the game.


    Position class: 
            in the positions class we added an equals and hashCode method. We did this so that we can properly compare between different position object. These methods were needed to properly identify positions on the board.
    Test classes: Condensed Model tests into one class
    

Changes for part 3:

    Model: 
        Added a MachinePlayer that uses the strategies in order to make decisions and play the game. We did this in order to properly separate the human player from the machine player.

        Modified the ThreeTriosModes constructor so that it can accept a battle type. We did this in order to create more flexibility with the battle logic. It also decouples the battle logic from the model and makes it easier to change without modifying the model.
    
        Added a addListener method in the model so that it can update the controller. We did this to create proper communication between he model and the controller. This way they can both communicate with each other and any changes to the state of the game can properly be reflected in the view.

        Added setPlayerTypes method to assign whether the player is a human or a machine. We did this to further support the different types of players in the game and to make it easier to switch player type.

        Updated playTurn so that it will notify the listener after each move so that the view can be updated. We did this in order to make sure that the game state properly changes and so that it is properly reflected in the view.

    Controller:
        Added a ThreeTriosController Interface and a TTController class: Implemented methods to integrate the updated model and to handle game actions. We also used listeners for updated regarding the stame of the game.
        
        Added PlayerAction Interface: we added this to define the player's action inputs which include selecting the cards and grid positions 

        Added ModelStatus Interface: we added this to standardize how the game state and game actions are executed.

    
Playing the game with the JAR: When playing the game you need to specify what type player 1 is, then what type player 2 is. If either player 1 or player 2 is a machine then after specifying machine you must specify the strategy.

        For example: 
            "Human Human"
            "Machine FlipMost Human"
            "Human Machine Corners"
            "Machine FlipMost Machine Corners"