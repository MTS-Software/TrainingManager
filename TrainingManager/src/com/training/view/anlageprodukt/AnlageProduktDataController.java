package com.training.view.anlageprodukt;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Kategorie;
import com.training.model.Produkt;
import com.training.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AnlageProduktDataController {

	@FXML
	private ResourceBundle resources;

	private Stage dialogStage;

	@FXML
	private Label nameLabel;

	@FXML
	public TableView<Produkt> table;
	@FXML
	private TableColumn<Produkt, Boolean> auswahlColumn;
	@FXML
	private TableColumn<Produkt, String> produktColumn;
	@FXML
	private TableColumn<Produkt, Hersteller> herstellerColumn;
	@FXML
	private TableColumn<Produkt, Kategorie> kategorieColumn;

	private Anlage data;

	@FXML
	private void initialize() {

		clearFields();

	}

	public void setData(Anlage data) {

		this.data = data;

		if (data != null) {

			nameLabel.setText(data.getName());

			ObservableList<Produkt> herstellerAll = FXCollections
					.observableArrayList(Service.getInstance().getProduktService().findAll());

			if (data.getProdukte() != null)
				for (Produkt h : herstellerAll) {
					for (Produkt h1 : data.getProdukte()) {
						if (h.getId() == h1.getId())
							h.setActive(true);

					}

				}

			auswahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Boolean>("active"));
			auswahlColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
			auswahlColumn.setCellFactory(CheckBoxTableCell.forTableColumn(auswahlColumn));
			auswahlColumn.setEditable(true);

			herstellerColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Hersteller>("hersteller"));
			herstellerColumn.setCellFactory(column -> {
				return new TableCell<Produkt, Hersteller>() {

					@Override
					protected void updateItem(Hersteller item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							setText(item.getName());
						}
					}

				};

			});

			produktColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("name"));
			produktColumn.setCellFactory(column -> {
				return new TableCell<Produkt, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {

							setText(item);
						}
					}

				};

			});

			kategorieColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Kategorie>("kategorie"));
			kategorieColumn.setCellFactory(column -> {
				return new TableCell<Produkt, Kategorie>() {

					@Override
					protected void updateItem(Kategorie item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							setText(item.getName());
						}
					}

				};

			});

			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			table.setEditable(true);
			table.setItems(herstellerAll);

		} else {
			clearFields();
		}

	}

	private void clearFields() {
		nameLabel.setText("");
		table.setItems(null);

	}

	public void setEditable(boolean editable) {

		table.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

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
