package lezli.hexengine.core.playables.unit.skills;

import java.io.IOException;
import java.util.HashMap;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.IntScript;
import lezli.hexengine.core.gametable.script.PLivingPlayableScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.LivingPlayable;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.structure.entities.gametable.Holding;
import lezli.hexengine.core.structure.entities.skill.affect.Affect;

import com.badlogic.gdx.utils.XmlWriter;

public class PAffect extends Playable< Affect >{

	private static HashMap< String, IntScript > SCRIPTS = new HashMap< String, IntScript >();
	
	private LivingPlayable< ? > mLivingTo;
	private PUnit mUnitFrom;
	private String mSkill;
	
	private int mWhen;
	private String mStat;
	
	private String mValueString;
	private String mValueExp;
	
	private int mValue;
	
	public PAffect( Affect xEntity ){
	
		super( xEntity );
		
		mValueString = xEntity.getAffectValueString();
		
		mWhen = xEntity.getWhen();
		mValueExp = xEntity.getValue();
		mStat = xEntity.getStat();
		
		checkScript();
		
	}
	
	@Override
	public String getName(){
	
		return HexEngine.EntitiesHolder.getCommon().getStats().get( mStat ).getName();
		
	}

	public int getValue(){
	
		return mValue;
		
	}

	public int getWhen(){
		
		return mWhen;
		
	}

	public String getStat(){
		
		return mStat;
		
	}

	public String getSkill(){
		
		return mSkill;
		
	}

	public void setLiving( LivingPlayable< ? > xLivingTo ){
		
		mLivingTo = xLivingTo;
		
	}
	
	public void init( PSkill xSkill, PUnit xUnitFrom, LivingPlayable< ? > xLivingTo ){
		
		mUnitFrom = xUnitFrom;

		if( xSkill != null )
			mSkill = xSkill.getEntityID();
		else
			mSkill = "NAS";

		mLivingTo = xLivingTo;
		
	}
	
	public void apply( PSkill xSkill, PUnit xUnitFrom, LivingPlayable< ? > xLivingTo ){

		mUnitFrom = xUnitFrom;

		if( xLivingTo == null )
			return;
		
		if( xSkill != null )
			mSkill = xSkill.getEntityID();
		else
			mSkill = "NAS";

		mLivingTo = xLivingTo;

		calculateValues();
		
		mLivingTo.putOnAffect( this );
		
		affect();
		
	}
	
	public boolean affect(){
		
		mWhen--;
		
		if( mWhen == 0 ){
		
			mLivingTo.addStat( mStat, mValue );
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean finished(){
		
		return mWhen == 0;
		
	}
	
	@Override
	public void load( Holding holding ){

		super.load( holding );
	
		if( hasSaved( holding, "when" ) )
			mWhen = Integer.parseInt( holding.getValues().get( "when" ) );
		if( hasSaved( holding, "stat" ) )
			mStat = holding.getValues().get( "stat" );
		if( hasSaved( holding, "value" ) )
			mValue = Integer.parseInt( holding.getValues().get( "value" ) );
		if( hasSaved( holding, "skill" ) )
			mSkill = holding.getValues().get( "skill" );
	}
	
	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{

		super.save( xmlWriter );
	
		xmlWriter.attribute( "skill", getSkill() ).
				  attribute( "when", getWhen() ).
		          attribute( "stat", getStat() ).
		          attribute( "value", getValue() );
		
	}

	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
		affect();
		
	}

	public String getAffectValue(){
		
		String val = "0";
		
		IntScript script = SCRIPTS.get( mValueExp );
		
		if( mUnitFrom != null ){
			
			script.rawset( "UnitFrom", ( PUnitScriptable ) mUnitFrom );
			
			if( mLivingTo != null ){
				
				script.rawset( "LivingTo", ( PLivingPlayableScriptable ) mLivingTo );
				
			}
			
			try{
				
				val = Integer.toString( script.call() );
					
			}catch( Exception e ){

				return mValueString;
				
			}
		}
		
		return val;
		
	}
	
	private void checkScript(){
		
		if( !SCRIPTS.containsKey( mValueExp ) )
			SCRIPTS.put( mValueExp, new IntScript( mValueExp ) );
		
	}

	private void calculateValues(){
		
		IntScript script = SCRIPTS.get( mValueExp );
		
		if( mUnitFrom != null )
			script.rawset( "UnitFrom", ( PUnitScriptable ) mUnitFrom );
		
		if( mLivingTo != null )
			script.rawset( "LivingTo", ( PLivingPlayableScriptable ) mLivingTo );

		mValue = script.call();
		
	}
	
}
