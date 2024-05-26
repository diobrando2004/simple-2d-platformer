package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprite();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	public void loadNextLevel() {
		lvlIndex++;
		if (lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("No more levels! Game completed!");
			Gamestate.state = Gamestate.MENU;
		}
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadlvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels) {
			levels.add(new Level(img));
		}
	}

//	public void loadNextLevel() {
////		Level newLevel = levels.get(lvlIndex);
//		game.getPlaying().getObjectManager().loadObjects(newLevel);
//	}

	private void importOutsideSprite() {

		levelSprite = new BufferedImage[48];
		for (int i = 0; i < 48; i++) {
			BufferedImage img = LoadSave.GetSpriteAtlas("Tile_" + (i + 1) + ".png");
			levelSprite[i] = img;
		}

	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int Index = levels.get(lvlIndex).GetSpriteIndex(i, j);
				g.drawImage(levelSprite[Index], i * game.TILES_SIZE - lvlOffset, j * game.TILES_SIZE, game.TILES_SIZE,
						game.TILES_SIZE, null);
			}
		}
		g.drawImage(levelSprite[2], 0, 0, null);
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	public int getAmountOfLevel() {
		return levels.size();
	}

}
