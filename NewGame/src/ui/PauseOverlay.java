package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW,bgH;
	private SoundButton music;
	private UrmButton menuB, replayB, unpauseB;
	private VolumeButton volumeB;
	public  PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButton();
		createUrmButtons();
		createVolumeButton();
	}
	private void createVolumeButton() {
		int vX = (int) (215*Game.scale);
		int vY = (int) (300*Game.scale);
		volumeB = new VolumeButton(vX, vY, 100, 250);
		
	}
	private void createUrmButtons() {
		int menuX = (int)(335*Game.scale);
		int replayX = (int) (428*Game.scale);
		int unpauseX = (int) (521*Game.scale);
		int bY = (int) (220*Game.scale);
		menuB = new UrmButton(menuX, bY, 100, 100, 2);
		replayB = new UrmButton(replayX, bY, 100, 100, 1);
		unpauseB = new UrmButton(unpauseX, bY, 100, 100, 0);
	}
	private void createSoundButton() {
		int soundX= (int) (242*Game.scale);
		int musicY = (int)(220*Game.scale);
		
		music = new SoundButton(soundX, musicY, 100, 100);
		
	}
	private void loadBackground() {
		backgroundImg= LoadSave.GetSpriteAtlas("fuckingpausemenu-removebg-preview.png");
		bgW= (int)(backgroundImg.getWidth()*Game.scale);
		bgH= (int)(backgroundImg.getHeight()*Game.scale);
		bgX= Game.GAME_WIDTH/2 - bgW/2*3/4;
		bgY = 100;
	}
	public void update() {
		music.update();
		menuB.update();
		replayB.update();
		unpauseB.update();
		volumeB.update();
	}
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY, bgW*3/4, bgH*3/4, null);
		//pause
		music.draw(g);
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		volumeB.draw(g);
	}
	public void mouseDragged(MouseEvent e) {
		if(volumeB.isMousePressed()) {
			volumeB.changeX(e.getX());
		}
	}
	public void mousePressed(MouseEvent e) {
		if(isIn(e, music))
			music.setMousePressed(true);
		else if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e, volumeB))
			volumeB.setMousePressed(true);
		
	}
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, music)) {
			if(music.isMousePressed()) {
				music.setMuted(!music.isMuted());
			}
		}
		else if(isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		}
		else if(isIn(e, replayB)) {
			if(replayB.isMousePressed()) 
				System.out.println("not implemented!");
		}
		else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed()) 
				playing.unpauseGame();
		}
		music.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeB.resetBools();
	}
	public void mouseMoved(MouseEvent e) {
		music.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeB.setMouseOver(false);
		if(isIn(e, music))
			music.setMouseOver(true);
		else if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if(isIn(e, replayB))
			replayB.setMouseOver(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if(isIn(e, volumeB))
			volumeB.setMouseOver(true);

		
	}
	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(),e.getY()));
	}
}
