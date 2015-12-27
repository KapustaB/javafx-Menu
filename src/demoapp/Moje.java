/**
 * Demo purpose In-SideFX (Un)decorator for JavaFX stage License: You can use
 * this code for any kind of purpose, commercial or not.
 */
package demoapp;

import insidefx.undecorator.Undecorator;
import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author in-sideFX
 */
public class Moje extends Application {

    Stage primaryStage;
    /*@FXML
    private AreaChart areaChart;
    @FXML
    private PieChart pieChart;*/

    @Override
    @SuppressWarnings("CallToThreadDumpStack")
    public void start(final Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Undecorator Stage Demo");
        // The UI (Client Area) to display
        Region root = null;
        try {
        		////izlouda se scena po zelji
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mojj.fxml"));
            fxmlLoader.setController(this);
            root = (Region) fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        	////u undercorator se posalje root(sa izloudanom zeljenom scenom) i default scena koji se crta
        	////undecorator animacije,tipkice,izgled stage-a -> sagedecoration.fxml u paketu insidefx.undecorator
		final Undecorator undecorator = new Undecorator(stage, root);
		
		//---> primjer kao napraviti drukciji stage sa animacijama
//		Undecorator nesto = new Undecorator(stage, "put.fxml", root);
		
		
        // Customize it by CSS if needed:
        undecorator.getStylesheets().add("skin/undecorator.css");

        // Optional: Enable this node to drag the stage
        // By default the root argument of Undecorator is set as draggable
//        Node node = root.lookup("#draggableNode");
//        undecorator.setAsStageDraggable(stage, node);

        Scene scene = new Scene(undecorator);

        // Install default Accelerators in the Scene
        undecorator.installAccelerators(scene);
        // Enable fade transition
        undecorator.setFadeInTransition();
  /*
         * Fade transition on window closing request
         */
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                we.consume();   // Do not hide
                undecorator.setFadeOutTransition();
            }
        });
        
        // Transparent scene and stage
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
       
        stage.show();

        // Set minimum size
        stage.setMinWidth(undecorator.getMinWidth());
        stage.setMinHeight(undecorator.getMinHeight());

    }

    public void handleUtilityAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
