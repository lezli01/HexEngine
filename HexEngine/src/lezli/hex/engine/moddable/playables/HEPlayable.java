package lezli.hex.engine.moddable.playables;

import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public interface HEPlayable {

	public String getName();
	public String getEntityID();
	public String getDescription();
	
	public SpriteDrawable getLargeIcon();
	
}
