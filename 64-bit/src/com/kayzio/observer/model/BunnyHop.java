package com.kayzio.observer.model;

import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.*;

import com.kayzio.observer.dialog.Dialog;
import com.kayzio.observer.Subject;
import com.kayzio.observer.Observer;

public class BunnyHop implements Observer{

	private boolean isDisable;
	private Robot robot;
	private DLLCall dllCall;
	private Subject subject;

	public BunnyHop(){
		try{
			robot = new Robot();
		}catch(Exception e){
			Platform.runLater(new Runnable(){
				public void run(){
					Dialog.dialog(AlertType.WARNING, "AWTException", null, "Robot could not be instantiated... exiting.");
					System.exit(-1);
				}
			});
		}	
		dllCall = DLLCall.getInstance();
	}

	public void runBunnyHop(){
		synchronized(this){
			if(!isDisable){
				int returnValue = dllCall.getJumpingValue();
				if(returnValue==1){
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.delay(35);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				}
			}
		}
	}

	@Override
	public void update(){
		isDisable = (boolean)subject.getUpdate();
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