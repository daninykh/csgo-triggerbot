package com.kayzio.observer.view.control;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.beans.value.*;
import javafx.geometry.Pos;

public class WaitingForGameNode extends VBox {

	// DIMENSIONS FOR NODE CALCULATIONS
	private int windowWidth, windowHeight, edgeMargin;

	// MAIN COMPONENTS / NODES
	private Label gameNotFoundLabel1;
	private Label gameNotFoundLabel2;
	private Label gameNotFoundLabel3;

	public WaitingForGameNode(int windowWidth, int windowHeight, int edgeMargin){
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.edgeMargin = edgeMargin;

		gameNotFoundLabel1 = new Label("---------------------------------");
		gameNotFoundLabel2 = new Label("  Waiting for CS:GO  ");
		gameNotFoundLabel3 = new Label("---------------------------------");
		//gameNotFoundLabel1.setTextFill(Color.RED);
		gameNotFoundLabel2.setTextFill(Color.RED);
		//gameNotFoundLabel3.setTextFill(Color.RED);


		setMaxWidth(windowWidth - edgeMargin);
		setAlignment(Pos.CENTER);
		getChildren().addAll(gameNotFoundLabel1, gameNotFoundLabel2, gameNotFoundLabel3);
	}

}