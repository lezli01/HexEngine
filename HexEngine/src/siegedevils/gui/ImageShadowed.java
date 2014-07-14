package siegedevils.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ImageShadowed extends Image{

	private static final int SHDW_SIZE = 10;
	
	private Image i;
	
	public ImageShadowed( Drawable drawable, Skin skin ){

		super( drawable );
		
		i = new Image( skin, "shadow" );
		
	}

	@Override
	public void draw( Batch batch, float parentAlpha ){

		i.setBounds( getX(), getY() - SHDW_SIZE, getWidth() + SHDW_SIZE, getHeight() + SHDW_SIZE );
		i.draw( batch, 0.75f );
		super.draw(batch, 1.0f );
	
	}
	
}
