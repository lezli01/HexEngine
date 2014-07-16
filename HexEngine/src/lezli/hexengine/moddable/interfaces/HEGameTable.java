package lezli.hexengine.moddable.interfaces;

import lezli.hexengine.moddable.listeners.HEGameTableEventListener;

public interface HEGameTable {

	public void addGameTableEventListener( HEGameTableEventListener xListener );
	public HEGameTableController getController();
	
	public void moveMap( float x, float y );
	public void flingMap( float x, float y );
	public void zoomMap( float z );
	public void translateMap( float x, float y );

	public void setShadowAngle( float x, float y );
	
	public int mapWidth();
	public int mapHeight();
	
	public HETile getTile( int x, int y );
	
	public boolean isEmpty( int x, int y );
	
	public HECamera getCamera();
	
}
