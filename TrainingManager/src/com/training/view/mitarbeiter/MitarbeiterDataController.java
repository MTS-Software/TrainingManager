package com.training.view.mitarbeiter;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import com.training.Main;
import com.training.db.service.Service;
import com.training.model.Abteilung;
import com.training.model.Mitarbeiter;
import com.training.util.Constants;
import com.training.view.alert.InputValidatorAlert;
import com.training.view.alert.NotImplementedAlert;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class MitarbeiterDataController {

	private Stage dialogStage;

	@FXML
	private ResourceBundle resources;

	@FXML
	public TextField vornameField;
	@FXML
	public TextField nachnameField;
	@FXML
	public TextField persnrField;
	@FXML
	public TextField telephoneField;
	@FXML
	public TextField taetigkeitField;
	@FXML
	public ImageView fotoImage;
	@FXML
	public Button fotoUploadButton;
	@FXML
	public Button fotoDeleteButton;
	@FXML
	public Button lebenslaufUploadBtn;
	@FXML
	public Button lebenslaufViewBtn;

	@FXML
	public ComboBox<Abteilung> abteilungComboBox;

	private Mitarbeiter data;

	public File lebenslaufFile;
	public File fotoFile;

	@FXML
	private void initialize() {

		clearFields();

		fotoImage.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 2) {

					File dir = new File(System.getProperty("user.home") + File.separator + "TrainingManager", "files");
					if (!dir.exists())
						dir.mkdir();

					try {

						byte[] byteData = data.getFoto();
						File file = new File(dir + File.separator + "foto.jpg");

						FileOutputStream fos = new FileOutputStream(file);
						fos.write(byteData);
						fos.close();

						if (file.exists()) {
							try {
								Desktop.getDesktop().open(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}

					} catch (Exception e) {

						e.printStackTrace();

					}

				}

			}
		});

		lebenslaufUploadBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				// FileChooser fileChooser = new FileChooser();
				// FileChooser.ExtensionFilter extFilterPng = new
				// FileChooser.ExtensionFilter("PDF files (*.pdf)",
				// "*.pdf");
				// fileChooser.getExtensionFilters().addAll(extFilterPng);
				// File file = fileChooser.showOpenDialog(dialogStage);
				// if (file != null) {
				// lebenslaufFile = file;
				// }

				NotImplementedAlert alert = new NotImplementedAlert(dialogStage);
				alert.showAndWait();
			}
		});
		lebenslaufViewBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// if (lebenslaufFile.exists()) {
				// try {
				// Desktop.getDesktop().open(lebenslaufFile);
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
				//
				// }

				NotImplementedAlert alert = new NotImplementedAlert(dialogStage);
				alert.showAndWait();

			}
		});

		fotoUploadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {

				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("JPG files (*.jpg)",
						"*.jpg");
				fileChooser.getExtensionFilters().addAll(extFilterPng);
				File file = fileChooser.showOpenDialog(dialogStage);

				if (file != null) {
					if (checkFileSize(1024, file)) {

						try {
							FileInputStream fis = new FileInputStream(file);
							Image img = new Image(fis);

							fotoImage.setImage(img);
							fotoFile = file;

						} catch (FileNotFoundException ex) {

							ex.printStackTrace();
						}
					}
				}

			}
		});

		fotoImage.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		fotoImage.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasFiles()) {
					success = true;
					String filePath = null;

					for (File file : db.getFiles()) {

						if (file.getName().endsWith(".jpg")) {
							if (checkFileSize(1024, file)) {
								filePath = file.getAbsolutePath();
								fotoFile = file;

								try {
									File f = fotoFile;
									FileInputStream fis = new FileInputStream(f);

									Image img = new Image(fis, 180, 220, false, false);
									fotoImage.setImage(img);

								} catch (FileNotFoundException e) { // TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Fehler");
							alert.setHeaderText("Falscher Dateityp");
							alert.setContentText("Bitte wählen Sie den richtigen Dateityp");
							alert.initOwner(dialogStage);

							DialogPane dialogPane = alert.getDialogPane();
							dialogPane.getStylesheets()
									.addAll(getClass().getResource(Constants.STYLESHEET).toExternalForm());
							alert.showAndWait();
						}

					}

				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

	}

	private boolean checkFileSize(int maxSizeKB, File file) {

		long fileSizeInBytes = file.length();
		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
		long fileSizeInKB = fileSizeInBytes / 1024;
		// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
		long fileSizeInMB = fileSizeInKB / 1024;

		if (fileSizeInKB < maxSizeKB) {
			return true;

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Datei zu gross, bitte verkleinern");
			alert.setContentText("Die Datei darf maximal 1024kB haben");
			alert.initOwner(dialogStage);

			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().addAll(getClass().getResource(Constants.STYLESHEET).toExternalForm());
			alert.showAndWait();

			return false;
		}

	}

	public void setData(Mitarbeiter data) {

		this.data = data;

		if (data != null) {
			vornameField.setText(data.getVorname());
			nachnameField.setText(data.getNachname());
			persnrField.setText(data.getPersnr());
			telephoneField.setText(data.getTelephone());
			taetigkeitField.setText(data.getTaetigkeit());

			File dir = new File(System.getProperty("user.home") + File.separator + "TrainingManager", "files");
			if (!dir.exists())
				dir.mkdir();

			if (data.getFoto() != null) {

				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {

								Image image1 = new Image(
										Main.class.getResourceAsStream("/com/training/resource/icons/ladeBild.png"));
								fotoImage.setImage(image1);

								try {

									byte[] byteData = data.getFoto();
									File file = new File(dir + File.separator + "foto.jpg");

									FileOutputStream fos = new FileOutputStream(file);
									fos.write(byteData);
									fos.close();

									fotoFile = file;

								} catch (Exception e) {

									e.printStackTrace();

								}

								try {
									FileInputStream fis;
									fis = new FileInputStream(fotoFile);
									Image img = new Image(fis, 180, 220, false, false);
									fotoImage.setImage(img);

								} catch (FileNotFoundException e) { // TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						});

					}

				});

				th.start();
			} else {
				Image image = new Image(Main.class.getResourceAsStream("/com/training/resource/icons/user128.png"),
						128, 128, false, false);
				fotoImage.setImage(image);
			}

			if (data.getLebenslauf() != null) {

				try {

					byte[] byteData = data.getLebenslauf();
					File file = new File(dir + File.separator + "lebenslauf.pdf");

					FileOutputStream fos = new FileOutputStream(file);
					fos.write(byteData);
					fos.close();

					lebenslaufFile = file;

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

			ObservableList<Abteilung> abteilungen = FXCollections
					.observableArrayList(Service.getInstance().getAbteilungService().findAbteilungenWithStandort());
			abteilungComboBox.setItems(abteilungen);
			abteilungComboBox.setConverter(new StringConverter<Abteilung>() {

				@Override
				public Abteilung fromString(String string) {
					return null;
				}

				@Override
				public String toString(Abteilung object) {
					return object.getName() + " / " + object.getStandort().getName();
				}

			});
			abteilungComboBox.getSelectionModel().select(data.getAbteilung());

		} else {
			clearFields();

		}

	}

	@FXML
	private void handleDelete() {

		fotoFile = null;

		Image image = new Image(Main.class.getResourceAsStream("/com/training/resource/icons/user128.png"), 128, 128,
				false, false);
		fotoImage.setImage(image);

	}

	private void clearFields() {

		vornameField.setText("");
		nachnameField.setText("");
		persnrField.setText("");
		telephoneField.setText("");
		taetigkeitField.setText("");
		abteilungComboBox.setItems(null);
		abteilungComboBox.getSelectionModel().select(null);
		fotoImage.setImage(null);
	}

	public void setEditable(boolean editable) {

		vornameField.setDisable(!editable);
		nachnameField.setDisable(!editable);
		persnrField.setDisable(!editable);
		telephoneField.setDisable(!editable);
		taetigkeitField.setDisable(!editable);
		abteilungComboBox.setDisable(!editable);
		fotoUploadButton.setDisable(!editable);
		fotoDeleteButton.setDisable(!editable);
		lebenslaufUploadBtn.setDisable(!editable);
		lebenslaufViewBtn.setDisable(!editable);
		fotoImage.setDisable(!editable);

		if (data != null)
			if (data.getLebenslauf() != null)
				lebenslaufViewBtn.setDisable(editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (vornameField.getText() == null || vornameField.getText().length() == 0)
			text += "Kein gültiger Vorname!\n";
		if (nachnameField.getText() == null || nachnameField.getText().length() == 0)
			text += "Kein gültiger Nachname!\n";
		if (persnrField.getText() == null || persnrField.getText().length() == 0)
			text += "Keine gültige Personalnummer!\n";
		if (taetigkeitField.getText() == null || taetigkeitField.getText().length() == 0)
			text += "Keine gültige Tätigkeit!\n";
		if (abteilungComboBox.getSelectionModel().getSelectedItem() == null)
			text += "Keine gültige Abteilung!\n";

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
