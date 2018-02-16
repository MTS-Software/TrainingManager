package com.training.view.standort;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Standort;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StandortEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private StandortDataController standortDataController;

	private Stage dialogStage;

	private Standort data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Standort data) {

		this.data = data;

		standortDataController.setData(data);
		standortDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!standortDataController.isInputValid())
			return;

		if (data == null)
			data = new Standort();

		if (data != null) {
			data.setName(standortDataController.nameField.getText());

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

		Service.getInstance().getStandortService().insert(data);

	}

	private void update() {

		Service.getInstance().getStandortService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
