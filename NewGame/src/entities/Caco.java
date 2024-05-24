package entities;

import static utilz.Constants.EnemyConstant.CACO;
import static utilz.Constants.EnemyConstant.CACO_ATTACK;
import static utilz.Constants.EnemyConstant.CACO_HEIGHT;
import static utilz.Constants.EnemyConstant.CACO_HIT;
import static utilz.Constants.EnemyConstant.CACO_IDLE;
import static utilz.Constants.EnemyConstant.CACO_WIDTH;

import main.Game;

public class Caco extends Enemy {

	public Caco(float x, float y) {
		super(x, y, CACO_WIDTH, CACO_HEIGHT, CACO);
		inithitbox(x, y, (int) (49 * Game.scale), (int) (39 * Game.scale));

	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
//		updateAttackBox();
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if (inAir) {
			updateInAir(lvlData);
		} else {
			switch (enemyState) {
			case CACO_IDLE:
				if (canSeePlayer(lvlData, player))
					turnTowardsPlayer(player);
				if (isPlayerCloseForAttack(player))
					newState(CACO_ATTACK);
				move(lvlData);
				break;
//			case ATTACK:
//				if (aniIndex == 0)
//					attackChecked = false;
//				if (aniIndex == 4 || aniIndex == 5 || aniIndex == 8 || aniIndex == 9 && !attackChecked)
//					checkEnemyHit(attackBox, player);
//				break;
			case CACO_HIT:

				break;
			}
		}
	}
}
