package levels;

import static utilz.HelpMethod.GetCacos;
import static utilz.HelpMethod.GetLevelData;
import static utilz.HelpMethod.GetPlayerSpawn;
import static utilz.HelpMethod.getSkele;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Caco;
import entities.Skele;
import main.Game;
import objects.EndingKey;

public class Level {
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Skele> skeles;
	private ArrayList<Caco> cacos;
	private ArrayList<EndingKey> keys;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEmenies();
		calcLvlOffsets();
		calcPlayerSpawn();
//		creatEndingKeys();

	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);

	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEmenies() {
		skeles = getSkele(img);
		cacos = GetCacos(img);

	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

//	private void creatEndingKeys() {
//		keys = HelpMethod.getKeys(img);
//	}

	public int GetSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Skele> getSkeles() {
		return skeles;
	}

	public ArrayList<Caco> getCacos() {
		return cacos;
	}

	public ArrayList<EndingKey> getEndingKeys() {
		return keys;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

}
