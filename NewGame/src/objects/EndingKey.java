package objects;

import main.Game;

public class EndingKey extends GameObject {

	public EndingKey(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		inithitbox(9, 16);
		xDrawOffset = (int) (4 * Game.scale);
		yDrawOffset = (int) (0 * Game.scale);
	}

	public void update() {
		updateAnimationTick();
	}

}
