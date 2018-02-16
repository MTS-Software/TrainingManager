package com.training.view.mitarbeiteranlage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Mitarbeiter;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MitarbeiterAnlageEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private MitarbeiterAnlageDataController mitarbeiterAnlageDataController;

	private Stage dialogStage;

	private Mitarbeiter data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Mitarbeiter data) {

		this.data = data;

		mitarbeiterAnlageDataController.setData(data);
		mitarbeiterAnlageDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!mitarbeiterAnlageDataController.isInputValid())
			return;

		if (data == null)
			data = new Mitarbeiter();

		if (data != null) {

			List<Anlage> anlagen = new ArrayList<>();
			for (Anlage h : mitarbeiterAnlageDataController.table.getItems()) {
				if (h.isActive())
					anlagen.add(h);

			}

			data.setAnlagen(anlagen);

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

		Service.getInstance().getMitarbeiterService().insert(data);

	}

	private void update() {

		Service.getInstance().getMitarbeiterService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
