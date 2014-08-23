package lezli.hex.engine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import lezli.hex.engine.core.HexEngine.HexEngineProperties.PropertyChangedListener;
import lezli.hex.engine.core.gametable.PGameTable;
import lezli.hex.engine.core.gametable.event.GameEvent;
import lezli.hex.engine.core.playables.Logger;
import lezli.hex.engine.core.playables.common.PCommon;
import lezli.hex.engine.core.structure.EntitiesHolder;
import lezli.hex.engine.core.structure.entities.Entity;
import lezli.hex.engine.moddable.interfaces.HEGameTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.XmlWriter;

public class HexEngine {

	private EntitiesHolder mEntitiesHolder;
	private PCommon mCommon;
	private Logger mLogger;
	
	private PGameTable mGameTable;
	private boolean mInited;
	
	private HexEngineProperties mProperties;
	
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

	public HexEngineProperties getProperties(){
		
		return mProperties;
		
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

			//TODO log
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
		
		mLogger = xLogger;

		Entity.setEngine( this );

		mEntitiesHolder = new EntitiesHolder( xPath );
		
		mCommon = new PCommon( mEntitiesHolder.getCommon(), this );
		
		mGameTable = new PGameTable( mEntitiesHolder.getGameTableManager().get( xMap ), this );
	
		mInited = true;
		
		PropertyChangedListener propListener = new PropertyChangedListener() {
			
			@Override
			public boolean onPropertyChanged( int property, Object value ){

				switch( property ){
				
					case HexEngineProperties.PROP_INSTANT_MOVE:
					
					break;
				
					default: break;
					
				}
				
				return false;
			
			}
			
		};
		
		mProperties = new HexEngineProperties( propListener );
	
	}

	public static class HexEngineProperties{

		public static final int PROP_INSTANT_MOVE	=	1 << 0;
		public static final int PROP_INSTANT_CAST	=	1 << 1;
		
		private int i;
		private ArrayList< PropertyChangedListener > mListeners;
		private HashMap< Integer, Object > mProperties;
		
		public HexEngineProperties( PropertyChangedListener xListener ){
			
			mListeners = new ArrayList< PropertyChangedListener >();
			mProperties = new HashMap< Integer, Object >();
			
			addListener( xListener );
			addProperty( PROP_INSTANT_MOVE, false );
			addProperty( PROP_INSTANT_CAST, false );
			
		}

		public void addListener( PropertyChangedListener xListener ){
			
			mListeners.add( xListener );
			
		}
		
		protected void addProperty( int xProp, Object xValue ){
			
			if( mProperties.containsKey( xProp ) )
				return;
			
			mProperties.put( xProp, xValue );
			
		}
		
		public void setProperty( int xProp, Object xValue ){
			
			if( !mProperties.containsKey( xProp ) )
				return;
			
			mProperties.put( xProp, xValue );
			
			for( i = 0; i < mListeners.size(); i++ )
				mListeners.get( i ).onPropertyChanged( xProp, xValue );
			
		}
		
		public Object getProperty( int xProp ){
			
			Object value = mProperties.get( xProp );
			
			if( value != null )
				return value;
			
			return false;
			
		}
		
		public static interface PropertyChangedListener{
			
			public boolean onPropertyChanged( int property, Object value );
			
			
		}

	}
	
}
