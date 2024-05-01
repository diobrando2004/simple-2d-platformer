package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.Playing;
import gamestates.Gamestate;
import main.Game;

public class GameOverOverlay {
	private Playing playing;
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
	}
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			playing.resetAll();
			Gamestate.state = Gamestate.MENU;
		}
	}
}
