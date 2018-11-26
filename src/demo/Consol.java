package demo;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Consol  extends JPanel implements KeyListener, Runnable{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 630;
	public static final Font main = new Font("Bebas Neuse Regular", Font.PLAIN, 28);
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Grid board;
	
	private long startTime;
	private long elapsed;
	private boolean set;
	
	
	public Consol() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		board = new Grid(WIDTH / 2 - Grid.BOARD_WIDTH / 2, HEIGHT - Grid.BOARD_HEIGHT - 10);
	}
	
	private void update() {
//		if(Input.pressed[KeyEvent.VK_SPACE]) {
//			System.out.println("hit space");
//		}
//		if(Input.typed(KeyEvent.VK_RIGHT)) {
//			System.out.println("hit ringht");
//		}
		
		board.update();
		Input.update();
	}
	
	private void render() {
		Graphics2D g = (Graphics2D)
		image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		board.run(g);
		g.dispose();
		
		Graphics2D g2d = (Graphics2D)
		getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
	
	@Override
	public void run() {
		boolean shouldRender = false;
		
		int fps = 0, updates = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 100000000.0/60;
		
		double then = System.nanoTime();
		double unprocessed = 0;
		
		while(running) {
			
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			
			while(unprocessed >= 1) {
				updates++;
				update();
				unprocessed--;
				shouldRender = true;
			}
			
			if(shouldRender) {
				fps++;
				render();
				shouldRender = false;
			}
			else {
				try {
					Thread.sleep(1);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(System.currentTimeMillis() - fpsTimer > 1000) {
			System.out.printf("%d fps %d updates", fps, updates);
			System.out.println();
			fps = 0;
			updates = 0;
			fpsTimer += 1000;
			
		}
	}
		
		public synchronized void start() {
			if(running) return;
			running = true;
			game = new Thread(this,"game");
			game.start();
		}
		
		public synchronized void stop() {
			if(!running)
				return;
			running = false;
			System.exit(0);
		}

		
	@Override
	public void keyPressed(KeyEvent e) {
		Input.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Input.keyReleased(e);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void master() {
	}
}
