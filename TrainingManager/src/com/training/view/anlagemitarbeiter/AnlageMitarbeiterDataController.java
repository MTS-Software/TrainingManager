package com.training.view.anlagemitarbeiter;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Abteilung;
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

public class AnlageMitarbeiterDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	private Label nameLabel;

	@FXML
	public TableView<Mitarbeiter> table;
	@FXML
	private TableColumn<Mitarbeiter, Boolean> auswahlColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> mitarbeiterVornameColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> mitarbeiterNachnameColumn;
	@FXML
	private TableColumn<Mitarbeiter, Abteilung> mitarbeiterAbteilungColumn;

	private Anlage data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Anlage data) {

		this.data = data;

		if (data != null) {

			nameLabel.setText(data.getName());

			ObservableList<Mitarbeiter> mitarbeiterAll = FXCollections.observableArrayList(Service.getInstance()
					.getMitarbeiterService().findMitarbeiterFromStandortWithAbteilung(data.getStandort().getName()));

			if (data.getMitarbeiter() != null)
				for (Mitarbeiter h : mitarbeiterAll) {
					for (Mitarbeiter h1 : data.getMitarbeiter()) {
						if (h.getId() == h1.getId()) {
							h.setActive(true);

						}

					}

				}

			auswahlColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Boolean>("active"));
			auswahlColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
			auswahlColumn.setCellFactory(CheckBoxTableCell.forTableColumn(auswahlColumn));
			auswahlColumn.setEditable(true);

			mitarbeiterVornameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("vorname"));
			mitarbeiterVornameColumn.setCellFactory(column -> {
				return new TableCell<Mitarbeiter, String>() {

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

			mitarbeiterNachnameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("nachname"));
			mitarbeiterNachnameColumn.setCellFactory(column -> {
				return new TableCell<Mitarbeiter, String>() {

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

			mitarbeiterAbteilungColumn
					.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Abteilung>("abteilung"));
			mitarbeiterAbteilungColumn.setCellFactory(column -> {
				return new TableCell<Mitarbeiter, Abteilung>() {

					@Override
					protected void updateItem(Abteilung item, boolean empty) {
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

			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.setEditable(true);
			table.setItems(mitarbeiterAll);

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
