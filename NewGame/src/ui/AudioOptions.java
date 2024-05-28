package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;

public class AudioOptions {
	private VolumeButton volumeB;
	private SoundButton music;
	private Game game;

	public AudioOptions(Game game) {
		this.game = game;
		createSoundButton();
		createVolumeButton();

	}

	private void createVolumeButton() {
		int vX = (int) (215 * Game.scale);
		int vY = (int) (300 * Game.scale);
		volumeB = new VolumeButton(vX, vY, 100, 250);

	}

	private void createSoundButton() {
		int soundX = (int) (242 * Game.scale);
		int musicY = (int) (220 * Game.scale);

		music = new SoundButton(soundX, musicY, 100, 100);

	}

	public void update() {
		music.update();
		volumeB.update();
	}

	public void draw(Graphics g) {
		music.draw(g);
		volumeB.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if (volumeB.isMousePressed()) {
			float valueBefore = volumeB.getFloatValue();
			volumeB.changeX(e.getX());
			float valueAfter = volumeB.getFloatValue();
			if (valueBefore != valueAfter) {
				game.getAudioPlayer().setVolume(valueAfter);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, music))
			music.setMousePressed(true);
		else if (isIn(e, volumeB))
			volumeB.setMousePressed(true);

	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, music)) {
			if (music.isMousePressed()) {
				music.setMuted(!music.isMuted());
				game.getAudioPlayer().toggleSongMute();
				game.getAudioPlayer().toggleEffectMute();
			}
		}

		music.resetBools();

		volumeB.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		music.setMouseOver(false);

		volumeB.setMouseOver(false);
		if (isIn(e, music))
			music.setMouseOver(true);
		else if (isIn(e, volumeB))
			volumeB.setMouseOver(true);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY()));
	}
}
