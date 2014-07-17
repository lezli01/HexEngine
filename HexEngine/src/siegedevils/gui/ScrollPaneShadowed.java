package siegedevils.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScrollPaneShadowed extends ScrollPane{

	private static final int SHDW_SIZE = 15;
	
	private Image i;
	
	public ScrollPaneShadowed( Actor widget, Skin skin ){

		super( widget, skin );
	
		i = new Image( skin, "shadow" );
		
	}

	@Override
	public void draw( Batch batch, float parentAlpha ){

		i.setBounds( getX() - SHDW_SIZE, getY() - SHDW_SIZE, getWidth() + SHDW_SIZE * 2, getHeight() + SHDW_SIZE * 2 );
		i.draw( batch, 0.8f );
		
		super.draw( batch, 1.0f );
	
	}
	
}
