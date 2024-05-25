package entities;

import static utilz.Constants.EnemyConstant.CACO_HEIGHT;
import static utilz.Constants.EnemyConstant.CACO_HEIGHT_DEFAULT;
import static utilz.Constants.EnemyConstant.CACO_WIDTH;
import static utilz.Constants.EnemyConstant.CACO_WIDTH_DEFAULT;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] SkeleArr;
	private ArrayList<Skele> Skeles = new ArrayList<>();
	private BufferedImage[][] cacoArr;
	private ArrayList<Caco> cacos = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}

	private void addEnemies() {
		Skeles = LoadSave.getSkele();
		cacos = LoadSave.GetCacos();

	}

	public void update(int[][] lvlData, Player player) {
		for (Skele sk : Skeles)
			if (sk.isActive())
				sk.update(lvlData, player);
		for (Caco c : cacos) {
			c.update(lvlData, player);
		}
	}

	public void draw(Graphics g, int xlvlOffset) {
		drawSkele(g, xlvlOffset);
		drawCaco(g, xlvlOffset);

	}

	private void drawSkele(Graphics g, int xlvlOffset) {
		for (Skele sk : Skeles)
			if (sk.isActive()) {
				g.drawImage(SkeleArr[sk.GetState()][sk.getAniIndex()],
						(int) sk.getHitbox().x - xlvlOffset + 80 + sk.flipX(), (int) sk.getHitbox().y - 52,
						64 * 2 * (sk.flipW()), 64 * 2, null);
				g.setColor(Color.red);
				g.drawRect((int) sk.getHitbox().x - xlvlOffset, (int) sk.getHitbox().y, (int) sk.getHitbox().getWidth(),
						(int) sk.getHitbox().getHeight());
				sk.drawAttackBox(g, xlvlOffset);
			}
	}

	public void drawCaco(Graphics g, int xLvOffset) {
		for (Caco c : cacos) {
			g.drawImage(cacoArr[c.GetState()][c.getAniIndex()], (int) c.getHitbox().x - xLvOffset,
					(int) c.getHitbox().y, CACO_WIDTH, CACO_HEIGHT, null);
		}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Skele sk : Skeles)
			if (sk.isActive())
				if (attackBox.intersects(sk.getHitbox())) {
					sk.hurt(10);
					return;
				}
	}

	private void loadEnemyImgs() {
		SkeleArr = new BufferedImage[5][13];
		BufferedImage temp = LoadSave.GetSpriteAtlas("Skeleton enemy.png");
		for (int j = 0; j < SkeleArr.length; j++)
			for (int i = 0; i < SkeleArr[j].length; i++)
				SkeleArr[j][i] = temp.getSubimage(64 * i, j * 64, 64, 64);

		cacoArr = new BufferedImage[4][8];
		BufferedImage temp2 = LoadSave.GetSpriteAtlas(LoadSave.CACO_SPRITE);
		for (int j = 0; j < cacoArr.length; j++) {
			for (int i = 0; i < cacoArr[j].length; i++) {
				cacoArr[j][i] = temp2.getSubimage(i * CACO_WIDTH_DEFAULT, j * CACO_HEIGHT_DEFAULT, CACO_WIDTH_DEFAULT,
						CACO_HEIGHT_DEFAULT);
			}
		}
	}

	public void resetAllEnemies() {
		for (Skele sk : Skeles)
			sk.resetEnemy();
	}
}
