package lezli.hex.engine.core.playables.sound;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SfxPool {

	private static HashMap< String, Sound > mSoundEffects = new HashMap< String, Sound >();
	
	public static void load( String xFileName ){
		
		if( mSoundEffects.containsKey( xFileName ) )
			return;
		
		mSoundEffects.put( xFileName, Gdx.audio.newSound( Gdx.files.internal( xFileName ) ) );
		
	}
	
	public static void play( String xFileName ){
		
		System.out.println( "Playing: " + xFileName );
		
		if( mSoundEffects.containsKey( xFileName ) )
			mSoundEffects.get( xFileName ).play();
		
	}
	
}
