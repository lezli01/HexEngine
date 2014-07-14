package lezli.hexengine.core.structure.entities.map.tile;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hexengine.core.structure.entities.Entity;

public class TilePlaceholder extends Entity{

	public String placeholder;
	public float x,y,w,h;

	public TilePlaceholder( Element xElement ){
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		placeholder = xElement.get( "placeholder" );
		x = xElement.getFloat( "x" );
		y = xElement.getFloat( "y" );
		w = xElement.getFloat( "w" );
		h = xElement.getFloat( "h" );
		
	}
	
	@Override
	protected void init(){
		
	}
	
}
