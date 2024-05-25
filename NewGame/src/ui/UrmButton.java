package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class UrmButton extends PauseButton {
	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;

	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();

	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas("Orange Button Icons.png");
		imgs = new BufferedImage[3];
		if (rowIndex == 0) {
			for (int i = 0; i < imgs.length; i++)
				imgs[i] = temp.getSubimage(32 * 3 + i * 32, 32 * 3, 32, 32);
		} else if (rowIndex == 1) {
			for (int i = 0; i < imgs.length; i++)
				imgs[i] = temp.getSubimage(32 * 0 + i * 32, 32 * 1, 32, 32);
		} else if (rowIndex == 2) {
			for (int i = 0; i < imgs.length; i++)
				imgs[i] = temp.getSubimage(32 * 9 + i * 32, 0, 32, 32);
		}

	}

	public void update() {
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, width, height, null);
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}
