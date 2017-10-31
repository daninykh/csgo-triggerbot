package com.kayzio.observer.view;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.*;

import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

public class BunnyHopSubject implements Subject{

	private BooleanProperty booleanProperty = new SimpleBooleanProperty();
	private ArrayList<Observer> observerList;

	public BunnyHopSubject(){
		observerList = new ArrayList<Observer>();

		booleanProperty.addListener(new ChangeListener(){
			public void changed(ObservableValue obs, Object oldVal, Object newVal){
				notifyObservers();
			}
		});
	}

	public void setBooleanProperty(boolean bool){
		booleanProperty.set(bool);
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
	}
	@Override
	public Object getUpdate(){
		return booleanProperty.get();
	}

}