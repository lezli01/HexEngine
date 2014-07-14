package lezli.hexengine.core.structure.entities;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hexengine.core.structure.entities.graphics.GraphicalEntity;
import lezli.hexengine.core.structure.entities.stat.StatEntries;

public class LivingEntity extends GraphicalEntity{

	private StatEntries mStatEntries;
	
	public LivingEntity( String xFileName ){
		
		super( xFileName );
		
	}
	
	public LivingEntity( Element xElement ){
		
		super( xElement );
		
	}

	public StatEntries getStatEntries(){
		
		return mStatEntries;
		
	}

	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		Element statValues = xElement.getChildByName( "StatEntries" );
		if( statValues == null ){
			log( "No StatEntries defined!" );
			return;
		}
		setStatValues( new StatEntries( statValues ) );
		
	}
	
	private void setStatValues( StatEntries xStatEntries ){
		
		mStatEntries = xStatEntries;
		log( "StatValues defined (" + mStatEntries + ")" );
		
	}
	
	@Override
	protected void init(){
		
	}
	
}
