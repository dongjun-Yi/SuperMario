package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int width, height;
	
	protected double x, y;
	
	protected int animationIdx = 0;
	protected int frameCount = 0;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public void setWidth(int width) { 
		this.width = width;
	}
	
	public void setHeight(int height) { 
		this.height = height;
	}
	
	public abstract BufferedImage getCurrentImage();
	public abstract void draw(Graphics2D g2);
}
