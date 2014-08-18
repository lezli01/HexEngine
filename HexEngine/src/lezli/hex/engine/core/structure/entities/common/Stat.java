package lezli.hex.engine.core.structure.entities.common;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;

public class Stat extends GraphicalEntity{

	private String mShortName;
	
	public Stat( Element xElement ){
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
		
		setShortName( xElement.getAttribute( "short_name" ) );
	
	}
	
	private void setShortName( String xShortName ){
		
		mShortName = xShortName;
		log( "Short name set (" + mShortName + ")" );
		
	}
	
	@Override
	protected void init(){
		
	}

}
