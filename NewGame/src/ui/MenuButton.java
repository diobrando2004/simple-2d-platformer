package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import static utilz.Constants.UI.Buttons.*;

import gamestates.Gamestate;
import utilz.LoadSave;

public class MenuButton {
	private int xPos, yPos, index, rowIndex;
	private int xOffsetCenter = B_WIDTH /2;
	private boolean mouseOver, mousePressed;
	private Gamestate state;
	private Rectangle bounds;
	private BufferedImage[] imgs;
	public MenuButton(int xPos, int yPos,int  rowIndex , Gamestate state) {
		this.xPos =xPos;
		this.yPos = yPos;
		this.rowIndex =rowIndex;
		loadImgs();
		initBounds();
	}
	private void initBounds() {
		bounds = new Rectangle(xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT);
	}
	private void loadImgs() {
		imgs = new BufferedImage[3];
		if(rowIndex == 0) {
			imgs[0] = LoadSave.GetSpriteAtlas("PlayBtn.png");
			imgs[1] = LoadSave.GetSpriteAtlas("PlayClick.png");
			imgs[2] = LoadSave.GetSpriteAtlas("PlayClick.png");
		}
		else if(rowIndex== 1) {
			imgs[0] = LoadSave.GetSpriteAtlas("OptBtn.png");
			imgs[1] = LoadSave.GetSpriteAtlas("OptClick.png");
			imgs[2] = LoadSave.GetSpriteAtlas("OptClick.png");
		}
		else if(rowIndex== 2) {
			imgs[0] = LoadSave.GetSpriteAtlas("ExitBtn.png");
			imgs[1] = LoadSave.GetSpriteAtlas("ExitClick.png");
			imgs[2] = LoadSave.GetSpriteAtlas("ExitClick.png");
		}
	}
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos-xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}
	public void update() {
		index = 0;
		if (mouseOver) {
			index = 1;
		if(mousePressed) {
			index =2;
		}
		}
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
	public Rectangle getBounds() {
		return bounds;
	}
	public void applyGamestate() {
		Gamestate.state = state;
	}
	public void resetBools() {
		mouseOver =false;
		mousePressed =false;
	}
	
}
