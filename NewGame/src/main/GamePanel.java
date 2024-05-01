package main;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.Direction.*;
import static utilz.Constants.PlayerConstants.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInput;
public class GamePanel extends JPanel{
	private MouseInput mouseInput;
	private Game game;
	public GamePanel(Game game) {
		mouseInput = new MouseInput(this);
		this.game =game;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}
	public void updateGame() {

	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);

	}
	public Game getGame() {
		return game;
	}
}
