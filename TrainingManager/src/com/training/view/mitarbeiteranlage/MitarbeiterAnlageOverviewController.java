package com.training.view.mitarbeiteranlage;

import java.io.IOException;
import java.util.ResourceBundle;

import com.training.Main;
import com.training.db.service.Service;
import com.training.model.Mitarbeiter;
import com.training.util.Constants;
import com.training.view.alert.DeleteYesNoAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MitarbeiterAnlageOverviewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private TableView<Mitarbeiter> table;
	@FXML
	private TableColumn<Mitarbeiter, String> nachNameColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> vorNameColumn;

	@FXML
	private MitarbeiterAnlageDataController mitarbeiterAnlageDataController;

	private Stage dialogStage;

	@FXML
	private void initialize() {

		nachNameColumn.setCellValueFactory(cellData -> cellData.getValue().nachnameProperty());
		vorNameColumn.setCellValueFactory(cellData -> cellData.getValue().vornameProperty());

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

		ObservableList<Mitarbeiter> mitarbeiter = FXCollections
				.observableArrayList(Service.getInstance().getMitarbeiterService().findMitarbeiterWithAnlagen());
		table.setItems(mitarbeiter);

	}

	private void showDetails(Mitarbeiter data) {

		mitarbeiterAnlageDataController.setData(data);
		mitarbeiterAnlageDataController.setEditable(false);
	}

	@FXML
	private void handleDelete() {
		Mitarbeiter data;
		int selectedIndex = table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			DeleteYesNoAlert alert = new DeleteYesNoAlert(dialogStage);

			if (alert.isOKButton()) {
				data = table.getSelectionModel().getSelectedItem();
				Service.getInstance().getMitarbeiterService().delete(data);
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

		Mitarbeiter data = new Mitarbeiter();

		boolean okClicked = showEditDialog(data);
		if (okClicked) {
			table.setItems(FXCollections.observableArrayList(Service.getInstance().getMitarbeiterService().findMitarbeiterWithAnlagen()));
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

	private boolean showEditDialog(Mitarbeiter data) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/mitarbeiteranlage/MitarbeiterAnlageEdit.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.initOwner(this.dialogStage);
			dialogStage.centerOnScreen();
			dialogStage.setResizable(true);
			dialogStage.getIcons().addAll(this.dialogStage.getIcons());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setTitle("Mitarbeiter - Anlagen: Bearbeiten");

			Scene scene = new Scene(page);
			scene.getStylesheets().add(Constants.STYLESHEET);
			dialogStage.setScene(scene);

			MitarbeiterAnlageEditController controller = loader.getController();
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
