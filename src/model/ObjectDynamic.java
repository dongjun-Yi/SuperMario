package model;

public abstract class ObjectDynamic extends GameObject {

	protected int mapWidthBoundary;
	
	protected double xRightVel = 0;
	protected double xLeftVel = 0;
	protected double yVel = 0;
	protected int maxSpeed;
	
	protected int direction = 0;	// 0 : right 1 : left
	protected int jumpDir = 0;
	
	protected boolean isJump = false;
	protected boolean hasCollision = true;
	protected boolean hasGravity = true;
	
	public ObjectDynamic(double x, double y, int mapWidthBoundary) {
		super(x, y);
		this.mapWidthBoundary = mapWidthBoundary;
	}

	public boolean hasCollision() { return hasCollision; }
	
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void initVelocity() {
		xRightVel = 0.0;
		xLeftVel = 0.0;
		yVel = 0.0;
	}
	
	public void updatesCoordinate() {
		x += (xRightVel + xLeftVel);
		y += yVel;
		
		// gravity
		if(hasGravity)
			yVel += 0.65;	
		// grounded	충돌 구현하면 수정될 부분
		if(hasCollision && y + height >= 625.0) {
			isJump = false;
			y = 625.0 - height;
			yVel = 0.0;
		}
		// map boundary
		if(x <= 0)
			this.x = 0;
		if(x >= this.mapWidthBoundary - width)
			this.x = mapWidthBoundary - width;
	}
	
	public abstract void move();
}
