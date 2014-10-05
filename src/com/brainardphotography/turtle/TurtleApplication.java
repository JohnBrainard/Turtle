package com.brainardphotography.turtle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TurtleApplication extends Application {
	private static TurtleApplication instance = null;

	private Stage stage;

	public static TurtleApplication getInstance() {
		return instance;
	}

	public Stage getStage() {
		return stage;
	}

	@Override
    public void start(Stage primaryStage) throws Exception{
		if (instance != null)
			throw new IllegalStateException("TurtleApplication can only have one instance");

		instance = this;
		this.stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("Turtle.fxml"));
        primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
        primaryStage.show();
	}


    public static void main(String[] args) {
        launch(args);
    }
}
