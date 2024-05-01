package main;

import java.awt.Graphics;

import entities.Player;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;

public class Game implements Runnable{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int UPS= 200;
	private final int FPS = 120;
	private Playing playing;
	private Menu menu;
	public final static int TILES_DEFAULT= 32;
	public final static float scale = 1.5f;
	public final static int TILES_IN_WIDTH= 26;
	public final static int TILES_IN_HEIGHT= 14;
	public final static int TILES_SIZE= (int) (TILES_DEFAULT*scale);
	public final static int GAME_WIDTH= (int) (TILES_SIZE*TILES_IN_WIDTH);
	public final static int GAME_HEIGHT= (int) (TILES_SIZE*TILES_IN_HEIGHT);
	private LevelManager levelManager;
	public Game() {
	 InitClasses();
	gamePanel = new GamePanel(this);
	 gameWindow = new GameWindow(gamePanel);
	 gamePanel.requestFocus();
	 startGameLoop();
	}
	private void InitClasses() {
		menu=new Menu(this);
		playing = new Playing(this);
		
	}
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	private void update() {

		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;
		
		}
	}
	public void render(Graphics g) {
		switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		
		}
	}
	public void run() {
		double timePerFrame = 1000000000.0/FPS;
		double TimePerUpdate = 1000000000.0/UPS;
		long PreviousTime = System.nanoTime();
		int frames = 0;
		int updates = 0;
		long LastCheck = System.currentTimeMillis();
		double deltaU=0;
		double deltaF=0;
		while(true) {
			long currentTime = System.nanoTime();
			deltaU+=(currentTime-PreviousTime)/TimePerUpdate;
			deltaF+=(currentTime-PreviousTime)/timePerFrame;
			PreviousTime = currentTime;
			if(deltaU>=1) {
				update();
				updates++;
				deltaU--;
			}
			if(deltaF>=1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			if(System.currentTimeMillis()- LastCheck >= 1000) {
				LastCheck= System.currentTimeMillis();
				System.out.println("fps: " +frames +"| UPS: "+updates);
				frames=0;
				updates = 0;
			} 
		}
		
	}
	public void WindowFocusLost() {
		if(Gamestate.state==Gamestate.PLAYING) {
			playing.getPlayer().resetDirBoolean();
		}
	}
	public Menu getMenu() {
		return menu;
	}
	public Playing getPlaying() {
		return playing;
	}
}
