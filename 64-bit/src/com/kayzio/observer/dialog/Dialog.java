package com.kayzio.observer.dialog;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dialog {
	public static void dialog(Alert.AlertType type, String title, String headerText, String content){
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);

		alert.showAndWait();
	}
}