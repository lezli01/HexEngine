package lezli.hexengine.core.structure.entities.graphics;

import lezli.hexengine.core.structure.entities.Entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * The <b>Animation</b>'s <b>Frame</b> class.
 * @author Lezli
 *
 */
public class Frame extends Entity{
	
	private int mMultiply;
	private Rectangle mRect;
	
	public Frame( Element xElement ){
		
		super( xElement );
		
	}

	public int getMultiply(){
		
		return mMultiply;
		
	}
	
	public Rectangle getRect(){
		
		return mRect;
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
		
		int x_offset = xElement.getInt( "x_offset" );
		int y_offset = xElement.getInt( "y_offset" );
		int width = xElement.getInt( "width" );
		int height = xElement.getInt( "height" );
		int multiply = xElement.getInt( "multiply" );
		
		setDimensions( x_offset, y_offset, width, height );
		setMultiply( multiply );
		
	}
	
	@Override
	protected void init() {
	
		mRect = new Rectangle();
		
	}

	private void setDimensions( int xXoffset, int xYoffset, int xWidth, int xHeight ){
		
		mRect.set( xXoffset, xYoffset, xWidth, xHeight );
		log( "Frame dimensions set to (" + mRect + ")" );
		
	}
	
	private void setMultiply( int xMultiply ){
		
		mMultiply = xMultiply;
		log( "Frame multiply set to (" + mMultiply + ")" );
		
	}
	
}
