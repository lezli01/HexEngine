package lezli.hexengine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import lezli.hexengine.core.gametable.PGameTable;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.playables.Logger;
import lezli.hexengine.core.playables.common.PCommon;
import lezli.hexengine.core.structure.EntitiesHolder;
import lezli.hexengine.moddable.interfaces.HEGameTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.XmlWriter;

public class HexEngine {

	private EntitiesHolder mEntitiesHolder;
	private PCommon mCommon;
	private Logger mLogger;
	
	private PGameTable mGameTable;
	private boolean mInited;
	
	public HexEngine( String path, Logger logger, String map ){
		
		mInited = false;
		
		init( path, logger, map );
		
	}
	
	public EntitiesHolder entitiesHolder(){
		
		return mEntitiesHolder;
		
	}
	
	public PCommon common(){
		
		return mCommon;
		
	}
	
	public Logger logger(){
		
		return mLogger;
		
	}

	public HEGameTable getGameTable(){
		
		assertInit();
		
		return mGameTable;
		
	}

	public boolean eventTap( float x, float y, int count, int button ){
		
		return mGameTable.tap( x, y, count, button );
		
	}
	
	public boolean eventMove( float x, float y ){
		
		return mGameTable.hit( x, y );
		
	}
	
	public void addGameEvents( ArrayList< GameEvent > xEvents ){
		
		mGameTable.processGameEvents( xEvents );
		
	}

	public boolean save( String xPath ){
		
		if( !mGameTable.ready() )
			return false;
		
		File f = Gdx.files.local( xPath ).file();
		try {
		
			f.createNewFile();
		
		} catch (IOException e) {
	
			e.printStackTrace();
		
		}
		
		try {
		
			XmlWriter w = new XmlWriter( new PrintWriter( f ) );
			mGameTable.save( w );
			w.flush();
			w.close();
		
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		
		} catch (IOException e) {
	
			e.printStackTrace();
		
		}
		
		return true;
		
	}

	public void turn(){

		assertInit();
		
		/*
		 * 1. call every Playables turn()
		 * 2. switch active player
		 */
		
		mGameTable.turn();
		
	}
	
	public void start(){
		
		assertInit();
		
		mGameTable.start();
		
	}

	public void update(){
		
		assertInit();
		
		mGameTable.update();
		
	}
	
	public void render(){
		
		assertInit();
		
		mGameTable.render( null, null );
		
	}
	
	public void pause(){
		
		assertInit();
		
	}
	
	public void resume(){
		
		assertInit();
		
	}
	
	public void resize( int width, int height ){
		
		assertInit();
		
	}

	public void quit(){
		
		assertInit();
		
	}

	private void assertInit(){
		
		if( !mInited ){
	
			System.err.println( "Engine not initialized properly!" );
			System.exit( 1 );
			
		}
		
	}

	private void init( String xPath, Logger xLogger, String xMap ){
	
		Gdx.gl.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl.glEnable(GL10.GL_POINT_SMOOTH);
		Gdx.gl.glEnable( GL10.GL_POLYGON_SMOOTH_HINT );
		Gdx.gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);
	
		Gdx.gl.glEnable( GL10.GL_POINT_SMOOTH );
		Gdx.gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
	
		Gdx.gl.glEnable( GL10.GL_DEPTH_TEST );
		
		mEntitiesHolder = new EntitiesHolder( xPath );
		mLogger = xLogger;
		
		mCommon = new PCommon( mEntitiesHolder.getCommon() );
		
		mGameTable = new PGameTable( mEntitiesHolder.getGameTableManager().get( xMap ), this );
	
		mInited = true;
	
	}
	
}
