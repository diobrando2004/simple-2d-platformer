package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethod{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg;
	public Menu(Game game) {
		super(game);
		loadButtons();
		backgroundImg = LoadSave.GetSpriteAtlas("Background.png");
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(game.GAME_WIDTH/2, (int)(150*game.scale), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(game.GAME_WIDTH/2, (int)(220*game.scale), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(game.GAME_WIDTH/2, (int)(290*game.scale), 2, Gamestate.QUIT);
	}

	public void update() {
		for(MenuButton mb : buttons) {
			mb.update();
		}
		
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT, null);
		for(MenuButton mb : buttons) {
			mb.draw(g);
		}
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
			}
		}
		
	}

	public void mouseReleased(MouseEvent e) {
				if(buttons[0].isMousePressed())
					Gamestate.state =Gamestate.PLAYING;
				else if(buttons[1].isMousePressed())
					Gamestate.state = Gamestate.OPTIONS;
				else if(buttons[2].isMousePressed())
					Gamestate.state = Gamestate.QUIT;
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
	}

	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
