package com.training.view.produkt;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Hersteller;
import com.training.model.Kategorie;
import com.training.model.Produkt;
import com.training.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProduktDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	public TextField nameField;
	@FXML
	public ComboBox<Hersteller> herstellerComboBox;
	@FXML
	public ComboBox<Kategorie> kategorieComboBox;

	private Produkt data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Produkt data) {

		this.data = data;

		if (data != null) {
			nameField.setText(data.getName());

			ObservableList<Hersteller> hersteller = FXCollections
					.observableArrayList(Service.getInstance().getHerstellerService().findAll());
			herstellerComboBox.setItems(hersteller);
			herstellerComboBox.setConverter(new StringConverter<Hersteller>() {

				@Override
				public Hersteller fromString(String string) {
					return null;
				}

				@Override
				public String toString(Hersteller object) {
					return object.getName();
				}
			});
			herstellerComboBox.getSelectionModel().select(data.getHersteller());

			ObservableList<Kategorie> kategorien = FXCollections
					.observableArrayList(Service.getInstance().getKategorieService().findAll());
			kategorieComboBox.setItems(kategorien);
			kategorieComboBox.setConverter(new StringConverter<Kategorie>() {

				@Override
				public Kategorie fromString(String string) {
					return null;
				}

				@Override
				public String toString(Kategorie object) {
					return object.getName();
				}
			});
			kategorieComboBox.getSelectionModel().select(data.getKategorie());

		} else {
			clearFields();
		}

	}

	private void clearFields() {

		nameField.setText("");
		herstellerComboBox.setItems(null);
		herstellerComboBox.getSelectionModel().select(null);
		kategorieComboBox.setItems(null);
		kategorieComboBox.getSelectionModel().select(null);

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);
		herstellerComboBox.setDisable(!editable);
		kategorieComboBox.setDisable(!editable);
	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein gültiger Name!\n";
		if (herstellerComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Kein gültiger Hersteller!\n";
		if (kategorieComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Keine gültige Kategorie!\n";

		if (text.length() == 0) {
			return true;

		} else {
			new InputValidatorAlert(dialogStage, text).showAndWait();
			return false;
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
