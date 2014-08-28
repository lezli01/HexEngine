package lezli.hex.engine.moddable.gametable;

import lezli.hex.engine.moddable.interfaces.HECamera;
import lezli.hex.engine.moddable.interfaces.HETile;
import lezli.hex.engine.moddable.listeners.HEEventListener;
import lezli.hex.engine.moddable.listeners.HEGameTableEventListener;

public interface HEGameTable {

	public void addEventListener( HEEventListener xListener );
	public void addGameTableEventListener( HEGameTableEventListener xListener );
	public void addController( HEGameTableController xController );
	
	public HEGameTableFeatures getFeatures();
	
	public void moveMap( float x, float y );
	public void flingMap( float x, float y );
	public void zoomMap( float z );
	public void translateMap( float x, float y );

	public void setShadowAngle( float x, float y );
	
	public int mapWidth();
	public int mapHeight();
	
	public void sleepAfterEvent( long msec );
	
	public HETile getTile( int x, int y );
	public HETile getTile( float x, float y );
	public HETile getCenterTile();
	public HETile getCameraPosition();
	
	public boolean isEmpty( int x, int y );
	
	public HECamera getCamera();
	
}
