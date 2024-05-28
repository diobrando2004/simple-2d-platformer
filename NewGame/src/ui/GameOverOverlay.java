package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {
	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButton menu, play;

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}

	private void createButtons() {
		int menuX = (int) (335 * Game.scale);
		int playX = (int) (445 * Game.scale);
		int y = (int) (195 * Game.scale);
		play = new UrmButton(playX - 10, y, 100, 100, 1);
		menu = new UrmButton(menuX - 10, y, 100, 100, 2);

	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
		imgW = (int) (img.getWidth() * Game.scale);
		imgH = (int) (img.getHeight() * Game.scale);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int) (100 * Game.scale);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		menu.draw(g);
		play.draw(g);
	}

	public void update() {
		menu.update();
		play.update();
	}

	public void keyPressed(KeyEvent e) {

	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);
		if (isIn(menu, e)) {
			menu.setMouseOver(true);
		} else if (isIn(play, e)) {
			play.setMouseOver(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(Gamestate.MENU);
			}
		} else if (isIn(play, e)) {
			if (play.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());

			}
		}
		menu.resetBools();
		play.resetBools();

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e)) {
			menu.setMousePressed(true);
		} else if (isIn(play, e)) {
			play.setMousePressed(true);
		}
	}
}
