package model;

/**
 * Static class used to check valid/invalid parameters.
 */
public class Ensure {

  /**
   * Checks if the given index is within bounds.
   * @param givenNum the number that is being checked
   * @param lowerBound the lowest value the number can be
   * @param upperBound the highest value the number can be
   * @return an int that is within the valid bounds
   * @throws IllegalArgumentException if the given number is not within the bounds
   */
  public static int checkIdxBounds(int givenNum, int lowerBound, int upperBound) {
    if (givenNum < lowerBound || givenNum > upperBound) {
      throw new IllegalArgumentException("Given idx " + givenNum + " is out of bounds");
    }
    else {
      return givenNum;
    }
  }

  /**
   * Checks if the given value is within the bounds and returns a boolean.
   * @param givenNum then number that is being checked
   * @param lowerBound the lowest value a number can be
   * @param upperBound the highest value a number can be
   * @return if the value is within the bounds
   */
  public static boolean checkBounds(int givenNum, int lowerBound, int upperBound) {
    return (givenNum >= lowerBound && givenNum < upperBound);
  }

  /**
   * Makes sure the game is started, used before every method in ThreeTriosModel.
   * @param gameStarted true if game is started, false if not
   * @throws IllegalArgumentException if the game hasn't started yet
   */
  public static void checkGameStarted(boolean gameStarted) {
    if (!gameStarted) {
      throw new IllegalArgumentException("Game hasn't started!");
    }
  }

  

}
