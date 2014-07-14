package lezli.hexengine.core.playables.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.PGameTableCamera;
import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.gametable.script.PMapScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.LivingPlayable;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.playables.map.tile.PTile;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.structure.entities.gametable.Holding;
import lezli.hexengine.core.structure.entities.map.Map;
import lezli.hexengine.core.structure.entities.map.MapRow;
import lezli.hexengine.core.structure.entities.map.MapTile;
import lezli.hexengine.core.structure.utils.managers.MapTileManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.XmlWriter;

public class PMap extends GraphicalPlayable< Map > implements PMapScriptable{

	private static final int FIX = 0;
	
	private ArrayList< ArrayList< PTile > > mTiles;
	private ConcurrentHashMap< Vector3, Vector2 > mPathParts;
	private HashMap< PTile, GraphicalPlayable<?> > mPlayables;
	private HashMap< Vector2, Integer > mAreaHelper;
	
	private Player mCurrentPlayer;
	private HashMap< String, ArrayList< PTile > > mPlayerRevealedTiles;
	
	public static float mTileSize = 50.0f;
	public static float mTileLean = ( float ) mTileSize / 2.0f;
	public static float mTileRay = ( float ) Math.sqrt( 3 ) * mTileSize / 2.0f;
	public static float mTileHeight = 2 * mTileSize;
	public static float mTileWidth = 2 * mTileRay;
	public static float mTileLead = (float)( 3.0/2.0 * mTileSize );
	
	public PMap( Map xEntity ){
		
		super( xEntity );
		
		mTiles = new ArrayList< ArrayList< PTile > >();
		mPathParts = new ConcurrentHashMap< Vector3, Vector2 >();
		mPlayables = new HashMap< PTile, GraphicalPlayable<?> >();
		mAreaHelper = new HashMap< Vector2, Integer >();
		
		mPlayerRevealedTiles = new HashMap< String, ArrayList< PTile > >();
		
		float x = 0, y = 0;

		PTile.area.setWidth( mTileWidth + FIX );
		PTile.area.setHeight( mTileHeight + FIX );
		PTile.build.setWidth( mTileWidth + FIX );
		PTile.build.setHeight( mTileHeight + FIX );
		PTile.move.setWidth( mTileWidth + FIX );
		PTile.move.setHeight( mTileHeight + FIX );
		PTile.select.setWidth( mTileWidth + FIX );
		PTile.select.setHeight( mTileHeight + FIX );
		PTile.skill.setWidth( mTileWidth + FIX );
		PTile.skill.setHeight( mTileHeight + FIX );
		PTile.way.setWidth( mTileWidth + FIX );
		PTile.way.setHeight( mTileHeight + FIX );
		PTile.fog.setWidth( mTileWidth + FIX );
		PTile.fog.setHeight( mTileHeight + FIX );
		PTile.heed.setWidth( mTileWidth + FIX );
		PTile.heed.setHeight( mTileHeight + FIX );
		
		MapTileManager manager = HexEngine.EntitiesHolder.getMapTileManager();
		
		for( MapRow mapRow: xEntity.getAll() ){
			
			x = 0;
			
			ArrayList< PTile > pTileRow = new ArrayList< PTile >();
			
			for( MapTile tile: mapRow.getAll() ){

				String id = tile.getTile();
				
				if( manager.get( tile.getTile() ) != null ){
					tile = manager.get( tile.getTile() );
					id = tile.getID();
				}
				
				PTile pTile = new PTile( HexEngine.EntitiesHolder.getTileManager().get( tile.getTile() ) );

				float xt = ( x * ( mTileWidth ) + ( 1 - ( y % 2 ) ) * mTileRay );
				float yt = y * ( (float)( 3.0/2.0 * mTileSize ) );

				pTile.setCoordinates( xt, yt - mTileHeight * ( pTile.getHeightMultiplier() - 1.0f ), mTileWidth + FIX, mTileHeight * pTile.getHeightMultiplier() + FIX );
				pTile.setTileX( ( int ) x );
				pTile.setTileY( ( int ) y );
				pTile.addPlaceholders( tile.getPlaceholders() );
			
				pTile.overwriteEntityID( id );
				pTile.setWalkable( tile.walkable() );
				
				pTile.fog();
				
				pTileRow.add( pTile );
			
				x++;
			
			}
			
			mTiles.add( pTileRow );
			y++;
			
		}
		
	}
	
	public PTile getTile( GraphicalPlayable<?> xPlayable ){

		for( Entry< PTile, GraphicalPlayable< ? > > entry: mPlayables.entrySet() ){
			
			if( entry.getValue() == xPlayable )
				return entry.getKey();
			
		}
		
		return null;
		
	}

	public PTile getTile( int xX, int xY ){
		
		if( !isInRange( xX, xY ) )
			return null;
		
		return mTiles.get( xY ).get( xX );
		
	}
	
	public GraphicalPlayable<?> getPlayableOnTile( PTile tile ){
	
		return mPlayables.get( getTile( tile.getTileX(), tile.getTileY() ) );
		
	}

	public GraphicalPlayable<?> getPlayableOnTile( int xX, int xY ){
	
		return getPlayableOnTile( getTile( xX, xY ) );
		
	}
	
	public LivingPlayable< ? > getLivingOnTile( PTile xTile ){
		
		if( getPlayableOnTile( xTile ) instanceof LivingPlayable< ? > )
			return ( LivingPlayable< ? > ) getPlayableOnTile( xTile );
		
		return null;
		
	}
	
	public ArrayList< LivingPlayable< ? > > getLivingsOnTiles( ArrayList< PTile > xTiles ){
		
		ArrayList< LivingPlayable< ? > > livings = new ArrayList< LivingPlayable< ? > >();
		
		for( PTile tile: xTiles ){
			
			LivingPlayable< ? > livingPlayable = ( LivingPlayable< ? > ) getPlayableOnTile( tile );
			
			if( livingPlayable != null && !livings.contains( livingPlayable ) ){
				
				livings.add( livingPlayable );
				
			}
			
		}
		
		return livings;
		
	}

	public PUnit getUnitOnTile( PTile tile ){
	
		return getUnitOnTile( tile.getTileX(), tile.getTileY() );
		
	}

	public PUnit getUnitOnTile( int xX, int xY ){
		
		if( !isTileEmpty( xX, xY ) ){
			
			GraphicalPlayable<?> xPlayable = getPlayableOnTile( xX, xY );
			
			if( xPlayable instanceof PUnit )
				return ( ( PUnit ) xPlayable );
			
		}
		
		return null;
		
	}

	public PBuilding getBuildingOnTile( PTile tile ){
		
		return getBuildingOnTile( tile.getTileX(), tile.getTileY() );
		
	}

	public PBuilding getBuildingOnTile( int xX, int xY ){
		
		if( !isTileEmpty( xX, xY ) ){
			
			GraphicalPlayable<?> xPlayable = getPlayableOnTile( xX, xY );
			
			if( xPlayable instanceof PBuilding )
				return ( ( PBuilding ) xPlayable );
			
		}
		
		return null;
		
	}

	public ArrayList< PUnit > getUnitsOnTiles( ArrayList< PTile > xTiles ){
		
		ArrayList< PUnit > units = new ArrayList< PUnit >();
		
		for( PTile tile: xTiles ){
			
			PUnit unit = getUnitOnTile( tile );
			
			if( unit != null ){
				
					units.add( unit );
				
			}
			
		}
		
		return units;
		
	}

	public ArrayList< PUnit > getEnemyUnitsOnTiles( Player xCurrentPlayer, ArrayList< PTile > xTiles ){
		
		ArrayList< PUnit > units = new ArrayList< PUnit >();
		
		for( PTile tile: xTiles ){
			
			PUnit unit = getUnitOnTile( tile );
			
			if( unit != null ){
				
				if( !xCurrentPlayer.unitBelongs( unit ) )
					units.add( unit );
				
			}
			
		}
		
		return units;
		
	}

	public ArrayList< LivingPlayable< ? > > getEnemyPlayablesOnTiles( Player xCurrentPlayer, ArrayList< PTile > xTiles ){
		
		ArrayList< LivingPlayable< ? > > livings = new ArrayList< LivingPlayable< ? > >();
		
		for( PTile tile: xTiles ){
			
			LivingPlayable< ? > unit = ( LivingPlayable< ? > ) getPlayableOnTile( tile );
			
			if( unit != null ){
				
				if( !xCurrentPlayer.belongs( unit ) )
					livings.add( unit );
				
			}
			
		}
		
		return livings;
		
	}

	public PTile getTile( float x, float y, PGameTableCamera xCamera ){
		
		int i = 0, j = 0;
		
		x = ( ( x - Gdx.graphics.getWidth() / 2.0f ) * xCamera.zoom + xCamera.position.x );
		y = ( ( y - Gdx.graphics.getHeight() / 2.0f ) * xCamera.zoom + xCamera.position.y );
		
		j = ( int ) ( y / ( mTileSize + mTileLean ) );
		i = ( int ) ( ( j % 2 == 1 ) ? ( x / mTileWidth ) : ( ( x - mTileWidth / 2 ) / mTileWidth ) );
		
		int ny = ( int ) ( y - j * ( mTileSize + mTileLean ) );
		int nx = ( int ) ( x - i * mTileWidth - ( ( j + 1 ) % 2 ) * ( mTileWidth / 2 ) );
		
		if( ny <= mTileLean ){
	
			//LEFT
			if( nx < mTileWidth / 2 ){
	
				if( ny < mTileLean * ( 1 - nx / ( mTileWidth / 2 ) ) ){
					if( j % 2 == 1 )
						i--;
					j--;
				}
					
			}
			//RIGHT
			else{
				
				nx -= ( mTileWidth / 2 );
				if( ny < mTileLean * nx / ( mTileWidth / 2 ) ){
					if( j % 2 == 0 )
						i++;
					j--;
				}
				
			}
			
		}
		
		if( j >= 0 && j < mTiles.size() && i >= 0 && i < mTiles.get( 0 ).size() ){
	
			return getTile( i, j );
			
		}
		
		return null;
		
	}

	public PTile getFirstWalkableTileInArea( GraphicalPlayable<?> xPlayable ){
		
		int maxDistance = Math.max( mapWidth(), mapHeight() );
		
		for( int distance = 1; distance < maxDistance; distance++ ){
			
			for( PTile tile: getArea( xPlayable, distance, false ) ){
				
				if( isTileWalkable( tile ) )
					return tile;
				
			}
			
		}
		
		return null;
		
	}

	public ArrayList< PTile > getPath( PTile xFrom, PTile xTo ){
		
		ArrayList< PTile > path = new ArrayList< PTile >();
		int maxDistance = Math.max( mapWidth(), mapHeight() );
		
		for( int distance = 1; distance <= maxDistance; distance++ ){
			
			ArrayList< PTile > currentArea = getArea( xFrom, distance, true, getUnitOnTile( xFrom )  );
			
			if( currentArea.contains( xTo ) ){
				
				Vector3 pathPart = new Vector3( xTo.getTileX(), xTo.getTileY(), 0 );
				
				while( pathPart.x != xFrom.getTileX() || pathPart.y != xFrom.getTileY() ){
					
					PTile pathTile = getTile( (int) pathPart.x, (int)pathPart.y );
					path.add( pathTile );
					
					for( Vector3 key: getLastPathParts().keySet() ){
						
						if( key.x == pathPart.x && key.y == pathPart.y && key.z > pathPart.z )
							pathPart = key;
						
					}
					
					pathPart = new Vector3( getLastPathParts().get( pathPart ), 0 );
					
				}
				
				return path;
				
			}
			
		}
		
		return null;
		
	}

	public ConcurrentHashMap< Vector3, Vector2 > getLastPathParts(){
		
		return mPathParts;
		
	}

	public ArrayList< PTile > getArea( GraphicalPlayable<?> xExceptPlayable, int xDistance ){
		
		return getArea( xExceptPlayable.getTileX(), xExceptPlayable.getTileY(), xDistance, true, xExceptPlayable );
		
	}

	public ArrayList< PTile > getArea( GraphicalPlayable<?> xExceptPlayable, int xDistance, ArrayList< String > xAcceptedTiles ){
		
		ArrayList< PTile > tiles = getArea( xExceptPlayable.getTileX(), xExceptPlayable.getTileY(), xDistance, false, xExceptPlayable );
		
		if( xAcceptedTiles.size() == 0 )
			return tiles;
		
		Iterator< PTile > tileIter = tiles.iterator();
		while( tileIter.hasNext() ){
			
			if( xAcceptedTiles.contains( tileIter.next().getEntityID() ) )
				continue;
			
			tileIter.remove();
			
		}
		
		return tiles;
		
	}

	public ArrayList< PTile > getArea( int xX, int xY, int xDistance, GraphicalPlayable<?> xExceptPlayable ){
		
		return getArea( xX, xY, xDistance, true, xExceptPlayable );
		
	}

	public ArrayList< PTile > getArea( GraphicalPlayable<?> xExceptPlayable, int xDistance, boolean xBlocked ){
		
		return getArea( xExceptPlayable.getTileX(), xExceptPlayable.getTileY(), xDistance, xBlocked, xExceptPlayable );
		
	}

	public ArrayList< PTile > getArea( GraphicalPlayable<?> xFrom, int xDistance, boolean xBlocked, GraphicalPlayable< ? > xExceptPlayable ){
		
		return getArea( xFrom.getTileX(), xFrom.getTileY(), xDistance, xBlocked, new ArrayList< GraphicalPlayable< ? > >( Collections.singletonList( xExceptPlayable ) ) );
		
	}

	public ArrayList< PTile > getArea( GraphicalPlayable<?> xFrom, int xDistance, boolean xBlocked, ArrayList< ? extends GraphicalPlayable< ? > > xExceptPlayable ){
		
		return getArea( xFrom.getTileX(), xFrom.getTileY(), xDistance, xBlocked, xExceptPlayable );
		
	}

	public ArrayList< PTile > getArea( int xX, int xY, int xDistance, boolean xBlocked, GraphicalPlayable< ? > xExceptPlayable ){
		
		return getArea( xX, xY, xDistance, xBlocked, new ArrayList< GraphicalPlayable< ? > >( Collections.singletonList( xExceptPlayable ) ) );
		
	}

	public ArrayList< PTile > getArea( int xX, int xY, int xDistance, boolean xBlocked, ArrayList< ? extends GraphicalPlayable< ? > > xCenterPlayable ){
		
		mPathParts.clear();
		mAreaHelper.clear();
		return getAreaTiles( xX, xY, xDistance, xBlocked, xCenterPlayable );
		
	}

	public PTile getTileOf( GraphicalPlayable< ? > xPlayable ){
		
		if( mPlayables.containsValue( xPlayable ) )
			return getTile( xPlayable.getTileX(), xPlayable.getTileY() );
		
		return null;
		
	}
	
	public void setPlayable( PTile tile, GraphicalPlayable<?> xPlayable, boolean xResize ){
		
		setPlayable( tile.getTileX(), tile.getTileY(), xPlayable, xResize );
		
	}
	
	public void setPlayable( int xX, int xY, GraphicalPlayable<?> xPlayable, boolean xResize ){

		if( mPlayables.containsValue( xPlayable ) ){
			
			if( xPlayable instanceof LivingPlayable< ? > )
				getTileOf( xPlayable ).affectOnExit( ( LivingPlayable< ? > ) xPlayable );
			
			getTileOf( xPlayable ).releasePlayable();
			mPlayables.remove( getTileOf( xPlayable ) );
			
		}
		
		getTile( xX, xY ).setPlayable( xPlayable, xResize );
		xPlayable.setTileX( xX );
		xPlayable.setTileY( xY );
		
		mPlayables.put( getTile( xX, xY ), xPlayable );

		if( xPlayable instanceof LivingPlayable< ? > )
			getTile( xX, xY ).affectOnEnter( ( LivingPlayable< ? > ) xPlayable ); 

		updateView();
		
	}
	
	private void updateView(){
		
		ArrayList< PTile > revealedTiles = mPlayerRevealedTiles.get( mCurrentPlayer.getName() );
		
		for( ArrayList< PTile > pTileRow: mTiles ){
			
			for( PTile pTile: pTileRow ){
		
				pTile.fog();
				pTile.heed();
				
			}
			
		}
		
		for( PTile pTile: revealedTiles )
				pTile.unFog();
		
		for( LivingPlayable< ? > playable: mCurrentPlayer.getLivingUnits() ){
			
			if( mCurrentPlayer.belongs( playable ) ){
				
				unFog( playable );
				
			}
			
		}
		
		for( LivingPlayable< ? > playable: mCurrentPlayer.getLivingBuildings() ){
			
			if( mCurrentPlayer.belongs( playable ) ){
				
				unFog( playable );
				
			}
			
		}
		
	}
	
	private void unFogTile( String xPlayer, PTile tile ){
		
		if( !mPlayerRevealedTiles.containsKey( xPlayer ) )
			mPlayerRevealedTiles.put( xPlayer, new ArrayList< PTile >() );
		
		tile.unHeed();
		tile.unFog();
		
		if( !mPlayerRevealedTiles.get( xPlayer ).contains( tile ) )
			mPlayerRevealedTiles.get( xPlayer ).add( tile );
		
	}
	
	private void unFog( LivingPlayable< ? > xPlayable ){
		
		PTile tile = getTile( xPlayable.getTileX(), xPlayable.getTileY() );
		tile.unHeed();
		tile.unFog();
		
		for( PTile pTile: getArea( xPlayable, xPlayable.statValue( "@FIELD_OF_VIEW" ), false ) ){
			pTile.unFog();
			if( !mPlayerRevealedTiles.get( mCurrentPlayer.getName() ).contains( pTile ) )
				mPlayerRevealedTiles.get( mCurrentPlayer.getName() ).add( pTile );
		}
		
		for( PTile pTile: getArea( xPlayable, xPlayable.statValue( "@FIELD_OF_HEED" ), false ) )
			pTile.unHeed();
		
		if( !mPlayerRevealedTiles.get( mCurrentPlayer.getName() ).contains( tile ) )
			mPlayerRevealedTiles.get( mCurrentPlayer.getName() ).add( tile );
		
	}
	
	public void setCurrentPlayer( Player xPlayer ){
		
		mCurrentPlayer = xPlayer;
		
		if( !mPlayerRevealedTiles.containsKey( mCurrentPlayer.getName() ) )
			mPlayerRevealedTiles.put( mCurrentPlayer.getName(), new ArrayList< PTile >() );
		
		updateView();
		
	}
	
	public void removePlayable( GraphicalPlayable<?> xPlayable ){
		
		mPlayables.remove( getTile( xPlayable ) );
		
	}
	
	public int mapWidth(){
		
		return mTiles.get( 0 ).size();
		
	}
	
	public int mapHeight(){
		
		return mTiles.size();
		
	}
	
	public boolean isInRange( int xX, int xY ){
		
		return ( xX >= 0 && xY >= 0 && xX < mapWidth() && xY < mapHeight() );
		
	}

	public boolean isTileWalkable( int xX, int xY ){
		
		return mTiles.get( xY ).get( xX ).canWalk() && isTileEmpty( xX, xY );
		
	}
	
	public boolean isTileWalkable( PTile xTile ){
		
		return isTileWalkable( xTile.getTileX(), xTile.getTileY() );
		
	}
	
	public boolean isTileEmpty( PTile xTile ){
		
		return isTileEmpty( xTile.getTileX(), xTile.getTileY() );
		
	}
	
	public boolean isTileEmpty( int xX, int xY ){
		
		return !mPlayables.containsKey( getTile( xX, xY ) );
		
	}

	public boolean isOnTile( ArrayList< ? extends GraphicalPlayable<?> > xPlayables, PTile tile ){
		
		return isOnTile( xPlayables, tile.getTileX(), tile.getTileY() );
		
	}
	
	public boolean isOnTile( ArrayList< ? extends GraphicalPlayable<?> > xPlayables, int xX, int xY ){
		
		for( GraphicalPlayable<?> playable: xPlayables )
			if( isOnTile( playable,	 xX, xY ) )
				return true;
		
		return false;
		
	}
	
	public boolean isOnTile( GraphicalPlayable<?> xPlayable, PTile tile ){
		
		return isOnTile( xPlayable, tile.getTileX(), tile.getTileY() );
		
	}
	
	public boolean isOnTile( GraphicalPlayable<?> xPlayable, int xX, int xY ){
		
		return getPlayableOnTile( xX, xY ) == xPlayable;
		
	}
	
	@Override
	public boolean isTherePath( PUnitScriptable xUnit, int xX, int xY ){
	
		if( xX < 0 || xY < 0 || xX >= mapWidth() || xY >= mapHeight() )
			return false;
		
		return getPath( getTile( ( PUnit ) xUnit ), getTile( xX, xY ) ) != null;
	
	}

	@Override
	public void turn(){
		
		for( ArrayList< PTile > tileRow: mTiles )
			for( PTile tile: tileRow ){
			
				tile.turn();
			
				if( getLivingOnTile( tile ) != null )
					tile.affectOnStay( getLivingOnTile( tile ) );
				
			}
		
	}
	
	int i,j;

	@Override
	public void update() {
	
		super.update();
	
		i = j = 0;
		
		for( i = 0; i < mTiles.size(); i++ )
			for( j = 0; j < mTiles.get( 0 ).size(); j++ )
				mTiles.get( i ).get( j ).update();
		
	}

	int q,w;
	
	@Override
	public boolean render( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		int j = (int) ( ( ( xCamera.position.y / xCamera.zoom - ( Gdx.graphics.getHeight() + mTileHeight / xCamera.zoom ) / 2.0f ) / ( mTileHeight / xCamera.zoom ) ) );
		if( j < 0 ) j = 0;

		int i = (int) ( ( ( xCamera.position.x / xCamera.zoom - ( Gdx.graphics.getWidth() + mTileWidth / xCamera.zoom ) / 2.0f )  / ( mTileWidth / xCamera.zoom ) ) );
		if( i < 0 ) i = 0;

		boolean hasRendered = false;
		
		for( q = j; q < mapHeight(); q++ ){
			
			hasRendered = false;
			
			for( w = i; w < mapWidth(); w++ ){
				
				if( !mTiles.get( q ).get( w ).render( xSpriteBatch, xCamera ) ){
					if( hasRendered )
						break;
				}
				else
					hasRendered = true;
				
			}
			
		}
		
		for( q = j; q < mapHeight(); q++ ){
			
			for( w = i; w < mapWidth(); w++ ){
			
				PTile t = mTiles.get( q ).get( w );
				t.castPlaceholderShadow( xSpriteBatch, xCamera );
				
			}
		
		}
		
		for( q = j; q < mapHeight(); q++ ){
			
			for( w = i; w < mapWidth(); w++ ){
			
				PTile t = mTiles.get( q ).get( w );
				t.renderPlaceholders( xSpriteBatch, xCamera );
				
			}
		
		}
		
		for( q = j; q < mapHeight(); q++ ){
			
			for( w = i; w < mapWidth(); w++ ){
				
				PTile t = mTiles.get( q ).get( w );
				t.castPlayableShadow( xSpriteBatch, xCamera );
				t.renderPlayable( xSpriteBatch, xCamera );
				
			}
			
		}
		
		return true;
		
	}

	private ArrayList< PTile > getAreaTiles( int xX, int xY, int xDistance, boolean xBlocked, ArrayList< ? extends GraphicalPlayable<?> > xExceptPlayables ){
		
		ArrayList< PTile > tiles = new ArrayList<PTile>();
		if( xX < 0 || xY < 0 || xY > mapHeight() - 1 || xX > mapWidth() - 1 )
			return tiles;

		PTile tile = getTile( xX, xY );
		Integer v = mAreaHelper.get( new Vector2( xX, xY ) );

		if( !( v == null || v < xDistance ) ){
			return tiles;
		};
			
		mAreaHelper.put( new Vector2( xX, xY), xDistance );
		
		if( xBlocked && ( ( !isTileWalkable( xX, xY ) ) && !isOnTile( xExceptPlayables, tile ) ) )
			return tiles;
		
		if( !isOnTile( xExceptPlayables, tile ) )
			tiles.add( tile );
		
		if( xDistance > 0 ){
			
			if( xY % 2 == 0 ){
				
				mPathParts.putIfAbsent( new Vector3( xX + 1, xY + 1, xDistance ), new Vector2( xX, xY ) );
				mPathParts.putIfAbsent( new Vector3( xX + 1, xY - 1, xDistance ), new Vector2( xX, xY ) );
				
			}
			else{
				
				mPathParts.putIfAbsent( new Vector3( xX - 1, xY + 1, xDistance ), new Vector2( xX, xY ) );
				mPathParts.putIfAbsent( new Vector3( xX - 1, xY - 1, xDistance ), new Vector2( xX, xY ) );
				
			}
			mPathParts.putIfAbsent( new Vector3( xX - 1, xY, xDistance ), new Vector2( xX, xY ) );
			mPathParts.putIfAbsent( new Vector3( xX + 1, xY, xDistance ), new Vector2( xX, xY ) );
			mPathParts.putIfAbsent( new Vector3( xX, xY - 1, xDistance ), new Vector2( xX, xY ) );
			mPathParts.putIfAbsent( new Vector3( xX, xY + 1, xDistance ), new Vector2( xX, xY ) );
			
			if( xY % 2 == 0 ){
				
				tiles.addAll( getAreaTiles( xX + 1, xY + 1, xDistance - 1, xBlocked, xExceptPlayables ) );
				tiles.addAll( getAreaTiles( xX + 1, xY - 1, xDistance - 1, xBlocked, xExceptPlayables ) );
				
			}
			else{
				
				tiles.addAll( getAreaTiles( xX - 1, xY + 1, xDistance - 1, xBlocked, xExceptPlayables ) );
				tiles.addAll( getAreaTiles( xX - 1, xY - 1, xDistance - 1, xBlocked, xExceptPlayables ) );
				
			}
			
			tiles.addAll( getAreaTiles( xX - 1, xY, xDistance - 1, xBlocked, xExceptPlayables ) );
			tiles.addAll( getAreaTiles( xX + 1, xY, xDistance - 1, xBlocked, xExceptPlayables ) );
			tiles.addAll( getAreaTiles( xX, xY - 1, xDistance - 1, xBlocked, xExceptPlayables ) );
			tiles.addAll( getAreaTiles( xX, xY + 1, xDistance - 1, xBlocked, xExceptPlayables ) );
			
		}
		
		return tiles;
		
	}
	
	
	@Override
	protected void animationEnded( String xID ){
		
	}
	
	@Override
	public void save( XmlWriter xmlWriter ) throws IOException {

		super.save( xmlWriter );
	
		for( String player: mPlayerRevealedTiles.keySet() ){
			
			XmlWriter element = xmlWriter.element( "RevealedTiles" );
			element.attribute( "player", player );
			
			for( PTile tile: mPlayerRevealedTiles.get( player ) ){
				
				XmlWriter rte = element.element( "Tile" );
				rte.attribute( "x", tile.getTileX() );
				rte.attribute( "y", tile.getTileY() );
				rte.pop();
				
			}
			
			element.pop();
			
		}

		xmlWriter.pop();
		
	}
	
	@Override
	public void load( Holding holding ){

		super.load( holding );

		for( Holding h: holding.getHoldings() ){
			
			if( h.getType().equals( "RevealedTiles" ) ){
			
				String player = h.getValues().get( "player" );
				
				for( Holding ht: h.getHoldings() ){

					int x = Integer.parseInt( ht.getValues().get( "x" ) );
					int y = Integer.parseInt( ht.getValues().get( "y" ) );
					
					unFogTile( player, getTile( x, y ) );
					
				}
				
			}
			
		}
		
	}
	
}
