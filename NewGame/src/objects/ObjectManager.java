package objects;

import static utilz.Constants.ObjectConstants.HEART;
import static utilz.Constants.ObjectConstants.HEART_HEIGHT;
import static utilz.Constants.ObjectConstants.HEART_VALUE;
import static utilz.Constants.ObjectConstants.HEART_WIDTH;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[] keyImgs, heartImgs;
//	private ArrayList<EndingKey> keys;
	private ArrayList<Heart> hearts;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();

	}

	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Heart h : hearts) {
			if (h.isActive()) {
				if (hitbox.intersects(h.hitbox)) {
					h.setActive(false);
					applyEffectToPlayer(h);
				}
			}
		}
	}

	public void applyEffectToPlayer(Heart h) {
		if (h.getObjType() == HEART) {
			playing.getPlayer().changeHealth(HEART_VALUE);
		}
	}

	public void checkObjectHit(Rectangle2D.Float hitbox) {

	}

	public void loadObjects(Level newLevel) {
//		keys = newLevel.getEndingKeys();
		hearts = newLevel.getHearts();
	}

	private void loadImgs() {
//		BufferedImage keySprite = LoadSave.GetSpriteAtlas(LoadSave.ITEMS_ATLAS);
//		keyImgs = new BufferedImage[12];
//		for (int j = 0; j < keyImgs.length; j++) {
//			keyImgs[j] = keySprite.getSubimage(128 - 16 * 7, j * 16, 16, 16);
//		}

		BufferedImage heartSprite = LoadSave.GetSpriteAtlas(LoadSave.ITEMS_ATLAS);
		heartImgs = new BufferedImage[12];
		for (int j = 0; j < heartImgs.length; j++) {
			heartImgs[j] = heartSprite.getSubimage(0, j * 16, 16, 16);
		}
	}

	public void update() {
//		for (EndingKey k : keys) {
//			if (k.isActive()) {
//				k.update();
//			}
//		}

		for (Heart h : hearts) {
			if (h.isActive()) {
				h.update();
			}
		}
	}

	public void draw(Graphics g, int xLvlOffset) {
//		drawKeys(g, xLvlOffset);
		drawHeart(g, xLvlOffset);
	}

	private void drawHeart(Graphics g, int xLvlOffset) {
		for (Heart h : hearts) {
			if (h.isActive()) {
				g.drawImage(heartImgs[h.getAniIndex()], (int) (h.getHitbox().x - h.getxDrawOffset() - xLvlOffset),
						(int) (h.getHitbox().y - h.getyDrawOffset()), HEART_WIDTH, HEART_HEIGHT, null);
			}
		}

	}

	public void resetAllObjects() {
		for (Heart h : hearts) {
			h.reset();
		}

	}

//	private void drawKeys(Graphics g, int xLvlOffset) {
//		for (EndingKey k : keys) {
//			if (k.isActive()) {
//				g.drawImage(keyImgs[k.getAniIndex()], (int) (k.getHitbox().x - k.getxDrawOffset() - xLvlOffset),
//						(int) (k.getHitbox().y - k.getyDrawOffset()), ENDING_KEY_WIDTH, ENDING_KEY_HEIGHT, null);
//			}
//		}
//
//	}

}
