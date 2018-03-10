package com.training;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.training.db.service.Service;
import com.training.db.util.HibernateUtil;
import com.training.model.Abteilung;
import com.training.model.Standort;
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
import com.training.view.root.LoginDialog;
import com.training.view.root.SettingsController;
import com.training.view.schulung.SchulungOverviewController;
import com.training.view.standort.StandortOverviewController;
import com.training.view.status.StatusOverviewController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	// Revisionhistory
	// Muss manuell verändert werden
	public final static String VERSION = "$Version: 1.0$";

	// Wird automatisch von SVN beschrieben
	public final static String BUILD = "$Rev: 135 $";
	public final static String DATE = "$Date: 2018-02-13 09:23:13 +0100 (Di, 13 Feb 2018) $";

	// Java Entwicklungsversion
	public final static String JDK = "1.8.0_152";

	private static final Logger logger = Logger.getLogger(Main.class);
	private ResourceBundle resources = ResourceBundle.getBundle("language");

	private static String ip;

	private Stage primaryStage;

	private BorderPane rootLayout;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;

		HibernateUtil.getSessionFactory();

		String userHome = System.getProperty("user.home");

		PropertyConfigurator.configure(getClass().getClassLoader().getResource("log4j.properties"));
		ApplicationProperties.configure("application.properties",
				userHome + File.separator + resources.getString("appname"), "application.properties");
		ApplicationProperties.getInstance().setup();

		if (ip != null) {
			ApplicationProperties.getInstance().edit("db_host", ip);
			LoginDialog.loggedIn = true;

		}

		this.primaryStage.setTitle(resources.getString("appname") + " Build " + BUILD.replace("$", " "));
		this.primaryStage.setMaximized(true);
		this.primaryStage.getIcons()
				.add(new Image(getClass().getClassLoader().getResourceAsStream(Constants.APP_ICON)));
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				Platform.exit();

				System.exit(0);

			}
		});

		initRootLayout();

		// test();

		this.primaryStage.show();
	}

	private void test() {

		System.out.println("Abteilungen");
		for (Abteilung abt : Service.getInstance().getAbteilungService().findAll()) {

			System.out.println(abt.getName());
			System.out.println(abt.getStandort().getName());

			// for (Mitarbeiter ma : abt.getMitarbeiter()) {
			// System.out.println(ma.getVorname() + " " + ma.getNachname());
			// }

		}

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

			// showArtikelOverview();

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

}
