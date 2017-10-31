package com.kayzio.observer.model;

import java.lang.Thread;

import com.kayzio.observer.Observer;
import com.kayzio.observer.Subject;

public class Model implements Runnable{

	// THREAD
	private Thread modelThread;
	
	// GAME CHECKER
	private CheckForGame checkForGame;
	// MAIN TRIGGER
	private TriggerBot triggerBot;
	// GLOBAL KEY LISTENER
	private GlobalKeyListener globalKeyListener;

	public Model(){
		checkForGame = new CheckForGame();
		globalKeyListener = new GlobalKeyListener();
		triggerBot = new TriggerBot();

		modelThread = new Thread(this);
	}

	// START THREAD
	public void startModelThread(){
		modelThread.start();
	}

	// RETURN MODEL OBJECTS
	public Subject getCheckForGame(){
		return checkForGame;
	}
	public Subject getGlobalKeyListener(){
		return globalKeyListener;
	}
	public Observer getTriggerBot(){
		return triggerBot;
	}

	@Override
	public void run(){
		checkForGame.getWindowAndInitEverything();
		while(true){
			checkForGame.calculateGameRoute();
			if(checkForGame.getIsGameRunning()){
				triggerBot.runTriggerBot();
			}
		}
	}

}	