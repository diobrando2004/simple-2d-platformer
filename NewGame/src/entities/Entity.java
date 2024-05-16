package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;

	}

	protected void drawHitbox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void inithitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float((int) x, (int) y, width, height);

	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
}
