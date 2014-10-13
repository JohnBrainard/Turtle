package com.brainardphotography.turtle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class TurtleApplication extends Application {
	private static TurtleApplication instance = null;

	private static final String PREF_LAST_SCRIPT_PATH = "LastScriptPath";

	private Stage stage;
	private Preferences preferences;
	private ExecutorService executorService;
	private ApplicationConfiguration configuration;

	public static TurtleApplication getInstance() {
		return instance;
	}

	public Stage getStage() {
		return stage;
	}

	@Override
    public void start(Stage primaryStage) throws Exception {
		Platform.setImplicitExit(false);

		if (instance != null)
			throw new IllegalStateException("TurtleApplication can only have one instance");

		preferences = Preferences.userNodeForPackage(TurtleApplication.class);
		executorService = Executors.newSingleThreadExecutor();
		configuration = new ApplicationConfiguration();

		instance = this;
		this.stage = primaryStage;

		configuration.loadConfiguration(executorService, () -> {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("Turtle.fxml"));
				primaryStage.setTitle("Turtle!");
				primaryStage.setScene(new Scene(root));
				primaryStage.setResizable(false);
				primaryStage.setOnCloseRequest(e -> exit());
				primaryStage.show();
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
			}
		});
	}

	public void showErrorMessage(Exception exception) {
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));

		ErrorMessageDialog messageDialog = new ErrorMessageDialog(stage);
		messageDialog.setMessage(exception.getMessage() + "\n" + writer.toString());
		messageDialog.show();
	}

	public ExecutorService getExecutorService() {
		return this.executorService;
	}

	public ApplicationConfiguration getConfiguration() {
		return configuration;
	}

	public String getLastScriptPath() {
		return preferences.get(PREF_LAST_SCRIPT_PATH, null);
	}

	public void setLastScriptPath(String value) {
		System.out.println("Setting last script path: " + value);
		preferences.put(PREF_LAST_SCRIPT_PATH, value);
	}

	public void showErrorMessageSafe(Exception exception) {
		Platform.runLater(() -> showErrorMessage(exception));
	}

	public void close() {
		exit();
	}

	private void exit() {
		executorService.shutdown();
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		stage.close();
		Platform.exit();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
