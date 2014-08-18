package lezli.hex.engine.core.structure.entities.graphics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Sprite extends GraphicsElement{
	
	private Rectangle mRect;
	
	public Sprite( String xFileName ){
		
		super( xFileName );
		
	}
	
	public Rectangle getRect(){
		
		return mRect;
		
	}
	
	private void setDimensions( int xXoffset, int xYoffset, int xWidth, int xHeight ){
		
		mRect.set( xXoffset, xYoffset, xWidth, xHeight );
		log( "Setting dimensions to (" + mRect + ")" );
	
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		int x_offset = xElement.getInt( "x_offset" );
		int y_offset = xElement.getInt( "y_offset" );
		int width = xElement.getInt( "width" );
		int height = xElement.getInt( "height" );
		
		setDimensions( x_offset, y_offset, width, height );
		
	}
	
	@Override
	protected void init() {

		super.init();
		
		mRect = new Rectangle();
		
	}

}
