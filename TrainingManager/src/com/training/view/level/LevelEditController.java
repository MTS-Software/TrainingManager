package com.training.view.level;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Level;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LevelEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private LevelDataController levelDataController;

	private Stage dialogStage;

	private Level data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Level data) {

		this.data = data;

		levelDataController.setData(data);
		levelDataController.setDialogStage(dialogStage);
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!levelDataController.isInputValid())
			return;

		if (data == null)
			data = new Level();

		if (data != null) {
			data.setName(levelDataController.nameField.getText());

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

		Service.getInstance().getLevelService().insert(data);

	}

	private void update() {

		Service.getInstance().getLevelService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
