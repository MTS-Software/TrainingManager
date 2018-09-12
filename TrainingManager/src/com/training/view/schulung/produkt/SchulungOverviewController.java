package com.training.view.schulung.produkt;

import java.io.IOException;
import java.util.ResourceBundle;

import com.training.Main;
import com.training.db.service.Service;
import com.training.model.Level;
import com.training.model.Mitarbeiter;
import com.training.model.Schulung;
import com.training.model.Status;
import com.training.util.Constants;
import com.training.view.alert.DeleteYesNoAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SchulungOverviewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private TextField filterField;

	@FXML
	private TableView<Schulung> table;
	@FXML
	private TableColumn<Schulung, String> nameColumn;
	@FXML
	private TableColumn<Schulung, Mitarbeiter> mitarbeiterColumn;
	@FXML
	private TableColumn<Schulung, Level> levelColumn;

	@FXML
	private SchulungDataController schulungDataController;

	private ObservableList<Schulung> schulungenList;
	private Status status;

	private Stage dialogStage;

	private String standort;

	@FXML
	private void initialize() {

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		mitarbeiterColumn.setCellValueFactory(new PropertyValueFactory<Schulung, Mitarbeiter>("mitarbeiter"));
		mitarbeiterColumn.setCellFactory(column -> {
			return new TableCell<Schulung, Mitarbeiter>() {

				@Override
				protected void updateItem(Mitarbeiter item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.getNachname() + " " + item.getVorname());
					}
				}

			};

		});

		levelColumn.setCellValueFactory(new PropertyValueFactory<Schulung, Level>("level"));
		levelColumn.setCellFactory(column -> {
			return new TableCell<Schulung, Level>() {

				@Override
				protected void updateItem(Level item, boolean empty) {
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

	public void setData(String standort) {

		this.standort = standort;

		schulungenList = FXCollections.observableArrayList(Service.getInstance().getSchulungService()
				.findSchulungenFromStandortWithMitarbeiterWithLevelWithStatusWithProdukt(standort));
		table.setItems(schulungenList);

		filter();

	}

	public void setData(String standort, Status status) {

		this.status = status;

		ObservableList<Schulung> schulungenList = FXCollections
				.observableArrayList(Service.getInstance().getSchulungService()
						.findSchulungenFromStandortWithMitarbeiterWithLevelWithStatusWithProdukt(standort));

		this.schulungenList = FXCollections.observableArrayList();

		for (Schulung schulung : schulungenList) {

			if (schulung.getStatus().getId() == status.getId()) {
				this.schulungenList.add(schulung);
			}

		}

		table.setItems(this.schulungenList);

		filter();

	}

	private void showDetails(Schulung data) {

		schulungDataController.setData(standort, data);
		schulungDataController.setEditable(false);
	}

	private void filter() {

		FilteredList<Schulung> filteredData;
		filteredData = new FilteredList<>(schulungenList, p -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(schulung -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (schulung.getName() != null)
					if (schulung.getName().toLowerCase().contains(lowerCaseFilter)) {
						return true;

					}

				if (schulung.getMitarbeiter() != null)
					if (schulung.getMitarbeiter().getNachname().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}

				return false;
			});
		});

		SortedList<Schulung> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		table.setItems(sortedData);
	}

	@FXML
	private void handleDelete() {
		Schulung data;
		int selectedIndex = table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			DeleteYesNoAlert alert = new DeleteYesNoAlert(dialogStage);

			if (alert.isOKButton()) {
				data = table.getSelectionModel().getSelectedItem();
				Service.getInstance().getSchulungService().delete(data);
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

		Schulung data = new Schulung();

		boolean okClicked = showEditDialog(data);
		if (okClicked) {
			table.setItems(FXCollections.observableArrayList(Service.getInstance().getSchulungService()
					.findSchulungenFromStandortWithMitarbeiterWithLevelWithStatusWithProdukt(standort)));
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

			if (status != null)
				setData(standort, status);
			else
				setData(standort);
		}

	}

	private boolean showEditDialog(Schulung data) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/schulung/SchulungEdit.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.initOwner(this.dialogStage);
			dialogStage.centerOnScreen();
			dialogStage.setResizable(false);
			dialogStage.getIcons().addAll(this.dialogStage.getIcons());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setTitle("Schulung: Bearbeiten");

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			SchulungEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setData(standort, data);

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
