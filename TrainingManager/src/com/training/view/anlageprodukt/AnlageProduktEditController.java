package com.training.view.anlageprodukt;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Produkt;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AnlageProduktEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnlageProduktDataController anlageProduktDataController;

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

		anlageProduktDataController.setData(data);
		anlageProduktDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!anlageProduktDataController.isInputValid())
			return;

		if (data == null)
			data = new Anlage();

		if (data != null) {

			List<Produkt> herstellerProdukte = new ArrayList<>();
			for (Produkt h : anlageProduktDataController.table.getItems()) {
				if (h.isActive())
					herstellerProdukte.add(h);
			}
			data.setProdukte(herstellerProdukte);

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
