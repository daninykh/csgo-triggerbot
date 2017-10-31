package com.kayzio.observer.view.control;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.*;
import javafx.geometry.Pos;


import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

public class BunnyHopNode extends HBox implements Observer{

	private Subject subject;

	// For making calculations on the dimensions of our controls
	private int windowWidth, windowHeight, edgeMargin;
	private static final int HEALTH_BAR_HEIGHT = 25;

	private int disableKey;
	public static final String ENABLE = "Enabled";
	public static final String DISABLE = "Disabled";
	private BooleanProperty isNodeDisabledProperty = new SimpleBooleanProperty(true);

	// MAIN CONTROLS / NODES
	private Label titleLabel;
	private Label statusLabel;

	public BunnyHopNode(int windowWidth, int windowHeight, int edgeMargin, int keyCode){
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.edgeMargin = edgeMargin;
		disableKey = keyCode;
		
		titleLabel = new Label("BunnyHop - ");
		statusLabel = new Label(DISABLE);
		statusLabel.setTextFill(Color.RED);

		setMaxWidth(windowWidth - edgeMargin);
		setAlignment(Pos.CENTER);
		getChildren().addAll(titleLabel, statusLabel);
	}

	public void setStatus(String status, Color color){
		statusLabel.setText(status);
		statusLabel.setTextFill(color);
	}
	public String getStatus(){
		return statusLabel.getText();
	}

	public BooleanProperty getIsNodeDisabledProperty(){
		return isNodeDisabledProperty;
	}
	public boolean isNodeDisabled(){
		return isNodeDisabledProperty.get();
	}

	@Override
	public void update(){
		Platform.runLater(new Runnable(){
			public void run(){
				int keyCode = (int)subject.getUpdate();
				if(keyCode == disableKey){
					if(getStatus().equals(ENABLE)){
						setStatus(DISABLE, Color.RED);
						isNodeDisabledProperty.set(true);
					}else{
						setStatus(ENABLE, Color.GREEN);
						isNodeDisabledProperty.set(false);
					}
				}
			}
		});
	}
	@Override
	public void setSubject(Subject subject){
		this.subject = subject;
	}
	@Override 
	public Subject getSubject(){
		return subject;
	}

}