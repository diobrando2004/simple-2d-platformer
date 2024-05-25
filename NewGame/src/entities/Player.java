package entities;

import static utilz.Constants.aniSpeed;
import static utilz.Constants.gravity;
import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.PlayerConstants.Falling;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.HIT;
import static utilz.Constants.PlayerConstants.Idle;
import static utilz.Constants.PlayerConstants.Jumping;
import static utilz.Constants.PlayerConstants.attack;
import static utilz.Constants.PlayerConstants.running;
import static utilz.Constants.PlayerConstants.walkattack;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethod.CanMoveHere;
import static utilz.HelpMethod.GetEntityXPosNextToWall;
import static utilz.HelpMethod.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethod.IsEntityOnFloor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage image, image1, image2;
	private BufferedImage[][] animations = new BufferedImage[15][8];
	private boolean left, right, jump;
	private boolean moving = false, attacking = false;
	private boolean knockback = false;
	int knockbakccounter = 0;
	private int[][] lvlData;
	private float xDrawOffset = 6 * Game.scale;
	private float yDrawOffset = 6 * Game.scale;
	// ff

	private float jumpSpeed = -2.25f * Game.scale;
	private float fallSpeedAfterCollision = 0.5f * Game.scale;
	// status bar
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * Game.scale);
	private int statusBarHeight = (int) (58 * Game.scale);
	private int statusBarX = (int) (10 * Game.scale);
	private int statusBarY = (int) (10 * Game.scale);

	private int healthBarWidth = (int) (150 * Game.scale);
	private int healthBarHeight = (int) (4 * Game.scale);
	private int healthBarXStart = (int) (34 * Game.scale);
	private int healthBarYStart = (int) (14 * Game.scale);

	private int healthWidth = healthBarWidth;
	// attack box
	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	private Playing playing;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.maxHealth = 2500;
		this.currentHealth = maxHealth;
		this.state = Idle;
		this.WalkSpeed = 2.0f;
		LoadAnimation();
		LoadAnimation1();
		LoadAnimation2();
		LoadAnimation3();
		LoadAnimation4();
		LoadAnimation5();
		LoadAnimation6();
		LoadAnimation7();
		inithitbox(width * 5 / 8, height * 6 / 8);
		initAttackBox();

	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.scale), (int) (20 * Game.scale));
	}

	public void update() {
		updateHealthBar();
		if (currentHealth <= 0) {
			if(state != DEAD) {
				state= DEAD;
				AniTick = 0;
				AniIndex = 0;
				playing.setPlayerDying(true);
//			playing.setGameOver(true);
		} else if(AniIndex==GetSpriteAmount(DEAD)-1 && AniTick>= aniSpeed-1) {
			playing.setGameOver(true);
		}else {
			updateAnimationPick();
		}
			return;
		}
		updateAttackBox();
		if (state == HIT) {
			if (AniIndex <= GetSpriteAmount(state) - 3) {
				knockedup();
				if (inAir)
					pushBack(pushBackDir, lvlData, 1.25f);

			}

			updatePushBackDrawOffset();
			state = Idle;
		} else
			updatePos();
		if (attacking)
			checkAttack();
		updateAnimationPick();
		setAnimation();
	}

	private void checkAttack() {
		if (attackChecked || AniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.checkEnemyHitCACO(attackBox);

	}

	private void updateAttackBox() {
		if (right) {
			attackBox.x = hitbox.x + (int) hitbox.width / 2 + (int) (Game.scale * 10);
		} else if (left) {
			attackBox.x = hitbox.x - (int) hitbox.width / 2 - (int) (Game.scale * 10);
		}
		attackBox.y = hitbox.y + (Game.scale * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);

	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][AniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
				(int) (hitbox.y - yDrawOffset - 5), 64 * flipW, 64, null);
		drawHitbox(g, lvlOffset);
		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationPick() {
		AniTick++;
		if (AniTick >= aniSpeed) {
			AniTick = 0;
			AniIndex++;
			if (AniIndex >= GetSpriteAmount(state)) {
				AniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = state;
		if (state == HIT)
			return;
		if (moving)
			state = running;
		else
			state = Idle;
		if (inAir) {
			if (airSpeed < 0)
				state = Jumping;
			else
				state = Falling;
		}
		if (moving && attacking)
			state = walkattack;
		else if (attacking) {
			state = attack;
			if (startAni != attack) {
				AniIndex = 1;
				AniTick = 0;
				return;
			}
		}
		if (startAni != state) {
			resetAniTick();
		}
	}

	private void resetAniTick() {
		AniTick = 0;
		AniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		if (jump)
			jump();
		// if(!left && !right && !inAir)
		// return;
		if (!inAir)
			if ((!left && !right) || (right && left))
				return;
		float xSpeed = 0;
		if (left) {
			xSpeed -= WalkSpeed;
			flipX = 64;
			flipW = -1;
		}
		if (right) {
			xSpeed += WalkSpeed;
			flipX = 0;
			flipW = 1;
		}
		if (!inAir) {
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0) {
					resetInAir();
				} else {
					airSpeed = fallSpeedAfterCollision;
				}
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}
		moving = true;

//		if(CanMoveHere(hitbox.x+xSpeed,hitbox.y+ ySpeed, hitbox.width, hitbox.height, lvlData)) {
//			hitbox.x +=xSpeed;
//			hitbox.y += ySpeed;
//			moving= true;
//		}
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void knockedup() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed / 3;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
	}

	public void changeHealth(int value) {
		if (value < 0) {
			if (state == HIT)
				return;
			else {
				state = HIT;
				AniTick = 0;
				AniIndex = 0;
			}
		}
		if (flipW == -1)
			pushBackDir = RIGHT;
		else
			pushBackDir = LEFT;
		currentHealth += value;
		currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
	}

	private void LoadAnimation() {

		image = LoadSave.GetSpriteAtlas("Dude_Monster_Idle_4.png");
		for (int i = 0; i < GetSpriteAmount(Idle); i++) {
			animations[0][i] = image.getSubimage(i * 32, 0, 32, 32);
		}
		statusBarImg = LoadSave.GetSpriteAtlas("health_power_bar.png");
	}

	private void LoadAnimation1() {
		image1 = LoadSave.GetSpriteAtlas("Dude_Monster_Run_6.png");
		for (int i = 0; i < GetSpriteAmount(running); i++) {
			animations[1][i] = image1.getSubimage(i * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation2() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Attack1_4.png");
		for (int i = 0; i < GetSpriteAmount(attack); i++) {
			animations[2][i] = image2.getSubimage(i * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation3() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Walk+Attack_6.png");
		for (int i = 0; i < GetSpriteAmount(walkattack); i++) {
			animations[4][i] = image2.getSubimage(i * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation4() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Jump_8.png");
		for (int i = 0; i < 4; i++) {
			animations[3][i] = image2.getSubimage(i * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation5() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Jump_8.png");
		for (int i = 0; i < 4; i++) {
			animations[5][i] = image2.getSubimage(i * 32 + 4 * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation6() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Death_8.png");
		for (int i = 0; i < 8; i++) {
			animations[6][i] = image2.getSubimage(i * 32, 0, 32, 32);
		}
	}

	private void LoadAnimation7() {
		image2 = LoadSave.GetSpriteAtlas("Dude_Monster_Hurt_4.png");
		for (int i = 0; i < 4; i++) {
			animations[7][i] = image2.getSubimage(i * 32, 0, 32, 32);
		}
	}

	public void loadlvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public void resetDirBoolean() {
		left = false;
		right = false;
	}

	public void setAttack(Boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean Jump) {
		this.jump = Jump;
	}

	public void resetAll() {
		resetDirBoolean();
		inAir = false;
		attacking = false;
		moving = false;
		state = Idle;
		currentHealth = maxHealth;
		hitbox.x = x;
		hitbox.y = y;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

}
