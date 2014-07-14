package lezli.hexengine.core.playables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PLivingPlayableScriptable;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.playables.unit.skills.PAffect;
import lezli.hexengine.core.playables.unit.stats.PStatReg;
import lezli.hexengine.core.structure.entities.LivingEntity;
import lezli.hexengine.core.structure.entities.gametable.Holding;
import lezli.hexengine.core.structure.entities.stat.StatReg;

import com.badlogic.gdx.utils.XmlWriter;

public abstract class LivingPlayable< T extends LivingEntity > extends GraphicalPlayable< T > implements PLivingPlayableScriptable{

	private ArrayList< PAffect > mAffects;
	private boolean mDead;
	private boolean mDying;
	
	private HashMap< String, PStatReg > mStatRegs;
	
	protected abstract void die();

	public LivingPlayable( T xEntity ){
		
		super( xEntity );
		
		mStatRegs = new HashMap< String, PStatReg >();
		for( StatReg statReg: xEntity.getStatEntries().getAll() )
			mStatRegs.put( statReg.getStat(), new PStatReg( statReg ) );
		
		mAffects = new ArrayList< PAffect >();
		mDead = false;
		mDying = false;
		
	}
	
	public void setStat( String stat, int value ){
		
		if( mStatRegs.containsKey( stat ) )
			mStatRegs.get( stat ).setValue( value );
	
		if( getHitPoints() <= 0 ){
			
			setDying();
			die();
			
		}
		
	}
	
	public HashMap< String, PStatReg > getStatRegs(){
		
		return mStatRegs;
		
	}
	
	public ArrayList< PAffect > getAffects(){
		
		return mAffects;
		
	}
	
	public void clearAffects(){
		
		Iterator< PAffect > affectIter = mAffects.iterator();
		while( affectIter.hasNext() ){
			
			if( affectIter.next().finished() )
				affectIter.remove();
			
		}
		
	}

	public int getMaxHitPoints(){
		
		return statValue( "@MAX_HIT_POINTS" );
		
	}

	public int getHitPoints(){
	
		return statValue( "@HIT_POINTS" );
		
	}
	
	public int getSpeed(){
		
		return statValue( "@MAX_SPEED" );
		
	}
	
	public int getCurrentSpeed(){
		
		return statValue( "@SPEED" );
		
	}

	public int statValue( String xStatID ){
		
		if( getStatRegs().containsKey( xStatID ) == false )
			return 0;
		
		return getStatRegs().get( xStatID ).getValue();
		
	}
	
	public void addHitPoints( int xHitPoints ){
		
		addStat( "@HIT_POINTS", xHitPoints );
		
	}
	
	@Override
	public void update() {

		super.update();
	
	}
	
	public boolean isDying(){
		
		return mDying;
		
	}
	
	public boolean isDead(){
		
		return mDead;
		
	}
	
	public void addStat( final String xStat, final int xValue ){

		if( xStat.equals( "@HIT_POINTS" ) && xValue < 0 )
			addAnimation( "@DAMAGED" );
		
		if( mStatRegs.containsKey( xStat ) )
			mStatRegs.get( xStat ).addValue( xValue );

		if( getHitPoints() <= 0 ){
			
			setDying();
			die();
			
		}
		
	}

	public void putOnAffect( PAffect xAffect ){
		
		xAffect.setLiving( this );
		mAffects.add( xAffect );
		
	}
	
	public boolean hasAffects(){
		
		return mAffects.size() > 0;
		
	}
	
	@Override
	public void turn(){

		setStat( "@SPEED", statValue( "@MAX_SPEED" ) );
		
		updateAffects();
		
		Iterator< PAffect > affectIter = mAffects.iterator();
		while( affectIter.hasNext() ){
			
			PAffect affect = affectIter.next();
			affect.turn();
			
		}
		
		updateAffects();
		
	}

	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
	
		super.save( xmlWriter );
		
		for( PStatReg statReg: getStatRegs().values() )
			statReg.savep( xmlWriter );
		
		for( PAffect affect: getAffects() )
			affect.savep( xmlWriter );
	
	}

	@Override
	public void load( Holding holding ){
	
		super.load( holding );
	
		for( Holding h: holding.getHoldings() ){
			
			if( h.getType().equals( "PStatReg" ) )
				setStat( h.getValues().get( "stat" ), Integer.parseInt( h.getValues().get( "value" ) ) );
			
			if( h.getType().equals( "PAffect" ) ){
				
				PAffect affect = new PAffect( HexEngine.EntitiesHolder.getSkillManager().get( h.getValues().get( "skill" ) ).getAffect( h.getValues().get( "id" ) ) );
				affect.load( h );
				putOnAffect( affect );
				
			}
			
		}
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}

	protected void setDead(){
		
		mDead = true;
		mDying = false;
		
	}

	protected void setDying(){
		
		mDying = true;
		
	}

	private void updateAffects(){
		
		Iterator< PAffect > affectIter = mAffects.iterator();
		while( affectIter.hasNext() ){
			
			if( affectIter.next().finished() )
				affectIter.remove();
			
		}
		
	}
	
	@Override
	public int stat( String xStat ){

		return statValue( xStat );
	
	}
	
	@Override
	public int hp(){

		return stat( "@HIT_POINTS" );
	
	}
	
	@Override
	public int maxHp(){

		return stat( "@MAX_HIT_POINTS" );
	
	}
	
	@Override
	public int speed(){

		return getCurrentSpeed();
		
	}
	
	@Override
	public int maxSpeed(){

		return getSpeed();
		
	}
	
}
