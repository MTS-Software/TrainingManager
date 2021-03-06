package com.training.view.standort;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Standort;
import com.training.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StandortDataController {

	@FXML
	private ResourceBundle resources;

	@FXML
	public TextField nameField;

	private Standort data;

	private Stage dialogStage;

	@FXML
	private void initialize() {
		clearFields();
	}

	public void setData(Standort data) {

		this.data = data;

		ObservableList<Standort> standorte = FXCollections.observableArrayList();
		standorte.setAll(Service.getInstance().getStandortService().findAll());

		if (data != null) {
			nameField.setText(data.getName());

		} else {
			clearFields();

		}

	}

	private void clearFields() {

		nameField.setText("");

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein g�ltiger Name!\n";

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
