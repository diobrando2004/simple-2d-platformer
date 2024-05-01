package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
public class GameWindow {
		private JFrame jframe;
		public GameWindow(final GamePanel gamePanel) {
			jframe = new JFrame();
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.add(gamePanel);
			jframe.setResizable(false);
			jframe.pack();
			jframe.setLocationRelativeTo(null);
			jframe.setVisible(true);
			jframe.addWindowFocusListener(new WindowFocusListener() {
				
				public void windowLostFocus(WindowEvent e) {
					gamePanel.getGame().WindowFocusLost();
					
				}
				
				public void windowGainedFocus(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
}
