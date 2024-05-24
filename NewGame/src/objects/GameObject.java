package objects;

import static utilz.Constants.aniSpeed;
import static utilz.Constants.ObjectConstants.ENDING_KEY;
import static utilz.Constants.ObjectConstants.getSpriteAmount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class GameObject {
	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;

	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(objType)) {
				aniIndex = 0;
				if (objType == ENDING_KEY) {
					doAnimation = false;
				} else {
					doAnimation = true;
				}
			}
		}
	}

	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;
		doAnimation = true;
	}

	protected void inithitbox(float width, float height) {
		hitbox = new Rectangle2D.Float((int) x, (int) y, width, height);

	}

	public void drawHitbox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniIndex() {
		return aniIndex;
	}

}
