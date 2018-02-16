package com.training.view.schulung;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Level;
import com.training.model.Mitarbeiter;
import com.training.model.Produkt;
import com.training.model.Schulung;
import com.training.model.Status;
import com.training.view.alert.InputValidatorAlert;
import com.training.view.alert.NotImplementedAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SchulungDataController {

	private Stage dialogStage;

	@FXML
	private ResourceBundle resources;

	@FXML
	public TextField nameField;
	@FXML
	public DatePicker beginDateField;
	@FXML
	public DatePicker endDateField;
	@FXML
	public ComboBox<Mitarbeiter> mitarbeiterComboBox;
	@FXML
	public ComboBox<Level> levelComboBox;
	@FXML
	public ComboBox<Status> statusComboBox;

	@FXML
	public ComboBox<Produkt> produktComboBox;
	@FXML
	private Button noProduktSelectionButton;

	@FXML
	public Hyperlink anhaengeLink;

	private Schulung data;

	private ObservableList<Mitarbeiter> mitarbeiter;
	private ObservableList<Status> status;
	private ObservableList<Level> level;
	private ObservableList<Produkt> produkte;

	@FXML
	private void initialize() {

		clearFields();

		produktComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (produktComboBox.getSelectionModel().getSelectedItem() != null)
					noProduktSelectionButton.setVisible(true);
				else
					noProduktSelectionButton.setVisible(false);

			}
		});

		anhaengeLink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new NotImplementedAlert(dialogStage).showAndWait();

			}
		});

		anhaengeLink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new NotImplementedAlert(dialogStage).showAndWait();

			}
		});

		mitarbeiter = FXCollections.observableArrayList(Service.getInstance().getMitarbeiterService().findAll());
		status = FXCollections.observableArrayList(Service.getInstance().getStatusService().findAll());
		level = FXCollections.observableArrayList(Service.getInstance().getLevelService().findAll());
		produkte = FXCollections.observableArrayList(Service.getInstance().getProduktService().findAll());

	}

	public void setData(Schulung data) {

		this.data = data;

		if (data != null) {

			nameField.setText(data.getName());

			Calendar cal = Calendar.getInstance();

			if (data.getBegin() != null) {
				cal.setTime(data.getBegin());
				beginDateField.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
						cal.get(Calendar.DAY_OF_MONTH)));
			} else
				beginDateField.setValue(null);

			if (data.getEnd() != null) {
				cal.setTime(data.getEnd());
				endDateField.setValue(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
						cal.get(Calendar.DAY_OF_MONTH)));
			} else
				endDateField.setValue(null);

			// ObservableList<Mitarbeiter> mitarbeiter = FXCollections
			// .observableArrayList(Service.getInstance().getMitarbeiterService().findAll());
			mitarbeiterComboBox.setItems(mitarbeiter);
			mitarbeiterComboBox.setConverter(new StringConverter<Mitarbeiter>() {

				@Override
				public Mitarbeiter fromString(String string) {
					return null;
				}

				@Override
				public String toString(Mitarbeiter object) {
					return object.getNachname() + " " + object.getVorname();
				}

			});
			mitarbeiterComboBox.getSelectionModel().select(data.getMitarbeiter());

			// ObservableList<Level> level = FXCollections
			// .observableArrayList(Service.getInstance().getLevelService().findAll());
			levelComboBox.setItems(level);
			levelComboBox.setConverter(new StringConverter<Level>() {

				@Override
				public Level fromString(String string) {
					return null;
				}

				@Override
				public String toString(Level object) {
					return object.getName();
				}

			});
			levelComboBox.getSelectionModel().select(data.getLevel());

			// ObservableList<Status> status = FXCollections
			// .observableArrayList(Service.getInstance().getStatusService().findAll());
			statusComboBox.setItems(status);
			statusComboBox.setConverter(new StringConverter<Status>() {

				@Override
				public Status fromString(String string) {
					return null;
				}

				@Override
				public String toString(Status object) {
					return object.getName();
				}

			});
			statusComboBox.getSelectionModel().select(data.getStatus());

			// ObservableList<Produkt> herstellerTechnologien = FXCollections
			// .observableArrayList(Service.getInstance().getProduktService().findAll());

			produktComboBox.setItems(produkte);
			produktComboBox.setConverter(new StringConverter<Produkt>() {

				@Override
				public Produkt fromString(String string) {
					return null;
				}

				@Override
				public String toString(Produkt object) {

					return object.getName() + " - " + object.getHersteller().getName();
				}

			});
			produktComboBox.getSelectionModel().select(data.getProdukt());
			
			if (produktComboBox.getSelectionModel().getSelectedItem() != null)
				noProduktSelectionButton.setVisible(true);
			else
				noProduktSelectionButton.setVisible(false);


		} else {

			clearFields();

		}

	}

	@FXML
	private void handleNoProduktSelection() {

		produktComboBox.getSelectionModel().select(null);

	}

	private void clearFields() {
		nameField.setText("");
		beginDateField.setValue(null);
		endDateField.setValue(null);

		mitarbeiterComboBox.setItems(null);
		mitarbeiterComboBox.getSelectionModel().select(null);
		levelComboBox.setItems(null);
		levelComboBox.getSelectionModel().select(null);
		produktComboBox.setItems(null);
		produktComboBox.getSelectionModel().select(null);

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);
		beginDateField.setDisable(!editable);
		endDateField.setDisable(!editable);
		mitarbeiterComboBox.setDisable(!editable);
		levelComboBox.setDisable(!editable);
		statusComboBox.setDisable(!editable);
		produktComboBox.setDisable(!editable);
		anhaengeLink.setDisable(!editable);

		noProduktSelectionButton.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein gültiger Name!\n";
		if (beginDateField.getValue() == null)
			text += "Kein gueltiges Beginn Datum!\n";
		if (endDateField.getValue() == null)
			text += "Kein gueltiges Ende Datum!\n";
		if (mitarbeiterComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Kein gültiger Mitarbeiter!\n";
		if (statusComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Kein gültiger Status!\n";
		// if (produktComboBox.getSelectionModel().getSelectedItem() == null)
		// text += "Kein gültiges Produkt!\n";
		// if (firmaComboBox.getSelectionModel().getSelectedItem() == null)
		// text += "Keine gültige Firma!\n";
		if (levelComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Kein gültiger Level!\n";

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
