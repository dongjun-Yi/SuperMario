package view;

import java.awt.Graphics2D;

public interface GameStatusView {
	public abstract void updates();
	public abstract void draw(Graphics2D g);
}
