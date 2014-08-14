package lezli.hexengine.core.gametable.player.ai;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.PGameTable;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.player.EventPlayer;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.gametable.scriptable.Action;
import lezli.hexengine.core.gametable.scriptable.PBuildingScriptable;
import lezli.hexengine.core.gametable.scriptable.PGameTableScriptable;
import lezli.hexengine.core.gametable.scriptable.PLivingPlayableScriptable;
import lezli.hexengine.core.gametable.scriptable.PProducePlayableScriptable;
import lezli.hexengine.core.gametable.scriptable.PUnitScriptable;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.building.PBuildingReg;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.playables.unit.skills.PSkill;

import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class AIPlayer extends EventPlayer implements Action{

	LuaClosure mLua;
	private Prototype mPrototype;
	private boolean mTurn;
	private boolean mEvented;
	private boolean mCalled;

	public AIPlayer( String xName, PGameTable xGameTable, HexEngine xEngine ){
		
		super( xName, xGameTable, xEngine );
		
		mPrototype = null;
		
		try {
		
			mPrototype = LuaC.compile( new ByteArrayInputStream( engine().entitiesHolder().getScripts().get( "ai_core.lua" ) ), "script" );
			mLua = new LuaClosure( mPrototype, JsePlatform.debugGlobals() );
			mLua.getfenv().rawset( "GameTable", CoerceJavaToLua.coerce( ( PGameTableScriptable ) getGameTable() ) );
			mLua.getfenv().rawset( "Action", CoerceJavaToLua.coerce( ( Action ) this ) );
			
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		
	}
	
	@Override
	public boolean playTurn(){
		
		mTurn = false;
		mEvented = false;
		mCalled = true;
		mLua.call();
		mCalled = false;
		
		if( !mEvented )
			endTurn();
		
		return mTurn;
		
	}
	
	protected boolean ready(){
		
		return mCalled == false;
		
	}
	
	@Override
	protected void event( GameEvent xEvent ){

		if( mEvented )
			return;
		
		mEvented = true;
		
		if( xEvent.type == GameEvent.TURNED )
			mTurn = true;
		
		super.event( xEvent );
		
	}
	
	@Override
	public void move( PUnitScriptable xUnit, int xX, int xY ){

		PUnit unit = ( PUnit ) xUnit;
		addMoveEvent( unit.getTileX(), unit.getTileY(), xX, xY, unit.getPID() );
		
	}
	
	@Override
	public void skill( PLivingPlayableScriptable xUnitFrom, int xX, int xY, PSkillScriptable xSkill ){
		
		PUnit unitFrom = ( PUnit ) xUnitFrom;
		PSkill skill = ( PSkill ) xSkill;
		
		addSkillEvent( unitFrom.getPID(), xX, xY, skill.getEntityID() );
		
	}
	
	@Override
	public void build( int xX, int xY, PUnitScriptable xUnit, PBuildingScriptable xBuilding ){

		addBuildingEvent( xX, xY,( ( PUnit ) xUnit ).getPID() ,( ( PBuildingReg ) xBuilding ).getBuilding() ); 
		
	}

	@Override
	public void produce( PProducePlayableScriptable< ?, ? > xUnit, PBuildingScriptable xBuilding ){
		
		addProduceEvent( ( ( PProducePlayable< ?, ? > ) xUnit ).getEntityID(), ( ( PBuilding ) xBuilding ).getPID() );
		
	}
	
	@Override
	public void endTurn(){
		
		turnEvent();
	
	}
	
}