package com.training;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.training.db.util.HibernateUtil;
import com.training.model.Status;
import com.training.util.ApplicationProperties;
import com.training.util.Constants;
import com.training.view.abteilung.AbteilungOverviewController;
import com.training.view.anlage.AnlageOverviewController;
import com.training.view.anlagemitarbeiter.AnlageMitarbeiterOverviewController;
import com.training.view.anlageprodukt.AnlageProduktOverviewController;
import com.training.view.hersteller.HerstellerOverviewController;
import com.training.view.kategorie.KategorieOverviewController;
import com.training.view.level.LevelOverviewController;
import com.training.view.mitarbeiter.MitarbeiterOverviewController;
import com.training.view.mitarbeiteranlage.MitarbeiterAnlageOverviewController;
import com.training.view.produkt.ProduktOverviewController;
import com.training.view.root.LayoutController;
import com.training.view.root.SettingsController;
import com.training.view.schulung.SchulungOverviewController;
import com.training.view.standort.StandortOverviewController;
import com.training.view.status.StatusOverviewController;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {

	public final static String VERSION = "1.0";

	// Java Entwicklungsversion
	public final static String JDK = "1.8.0_152";

	private static final Logger logger = Logger.getLogger(Main.class);
	private ResourceBundle resources = ResourceBundle.getBundle("language");

	// Splash
	private Pane splashLayout;
	private ProgressBar loadProgress;
	private Label progressText;
	private Label appInfo;
	private Label developerInfo;
	public static final String SPLASH_IMAGE = Constants.SPLASHSCREEN_IMAGE_PROCESSMANAGER;
	private static int threadSplashSleepTime = Constants.THREAD_SPLASH_SLEEP_TIME;
	private static double fadeTransitionsTime = Constants.FADE_TRANSITIONS_TIME;
	private static boolean showSplashScreen = Constants.SHOW_SPLASH_SCREEN;

	public final static String APP_ICON = Constants.APP_ICON;

	private static String ip;

	private Stage primaryStage;

	private BorderPane rootLayout;

	public static void main(String[] args) {

		if (args != null) {

			// threadSplashSleepTime = 0;
			// fadeTransitionsTime = 0.0;
			showSplashScreen = true;

		}

		launch(args);

	}

	@Override
	public void start(Stage initStage) {

		this.primaryStage = new Stage();

		final Task<Integer> modulTask = new Task<Integer>() {
			@Override
			protected Integer call() throws InterruptedException {

				int actProgress = 1;
				int maxProgress = 2;

				updateProgress(0, maxProgress);
				updateMessage("Programm wird gestartet. . .");
				Thread.sleep(threadSplashSleepTime * 2);

				if (actProgress == 1) {
					updateProgress(actProgress, maxProgress);
					updateMessage(actProgress + " von " + maxProgress + ": "
							+ "Initialisiere Einstellungen, Datenbank . . .");

					initProperties();

					Thread.sleep(threadSplashSleepTime);
					actProgress++;
				}

				if (actProgress == 2) {
					updateProgress(actProgress, maxProgress);
					updateMessage(
							actProgress + " von " + maxProgress + ": " + "Initialisiere Benutzeroberfläche . . .");

					initGraphics();

					Thread.sleep(threadSplashSleepTime);
					actProgress++;
				}

				return actProgress;
			}
		};

		showSplash(initStage, modulTask, () -> initRootLayout());
		new Thread(modulTask).start();

	}

	private void initProperties() {

		PropertyConfigurator.configure(getClass().getClassLoader().getResource("log4j.properties"));

		String userHome = System.getProperty("user.home");

		ApplicationProperties.configure("application.properties",
				userHome + File.separator + resources.getString("appname"), "application.properties");
		ApplicationProperties.getInstance().setup();

		HibernateUtil.getSessionFactory();

	}

	private void initGraphics() {

		primaryStage.setTitle(
				resources.getString("appname") + "@" + ApplicationProperties.getInstance().getProperty("db_host"));
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream(Main.APP_ICON)));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				Platform.exit();
				System.exit(0);

			}
		});

	}

	public void initRootLayout() {

		try {

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(resources);
			loader.setLocation(Main.class.getResource("view/root/Layout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET).toExternalForm());

			// primaryStage.setMaximized(true);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			LayoutController controller = loader.getController();
			controller.setMain(this);

			this.primaryStage.show();

		} catch (IOException e) {

			if (logger.isInfoEnabled()) {
				logger.error(e);
			}

			e.printStackTrace();
		}

	}

	public void showAbteilungOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/abteilung/AbteilungOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Abteilungen");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			AbteilungOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showMitarbeiterOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/mitarbeiter/MitarbeiterOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Mitarbeiter");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			MitarbeiterOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showLevelOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/level/LevelOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Level");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			LevelOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showSchulungOverviewDialog(String standort) {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/schulung/SchulungOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Schulungen");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			SchulungOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showStandortOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/standort/StandortOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Standorte");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			StandortOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showAnlageMitarbeiterOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/anlagemitarbeiter/AnlageMitarbeiterOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Anlagen - Mitarbeiter");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			AnlageMitarbeiterOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showeMitarbeiterAnlageOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/mitarbeiteranlage/MitarbeiterAnlageOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Mitarbeiter - Anlagen");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			MitarbeiterAnlageOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showKategorieOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/kategorie/KategorieOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Kategorien");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			KategorieOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showStatusOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/status/StatusOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Status");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			StatusOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showAnlageOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/anlage/AnlageOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Anlagen");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			AnlageOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showAnlageProdukteOverviewDialog(String standort) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/anlageprodukt/AnlageProduktOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Anlagen - Produkte");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			AnlageProduktOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showProduktOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/produkt/ProduktOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Produkte");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			ProduktOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showHerstellerOverviewDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/hersteller/HerstellerOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Hersteller");
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			HerstellerOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void showSchulungStatusKontrolleOverviewDialog(String standort, Status status) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/schulung/SchulungOverview.fxml"));
			loader.setResources(resources);
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.centerOnScreen();
			// dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setTitle("Schulungen: Statuskontrolle -> " + status.getName());
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			SchulungOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort, status);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public boolean showSettingsDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/root/Settings.fxml"));
			loader.setResources(resources);
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.getIcons().addAll(primaryStage.getIcons());
			dialogStage.setTitle(resources.getString("settings"));
			// dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET).toExternalForm());
			dialogStage.setScene(scene);

			SettingsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData();

			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void init() {

		ImageView splash = new ImageView(new Image(SPLASH_IMAGE));

		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(Constants.SPLASH_WIDTH - 20);

		progressText = new Label("");
		progressText.setAlignment(Pos.CENTER);

		StringBuilder sb = new StringBuilder();
		appInfo = new Label("");

		sb.append(resources.getString("appname"));
		sb.append(" (Version 1.0)");

		appInfo.setFont(Font.font("System", FontWeight.BOLD, 15));
		appInfo.setTextFill(Color.DARKGREY);
		appInfo.setText(sb.toString().replace("$", ""));

		developerInfo = new Label("");
		developerInfo.setFont(Font.font("System", FontWeight.BOLD, 20));
		developerInfo.setTextFill(Color.DARKGREY);
		developerInfo.setText("\nEntwicklung: " + resources.getString("programer1"));

		splashLayout = new VBox();
		splashLayout.getChildren().addAll(splash, loadProgress, progressText, developerInfo, appInfo);
		splashLayout.setStyle(
				"-fx-padding: 5; " + "-fx-background-color: #DAE6F3; " + "-fx-border-width:5; " + "-fx-border-color: "
						+ "linear-gradient(" + "to bottom, " + "#7ebcea, " + "derive(#7ebcea, 50%)" + ");");
		splashLayout.setEffect(new DropShadow());
	}

	private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {

		progressText.textProperty().bind(task.messageProperty());
		loadProgress.progressProperty().bind(task.progressProperty());

		task.stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				loadProgress.progressProperty().unbind();
				loadProgress.setProgress(1);
				initStage.toFront();
				FadeTransition fadeSplash = new FadeTransition(Duration.seconds(fadeTransitionsTime), splashLayout);
				fadeSplash.setFromValue(1.0);
				fadeSplash.setToValue(0.0);
				fadeSplash.setOnFinished(actionEvent -> initStage.close());
				fadeSplash.play();

				initCompletionHandler.complete();
			} // todo add code to gracefully handle other task states.
		});

		Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
		final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		initStage.setScene(splashScene);
		initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - Constants.SPLASH_WIDTH / 2);
		initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - Constants.SPLASH_HEIGHT / 2);
		initStage.getIcons().add(new Image(Constants.APP_ICON));
		initStage.setTitle(resources.getString("appname"));
		initStage.initStyle(StageStyle.TRANSPARENT);
		initStage.setResizable(false);

		initStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				Platform.exit();
				System.exit(0);

			}
		});

		initStage.setAlwaysOnTop(true);

		if (showSplashScreen)
			initStage.show();
	}

	public interface InitCompletionHandler {
		void complete();
	}

}
