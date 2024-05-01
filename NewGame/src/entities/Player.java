package entities;

import static utilz.Constants.Direction.DOWN;
import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.Direction.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.Idle;
import static utilz.Constants.PlayerConstants.running;
import static utilz.Constants.PlayerConstants.attack;
import static utilz.Constants.PlayerConstants.walkattack;
import static utilz.Constants.PlayerConstants.Jumping;
import static utilz.Constants.PlayerConstants.Falling;
import static utilz.HelpMethod.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage image, image1, image2;
	private BufferedImage[][] animations  = new BufferedImage[15][8];
	private int playerAction = Idle;
	private boolean left, up, right, down, jump;
	private boolean moving =false, attacking = false;
	private int AniTick, AniIndex = 0, AniSpeed = 20;
	private float PlayerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 6*Game.scale;
	private float yDrawOffset = 6*Game.scale;
	//jumping
	private float airSpeed = 0f;
	private float gravity = 0.04f*Game.scale;
	private float jumpSpeed= -2.25f*Game.scale;
	private float fallSpeedAfterCollision = 0.5f*Game.scale;
	private boolean inAir = false;
	//status bar
	private BufferedImage statusBarImg;
	private int statusBarWidth=(int)(192*Game.scale);
	private int statusBarHeight=(int)(58*Game.scale);
	private int statusBarX=(int)(10*Game.scale);
	private int statusBarY=(int)(10*Game.scale);
	
	private int healthBarWidth=(int)(150*Game.scale);
	private int healthBarHeight=(int)(4*Game.scale);
	private int healthBarXStart=(int)(34*Game.scale);
	private int healthBarYStart=(int)(14*Game.scale);
	
	private int maxHealth = 10000;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	//attack box
	private Rectangle2D.Float attackBox;
	private int flipX = 0;
	private int flipW= 1;
	private boolean attackChecked;	
	private Playing playing;
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		LoadAnimation();
		LoadAnimation1();
		LoadAnimation2();
		LoadAnimation3();
		LoadAnimation4();
		LoadAnimation5();
		LoadAnimation6();
		LoadAnimation7();
		inithitbox(x,y,width*5/8, height*6/8);
		initAttackBox();
		
	}

	private void initAttackBox() {
		attackBox= new Rectangle2D.Float(x,y, (int)(20*Game.scale), (int)(20*Game.scale));
		
	}

	public void update() {
		updateHealthBar();
		if(currentHealth<= 0) {
			playing.setGameOver(true);
			return;
		}
		updateAttackBox();
		
		updatePos();
		if(attacking)
			checkAttack();
		updateAnimationPick();
		setAnimation();
	}
	private void checkAttack() {
		if(attackChecked||AniIndex!=1)
			return;
		attackChecked=true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitbox.x+(int)hitbox.width/2+(int)(Game.scale*10);
		}else if(left) {
			attackBox.x = hitbox.x-(int)hitbox.width/2-(int)(Game.scale*10);
		}
		attackBox.y = hitbox.y + (Game.scale*10);
	}

	private void updateHealthBar() {
		healthWidth =(int) ((currentHealth/(float)maxHealth)*healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][AniIndex],
				(int) (hitbox.x-xDrawOffset)-lvlOffset+flipX,
				(int) (hitbox.y-yDrawOffset-5),64*flipW, 64, null);
		drawHitbox(g,lvlOffset);
		drawAttackBox(g,lvlOffset);
		drawUI(g);
	}


	private void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffset,(int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
		
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart+statusBarX, healthBarYStart+statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationPick() {
		AniTick++;
		if(AniTick >= AniSpeed) {
			AniTick = 0;
			AniIndex ++;
			if (AniIndex >=GetSpriteAmount(playerAction)) {
				AniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}
	private void setAnimation() {
		int startAni = playerAction;
		if(moving) playerAction = running;
		else playerAction= Idle;
		if(inAir) {
			if(airSpeed<0) playerAction=Jumping;
			else playerAction = Falling;
		}
		if(moving && attacking) playerAction = walkattack;
		else if (attacking) 
		{
			playerAction = attack;
			if(startAni!= attack) {
				AniIndex = 1;
				AniTick = 0;
				return;
			}
			}
		if(startAni != playerAction) {
			resetAniTick();
		}
	}
	private void resetAniTick() {
		AniTick = 0;
		AniIndex =0;
	}
	private void updatePos() {
		moving = false;
		if(jump) jump();
		//if(!left && !right && !inAir)
		//	return;
		if(!inAir)
			if((!left&&!right)|| (right && left))
				return;
		float xSpeed = 0;
		if(left) {
			xSpeed -=PlayerSpeed;
			flipX = 64;
			flipW = -1;
		}
		if (right) {
			xSpeed+=PlayerSpeed;
			flipX = 0;
			flipW = 1;
		}
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox,lvlData))
				inAir = true;
		}
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y+=airSpeed;
				airSpeed+=gravity;
				updateXPos(xSpeed);
			}
			else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed>0) {
					resetInAir();
				}
				else {
					airSpeed = fallSpeedAfterCollision;				
				}
				updateXPos(xSpeed);
			}
		}
		else {
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
		if(inAir) return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

		private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

		private void updateXPos(float xSpeed) {
			if(CanMoveHere(hitbox.x+xSpeed,hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x +=xSpeed;
			}else {
				hitbox.x=GetEntityXPosNextToWall(hitbox, xSpeed);
			}
	}
		public void changeHealth(int value) {
			currentHealth+= value;
			if(currentHealth<=0) {
				currentHealth = 0;
				//owarida
			} else if(currentHealth>= maxHealth)
				currentHealth = maxHealth;
		}
		private void LoadAnimation() {
		
			image = LoadSave.GetSpriteAtlas("Dude_Monster_Idle_4.png");
			for(int i = 0; i< GetSpriteAmount(Idle);i++) {
				animations[0][i] = image.getSubimage(i*32, 0, 32, 32);
			}
			statusBarImg = LoadSave.GetSpriteAtlas("health_power_bar.png");
		} 

		private void LoadAnimation1() {
				image1 = LoadSave.GetSpriteAtlas("Dude_Monster_Run_6.png");
				for(int i = 0; i< GetSpriteAmount(running);i++) {
					animations[1][i] = image1.getSubimage(i*32, 0, 32, 32);
				}
	}
		private void LoadAnimation2() {
				image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Attack1_4.png");
				for(int i = 0; i< GetSpriteAmount(attack);i++) {
					animations[2][i] = image2.getSubimage(i*32, 0, 32, 32);
				}
			} 
		private void LoadAnimation3() {
			image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Walk+Attack_6.png");
			for(int i = 0; i< GetSpriteAmount(walkattack);i++) {
				animations[4][i] = image2.getSubimage(i*32, 0, 32, 32);
			}
		} 
		private void LoadAnimation4() {
			image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Jump_8.png");
			for(int i = 0; i< 4;i++) {
				animations[3][i] = image2.getSubimage(i*32, 0, 32, 32);
			}
		} 
		private void LoadAnimation5() {
			image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Jump_8.png");
			for(int i = 0; i< 4;i++) {
				animations[5][i] = image2.getSubimage(i*32+4*32, 0, 32, 32);
			}
		} 
		private void LoadAnimation6() {
			image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Death_8.png");
			for(int i = 0; i< 8;i++) {
				animations[6][i] = image2.getSubimage(i*32, 0, 32, 32);
			}
		} 
		private void LoadAnimation7() {
			image2 =LoadSave.GetSpriteAtlas("Dude_Monster_Hurt_4.png");
			for(int i = 0; i< 4;i++) {
				animations[7][i] = image2.getSubimage(i*32, 0, 32, 32);
			}
		} 
		public void loadlvlData(int[][] lvlData) {
			this.lvlData = lvlData;
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		public void resetDirBoolean() {
			left=false;
			right= false;
			up= false;
			down = false;
		}
		public void setAttack(Boolean attacking) {
			this.attacking= attacking;
		}

		public boolean isLeft() {
			return left;
		}

		public void setLeft(boolean left) {
			this.left = left;
		}

		public boolean isUp() {
			return up;
		}

		public void setUp(boolean up) {
			this.up = up;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
		}
		public void setJump(boolean Jump) {
			this.jump =Jump;
		}

		public void resetAll() {
			resetDirBoolean();
			inAir = false;
			attacking = false;
			moving=false;
			playerAction=Idle;
			currentHealth = maxHealth;
			hitbox.x = x;
			hitbox.y = y;
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		
}
