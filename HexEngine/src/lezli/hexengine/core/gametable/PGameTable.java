package lezli.hexengine.core.gametable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.event.GameEventListener;
import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.gametable.player.RemotePlayer;
import lezli.hexengine.core.gametable.player.ai.AIPlayer;
import lezli.hexengine.core.gametable.script.BooleanScript;
import lezli.hexengine.core.gametable.script.PBuildingScriptable;
import lezli.hexengine.core.gametable.script.PGameTableScriptable;
import lezli.hexengine.core.gametable.script.PGraphicalPlayableScriptable;
import lezli.hexengine.core.gametable.script.PLivingPlayableScriptable;
import lezli.hexengine.core.gametable.script.PMapScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.LivingPlayable;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.building.PBuildingListener;
import lezli.hexengine.core.playables.building.PBuildingReg;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.playables.building.produce.PUnitProduce;
import lezli.hexengine.core.playables.building.produce.PUpgradeProduce;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.playables.map.PMap;
import lezli.hexengine.core.playables.map.tile.PTile;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.playables.unit.PUnit.PUnitListener;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.core.structure.entities.gametable.GameTable;
import lezli.hexengine.core.structure.entities.gametable.Holding;
import lezli.hexengine.core.structure.entities.skill.Skill;
import lezli.hexengine.moddable.interfaces.HEGameTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlWriter;

public class PGameTable extends GraphicalPlayable< GameTable > implements PGameTableScriptable, HEGameTable{

	public static final float SCROLL_SPEED = 16000.0f;
	
	private HashMap< String, Player > mPlayers;
	private ArrayList< String > mPlayerNames;
	private Player mCurrentPlayer;
	private BooleanScript mLoseScript;
	
	private ArrayList< PTile > mPrevTiles;
	private PTile mSelectedTile;
	
	private ArrayList< PTile > mPathTiles;

	private ArrayList< PUnit > mUnits;
	private HashMap< String, PUnit > mUnitsByPID;
	private PUnit mSelectedPlayerUnit;
	private PUnit mSelectedUnit;
	
	private ArrayList< PTile > mSkillRangeTiles;
	private ArrayList< PTile > mSkillAreaTiles;
	private PSkill mSelectedSkill;
	
	private ArrayList< PBuilding > mBuildings;
	private HashMap< String, PBuilding > mBuildingsByPID;
	private ArrayList< PTile > mBuildRangeTiles;
	private PBuilding mSelectedBuilding;
	private PBuildingReg mSelectedBuildingReg;
	
	private ArrayList< GameEvent > mEvents;
	
	private PBuildingListener mBuildingListener = new PBuildingListener(){
		
		@Override
		public void upgradeProduced( PBuilding xBuilding, PUpgradeProduce< ?, ? > xProduce ){
			
			mCurrentPlayer.applyUpgrade( xProduce );
			
		}
		
		@Override
		public void unitProduced( PBuilding xBuilding, PUnitProduce xProduce ){
			
			addUnit( xProduce, xBuilding );
			
		}
		
	};
	
	private ArrayList< GameEvent > mRemoteEvents;
	
	private GameEventListener mGameEventListener = new GameEventListener() {
		
		@Override
		public boolean newEvent( Playable< ? > xPlayable, GameEvent xEvent ){
			
			xEvent.playerName = mCurrentPlayer.getName();
			mRemoteEvents.add( xEvent );
			
			return true;
			
		}
	};
	
	private PUnitListener mUnitListener = new PUnitListener() {
		
		@Override
		public void movedTo( PUnit xUnit, PTile xTile ){
		
			mMap.setPlayable( xTile, xUnit, false );
			
		}
		
	};
	
	private ArrayList< PGameTableEventListener > mListeners;
	private PGameTableCamera mCamera;
	private SpriteBatch mSpriteBatch;
	
	private PMap mMap;
	
	public PGameTable( GameTable xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		addListener( mGameEventListener );

		mEvents = new ArrayList< GameEvent >();
		
		mPrevTiles = new ArrayList< PTile >();
		
		mPlayers = new HashMap< String, Player >();
		mPlayerNames = new ArrayList< String >();
		mLoseScript = new BooleanScript( "lose.lua", engine() );
		mLoseScript.rawset( "GameTable", this );
		
		mPathTiles = new ArrayList< PTile >();

		mUnits = new ArrayList< PUnit >();
		mUnitsByPID = new HashMap< String, PUnit >();
		
		mSkillRangeTiles = new ArrayList< PTile >();
		mSkillAreaTiles = new ArrayList< PTile >();
		
		mBuildings = new ArrayList< PBuilding >();
		mBuildingsByPID = new HashMap< String, PBuilding >();
		mBuildRangeTiles = new ArrayList< PTile >();
		
		mListeners = new ArrayList< PGameTableEventListener >();
		
		mSpriteBatch = new SpriteBatch();
		
		mRemoteEvents = new ArrayList< GameEvent >();

		load( xEntity );
		
		mCamera = new PGameTableCamera( mMap );
		mCamera.setToOrtho( true );
		mCamera.position.set( Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, 0.0f );
		
		nextPlayer( xEntity.getCurrentPlayer() );
		mCurrentPlayer.continueTurn();
		
	}
	
	public PGameTableCamera getCamera(){
		
		return mCamera;
		
	}
	
	public void start(){
		
		if( mCurrentPlayer instanceof RemotePlayer ){
			
			eventsOff();
			
			for( PGameTableEventListener listener: mListeners ){

				if( listener.remotePlayerTurn( ( RemotePlayer ) mCurrentPlayer, mRemoteEvents ) ){
					mRemoteEvents.clear();
					break;
				
				}
				
			}
			
		}
		else{
		
			for( PGameTableEventListener listener: mListeners )
				if( listener.localPlayerTurn( mCurrentPlayer ) )
					break;
			
			eventsOn();
		
		}
		
	}
	
	public PGameTableController getController(){
		
		return new PGameTableController() {
			
			@Override
			public void chooseSkill( PSkill xSkill ){
				
				onSkillChoosen( xSkill );
				
			}
			
			@Override
			public void chooseBuilding( PBuildingReg xBuilding ){
				
				onBuildingChoosen( xBuilding );
				
			}
			
			@Override
			public void produce( PProducePlayable< ?, ? > xProduce ){
				
				PGameTable.this.produce( xProduce.getEntityID(), mSelectedBuilding.getPID() );
				
			}
			
			@Override
			public void castSkill(){

				PGameTable.this.cast( mSelectedUnit, mSelectedTile, mSelectedSkill, true );
				
			}
			
			@Override
			public void clearHighlights() {

				clearAllHighlights();
				
			}
			
		};
		
	}
	
	public Player getCurrentPlayer(){
		
		return mCurrentPlayer;
		
	}

	public PTile getTile( int xX, int xY ){
		
		return mMap.getTile( xX, xY );
		
	}

	public void addPlayer( Player xPlayer ){
		
		mPlayers.put( xPlayer.getName(), xPlayer );
		mPlayerNames.add( xPlayer.getName() );
		
		if( mCurrentPlayer == null )
			mCurrentPlayer = xPlayer;
		
	}

	public boolean removePlayer( String xName ){
	
		if( ready() ){
	
			Player removedPlayer = mPlayers.remove( xName );
			mPlayerNames.remove( xName );
			
			for( PGameTableEventListener listener: mListeners )
				if( listener.playerRemoved( removedPlayer ) )
					break;
			
			return true;
			
		}
		
		return false;
		
	}

	public void selectTile( PTile xTile ){
		
		if( xTile.isFog() || xTile.isHeed() )
			return;
		
		manageTileRecords();
		
		if( mSelectedTile != null )
			mSelectedTile.deSelect();
		
		mSelectedTile = xTile;
		mSelectedTile.select();
		
		for( PGameTableEventListener listener: mListeners )
			if( listener.tileClicked( mSelectedTile, PGameTable.this ) )
				break;
		
		if( mSelectedTile.isSkillRangeHighlighted() ){
			
			createSkillArea();
			
		}
		
		if( mSelectedTile.isBuildHighlighted() && mMap.isTileEmpty( mSelectedTile ) ){
			
			addBuilding( mSelectedBuildingReg, mSelectedTile, mSelectedUnit.getPID() );
			clearAllHighlights();
			
		}
		
		if( isTileReselected() ){
			
			if( mSelectedTile.isSkillAreaHighlighted() ){
			
				cast( mSelectedPlayerUnit, mSelectedTile, mSelectedSkill, true );
				return;
				
			}
			
		}
		
		if( mPathTiles.size() > 0 && mSelectedUnit != null ){
			
			if( mSelectedTile.isPathHighlighted() && isTileReselected() ){
				
				moveUnit( mSelectedUnit, mSelectedTile );
				
				return;
				
			}
			
			if( mSelectedTile.isPathHighlighted() ){
				
				for( PTile tile: mPathTiles )
					tile.wayUnHighlight();
				
				ArrayList< PTile > wayTiles = mMap.getPath( mMap.getTile( mSelectedUnit ), mSelectedTile );
				
				for( PTile tile: wayTiles )
					if( tile.isFog() || tile.isHeed() )
						return;
				
				for( PTile tile: wayTiles )
					tile.wayHighlight();
				
				return;
				
			}
			
		}
		
		for( PTile tile: mPathTiles ){
			tile.pathUnHighlight();
			tile.wayUnHighlight();
		}
		mPathTiles.clear();
		
		if( !mSelectedTile.isSkillRangeHighlighted() ){
			
			for( PTile tile: mSkillRangeTiles )
				tile.skillRangeUnHighlight();
			mSkillRangeTiles.clear();
			
			for( PTile tile: mSkillAreaTiles )
				tile.skillAreaUnHighlight();
			mSkillAreaTiles.clear();
			
		}
		
		if( !mSelectedTile.isBuildHighlighted() ){
			
			for( PTile tile: mBuildRangeTiles )
				tile.buildUnHighlight();
			mBuildRangeTiles.clear();
			
		}
		
		PUnit unit;
		if( ( unit = mMap.getUnitOnTile( mSelectedTile ) ) != null && !mSelectedTile.isSkillRangeHighlighted() ){
	
			mSelectedUnit = unit;
	
			if( mCurrentPlayer.belongs( unit ) ){
				mSelectedPlayerUnit = unit;
				createPath();
			}
			
			for( PGameTableEventListener listener: mListeners )
				if( listener.unitSelected( mSelectedUnit ) )
					break;
			
		}
		
		PBuilding building;
		if( ( building = mMap.getBuildingOnTile( mSelectedTile ) ) != null ){
			
			mSelectedBuilding = building;
			
			for( PGameTableEventListener listener: mListeners )
				if( listener.buildingSelected( mSelectedBuilding ) )
					break;
			
		}
		
		if( !mMap.isTileEmpty( mSelectedTile ) )
			mCamera.moveTo( mSelectedTile, 1.0f, true );
		
	}

	public boolean isTileReselected(){
		
		if( mPrevTiles.size() > 0 )
			return mSelectedTile == mPrevTiles.get( mPrevTiles.size() - 1 );
		
		return false;
		
	}

	public boolean hit( float xX, float xY ){
	
		PTile tile = mMap.getTile( xX, xY, mCamera );
		
		if( tile != null ){
	
			GraphicalPlayable<?> playable = null;
			
			if( ( playable = mMap.getPlayableOnTile( tile ) ) != null ){
				
				if( playable instanceof PUnit )
				for( PGameTableEventListener listener: mListeners )
					if( listener.unitHovered( ( PUnit ) playable ) )
						break;
	
				if( playable instanceof PBuilding )
				for( PGameTableEventListener listener: mListeners )
					if( listener.buildingHovered( ( PBuilding ) playable ) )
						break;
				
				return true;
				
			}
			
			return false;
			
		}
		
		return false;
		
	}

	public boolean tap( float x, float y, int count, int button ){
		
		if( button == Buttons.RIGHT ){
			clearAllHighlights();
			for( PGameTableEventListener listener: mListeners )
				if( listener.canceled() )
					break;
			return true;
		}
		
		if( !ready() )
			return false;
		
		PTile tile = mMap.getTile( x, y, mCamera );
		if( tile != null ){
			selectTile( tile );
			return true;
		}
		
		return true;
		
	}

	public void addGameTableEventListener( PGameTableEventListener xListener ){
		
		mListeners.add( xListener );
		
		xListener.assigned( this );
		
	}

	public void zoomMap( float xAmount ){
		
		mCamera.zoom( mCamera.zoom + xAmount );
		
	}

	public void translateMap( float dX, float dY ){
		
		mCamera.cancel();
		mCamera.translate( dX * mCamera.zoom, dY * mCamera.zoom );
		
	}

	public void moveMap( float dX, float dY ){
		
		mCamera.moveBy( dX * SCROLL_SPEED * Gdx.graphics.getDeltaTime(), dY * SCROLL_SPEED * Gdx.graphics.getDeltaTime(), 1.0f, false );
		
	}
	
	public void flingMap( float dX, float dY ){
		
		mCamera.moveBy( -dX * SCROLL_SPEED * Gdx.graphics.getDeltaTime(), -dY * SCROLL_SPEED * Gdx.graphics.getDeltaTime(), 1.0f, true );
		
	}

	@Override
	public void setShadowAngle( float x, float y ){

		GraphicalPlayable.setShadowAngleVals( x, y );
		
	}

	public void processGameEvents( ArrayList< ? extends GameEvent > xEvents ){
	
		synchronized ( mEvents ) {
			
			mEvents.addAll( xEvents );
	
		}
		
	}

	public boolean ready(){
		
		return mEvents.size() == 0 && calm();
		
	}

	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
		
		if( !ready() )
			return;
		
		super.save( xmlWriter );
	
		xmlWriter.attribute( "map", mMap.getEntityID() );
		xmlWriter.attribute( "player", mCurrentPlayer.getName() );
		
		mMap.save( xmlWriter );
		
		for( Player player: mPlayers.values() ){
	
			player.save( xmlWriter );
			
		}
	
		xmlWriter.pop();
		
	}

	/*
	 * SCRIPTABLE
	 */
	
	int i;
	
	@Override
	public void update(){
		
		if( !ready() ){
			for( i = 0; i < mListeners.size(); i++ )
				if( mListeners.get( i ).busy() )
					break;
		}
		else
			for( i = 0; i < mListeners.size(); i++ )
				if( mListeners.get( i ).ready() )
					break;
		
		mCamera.viewportWidth = Gdx.graphics.getWidth();
		mCamera.viewportHeight = Gdx.graphics.getHeight();
		
		mMap.update();

		for( i = 0; i < mUnits.size(); i++ ){
			
			PUnit unit = mUnits.get( i );
			unit.update();
			
			if( unit.isDead() ){
				
				mMap.removePlayable( unit );
				mUnitsByPID.remove( unit.getPID() );

				mUnits.remove( i );
				i--;
				
			}
			
		}
		
		for( i = 0; i < mBuildings.size(); i++ ){
			
			PBuilding building = mBuildings.get( i );
			building.update();
			
			if( building.isDead() ){
				
				mMap.removePlayable( building );
				mBuildings.remove( i );
				i--;
				
			}
			
		}
		
//		if( mSelectedUnit != null && mSelectedUnit.isMoving() )
//			mCamera.moveTo( mSelectedUnit, 2.0f, false );
//		else if( mSelectedSkill != null && mSelectedSkill.isProjectileActive() )
//			mCamera.moveTo( mSelectedSkill, 1.0f, false );
//		else
//			mCamera.makeCancellable();
		
		mCamera.update();
	
		processNextEvent();
		
	}

	@Override
	public void turn(){
		
//		if( mCurrentPlayer instanceof RemotePlayer || mCurrentPlayer instanceof AIPlayer )
//			return;
		
		if( mSelectedUnit != null && mSelectedUnit.isMoving() )
			return;
		
		if( mSelectedTile != null )
			mSelectedTile.deSelect();
		
		mSelectedUnit = null;
		
		clearAllHighlights();
	
		event( new GameEvent(){{
			type = TURNED;
		}});
		
		nextPlayer( null );
		
		mCurrentPlayer.turn();
	
		for( PGameTableEventListener listener: mListeners )
			if( listener.turned( this ) )
				break;
		
	}

	@Override
	public boolean render( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){
	
		mSpriteBatch.setProjectionMatrix( mCamera.combined );
		mSpriteBatch.begin();
		
		mMap.render( mSpriteBatch, mCamera );
		
		mSpriteBatch.end();
		
		return true;
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}

	private void clearAllHighlights(){
		
		for( PTile tile: mPathTiles ){
			tile.pathUnHighlight();
			tile.wayUnHighlight();
		}
		mPathTiles.clear();

		for( PTile tile: mSkillRangeTiles )
			tile.skillRangeUnHighlight();
		mSkillRangeTiles.clear();
		
		for( PTile tile: mSkillAreaTiles )
			tile.skillAreaUnHighlight();
		mSkillAreaTiles.clear();
		
		for( PTile tile: mBuildRangeTiles )
			tile.buildUnHighlight();
		mBuildRangeTiles.clear();

	}
	
	private void onSkillChoosen( PSkill xSkill ){
		
		clearAllHighlights();
		
		mSelectedSkill = xSkill;

		createSkillRegion();
		
		if( xSkill.getRange() == 0 )
			createSkillArea();
		
	}
	
	private void onBuildingChoosen( PBuildingReg xBuilding ){

		if( !mCurrentPlayer.canAfford( xBuilding ) )
			return;
		
		clearAllHighlights();
		
		mSelectedBuildingReg = xBuilding;
		
		createBuildRegion();
		
	}
	
	private void createSkillRegion(){
		
		for( PTile tile: mSkillRangeTiles )
			tile.skillRangeUnHighlight();
		mSkillRangeTiles.clear();
		
		//currentplayer units playables
		ArrayList< GraphicalPlayable< ? > > playables = new ArrayList< GraphicalPlayable< ? > >();
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.FRIENDLY ) )
			playables.addAll( mCurrentPlayer.getPlayables() );
		
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.ENEMY ) )
			for( Player player: mPlayers.values() )
				if( !player.equals( mCurrentPlayer ) )
					playables.addAll( player.getPlayables() );
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.BUILDING ) )
			for( Player player: mPlayers.values() )
				playables.addAll( player.getBuildings() );
		
		mSkillRangeTiles = mMap.getArea( mSelectedUnit.getTileX(), mSelectedUnit.getTileY(), mSelectedSkill.getRange(), false, playables );
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.SELF ) )
			mSkillRangeTiles.remove( mMap.getTile( mSelectedUnit.getTileX(), mSelectedUnit.getTileY() ) );
			
		for( PTile tile: mSkillRangeTiles )
			tile.skillRangeHighlight();
		
	}
	
	private void createSkillArea(){
		
		for( PTile tile: mSkillAreaTiles )
			tile.skillAreaUnHighlight();
		mSkillAreaTiles.clear();

		//currentplay units playables
		ArrayList< GraphicalPlayable< ? > > playables = new ArrayList< GraphicalPlayable< ? > >();
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.FRIENDLY ) )
			playables.addAll( mCurrentPlayer.getPlayables() );
		
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.ENEMY ) )
			for( Player player: mPlayers.values() )
				if( !player.equals( mCurrentPlayer ) )
					playables.addAll( player.getPlayables() );
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.BUILDING ) )
			for( Player player: mPlayers.values() )
				playables.addAll( player.getBuildings() );
		
		mSkillAreaTiles = mMap.getArea( mSelectedTile.getTileX(), mSelectedTile.getTileY(), mSelectedSkill.getArea(), false, playables );
		
		if( !mSelectedSkill.affectsOn( Skill.Affects.SELF ) )
			mSkillAreaTiles.remove( mMap.getTile( mSelectedUnit.getTileX(), mSelectedUnit.getTileY() ) );
		
		for( PTile tile: mSkillAreaTiles )
			tile.skillAreaHighlight();
		
		for( PGameTableEventListener listener: mListeners )
			if( listener.skillAreaSelected( mSelectedSkill, mSkillAreaTiles ) )
				break;
		
	}
	
	private void createBuildRegion(){
		
		mBuildRangeTiles = mMap.getArea( mSelectedUnit, 1, mSelectedBuildingReg.getAcceptedTilesID() );
		
		for( PTile tile: mBuildRangeTiles ){
		
			if( !tile.canWalk() && !mSelectedBuildingReg.getAcceptedTilesID().contains( tile.getEntityID() ) )
				continue;
			
			tile.buildHighlight();
		
		}
		
	}
	
	private void createPath(){
		
		clearAllHighlights();
		
		for( PTile tile: mPathTiles )
			tile.pathUnHighlight();
		mPathTiles.clear();
		
		mPathTiles = mMap.getArea( mSelectedUnit.getTileX(), mSelectedUnit.getTileY(), mSelectedUnit.getCurrentSpeed(), mSelectedUnit );
		
		for( PTile tile: mPathTiles )
			tile.pathHighlight();
		
	}

	

	/*
	 * SCRIPTABLE
	 */
	
	private void addBuilding( final PBuildingReg xBuilding, final PTile xTile, final String xUnit ){
		
		PBuilding newBuilding = xBuilding.createBuilding( mCurrentPlayer.getName() );
		
		mCurrentPlayer.pay( xBuilding );
		
		for( PGameTableEventListener listener: mListeners )
			if( listener.payed( xBuilding.getCost() ) )
				break;
		
		createBuilding( newBuilding, xTile.getTileX(), xTile.getTileY(), mCurrentPlayer.getName() );
		
		mCamera.moveTo( newBuilding, 2.0f, true );

		event( new GameEvent(){{
			type = BUILDING_ADDED;
			unitID = xUnit;
			buildingID = xBuilding.getBuilding();
			addCoords( xTile );
		}});
		
	}
	
	private void createBuilding( PBuilding xBuilding, int xX, int xY, String xPlayerName ){
		
		mMap.setPlayable( xX, xY, xBuilding, true );
		mBuildings.add( xBuilding );
		mBuildingsByPID.put( xBuilding.getPID(), xBuilding );
		
		xBuilding.addListener( mBuildingListener );
		xBuilding.addListener( mGameEventListener );
		
		mPlayers.get( xPlayerName ).addBuilding( xBuilding );
		
	}
	
	private void addUnit( final PUnitProduce xUnitProduce, final PBuilding xBuilding ){
		
		final PUnit unit = new PUnit( xUnitProduce, engine() );
		unit.setListener( mUnitListener );
		
		PTile tile = mMap.getFirstWalkableTileInArea( xBuilding );
		
		if( tile != null ){
			
			createUnit( unit, tile.getTileX(), tile.getTileY(), mCurrentPlayer.getName() );
			
		}
		
	}
	
	private void createUnit( PUnit xUnit, int xX, int xY, String xPlayerName ){

		mPlayers.get( xPlayerName ).addUnit( xUnit );
		mMap.setPlayable( xX, xY, xUnit, true );
		mUnits.add( xUnit );
		mUnitsByPID.put( xUnit.getPID(), xUnit );
		
		xUnit.addListener( mGameEventListener );
		
	}
	
	

	/*
	 * SCRIPTABLE
	 */
	
	private boolean produce( String xProdID, final String xBuildingID ){
		
		PBuilding building = mBuildingsByPID.get( xBuildingID );
		
		if( building == null || !mCurrentPlayer.belongs( building ) )
			return false;
		
		if( !building.isConstructed() )
			return false;
		
		final PProducePlayable< ?, ? > produce = building.getProduce( xProdID );
		
		if( produce == null || !mCurrentPlayer.canAfford( produce ) )
			return false;

		if( produce.isProducing() )
			return false;
		
		if( mCurrentPlayer.canAfford( produce ) ){
			
			mCurrentPlayer.pay( produce );
			produce.produce( building.getEntityID() );
			
			for( PGameTableEventListener listener: mListeners )
				if( listener.payed( produce.getCost() ) )
					break;

			event( new GameEvent(){{
				type = PRODUCED;
				buildingID=xBuildingID;
				produceID=produce.getEntityID();
			}});
			
		}
		
		return true;
		
	}
	
	private boolean build( int xX, int xY, String xUnit, String xBuilding ){
		
		PUnit unit = mUnitsByPID.get( xUnit );

		if( unit == null || !mCurrentPlayer.belongs( unit ) )
			return false;
		
		PBuildingReg buildingReg = unit.getBuildingRegs().get( xBuilding );
		
		if( buildingReg == null )
			return false;
	
		if( !mCurrentPlayer.canAfford( buildingReg ) )
			return false;
	
		PTile tile = getTile( xX, xY );
		
		boolean accepted = false;
		
		if( buildingReg.getAcceptedTilesID().size() == 0 )
			accepted = true;
		else
			accepted = buildingReg.getAcceptedTilesID().contains( tile.getEntityID() );
		
		if( tile == null || !mMap.isTileWalkable( tile ) || unit.distance( tile ) > 1 || !accepted )
			return false;
		
		addBuilding( buildingReg, tile, xUnit );
		
		return true;
		
	}

	private boolean cast( final PUnit xUnitFrom, final int xX, final int xY, String xSkill ){

		PTile to = mMap.getTile( xX, xY );
		
		if( to == null )
			return false;
		
		cast( xUnitFrom, mMap.getTile( xX, xY ), xUnitFrom.getSkills().get( xSkill ), false );
		
		return true;
		
	}
	
	private void cast( final PUnit xUnitFrom, final PTile xTileTo, final PSkill skill, boolean event ){

		if( event )
		event( new GameEvent(){{
			type = SKILL_CASTED;
			skillID = skill.getEntityID();
			unitID = xUnitFrom.getPID();
			addCoords( mMap.getTile( xUnitFrom ) ).addCoords( xTileTo );
		}});

		ArrayList< LivingPlayable< ? > > livingPlayables = mMap.getLivingsOnTiles( mMap.getArea( xTileTo, skill.getArea(), false ) );

		Iterator< LivingPlayable< ? > > livingIter = livingPlayables.iterator();
		
		
		while( livingIter.hasNext() ){
			
			LivingPlayable< ? > living = livingIter.next();
			
			if( !skill.affectsOn( Skill.Affects.FRIENDLY ) && mCurrentPlayer.belongs( living ) && !living.equals( xUnitFrom ) ){
				livingIter.remove();
				continue;
			}
				
			if( !skill.affectsOn( Skill.Affects.ENEMY ) && !mCurrentPlayer.belongs( living ) ){
				livingIter.remove();
				continue;
			}
			
			if( !skill.affectsOn( Skill.Affects.SELF ) && xUnitFrom.equals( living ) ){
				livingIter.remove();
				continue;
			}
			
			if( !skill.affectsOn( Skill.Affects.BUILDING ) &&  ( living instanceof PBuilding ) ){
				livingIter.remove();
				continue;
			}
			
		}

		skill.cast( mMap.getTile( xUnitFrom ), 
				xUnitFrom, 
				xTileTo, 
				livingPlayables,
				null );
		
		clearAllHighlights();

		for( PGameTableEventListener listener: mListeners )
			if( listener.skillCasted( mSelectedSkill ) )
				break;
		
	}

	private void moveUnit( final PUnit xUnit, final int xX, final int xY ){
		
		if( !mMap.isTherePath( xUnit, xX, xY ) )
			return;		
		
		moveUnit( xUnit, getTile( xX, xY ) );
		
	}
	
	private void moveUnit( final PUnit xUnit, final PTile xTile ){
		
		clearAllHighlights();

		ArrayList< PTile > path = mMap.getPath( mMap.getTile( xUnit), xTile );
		
		if( path.size() > xUnit.getCurrentSpeed() )
			return;
		
		mSelectedUnit = xUnit;

		mSelectedUnit.clearAffects();
		
		event( new GameEvent(){{
			type = UNIT_MOVED;
			unitID = xUnit.getPID();
			addCoords( xUnit ).addCoords( xTile );
		}});

		for( PTile tile: mMap.getPath( mMap.getTile( xUnit ), xTile ) ){
			
			xUnit.addToPath( tile.getUnitCoordinates( xUnit ), tile );
			
		}
		
		xUnit.addToPath( mMap.getTileOf( xUnit ).getUnitCoordinates( xUnit ), mMap.getTileOf( xUnit ), false );
		
//		mMap.setPlayable( xTile, xUnit, false );
		
	}
	
	

	/*
	 * SCRIPTABLE
	 */
	
	private void manageTileRecords(){
		
		mPrevTiles.add( mSelectedTile );
		
		if( mPrevTiles.size() > 10 )
			mPrevTiles.remove( 0 );
		
	}

	/*
	 * SCRIPTABLE
	 */
	
	private void nextPlayer( String xName ){
		
		if( xName != null ){
			
			while( !mPlayerNames.get( 0 ).equals( xName ) )
				mPlayerNames.add( mPlayerNames.remove( 0 ) );
			
		}else{
		
			mPlayerNames.add( mPlayerNames.remove( 0 ) );
		
		}
		
		mCurrentPlayer = mPlayers.get( mPlayerNames.get( 0 ) );
	
		if( mLoseScript.call() ){
			
			removePlayer( mCurrentPlayer.getName() );
			nextPlayer( null );
			
		}
		
		if( mCurrentPlayer.getLivingUnits().size() > 0 )
			mCamera.moveTo( mCurrentPlayer.getLivingUnits().get( 0 ).getCenter(), 1.0f, true );
		
		mMap.setCurrentPlayer( mCurrentPlayer );
		
		if( mCurrentPlayer instanceof RemotePlayer ){
			
			eventsOff();
			
			for( PGameTableEventListener listener: mListeners ){

				if( listener.remotePlayerTurn( ( RemotePlayer ) mCurrentPlayer, mRemoteEvents ) ){
					mRemoteEvents.clear();
					break;
				
				}
				
			}
			
		}
		else{
		
			for( PGameTableEventListener listener: mListeners )
				if( listener.localPlayerTurn( mCurrentPlayer ) )
					break;
			
			eventsOn();
		
		}
		
	}

	

	/*
	 * SCRIPTABLE
	 */
	
	private void processNextEvent(){

		if( !calm() || mEvents.size() == 0 )
			return;

		GameEvent xEvent = mEvents.get( 0 );

		if( ( xEvent.type ) > 0 ){
			
			switch( xEvent.type ){
			
				case GameEvent.UNIT_MOVED:
					
					moveUnit( mMap.getUnitOnTile( xEvent.eventX.get( 0 ), xEvent.eventY.get( 0 ) ), xEvent.eventX.get( 1 ), xEvent.eventY.get( 1 ) );
					
				break;
				
				case GameEvent.SKILL_CASTED:
					
					cast( mMap.getUnitOnTile( xEvent.eventX.get( 0 ), xEvent.eventY.get( 0 ) ), xEvent.eventX.get( 1 ), xEvent.eventY.get( 1 ), xEvent.skillID );
					
				break;
			
				case GameEvent.BUILDING_ADDED:
					
					build( xEvent.eventX.get( 0 ), xEvent.eventY.get( 0 ), xEvent.unitID, xEvent.buildingID );
					
				break;
				
				case GameEvent.PRODUCED:
					
					produce( xEvent.produceID, xEvent.buildingID );
					
				break;
				
				case GameEvent.TURNED:
					
					turn();
					
				break;
				
			}
		
		}
		
		mEvents.remove( 0 );

	}
	
	

	/*
	 * SCRIPTABLE
	 */
	
	private boolean calm(){
		
		for( i = 0; i < mUnits.size(); i++ )
			if( mUnits.get( i ).isBusy() )
				return false;
		
		return true;
		
	}
	
	
	
	/*
	 * SCRIPTABLE
	 */
	private void load( GameTable gt ){
		
		mMap = new PMap( engine().entitiesHolder().getMapManager().get( gt.getMap() ), engine() );
		
		for( Holding holding: gt.getHoldings() ){

			Player p = null;
			
			if( holding.getType().equals( "PMap" ) ){
				mMap.load( holding );
				continue;
			}
			
			if( holding.getType().equals( "AIPlayer" ) )
				p = new AIPlayer( holding.getValues().get( "name" ), this, engine() );
			
			if( holding.getType().equals( "Player" ) )
				p = new Player( holding.getValues().get( "name" ), engine() );
			
			if( holding.getType().equals( "RemotePlayer" ) )
				p = new RemotePlayer( holding.getValues().get( "name" ), engine() );
			
			
			p.load( holding.getHoldings() );
			
			mUnits.addAll( p.getLivingUnits() );
			mBuildings.addAll( p.getLivingBuildings() );
			
			addPlayer( p );
			
		}

		mMap.setCurrentPlayer( mCurrentPlayer );

		for( PUnit unit: mUnits )
			unit.setListener( mUnitListener );
		
		for( PUnit unit: mUnits ){
			
			mMap.setPlayable( unit.getTileX(), unit.getTileY(), unit, true );
			mUnitsByPID.put( unit.getPID(), unit );
			unit.addListener( mGameEventListener );
			
		}
		
		for( PBuilding building: mBuildings ){

			mMap.setPlayable( building.getTileX(), building.getTileY(), building, true );
			mBuildingsByPID.put( building.getPID(), building );
			building.addListener( mGameEventListener );
			building.addListener( mBuildingListener );
			
		}

	}

	/*
	 * SCRIPTABLE
	 */
	
	@Override
	public PMapScriptable map(){
	
		return ( PMapScriptable ) mMap;
		
	}

	@Override
	public ArrayList< PUnitScriptable > units(){
	
		return new ArrayList< PUnitScriptable >( mCurrentPlayer.getLivingUnits() );
		
	}

	@Override
	public ArrayList< PBuildingScriptable > buildings(){
	
		return new ArrayList< PBuildingScriptable >( mCurrentPlayer.getLivingBuildings() );
	
	}

	@Override
	public ArrayList< PUnitScriptable > enemyUnits(){
	
		ArrayList< PUnitScriptable > units = new ArrayList< PUnitScriptable >();
		
		for( Player player: mPlayers.values() )
			if( player != mCurrentPlayer )
				units.addAll( player.getLivingUnits() );
		
		return units;
	
	}

	@Override
	public ArrayList<PBuildingScriptable> enemyBuildings() {
	
		ArrayList< PBuildingScriptable > buildings = new ArrayList< PBuildingScriptable >();
		
		for( Player player: mPlayers.values() )
			if( player != mCurrentPlayer )
				buildings.addAll( player.getLivingBuildings() );
		
		return buildings;
	
	}

	@Override
	public ArrayList< PLivingPlayableScriptable > enemies(){
	
		ArrayList< PLivingPlayableScriptable > livings = new ArrayList< PLivingPlayableScriptable >();
		
		livings.addAll( enemyUnits() );
		livings.addAll( enemyBuildings() );
		
		return livings;
		
	}

	@Override
	public int distance( PGraphicalPlayableScriptable x, PGraphicalPlayableScriptable y ){
	
		return ( (lezli.hexengine.core.playables.graphics.GraphicalPlayable< ? > ) x ).distance( (lezli.hexengine.core.playables.graphics.GraphicalPlayable< ? > ) y );
	
	}
	
	/*
	 * SCRIPTABLE
	 */
	
}
