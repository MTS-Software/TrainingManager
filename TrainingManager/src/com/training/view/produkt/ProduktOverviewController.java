package com.training.view.produkt;

import java.io.IOException;
import java.util.ResourceBundle;

import com.training.Main;
import com.training.db.service.Service;
import com.training.model.Hersteller;
import com.training.model.Kategorie;
import com.training.model.Produkt;
import com.training.util.Constants;
import com.training.view.alert.DeleteYesNoAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProduktOverviewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private TableView<Produkt> table;
	@FXML
	private TableColumn<Produkt, String> nameColumn;
	@FXML
	private TableColumn<Produkt, Hersteller> herstellerColumn;
	@FXML
	private TableColumn<Produkt, Kategorie> kategorieColumn;
	@FXML
	private Label nameLabel;

	@FXML
	private ProduktDataController produktDataController;

	private Stage dialogStage;

	@FXML
	private void initialize() {

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		herstellerColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Hersteller>("hersteller"));
		herstellerColumn.setCellFactory(column -> {
			return new TableCell<Produkt, Hersteller>() {

				@Override
				protected void updateItem(Hersteller item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.getName());
					}
				}

			};

		});

		kategorieColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Kategorie>("kategorie"));
		kategorieColumn.setCellFactory(column -> {
			return new TableCell<Produkt, Kategorie>() {

				@Override
				protected void updateItem(Kategorie item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.getName());
					}
				}

			};

		});
		showDetails(null);

		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 2)
					if (event.getButton() == MouseButton.PRIMARY)
						if (!table.getSelectionModel().isEmpty())
							showEditDialog(table.getSelectionModel().getSelectedItem());

			}
		});
		table.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void setData() {

		ObservableList<Produkt> data;
		data = FXCollections.observableArrayList(Service.getInstance().getProduktService().findAll());

		table.setItems(data);

	}

	private void showDetails(Produkt abteilung) {

		produktDataController.setData(abteilung);
		produktDataController.setEditable(false);
	}

	@FXML
	private void handleDelete() {
		Produkt data;
		int selectedIndex = table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			DeleteYesNoAlert alert = new DeleteYesNoAlert(dialogStage);

			if (alert.isOKButton()) {
				data = table.getSelectionModel().getSelectedItem();
				Service.getInstance().getProduktService().delete(data);
				if (!Service.getInstance().isErrorStatus()) {
					table.getItems().remove(data);
					table.getSelectionModel().clearSelection();
					table.refresh();

				}
			}

		}

	}

	@FXML
	private void handleDeleteKeyPressed(KeyEvent event) {

		if (event.getEventType() == KeyEvent.KEY_PRESSED)
			if (event.getCode() == KeyCode.DELETE)
				handleDelete();

	}

	@FXML
	private void handleNew() {

		Produkt data = new Produkt();

		boolean okClicked = showEditDialog(data);
		if (okClicked) {
			table.setItems(FXCollections.observableArrayList(Service.getInstance().getProduktService().findAll()));
			table.refresh();
			showDetails(null);
		}
	}

	@FXML
	private void handleEdit() {

		showEditDialog(table.getSelectionModel().getSelectedItem());

	}

	@FXML
	private void handleEditMouseClick(MouseEvent event) {

		if (event.getButton() == MouseButton.PRIMARY)
			if (event.getClickCount() == 2)
				handleEdit();

	}

	@FXML
	private void handleRefresh(KeyEvent event) {

		if (event.getCode() == KeyCode.F5) {
			setData();
		}

	}

	private boolean showEditDialog(Produkt data) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/produkt/ProduktEdit.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.initOwner(this.dialogStage);
			dialogStage.centerOnScreen();
			dialogStage.setResizable(false);
			dialogStage.getIcons().addAll(this.dialogStage.getIcons());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setTitle("Produkt: Bearbeiten");

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			ProduktEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(data);

			dialogStage.showAndWait();

			showDetails(data);

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
