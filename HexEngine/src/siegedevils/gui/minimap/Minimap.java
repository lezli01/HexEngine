package siegedevils.gui.minimap;

import lezli.hex.engine.moddable.interfaces.HEGameTable;
import lezli.hex.engine.moddable.interfaces.HETile;
import siegedevils.gui.ScrollPaneShadowed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Minimap extends ScrollPaneShadowed{

	private HEGameTable gt;
	private Texture pix;
	
	private static final float BORDER = 5.0f;
	
	private int i, j; 
	private float pw, ph;
	private Image border;
	
	public  Minimap( HEGameTable gameTable, Skin xSkin ){
		
		super( null, xSkin );
		
		gt = gameTable;
		
		pix = new Texture( Gdx.files.internal( "siegedevils/graphics_elements/textures/pix.png" ) );
		
//		setBackground( new TiledDrawable( xSkin.getRegion( "scroll-bg-tiled" ) ) );
		
		border = new Image( xSkin, "scroll-bg" );
		
	}

	@Override
	public void setSize( float width, float height ){

		super.setSize( width, height );
	
		if( gt == null )
			return;
		
		pw = ( width - ( 2 * BORDER ) ) / gt.mapWidth();
		ph = ( height - ( 2 * BORDER ) ) / gt.mapHeight();
		
		border.setBounds( getX(), getY(), width, height );
		
	}
	
	@Override
	public void draw( Batch batch, float parentAlpha ){

		super.draw( batch, parentAlpha );
	
		border.draw( batch, 1.0f );
		
		for( i = 0; i < gt.mapWidth(); i++ )
			for( j = 0; j < gt.mapHeight(); j++ ){
			
				HETile t = gt.getTile( i, j );

				batch.setColor( Color.BLUE );
				
				if( t.isEmpty() )
					batch.setColor( Color.GREEN );
				if( !t.canWalk() )
					batch.setColor( 0.1f, 0.5f, 0.1f, 1.0f );
				if( t.isHeed() )
					batch.setColor( Color.GRAY );
				if( t.isFog() )
					batch.setColor( Color.BLACK );
				
				batch.draw( pix, i * pw + BORDER, getHeight() - ( j + 1 ) * ph - BORDER, pw, ph );
		
			}
		
			HETile t = gt.getCameraPosition();
			
			batch.setColor( Color.CYAN );
			batch.draw( pix, t.getTileX() * pw, getHeight() - ( t.getTileY() + 1 ) * ph, pw, ph );
		
	}
	
}
