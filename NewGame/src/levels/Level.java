package levels;

import java.util.ArrayList;

import objects.EndingKey;
import utilz.HelpMethod;

public class Level {
	private int[][] lvlData;
	private ArrayList<EndingKey> keys;

	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
		creatEndingKeys();
	}

	private void creatEndingKeys() {
		keys = HelpMethod.getKeys(img);
	}

	public int GetSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public ArrayList<EndingKey> getEndingKeys() {
		return keys;
	}

}
