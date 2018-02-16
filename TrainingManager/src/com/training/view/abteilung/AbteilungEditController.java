package com.training.view.abteilung;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Abteilung;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AbteilungEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private AbteilungDataController abteilungDataController;

	private Stage dialogStage;

	private Abteilung abteilung;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Abteilung abteilung) {

		this.abteilung = abteilung;

		abteilungDataController.setData(abteilung);
		abteilungDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!abteilungDataController.isInputValid())
			return;

		if (abteilung == null)
			abteilung = new Abteilung();

		if (abteilung != null) {
			abteilung.setName(abteilungDataController.nameField.getText());
			abteilung.setKostenstelle(abteilungDataController.kostenstelleField.getText());
			abteilung.setStandort(abteilungDataController.standortComboBox.getSelectionModel().getSelectedItem());

		}

		if (abteilung.getId() == 0)
			insert();
		else
			update();

		if (!Service.getInstance().isErrorStatus()) {
			okClicked = true;
			dialogStage.close();
		}

	}

	private void insert() {

		Service.getInstance().getAbteilungService().insert(abteilung);

	}

	private void update() {

		Service.getInstance().getAbteilungService().update(abteilung);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
