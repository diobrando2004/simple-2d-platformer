package ui;

import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;

public class LevelCompletedOverlay {
	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;

	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (330 * Game.scale);
		int nextX = (int) (445 * Game.scale);
		int y = (int) (195 * Game.scale);
		next = new UrmButton(nextX, y, UrmButton, nextX, y)
		
	}

	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
		bgW = (int) (img.getWidth() * Game.scale);
		bgH = (int) (img.getHeight() * Game.scale);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.scale);
	}

}
