package lezli.hex.engine.moddable.interfaces;

import lezli.hex.engine.moddable.listeners.HEEventListener;
import lezli.hex.engine.moddable.listeners.HEGameTableEventListener;

public interface HEGameTable {

	public void addEventListener( HEEventListener xListener );
	public void addGameTableEventListener( HEGameTableEventListener xListener );
	
	public HEGameTableController getController();
	
	public void moveMap( float x, float y );
	public void flingMap( float x, float y );
	public void zoomMap( float z );
	public void translateMap( float x, float y );

	public void setShadowAngle( float x, float y );
	
	public int mapWidth();
	public int mapHeight();
	
	public void sleepAfterEvent( long msec );
	
	public HETile getTile( int x, int y );
	public HETile getCenterTile();
	public HETile getCameraPosition();
	
	public boolean isEmpty( int x, int y );
	
	public HECamera getCamera();
	
}
