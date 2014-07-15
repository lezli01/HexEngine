package lezli.hexengine.core.playables.building;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.gametable.script.PBuildingScriptable;
import lezli.hexengine.core.gametable.script.PProducePlayableScriptable;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.gametable.script.PStatEntriesScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.LivingPlayable;
import lezli.hexengine.core.playables.building.produce.PProduceListener;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.playables.building.produce.PSkillUpgrade;
import lezli.hexengine.core.playables.building.produce.PStatUpgrade;
import lezli.hexengine.core.playables.building.produce.PUnitProduce;
import lezli.hexengine.core.playables.building.produce.PUpgradeProduce;
import lezli.hexengine.core.playables.common.PResource;
import lezli.hexengine.core.structure.entities.building.Building;
import lezli.hexengine.core.structure.entities.building.SkillUpgrade;
import lezli.hexengine.core.structure.entities.building.StatUpgrade;
import lezli.hexengine.core.structure.entities.building.UnitProduce;
import lezli.hexengine.core.structure.entities.cost.ResourceCost;
import lezli.hexengine.core.structure.entities.gametable.Holding;

import com.badlogic.gdx.utils.XmlWriter;

public class PBuilding extends LivingPlayable< Building > implements PBuildingScriptable{

	private int mDuration;

	private int mHitPointsPerTurn;
	
	private HashMap< String, PUnitProduce > mUnitProduces;

	private static ConcurrentHashMap< String, ConcurrentHashMap< String, PStatUpgrade > > mStatUpgrades = new ConcurrentHashMap< String, ConcurrentHashMap< String, PStatUpgrade > >();
	private static ConcurrentHashMap< String, ConcurrentHashMap< String, PSkillUpgrade > > mSkillUpgrades = new ConcurrentHashMap< String, ConcurrentHashMap< String, PSkillUpgrade > >();
	private static ConcurrentHashMap< String, ConcurrentHashMap< String, PProducePlayable< ?, ? > > > mStaticProducesByID = new ConcurrentHashMap< String, ConcurrentHashMap< String, PProducePlayable< ?, ? > > >();
	
	private ArrayList< PResource > mResourceProduces;
	private ArrayList< PBuildingListener > mListeners;

	private PProduceListener mListener = new PProduceListener() {
		
		@Override
		public void produced( PProducePlayable< ?, ? > xProduce ){

			if( xProduce instanceof PStatUpgrade ){
				mStatUpgrades.get( mBuilding ).remove( xProduce.getEntityID() );
				mStaticProducesByID.get( mBuilding ).remove( xProduce.getEntityID() );
			}
			if( xProduce instanceof PSkillUpgrade ){
				mSkillUpgrades.get( mBuilding ).remove( xProduce.getEntityID() );
				mStaticProducesByID.get( mBuilding ).remove( xProduce.getEntityID() );
			}
			
			for( PBuildingListener listener: mListeners ){
			
				if( xProduce instanceof PUnitProduce )
					listener.unitProduced( PBuilding.this, ( PUnitProduce ) xProduce );
				else
					listener.upgradeProduced( PBuilding.this, ( PUpgradeProduce< ?, ? > ) xProduce );
			
			}
			
		}
		
	};
	
	private String mBuilding;
	
	public PBuilding( Building xEntity, String xPlayer, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mBuilding = xEntity.getID() + xPlayer;
		
		mStaticProducesByID.put( mBuilding, new ConcurrentHashMap< String, PProducePlayable< ?, ? > >() );
		mSkillUpgrades.put( mBuilding, new ConcurrentHashMap< String, PSkillUpgrade >() );
		mStatUpgrades.put( mBuilding, new ConcurrentHashMap< String, PStatUpgrade >() );
		
		setDefaultAnimation( "@CONSTRUCTING" );
		
		mDuration = xEntity.getDuration();
		mHitPointsPerTurn = statValue( "@MAX_HIT_POINTS" ) / mDuration;
		
		mResourceProduces = new ArrayList< PResource >();
		for( ResourceCost resProduce: xEntity.getResourceProduces() ){

			PResource resource = new PResource( engine().entitiesHolder().getCommon().getResources().get( resProduce.getResource() ), engine() );
			resource.add( resProduce.getValue() );
			
			mResourceProduces.add( resource );
			
		}

		mUnitProduces = new HashMap< String, PUnitProduce >();
		for( UnitProduce unitProduce: xEntity.getUnitproduces() ){
			PUnitProduce pUnitProduce = new PUnitProduce( unitProduce, engine() );
			pUnitProduce.addListener( mListener );
			mUnitProduces.put( unitProduce.getID(), pUnitProduce );
		}
		
		for( StatUpgrade statUpgrade: xEntity.getStatUpgrades() ){
			PStatUpgrade pStatUpgrade = new PStatUpgrade( statUpgrade, engine() );
			pStatUpgrade.addListener( mListener );
			mStatUpgrades.get( mBuilding ).putIfAbsent( statUpgrade.getID(), pStatUpgrade );
			mStaticProducesByID.get( mBuilding ).putIfAbsent( statUpgrade.getID(), pStatUpgrade );
		}
		for( SkillUpgrade skillUpgrade: xEntity.getSkillUpgrades() ){
			PSkillUpgrade pSkillUpgrade = new PSkillUpgrade( skillUpgrade, engine() );
			pSkillUpgrade.addListener( mListener );
			mSkillUpgrades.get( mBuilding ).putIfAbsent( skillUpgrade.getID(), pSkillUpgrade );
			mStaticProducesByID.get( mBuilding ).putIfAbsent( skillUpgrade.getID(), pSkillUpgrade );
		}
		
		mListeners = new ArrayList< PBuildingListener >();
		
	}
	
	public HashMap< String, PUnitProduce > getUnitProduces(){
		
		return mUnitProduces;
		
	}

	public ConcurrentHashMap< String, PStatUpgrade > getStatUpgrades(){
		
		return mStatUpgrades.get( mBuilding );
		
	}

	public ConcurrentHashMap< String, PSkillUpgrade > getSkillUpgrades(){
		
		return mSkillUpgrades.get( mBuilding );
		
	}

	public boolean isConstructed(){
		
		return mDuration <= 0;
		
	}
	
	public void filterPlayer( Player xPlayer ){
		
		for( PUpgradeProduce< ?, ? > upgrade: xPlayer.getAlreadyUpgraded() ){
			
			mStaticProducesByID.get( mBuilding ).remove( upgrade.getEntityID() );
			mStatUpgrades.get( mBuilding ).remove( upgrade.getEntityID() );
			mSkillUpgrades.get( mBuilding ).remove( upgrade.getEntityID() );
			
		}
		
	}
	
	public void addListener( PBuildingListener xListener ){
		
		mListeners.add( xListener );
		
	}
	
	@Override
	public void load( Holding holding ){
	
		super.load( holding );
		
		for( Holding h: holding.getHoldings() ){
			
			if( h.getType().equals( "PUnitProduce" ) )
				getUnitProduces().get( h.getValues().get( "id" ) ).load( h );
			
			if( h.getType().equals( "PStatUpgrade" ) )
				getStatUpgrades().get( h.getValues().get( "id" ) ).load( h );
	
			if( h.getType().equals( "PSkillUpgrade" ) )
				getSkillUpgrades().get( h.getValues().get( "id" ) ).load( h );
	
			if( h.getType().equals( "Attributes" ) ){
				
				mDuration = Integer.parseInt( h.getValues().get( "duration" ) );
				
			}
			
		}
	
		if( mDuration <= 0 )
			setDefaultAnimation( "@IDLE" );
		
	}

	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
	
		super.save( xmlWriter );
	
		xmlWriter.element( "Attributes" ).
					attribute( "duration", mDuration ).
				  pop();
		
		for( PUnitProduce unitProduce: getUnitProduces().values() ){
			
			if( unitProduce.isProducing() )
				unitProduce.savep( xmlWriter );
			
		}
		
		for( PProducePlayable< ?, ? > produce: mStaticProducesByID.get( mBuilding ).values() ){
			
			if( produce.isProducing() )
				produce.savep( xmlWriter );
			
		}
		
	}

	@Override
	public void update() {
	
		super.update();
	
		if( getHitPoints() < getMaxHitPoints() && isConstructed() )
			setDefaultAnimation( "@DAMAGED" );
		
		if( getHitPoints() == getMaxHitPoints() )
			setDefaultAnimation( "@IDLE" );
		
	}

	@Override
	public void turn(){

		super.turn();
		
		if( mDuration > 0 ){
			addHitPoints( mHitPointsPerTurn );
			if( getMaxHitPoints() < getHitPoints() )
				setStat( "@HIT_POINTS", getMaxHitPoints() );
		}

		if( mDuration >= 0 ){
			mDuration--;
		}

		if( mDuration <= 0 )
			setDefaultAnimation( "@IDLE" );
		
		
		if( mDuration < 0 ){
		
			for( PUnitProduce unitProduce: mUnitProduces.values() )
				unitProduce.turn();
			
			for( PProducePlayable< ?, ? > produce: mStaticProducesByID.get( mBuilding ).values() )
				produce.turn();
		
		}
		
	}
	
	public PProducePlayable< ?, ? > getProduce( String xEntityID ){
		
		if( mUnitProduces.containsKey( xEntityID ) )
			return mUnitProduces.get( xEntityID );
		
		return mStaticProducesByID.get( mBuilding ).get( xEntityID );
		
	}
	
	public ArrayList< PResource > getResourceProduces(){
		
		if( mDuration <= 0 )
			return mResourceProduces;
		else 
			return null;
		
	}

	@Override
	protected void die(){
		
		addAnimation( "@DEMOLISHED" );
		
	}

	@Override
	protected void animationEnded( String xID ){
		
		if( xID.equals( "@DEMOLISHED" ) ){
			
			setDead();
			
		}
		
	}

	@Override
	public ArrayList< PSkillScriptable > skills(){
		
		return new ArrayList< PSkillScriptable >( getSkillUpgrades().values() );
	
	}

	@Override
	public ArrayList< PStatEntriesScriptable > stats(){
		
		return new ArrayList< PStatEntriesScriptable >( getStatUpgrades().values() );
	
	}

	@Override
	public ArrayList< PUnitScriptable > units(){
	
		return new ArrayList< PUnitScriptable >( getUnitProduces().values() );

	}
	
	@Override
	public ArrayList< PProducePlayableScriptable< ?, ? > > produces() {

		ArrayList< PProducePlayableScriptable< ?, ? > > produces = new ArrayList< PProducePlayableScriptable< ?, ? > >( mStaticProducesByID.get( mBuilding ).values() );
		
		produces.addAll( getUnitProduces().values() );
		
		return produces;
	
	}

	@Override
	public boolean constructing() {

		return !isConstructed();
	
	}
	
}
