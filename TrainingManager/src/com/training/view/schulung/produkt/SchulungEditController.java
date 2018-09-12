package com.training.view.schulung.produkt;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Schulung;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SchulungEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private SchulungDataController schulungDataController;

	private Stage dialogStage;

	private Schulung data;

	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	public void setData(String standort, Schulung data) {

		this.data = data;

		schulungDataController.setData(standort, data);
		schulungDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!schulungDataController.isInputValid())
			return;

		if (data == null)
			data = new Schulung();

		if (data != null) {
			data.setName(schulungDataController.nameField.getText());

			Date dateBegin = Date
					.from(schulungDataController.beginDateField.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));
			data.setBegin(dateBegin);
			Date dateEnd = Date
					.from(schulungDataController.endDateField.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));
			data.setEnd(dateEnd);

			data.setMitarbeiter(schulungDataController.mitarbeiterComboBox.getSelectionModel().getSelectedItem());
			data.setLevel(schulungDataController.levelComboBox.getSelectionModel().getSelectedItem());
			data.setStatus(schulungDataController.statusComboBox.getSelectionModel().getSelectedItem());
			data.setProdukt(schulungDataController.produktComboBox.getSelectionModel().getSelectedItem());

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

		Service.getInstance().getSchulungService().insert(data);

	}

	private void update() {

		Service.getInstance().getSchulungService().update(data);

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
