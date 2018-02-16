package com.training.view.mitarbeiteranlage;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Mitarbeiter;
import com.training.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MitarbeiterAnlageDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	private Label nameLabel;

	@FXML
	public TableView<Anlage> table;
	@FXML
	private TableColumn<Anlage, Boolean> auswahlColumn;
	@FXML
	private TableColumn<Anlage, String> nameColumn;

	private Mitarbeiter data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Mitarbeiter data) {

		this.data = data;

		if (data != null) {

			nameLabel.setText(data.getNachname() + " " + data.getVorname());

			ObservableList<Anlage> anlageAll = FXCollections
					.observableArrayList(Service.getInstance().getAnlageService().findAll());

			if (data.getAnlagen() != null)
				for (Anlage h : anlageAll) {
					for (Anlage h1 : data.getAnlagen()) {
						if (h.getId() == h1.getId()) {
							h.setActive(true);

						}

					}

				}

			auswahlColumn.setCellValueFactory(new PropertyValueFactory<Anlage, Boolean>("active"));
			auswahlColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
			auswahlColumn.setCellFactory(CheckBoxTableCell.forTableColumn(auswahlColumn));
			auswahlColumn.setEditable(true);

			nameColumn.setCellValueFactory(new PropertyValueFactory<Anlage, String>("name"));
			nameColumn.setCellFactory(column -> {
				return new TableCell<Anlage, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							setText(item);

						}
					}

				};

			});

			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.setEditable(true);
			table.setItems(anlageAll);

		} else {
			clearFields();
		}

	}

	private void clearFields() {
		nameLabel.setText("");
		table.setItems(null);

	}

	public void setEditable(boolean editable) {

		table.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (text.length() == 0) {
			return true;

		} else {
			new InputValidatorAlert(dialogStage, text).showAndWait();
			return false;
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
