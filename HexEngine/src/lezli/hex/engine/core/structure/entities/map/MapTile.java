package lezli.hex.engine.core.structure.entities.map;

import java.util.ArrayList;

import lezli.hex.engine.core.structure.entities.Entity;
import lezli.hex.engine.core.structure.entities.map.tile.TilePlaceholder;

import com.badlogic.gdx.utils.XmlReader.Element;

public class MapTile extends Entity{

	private String mTile;
	
	private boolean mWalkable;
	private ArrayList< TilePlaceholder > mPlaceholders;
	
	public MapTile( String xFilename ){
		
		super( xFilename );
		
	}
	
	public MapTile( Element xElement ){
		
		super( xElement );
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
		
		setTile( xElement.getAttribute( "tile" ) );
		
		try{
		
		mWalkable = xElement.getBoolean( "walkable" );

		}
		catch( Exception e ){
			
		}
		
		for( Element placeholder: xElement.getChildrenByName( "Placeholder" ) )
			mPlaceholders.add( new TilePlaceholder( placeholder ) );

		
	}
	
	private void setTile( String xTile ){
		
		mTile = xTile;
		log( "Tile set (" + mTile + ")" );
		
	}
	
	public String getTile(){
		
		return mTile;
		
	}
	
	public boolean walkable(){
		
		return mWalkable;
		
	}
	
	public ArrayList< TilePlaceholder > getPlaceholders(){
		
		return mPlaceholders;
		
	}
	
	@Override
	protected void init(){
	
		mPlaceholders = new ArrayList< TilePlaceholder >();
		
	}

}
