package com.kayzio.observer.model;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import com.kayzio.observer.dialog.Dialog;

public class DLLCall{
	static{
		try{
			System.loadLibrary("cant_touch_this");
		}catch(UnsatisfiedLinkError e){
			e.printStackTrace();
			Platform.runLater(new Runnable(){
				public void run(){
					Dialog.dialog(AlertType.WARNING, "DLL ERROR", null, "Could not load dll.");
					System.exit(-1);
				}
			});	
		}
	}
	// Initialize everything (looks for the active window first with while loop)
	// (return 0 if OKAY)
	private native int functionF(int z); 
	// Check if window is active (return 0 if OKAY)
	private native int functionF(int z, int a);
	// Close handles
	private native void functionF(int z, int a, int b);
	// Get current zoom value
	private native int functionF(int z, int a, int b, int c);
	// GET CROSSHAIR ID
	private native int functionF(int z, int a, int b, int c, int d);

	// The main properties of the Singleton design for a object
	private static DLLCall instance = new DLLCall();
	private DLLCall(){}
	public static DLLCall getInstance(){ return instance; }

	public int getWindowAndInitEverything(){
		return functionF(0);
	}
	public int checkForWindow(){
		return functionF(0, 0);
	}
	public void closeHandles(){
		functionF(0, 0, 0);
	}
	public int getIsAimingAtEnemy(){
		return functionF(0, 0, 0, 0);
	}
	public int getCrosshairID(){
		return functionF(0, 0, 0, 0, 0);
	}

}