package com.training.view.auswertung;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Produkt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FehlendeSchulungenOverviewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private TableView<NoTraining> table;
	@FXML
	private TableColumn<NoTraining, Anlage> anlageColumn;
	@FXML
	private TableColumn<NoTraining, Produkt> produktColumn;
	@FXML
	private TableColumn<NoTraining, Hersteller> herstellerColumn;
	@FXML
	private TableColumn<NoTraining, Produkt> kategorieColumn;

	@FXML
	private Label nameLabel;

	private Stage dialogStage;

	@FXML
	private void initialize() {

		anlageColumn.setCellValueFactory(new PropertyValueFactory<NoTraining, Anlage>("anlage"));
		anlageColumn.setCellFactory(column -> {
			return new TableCell<NoTraining, Anlage>() {

				@Override
				protected void updateItem(Anlage item, boolean empty) {
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

		produktColumn.setCellValueFactory(new PropertyValueFactory<NoTraining, Produkt>("produkt"));
		produktColumn.setCellFactory(column -> {
			return new TableCell<NoTraining, Produkt>() {

				@Override
				protected void updateItem(Produkt item, boolean empty) {
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

		herstellerColumn.setCellValueFactory(new PropertyValueFactory<NoTraining, Hersteller>("hersteller"));
		herstellerColumn.setCellFactory(column -> {
			return new TableCell<NoTraining, Hersteller>() {

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

		kategorieColumn.setCellValueFactory(new PropertyValueFactory<NoTraining, Produkt>("produkt"));
		kategorieColumn.setCellFactory(column -> {
			return new TableCell<NoTraining, Produkt>() {

				@Override
				protected void updateItem(Produkt item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.getKategorie().getName());
					}
				}

			};

		});

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void setData() {

		ObservableList<NoTraining> data = FXCollections.observableArrayList(generateData());

		table.setItems(data);

	}

	private List<NoTraining> generateData() {

		List<Anlage> anlagenOhneSchulung = new ArrayList<>();

		for (Anlage anlage : Service.getInstance().getAnlageService().findAll()) {

			List<Produkt> ht = new ArrayList<>();

			for (Produkt herstellerTechnologieAnlage : anlage.getProdukte()) {

				for (Produkt h : Service.getInstance().getProduktService().findAll()) {

					if (herstellerTechnologieAnlage.getId() == h.getId()) {
						if (h.getSchulungen().isEmpty()) {
							ht.add(h);
						}
					}
				}
			}

			anlage.setProdukte(ht);

			if (!ht.isEmpty())
				anlagenOhneSchulung.add(anlage);

		}

		List<NoTraining> noTraining = new ArrayList<>();

		for (Anlage anlage : anlagenOhneSchulung) {

			for (Produkt h : anlage.getProdukte()) {

				NoTraining not = new NoTraining();
				not.setAnlage(anlage);
				not.setHersteller(h.getHersteller());
				not.setProdukt(h);

				noTraining.add(not);
			}
		}

		return noTraining;

	}

	@FXML
	private void handleSearch() {

		setData();

	}

	@FXML
	private void handleRefresh(KeyEvent event) {

		if (event.getCode() == KeyCode.F5) {
			setData();
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
