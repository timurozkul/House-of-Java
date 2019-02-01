package firstGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import firstGame.Game.STATE;

public class Menu extends MouseAdapter{
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			
			//play button
			if(mouseOver(mx, my, 210 , 150, 200,  64)) {
				game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemys();
				handler.addObject(new BasicEnemy(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.BasicEnemy, handler));
			}
			
			//quit button
			if(mouseOver(mx, my, 210 , 350, 200,  64)) {
				System.exit(1);
			}
			
			//help button
			if(mouseOver(mx, my, 210 , 250, 200,  64)) {
				game.gameState = STATE.Help;
			}
			 
		}
	
		//back button for help
		if(game.gameState == STATE.Help) {
			if(mouseOver(mx, my, 150, 250, 200, 64)) {
			game.gameState = STATE.Menu;
			return;
			}
		}
		
		//back button for help
		if(game.gameState == STATE.End) {
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				hud.setLevel(1);
				hud.setScore(1);
				game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemys();
				handler.addObject(new BasicEnemy(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.BasicEnemy, handler));
				
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 50);
		Font fnt2 = new Font("arial", 1, 30);
		Font fnt3 = new Font("arial", 1, 15);
	
		if(game.gameState == STATE.Menu) {

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Wave", 245, 70);
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(210, 157, 200, 64);
			g.drawString("Play", 275, 200);
			
			g.setColor(Color.white);
			g.drawRect(210, 257, 200, 64);
			g.drawString("Help", 275, 300);
			
			g.setColor(Color.white);
			g.drawRect(210, 357, 200, 64);
			g.drawString("Quit", 275, 400);
			
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawString("by Timur Ozkul", 260, 110);
			
		} else if (game.gameState == STATE.Help) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help", 240, 70);
			
			g.setFont(fnt3);
			g.drawString("Use WASD keys to move the player and dodge the enemies", 100, 200);
			g.setFont(fnt2);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Back", 270, 290);
		} else if (game.gameState == STATE.End) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", 180, 70);
			
			g.setFont(fnt3);
			g.drawString("You lost with a score of: " + hud.getScore(), 195, 200);
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Try again", 245, 390);
		}
	
	}
}
