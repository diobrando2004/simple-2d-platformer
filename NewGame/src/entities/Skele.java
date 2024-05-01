package entities;

import static utilz.Constants.Direction.*;
import static utilz.Constants.EnemyConstant.*;
import static utilz.HelpMethod.CanMoveHere;
import static utilz.HelpMethod.GetEntityYPosUnderRoofOrAboveFloorEnemy;
import static utilz.HelpMethod.IsEntityOnFloor;
import static utilz.HelpMethod.isFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;
public class Skele extends Enemy{
	private Rectangle2D.Float attackBox;
	private int attackboxOffsetX;

	public Skele(float x, float y) {
		super(x, y, SKELE_WIDTH, SKELE_HEIGHT, SKELE);
		inithitbox(x, y, (int)24*Game.scale,(int) 28*Game.scale);
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y,(int)(84*Game.scale),(int)(28*Game.scale));
		attackboxOffsetX=(int)(Game.scale*30);
		
	}

	public void update(int[][] lvlData,Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackboxOffsetX;
		attackBox.y = hitbox.y-16;
		
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
			}
			if(inAir) {
				updateInAir(lvlData);
				}
			else {
				switch(enemyState) {
				case IDLE:
					newState(RUNNING);
					break;
				case RUNNING:
					if(canSeePlayer(lvlData, player))
						turnTowardsPlayer(player);
					if(isPlayerCloseForAttack(player))
						newState(ATTACK);
					move(lvlData);
					break;
				case ATTACK:
					if(aniIndex==0)
						attackChecked = false;
					if(aniIndex==4||aniIndex==5||aniIndex==8||aniIndex==9 && !attackChecked)
						checkEnemyHit(attackBox, player);
					break;
				case HIT:
					
					break;
				}
			}
		}
	

	public void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x-lvlOffset),(int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
	}
	public int flipX(){
		if(walkDir == RIGHT)
			return -128;
		else
			return 0;
	}
	public int flipW() {
		if(walkDir== RIGHT)
			return 1;
		else 
			return -1;
	}
}
