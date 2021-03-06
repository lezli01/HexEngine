package lezli.hex.engine.core.playables.unit.skills;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.HexEngine.HexEngineProperties;
import lezli.hex.engine.core.gametable.script.PSkillScriptable;
import lezli.hex.engine.core.playables.LivingPlayable;
import lezli.hex.engine.core.playables.building.produce.PSkillUpgrade;
import lezli.hex.engine.core.playables.graphics.PGraphicalPlayable;
import lezli.hex.engine.core.playables.map.tile.PTile;
import lezli.hex.engine.core.playables.unit.PUnit;
import lezli.hex.engine.core.structure.entities.skill.Skill;
import lezli.hex.engine.core.structure.entities.skill.affect.Affect;
import lezli.hex.engine.moddable.playables.HESkill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class PSkill extends PGraphicalPlayable< Skill > implements PSkillScriptable, HESkill{

	private int mRange;
	private int mArea;
	
	private Vector2 mFrom;
	private Vector2 mTo;
	private float mDistance;
	private float mCurrentDistance;
	
	private float mProjWidth;
	private float mProjHeight;
	private float mSpeed;
	private String mUnitAnimation;
	
	private int mCooldown;
	private int mCurrentCooldown;
	
	private float mCalcSpeed;
	
	private ArrayList< Affect > mAffects;
	
	private HashMap< PUnit, ArrayList< LivingPlayable< ? > > > mUnitsToAffect;
	
	private PUnit mHolder;
	
	private ArrayList< Integer > mAffectsOn;
	
	@SuppressWarnings("unchecked")
	public PSkill( Skill xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mAffectsOn = xEntity.getAffectsOn();
		
		mRange = xEntity.getRange();
		mArea = xEntity.getArea();

		mCurrentDistance = 0;
		mDistance = 0;
		mCurrentCooldown = 0;
		
		mSpeed = xEntity.getSpeed();
		mProjWidth = xEntity.getProjectileWidth();
		mProjHeight = xEntity.getProjectileHeight();
		mUnitAnimation = xEntity.getUnitAnimation();
		mCooldown = xEntity.getCooldown();
		
		mAffects = ( ArrayList< Affect > ) xEntity.getAffects().clone();
		
		mUnitsToAffect = new HashMap< PUnit, ArrayList< LivingPlayable< ? > > >();
		
		setDefaultAnimation( null );
		
	}
	
	public PSkill( Skill xEntity, PUnit xHolder, HexEngine xEngine ){
		
		this( xEntity, xEngine );
		
		setHolder( xHolder );
		
	}
	
	public PSkill( PSkillUpgrade xUpgrade, HexEngine xEngine ){
		
		this( xEngine.entitiesHolder().getSkillManager().get( xUpgrade.getSkill() ), xEngine );
		
	}
	
	public PSkill( PSkillUpgrade xUpgrade, PUnit xHolder, HexEngine xEngine ){
		
		this( xEngine.entitiesHolder().getSkillManager().get( xUpgrade.getSkill() ), xEngine );
		
		setHolder( xHolder );
		
	}
	
	public String getAnim(){
		
		return mUnitAnimation;
	
	}

	public ArrayList< Affect > getAffects(){
		
		return mAffects;
		
	}

	public PUnit getHolder(){
		
		return mHolder;
		
	}

	public int getCooldownStatus(){
		
		return mCurrentCooldown;
		
	}

	public int getRange(){
		
		return mRange;
		
	}

	public int getArea(){//
		
		return mArea;
		
	}

	public void setHolder( PUnit xHolder ){
		
		mHolder = xHolder;
		
	}
	
	public void cast( final PTile xFromTile, final PUnit xUnitFrom, final PTile xToTile, ArrayList< LivingPlayable< ? > > xLivingsTo, ArrayList< PTile > xAreaTiles ){

		if( isCooldown() ){
			log( "Skill is on cooldown (" + getCooldownStatus() + ")" );
			return;
		}
		
		if( xFromTile.distance( xToTile ) > getRange() ){
			log( "Distance too big (" + xFromTile.distance( xToTile ) + ">" + getRange() + ")" );
			return;
		}
		
		mCalcSpeed = xToTile.getWidth() * mSpeed;
		setWidth( xToTile.getWidth() * mProjWidth );
		setHeight( xToTile.getHeight() * mProjHeight );
		
		
		mFrom = xFromTile.getCenter();
		mTo = xToTile.getCenter();
		
		mFrom.x -= getWidth() / 2.0f;
		mFrom.y -= getHeight() / 2.0f; 
		
		mTo.x -= getWidth() / 2.0f;
		mTo.y -= getHeight() / 2.0f;
		
		mCurrentDistance = 0;
		mDistance = mFrom.dst( mTo );

		setRotation( new Vector2( mFrom ).sub( mTo ).angle() );

		xUnitFrom.setCasted( this );
		
		mUnitsToAffect.put( xUnitFrom, xLivingsTo );
		
		mCurrentCooldown = mCooldown;
		
		if( xUnitFrom.getTileX() == xToTile.getTileX() && xUnitFrom.getTileY() == xToTile.getTileY() ){

			setX( mTo.x );
			setY( mTo.y );
			mCurrentDistance = 0;
			mDistance = 0;
			setDefaultAnimation( "@PROJECTILE" );
			addAnimation( "@ARRIVAL" );
			
		}
		
		if( ( Boolean ) engine().getProperties().getProperty( HexEngineProperties.PROP_INSTANT_CAST ) ){
			
			setX( mTo.x );
			setY( mTo.y );
			mCurrentDistance = 0;
			mDistance = 0;
			setDefaultAnimation( null );

		}
		
	}
	
	public boolean isProjectileActive(){
		
		return mCurrentDistance < mDistance;
		
	}
	
	public boolean isArrived(){
		
		if( currentAnimation() == null )
			return true;
		
		return !currentAnimation().equals( "@ARRIVAL" );
		
	}

	public boolean isCooldown(){
		
		return mCurrentCooldown != 0;
		
	}
	
	public boolean affectsOn( int affect ){
		
		return mAffectsOn.contains( affect );
		
	}
	
	int i;
	
	@Override
	public void update(){
		
		super.update();
		
		if( isProjectileActive() ){
		
			setDefaultAnimation( "@PROJECTILE" );
			float movedDistance = Gdx.graphics.getDeltaTime() * mCalcSpeed;
			mCurrentDistance += movedDistance;
			
			if( mCurrentDistance >= mDistance ){
				
				setDefaultAnimation( null );
				mCurrentDistance = mDistance;
				addAnimation( "@ARRIVAL" );
				
			}
			
			float a = mCurrentDistance / mDistance;
			
			setX( Interpolation.linear.apply( mFrom.x, mTo.x, a ) );
			setY( Interpolation.linear.apply( mFrom.y, mTo.y, a ) );
		
		}
		else{
			
			setRotation( 0.0f );
			
			if( mUnitsToAffect.size() == 0 )
				return;
			
			for( i = 0; i < mUnitsToAffect.size(); i++ )
			
			for( PUnit unitFrom: mUnitsToAffect.keySet() ){
				
				for( LivingPlayable< ? > unitTo: mUnitsToAffect.get( unitFrom ) ){
					
					putAffectsOnLiving( unitFrom, unitTo );
					
				}
				
			}
			
			mUnitsToAffect.clear();
			
		}
		
	}

	@Override
	public void turn(){
		
		if( mCurrentCooldown > 0 )
			mCurrentCooldown--;
		
	}

	protected void animationEnded( String xID ){
	}

	private void putAffectsOnLiving( PUnit xUnitFrom, LivingPlayable< ? > xLivingTo ){
		
		for( Affect affect: mAffects ){
			
			PAffect pAffect = new PAffect( affect, engine() );
			pAffect.apply( this, xUnitFrom, xLivingTo );
			
		}
		
	}
	
	@Override
	public int cooldown(){
		
		return getCooldownStatus();
	
	}
	
	@Override
	public int range(){

		return getRange();
		
	}

}
