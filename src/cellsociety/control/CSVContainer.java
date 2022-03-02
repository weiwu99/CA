package cellsociety.control;

/**
 * handles a CSV file input
 *
 * @author Luke Turkovich
 */

public class CSVContainer {

  private int[][] CSVStatus;

  /**
   * Constructor of a container that stores all the information from the .csv file including the row
   * number, column number, and the starting status of each cell in the grid. Credit:
   * https://www.baeldung.com/java-method-return-multiple-values
   */
  public CSVContainer(int row, int col, int[][] statusMatrix) {
    CSVStatus = statusMatrix;
  }

  /**
   * returns status array stored in CSV file
   *
   * @return int array of statuses as specified by CSV file
   */
  public int[][] getCSVStatus() {
    return CSVStatus;
  }
}
