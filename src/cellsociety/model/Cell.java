package cellsociety.model;

/**
 * backend model for a cell
 *
 * @author David Wu
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public class Cell {

  private int myStatus;
  private int futureStatus;
  private int myRow;
  private int myCol;

  public Cell(int row, int col, int status) {
    myStatus = status;
    myRow = row;
    myCol = col;
  }

  public int getMyStatus() {
    return myStatus;
  }

  public void setMyStatus(int myStatus) {
    this.myStatus = myStatus;
  }

  public int getRow() {
    return myRow;
  }

  public int getCol() {
    return myCol;
  }

  public int getFutureStatus() {
    return futureStatus;
  }

  public void setFutureStatus(int futureStatus) {
    this.futureStatus = futureStatus;
  }
}
