package com.kayzio.observer.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

public class CheckForGameObserver implements Observer {

	private BooleanProperty isGameRunning = new SimpleBooleanProperty();
	private Subject subject;

	public BooleanProperty getIsGameRunningProperty(){
		return isGameRunning;
	}

	@Override
	public void update(){
		isGameRunning.set((boolean)subject.getUpdate());
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