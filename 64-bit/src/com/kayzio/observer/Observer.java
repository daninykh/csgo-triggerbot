package com.kayzio.observer;

public interface Observer {
	public void update();
	public void setSubject(Subject subject);
	public Subject getSubject();
}