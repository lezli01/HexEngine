package lezli.hexengine.core.playables.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;

public class PParticleEffect extends ParticleEffect{

	private ArrayList< Float > mVals;
	
	public PParticleEffect(){
		
		super();
		
		mVals = new ArrayList< Float >();
		
	}
	
	public void rotate( float val ){
		
		int i = 0;
		val -= 90;
		
		for( ParticleEmitter emitter: getEmitters() ){
			
			ScaledNumericValue value = emitter.getAngle();
			value.setLow( mVals.get( i ) + val, mVals.get( i + 1 ) + val );
			value.setHigh( mVals.get( i + 2 ) + val, mVals.get( i + 3 ) + val );
			
			emitter.getSprite().setRotation( val );
			
			i+=4;
			
		}
		
	}
	
	@Override
	public void load( FileHandle effectFile, FileHandle imagesDir ){

		super.load( effectFile, imagesDir );
	
		for( ParticleEmitter emitter: getEmitters() ){
			
			mVals.add( emitter.getAngle().getLowMin() );
			mVals.add( emitter.getAngle().getLowMax() );
			mVals.add( emitter.getAngle().getHighMin() );
			mVals.add( emitter.getAngle().getHighMax() );
			
		}
		
	}
	
}
