package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Animal;
import cellsociety.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * backend model for WaTor CA simulation
 *
 * @author David Wu
 * @author Gordon Kim
 * @author Henry Huynh
 */

public class WaTorModel extends CAModel {

  private static final int NUM_STATES = 3;

  // 0 == ocean, 1 == fish, 2 == shark
  private final int OCEAN = 0;
  private final int FISH = 1;
  private final int SHARK = 2;

  private final int FISH_REPRODUCTION_RATE = 3;
  private final int SHARK_REPRODUCTION_RATE = 15;
  private final int INITIAL_SHARK_ENERGY = 4;
  private final int SHARK_ENERGY_GAIN = 2;
  private final int SHARK_ENERGY_LOSS = 1;

  /**
   * Constructor of the GameOfLife model
   *
   * @param row row number for myGrid
   * @param col column number for myGrid
   */
  public WaTorModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    super(row, col, cellShape, neighborPolicy, edgePolicy);
  }

  @Override
  public void step() {
    updateCellStatus();
  }

  @Override
  public int getNumStates() {
    return NUM_STATES;
  }

  @Override
  Cell[][] initializeDefaultGrid(int row, int col) {
    Cell[][] cellGrid = new Cell[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        int status = Math.random() < 0.1 ? 0 : (Math.random() < 0.9 ? 1 : 2);
        cellGrid[r][c] = new Animal(r, c, status, 0);
      }
    }
    return cellGrid;
  }

  /**
   * Update the status for each cell next round
   */
  @Override
  public void updateCellStatus() {
    updateIfProcessed();
    updateAnimalStatus(SHARK);
    updateAnimalStatus(FISH);
  }

  private void updateAnimalStatus(int speciesType) {
    for (Cell animal : cellNeighborMap.keySet()) {
      Animal species = (Animal) animal;
      if (species.getMyStatus() == speciesType && !species.isProcessed()) {
        executeRules(species);
      }
    }
  }

  private void updateIfProcessed() {
    for (Cell cell : cellNeighborMap.keySet()) {
      Animal animal = (Animal) cell;
      animal.setProcessed(false);
    }
  }

  /**
   * Check and execute the rules for the game
   *
   * @param currentCell the cell we are examining at the moment
   */
  @Override
  void executeRules(Cell currentCell) {
    Animal animal = (Animal) currentCell;
    List<Cell> emptyNeighbors = new ArrayList<>();
    List<Cell> fishNeighbors = new ArrayList<>();

    for (Cell neighbor : cellNeighborMap.get(currentCell)) {
      if (neighbor.getMyStatus() == OCEAN) {
        emptyNeighbors.add(neighbor);
      } else if (neighbor.getMyStatus() == FISH) {
        fishNeighbors.add(neighbor);
      }
    }

    animal.setChronon(animal.getChronon() + 1);
    switch (animal.getMyStatus()) {
      case FISH -> executeFishRule(animal, emptyNeighbors);
      case SHARK -> executeSharkRule(animal, emptyNeighbors, fishNeighbors);
    }
  }

  public Cell getRandomElement(List<Cell> list) {
    return list.get((int) (Math.random() * list.size()));
  }

  private void executeFishRule(Animal fish, List<Cell> emptyNeighbors) {
    if (!emptyNeighbors.isEmpty()) { // move randomly

      Animal random = (Animal) getRandomElement(emptyNeighbors);
      random.setMyStatus(FISH);
      random.setProcessed(true);

      if (fish.getChronon() % FISH_REPRODUCTION_RATE != 0) {
        fish.setMyStatus(OCEAN);
      } else {
        fish.setProcessed(true);
      }
    }
  }

  private void executeSharkRule(Animal shark, List<Cell> emptyNeighbors, List<Cell> fishNeighbors) {
    shark.setEnergy(shark.getEnergy() - SHARK_ENERGY_LOSS);

    if (shark.getEnergy() == 0) {
      shark.setMyStatus(OCEAN);
      return;
    }

    if (!fishNeighbors.isEmpty()) { // eat fish
      Animal fish = (Animal) getRandomElement(fishNeighbors);
      fish.setMyStatus(SHARK);
      fish.setEnergy(shark.getEnergy() + SHARK_ENERGY_GAIN);
      fish.setProcessed(true);

      if (shark.getChronon() % SHARK_REPRODUCTION_RATE != 0) {
        shark.setEnergy(INITIAL_SHARK_ENERGY);
        shark.setMyStatus(OCEAN);
      } else {
        shark.setProcessed(true);
      }
    } else if (!emptyNeighbors.isEmpty()) { //if no fish but move
      Animal ocean = (Animal) getRandomElement(emptyNeighbors);
      ocean.setMyStatus(SHARK);
      ocean.setEnergy(shark.getEnergy());
      ocean.setProcessed(true);

      if (shark.getChronon() % 10 != 0) {
        shark.setEnergy(INITIAL_SHARK_ENERGY);
        shark.setMyStatus(OCEAN);
      } else {
        shark.setProcessed(true);
      }
    }
  }
}
