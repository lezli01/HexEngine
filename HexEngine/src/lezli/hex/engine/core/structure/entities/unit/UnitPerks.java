package lezli.hex.engine.core.structure.entities.unit;

import java.util.ArrayList;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hex.engine.core.structure.entities.Entity;

public class UnitPerks extends Entity{

	public static final int ALLOW_ALL		= 1 << 0;
	public static final int DISABLE_ALL		= 1 << 1;
	
	private int mRule;
	private ArrayList< String > mEnabledTiles;
	private ArrayList< String > mDisabledTiles;
	
	public UnitPerks( String xFileName ){
		
		super( xFileName );
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		Element mapTiles = xElement.getChildByName( "MapTiles" );
		
		if( mapTiles.getAttribute( "rule" ).equals( "@DISABLE_ALL" ) )
			mRule = DISABLE_ALL;
		else
			mRule = ALLOW_ALL;
		
		for( Element rule: mapTiles.getChildrenByName( "Enable" ) )
			mEnabledTiles.add( rule.getAttribute( "maptile" ) );
	
		for( Element rule: mapTiles.getChildrenByName( "Disable" ) )
			mDisabledTiles.add( rule.getAttribute( "maptile" ) );
	
	}

	public int getRule(){
		
		return mRule;
		
	}
	
	public ArrayList< String > getEnabledTiles(){
		
		return mEnabledTiles;
		
	}
	
	public ArrayList< String > getDisabledTiles(){
		
		return mDisabledTiles;
		
	}
	
	public static UnitPerks createDefault(){
		
		return new UnitPerks( "hex.engine/units/default_perks.xml" );
	
	}
	
	@Override
	protected void init(){
		
		mRule = ALLOW_ALL;
		
		mEnabledTiles = new ArrayList< String >();
		mDisabledTiles = new ArrayList< String >();
		
	}

}
