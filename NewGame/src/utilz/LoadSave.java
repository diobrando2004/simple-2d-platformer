package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {
	public static final String CACO_SPRITE = "Cacodaemon Sprite Sheet.png";
	public static final String ENDING_KEY_ATLAS = "GameItemsAnims-Sheet.png";
	public static final String COMPLETED_IMG = "complete_sprites.png";

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

	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++) {
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals("" + (i + 1) + ".png")) {
					filesSorted[i] = files[j];
				}
			}
		}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		for (int i = 0; i < imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return imgs;
	}

//	public static int[][] GetLevelData() {
//		BufferedImage img = LoadSave.GetSpriteAtlas(as);
//		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
//		for (int j = 0; j < img.getHeight(); j++)
//			for (int i = 0; i < img.getWidth(); i++) {
//				Color color = new Color(img.getRGB(i, j));
//				int value = color.getRed();
//				if (value >= 48)
//					value = 0;
//				lvlData[j][i] = value;
//			}
//		return lvlData;
//	}

}
