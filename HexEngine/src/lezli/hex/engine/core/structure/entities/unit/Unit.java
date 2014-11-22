package lezli.hex.engine.core.structure.entities.unit;


import lezli.hex.engine.core.structure.entities.LivingEntity;
import lezli.hex.engine.core.structure.entities.building.BuildingEntries;
import lezli.hex.engine.core.structure.entities.skill.SkillEntries;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Unit extends LivingEntity{

	private String mRace;
	private float mWidth, mHeight;
	
	private SkillEntries mSkillEntries;
	private BuildingEntries mBuildingEntries;
	
	private String mUnitPerks;
	
	public Unit( Element xElement ){
		
		super( xElement );
		
	}
	
	public Unit( String xFileName ){
		
		super( xFileName );
		
	}
	
	public String getPerks(){
		
		return mUnitPerks;
		
	}
	
	public String getRace(){
		
		return mRace;
		
	}
	
	public float getWidth(){
		
		return mWidth;
		
	}
	
	public float getHeight(){
		
		return mHeight;
		
	}
	
	public SkillEntries getSkillEntries(){
		
		return mSkillEntries;
		
	}
	
	public BuildingEntries getBuildingEntries(){
		
		return mBuildingEntries;
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse(xElement);

		mUnitPerks = xElement.getAttribute( "perks", "@default_perks" );
		
		try{
			
			setRace( xElement.getAttribute( "race" ) );
			
		}catch( Exception e ){
			
			log( "No attribute [race]!" );
			
		}
		
		try{
			
			setWidth( xElement.getFloat( "width" ) );
			
		}catch( Exception e ){
			
			log( "No attribute [width]!" );
			
		}

		try{
			
			setHeight( xElement.getFloat( "height" ) );
			
		}catch( Exception e ){
			
			log( "No attribute [height]!" );
			
		}
		
		Element skillsEntries = xElement.getChildByName( "SkillEntries" );
		if( skillsEntries == null ){
			log( "No SkillEntries defined!" );
			return;
		}
		setSkillEntries( new SkillEntries( skillsEntries ) );
		
		Element buildingEntries = xElement.getChildByName( "BuildingEntries" );
		if( buildingEntries == null ){
			log( "No BuildingEntries defined." );
			return;
		}
		setBuildingEntries( new BuildingEntries( buildingEntries ) );
		
	}

	private void setRace( String xRace ){
		
		mRace = xRace;
		log( "Race set to (" + mRace + ")" );
		
	}
	
	private void setWidth( float xWidth ){
		
		mWidth = xWidth;
		log( "Width set (" + mWidth + ")" );
		
	}
	
	private void setHeight( float xHeight ){
		
		mHeight = xHeight;
		log( "Height set (" + mHeight + ")" );
		
	}
	
	private void setSkillEntries( SkillEntries xSkillEntries ){
		
		mSkillEntries = xSkillEntries;
		log( "SkillEntries defined (" + mSkillEntries + ")" );
		
	}
	
	private void setBuildingEntries( BuildingEntries xBuildingEntries ){
		
		mBuildingEntries = xBuildingEntries;
		log( "BuildingEntries defined (" + mBuildingEntries + ")" );
		
	}
	
	@Override
	protected void init() {
		
	}

}
