package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class VolumeButton extends PauseButton {
	private BufferedImage imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0f;

	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, 563, height);
		buttonX = x + width;
		minX = 360;
		maxX = x + 503;
		loadImgs();

	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas("second-removebg-preview.png");
		imgs = temp;
		BufferedImage temp2 = LoadSave.GetSpriteAtlas("first-removebg-preview.png");
		slider = temp2;
	}

	public void update() {
		index = 0;
		if (isMouseOver())
			index = 1;
		if (isMousePressed())
			index = 2;
	}

	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs, buttonX, y, 563, height, null);
	}

	public void changeX(int x) {
		if (x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		updateFloatValue();
	}

	private void updateFloatValue() {
		float range = maxX - minX;
		float value = buttonX - minX;
		floatValue = value / range;
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

	public float getFloatValue() {
		return floatValue;
	}
}
