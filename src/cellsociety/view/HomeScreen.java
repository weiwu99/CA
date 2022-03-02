//package cellsociety.view;
//
//import cellsociety.control.Controller;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.util.List;
//
//public class HomeScreen extends View {
//
//    private final List<EventHandler<ActionEvent>> CHANGE_SIMULATION_DISPLAY_ACTIONS = List.of(
//            e -> showGameOfLife()
//            ,e -> showSpreadingOfFire()
//            ,e -> showSchelling()
//            ,e -> showWaTor()
//            ,e -> showPercolation()
//    );
//    private final List<String> SIMULATION_BUTTON_TITLES = List.of(
//            "Game of Life"
//            ,"Spreading of Fire"
//            ,"Schelling's model of Segregation"
//            ,"Wa-Tor World model of predator-prey relationships"
//            ,"PERCOLATION"
//    );
//    private final String TITLE = "Cell Society Home";
//    private final int BUTTON_WIDTH = 800;
//    private final int BUTTON_HEIGHT = 100;
//    private static final int VERTICAL_SPACING = 20;
//
//
//    /**
//     * creates new homescreen and calls start method
//     * @param c is the controller that must be maintained throughout simulation
//     */
//    public HomeScreen(Controller c){
//        controller = c;
//        stage = new Stage();
//        start(setupDisplay());
//    }
//
//    /**
//     * creates scene for this screen
//     * @return scene with properly setup HomeScreen display
//     */
//    private Scene setupDisplay() {
//        Group root = new Group();
//        Scene scene = new Scene(root, WIDTH, HEIGHT);
//        root.getChildren().addAll(makeControls());
//        return scene;
//    }
//
//    /**
//     * makes the control panel of buttons on homescreen
//     * @return node VBox containing all buttons for different simulations
//     */
//    private Node makeControls() {
//        VBox vertBox = new VBox();
//
//        VBox modelButtons = new VBox();
//        modelButtons.setSpacing(VERTICAL_SPACING);
//
//        for(int i = 0; i < SIMULATION_BUTTON_TITLES.size(); i++){
//            modelButtons.getChildren().add(
//                    makeHomeScreenButton(SIMULATION_BUTTON_TITLES.get(i), CHANGE_SIMULATION_DISPLAY_ACTIONS.get(i))
//            );
//        }
//
//        vertBox.getChildren().add(modelButtons);
//
//        return vertBox;
//    }
//
//    /**
//     * makes new button for HomeScreen display
//     * @param name is the title for the new button
//     * @return new Button with specified name and size
//     */
//    private Button makeHomeScreenButton(String name, EventHandler<ActionEvent> action) {
//        Button button = new Button(name);
//        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//        button.setOnAction(action);
//
//        return button;
//    }
//
//    /**
//     * sets scnee, title, and shows the stage
//     * @param scene must be properly setup to be displayed on stage
//     */
//    public void start(Scene scene) {
//        stage.setScene(scene);
//        stage.setTitle(TITLE);
//        stage.show();
//    }
//    //TODO: add new methods below that mimic showGameOfLife that correspond to other models
//}
