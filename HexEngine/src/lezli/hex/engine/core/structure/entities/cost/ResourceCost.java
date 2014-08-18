package lezli.hex.engine.core.structure.entities.cost;

import lezli.hex.engine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;

public class ResourceCost extends Entity{

	private String mResource;
	private int mValue;
	
	public ResourceCost( Element xElement ){
		
		super( xElement );
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		setResource( xElement.getAttribute( "resource" ) );
		setValue( xElement.getInt( "value" ) );
		
	}
	
	private void setResource( String xResource ){
		
		mResource = xResource;
		log( "Resource set (" + mResource + ")" );
		
	}

	private void setValue( int xValue ){
		
		mValue = xValue;
		log( "Value set (" + mValue + ")" );
		
	}
	
	public String getResource(){
		
		return mResource;
		
	}
	
	public int getValue(){
		
		return mValue;
		
	}
	
	@Override
	protected void init() {
		
	}
	
}
