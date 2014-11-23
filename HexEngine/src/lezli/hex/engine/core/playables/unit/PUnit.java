package lezli.hex.engine.core.playables.unit;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.HexEngine.HexEngineProperties;
import lezli.hex.engine.core.gametable.PGameTableCamera;
import lezli.hex.engine.core.gametable.event.GameEventListener;
import lezli.hex.engine.core.gametable.script.PSkillScriptable;
import lezli.hex.engine.core.gametable.scriptable.PBuildingScriptable;
import lezli.hex.engine.core.gametable.scriptable.PUnitScriptable;
import lezli.hex.engine.core.playables.LivingPlayable;
import lezli.hex.engine.core.playables.building.PBuildingReg;
import lezli.hex.engine.core.playables.building.produce.PProducePlayable;
import lezli.hex.engine.core.playables.building.produce.PSkillUpgrade;
import lezli.hex.engine.core.playables.building.produce.PStatUpgrade;
import lezli.hex.engine.core.playables.building.produce.PUnitProduce;
import lezli.hex.engine.core.playables.map.tile.PTile;
import lezli.hex.engine.core.playables.unit.skills.PSkill;
import lezli.hex.engine.core.structure.entities.building.BuildingReg;
import lezli.hex.engine.core.structure.entities.skill.SkillReg;
import lezli.hex.engine.core.structure.entities.stat.StatReg;
import lezli.hex.engine.core.structure.entities.unit.Unit;
import lezli.hex.engine.moddable.playables.HEBuildingReg;
import lezli.hex.engine.moddable.playables.HESkill;
import lezli.hex.engine.moddable.playables.HEUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PUnit extends LivingPlayable< Unit > implements PUnitScriptable, HEUnit{

	private static final float MOVING_SPEED_X = 100.0f;
	private static final float MOVING_SPEED_Y = 175.0f;
	
	private ArrayList< Vector2 > mGotos;
	private ArrayList< PTile > mGotoTiles;
	
	private HashMap< String, PSkill > mSkills;
	private ArrayList< String > mSkillNames;
	
	private HashMap< String, PBuildingReg > mBuildingRegs;
	
	private PUnitPerks mUnitPerks;
	
	public static abstract class PUnitListener{
		
		public abstract void movedTo( PUnit xUnit, PTile xTile );
		
	}
	private PUnitListener mListener;
	
	public PUnit( Unit xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mUnitPerks = new PUnitPerks( xEngine.entitiesHolder().getUnitPerksManager().get( xEntity.getPerks() ), xEngine );
		
		mGotos = new ArrayList< Vector2 >();
		mGotoTiles = new ArrayList< PTile >();

		setDefaultAnimation( "@IDLE" );
		setCastShadow( false );
		
		mSkills = new HashMap< String, PSkill >();
		mSkillNames = new ArrayList< String >();
		for( SkillReg skillReg: xEntity.getSkillEntries().getAll() ){
			mSkills.put( skillReg.getSkill(), new PSkill( engine().entitiesHolder().getSkillManager().get( skillReg.getSkill() ), this, engine() ) );
			mSkillNames.add( skillReg.getSkill() );
		}
		mBuildingRegs = new HashMap< String, PBuildingReg >();
		if( xEntity.getBuildingEntries() == null )
			return;
		for( BuildingReg buildingReg: xEntity.getBuildingEntries().getAll() )
			mBuildingRegs.put( buildingReg.getBuilding(), new PBuildingReg( buildingReg, engine() ) );

		
	}
	
	public PUnit( PUnitProduce xProduce, HexEngine xEngine ){
		
		this( xEngine.entitiesHolder().getUnitManager().get( xProduce.getUnit() ), xEngine );
		
	}

	public void setListener( PUnitListener xListener ){
		
		mListener = xListener;
		
	}
	
	public PUnitPerks getPerks(){
		
		return mUnitPerks;
		
	}
	
	public HashMap< String, PSkill > getSkills(){
		
		return mSkills;
		
	}
	
	public HashMap< String, HESkill > allSkills(){
		
		return new HashMap< String, HESkill >( mSkills );
		
	}

	public HashMap< String, PBuildingReg > getBuildingRegs(){
		
		return mBuildingRegs;
		
	}

	public HashMap< String, HEBuildingReg > buildingRegs(){
		
		return new HashMap< String, HEBuildingReg >( mBuildingRegs );
		
	}
	
	public void setCasted( PSkill xSkill ){
		
		addAnimation( xSkill.getAnim() );
		
	}

	@Override
	public void addListener( GameEventListener xListener ){

		super.addListener( xListener );
	
		for( PSkill skill: mSkills.values() )
			skill.addListener( xListener );
		
	}
	
	public void addToPath( Vector2 xVertex, PTile xTile, boolean xSpeedConsume ){
		
		mGotos.add( 0, xVertex );
		mGotoTiles.add( 0, xTile );
		
		if( xSpeedConsume )
			addStat( "@SPEED", -1 );
		
	}
	
	public void addToPath( Vector2 xVertex, PTile xTile ){
		
		addToPath( xVertex, xTile, true );
		
	}

	public void applyUpgrade( PProducePlayable< ?, ? > xProduce ){
		
		if( xProduce instanceof PSkillUpgrade ){
			
			PSkillUpgrade skillUpgrade = ( PSkillUpgrade ) xProduce;
			
			if( !skillUpgrade.getUnit().equals( getEntityID() ) )
				return;
			
			PSkill newSkill = new PSkill( skillUpgrade, this, engine() );
			newSkill.addListeners( getListeners() );
			
			mSkills.put( skillUpgrade.getSkill(), newSkill );
			mSkillNames.add( skillUpgrade.getSkill() );
			
		}
		
		if( xProduce instanceof PStatUpgrade ){
			
			PStatUpgrade su = ( PStatUpgrade ) xProduce;
			
			if( !su.getUnit().equals( getEntityID() ) )
				return;
			
			for( StatReg statReg: su.getStatRegs().values() )
				addStat( statReg.getStat(), statReg.getValue() );
			
		}
		
	}
	
	public boolean isOnTile( PTile xTile ){
		
		return ( xTile.getTileX() == getTileX() && xTile.getTileY() == getTileY() );
		
	}
	
	public boolean isMoving(){
		
		return mGotos.size() > 0;
		
	}
	
	public boolean isBusy(){
		
		if( !currentAnimation().equals( "@IDLE" ) )
			return true;

		for( i = 0; i < mSkillNames.size(); i++ ){
			
			PSkill skill = mSkills.get( mSkillNames.get( i ) );
			
			if( skill.isProjectileActive() || !skill.isArrived() )
				return true;
		}
		
		return isMoving() || isDying();
		
	}
	
	@Override
	public void update(){
		
		super.update();
		
		float speed = getMovingSpeedX();
		
		boolean instant = ( Boolean ) engine().getProperties().getProperty( HexEngineProperties.PROP_INSTANT_MOVE );
		
		if( mGotos.size() > 0 ){

			if( instant ){
				
				while( mGotos.size() > 1 ){
					
					if( mGotoTiles.size() > mGotos.size() )
						mGotoTiles.remove( 0 );
					
					mGotos.remove( 0 );
					
				}
				
			}
			
			Vector2 current = mGotos.get( 0 );
			
			if( instant ){
			
				setX( current.x );
				setY( current.y );
				
			}
			
			if( getX() < current.x ){
				
				setX( getX() + speed );

				
				if( getX() > current.x )
					setX( current.x );
				
			}
			else if( getX() > current.x ){
				
				setX( getX() - speed );
				
				if( getX() < current.x )
					setX( current.x );
				
			}
			
			speed = getMovingSpeedY();
			
			if( getY() < current.y ){
				
				setY( getY() + speed );
				
				if( getY() > current.y )
					setY( current.y );
				
			}
			else if( getY() > current.y ){
				
				setY( getY() - speed );
				
				if( getY() < current.y )
					setY( current.y );
				
			}
			
			if( getX() == current.x && getY() == current.y ){

				if( mGotos.size() < mGotoTiles.size() ){
					
					mGotoTiles.remove( 0 );
					
					if( mListener != null )
						mListener.movedTo( this, mGotoTiles.get( 0 ) );
					
				}
				
				mGotos.remove( 0 );
				
				if( mGotos.size() == 0 )
					mGotoTiles.clear();
				
			}
			
		}
		
		updateSkills();
		updateAnimation();
		
	}

	@Override
	public void turn(){
		
		super.turn();
		
		turnSkills();

	}

	@Override
	public boolean render( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		boolean ret = super.render( xSpriteBatch, xCamera );
		
		for( i = 0; i < mSkillNames.size(); i++ )
			mSkills.get( mSkillNames.get( i ) ).render( xSpriteBatch, xCamera );
	
		return ret;
		
	}

	@Override
	protected void die(){
		
		addAnimation( "@DYING" );
		
	}

	@Override
	protected void animationEnded( String xID ){
	
		if( xID.equals( "@DYING" ) ){
	
			addAnimation( "@DEAD" );
			setDead();
			
		}
		
	}

	private void turnSkills(){
		
		for( PSkill skill: mSkills.values() )
			skill.turn();
		
	}

	private float getMovingSpeedX(){
		
		return Gdx.graphics.getDeltaTime() * MOVING_SPEED_X;
		
	}

	private float getMovingSpeedY(){
		
		return Gdx.graphics.getDeltaTime() * MOVING_SPEED_Y;
		
	}

	int i;
	
	private void updateSkills(){
		
		for( i = 0; i < mSkillNames.size(); i++ )
			mSkills.get( mSkillNames.get( i ) ).update();
		
	}

	private void updateAnimation(){
		
		if( isMoving() && currentAnimation().equals( "@IDLE" ) ){
		
			if( mGotos.size() > 0 )
				addAnimation( "@WALK" );
			
		}
		else{
			
			setDefaultAnimation( "@IDLE" );
			
		}
		
	}

	@Override
	public ArrayList< PSkillScriptable > skills(){
	
		return new ArrayList< PSkillScriptable >( getSkills().values() );
	
	}

	@Override
	public ArrayList< PBuildingScriptable > buildings() {
	
		return new ArrayList< PBuildingScriptable >( getBuildingRegs().values() );
	
	}
	
}