package objects;

import main.Game;

public class Heart extends GameObject {
	private float hoverOffset;
	private int maxHoverOffset, hoverDir = 1;

	public Heart(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		inithitbox(16, 16);
		xDrawOffset = (int) (0 * Game.scale);
		yDrawOffset = (int) (0 * Game.scale);
		maxHoverOffset = (int) (10 * Game.scale);

	}

	public void update() {
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset += (0.075f * Game.scale * hoverDir);
		if (hoverOffset >= maxHoverOffset) {
			hoverDir = -1;
		} else if (hoverOffset < 0) {
			hoverDir = 1;
		}
		hitbox.y = y + hoverOffset;
	}

}
