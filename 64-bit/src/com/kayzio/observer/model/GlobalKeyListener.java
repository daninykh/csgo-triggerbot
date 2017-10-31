package com.kayzio.observer.model;

import java.util.ArrayList;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.*;

import com.kayzio.observer.model.DLLCall;
import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements Subject, NativeKeyListener{

	private int keyCode;
	private ArrayList<Observer> observerList;

	public GlobalKeyListener(){
		observerList = new ArrayList<Observer>();
		GlobalScreen.addNativeKeyListener(this);
	}

	private int getKeyCode(){
		return keyCode;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e){
		keyCode = e.getKeyCode();
		notifyObservers();
	}
	@Override
	public void nativeKeyReleased(NativeKeyEvent e){}
	@Override
	public void nativeKeyTyped(NativeKeyEvent e){}

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
		return getKeyCode();
	}

}