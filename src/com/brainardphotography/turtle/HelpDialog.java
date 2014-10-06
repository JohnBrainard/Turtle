package com.brainardphotography.turtle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by johnbrainard on 10/5/14.
 */
public class HelpDialog implements Initializable {
	private final Stage primaryStage;
	private final Stage dialogStage;

	@FXML
	WebView webView;

	public HelpDialog(Stage primaryStage) {
		this.primaryStage = primaryStage;

		dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.initOwner(primaryStage);
		dialogStage.centerOnScreen();
		dialogStage.setTitle("Help");

		FXMLLoader fxml = new FXMLLoader(getClass().getResource("Help.fxml"));
		fxml.setController(this);
		Parent root = null;
		try {
			root = (Parent)fxml.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		dialogStage.setScene(new Scene(root));
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		WebEngine engine = webView.getEngine();
		engine.load(getClass().getResource("Help.html").toString());
	}

	public void show() {
		dialogStage.show();
	}

	@FXML
	public void close() {
		dialogStage.close();
	}

}
