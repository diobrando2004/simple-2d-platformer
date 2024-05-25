package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import static utilz.Constants.Direction.*;
import static utilz.HelpMethod.*;
public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int AniTick, AniIndex = 0;
	protected int state;
	protected float airSpeed = 0f;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth;
	protected Rectangle2D.Float attackBox;
	protected float WalkSpeed;
	
	protected int pushBackDir;
	protected float pushDrawOffset;
	protected int pushBackOffsetDir = UP;
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	protected void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - lvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}
	protected void drawHitbox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}
	protected void inithitbox(float width, float height) {
		hitbox = new Rectangle2D.Float((int) x, (int) y, width, height);

	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	public int GetState() {
		return state;
	}
	public int getAniIndex() {
		return AniIndex;
	}
	protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
		float xSpeed = 0;
		if (pushBackDir == LEFT)
			xSpeed = -WalkSpeed;
		else
			xSpeed = WalkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed * speedMulti, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed * speedMulti;
	}
	protected void updatePushBackDrawOffset() {
		float speed = 0.95f;
		float limit = -30f;

		if (pushBackOffsetDir == UP) {
			pushDrawOffset -= speed;
			if (pushDrawOffset <= limit)
				pushBackOffsetDir = DOWN;
		} else {
			pushDrawOffset += speed;
			if (pushDrawOffset >= 0)
				pushDrawOffset = 0;
		}
	}
	
}
