package gamestates;

import static utilz.Constants.Environment.BIG_CLOUD_HEIGHT;
import static utilz.Constants.Environment.BIG_CLOUD_WIDTH;
import static utilz.Constants.Environment.SMALL_CLOUD_HEIGHT;
import static utilz.Constants.Environment.SMALL_CLOUD_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethod {
	private Player player;
	private LevelManager levelManager;
//	private CacoManager cacoManager;
	private ObjectManager objectManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private boolean paused = false;
	private int xLvlOffset;
	private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.4 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * game.TILES_SIZE;
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudPos;
	private Random rnd = new Random();
	private boolean gameOver;

	public Playing(Game game) {
		super(game);
		InitClasses();
		backgroundImg = LoadSave.GetSpriteAtlas("playing_bg_img.png");
		bigCloud = LoadSave.GetSpriteAtlas("big_clouds.png");
		smallCloud = LoadSave.GetSpriteAtlas("small_clouds.png");
		smallCloudPos = new int[8];
		for (int i = 0; i < smallCloudPos.length; i++)
			smallCloudPos[i] = (int) (90 * game.scale) + rnd.nextInt((int) (100 * game.scale));
	}

	private void InitClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		player = new Player(200, 200, 64, 64, this);
		player.loadlvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}

	public void update() {
		if (!paused && !gameOver) {
			levelManager.update();
			objectManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
//			cacoManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBordder();
		} else
			pauseOverlay.update();

	}

	private void checkCloseToBordder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;
		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT, null);
		drawCloud(g);
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);
//		cacoManager.draw(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
	}

	private void drawCloud(Graphics g) {
		for (int i = 0; i < 3; i++)
			g.drawImage(bigCloud, 0 + i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * game.scale),
					BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		for (int i = 0; i < smallCloudPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudPos[i],
					SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
	}

	public void resetAll() {
		// reset playing, enemy
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}

	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttack(true);

	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}

	public void mousePressed(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseReleased(e);

	}

	public void mouseMoved(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseMoved(e);

	}

	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
//				player.setUp(true);
				player.setJump(true);
				break;
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_S:
				player.setDown(true);

				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_J:
				player.setAttack(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ENTER:
				Gamestate.state = Gamestate.MENU;
				break;
			case KeyEvent.VK_ESCAPE:
				paused = true;
				break;
			}

	}

	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
//				player.setUp(false);
				player.setJump(false);
				break;
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_S:
				player.setDown(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			case KeyEvent.VK_J:
				player.setAttack(false);
				break;
			}

	}

	public void unpauseGame() {
		paused = false;
	}

	public Player getPlayer() {
		return player;
	}

	public void WindowFocusLost() {
		player.resetDirBoolean();
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}
}
