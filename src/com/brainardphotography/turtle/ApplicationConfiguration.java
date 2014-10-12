package com.brainardphotography.turtle;

import javafx.application.Platform;
import javafx.beans.property.*;
import jline.internal.Log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by johnbrainard on 10/8/14.
 */
public class ApplicationConfiguration {
	private static final Logger logger = Logger.getLogger(ApplicationConfiguration.class.getName());

	/**
	 * Top level scripts directory.
	 */
	private ReadOnlyObjectProperty<Path> homePath;

	/**
	 * Turtle Program scripts directory
	 */
	private ReadOnlyObjectProperty<Path> programsPath;

	/**
	 * Directory where environment scripts are stored
	 */
	private ReadOnlyObjectProperty<Path> environmentsPath;

	/**
	 * Directory where library scripts are stored
	 */
	private ReadOnlyObjectProperty<Path> libraryPath;

	/**
	 * Load the application configuration in the background
	 *
	 * @param onComplete Called when configuration is finished loading.
	 */
	void loadConfiguration(ExecutorService executorService, Runnable onComplete) {
		executorService.submit(() -> {
			String userHome = System.getProperty("user.home");

			if (userHome == null)
				throw new IllegalStateException("System property user.home not found.");

			Path homePath = Paths.get(userHome, "Turtle");
			Path programsPath = homePath.resolve("Programs");
			Path environmentsPath = homePath.resolve("Environment");
			Path libraryPath = homePath.resolve("Libraries");

			File homeFile = homePath.toFile();
			File programsFile = programsPath.toFile();
			File environmentsFile = environmentsPath.toFile();
			File librariesFile = libraryPath.toFile();

			logger.log(Level.INFO, "Home File: " + homeFile.toString());
			logger.log(Level.INFO, "Programs File: " + programsFile.toString());
			logger.log(Level.INFO, "Environments File: " + environmentsFile.toString());
			logger.log(Level.INFO, "Libraries File: " + librariesFile.toString());

			if (!homeFile.exists())
				homeFile.mkdirs();
			if (!programsFile.exists())
				programsFile.mkdirs();
			if (!environmentsFile.exists())
				environmentsFile.mkdirs();
			if (!librariesFile.exists())
				librariesFile.mkdirs();

			this.homePath = new SimpleReadOnlyObjectProperty<Path>(homePath, "homePath");
			this.programsPath = new SimpleReadOnlyObjectProperty<Path>(programsPath, "programsPath");
			this.environmentsPath = new SimpleReadOnlyObjectProperty<Path>(environmentsPath, "environmentsPath");
			this.libraryPath = new SimpleReadOnlyObjectProperty<Path>(libraryPath, "libraryPath");

			Platform.runLater(onComplete);
		});
	}

	public ReadOnlyObjectProperty<Path> homePathProperty() {
		return homePath;
	}

	public Path getHomePath() {
		return homePath.getValue();
	}

	public ReadOnlyObjectProperty<Path> programsPathProperty() {
		return programsPath;
	}

	public Path getProgramsPath() {
		return programsPath.getValue();
	}

	public ReadOnlyObjectProperty<Path> environmentsPath() {
		return environmentsPath;
	}

	public Path getEnvironmentsPath() {
		return environmentsPath.getValue();
	}

	public ReadOnlyObjectProperty<Path> libraryPath() {
		return libraryPath;
	}

	public Path getLibraryPath() {
		return libraryPath.getValue();
	}

	private class SimpleReadOnlyObjectProperty<T> extends ReadOnlyObjectPropertyBase<T> {
		private final T value;
		private final String name;

		public SimpleReadOnlyObjectProperty(T value, String name) {
			this.value = value;
			this.name = name;
		}

		@Override
		public T get() {
			return value;
		}

		@Override
		public Object getBean() {
			return null;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
