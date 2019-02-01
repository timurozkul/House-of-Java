package firstGame;

	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;

public class FastEnemy extends GameObject {
	
	private Handler handler;
		public FastEnemy(int x, int y, ID id, Handler handler) {
			super(x, y, id);
			this.handler = handler;
			velX = 2;
			velY = 8;
	}
		
		public void tick() {
			x += velX;
			y += velY;
			
			if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
			if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
			
			handler.addObject(new Trail(x, y, ID.Trail, 0.08f, Color.cyan, handler));
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

