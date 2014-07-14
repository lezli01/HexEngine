package lezli.hexengine.core.gametable.player;

import java.util.ArrayList;

import lezli.hexengine.core.gametable.PGameTable;
import lezli.hexengine.core.gametable.event.GameEvent;

public abstract class EventPlayer extends Player{

	private PGameTable mGameTable;
	
	private ArrayList< GameEvent > mEvents;
	
	public EventPlayer( String xName, PGameTable xGameTable ){
		
		super( xName );
		
		mGameTable = xGameTable;
		
		mEvents = new ArrayList< GameEvent >();
		
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
						
						while( !( mGameTable.ready() && ready() ) ){
							sleep( 500 );
						}

						turned = playTurn();
						getGameTable().processGameEvents( flushEvents() );
						
					}while( !turned );
					
				}catch( InterruptedException e ){

					e.printStackTrace();
				
				}
			}
		
		};

		turnthread.start();
		
	}
	
	protected abstract boolean ready();
	
	@SuppressWarnings("unchecked")
	public ArrayList< GameEvent > flushEvents(){
		
		ArrayList< GameEvent > clone = ( ArrayList< GameEvent > ) mEvents.clone();
		
		mEvents.clear();
		
		return clone;
		
	}
	
	protected void skill( final String xUnitFrom, final int xToX, final int xToY, final String xSkill ){

		event( new GameEvent(){{
			type = SKILL_CASTED;
			addCoords( xToX, xToY );
			unitID = xUnitFrom;
			skillID = xSkill;
		}});
		
	}
	
	protected void move( final int xFromX, final int xFromY, final int xToX, final int xToY, final String xUnit ){
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

}
