package lezli.hex.engine.core.gametable.player;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.PGameTable;
import lezli.hex.engine.core.gametable.event.GameEvent;
import lezli.hex.engine.core.structure.Values;

public abstract class EventPlayer extends Player{

	private PGameTable mGameTable;
	
	private ArrayList< GameEvent > mEvents;
	
	private long mTimeout;
	
	public EventPlayer( String xName, PGameTable xGameTable, HexEngine xEngine ){
		
		super( xName, xEngine );
		
		mTimeout = xEngine.entitiesHolder().getValues().getLong( Values.AI_TIMEOUT );
		
		mGameTable = xGameTable;
		
		mEvents = new ArrayList< GameEvent >();
		
	}
	
	public void timeout( long msec ){

		mTimeout = msec;
		
	}
	
	protected PGameTable getGameTable(){
		
		return mGameTable;
		
	}
	
	protected void event( GameEvent xEvent ){
		
		xEvent.playerName = getName();
		mEvents.add( xEvent );
		
	}
	
	public abstract boolean playTurn();
	
	@Override
	public void turn() {

		super.turn();

		continueTurn();
		
	}
	
	@Override
	public void continueTurn() {

		super.continueTurn();
		
		final Thread turnthread = new Thread(){
			
			public void run() {
				
				boolean turned;
			
				try {
					
					do{
						
						while( !( mGameTable.ready() && ready() ) )
								sleep( 500 );

						turned = playTurn();
						getGameTable().processGameEvents( flushEvents() );
						
					}while( !turned || !isInterrupted() );
					
				}catch( InterruptedException e ){

					//TODO LOGGING
					e.printStackTrace();
					
				}
				
			}
		
		};
		
		TimeoutThread timeoutThread = new TimeoutThread( mTimeout, new Runnable() {
			
			@Override
			public void run() {
				
				turnthread.interrupt();
				turnEvent();
				
				getGameTable().processGameEvents( flushEvents() );
				
			}
			
		});
		
		timeoutThread.start();
		turnthread.start();
		
	}
	
	protected abstract boolean ready();
	
	@SuppressWarnings("unchecked")
	public ArrayList< GameEvent > flushEvents(){
		
		ArrayList< GameEvent > clone = ( ArrayList< GameEvent > ) mEvents.clone();
		
		mEvents.clear();
		
		return clone;
		
	}
	
	protected void addSkillEvent( final String xUnitFrom, final int xToX, final int xToY, final String xSkill ){

		event( new GameEvent(){{
			type = SKILL_CASTED;
			addCoords( xToX, xToY );
			unitID = xUnitFrom;
			skillID = xSkill;
		}});
		
	}
	
	protected void addMoveEvent( final int xFromX, final int xFromY, final int xToX, final int xToY, final String xUnit ){
		event( new GameEvent(){{
			type = UNIT_MOVED;
			addCoords( xFromX, xFromY ).addCoords( xToX, xToY );
			unitID = xUnit;
		}});
	}
	
	protected void addProduceEvent( final String xProduce, final String xBuilding ){
		event( new GameEvent(){{
			type = PRODUCED;
			buildingID=xBuilding;
			produceID=xProduce;
		}});
	}
	
	protected void addBuildingEvent( final int xX, final int xY, final String xUnit, final String xBuilding ){
		event( new GameEvent(){{
			type = BUILDING_ADDED;
			addCoords( xX, xY );
			unitID = xUnit;
			buildingID = xBuilding;
		}});
	}
	
	protected void turnEvent(){
		event( new GameEvent(){{
			type = TURNED;
		}});
	}

	public static class TimeoutThread extends Thread{
		
		private boolean mToTimeout;
		private long mTimeout;
		private Runnable mRunnable;
		
		public TimeoutThread( long msec, Runnable runnable ){
			
			mTimeout = msec;
			mRunnable = runnable;
			
		}
		
		public synchronized void unset(){
			
			mToTimeout = false;
			
		}
		
		public synchronized void set(){
			
			mToTimeout = true;
			
		}
		
		public synchronized boolean isset(){
			
			return mToTimeout;
			
		}
		
		@Override
		public void run(){

			set();
			
			try{
				
				sleep( mTimeout );
			
				if( isset() )
					mRunnable.run();
				
			}catch( InterruptedException e ){

				e.printStackTrace();
			
			}
		
		}
		
	}
	
}
