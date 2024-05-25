package entities;

import static utilz.Constants.aniSpeed;
import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstant.ATTACK;
import static utilz.Constants.EnemyConstant.DEAD;
import static utilz.Constants.EnemyConstant.HIT;
import static utilz.Constants.EnemyConstant.IDLE;
import static utilz.Constants.EnemyConstant.getEnemyDmg;
import static utilz.Constants.EnemyConstant.getMaxHealth;
import static utilz.Constants.EnemyConstant.getSpriteAmount;
import static utilz.HelpMethod.CanMoveHere;
import static utilz.HelpMethod.GetEntityYPosUnderRoofOrAboveFloorEnemy;
import static utilz.HelpMethod.IsEntityOnFloor;
import static utilz.HelpMethod.IsSightClear;
import static utilz.HelpMethod.isFloor;
import static utilz.Constants.gravity;
import static utilz.Constants.EnemyConstant.*;

import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Enemy extends Entity {
	protected int enemyType;	
	protected boolean firstUpdate = true;
//hh

	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = getMaxHealth(enemyType);
		currentHealth = maxHealth;
		WalkSpeed = 0.1f * Game.scale;
	}

	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
		firstUpdate = false;
	}

	protected void move(int[][] lvlData) {
		float xSpeed = 0;
		if (walkDir == LEFT)
			xSpeed = -WalkSpeed;
		else
			xSpeed = +WalkSpeed;
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (isFloor(hitbox, xSpeed, lvlData, walkDir)) {
				hitbox.x += xSpeed;
				return;
			}
		changeWalkDir();
	}

	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloorEnemy(hitbox, airSpeed);
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
		}
	}

	protected void newState(int enemyState) {
		this.state = enemyState;
		AniTick = 0;
		AniIndex = 0;
	}

	public void hurt(int amount) {
		if(enemyType == SKELE) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
		}
		else if(enemyType== CACO) {
			currentHealth -= amount;
			if (currentHealth <= 0)
				newState(CACO_DEAD);
			else
				newState(CACO_HIT);
			}
		}

	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitbox)) {
			player.changeHealth(-getEnemyDmg(enemyType));
		}
		attackChecked = true;

	}

	protected void updateAnimationTick() {
		AniTick++;
		if (AniTick >= aniSpeed) {
			AniTick = 0;
			AniIndex++;
			if (AniIndex >= getSpriteAmount(enemyType, state)) {
				AniIndex = 0;
				if (state == ATTACK)
					state = IDLE;
				else if (state == HIT) {
					state = IDLE;
				} else if (state == DEAD)
					active = false;
			}
		}
	}
	protected void updateAnimationTickCACO() {
		AniTick++;
		if (AniTick >= aniSpeed) {
			AniTick = 0;
			AniIndex++;
			if (AniIndex >= getSpriteAmount(enemyType, state)) {
				AniIndex = 0;
				if (state == CACO_ATTACK)
					state = CACO_IDLE;
				else if (state == CACO_HIT) {
					state = CACO_IDLE;
				} else if (state == CACO_DEAD)
					active = false;
			}
		}
	}

	protected void turnTowardsPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
		if (playerTileY + 1 == tileY) {
			if (isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
					return true;
			}
		}
		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 10;
	}

	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;

	}

	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}


	public boolean isActive() {
		return active;
	}
}
