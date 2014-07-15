package siegedevils.gui.minimap;

import lezli.hexengine.moddable.interfaces.HEGameTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class Minimap extends ScrollPane{

	private HEGameTable gt;
	private Texture pix;
	
	private int i, j; 
	private float pw, ph;
	
	public  Minimap( HEGameTable gameTable ){
		
		super( null );
		
		gt = gameTable;
		
		pix = new Texture( Gdx.files.internal( "siegedevils/graphics_elements/textures/pix.png" ) );
		
	}

	@Override
	public void setSize( float width, float height ){

		super.setSize( width, height );
	
		if( gt == null )
			return;
		
		pw = width / gt.mapWidth();
		ph = height / gt.mapHeight();
		
	}
	
	@Override
	public void draw( Batch batch, float parentAlpha ){

		super.draw( batch, parentAlpha );
	
		for( i = 0; i < gt.mapWidth(); i++ )
			for( j = 0; j < gt.mapHeight(); j++ ){
				if( gt.isEmpty( i, j ) )
					batch.setColor( Color.GRAY );
				else
					batch.setColor( Color.GREEN );
				batch.draw( pix, i * pw, getHeight() - ( j + 1 ) * ph, pw, ph );
			}
		
	}
	
}
