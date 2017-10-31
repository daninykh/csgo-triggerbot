package com.kayzio.observer.view;

import java.util.ArrayList;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.geometry.Pos;

import com.kayzio.observer.view.control.*;
import com.kayzio.observer.Observer;
import com.kayzio.observer.Subject;

import org.jnativehook.keyboard.NativeKeyEvent;

public class View {

	/*---- Objects ----*/
	// CHECK GAME SHIT
	private CheckForGameObserver gameObserver;
	private TriggerBotSubject triggerBotSubject;

	// Application dimensions for node calculations
	private static final int WINDOW_WIDTH = 200;
	private static final int WINDOW_HEIGHT = 60;
	private static final int EDGE_MARGIN = 10;

	// Lol Simple Main Layout
	StackPane root;
	// Lol Simple Before Found Game Layouts
	VBox beforeNodesPane;
	// Lol Simple After Found Game Layouts
	VBox afterNodesPane;

	// Lol Simple Scene
	private Scene mainScene;

	/*	more view controls will be added
		once the basic functionality and
		design pattern is completed.	
	*/
	// Lol Simple No Window Open Label
	private WaitingForGameNode waitingForGameNode;
	// Lol Simple Controls
	private TriggerBotNode triggerBotNode;

	public View(){
		gameObserver = new CheckForGameObserver();
		triggerBotSubject = new TriggerBotSubject();

		triggerBotNode = new TriggerBotNode(WINDOW_WIDTH, WINDOW_HEIGHT, EDGE_MARGIN, NativeKeyEvent.VC_F1);

		initListeners();
	}

	public void initGui(Stage mainStage){
		root = new StackPane();

		beforeNodesPane = new VBox();
		beforeNodesPane.setAlignment(Pos.CENTER);
		afterNodesPane = new VBox();
		afterNodesPane.setAlignment(Pos.CENTER);

		initNodes();

		mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		root.getChildren().addAll(beforeNodesPane, afterNodesPane);

		mainStage.setTitle(getRandomTitle());
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		mainStage.sizeToScene();
		mainStage.show();

	}

	// INITIALIZE SHIT
	private void initNodes(){
		initWaitingForGameNode();
		initTriggerBotNode();

		setBeforeGameNodesVisible(true);
		setAfterGameNodesVisible(false);
	}
	private void initListeners(){
		gameObserver.getIsGameRunningProperty().addListener(new ChangeListener(){
			public void changed(ObservableValue obs, Object oldVal, Object newVal){
				Platform.runLater(new Runnable(){
					public void run(){
						if((boolean)newVal){
							setBeforeGameNodesVisible(false);
							setAfterGameNodesVisible(true);
						}else{
							setBeforeGameNodesVisible(true);
							setAfterGameNodesVisible(false);
						}
					}
				});
			}
		});
		triggerBotNode.getIsNodeDisabledProperty().addListener(new ChangeListener(){
			public void changed(ObservableValue obs, Object oldVal, Object newVal){
				Platform.runLater(new Runnable(){
					public void run(){
						triggerBotSubject.setBooleanProperty((boolean)newVal);
					}
				});
			}
		});
	}

	//RANDOM TITLE
	private String getRandomTitle(){
		String[] randomValues = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 30; i++){
			builder.append(randomValues[rand.nextInt(randomValues.length)]);
		}
		return builder.toString();
	}

	// SET VISIBILITY
	private void setBeforeGameNodesVisible(boolean bool){
		beforeNodesPane.setVisible(bool);
	}
	private void setAfterGameNodesVisible(boolean bool){
		afterNodesPane.setVisible(bool);
	}

	// INIT NODES
	public void initWaitingForGameNode(){
		waitingForGameNode = new WaitingForGameNode(WINDOW_WIDTH, WINDOW_HEIGHT, EDGE_MARGIN);
		beforeNodesPane.getChildren().add(waitingForGameNode);
	}
	public void initTriggerBotNode(){ 
		afterNodesPane.getChildren().add(triggerBotNode);
	}

	// RETURN OBJECTS
	public Observer getCheckForGameObserver(){
		return gameObserver;
	}
	public Subject getTriggerBotSubject(){
		return triggerBotSubject;
	}
	public Observer getTriggerBotNode(){
		return triggerBotNode;
	}

}