package com.training.view.alert;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class InputValidatorAlert extends Alert {

	public InputValidatorAlert(Stage dialogStage, String text) {

		super(AlertType.INFORMATION);

		initOwner(dialogStage);

		setTitle("Daten ung�ltig");
		setHeaderText("Es wurden nicht alle ben�tigten Daten eingegeben.");
		setContentText(text);
	}

}
