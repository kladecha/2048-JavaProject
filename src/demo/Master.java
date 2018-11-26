package demo;

import javax.swing.JFrame;

public class Master {

	public static void main(String[] args) {
		Consol game = new Consol();
		
		JFrame window = new JFrame("2048");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.start();
	}

}
