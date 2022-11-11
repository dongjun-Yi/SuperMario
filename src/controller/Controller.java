package controller;

import java.awt.event.KeyListener;

public interface Controller extends KeyListener {
	public void initKey();
	public boolean getUpPressed();
	public boolean getDownPressed();
	public boolean getLeftPressed();
	public boolean getRightPressed();
	public boolean getSpacePressed();
}
