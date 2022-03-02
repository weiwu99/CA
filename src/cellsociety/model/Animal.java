package cellsociety.model;

/**
 * backend model for animal, which extends cell
 *
 * @author David Wu
 * @author Gordon Kim
 */

public class Animal extends Cell {

  private int chronon;
  private int energy;
  private boolean processed;

  // 0 = ocean, 1 = fish, 2 = shark
  public Animal(int row, int col, int status, int lives) {
    super(row, col, status);
    chronon = lives;
    energy = 4;
  }

  public int getChronon() {
    return chronon;
  }

  public void setChronon(int numLives) {
    chronon = numLives;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }
}

