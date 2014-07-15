package lezli.hexengine.core.playables.graphics;

import lezli.hexengine.core.gametable.PGameTableCamera;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.structure.entities.graphics.Frame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class PFrame extends Playable< Frame >{

	private int mMultiply;
	private Rectangle mRect;
	
	private TextureRegion mTextureRegion;
	private PShearedSprite mSprite;

	public PFrame( Frame xEntity, Texture xTexture, int xXstart, int xYstart ){
		
		super( xEntity, null );
		
		mRect = new Rectangle( xEntity.getRect() );
		mMultiply = xEntity.getMultiply();
		
		mTextureRegion = new TextureRegion( xTexture, (int)mRect.getX() + xXstart, (int)mRect.getY() + xYstart, (int)mRect.getWidth(), (int)mRect.getHeight() );
		mTextureRegion.flip( false, true );
		mSprite = new PShearedSprite( mTextureRegion );
		
	}

	public Sprite getSprite(){
		
		return mSprite;
		
	}

	public Rectangle getRect(){
		
		return mRect;
		
	}
	
	public int getMultiply(){
		
		return mMultiply;
		
	}
	
	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
	}

	public boolean render( SpriteBatch xSpriteBatch, float x, float y, float width, float height, float rotation, PGameTableCamera xCamera ){

		if( x + width > ( 0 - Gdx.graphics.getWidth() * xCamera.zoom / 2.0f ) + xCamera.position.x &&
				x < Gdx.graphics.getWidth() / 2.0f * xCamera.zoom + xCamera.position.x  &&
				y < Gdx.graphics.getHeight() / 2.0f * xCamera.zoom + xCamera.position.y &&
				y + height > 0 - Gdx.graphics.getHeight() / 2.0f * xCamera.zoom + xCamera.position.y ){

			mSprite.setBounds( x, y, width, height );
			mSprite.setOrigin( x + width / 2.0f, y + height / 2.0f );
			
			mSprite.draw( xSpriteBatch );
			
			return true;
		
		}
		
		return false;
		
	}
	
	public boolean render( SpriteBatch xSpriteBatch, float x, float y, float width, float height, float sx, float sy, float rotation, PGameTableCamera xCamera ){

		mSprite.shear( sx, sy );
		
		boolean ret = render( xSpriteBatch, x, y, width, height, rotation, xCamera );
		
		mSprite.shear( 0, 0 );
		
		return ret;
		
	}

}
