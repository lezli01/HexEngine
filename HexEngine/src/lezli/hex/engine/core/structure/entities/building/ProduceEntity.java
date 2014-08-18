package lezli.hex.engine.core.structure.entities.building;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hex.engine.core.structure.entities.cost.CostEntity;

public class ProduceEntity extends CostEntity{

	private String mUnit;
	private int mDuration;
	
	public ProduceEntity( Element xElement ){
		
		super( xElement );
		
	}
	
	public String getUnit(){
		
		return mUnit;
		
	}
	
	public int getDuration(){
		
		return mDuration;
		
	}
	
	@Override
	protected void parse( Element xElement ){
	
		super.parse( xElement );
	
		setUnit( xElement.getAttribute( "unit" ) );
		setDuration( xElement.getInt( "duration" ) );
		
	}

	private void setUnit( String xUnit ){
		
		mUnit = xUnit;
		log( "Unit set (" + mUnit + ")" );
		
	}

	private void setDuration( int xDuration ){
		
		mDuration = xDuration;
		log( "Duration set (" + mDuration + ")" );
		
	}

}
