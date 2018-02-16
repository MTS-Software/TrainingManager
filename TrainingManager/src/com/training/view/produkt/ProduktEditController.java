package com.training.view.produkt;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Produkt;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ProduktEditController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private ProduktDataController produktDataController;

	private Stage dialogStage;

	private Produkt produkt;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Produkt produkt) {

		this.produkt = produkt;

		produktDataController.setData(produkt);
		produktDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!produktDataController.isInputValid())
			return;

		if (produkt == null)
			produkt = new Produkt();

		if (produkt != null) {
			produkt.setName(produktDataController.nameField.getText());
			produkt.setHersteller(produktDataController.herstellerComboBox.getSelectionModel().getSelectedItem());
			produkt.setKategorie(produktDataController.kategorieComboBox.getSelectionModel().getSelectedItem());

		}

		if (produkt.getId() == 0)
			insert();
		else
			update();

		if (!Service.getInstance().isErrorStatus()) {
			okClicked = true;
			dialogStage.close();
		}

	}

	private void insert() {

		Service.getInstance().getProduktService().insert(produkt);

	}

	private void update() {

		Service.getInstance().getProduktService().update(produkt);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
