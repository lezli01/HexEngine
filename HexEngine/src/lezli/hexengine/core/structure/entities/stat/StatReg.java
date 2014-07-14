package lezli.hexengine.core.structure.entities.stat;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hexengine.core.structure.entities.Entity;

public class StatReg extends Entity{

	private String mStat;
	private int mValue;
	
	public StatReg( Element xElement ){
		
		super( xElement );

	}
	
	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );

		setStat( xElement.getAttribute( "stat" ) );
		setValue( xElement.getInt( "value" ) );
		
	}
	
	public String getStat(){
		
		return mStat;
		
	}
	
	public int getValue(){
		
		return mValue;
		
	}

	private void setStat( String xStat ){
		
		mStat = xStat;
		log( "Stat set to (" + mStat + ")" );
		
	}
	
	private void setValue( int xValue ){
		
		mValue = xValue;
		log( "Value set to (" + mValue + ")" );
		
	}
	

	@Override
	protected void init(){
		
	}
	
	@Override
	public String toString() {

		return "[" + mStat + ":" + mValue + "]";
		
	}

}
