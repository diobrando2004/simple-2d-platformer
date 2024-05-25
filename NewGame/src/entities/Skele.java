package entities;

//ihm
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstant.ATTACK;
import static utilz.Constants.EnemyConstant.HIT;
import static utilz.Constants.EnemyConstant.IDLE;
import static utilz.Constants.EnemyConstant.RUNNING;
import static utilz.Constants.EnemyConstant.SKELE;
import static utilz.Constants.EnemyConstant.SKELE_HEIGHT;
import static utilz.Constants.EnemyConstant.SKELE_WIDTH;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Skele extends Enemy {
	private int attackboxOffsetX;

	public Skele(float x, float y) {
		super(x, y, SKELE_WIDTH, SKELE_HEIGHT, SKELE);
		inithitbox((int) 24 * Game.scale, (int) 28 * Game.scale);
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (84 * Game.scale), (int) (28 * Game.scale));
		attackboxOffsetX = (int) (Game.scale * 30);

	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackboxOffsetX;
		attackBox.y = hitbox.y - 16;

	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if (inAir) {
			updateInAir(lvlData);
		} else {
			switch (state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
					if (isPlayerCloseForAttack(player))
						newState(ATTACK);
				}
				move(lvlData);
				break;
			case ATTACK:
				if (AniIndex == 0)
					attackChecked = false;
				if (AniIndex == 4 || AniIndex == 5 || AniIndex == 8 || AniIndex == 9 && !attackChecked)
					checkEnemyHit(attackBox, player);
				break;
			case HIT:

				break;
			}
		}
	}

	public int flipX() {
		if (walkDir == RIGHT)
			return -128;
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
