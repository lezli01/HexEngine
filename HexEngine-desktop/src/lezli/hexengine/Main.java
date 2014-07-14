package lezli.hexengine;

import siegedevils.Starter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "HexEngine";
		cfg.useGL20 = true;
		cfg.resizable = false;
		cfg.fullscreen = false;
		cfg.width = 1366;
		cfg.height = 768;

		new LwjglApplication(new Starter(), cfg);
	}
}
