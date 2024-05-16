package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] sprite1;

	private Level Level1;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprite();
		Level1 = new Level(LoadSave.GetLevelData());
	}

	private void importOutsideSprite() {

		sprite1 = new BufferedImage[48];
		for (int i = 0; i < 48; i++) {
			BufferedImage img = LoadSave.GetSpriteAtlas("Tile_" + (i + 1) + ".png");
			sprite1[i] = img;
		}

	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < Level1.getLevelData()[0].length; i++) {
				int Index = Level1.GetSpriteIndex(i, j);
				g.drawImage(sprite1[Index], i * game.TILES_SIZE - lvlOffset, j * game.TILES_SIZE, game.TILES_SIZE,
						game.TILES_SIZE, null);
			}
		}
		g.drawImage(sprite1[2], 0, 0, null);
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return Level1;
	}
}
