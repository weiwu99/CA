package cellsociety.control;

import cellsociety.model.*;
import cellsociety.model.simulations.*;
import cellsociety.view.View;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

/**
 * sits between view and model, ports all information between each end, holds important simulation
 * data
 *
 * @author Luke Turkovich
 * @author Gordon Kim
 * @author Henry Huynh
 */

public class Controller {

  public final int DEFAULT_SIZE = 10;
  private static final String NO_FILE_MESSAGE = "No file found or selected";
  private static final String WRONG_FILE_MESSAGE = "Wrong file selected, must be CSV";
  private static final String FAILED_SAVING_TO_FILE = "Failed saving to file";
  private static final Simulation DEFAULT_SIMULATION = Simulation.GAMEOFLIFE;
  private static final CellShape DEFAULT_CELL_SHAPE = CellShape.RECTANGLE;
  private static final EdgePolicy DEFAULT_EDGE_POLICY = EdgePolicy.FINITE;
  private static final NeighborPolicy DEFAULT_NEIGHBOR_POLICY = NeighborPolicy.COMPLETE;
  private CAModel model;
  private Simulation simulation;
  private View view;
  private Settings mySettings;
  private RunInfoController runInfo;
  private ErrorHandler errorHandler;
  //setup for arrangement
  //sim file

  /**
   * creates new instances of GameOfLifeView, GameOfLifeModel, and properly initializes stage
   *
   * @param row number of rows in the game of life model
   * @param col number of columns in the game of life model
   */
  public Controller(int row, int col) {
    runInfo = new RunInfoController();
    errorHandler = new ErrorHandler();
    simulation = DEFAULT_SIMULATION;
    model = new GameOfLifeModel(row, col, DEFAULT_CELL_SHAPE, DEFAULT_NEIGHBOR_POLICY,
        DEFAULT_EDGE_POLICY);
    view = new View(this);
    mySettings = new Settings();
  }

  /**
   * called from view, moves model through next time step
   */
  public void step() {
    model.step();
    view.step();
  }

  /**
   * initializes randomGrid in model and updates grid status in view
   */
  public void initializeRandomGrid() {
    pause();
    int min = 5;
    int max = 50;
    int rows = (int) Math.floor(Math.random() * (max - min + 1) + min);
    int cols = (int) Math.floor(Math.random() * (max - min + 1) + min);
    ;
    int[][] statusGrid = new int[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        statusGrid[i][j] = (int) (Math.random() * model.getNumStates());
      }
    }
    model.updateCellGrid(statusGrid);
    view.updateGridStatus();
  }

  /**
   * updates model's neighborPolicy based on selection in view
   *
   * @param neighborPolicy is the new neighborPolicy to use in model
   */
  public void updateNeighbors(NeighborPolicy neighborPolicy) {
    model.updateNeighborPolicy(neighborPolicy);
  }

  /**
   * updates model's edgePolicy based on selection in view
   *
   * @param edgePolicy is the new edgePolicy to use in model
   */
  public void updateEdgePolicy(EdgePolicy edgePolicy) {
    model.updateEdgePolicy(edgePolicy);
  }

  /**
   * updates model's cellShape based on selection in view
   *
   * @param cellShape the new cellShape to use in model
   */
  public void updateCellShape(CellShape cellShape) {
    model.updateCellShape(cellShape);
    view.updateCellShape();
  }

  /**
   * changes simulation, updates view's grid status, and updates all policies/shape
   *
   * @param simulation is the new type of simulation to run
   */
  public void updateModel(Simulation simulation) {
    pause();
    reset();
    this.simulation = simulation;
    int row = model.getNumRows();
    int col = model.getNumRows();
    CellShape cellShape = model.getCellShape();
    NeighborPolicy neighborPolicy = model.getNeighborPolicy();
    EdgePolicy edgePolicy = model.getEdgePolicy();
    switch (simulation) {
      case GAMEOFLIFE -> model = new GameOfLifeModel(row, col, cellShape, neighborPolicy,
          edgePolicy);
      case SPREADOFFIRE -> model = new SpreadOfFireModel(row, col, cellShape, neighborPolicy,
          edgePolicy);
      case SEGREGATION -> model = new SegregationModel(row, col, cellShape, neighborPolicy,
          edgePolicy);
      case WATOR -> model = new WaTorModel(row, col, cellShape, neighborPolicy, edgePolicy);
      case PERCOLATION -> model = new PercolationModel(row, col, cellShape, neighborPolicy,
          edgePolicy);
    }
    view.updateGridStatus();
  }

  /**
   * resets view and model to default settings
   */
  public void reset() {
    model.reset();
    view.reset();
  }

  /**
   * get specific piece of runInfo data
   *
   * @param key is the key we want to obtain information for
   * @return string held at value of key in runInfo
   */
  public String getRunInfo(SimKey key) {
    return runInfo.getInfo(key);
  }

  /**
   * set a particular value at some key in runInfo
   *
   * @param key  the key we want to update
   * @param info the new value to store at specified key
   */
  public void setRunInfo(SimKey key, String info) {
    this.runInfo.set(key, info);
  }

  /**
   * uploads data from a .csv file into CSV container object
   *
   * @throws FileNotFoundException if selected file is not a .csv or is null
   */
  public void uploadConfiguration() {
    pause();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select sim File to Run");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("sim Files", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      runInfo.setSimSettings(selectedFile);
      initializeGridFromFile(runInfo.getCSVFile());
    } // need an error msg for choosing non .csv but im not sure if user is able to do this anyway
    else {
      errorHandler.handleError(NO_FILE_MESSAGE);
    }
  }

  /**
   * moves data from CSVContainer to model, controller, and display
   *
   * @param selectedFile holds row, column, and a statusGrid from input CSV
   */
  public void initializeGridFromFile(File selectedFile) {
    CSVContainer container = mySettings.readAllDataCSV(selectedFile);
    int[][] statusGrid = container.getCSVStatus();

    model.updateCellGrid(statusGrid);
    view.updateRunInfo();
    view.updateGridStatus();
  }


  /**
   * save all runInfo into a file
   */
  public void saveRunInfo() {
    runInfo.saveRunInfo(simulation, getStatusGrid());
        /*try {
            runInfo.saveRunInfo(simulation, getStatusGrid());
        } catch (IOException e) {
           errorHandler.handleError(FAILED_SAVING_TO_FILE);
        }*/
  }

  /**
   * pause the simulation in view
   */
  public void pause() {
    view.pause();
  }

  /**
   * play the simulation in view
   */
  public void play() {
    view.play();
  }

  /**
   * change the speed the animation operates at
   *
   * @param rate the speed at which the simulation updates
   */
  public void setAnimationRate(double rate) {
    view.setAnimationRate(rate);
  }

  /**
   * changes theme in view
   *
   * @param theme is the name of the new theme to move to
   */
  public void changeTheme(String theme) {
    view.changeTheme(theme);
  }

  /**
   * increases cell status at row, col, by 1
   *
   * @param row is the row value of the cell to increment
   * @param col is the column value of the cell to increment
   */
  public void incrementCellStatus(int row, int col) {
    model.incrementCellStatus(row, col);
    view.updateGridStatus();
  }

  /**
   * get a grid with statuses of every cell in the simulation
   *
   * @return integer array representing the state of every cell
   */
  public int[][] getStatusGrid() {
    Cell[][] cells = model.getCellGrid();
    int[][] statusGrid = new int[model.getNumRows()][model.getNumCols()];
    for (int i = 0; i < model.getNumRows(); i++) {
      for (int j = 0; j < model.getNumCols(); j++) {
        statusGrid[i][j] = cells[i][j].getMyStatus();
      }
    }
    return statusGrid;
  }

  public void changeLanguage(String language) {
    view.changeLanguage(language);
  }

  public CellShape getCellShape() {
    return model.getCellShape();
  }

  public CAModel getModel() {
    return model;
  }

  public View getView() {
    return view;
  }
}
