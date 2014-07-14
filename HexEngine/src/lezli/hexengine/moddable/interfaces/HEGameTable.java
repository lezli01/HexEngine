package lezli.hexengine.moddable.interfaces;

import lezli.hexengine.core.gametable.PGameTableEventListener;

public interface HEGameTable {

	public void addGameTableEventListener( PGameTableEventListener xListener );
	public HEGameTableController getController();
	
	public void moveMap( float x, float y );
	public void flingMap( float x, float y );
	public void zoomMap( float z );
	public void translateMap( float x, float y );

	public void setShadowAngle( float x, float y );
	
	public HECamera getCamera();
	
}
