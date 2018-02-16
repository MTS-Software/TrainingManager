package com.training.view.anlagemitarbeiter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Mitarbeiter;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AnlageMitarbeiterEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnlageMitarbeiterDataController anlageMitarbeiterDataController;

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

		anlageMitarbeiterDataController.setData(data);
		anlageMitarbeiterDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!anlageMitarbeiterDataController.isInputValid())
			return;

		if (data == null)
			data = new Anlage();

		if (data != null) {

			List<Mitarbeiter> mitarbeiter = new ArrayList<>();
			for (Mitarbeiter h : anlageMitarbeiterDataController.table.getItems()) {
				if (h.isActive())
					mitarbeiter.add(h);

			}

			data.setMitarbeiter(mitarbeiter);

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
