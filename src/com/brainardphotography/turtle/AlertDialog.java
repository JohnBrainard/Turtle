package com.brainardphotography.turtle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by johnbrainard on 10/5/14.
 */
public class AlertDialog implements Initializable {
	private final Stage primaryStage;
	private final Stage dialogStage;

	private StringProperty messageProperty = new SimpleStringProperty("Message");

	@FXML
	TextArea titleLabel;

	@FXML
	TextArea messageArea;

	public AlertDialog(Stage primaryStage) {
		this.primaryStage = primaryStage;

		dialogStage = new Stage();
		dialogStage.setResizable(true);
		dialogStage.initOwner(primaryStage);
		dialogStage.centerOnScreen();

		FXMLLoader fxml = new FXMLLoader(getClass().getResource("Alert.fxml"));
		fxml.setController(this);
		Parent root = null;
		try {
			root = fxml.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		dialogStage.setScene(new Scene(root));
	}

	public StringProperty messageProperty() {
		return messageProperty;
	}

	public String getMessage() {
		return messageProperty.getValue();
	}

	public void setMessage(String value) {
		messageProperty.setValue(value);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		messageArea.textProperty().bind(messageProperty);
	}

	public void show() {
		dialogStage.show();
	}

	@FXML
	public void close() {
		dialogStage.close();
	}

	@FXML
	public void showHelp() {

	}
}
