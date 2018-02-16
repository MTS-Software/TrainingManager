package com.training.view.anlage;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AnlageEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnlageDataController anlageDataController;

	private Stage dialogStage;

	private Anlage data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Anlage data) {

		this.data = data;

		anlageDataController.setData(data);
		anlageDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!anlageDataController.isInputValid())
			return;

		if (data == null)
			data = new Anlage();

		if (data != null) {
			data.setName(anlageDataController.nameField.getText());
			data.setStandort(anlageDataController.standortComboBox.getSelectionModel().getSelectedItem());

		}

		if (data.getId() == 0)
			insert();
		else
			update();

		if (!Service.getInstance().isErrorStatus()) {
			okClicked = true;
			dialogStage.close();
		}

	}

	private void insert() {

		Service.getInstance().getAnlageService().insert(data);

	}

	private void update() {

		Service.getInstance().getAnlageService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
