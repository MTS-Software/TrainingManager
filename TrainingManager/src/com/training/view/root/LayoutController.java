package com.training.view.root;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.training.Main;
import com.training.db.service.Service;
import com.training.model.Standort;
import com.training.model.Status;
import com.training.util.Constants;
import com.training.view.auswertung.FehlendeSchulungenOverviewController;
import com.training.view.auswertung.MitarbeiterMitAnlagenSchulungOverviewController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LayoutController implements Initializable {

	private static final Logger logger = Logger.getLogger(LayoutController.class);

	@FXML
	private TreeView<Object> treeView;
	@FXML
	private BorderPane dataPane;
	@FXML
	private ResourceBundle resources;

	private TreeItem<Object> root;

	private Main main;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.resources = resources;

		initNewTree();
		// initTreeView();

		showHomeScreen();

	}

	private void showHomeScreen() {

		ImageView background = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/trainingBackground.png")));

		background.fitWidthProperty().bind(dataPane.widthProperty());
		background.fitHeightProperty().bind(dataPane.heightProperty());

		dataPane.setCenter(background);

	}

	private void showAuswertungFehlendeSchulungen() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/auswertung/FehlendeSchulungenOverview.fxml"));
			loader.setResources(resources);
			AnchorPane pane = (AnchorPane) loader.load();

			FehlendeSchulungenOverviewController controller = loader.getController();
			// controller.setData();

			dataPane.setCenter(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showAuswertungMitarbeiterMitAnlagen() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/auswertung/MitarbeiterMitAnlagenSchulungOverview.fxml"));
			loader.setResources(resources);
			AnchorPane pane = (AnchorPane) loader.load();

			MitarbeiterMitAnlagenSchulungOverviewController controller = loader.getController();
			// controller.setData();

			dataPane.setCenter(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initNewTree() {

		ImageView imageView;
		int imageSize = 18;

		// Root
		root = new TreeItem<>("TrainingManager");
		root.setExpanded(true);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/book48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		root.setGraphic(imageView);

		// Stammdaten
		TreeItem<Object> itemStammdaten = new TreeItem<>("Stammdaten");
		itemStammdaten.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/data48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStammdaten.setGraphic(imageView);
		root.getChildren().add(itemStammdaten);

		// Stammdaten - Standorte
		TreeItem<Object> itemStandorte = new TreeItem<>("Standorte");
		itemStandorte.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/map48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStandorte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemStandorte);

		// Stammdaten - Level
		TreeItem<Object> itemLevel = new TreeItem<>("Level");
		itemLevel.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/level48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemLevel.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemLevel);

		// Stammdaten - Status
		TreeItem<Object> itemStatus = new TreeItem<>("Status");
		itemStatus.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/status48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStatus.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemStatus);

		// Stammdaten - Kategorien
		TreeItem<Object> itemKategorien = new TreeItem<>("Kategorien");
		itemKategorien.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/level48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemKategorien.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemKategorien);

		// Stammdaten - Produkte
		TreeItem<Object> itemProdukte = new TreeItem<>("Produkte");
		itemProdukte.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/technology48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemProdukte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemProdukte);

		// Stammdaten - Hersteller
		TreeItem<Object> itemHersteller = new TreeItem<>("Hersteller");
		itemHersteller.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/process48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemHersteller.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemHersteller);

		initStandorteTree();

		// Auswertungen
		TreeItem<Object> itemAuswertungen = new TreeItem<>("Auswertungen");
		itemAuswertungen.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/chart48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemAuswertungen.setGraphic(imageView);

		root.getChildren().add(itemAuswertungen);

		// Auswertungen - Fehlende Schulungen
		TreeItem<Object> itemNoTrainings = new TreeItem<>("Fehlende Produkt - Schulungen");
		itemNoTrainings.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemNoTrainings.setGraphic(imageView);

		itemAuswertungen.getChildren().add(itemNoTrainings);

		// Auswertungen - MitarbeiterMitAnlagenSchulung
		TreeItem<Object> itemMitarbeiterMitAnlagenSchulung = new TreeItem<>("Geschulte Anlagen - Mitarbeiter");
		itemMitarbeiterMitAnlagenSchulung.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemMitarbeiterMitAnlagenSchulung.setGraphic(imageView);

		itemAuswertungen.getChildren().add(itemMitarbeiterMitAnlagenSchulung);

		treeView.setRoot(root);

		EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getButton() == MouseButton.PRIMARY) {

					if (event.getClickCount() == 2) {

						TreeItem<Object> selectedItem = treeView.getSelectionModel().getSelectedItem();

						if (selectedItem != null) {

							if (selectedItem.getValue() instanceof String) {
								selectTreeItem(selectedItem);

							}

							if (selectedItem.getValue() instanceof Status) {

								Status status = (Status) selectedItem.getValue();
								main.showSchulungStatusKontrolleOverviewDialog(
										selectedItem.getParent().getParent().getValue().toString(), status);
							}

						}
					}
				}

			}
		};
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);
	}

	private void initStandorteTree() {

		ImageView imageView;
		int imageSize = 18;

		// Standorte
		TreeItem<Object> itemStandorte = new TreeItem<>("Standorte");
		itemStandorte.setExpanded(true);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/map48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStandorte.setGraphic(imageView);
		root.getChildren().add(itemStandorte);

		for (Standort s : Service.getInstance().getStandortService().findAll()) {

			TreeItem<Object> item = new TreeItem<>(s.getName());
			item.setExpanded(false);

			imageView = new ImageView(new Image(
					getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/map48.png")));
			imageView.setFitHeight(imageSize);
			imageView.setFitWidth(imageSize);

			item.setGraphic(imageView);
			itemStandorte.getChildren().add(item);

			initEveryStandortTree(item);

		}

	}

	private void initEveryStandortTree(TreeItem<Object> parentTreeItem) {

		ImageView imageView;
		int imageSize = 18;

		// Stammdaten
		TreeItem<Object> itemStammdaten = new TreeItem<>("Stammdaten");
		itemStammdaten.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/data48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStammdaten.setGraphic(imageView);
		parentTreeItem.getChildren().add(itemStammdaten);

		// Stammdaten - Abteilungen
		TreeItem<Object> itemAbteilungen = new TreeItem<>("Abteilungen");
		itemAbteilungen.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/department48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAbteilungen.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAbteilungen);

		// Stammdaten - Mitarbeiter
		TreeItem<Object> itemMitarbeiter = new TreeItem<>("Mitarbeiter");
		itemMitarbeiter.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/person48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemMitarbeiter.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemMitarbeiter);

		// Stammdaten - Anlagen
		TreeItem<Object> itemAnlagen = new TreeItem<>("Anlagen");
		itemAnlagen.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagen.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAnlagen);

		// Stammdaten Anlagen - Produkte
		TreeItem<Object> itemAnlagenProdukte = new TreeItem<>("Anlagen - Produkte");
		itemAnlagenProdukte.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagenProdukte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAnlagenProdukte);

		// Stammdaten Mitarbeiter - Anlagen
		// TreeItem<Object> itemMitarbeiterAnlagen = new TreeItem<>("Mitarbeiter -
		// Anlagen");
		// itemMitarbeiterAnlagen.setExpanded(false);
		//
		// imageView = new ImageView(new Image(
		// getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		// imageView.setFitHeight(imageSize);
		// imageView.setFitWidth(imageSize);
		//
		// itemMitarbeiterAnlagen.setGraphic(imageView);
		// itemStammdaten.getChildren().add(itemMitarbeiterAnlagen);

		// Schulungen
		TreeItem<Object> itemSchulungen = new TreeItem<>("Schulungen");
		itemSchulungen.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/course48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemSchulungen.setGraphic(imageView);

		parentTreeItem.getChildren().add(itemSchulungen);

		// Schulungen - Produkte
		TreeItem<Object> itemSchulungenVerwaltung = new TreeItem<>("Produkte");
		itemSchulungenVerwaltung.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/book48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemSchulungenVerwaltung.setGraphic(imageView);

		itemSchulungen.getChildren().add(itemSchulungenVerwaltung);

		// Schulungen Anlagen
		TreeItem<Object> itemAnlagenMitarbeiterTechnologien = new TreeItem<>("Anlagen");
		itemAnlagenMitarbeiterTechnologien.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagenMitarbeiterTechnologien.setGraphic(imageView);
		itemSchulungen.getChildren().add(itemAnlagenMitarbeiterTechnologien);

		// Schulungen - Status
		// TreeItem<Object> itemSchulungenStatus = new TreeItem<>("Statuskontrolle");
		// itemSchulungenStatus.setExpanded(false);
		//
		// imageView = new ImageView(
		// new
		// Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/status48.png")));
		// imageView.setFitHeight(imageSize);
		// imageView.setFitWidth(imageSize);
		// itemSchulungenStatus.setGraphic(imageView);
		//
		// itemSchulungen.getChildren().add(itemSchulungenStatus);

	}

	private void initTreeView() {

		ImageView imageView;
		int imageSize = 18;

		// Root
		root = new TreeItem<>("TrainingManager");
		root.setExpanded(true);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/book48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		root.setGraphic(imageView);

		// Stammdaten
		TreeItem<Object> itemStammdaten = new TreeItem<>("Stammdaten");
		itemStammdaten.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/data48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStammdaten.setGraphic(imageView);
		root.getChildren().add(itemStammdaten);

		// Stammdaten - Abteilungen
		TreeItem<Object> itemAbteilungen = new TreeItem<>("Abteilungen");
		itemAbteilungen.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/department48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAbteilungen.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAbteilungen);

		// Stammdaten - Standorte
		TreeItem<Object> itemStandorte = new TreeItem<>("Standorte");
		itemStandorte.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/map48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStandorte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemStandorte);

		// Stammdaten - Mitarbeiter
		TreeItem<Object> itemMitarbeiter = new TreeItem<>("Mitarbeiter");
		itemMitarbeiter.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/person48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemMitarbeiter.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemMitarbeiter);

		// Stammdaten - Level
		TreeItem<Object> itemLevel = new TreeItem<>("Level");
		itemLevel.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/level48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemLevel.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemLevel);

		// Stammdaten - Status
		TreeItem<Object> itemStatus = new TreeItem<>("Status");
		itemStatus.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/status48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemStatus.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemStatus);

		// Stammdaten - Anlagen
		TreeItem<Object> itemAnlagen = new TreeItem<>("Anlagen");
		itemAnlagen.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagen.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAnlagen);

		// Stammdaten - Kategorien
		TreeItem<Object> itemKategorien = new TreeItem<>("Kategorien");
		itemKategorien.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/level48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemKategorien.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemKategorien);

		// Stammdaten - Produkte
		TreeItem<Object> itemProdukte = new TreeItem<>("Produkte");
		itemProdukte.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/technology48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemProdukte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemProdukte);

		// Stammdaten - Hersteller
		TreeItem<Object> itemHersteller = new TreeItem<>("Hersteller");
		itemHersteller.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/process48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemHersteller.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemHersteller);

		// Stammdaten Anlagen - Produkte
		TreeItem<Object> itemAnlagenProdukte = new TreeItem<>("Anlagen - Produkte");
		itemAnlagenProdukte.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagenProdukte.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemAnlagenProdukte);

		// Stammdaten Mitarbeiter - Anlagen
		TreeItem<Object> itemMitarbeiterAnlagen = new TreeItem<>("Mitarbeiter - Anlagen");
		itemMitarbeiterAnlagen.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemMitarbeiterAnlagen.setGraphic(imageView);
		itemStammdaten.getChildren().add(itemMitarbeiterAnlagen);

		// Schulungen
		TreeItem<Object> itemSchulungen = new TreeItem<>("Schulungen");
		itemSchulungen.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/course48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemSchulungen.setGraphic(imageView);

		root.getChildren().add(itemSchulungen);

		// Schulungen - Produkte
		TreeItem<Object> itemSchulungenVerwaltung = new TreeItem<>("Produkte");
		itemSchulungenVerwaltung.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/book48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemSchulungenVerwaltung.setGraphic(imageView);

		itemSchulungen.getChildren().add(itemSchulungenVerwaltung);

		// Schulungen Anlagen
		TreeItem<Object> itemAnlagenMitarbeiterTechnologien = new TreeItem<>("Anlagen");
		itemAnlagenMitarbeiterTechnologien.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);

		itemAnlagenMitarbeiterTechnologien.setGraphic(imageView);
		itemSchulungen.getChildren().add(itemAnlagenMitarbeiterTechnologien);

		// Schulungen - Status
		TreeItem<Object> itemSchulungenStatus = new TreeItem<>("Statuskontrolle");
		itemSchulungenStatus.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/status48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemSchulungenStatus.setGraphic(imageView);

		itemSchulungen.getChildren().add(itemSchulungenStatus);

		// Auswertungen
		TreeItem<Object> itemAuswertungen = new TreeItem<>("Auswertungen");
		itemAuswertungen.setExpanded(false);

		imageView = new ImageView(
				new Image(getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/chart48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemAuswertungen.setGraphic(imageView);

		root.getChildren().add(itemAuswertungen);

		// Auswertungen - Fehlende Schulungen
		TreeItem<Object> itemNoTrainings = new TreeItem<>("Fehlende Produkt - Schulungen");
		itemNoTrainings.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemNoTrainings.setGraphic(imageView);

		itemAuswertungen.getChildren().add(itemNoTrainings);

		// Auswertungen - MitarbeiterMitAnlagenSchulung
		TreeItem<Object> itemMitarbeiterMitAnlagenSchulung = new TreeItem<>("Geschulte Anlagen - Mitarbeiter");
		itemMitarbeiterMitAnlagenSchulung.setExpanded(false);

		imageView = new ImageView(new Image(
				getClass().getClassLoader().getResourceAsStream("com/training/resource/icons/machine48.png")));
		imageView.setFitHeight(imageSize);
		imageView.setFitWidth(imageSize);
		itemMitarbeiterMitAnlagenSchulung.setGraphic(imageView);

		itemAuswertungen.getChildren().add(itemMitarbeiterMitAnlagenSchulung);

		try {
			for (Status status : Service.getInstance().getStatusService().findAll()) {

				TreeItem<Object> itemSchulungStatus = new TreeItem<>(status);
				itemSchulungStatus.setExpanded(false);
				itemSchulungenStatus.getChildren().add(itemSchulungStatus);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		treeView.setRoot(root);

		EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getButton() == MouseButton.PRIMARY) {

					if (event.getClickCount() == 2) {

						TreeItem<Object> selectedItem = treeView.getSelectionModel().getSelectedItem();

						if (selectedItem != null) {

							if (selectedItem.getValue() instanceof String) {
								selectTreeItem(selectedItem);

							}

							if (selectedItem.getValue() instanceof Status) {

								Status status = (Status) selectedItem.getValue();
								main.showSchulungStatusKontrolleOverviewDialog(
										selectedItem.getParent().getParent().getValue().toString(), status);
							}

						}
					}
				}

			}
		};
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent);

		/*
		 * EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent event) {
		 * 
		 * if (event.getCode() == KeyCode.ENTER) {
		 * 
		 * TreeItem<Object> selectedItem =
		 * treeView.getSelectionModel().getSelectedItem();
		 * 
		 * if (selectedItem.getValue() instanceof String) {
		 * selectTreeItem(selectedItem); }
		 * 
		 * }
		 * 
		 * } };
		 */

		// treeView.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent);

	}

	private void selectTreeItem(TreeItem<Object> selectedItem) {

		if (selectedItem.getValue().toString().equalsIgnoreCase(resources.getString("appname"))) {

			showHomeScreen();

		}

		if (selectedItem.getParent().getValue().toString().equalsIgnoreCase("Stammdaten")) {

			if (selectedItem.getValue().toString().equalsIgnoreCase("Abteilungen")) {
				main.showAbteilungOverviewDialog(selectedItem.getParent().getParent().getValue().toString());

			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Mitarbeiter")) {

				main.showMitarbeiterOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Standorte")) {
				main.showStandortOverviewDialog();
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Level")) {
				main.showLevelOverviewDialog();
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Status")) {
				main.showStatusOverviewDialog();
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Kategorien")) {
				main.showKategorieOverviewDialog();

			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Produkte")) {
				main.showProduktOverviewDialog();
			}
			if (selectedItem.getValue().toString().equalsIgnoreCase("Hersteller")) {
				main.showHerstellerOverviewDialog();
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Anlagen")) {
				main.showAnlageOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Anlagen - Produkte")) {
				main.showAnlageProdukteOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Mitarbeiter - Anlagen")) {
				main.showeMitarbeiterAnlageOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

		}

		if (selectedItem.getParent().getValue().toString().equalsIgnoreCase("Schulungen")) {
			if (selectedItem.getValue().toString().equalsIgnoreCase("Produkte")) {
				main.showSchulungOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Anlagen")) {
				main.showAnlageMitarbeiterOverviewDialog(selectedItem.getParent().getParent().getValue().toString());
			}

		}

		if (selectedItem.getParent().getValue().toString().equalsIgnoreCase("Auswertungen")) {
			if (selectedItem.getValue().toString().equalsIgnoreCase("Fehlende Produkt - Schulungen")) {
				showAuswertungFehlendeSchulungen();
			}

			if (selectedItem.getValue().toString().equalsIgnoreCase("Geschulte Anlagen - Mitarbeiter")) {
				showAuswertungMitarbeiterMitAnlagen();
			}

		}

	}

	public void setMain(Main main) {
		this.main = main;

	}

	@FXML
	private void handleSettings() {
		main.showSettingsDialog();
	}

	@FXML
	private void handleUpdateKeyEvent(KeyEvent event) {

	}

	@FXML
	private void handleUpdateMenuItem() {

	}

	@FXML
	private void handleAbout() {

		StringBuilder sb = new StringBuilder();

		sb.append(Main.VERSION + "\n");
		sb.append(Main.BUILD + "\n");
		sb.append(Main.DATE.substring(0, 26) + " $" + "\n");
		sb.append("Entwicklung mit JDK: " + Main.JDK + "\n");
		sb.append("Ausfuehrende JRE: " + System.getProperty("java.version"));

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(resources.getString("about"));
		alert.setHeaderText(resources.getString("appname") + "\n" + sb.toString().replace("$", ""));
		alert.initOwner(main.getPrimaryStage());

		alert.setContentText(
				"Entwicklung:\n" + resources.getString("programer1") + "\n" + resources.getString("programer2"));

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().addAll(Constants.STYLESHEET);

		alert.showAndWait();
	}

	@FXML
	private void handleExit() {
		System.exit(0);
	}

}