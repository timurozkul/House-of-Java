package firstGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;	
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {
	
	Random r = new Random();
	private Color col;
	
	private Handler handler;
		public MenuParticle(int x, int y, ID id, Handler handler) {
			super(x, y, id);
			this.handler = handler;
			velX = 2;
			velY = 8;
			
			col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
		
		public void tick() {
			x += velX;
			y += velY;
			
			if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
			if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
			
			handler.addObject(new Trail(x, y, ID.Trail, 0.05f, col, handler));
		}
		
		public Rectangle getBounds() {
			return new Rectangle((int)x, (int)y, 12, 12);
		}
		
		public void render(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.cyan);
			g.fillRect((int)x, (int)y, 12, 12);
		}
	}

