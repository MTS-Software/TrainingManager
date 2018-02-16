package com.training.view.hersteller;

import java.util.ResourceBundle;

import com.training.model.Hersteller;
import com.training.view.alert.InputValidatorAlert;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HerstellerDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	public TextField nameField;

	private Hersteller data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Hersteller data) {

		this.data = data;

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
			text += "Kein gültiger Name!\n";

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
