package com.kayzio.observer.model;

import java.lang.Thread;
import java.util.ArrayList;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.*;

import com.kayzio.observer.dialog.Dialog;
import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

public class CheckForGame implements Subject{

	private ArrayList<Observer> observerList;
	private DLLCall dllCall;
	private BooleanProperty isGameRunning = new SimpleBooleanProperty();

	public CheckForGame(){ 
		observerList = new ArrayList<Observer>();
		dllCall = DLLCall.getInstance();
		
		initListeners();
	}

	public Boolean getIsGameRunning(){
		return isGameRunning.get();
	}

	private void initListeners(){
		isGameRunning.addListener(new ChangeListener(){
			public void changed(ObservableValue obs, Object oldVal, Object newVal){
				notifyObservers();
			}
		});
	}

	// (not a good name for this method, BTW. I just got tired.)
	public void calculateGameRoute(){
		int currentStatus = dllCall.checkForWindow();
		if(currentStatus == 0){
			isGameRunning.set(true);
			//System.out.println("Window is still open.");
		}else{
			isGameRunning.set(false);
			//System.out.println("Window is not open.");
			dllCall.closeHandles();
			getWindowAndInitEverything();
		}
	}
	public void getWindowAndInitEverything(){
		int returnVal = dllCall.getWindowAndInitEverything();
		if(returnVal == 0){
			isGameRunning.set(true);
		}else{
			Dialog.dialog(AlertType.WARNING, "FAILED TO INITIALIZE", null, "Failed to initialize the window handle in C++... Please retry.");
			System.exit(-1);
		}
	}

	@Override
	public void register(Observer observer){
		if(!observerList.contains(observer)){
			observerList.add(observer);
		}
	}
	@Override
	public void unregister(Observer observer){
		observerList.remove(observer);
	}
	@Override
	public void notifyObservers(){
		for(Observer observer: observerList){
			observer.update();
		}
		System.out.println("Check For Game >> Updated observers");
	}
	@Override
	public Object getUpdate(){
		return getIsGameRunning();
	}

}