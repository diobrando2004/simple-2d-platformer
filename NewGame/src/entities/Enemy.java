package entities;

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

import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Enemy extends Entity {
	protected int aniIndex, enemyState = 3, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir = false;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.scale;
	protected float walkSpeed = 0.1f * Game.scale;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected int maxHealth;
	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;

	public Enemy(float x, float y, float width, float height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		inithitbox(x, y, width, height);
		maxHealth = getMaxHealth(enemyType);
		currentHealth = maxHealth;
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
			xSpeed = -walkSpeed;
		else
			xSpeed = +walkSpeed;
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (isFloor(hitbox, xSpeed, lvlData, walkDir)) {
				hitbox.x += xSpeed;
				return;
			}
		changeWalkDir();
	}

	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloorEnemy(hitbox, fallSpeed);
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
		}
	}

	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}

	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitbox))
			player.changeHealth(-getEnemyDmg(enemyType));
		attackChecked = true;

	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				if (enemyState == ATTACK)
					enemyState = IDLE;
				else
//					if (enemyState == HIT) {
//					enemyState = IDLE;
//				}
//
//				else
				if (enemyState == DEAD)
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
		int playerTileY = (int) player.getHitbox().y / Game.TILES_SIZE;
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
		fallSpeed = 0;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getEnemyState() {
		return enemyState;
	}

	public boolean isActive() {
		return active;
	}
}
