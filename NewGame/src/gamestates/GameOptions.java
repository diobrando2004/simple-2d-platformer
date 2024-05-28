package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.AudioOptions;
import ui.UrmButton;
import utilz.LoadSave;

public class GameOptions extends State implements Statemethod {
	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB;

	public GameOptions(Game game) {
		super(game);
		loadImgs();
		loadButtons();
		audioOptions = game.getAudioOptions();
	}

	private void loadButtons() {
		int menuX = (int) (387 * Game.scale);
		int menuY = (int) (325 * Game.scale);
		menuB = new UrmButton(menuX, menuY, 100, 100, 0);

	}

	private void loadImgs() {
		backgroundImg = LoadSave.GetSpriteAtlas("Background.png");
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);
		bgW = (int) (optionsBackgroundImg.getWidth() * Game.scale);
		bgH = (int) (optionsBackgroundImg.getHeight() * Game.scale);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * Game.scale);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
