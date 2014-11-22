package lezli.hex.engine.core.playables.graphics;

import java.util.ArrayList;

import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.playables.sound.SfxPool;
import lezli.hex.engine.core.structure.entities.graphics.GraphicsElement;

public class PGraphicsElement extends Playable< GraphicsElement >{

	private ArrayList< String > mParticleFiles;
	
	private ArrayList< String > mSoundEffects;
	
	int i;
	
	public PGraphicsElement( GraphicsElement xEntity ){
		
		super( xEntity, null );
		
		mParticleFiles = xEntity.getParticles();

		mSoundEffects = xEntity.getSoundEffects();
		for( String fileName: xEntity.getSoundEffects() )
			SfxPool.load( fileName );
		
	}

	public void start( boolean xLoop ){
		
		playSounds();
		
	}
	
	protected void playSounds(){
		
		for( int i = 0; i < mSoundEffects.size(); i++ )
			SfxPool.play( mSoundEffects.get( i ) );
		
	}
	
	protected ArrayList< String > getParticleFiles(){
		
		return mParticleFiles;
		
	}
	
	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
	}

}
