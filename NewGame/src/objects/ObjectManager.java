package objects;

import static utilz.Constants.ObjectConstants.ENDING_KEY;
import static utilz.Constants.ObjectConstants.ENDING_KEY_HEIGHT;
import static utilz.Constants.ObjectConstants.ENDING_KEY_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[] keyImgs;
	private ArrayList<EndingKey> keys;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
		keys = new ArrayList<>();
		keys.add(new EndingKey(300, 300, ENDING_KEY));
	}

	private void loadImgs() {
		BufferedImage keySprite = LoadSave.GetSpriteAtlas(LoadSave.ENDING_KEY_ATLAS);
		keyImgs = new BufferedImage[12];
		for (int j = 0; j < keyImgs.length; j++) {
			keyImgs[j] = keySprite.getSubimage(128 - 16 * 7, j * 16, 16, 16);
		}
	}

	public void update() {
		for (EndingKey k : keys) {
			if (k.isActive()) {
				k.update();
			}
		}
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawKeys(g, xLvlOffset);
	}

	private void drawKeys(Graphics g, int xLvlOffset) {
		for (EndingKey k : keys) {
			if (k.isActive()) {
				g.drawImage(keyImgs[k.getAniIndex()], (int) (k.getHitbox().x - k.getxDrawOffset() - xLvlOffset),
						(int) (k.getHitbox().y - k.getyDrawOffset()), ENDING_KEY_WIDTH, ENDING_KEY_HEIGHT, null);
			}
		}

	}

	public void aa() {
		
	}
