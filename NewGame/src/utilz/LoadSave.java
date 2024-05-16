package utilz;

import static utilz.Constants.EnemyConstant.CACO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Caco;
import entities.Skele;
import main.Game;;

public class LoadSave {
	public static final String CACO_SPRITE = "Cacodaemon Sprite Sheet.png";

	public static BufferedImage GetSpriteAtlas(String filename) {
		BufferedImage image = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
		try {
			image = ImageIO.read(is);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public static ArrayList<Skele> getSkele() {
		BufferedImage img = LoadSave.GetSpriteAtlas("level_one_data.png");
		ArrayList<Skele> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 0)
					list.add(new Skele(i * Game.TILES_SIZE, (int) j * Game.TILES_SIZE));

			}
		return list;
	}

	public static int[][] GetLevelData() {
		BufferedImage img = LoadSave.GetSpriteAtlas("level_one_data.png");
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}

	public static ArrayList<Caco> GetCacos() {
		BufferedImage img = LoadSave.GetSpriteAtlas("level_one_data.png");
		ArrayList<Caco> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CACO) {
					list.add(new Caco(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}
}
