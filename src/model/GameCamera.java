package model;

import main.GameSettings;

public class GameCamera {
	private double x, y;
	private int mapWidthBoundary;
	
	public GameCamera(int mapWidthBoundary) { 
		x = y = 0.0; 
		this.mapWidthBoundary = mapWidthBoundary;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	
	public void setWidthBoundary(int mapWidthBoundary) {
		this.mapWidthBoundary = mapWidthBoundary;
	}
	
	public void moveX(double x) {
		this.x = x;
		if(x <= 0)
			this.x = 0;
		if(x >= this.mapWidthBoundary - GameSettings.screenWidth)
			this.x = mapWidthBoundary - GameSettings.screenWidth;
	}
}
