package entities;

import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstant.CACO;
import static utilz.Constants.EnemyConstant.CACO_ATTACK;
import static utilz.Constants.EnemyConstant.CACO_HEIGHT;
import static utilz.Constants.EnemyConstant.CACO_HIT;
import static utilz.Constants.EnemyConstant.CACO_IDLE;
import static utilz.Constants.EnemyConstant.CACO_WIDTH;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Caco extends Enemy {
	private int attackboxOffsetX;

	public Caco(float x, float y) {

		super(x, y, CACO_WIDTH, CACO_HEIGHT, CACO);
		WalkSpeed = 0.5f * Game.scale;
		inithitbox((int) 39 * Game.scale, (int) 30 * Game.scale);
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) 45 * Game.scale, (int) 30 * Game.scale);
		attackboxOffsetX = (int) (Game.scale * 5);

	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTickCACO();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackboxOffsetX;
		attackBox.y = hitbox.y;

	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if (inAir) {
			updateInAir(lvlData);
		} else {
			switch (state) {
			case CACO_IDLE:
				if (canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);

					if (isPlayerCloseForAttack(player))
						newState(CACO_ATTACK);
				}
				move(lvlData);
				break;
			case CACO_ATTACK:
				if (AniIndex == 0)
					attackChecked = false;
				if (AniIndex >= 0 && AniIndex <= 5 && !attackChecked)
					checkEnemyHit(attackBox, player);
				break;
			case CACO_HIT:

				break;
			}
		}
	}

	public int flipX() {
		if (walkDir == RIGHT)
			return -100;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == RIGHT)
			return 1;
		else
			return -1;
	}
}
