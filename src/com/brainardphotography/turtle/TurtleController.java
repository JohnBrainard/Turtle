package com.brainardphotography.turtle;

import com.beust.jcommander.internal.Lists;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TurtleController implements Initializable {
	@FXML
	Button runButton;

	@FXML
	Button openButton;

	@FXML
	Button stopButton;

	@FXML
	Label statusLabel;

	@FXML
	AnchorPane canvasContainer;

	@FXML
	Canvas canvas;

	ObjectProperty<File> scriptProperty = new SimpleObjectProperty<File>();
	BooleanProperty runningProperty = new SimpleBooleanProperty();

	AnimationTimer animationTimer;

	private Turtle turtle = new Turtle();
	private TurtleProgram program = null;
	private List<TurtleObject> turtleObjects;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		runButton.disableProperty().bind(BooleanExpression.booleanExpression(runningProperty)
						.or(ObjectExpression.objectExpression(scriptProperty).isNull()));
		stopButton.disableProperty().bind(BooleanExpression.booleanExpression(runningProperty).not());

		turtleObjects = Lists.newArrayList();
		turtle.setObjectConsumer(object -> turtleObjects.add(object));

		draw(canvas.getGraphicsContext2D());
	}

	@FXML
	public void run()
	{
		if (this.program == null)
			return;

		System.out.println("Executing script: " + scriptProperty.getValue());

		if (animationTimer != null)
			animationTimer.stop();

		turtleObjects.clear();
		turtle.reset();

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				draw(canvas.getGraphicsContext2D());
			}
		};
		animationTimer.start();
		runningProperty.setValue(true);

		new Thread(this.program).start();
	}

	@FXML
	public void stop() {
		if (animationTimer != null)
			animationTimer.stop();
		runningProperty.setValue(false);
	}

	@FXML
	public void openFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Groovy Script");
		File file = fileChooser.showOpenDialog(TurtleApplication.getInstance().getStage());

		if (file != null && file.exists())
			this.program = new GroovyTurtleProgram(this.turtle, file);

		scriptProperty.setValue(file);
	}

	@FXML
	public void quit()
	{
		TurtleApplication.getInstance().getStage().close();
	}

	private void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.save();
		gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

		for (TurtleObject object : turtleObjects) {
			object.draw(gc);
		}

		turtle.draw(gc);
		gc.restore();
	}

	private void scriptPropertyChanged(ObservableValue<? extends File> observableValue, File oldValue, File newValue) {
		runButton.setDisable(newValue == null || !newValue.exists());
		statusLabel.setText(newValue == null ? "" : "Running: " + newValue.toString());
	}
}