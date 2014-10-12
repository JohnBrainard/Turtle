package com.brainardphotography.turtle;

import com.beust.jcommander.internal.Lists;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
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

	private TurtleApplication application;
	private Turtle turtle;
	private TurtleProgram program = null;
	private List<TurtleObject> turtleObjects;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		application = TurtleApplication.getInstance();
		runButton.disableProperty().bind(BooleanExpression.booleanExpression(runningProperty)
						.or(ObjectExpression.objectExpression(scriptProperty).isNull()));
		stopButton.disableProperty().bind(BooleanExpression.booleanExpression(runningProperty).not());

		turtleObjects = Lists.newArrayList();

		turtle = new Turtle(50.0, 50.0);
		turtle.setObjectConsumer(object -> turtleObjects.add(object));

		String lastScript = TurtleApplication.getInstance().getLastScriptPath();
		System.out.println("Found last script path " + lastScript);
		if (lastScript != null) {
			openScriptFile(new File(lastScript));
		}

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

		resetScene();

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				draw(canvas.getGraphicsContext2D());
			}
		};

		animationTimer.start();
		runningProperty.setValue(true);

		Thread thread = new Thread(this.program);
		thread.setDaemon(true);
		thread.start();
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
		fileChooser.setInitialDirectory(application.getConfiguration().getProgramsPath().toFile());
		fileChooser.setTitle("Open Groovy Script");
		File file = fileChooser.showOpenDialog(TurtleApplication.getInstance().getStage());

		if (file != null) {
			openScriptFile(file);
		}
	}

	@FXML
	public void quit()
	{
		TurtleApplication.getInstance().getStage().close();
	}

	@FXML
	public void showHelp() {
		HelpDialog helpDialog = new HelpDialog(TurtleApplication.getInstance().getStage());
		helpDialog.show();
	}

	public void resetScene() {
		this.turtle.reset(50.0, 50.0);
		this.turtleObjects.clear();

		double wallHeight = canvas.getHeight() / 2 - 100;

//		turtleObjects.add(new Wall(canvas.getWidth() / 2 - 20, 0, 40, wallHeight));
//		turtleObjects.add(new Wall(canvas.getWidth() / 2 - 20, canvas.getHeight() - wallHeight, 40, wallHeight));
//		turtleObjects.add(Wall.randomWall(canvas));
	}

	private void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.save();

		for (TurtleObject object : turtleObjects) {
			object.updatePosition(rect -> checkRect(rect));
			object.draw(gc);
		}

		turtle.updatePosition(rect -> checkRect(rect));
		turtle.draw(gc);
		gc.restore();
	}

	private boolean checkRect(Rectangle2D rect) {
		Rectangle2D canvasRect = new Rectangle2D(0, 0, canvas.getWidth(), canvas.getHeight());

		if (!canvasRect.contains(rect))
			return false;

		if (turtleObjects.stream().anyMatch(o -> o.getRect() != null && o.getRect().intersects(rect)))
			return false;

		return true;
	}

	private void scriptPropertyChanged(ObservableValue<? extends File> observableValue, File oldValue, File newValue) {
		runButton.setDisable(newValue == null || !newValue.exists());
		statusLabel.setText(newValue == null ? "" : "Running: " + newValue.toString());
	}

	private void openScriptFile(File scriptFile) {
		final TurtleApplication application = TurtleApplication.getInstance();

		application.getExecutorService().submit(() -> {
			if (scriptFile.exists()) {
				TurtleProgram program = new GroovyTurtleProgram(this.turtle, this.canvas, scriptFile);
				program.setErrorConsumer(exception -> application.showErrorMessageSafe(exception));
				program.setErrorConsumer(exception -> TurtleApplication.getInstance().showErrorMessageSafe(exception));

				Platform.runLater(() -> {
					this.program = program;
					this.scriptProperty.setValue(scriptFile);
					TurtleApplication.getInstance().setLastScriptPath(scriptFile.getAbsolutePath());
				});
			}
		});
	}
}
