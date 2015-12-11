package com.dlsc.ecosystem;

import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class EcosystemApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		MasterPane masterPane = new MasterPane();

		Undecorator undecorator = new Undecorator(stage, masterPane);
        undecorator.getStylesheets().add("skin/undecorator.css");
        masterPane.getStylesheets().add(EcosystemApp.class.getResource("eco.css").toExternalForm());

        // Optional: Enable this node to drag the stage
        // By default the root argument of Undecorator is set as draggable
        undecorator.setAsStageDraggable(stage, masterPane);

        Scene scene = new Scene(undecorator);

        // Install default Accelerators in the Scene
        undecorator.installAccelerators(scene);
        // Enable fade transition
        undecorator.setFadeInTransition();
  /*
         * Fade transition on window closing request
         */
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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

        stage.setWidth(1000);
        stage.setHeight(800);
        stage.show();

        // Set minimum size
        stage.setMinWidth(undecorator.getMinWidth());
        stage.setMinHeight(undecorator.getMinHeight());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
