package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private AudioOptions audioOption;
	private UrmButton menuB, replayB, unpauseB;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOption = playing.getGame().getAudioOptions();
		createUrmButtons();
	}

	private void createUrmButtons() {
		int menuX = (int) (335 * Game.scale);
		int replayX = (int) (428 * Game.scale);
		int unpauseX = (int) (521 * Game.scale);
		int bY = (int) (220 * Game.scale);
		menuB = new UrmButton(menuX, bY, 100, 100, 2);
		replayB = new UrmButton(replayX, bY, 100, 100, 1);
		unpauseB = new UrmButton(unpauseX, bY, 100, 100, 0);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas("fuckingpausemenu-removebg-preview.png");
		bgW = (int) (backgroundImg.getWidth() * Game.scale);
		bgH = (int) (backgroundImg.getHeight() * Game.scale);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2 * 3 / 4;
		bgY = 100;
	}

	public void update() {

		menuB.update();
		replayB.update();
		unpauseB.update();
		audioOption.update();
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY, bgW * 3 / 4, bgH * 3 / 4, null);
		// pause

		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		audioOption.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOption.mouseDragged(e);
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else {
			audioOption.mousePressed(e);
		}

	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.setGameState(Gamestate.MENU);
				playing.unpauseGame();
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}

		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		} else {
			audioOption.mouseReleased(e);
		}
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
	}

	public void mouseMoved(MouseEvent e) {

		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else {
			audioOption.mouseMoved(e);
		}

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY()));
	}
}
