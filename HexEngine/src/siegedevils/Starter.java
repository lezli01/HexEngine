package siegedevils;

import java.util.ArrayList;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.PGameTableEventListener;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.player.RemotePlayer;
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
	
	private static Gui mGui;
	
	private Vector2 mScrollVector = new Vector2();
	
	Bartender bt = new Bartender( "player1", "map_test01", new BartenderListener() {
		
		@Override
		public void events( ArrayList<GameEvent> xEvents ){
			
			System.out.println( "events" );
			HexEngine.getInstance().addGameEvents( xEvents );
			
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
		mGui = new Gui();
		HexEngine.getInstance().init( "siegedevils", mGui.getLogger(), "@map_test01" );
		HexEngine.getInstance().getGameTable().setShadowAngle( -1.0f, 0.2f );
		
		//Setting up input
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor( mGui.getInputProcessor() );
		multiplexer.addProcessor( getInputProcessor() );
		Gdx.input.setInputProcessor( multiplexer );
		
		//Setting up listeners
		HexEngine.getInstance().getGameTable().addGameTableEventListener( mGui.getGameTableListener() );
		
		mGui.addGuiEventListener( HexEngine.getInstance().getGameTable().getController() );
		
		
		HexEngine.getInstance().getGameTable().addGameTableEventListener( new PGameTableEventListener() {
			
			@Override
			public boolean remotePlayerTurn( RemotePlayer remotePlayer, ArrayList< GameEvent > xEvents ) {

				bt.tell( remotePlayer.getName(), xEvents );
				bt.ask();
				
				return true;
			
			}
			
		});
		
		//Start the map
		HexEngine.getInstance().start();

	}

	@Override
	public void dispose(){
		
		HexEngine.getInstance().quit();
		
	}

	private void update(){
		
		if( !mScrollVector.isZero() )
			HexEngine.getInstance().getGameTable().moveMap( -mScrollVector.x, -mScrollVector.y );
		
		HexEngine.getInstance().update();
		
		mGui.update();
		
	}
	
	@Override
	public void render(){

		update();
		
		Gdx.gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glDisable( GL20.GL_CULL_FACE );
		HexEngine.getInstance().render();
		
		mGui.render();
		
	}

	@Override
	public void resize( int width, int height ){
	
		HexEngine.getInstance().resize( width, height );
		mGui.resize( width, height );
		
	}

	@Override
	public void pause(){
		
		HexEngine.getInstance().pause();
		
	}

	@Override
	public void resume(){
		
		HexEngine.getInstance().resume();
		
	}
	
	public InputProcessor getInputProcessor(){
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		
		multiplexer.addProcessor( new GestureDetector( new GestureListener() {
		
			private float mPrevDistance;
			
			@Override
			public boolean zoom( float initialDistance, float distance ){
				
				if( mPrevDistance == 0 )
					mPrevDistance = initialDistance;
				HexEngine.getInstance().getGameTable().zoomMap( ( mPrevDistance - distance ) * 0.05f );
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
				
				return HexEngine.getInstance().eventTap( x, y, count, button );
			
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
				
				HexEngine.getInstance().getGameTable().translateMap( -deltaX, -deltaY );
				
				return true;
				
			}
			
			@Override
			public boolean longPress( float x, float y ){
				
				return false;
				
			}
			
			@Override
			public boolean fling( float velocityX, float velocityY, int button ){
				
				HexEngine.getInstance().getGameTable().flingMap( velocityX * 0.0005f, velocityY * 0.0005f );

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
				
				HexEngine.getInstance().getGameTable().zoomMap( amount * 0.3f );
				
				return true;
				
			}
			
			@Override
			public boolean mouseMoved( int screenX, int screenY ){
				
				return HexEngine.getInstance().eventMove( screenX, screenY );
			
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
