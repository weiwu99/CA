package cellsociety.control;

import java.io.*;
import java.util.*;


/**
 * controls the storage of run info for a particular simFile
 *
 * @author Henry Huynh
 * @author Gordon Kim
 * @author Luke Turkovich
 */

public class RunInfoController {

  private final ErrorHandler errorHandler = new ErrorHandler();
  private final String BASE_PATH = "data/";
  private final String FILE_NOT_FOUND = "File not Found";
  private final String ERROR_SAVING = "Error saving to files";
  private final String REQUIRED_KEY = "Missing required key(s)";


  private static final List<SimKey> REQUIRED_KEYS = List.of(
      SimKey.Type,
      SimKey.Title,
      SimKey.Author,
      SimKey.Description,
      SimKey.InitialStates
  );

  private Properties simSettings;
  Map<SimKey, String> runInfoMap;

  /**
   * instantiates new RunInfoController, sets runInfoMap equal to new HashMap
   */
  public RunInfoController() {
    runInfoMap = new HashMap<>();
  }

  /**
   * set new info value at particular SimKey
   *
   * @param key  the key to update
   * @param info the value at that key
   */
  public void set(SimKey key, String info) {
    runInfoMap.put(key, info);
  }

  /**
   * set simSettings equal to what is passed by file
   *
   * @param file a Sim file to update settings with
   */
  public void setSimSettings(File file) {
    simSettings = readSim(file);
    for (SimKey key : REQUIRED_KEYS) {
      runInfoMap.put(key, simSettings.get(key.toString()).toString());
    }
    for (SimKey key : REQUIRED_KEYS) {
      if (!runInfoMap.containsKey(key)) {

      }
    }
  }

  /**
   * return the file referenced by InitialStates
   *
   * @return CSV file stored in Properties file
   */
  public File getCSVFile() {
    return new File(BASE_PATH + runInfoMap.get(SimKey.InitialStates));
  }

  /**
   * get info stored at key
   *
   * @param key what piece of data we want to return
   * @return the value at key
   */
  public String getInfo(SimKey key) {
    return runInfoMap.get(key);
  }

  /**
   * reads data from .sim file to Properties
   *
   * @param filename is the sim file we want to read
   * @return properly initialized Properties file containing all that is in filename
   */
  private Properties readSim(File filename) {
    Properties sim = new Properties();
    InputStream inputStream = null;

    try {
      inputStream = new FileInputStream(filename);
    } catch (FileNotFoundException e) {
      errorHandler.handleError(FILE_NOT_FOUND);
    }
    try {
      sim.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sim;
  }

  /**
   * saveRunInfo handles the bulk of the process of saving the run information into a sim and a
   * corresponding csv file
   *
   * @param simulation Takes in the simulation type
   * @param statusGrid Takes in the grid, including the status information which should be saved
   */
  public void saveRunInfo(Simulation simulation, int[][] statusGrid) {
    try {
      Settings mySettings = new Settings();
      EnumMap<SimKey, String> simMap = new EnumMap<>(SimKey.class);
      simMap.putAll(runInfoMap);
      if (simMap.size() == 3) { // meaning it is a default initialized scenario
        simMap.put(SimKey.Type, simulation.toString());
      }
      List<SimKey> keyList = new ArrayList<>(simMap.keySet());
      List<String> valueList = new ArrayList<>(simMap.values());
      StringBuilder simInfo = new StringBuilder();
      for (int i = 0; i < keyList.size(); i++) {
        simInfo.append(keyList.get(i)).append("=").append(valueList.get(i)).append("\n");
      }
      String title = simMap.get(SimKey.Title).replace(" ", "_");
      File csvFile = mySettings.createFile(simulation, title, ".csv");
      simMap.put(SimKey.InitialStates, csvFile.getPath());
      int startingIndex = csvFile.getPath().indexOf("/") + 1; // to avoid writing "data" in sim
      int endingIndex = simInfo.indexOf(
          SimKey.InitialStates.toString()); // to overwrite existing path if user is making a new file from
      // ... an uploaded config
      if (simInfo.toString().contains(SimKey.InitialStates.toString())) {
        simInfo = new StringBuilder(simInfo.substring(0, endingIndex));
      }
      simInfo.append(SimKey.InitialStates.toString()).append("=/")
          .append(csvFile.getPath().substring(startingIndex)).append("\n");
      mySettings.writeDataToCSV(csvFile, statusGrid);
      File simFile = mySettings.createFile(simulation, title, ".sim");
      mySettings.writeDataToSim(simFile, simInfo.toString());
    } catch (Exception e) {
      errorHandler.handleError(ERROR_SAVING);
    }
  }
}