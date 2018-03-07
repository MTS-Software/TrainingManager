package com.training.view.auswertung;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import com.training.db.service.Service;
import com.training.model.Anlage;
import com.training.model.Hersteller;
import com.training.model.Produkt;
import com.training.util.Constants;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FehlendeSchulungenOverviewController {

	@FXML
	private ResourceBundle resources = ResourceBundle.getBundle("language");

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
	@FXML
	private Button searchButton;

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

		table.setPlaceholder(new Label("Keine Daten vorhanden"));
	}

	public void setData() {

		ProgressIndicator progress = new ProgressIndicator();
		progress.setMinWidth(Constants.PROGRESSINDICATOR_SIZE);
		progress.setMinHeight(Constants.PROGRESSINDICATOR_SIZE);
		progress.setMaxWidth(Constants.PROGRESSINDICATOR_SIZE);
		progress.setMaxHeight(Constants.PROGRESSINDICATOR_SIZE);

		Label label = new Label();
		label.setFont(new Font("Arial", 28));
		// label.setText("Suche nach verbauten Produkten in Anlagen, an welchen es keine
		// Schulung gibt ...");
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

				ObservableList<NoTraining> data = FXCollections.observableArrayList(generateData());

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						if (data.isEmpty())
							table.setPlaceholder(null);
						table.setItems(data);

					}
				});

			}
		});

		th.start();

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
