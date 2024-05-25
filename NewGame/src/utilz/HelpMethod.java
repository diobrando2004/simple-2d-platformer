package utilz;

import static utilz.Constants.ObjectConstants.ENDING_KEY;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import objects.EndingKey;

public class HelpMethod {
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		return isTileSolid((int) xIndex, (int) yIndex, lvlData);

	}

	public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value;
		if (xTile == 0 || yTile == 14 || yTile == 0 || yTile >= 14)
			value = 1;
		else
			value = lvlData[yTile][(int) xTile];
		if (value >= 48 || value < 0 || value != 47)
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else {
			// left
			return currentTile * Game.TILES_SIZE;
		}

	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int YOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + YOffset + 47;
		} else {
			// jumping
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static float GetEntityYPosUnderRoofOrAboveFloorEnemy(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int YOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + YOffset - 1;
		} else {
			// jumping
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// pixel below bottom left and right
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData, int walkDir) {

		if (walkDir == 0)
			return IsSolid(hitbox.x - xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (isTileSolid(xStart + i, y, lvlData))
				return false;
			if (!isTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}
		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firsthitbox, Rectangle2D.Float secondhitbox,
			int yTile) {
		int firstXTile = (int) (firsthitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondhitbox.x / Game.TILES_SIZE);
		if (firstXTile > secondXTile)
			return isAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return isAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);

	}

	public static ArrayList<EndingKey> getKeys(BufferedImage img) {

		ArrayList<EndingKey> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == ENDING_KEY)
					list.add(new EndingKey((int) i * Game.TILES_SIZE, (int) j * Game.TILES_SIZE, ENDING_KEY));

			}
		return list;
	}
}
