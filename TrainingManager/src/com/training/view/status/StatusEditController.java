package com.training.view.status;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Status;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StatusEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private StatusDataController statusDataController;

	private Stage dialogStage;

	private Status data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Status data) {

		this.data = data;

		statusDataController.setData(data);
		statusDataController.setDialogStage(dialogStage);
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!statusDataController.isInputValid())
			return;

		if (data == null)
			data = new Status();

		if (data != null) {
			data.setName(statusDataController.nameField.getText());

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

		Service.getInstance().getStatusService().insert(data);

	}

	private void update() {

		Service.getInstance().getStatusService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
