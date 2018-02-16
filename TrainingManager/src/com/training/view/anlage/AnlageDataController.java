package com.training.view.anlage;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Standort;
import com.training.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AnlageDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	public TextField nameField;
	@FXML
	public ComboBox<Standort> standortComboBox;

	private Anlage data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Anlage data) {

		this.data = data;

		if (data != null) {
			nameField.setText(data.getName());

			ObservableList<Standort> standorte = FXCollections
					.observableArrayList(Service.getInstance().getStandortService().findAll());
			standortComboBox.setItems(standorte);
			standortComboBox.setConverter(new StringConverter<Standort>() {

				@Override
				public Standort fromString(String string) {
					return null;
				}

				@Override
				public String toString(Standort object) {
					return object.getName();
				}
			});
			standortComboBox.getSelectionModel().select(data.getStandort());

		} else {
			clearFields();
		}

	}

	private void clearFields() {

		nameField.setText("");
		standortComboBox.setItems(null);
		standortComboBox.getSelectionModel().select(null);

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);
		standortComboBox.setDisable(!editable);
	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein gültiger Name!\n";
		if (standortComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Kein gültiger Standort!\n";

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
