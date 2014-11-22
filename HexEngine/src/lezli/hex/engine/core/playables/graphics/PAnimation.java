package lezli.hex.engine.core.playables.graphics;

import java.util.ArrayList;

import lezli.hex.engine.core.gametable.PGameTableCamera;
import lezli.hex.engine.core.structure.entities.graphics.Animation;
import lezli.hex.engine.core.structure.entities.graphics.Frame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PAnimation extends PGraphicsElement{

	private int mXstart;
	private int mYstart;
	private long mDuration;
	private ArrayList< PFrame > mFrames;
	private int mFrameCount;
	private Texture mTexture;
	
	private boolean mPlaying;
	private long mCurrentTime;
	private PFrame mCurrentFrame;
	private int mLoopCnt;
	private boolean mLooped;
	
	private float x, y;
	
	private ArrayList< PParticleEffect > mParticles;
	
	public PAnimation( Animation xAnimation, Texture xTexture ){
		
		super( xAnimation );
		
		mTexture = xTexture;
		
		mFrames = new ArrayList< PFrame >();
		
		mXstart = xAnimation.getXStart();
		mYstart = xAnimation.getYStart();
		mDuration = xAnimation.getDuration();
		
		mPlaying = false;
		mCurrentTime = 0;
		mFrameCount = 0;
		mLoopCnt = 0;
		mLooped = false;
		
		for( Frame frame: xAnimation.getFrames() ){
		
			mFrames.add( new PFrame( frame, mTexture, mXstart, mYstart ) );
			mFrameCount += frame.getMultiply();
			
		}
		
		if( mFrames.size() > 0 )
			mCurrentFrame = mFrames.get( 0 );
		
		mParticles = new ArrayList< PParticleEffect >();
		
	}
	
	public int getLoopCnt(){
		
		return mLoopCnt;
		
	}

	public long getCurrentDelta(){
		
		return mCurrentTime;
		
	}

	public PFrame getFrame( int xIdx ){
		
		return mFrames.get( xIdx );
		
	}
	
	public void setPosition( float x, float y ){
		
		this.x = x;
		this.y = y;
		
		for( ParticleEffect particleEffect: mParticles )
			particleEffect.setPosition( x, y );
		
	}
	
	public void setRotation( float rot ){
		
		for( i = 0; i < mParticles.size(); i++ )
			mParticles.get( i ).rotate( rot );
		
		
	}

	@Override
	public void start( boolean xLoop ){
		
		super.start( xLoop );
		
		mParticles.clear();
		
		for( String pFile: getParticleFiles() ){
			
			PParticleEffect particle = new PParticleEffect();
			particle.load( Gdx.files.internal( pFile ), Gdx.files.internal( pFile ).parent() );
			
			particle.flipY();
			particle.setFlip( false, true );

			particle.setPosition( x, y );
			particle.start();
			
			mParticles.add( particle );
			
		}
		
		mCurrentTime = 0;
		mLoopCnt = 0;
		mLooped = xLoop;
		mPlaying = true;
		
	}
	
	public void stop(){
		
		mCurrentTime = 0;
		mPlaying = false;
		
	}

	public boolean playing(){
		
		return mPlaying;
		
	}
	
	public boolean finished(){
		
		for( i = 0; i < mParticles.size(); i++ )
			if( !mParticles.get( i ).isComplete() )
				return false;
		
		return mCurrentTime >= mDuration;
		
	}

	public boolean looped(){
		
		return mLooped;
		
	}
	
	@Override
	public void update(){
		
		if( !playing() )
			return;
		
		for( i = 0; i < mParticles.size(); i++ )
			mParticles.get( i ).update( Gdx.graphics.getDeltaTime() );
		
		long dT = ( long ) ( Gdx.graphics.getDeltaTime() * 1000.0f );
	
		mCurrentTime += dT;
		if( mCurrentTime >= mDuration && mLooped ){
			
			for( i = 0; i < mParticles.size(); i++ ){

				PParticleEffect particle = mParticles.get( i );
				
				if( particle.isComplete() )
					particle.start();
				
			}
			
			playSounds();
			
			mCurrentTime = 0;
			mLoopCnt++;
	
		}
			
		setCurrentFrame();
		
	}

	@Override
	public void turn(){
		
	}

	public boolean render( SpriteBatch xSpriteBatch, float x, float y, float width, float height, float rotation, PGameTableCamera xCamera ){

		boolean retVal = false;
		
		if( mFrames.size() > 0 )
			retVal = mCurrentFrame.render( xSpriteBatch, x, y, width, height, rotation, xCamera );

		for( PParticleEffect particle: mParticles ){

			particle.rotate( rotation );
			particle.setPosition( x + width / 2, y + height / 2 );
			particle.draw( xSpriteBatch );

		}
		
		return retVal;
	}

	public boolean render( SpriteBatch xSpriteBatch, float x, float y, float width, float height, float sx, float sy, float rotation, PGameTableCamera xCamera ){

		boolean retVal = false;
		
		if( mFrames.size() > 0 )
			retVal = mCurrentFrame.render( xSpriteBatch, x, y, width, height, sx, sy, rotation, xCamera );

		for( i = 0; i < mParticles.size(); i++ ){
		
			PParticleEffect particle = mParticles.get( i );

			particle.rotate( rotation );
			particle.setPosition( x + width / 2, y + height / 2 );
			particle.draw( xSpriteBatch );
		
		}
		
		return retVal;
	}
	
	int i;
	
	private void setCurrentFrame(){
		
		float range = ( ( float ) mCurrentTime ) / ( ( float ) mDuration );
		
		int frameIdx = ( ( int ) ( ( ( float ) mFrameCount ) * range ) ) + 1;
		
		for( i = 0; i < mFrames.size(); i++ ){

			frameIdx -= mFrames.get( i ).getMultiply();
			
			if( frameIdx <= 0 ){
				mCurrentFrame = mFrames.get( i );
				break;
			}
			
		}
		
	}
	
}
