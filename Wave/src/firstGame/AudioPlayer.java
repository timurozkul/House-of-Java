package firstGame;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
	
	public static  Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load() {
		try {
			musicMap.put("music", new Music("res/background_music.wav"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Music getMsuic(String key) {
		return musicMap.get(key);
	}
}
 