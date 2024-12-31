package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.ThreeTriosModelInterface;

/**
 * Represents a class holding static methods initGrid() and initDeck(), which initializes the
 * appropriate data based on the given String path to the configuration file into the given
 * model.
 */
public class ConfigureGame {

  /**
   * Initializes a grid based on a configuration file.
   * @param gridPath represents the given file
   */
  public static void initGrid(String gridPath, ThreeTriosModelInterface model) {
    int numRows = 0;
    int numCols = 0;
    List<String> gridConfig = new ArrayList<>();
    File gridFile = new File(gridPath);

    try {
      FileReader fr = new FileReader(gridFile);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        gridConfig.add(line);
      }
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("Cannot find the file on this path: " + gridPath);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Error reading line");
    }
    try {
      numRows = Integer.parseInt(gridConfig.get(0).substring(0, 1));
      numCols = Integer.parseInt(gridConfig.remove(0).substring(2, 3));
    } catch (IndexOutOfBoundsException | NumberFormatException ex) {
      throw new IllegalArgumentException("Invalid grid row/col");
    }
    try {
      model.createGrid(numRows, numCols, gridConfig);

    } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException("Cannot create grid: " + ex.getMessage());
    }
    if (model.countFreeSpace() % 2 == 0) {
      throw new IllegalArgumentException("Invalid Grid: Must not have even number of empty space");
    }
  }

  /**
   * Initializes a deck based on a configuration file.
   * @param cardPath represents the given file
   */
  public static void initDeck(String cardPath, ThreeTriosModelInterface model) {
    List<String> cardConfig = new ArrayList<String>();
    File cardFile = new File(cardPath);
    try {
      FileReader fr = new FileReader(cardFile);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        cardConfig.add(line);
      }
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("Cannot find the file on this path: " + cardPath);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Error reading line");
    }

    model.createDeck(cardConfig);
  }
}
