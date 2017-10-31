package com.kayzio.observer;

import com.kayzio.observer.dialog.Dialog;
import com.kayzio.observer.model.*;
import com.kayzio.observer.view.*;

import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main extends Application{

	private DLLCall dllCall = DLLCall.getInstance();
	private View view;
	private Model model;

	public Main(){
		view = new View();
		model = new Model();

		// IS GAME ACTIVE
		model.getCheckForGame().register(view.getCheckForGameObserver());
		view.getCheckForGameObserver().setSubject(model.getCheckForGame());

		// SHOULD I ACTIVATE TRIGGER BOT?
		model.getGlobalKeyListener().register(view.getTriggerBotNode());
		view.getTriggerBotNode().setSubject(model.getGlobalKeyListener());

		// RUN TRIGGER BOT IF ACTIVE
		model.getTriggerBot().setSubject(view.getTriggerBotSubject());
		view.getTriggerBotSubject().register(model.getTriggerBot());

		// START CHECKING B1CH
		model.startModelThread();
	}

	@Override
	public void start(Stage stage){
		view.initGui(stage);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent e){
				try{
					dllCall.closeHandles();
				}catch(UnsatisfiedLinkError ex){
					Platform.runLater(new Runnable(){
						public void run(){
							Dialog.dialog(AlertType.WARNING, "UnsatisfiedLinkError", null, "Could not close handles from DLL call.");
							System.exit(-1);
						}
					});
				}
				System.out.println("Handles Closed.");
				System.exit(0);
			}
		});
	}

	public static void main(String[] args){
		try{
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);

			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e){
			Platform.runLater(new Runnable(){
				public void run(){
					Dialog.dialog(AlertType.WARNING, "NativeHookExcepion", null, "Problem registering the native hook.");
					System.exit(-1);
				}
			});
		}

		Application.launch(Main.class, args);
	}	

}