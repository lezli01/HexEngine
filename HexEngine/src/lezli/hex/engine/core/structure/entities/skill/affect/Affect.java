package lezli.hex.engine.core.structure.entities.skill.affect;

import lezli.hex.engine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Affect extends Entity{

	private int mWhen;
	private String mStat;
	private String mValue;
	private String mValueString;
	
	public Affect( Element xElement ) {
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse(xElement);
		
		setWhen( xElement.getInt( "when" ) );
		setStat( xElement.getAttribute( "stat" ) );
		setValue( xElement.getAttribute( "value" ) );

		try{
			
			mValueString = xElement.get( "valueString" );
			
		}
		catch( Exception e ){
			
			
			
		}
	}
	
	private void setWhen( int xWhen ){
		
		mWhen = xWhen;
		log( "Duration set (" + mWhen + ")" );
		
	}
	
	public String getAffectValueString(){
		
		return mValueString;
		
	}
	
	public String getValue(){
		
		return mValue;
		
	}

	public int getWhen(){
		
		return mWhen;
		
	}
	
	public String getStat(){
		
		return mStat;
		
	}
	
	private void setValue( String xMinVal ){
		
		mValue = xMinVal;
		log( "Minimum value set (" + mValue + ")" );
		
	}
	
	private void setStat( String xStat ){
		
		mStat = xStat;
		log( "Stat set (" + mStat + ")" );
		
	}
	
	@Override
	protected void init() {
	}

}
