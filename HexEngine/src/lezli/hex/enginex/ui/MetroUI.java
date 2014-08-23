package lezli.hex.enginex.ui;

import java.util.HashMap;

import lezli.hex.enginex.ui.metro.elements.MetroScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MetroUI extends Stage{

	private MetroScreen mActiveScreen;
	private HashMap< String, MetroScreen > mScreens;
	
	public MetroUI(){
		
		super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true );
		
		mScreens = new HashMap< String, MetroScreen >();

		addListener( new InputListener(){
			
			@Override
			public void enter( InputEvent event, float x, float y, int pointer, Actor fromActor ){

				System.out.println( x );
				
			}
			
		});
		
	}
	
	public void resize( float xWidth, float xHeight ){
		
		setViewport( xWidth, xHeight );

	}
	
	public void addScreen( MetroScreen xScreen ){
		
		mScreens.put( xScreen.getId(), xScreen );

	}
	
	public void setActive( String xId ){
		
		mActiveScreen = mScreens.get( xId );
		
		clear();
		
		if( mActiveScreen != null )
			addActor( mActiveScreen );
		
	}
	
	public void update(){
		
		act( Gdx.graphics.getDeltaTime() );
		
	}
	
}
