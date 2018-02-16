package com.training.view.hersteller;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Hersteller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HerstellerEditController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private HerstellerDataController herstellerDataController;

	private Stage dialogStage;

	private Hersteller hersteller;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Hersteller hersteller) {

		this.hersteller = hersteller;

		herstellerDataController.setData(hersteller);
		herstellerDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!herstellerDataController.isInputValid())
			return;

		if (hersteller == null)
			hersteller = new Hersteller();

		if (hersteller != null) {
			hersteller.setName(herstellerDataController.nameField.getText());

		}

		if (hersteller.getId() == 0)
			insert();
		else
			update();

		if (!Service.getInstance().isErrorStatus()) {
			okClicked = true;
			dialogStage.close();
		}

	}

	private void insert() {

		Service.getInstance().getHerstellerService().insert(hersteller);

	}

	private void update() {

		Service.getInstance().getHerstellerService().update(hersteller);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
