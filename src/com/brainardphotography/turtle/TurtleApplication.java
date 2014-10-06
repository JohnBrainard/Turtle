package com.brainardphotography.turtle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        primaryStage.setTitle("Turtle!");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
        primaryStage.show();
	}

	public void showErrorMessage(Exception exception) {
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));

		ErrorMessageDialog messageDialog = new ErrorMessageDialog(stage);
		messageDialog.setMessage(exception.getMessage() + "\n" + writer.toString());
		messageDialog.show();
	}

	public void showErrorMessageSafe(Exception exception) {
		Platform.runLater(() -> showErrorMessage(exception));
	}

    public static void main(String[] args) {
        launch(args);
    }
}
