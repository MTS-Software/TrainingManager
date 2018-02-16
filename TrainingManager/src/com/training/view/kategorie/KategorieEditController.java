package com.training.view.kategorie;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Kategorie;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class KategorieEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private KategorieDataController kategorieDataController;

	private Stage dialogStage;

	private Kategorie data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Kategorie data) {

		this.data = data;

		kategorieDataController.setData(data);
		kategorieDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!kategorieDataController.isInputValid())
			return;

		if (data == null)
			data = new Kategorie();

		if (data != null) {
			data.setName(kategorieDataController.nameField.getText());

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

		Service.getInstance().getKategorieService().insert(data);

	}

	private void update() {

		Service.getInstance().getKategorieService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
