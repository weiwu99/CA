package cellsociety.control;
// A class to deal with writing/reading to/from CSV,SIM,PROPERTIES files
// Also saves to new file

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Properties;

/**
 * holds settings specifications from a sim file
 *
 * @author Henry Huynh
 * @author Gordon Kim
 * @author Luke Turkovich
 */

public class Settings {

  public static final String PROPERTY_BACKGROUND = "background";
  private static final String CSV_FILE_NAME = "" + ".csv";
  private static final String ERROR_CSV = "Error reading or writing to CSV";
  private static final String FILE_ALREADY_EXISTS = "File of same name already exists";
  private static final String ERROR_CREATING_FILE = "Error creating file";
  private static final String INCORRECT_EXTENSION = "Incorrect extension";
  private static final String ERROR_SIM = "Error reading or writing to SIM";
  private static final String FILE_NOT_FOUND = "File not found";
  private final Properties properties;
  private final ErrorHandler errorHandler;

  public Settings() {
    errorHandler = new ErrorHandler();
    properties = new Properties();
  }


  /**
   * reads data from CSV file to CSV container
   *
   * @param file is the file containing a configuration
   * @return properly initialized CSV file
   */
  public CSVContainer readAllDataCSV(File file) {
    try {

      BufferedReader reader = new BufferedReader(new FileReader(file));
      CSVReader csvReader = new CSVReaderBuilder(reader)
          .build();
      List<String[]> allData = csvReader.readAll();

      int numOfRows = Integer.parseInt(allData.get(0)[1].trim());
      int numOfCols = Integer.parseInt(allData.get(0)[0].trim());

      int[][] statusGrid = new int[numOfRows][numOfCols];
      for (int r = 1; r < allData.size(); r++) {
        for (int c = 0; c < allData.get(1).length; c++) {
          statusGrid[r - 1][c] = Integer.parseInt(allData.get(r)[c].trim());
        }
      }
      System.out.println("Number of rows: " + numOfRows);
      System.out.println("Number of cols: " + numOfCols);
      for (int r = 0; r < statusGrid.length; r++) {
        for (int c = 0; c < statusGrid[0].length; c++) {
          System.out.print(statusGrid[r][c] + "\t"); // means insert a tab
        }
        System.out.println();
      }
      CSVContainer CSVInformation = new CSVContainer(numOfRows, numOfCols, statusGrid);
      return CSVInformation;
    } catch (Exception e) {
      errorHandler.handleError(ERROR_CSV);
      return null;
    }
  }


  /**
   * This method is able to create both a CSV and Sim file and is called twice when save info is
   * called
   *
   * @param simulation Specific simulation type to save
   * @param title      Specific title to save
   * @param extension  Extension type, either .csv or .sim
   * @return
   */
  public File createFile(Simulation simulation, String title, String extension) {
    try {
      String simulationPath = "";
      switch (simulation) {
        case GAMEOFLIFE -> simulationPath = "game_of_life";
        case SPREADOFFIRE -> simulationPath = "spread_of_fire";
        case WATOR -> simulationPath = "wator";
        case SEGREGATION -> simulationPath = "segregation";
        case PERCOLATION -> simulationPath = "percolation";
      }
      //ex: data/game_of_life/penta_decathlon.sim
      File file = new File("data" + "/" + simulationPath + "/" + title + extension);
      boolean result;
      result = file.createNewFile();
      if (result) {    // test if successfully created a new file
        System.out.println("file created " + file.getCanonicalPath()); //returns the path string
      } else {
        System.out.println("File already exist at location: " + file.getCanonicalPath());
      }
      return file;
    } catch (IOException e) {
      errorHandler.handleError(ERROR_CREATING_FILE);
      return null;
    }
  }

  /**
   * Method writes all the important information to the Sim File that was recently created to save
   * the simulation info
   *
   * @param filename Name of the file to write to
   * @param info
   */
  public void writeDataToSim(File filename, String info) {
    try (FileWriter simWriter = new FileWriter(filename)) {
      simWriter.append(info);
      simWriter.flush();
    } catch (FileNotFoundException e) {
      errorHandler.handleError(FILE_NOT_FOUND);
    } catch (FileAlreadyExistsException e) {
      errorHandler.handleError(FILE_ALREADY_EXISTS);
    } catch (IOException e) {
      errorHandler.handleError(ERROR_SIM);
    }
  }


  /**
   * Method writes data to CSV files
   *
   * @param file The CSV file that is to be written to
   * @param grid Takes in the grid of the simulation that is to be recorded/saved
   */
  public void writeDataToCSV(File file, int[][] grid) {
    try (FileWriter csvWriter = new FileWriter(file)) {
      int numOfRows = grid.length;
      int numOfCols = grid[0].length;
      csvWriter.append(String.valueOf(numOfCols)).append(",").append(String.valueOf(numOfRows))
          .append("\n");
      for (int i = 0; i < numOfRows; i++) {
        for (int j = 0; j < numOfCols; j++) {
          csvWriter.append(String.valueOf(grid[i][j]));
          if (j == grid[0].length - 1) {
            csvWriter.append("\n");
          } else {
            csvWriter.append(",");
          }
        }
      }
      csvWriter.flush();
    } catch (FileNotFoundException e) {
      errorHandler.handleError(FILE_NOT_FOUND);
    } catch (FileAlreadyExistsException e) {
      errorHandler.handleError(FILE_ALREADY_EXISTS);
    } catch (IOException e) {
      errorHandler.handleError(ERROR_CSV);
    }
  }
}
