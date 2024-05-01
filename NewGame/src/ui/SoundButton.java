package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class SoundButton extends PauseButton {
	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		loadSoundImgs();
	}
	private void loadSoundImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas("Orange Button Icons.png");
		BufferedImage temp1 = LoadSave.GetSpriteAtlas("Pink Buttons Icons.png");
		soundImgs = new BufferedImage[2][3];
		for(int j = 0; j<soundImgs[0].length;j++)
			soundImgs[0][j] = temp.getSubimage(32*6+j*32, 32*6, 32, 32);
		for(int j = 0; j<soundImgs[0].length;j++)
			soundImgs[1][j] = temp1.getSubimage(32*6+j*32, 32*6, 32, 32);
	}
	public void update() {
		if(muted)
			rowIndex = 1;
		else rowIndex =0;
		if(mouseOver) colIndex = 1;
		if(mousePressed) colIndex =2;
	}
	public void resetBools() {
		mouseOver =false;
		mousePressed =false;
	}
	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
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
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
}

