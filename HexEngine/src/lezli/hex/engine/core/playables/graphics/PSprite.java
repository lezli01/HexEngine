package lezli.hex.engine.core.playables.graphics;

import lezli.hex.engine.core.gametable.PGameTableCamera;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.graphics.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class PSprite extends Playable< Sprite >{

	private Rectangle mRect;
	private TextureRegion mTextureRegion;
	private PShearedSprite mSprite;
	
	public PSprite( Sprite xEntity, Texture xTexture ){
		
		super( xEntity, null );
		
		mRect = xEntity.getRect();
		
		mTextureRegion = new TextureRegion( xTexture, (int)mRect.getX(), (int)mRect.getY(), (int)mRect.getWidth(), (int)mRect.getHeight() );
		mTextureRegion.flip( false, true );
		mSprite = new PShearedSprite( mTextureRegion );
		
	}

	public com.badlogic.gdx.graphics.g2d.Sprite getSprite(){
		
		return mSprite;
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void turn() {
		
	}

	public boolean render( SpriteBatch xSpriteBatch, float x, float y, float width, float height, float rotation, PGameTableCamera xCamera ){

		if( x + width > ( 0 - Gdx.graphics.getWidth() / xCamera.zoom / 2.0f ) + xCamera.position.x &&
				x < Gdx.graphics.getWidth() / 2.0f / xCamera.zoom + xCamera.position.x  &&
				y < Gdx.graphics.getHeight() / 2.0f / xCamera.zoom + xCamera.position.y &&
				y + height > 0 - Gdx.graphics.getHeight() / 2.0f / xCamera.zoom + xCamera.position.y ){
		
			xSpriteBatch.draw( mTextureRegion, x, y, width / 2, height / 2, width, height, 1.0f, 1.0f, rotation );
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
