package siegedevils;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.HexEngine.HexEngineProperties;
import lezli.hex.engine.core.gametable.event.GameEvent;
import lezli.hex.engine.core.gametable.player.RemotePlayer;
import lezli.hex.engine.moddable.interfaces.HEGameEvent;
import lezli.hex.engine.moddable.listeners.HEEventListener;
import lezli.hex.engine.moddable.listeners.HEGameTableEventListener;
import lezli.hex.enginex.utils.controller.DefaultGameController;
import lezli.hex.enginex.utils.log.FileLogger;
import siegedevils.gui.GameLog;
import siegedevils.gui.Gui;
import siegedevils.gui.Gui.GuiListener;
import siegedevils.menu.MenuListener;
import siegedevils.menu.Menu;
import siegedevils.multiplayer.Bartender;
import siegedevils.multiplayer.Bartender.BartenderListener;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Starter implements ApplicationListener {

	private static final int LOG_LEVEL = Application.LOG_NONE;
	
	private HexEngine mEngine;
	
	private Menu mMenu;
	private static Gui mGui;
	
	private Vector2 mScrollVector = new Vector2();
	
	Bartender bt = new Bartender( "player1", "map_test01", new BartenderListener() {
		
		@Override
		public void events( ArrayList<GameEvent> xEvents ){
			
			System.out.println( "events" );
			mEngine.addGameEvents( xEvents );
			
		}
		
		public void noevents(){
			
			try {
			
				Thread.sleep( 2500 );
			
			} catch (InterruptedException e) {

				e.printStackTrace();
			
			}
			
			bt.ask();
			
		};
		
		public void unknown(){
			
			System.out.println( "unknown" );
		
		};
		
	});
	
	@Override
	public void create(){

		Gdx.app.setLogLevel( LOG_LEVEL );
		
		mMenu = new Menu();
		
		mMenu.addListener( new MenuListener() {
			
			@Override
			public void mapStarted( String xGameTableId ) {
			
				System.out.println( xGameTableId );
				startEngine( xGameTableId );
				
			}
			
			@Override
			public void mapEnded() {
				
				mEngine.quit();
				mEngine = null;
				mGui = null;
				
				Gdx.input.setInputProcessor( mMenu );
				mMenu.setActive( Menu.SCR_MAIN );
				
			}
			
			@Override
			public void save( String xFileName ){

				mEngine.save( xFileName );
				
			}
			
		});
		
		Gdx.input.setInputProcessor( mMenu );
		
	}

	private void startEngine( String xGameTableId ){
		
		mEngine = new HexEngine( "hex.engine", new FileLogger( "log.txt" ), xGameTableId );
		
		mGui = new Gui( mEngine );
		
		mGui.addGuiListener( new GuiListener() {
			
			@Override
			public void menuCalled() {
			
				mMenu.setActive( Menu.SCR_INGAME );
				
			}
			
		});
		
		mEngine.getGameTable().addGameTableEventListener( mGui.getGameTableListener() );
		mEngine.getGameTable().addGameTableEventListener( new HEGameTableEventListener() {
			
			@Override
			public boolean remotePlayerTurn( RemotePlayer remotePlayer, ArrayList< GameEvent > xEvents ) {

				bt.tell( remotePlayer.getName(), xEvents );
				bt.ask();
				
				return true;
			
			}
			
		});
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor( Gdx.input.getInputProcessor() );
		multiplexer.addProcessor( mGui.getInputProcessor() );
		multiplexer.addProcessor( getInputProcessor() );
		Gdx.input.setInputProcessor( multiplexer );

		
		mGui.addFeatures( mEngine.getGameTable().getFeatures() );
		//Start the map
		mEngine.getGameTable().setShadowAngle( -1.0f, 0.2f );
		mEngine.start();

		mEngine.getProperties().setProperty( HexEngineProperties.PROP_INSTANT_CAST, false );
		mEngine.getProperties().setProperty( HexEngineProperties.PROP_INSTANT_MOVE, false );
		
		mEngine.getGameTable().addEventListener( new HEEventListener() {
			
			@Override
			public boolean event( HEGameEvent xEvent ){

				System.out.println( xEvent.getType() );
				
				return true;
				
			}
		
		});
		
		mEngine.getGameTable().addController( new DefaultGameController() );
		
	}
	
	@Override
	public void dispose(){
		
		if( mEngine != null )
			mEngine.quit();
		
	}

	private void update(){
		
		if( !mScrollVector.isZero() )
			mEngine.getGameTable().moveMap( -mScrollVector.x, -mScrollVector.y );
		
		if( mEngine != null )
			mEngine.update();
		
		if( mGui != null )
			mGui.update();
		
		mMenu.update();
		
	}
	
	@Override
	public void render(){

		update();
		
		Gdx.gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glDisable( GL20.GL_CULL_FACE );
		
		if( mEngine != null )
			mEngine.render();
		
		if( mGui != null )
			mGui.render();
		
		mMenu.draw();
		
	}

	@Override
	public void resize( int width, int height ){
	
		if( mEngine != null )
			mEngine.resize( width, height );
		
		if( mGui != null )
			mGui.resize( width, height );
		
		mMenu.resize( width, height );
		
	}

	@Override
	public void pause(){
		
		if( mEngine != null )
			mEngine.pause();
		
	}

	@Override
	public void resume(){
		
		if( mEngine != null )
			mEngine.resume();
		
	}
	
	public InputProcessor getInputProcessor(){
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		
		multiplexer.addProcessor( new GestureDetector( new GestureListener() {
		
			private float mPrevDistance;
			
			@Override
			public boolean zoom( float initialDistance, float distance ){
				
				if( mPrevDistance == 0 )
					mPrevDistance = initialDistance;
				mEngine.getGameTable().zoomMap( ( mPrevDistance - distance ) * 0.05f );
				mPrevDistance = distance;
				
				return true;
				
			}
			
			@Override
			public boolean touchDown( float x, float y, int pointer, int button ){
			
				mPrevDistance = 0;
				
				return true;
	
			}
			
			@Override
			public boolean tap( float x, float y, int count, int button ){
				
				return mEngine.eventTap( x, y, count, button );
			
			}
			
			@Override
			public boolean pinch( Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2 ){
				
				return false;
				
			}
			
			@Override
			public boolean panStop( float x, float y, int pointer, int button ){
				
				return false;
			
			}
			
			@Override
			public boolean pan( float x, float y, float deltaX, float deltaY ){
				
				mEngine.getGameTable().translateMap( -deltaX, -deltaY );
				
				return true;
				
			}
			
			@Override
			public boolean longPress( float x, float y ){
				
				return false;
				
			}
			
			@Override
			public boolean fling( float velocityX, float velocityY, int button ){
				
				mEngine.getGameTable().flingMap( velocityX * 0.0005f, velocityY * 0.0005f );

				return true;
				
			}
			
		}));
		
		multiplexer.addProcessor( new InputProcessor() {
			
			@Override
			public boolean touchUp( int screenX, int screenY, int pointer, int button ){
				
				return false;
				
			}
			
			@Override
			public boolean touchDragged( int screenX, int screenY, int pointer ){
				
				return false;
				
			}
			
			@Override
			public boolean touchDown( int screenX, int screenY, int pointer, int button ){
				
				return false;
				
			}
			
			@Override
			public boolean scrolled( int amount ){
				
				mEngine.getGameTable().zoomMap( amount * 0.3f );
				
				return true;
				
			}
			
			@Override
			public boolean mouseMoved( int screenX, int screenY ){
				
				return mEngine.eventMove( screenX, screenY );
			
			}
			
			@Override
			public boolean keyUp( int keycode ){
				
				switch( keycode ){
				
					case Input.Keys.W: mScrollVector.y -= 1; break;
					case Input.Keys.S: mScrollVector.y += 1; break;
					case Input.Keys.A: mScrollVector.x -= 1; break;
					case Input.Keys.D: mScrollVector.x += 1; break;
					
				}
				
				return true;
				
			}
			
			@Override
			public boolean keyTyped(char character) {
				
				if( character == '0' )
					( ( GameLog ) mGui.getLogger() ).toggle();
				
				return true;
			}
			
			@Override
			public boolean keyDown( int keycode ){
				
				switch( keycode ){
				
					case Input.Keys.W: mScrollVector.y += 1; break;
					case Input.Keys.S: mScrollVector.y -= 1; break;
					case Input.Keys.A: mScrollVector.x += 1; break;
					case Input.Keys.D: mScrollVector.x -= 1; break;
					
				}
				
				return false;
			}
			
		});
		
		return multiplexer;
		
	}
	
}
