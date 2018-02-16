package com.training.view.root;

import java.net.URL;
import java.util.ResourceBundle;

import com.training.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author Markus Thaler
 */
public class HomeScreenController implements Initializable {

	private ResourceBundle resources;

	private Main main;
	private Stage dialogStage;

	@FXML
	ImageView image;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public HomeScreenController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.resources = resources;

		image.fitWidthProperty().bind(dialogStage.widthProperty());

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;

	}

}