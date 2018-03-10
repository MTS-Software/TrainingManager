package com.training.view.auswertung;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.training.db.service.Service;
import com.training.model.Abteilung;
import com.training.model.Anlage;
import com.training.model.Mitarbeiter;
import com.training.util.Constants;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class MitarbeiterMitAnlagenSchulungOverviewController {

	@FXML
	private ResourceBundle resources = ResourceBundle.getBundle("language");

	@FXML
	private TableView<Mitarbeiter> table;
	@FXML
	private TableColumn<Mitarbeiter, String> nachnameColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> vornameColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> taetigkeitColumn;
	@FXML
	private TableColumn<Mitarbeiter, String> telephoneColumn;
	@FXML
	private TableColumn<Mitarbeiter, Abteilung> abteilungColumn;
	@FXML
	private TableColumn<Mitarbeiter, Abteilung> standortColumn;

	@FXML
	public ComboBox<Anlage> anlageComboBox;
	@FXML
	private Button searchButton;

	private Stage dialogStage;

	@FXML
	private void initialize() {

		nachnameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("nachname"));
		vornameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("vorname"));
		taetigkeitColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("taetigkeit"));
		telephoneColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("telephone"));

		searchButton.setDisable(true);

		ObservableList<Anlage> standorte = FXCollections
				.observableArrayList(Service.getInstance().getAnlageService().findAnlagenWithStandort());
		anlageComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (anlageComboBox.getSelectionModel().isEmpty())
					searchButton.setDisable(true);
				else
					searchButton.setDisable(false);

			}
		});
		anlageComboBox.setItems(standorte);
		anlageComboBox.setConverter(new StringConverter<Anlage>() {

			@Override
			public Anlage fromString(String string) {
				return null;
			}

			@Override
			public String toString(Anlage object) {
				return object.getName();
			}
		});

		abteilungColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Abteilung>("abteilung"));
		abteilungColumn.setCellFactory(column -> {
			return new TableCell<Mitarbeiter, Abteilung>() {

				@Override
				protected void updateItem(Abteilung item, boolean empty) {
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

		standortColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Abteilung>("abteilung"));
		standortColumn.setCellFactory(column -> {
			return new TableCell<Mitarbeiter, Abteilung>() {

				@Override
				protected void updateItem(Abteilung item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item.getStandort().getName());
					}
				}

			};

		});

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

				ObservableList<Mitarbeiter> data = FXCollections.observableArrayList(generateData());

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

	private List<Mitarbeiter> generateData() {

		List<Mitarbeiter> mitarbeiter = new ArrayList<>();

		for (Anlage anl : Service.getInstance().getAnlageService().findAnlagenWithStandortWithAbteilungWithMitarbeiter()) {

			if (anl.getId() == anlageComboBox.getSelectionModel().getSelectedItem().getId())
				mitarbeiter = anl.getMitarbeiter();

		}

		return mitarbeiter;

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
