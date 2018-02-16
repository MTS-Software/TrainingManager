package com.training.db.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.training.util.Constants;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Service {

	private static Service instance;

	private static final Logger logger = Logger.getLogger(Service.class);

	private boolean errorStatus = false;

	private AbteilungService abteilungService;
	private AnlageService anlageService;
	private HerstellerService herstellerService;
	private LevelService levelService;
	private MitarbeiterService mitarbeiterService;
	private SchulungService schulungService;
	private StandortService standortService;
	private StatusService statusService;
	private ProduktService produktService;
	private KategorieService kategorieService;

	private Service() {

		abteilungService = new AbteilungService();
		anlageService = new AnlageService();
		herstellerService = new HerstellerService();
		levelService = new LevelService();
		mitarbeiterService = new MitarbeiterService();
		schulungService = new SchulungService();
		standortService = new StandortService();
		statusService = new StatusService();
		produktService = new ProduktService();
		kategorieService = new KategorieService();

	}

	public synchronized static Service getInstance() {

		if (instance == null) {
			instance = new Service();
		}

		return instance;

	}

	public AbteilungService getAbteilungService() {
		return abteilungService;
	}

	public AnlageService getAnlageService() {
		return anlageService;
	}

	public HerstellerService getHerstellerService() {
		return herstellerService;
	}

	public LevelService getLevelService() {
		return levelService;
	}

	public MitarbeiterService getMitarbeiterService() {
		return mitarbeiterService;
	}

	public SchulungService getSchulungService() {
		return schulungService;
	}

	public StandortService getStandortService() {
		return standortService;
	}

	public ProduktService getProduktService() {
		return produktService;
	}

	public KategorieService getKategorieService() {
		return kategorieService;
	}

	public StatusService getStatusService() {
		return statusService;
	}

	public boolean isErrorStatus() {
		return errorStatus;
	}

	private void showExceptionMessage(Exception e) {
		errorStatus = true;
		showExceptionAlertDialog(e);
	}

	private void showExceptionAlertDialog(Exception e) {

		errorStatus = true;

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setContentText(e.getMessage() + "\nException: " + e.getClass().getName());
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().addAll(getClass().getResource(Constants.STYLESHEET).toExternalForm());

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was: ");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Constants.APP_ICON));

		alert.showAndWait();

	}

}
