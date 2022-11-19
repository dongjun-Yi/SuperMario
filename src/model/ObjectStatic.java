package model;

public abstract class ObjectStatic extends GameObject {

	protected boolean isTouched = false;

	public ObjectStatic(double x, double y) {
		super(x, y);
	}

	public abstract int touch();
}
