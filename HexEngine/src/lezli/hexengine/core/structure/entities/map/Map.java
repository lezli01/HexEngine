package lezli.hexengine.core.structure.entities.map;

import com.badlogic.gdx.math.Rectangle;

import lezli.hexengine.core.structure.entities.EntityArray;

public class Map extends EntityArray< MapRow >{

	private Rectangle mRect;
	
	public Map( String xFileName ){
		
		super( xFileName );
		
	}
	
	public MapTile get( int xRow, int xColumn ){
		
		return getAll().get( xRow ).getAll().get( xColumn );
		
	}
	
	public Rectangle getRect(){

		if( mRect == null )
			mRect = new Rectangle( 0, 0, getSize(), get( 0 ).getSize() );
		
		return mRect;
		
	}

}
