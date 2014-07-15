package lezli.hexengine.core.playables.graphics;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hexengine.core.gametable.PGameTableCamera;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.structure.entities.graphics.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.MipMapGenerator;

public class PGraphics extends Playable< Graphics >{

	private static HashMap< String, Texture> mTextures = new HashMap< String, Texture >();

	private String mSrc;
	
	private HashMap< String, PSCMLAnimation > mSCMLAnimations;
	private HashMap< String, PAnimation > mAnimations;
	private ArrayList< String > mAnimationNames;
	
	private HashMap< String, PSprite > mSprites;
	
	private ArrayList< PAnimationListener > mAnimationListeners;
	
	private TextureFilter mMinFilter;
	private TextureFilter mMagFilter;
	
	private PScmlHandler mScmlHandler;
	
	public PGraphics( Graphics xGraphics ){
		
		super( xGraphics, null );
		
		mSrc = xGraphics.getSrc();
		mAnimations = new HashMap< String, PAnimation >();
		mAnimationNames = new ArrayList< String >();
		mSCMLAnimations = new HashMap< String, PSCMLAnimation >();
		mSprites = new HashMap< String, PSprite >();
		
		mMinFilter = TextureFilter.MipMapLinearLinear;
		mMagFilter = TextureFilter.Linear;
		
		if( xGraphics.getFilter().equals( "nearest" ) ){
			
			mMinFilter = TextureFilter.MipMapNearestNearest;
			mMagFilter = TextureFilter.Nearest;
		
		}
		
		if( loadTexture( xGraphics.getSrc(), mMinFilter, mMagFilter ) )
			log( "Texture loaded (" + xGraphics.getSrc() + ")" );
		else
			log( "Texture already loaded (" + xGraphics.getSrc() + ")" );
		
		for( String animationID: xGraphics.getAnimations().keySet() ){
		
			mAnimations.put( animationID, new PAnimation( xGraphics.getAnimation( animationID ), mTextures.get( mSrc ) ) );
			mAnimationNames.add( animationID );

		}
		
		if( xGraphics.getScml() != null ){
			
			mScmlHandler = new PScmlHandler( xGraphics.getScml() );
			for( String animationID: xGraphics.getScmlAnimations().keySet() )
				mSCMLAnimations.put( animationID, new PSCMLAnimation( xGraphics.getScmlAnimations().get( animationID ) ) );
		
		}
		
		for( String spriteID: xGraphics.getSprites().keySet() )
			mSprites.put( spriteID, new PSprite( xGraphics.getSprite( spriteID ), mTextures.get( mSrc ) ) );
		
		mAnimationListeners = new ArrayList< PAnimationListener >();
		
	}
	
	public static void reloadTextures(){
		
		for( String src: mTextures.keySet() ){
			
			loadTexture( src, true, null, null );
			
		}
		
	}

	public Sprite getSprite( String xID ){
		
		return mSprites.get( xID ).getSprite();
		
	}
	
	public boolean hasAnimation( String xID ){
		
		return mAnimations.get( xID ) != null || mSCMLAnimations.containsKey( xID );
		
	}
	
	public void setPosition( float x, float y ){
		
		for( PAnimation animation: mAnimations.values() ){
			
			animation.setPosition( x, y );
			
		}
		
	}
	
	int i;
	
	public void setRotation( float rot ){
		
		for( i = 0; i < mAnimationNames.size(); i++ )
			mAnimations.get( mAnimationNames.get( i ) ).setRotation( rot );
		
	}
	
	public void start( String xID ){
		
		start( xID, true );
		
	}
	
	public void start( String xID, boolean xLoop ){
		
		if( !hasAnimation( xID ) )
			return;
		
		for( PAnimationListener listener: mAnimationListeners )
			listener.onAnimationStarted( xID );
		
		if( mAnimations.containsKey( xID ) )
			mAnimations.get( xID ).start( xLoop );
		
		if( mSCMLAnimations.containsKey( xID ) )
			mSCMLAnimations.get( xID ).start( xLoop );
			
	}
	
	public void stop( String xID ){
		
		for( PAnimationListener listener: mAnimationListeners )
			listener.onAnimationStopped( xID );
		
		mAnimations.get( xID ).stop();
		
	}
	
	public void addAnimationListener( PAnimationListener xListener ){
		
		mAnimationListeners.add( xListener );
		
		if( mScmlHandler != null )
			mScmlHandler.addListener( xListener );
		
	}
	
	int j;
	
	@Override
	public void update(){
	
		if( mScmlHandler != null )
			mScmlHandler.update();
		
		for( i = 0; i < mAnimationNames.size(); i++ ){
			
			String id = mAnimationNames.get( i );
			PAnimation anim = mAnimations.get( id );
			
			anim.update();
			
			if( anim.getCurrentDelta() == 0 && anim.looped() ){
				
				for( j = 0; j < mAnimationListeners.size(); j++ )
					mAnimationListeners.get( j ).onAnimationLooped( id, anim.getLoopCnt() );
				
			}
			
			if( !anim.looped() && anim.finished() ){
				
				anim.stop();
				
				for( j = 0; j < mAnimationListeners.size(); j++ )
					mAnimationListeners.get( j ).onAnimationStopped( id );
				
			}
		}
		
	}

	@Override
	public void turn(){
		
	}

	public boolean render( SpriteBatch xSpriteBatch, String xID, float x, float y, float width, float height, float sx, float sy, float rotation, PGameTableCamera xCamera ){
		
		if( mAnimations.containsKey( xID ) )
			return mAnimations.get( xID ).render( xSpriteBatch, x, y, width, height, sx, sy, rotation, xCamera );
		else if( mSprites.containsKey( xID ) )
			return mSprites.get( xID ).render( xSpriteBatch, x, y, width, height, sx, sy, rotation, xCamera );
		else if( mSCMLAnimations.containsKey( xID ) )
			mScmlHandler.render( xSpriteBatch, mSCMLAnimations.get( xID ).getSrc(), x, y, width, height );
		else
			log( "No animation with ID (" + xID + ")" );
		
		return true;
		
	}
	
	public boolean render( SpriteBatch xSpriteBatch, String xID, float x, float y, float width, float height, float rotation, PGameTableCamera xCamera ){
		
		return render( xSpriteBatch, xID, x, y, width, height, 0, 0, rotation, xCamera );
		
	}

	public boolean render( SpriteBatch xSpriteBatch, String xID, float x, float y, float width, float height, PGameTableCamera xCamera ){
	
		return render( xSpriteBatch, xID, x, y, width, height, 0.0f, xCamera );
		
	}

	private static boolean loadTexture( String xSrc, TextureFilter xMinFilter, TextureFilter xMagFilter ){
		
		return loadTexture( xSrc, false, xMinFilter, xMagFilter );
		
	}

	private static boolean loadTexture( String xSrc, boolean forceLoad, TextureFilter xMinFilter, TextureFilter xMagFilter ){
		
		if( !mTextures.containsKey( xSrc ) || forceLoad ){
	
			if( mTextures.containsKey( xSrc ) ){
				
				Texture t = mTextures.get( xSrc );
				xMinFilter = t.getMinFilter();
				xMagFilter = t.getMagFilter();
				t.dispose();
				
			}
			
			Texture texture = new Texture( Gdx.files.internal( xSrc ) );
			texture.setFilter( xMinFilter, xMagFilter );
			texture.getTextureData().prepare();
			MipMapGenerator.setUseHardwareMipMap( true );
			MipMapGenerator.generateMipMap( texture.getTextureData().consumePixmap(), texture.getWidth(), texture.getHeight() );
			mTextures.put( xSrc, texture );
	
			return true;
			
		}
		else
			return false;
	}
	
}
