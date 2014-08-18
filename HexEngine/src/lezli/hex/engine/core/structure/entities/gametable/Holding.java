package lezli.hex.engine.core.structure.entities.gametable;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hex.engine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Holding extends Entity{

	private String mType;
	private HashMap< String, String > mValues;
	private ArrayList< Holding > mHoldings;
	
	public Holding( Element xElement ){
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );

		mType = xElement.getName();
		
		for( Entry<String, String> value: xElement.getAttributes().entries() )
			mValues.put( value.key, value.value );

		for( int i = 0; i < xElement.getChildCount(); i++ )
			mHoldings.add( new Holding( xElement.getChild( i ) ) );
		
	}

	public String getType(){
		
		return mType;
		
	}
	
	public HashMap< String, String > getValues(){
		
		return mValues;
		
	}
	
	public ArrayList< Holding > getHoldings(){
		
		return mHoldings;
		
	}
	
	@Override
	protected void init(){
		
		mValues = new HashMap< String, String >();
		mHoldings = new ArrayList< Holding >();
		
	}

}
