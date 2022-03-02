# Cell Society Design Plan

### Team Number 7

### Henry Huynh, Gordon Kim, Luke Turkovich, David Wu

## Design Overview

### Model

* A cell object that stores the row, column, and status of a cell in the grid
    * Might serve as a super class in case other applications have different features required
      within the cell
    * Store the status for next round so that all cells can be udpated altogether
* Grid: Currently just a 2-D array matrix of cell objects
    * Might need to have a separate Grid object to adapt to different Grid systems.
        * How to deal with hexagons?
* GameOfLifeModel: Backend model for game of life CA
    * Need to intialize all cells with a default values
    * Take actions on each rule based on the rule
        * Need to check on each cell's neighbors
    * Update all cells altogether for each step after all cells are processed

### Controller

* Controller can make the model’s info immutable
* Serves as the path between model and the cellsociety.view
* Handles exceptions thrown by the model
* As the project becomes more complicated, the controller will manipulate data from the model and
  pass to the view
    * Step function in the controller updates appropriate values for each time step
* Instance data
    * Model
    * Display
    * Grid of Cells [][]
* Functions:
    * GetGrid
        * Sets grid equal to updated grid from model
    * Any new function that will take and manipulate data from the backend to pass to the front end

### View

* GridView extends GridPane - One grid contains one CellView
* UI - Creates UI window
* CellView extends Node - Contains color and cretes cell shape
* GameofLifeView
    * Initializes GridView and UI.
    * Calls controller to get model data

## Design Details

### Model

Grid: 2D array of cells

Cell objects

Class cell

bacterium (boolean/2 val

fire cell (3 val)

In the backend model, do this:

```java
public Cell() {
    myStatus = 0;
}
public Cell(int row, int col, int status) {
    myStatus = status;
    myRow = row;
    myCol = col;
}
public int getFutureStatus() {}
public void setFutureStatus(int futureStatus){}
```

```java
public GameOfLife(row, col) {  
    myGrid = new Cell[row][col];
    initializeGrid();
}
private void initializeGrid() {}
public void step() {} // ideas from Darwin
private List<Cell> getNeighbors(Cell target) {}
private void executeRules(Cell currentCell) {}
```

In the future, should make a Grid object to adapt to non-matrix grid system (hexagon?)

## Design Considerations

#### Design Issue #1: How to pass data from model to display

* Alternative #1: Use a controller to port all data through. Write methods that manipulate data from
  model as intended

* Alternative #2: Allow display to have an instance of the model and pull directly from there (model
  is less dynamic/subject to change from updates than display, so wouldn’t do it in the opposite
  direction)

* Trade-offs: Having a controller could possibly make the code less readable since there is an
  additional layer between the display and the data is passed through an additional class, but it is
  still better to design the code this way to allow for a single responsibility for each class and
  let the view simply display, backend hold data, and have the controller manipulate it and pass it
  between the two

#### Design Issue #2: How to follow the open-closed principle (allow the grid’s behavior to be extended without modifying it). Specifically, consider a case where the grid needs to be instead a hexagon with triangles for cells

* Alternative #1: We can already make it work for different shapes. In the case that we want a
  triangle for example, we would have basically half the rectangular cell be zero.


* Alternative #2: The first alternative feels like a workaround. We should instead abstract the grid
  more


* Trade-offs: The first alternative feels like a workaround, but at the same time it is easier to
  implement. The second alternative is indeed more thorough and allows for modifications to be made
  without altering the existing source code

## User Interface

* View and edit the simulation's descriptive information
* its type, name, author, description, state colors, (default or set for this run) and parameter
  values
* animate a simulation from its initial state indefinitely
* pause, step, resume, stop the simulation
* speed up or slow down the simulation's animation rate
* change a cell's state by clicking on it
* Choose a color to represent all cells of the same state
* load a new configuration file, which stops the current simulation and starts the new one
* Note, the app's size should not change based on the size of the grid to be displayed
* save the current state of the simulation to both properties and CSV files that share one name
  entered by the user (with different extensions: .sim and .csv)
* run multiple simulations at the same time in such a way that they can be seen side by side (
  perhaps to compare results, so you should not use tabs like a browser)
* Any colors, fonts, or other appearance styling should be able to be changed dynamically between at
  least three different options (such as Dark or Light modes, Duke or UNC colors, larger fonts for
  presentation mode, etc.). Any text displayed should be able to appear in at least two other
  languages (you can use Google Translate if no one on your team can do it).
* Can just have 3 presets in css style

## Team Responsibilities

* Gordon Kim: cellsociety.view

* David Wu: backend model

* Henry Hyunh: general

* Luke Turkovich: Controller

#### Proposed Schedule

1. Game of life model (10/15)
2. Game of life displaying grid cellsociety.view (10/16)
3. Refactor view: rename timeline to animation "static" base class members should not be accessed
   via derived types (Henry by Oct. 16)

