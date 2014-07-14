package lezli.hexengine.core.structure.entities.building;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hexengine.core.structure.entities.LivingEntity;
import lezli.hexengine.core.structure.entities.cost.ResourceCost;
import lezli.hexengine.core.structure.entities.stat.StatEntries;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Building extends LivingEntity{

	private int mDuration;
	private AcceptedTiles mAcceptedTiles;
	
	private HashMap< String, ResourceCost > mResourceProduces;
	private HashMap< String, StatUpgrade > mStatUpgrades;
	private HashMap< String, SkillUpgrade > mSkillUpgrades;
	private HashMap< String, UnitProduce > mUnitProduces;
	
	private StatEntries mStatEntries;
	
	public Building( String xFileName ){
		
		super( xFileName );
		
	}
	
	public int getDuration(){
		
		return mDuration;
		
	}
	
	public ArrayList< ResourceCost > getResourceProduces(){
		
		return new ArrayList< ResourceCost >( mResourceProduces.values() );
		
	}
	
	public AcceptedTiles getAcceptedTiles(){
		
		return mAcceptedTiles;
		
	}
	
	public StatUpgrade getStatUpgrade( String xId ){
		
		return mStatUpgrades.get( xId );
		
	}
	
	public ArrayList< StatUpgrade > getStatUpgrades(){

		return new ArrayList<StatUpgrade>( mStatUpgrades.values() );
		
	}
	
	public SkillUpgrade getSkillUpgrade( String xId ){
		
		return mSkillUpgrades.get( xId );
		
	}
	
	public ArrayList< SkillUpgrade > getSkillUpgrades(){

		return new ArrayList<SkillUpgrade>( mSkillUpgrades.values() );
		
	}

	public StatEntries getStatEntries(){
		
		return mStatEntries;
		
	}
	
	public UnitProduce getUnitProduce( String xId ){
		
		return mUnitProduces.get( xId );
		
	}
	
	public ArrayList< UnitProduce > getUnitproduces(){

		return new ArrayList< UnitProduce >( mUnitProduces.values() );
		
	}
	
	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setDuration( xElement.getInt( "duration" ) );
		setAcceptedTiles( new AcceptedTiles( xElement.getChildByName( "AcceptedTiles" ) ) );

		Element resProds = xElement.getChildByName( "Produce" );
		if( resProds != null ){
			
			
			for( Element resProd: resProds.getChildrenByName( "ResourceCost" ) )
				addNewResourceProduce( new ResourceCost( resProd ) );
			
		}

		for( Element statUpgrade: xElement.getChildrenByName( "StatUpgrade" ) )
			addNewStatUpgrade( new StatUpgrade( statUpgrade ) );
		
		for( Element skillUpgrade: xElement.getChildrenByName( "SkillUpgrade" ) )
			addNewSkillUpgrade( new SkillUpgrade( skillUpgrade ) );
		
		for( Element unitProduced: xElement.getChildrenByName( "UnitProduced" ) )
			addNewUnitProduce( new UnitProduce( unitProduced ) );
		
		Element statValues = xElement.getChildByName( "StatEntries" );
		if( statValues == null ){
			log( "No StatEntries defined!" );
			return;
		}
		setStatValues( new StatEntries( statValues ) );
		
	}
	
	private void setDuration( int xDuration ){
		
		mDuration = xDuration;
		log( "Duration set (" + mDuration + ")" );
		
	}
	
	private void setAcceptedTiles( AcceptedTiles xAcceptedTiles ){
		
		mAcceptedTiles = xAcceptedTiles;
		log( "AcceptedTiles set (" + mAcceptedTiles + ")" );
		
	}
	
	private void setStatValues( StatEntries xStatEntries ){
		
		mStatEntries = xStatEntries;
		log( "StatValues defined (" + mStatEntries + ")" );
		
	}
	
	private void addNewResourceProduce( ResourceCost xResourceProduce ){
		
		mResourceProduces.put( xResourceProduce.getID(), xResourceProduce );
		log( "Resource produce added (" + xResourceProduce + ")" );
		
	}
	
	private void addNewStatUpgrade( StatUpgrade xStatUpgrade ){
		
		mStatUpgrades.put( xStatUpgrade.getID(), xStatUpgrade );
		log( "StatUpgrade added (" + xStatUpgrade + ";" + mStatUpgrades.size() + ")" );
		
	}

	private void addNewSkillUpgrade( SkillUpgrade xSkillUpgrade ){
		
		mSkillUpgrades.put( xSkillUpgrade.getID(), xSkillUpgrade );
		log( "SkillUpgrade added (" + xSkillUpgrade + ";" + mSkillUpgrades.size() + ")" );
		
	}
	
	private void addNewUnitProduce( UnitProduce xUnitProduced ){
		
		mUnitProduces.put( xUnitProduced.getUnit(), xUnitProduced );
		log( "UnitProduced added (" + xUnitProduced + ";" + mUnitProduces.size() +  ")" );
		
	}
	
	@Override
	protected void init(){
		
		mResourceProduces = new HashMap< String, ResourceCost >();
		mStatUpgrades = new HashMap< String, StatUpgrade >();
		mSkillUpgrades = new HashMap< String, SkillUpgrade >();
		mUnitProduces = new HashMap< String, UnitProduce >();
		
	}

}
