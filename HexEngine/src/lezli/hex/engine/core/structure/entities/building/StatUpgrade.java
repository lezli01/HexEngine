package lezli.hex.engine.core.structure.entities.building;

import lezli.hex.engine.core.structure.entities.stat.StatEntries;

import com.badlogic.gdx.utils.XmlReader.Element;

public class StatUpgrade extends ProduceEntity{

	private StatEntries mStatEntries;

	public StatUpgrade( Element xElement ){
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setStatEntries( new StatEntries( xElement.getChildByName( "StatEntries" ) ) );
		
	}

	public StatEntries getStatEntries(){
		
		return mStatEntries;
		
	}
	
	private void setStatEntries( StatEntries xStatEntries ){
		
		mStatEntries = xStatEntries;
		log( "StatEntries set (" + mStatEntries + ")" );
		
	}
	
	@Override
	protected void init(){
		
	}

}
