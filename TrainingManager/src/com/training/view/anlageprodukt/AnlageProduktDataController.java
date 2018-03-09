package com.training.view.anlageprodukt;

import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Kategorie;
import com.training.model.Produkt;
import com.training.util.Constants;
import com.training.view.alert.InputValidatorAlert;
import com.training.view.auswertung.NoTraining;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AnlageProduktDataController {

	private ResourceBundle resources = ResourceBundle.getBundle("language");

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
		table.setPlaceholder(new Label("Keine Daten vorhanden"));

	}

	public void setData(Anlage data) {

		this.data = data;

		if (data != null) {

			nameLabel.setText(data.getName());

			ProgressIndicator progress = new ProgressIndicator();
			progress.setMinWidth(Constants.PROGRESSINDICATOR_SIZE);
			progress.setMinHeight(Constants.PROGRESSINDICATOR_SIZE);
			progress.setMaxWidth(Constants.PROGRESSINDICATOR_SIZE);
			progress.setMaxHeight(Constants.PROGRESSINDICATOR_SIZE);

			Label label = new Label();
			label.setFont(new Font("Arial", 28));
			label.setText(resources.getString("loading_data"));

			VBox vBox = new VBox();
			vBox.setSpacing(10);
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().add(progress);
			vBox.getChildren().add(label);

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							table.setItems(null);
							table.setPlaceholder(vBox);
						}
					});

					ObservableList<Produkt> produkteAll = FXCollections.observableArrayList(
							Service.getInstance().getProduktService().findProduktWithKategorieWithHersteller());

					if (data.getProdukte() != null)
						for (Produkt h : produkteAll) {
							for (Produkt h1 : data.getProdukte()) {
								if (h.getId() == h1.getId())
									h.setActive(true);

							}

						}

					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							if (produkteAll.isEmpty())
								table.setPlaceholder(null);
							table.setItems(produkteAll);

						}
					});

				}
			});

			th.start();

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
