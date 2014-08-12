package siegedevils;

import java.util.ArrayList;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.HexEngine.HexEngineProperties;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.player.RemotePlayer;
import lezli.hexengine.core.playables.Logger;
import lezli.hexengine.moddable.listeners.HEGameTableEventListener;
import siegedevils.gui.GameLog;
import siegedevils.gui.Gui;
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
				// TODO Auto-generated catch block
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
		
		//Initialize
		
		Logger l = new Logger() {
			
			@Override
			public void log(String xMsg, int xDepth) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void log(String xMsg) {
				// TODO Auto-generated method stub
				
			}
		};
		
		mEngine = new HexEngine( "siegedevils", l, "@map_test01" );

		mGui = new Gui( mEngine );
		
		//Setting up listeners
		mEngine.getGameTable().addGameTableEventListener( mGui.getGameTableListener() );
		mEngine.getGameTable().addGameTableEventListener( new HEGameTableEventListener() {
			
			@Override
			public boolean remotePlayerTurn( RemotePlayer remotePlayer, ArrayList< GameEvent > xEvents ) {

				bt.tell( remotePlayer.getName(), xEvents );
				bt.ask();
				
				return true;
			
			}
			
		});

		//Setting up input
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor( mGui.getInputProcessor() );
		multiplexer.addProcessor( getInputProcessor() );
		Gdx.input.setInputProcessor( multiplexer );

		
		mGui.addGuiEventListener( mEngine.getGameTable().getController() );
		//Start the map
		mEngine.getGameTable().setShadowAngle( -1.0f, 0.2f );
		mEngine.start();

		mEngine.getProperties().setProperty( HexEngineProperties.PROP_INSTANT, true );
		
	}

	@Override
	public void dispose(){
		
		mEngine.quit();
		
	}

	private void update(){
		
		if( !mScrollVector.isZero() )
			mEngine.getGameTable().moveMap( -mScrollVector.x, -mScrollVector.y );
		
		mEngine.update();
		
		mGui.update();
		
	}
	
	@Override
	public void render(){

		update();
		
		Gdx.gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glDisable( GL20.GL_CULL_FACE );
		mEngine.render();
		
		mGui.render();
		
	}

	@Override
	public void resize( int width, int height ){
	
		mEngine.resize( width, height );
		mGui.resize( width, height );
		
	}

	@Override
	public void pause(){
		
		mEngine.pause();
		
	}

	@Override
	public void resume(){
		
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
