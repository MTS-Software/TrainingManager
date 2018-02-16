package com.training.view.mitarbeiter;

import java.io.FileInputStream;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Mitarbeiter;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MitarbeiterEditController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private MitarbeiterDataController mitarbeiterDataController;

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

		mitarbeiterDataController.setData(data);
		mitarbeiterDataController.setDialogStage(dialogStage);

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (!mitarbeiterDataController.isInputValid())
			return;

		if (data == null)
			data = new Mitarbeiter();

		if (data != null) {
			data.setVorname(mitarbeiterDataController.vornameField.getText());
			data.setNachname(mitarbeiterDataController.nachnameField.getText());
			data.setPersnr(mitarbeiterDataController.persnrField.getText());

			if (mitarbeiterDataController.fotoFile != null)
				try {
					byte[] imageData = new byte[(int) mitarbeiterDataController.fotoFile.length()];
					FileInputStream fileInputStream = new FileInputStream(mitarbeiterDataController.fotoFile);
					fileInputStream.read(imageData);
					fileInputStream.close();
					data.setFoto(imageData);
				} catch (Exception e) {
					e.printStackTrace();
				}
			else
				data.setFoto(null);

			if (mitarbeiterDataController.lebenslaufFile != null)
				try {
					byte[] imageData = new byte[(int) mitarbeiterDataController.lebenslaufFile.length()];
					FileInputStream fileInputStream = new FileInputStream(mitarbeiterDataController.lebenslaufFile);
					fileInputStream.read(imageData);
					fileInputStream.close();
					data.setLebenslauf(imageData);
				} catch (Exception e) {
					e.printStackTrace();
				}
			data.setAbteilung(mitarbeiterDataController.abteilungComboBox.getSelectionModel().getSelectedItem());

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
