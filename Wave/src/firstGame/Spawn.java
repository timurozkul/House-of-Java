package firstGame;

import java.util.Random;

public class Spawn {
	private Handler handler;
	private HUD hud;
	private int scoreKeep;
	private Random r = new Random();
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud =  hud;
		
	}
	
	public void tick() {
		scoreKeep++; 
		if(scoreKeep >= 250) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
			if(hud.getLevel() == 2 || hud.getLevel() == 3 || hud.getLevel() == 5 || hud.getLevel() == 6 || hud.getLevel() == 7) {
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
			} else if(hud.getLevel() == 4 || hud.getLevel() == 8) {
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.FastEnemy, handler));
			} else if(hud.getLevel() == 9) {
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.SmartEnemy, handler));
			} else if(hud.getLevel() == 10) {
				handler.clearEnemys();
				handler.addObject(new EnemyBoss(Game.WIDTH/2, -100, ID.EnemyBoss, handler));
			}
			
		}
	}
	
}
