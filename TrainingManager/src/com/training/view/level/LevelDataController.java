package com.training.view.level;

import java.util.ResourceBundle;

import com.training.model.Level;
import com.training.view.alert.InputValidatorAlert;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LevelDataController {

	@FXML
	private ResourceBundle resources;

	@FXML
	public TextField nameField;
	@FXML
	public TextField levelField;

	private Level data;

	private Stage dialogStage;

	@FXML
	private void initialize() {
		clearFields();
	}

	public void setData(Level data) {

		this.data = data;

		if (data != null) {
			nameField.setText(data.getName());
			levelField.setText(String.valueOf(data.getLevel()));

		} else {

			clearFields();
		}

	}

	private void clearFields() {
		nameField.setText("");
		levelField.setText("");

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);
		levelField.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein gültiger Name!\n";
		if (levelField.getText() == null || levelField.getText().length() == 0)
			text += "Keine gültiger Level!\n";

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
