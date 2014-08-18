package lezli.hex.engine.core.gametable;

import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import lezli.hex.engine.core.playables.map.PMap;
import lezli.hex.engine.moddable.interfaces.HECamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PGameTableCamera extends OrthographicCamera implements HECamera{

	public static final float ZOOM_MAX = 1.0f;
	public static final float ZOOM_MIN = 0.5f;
	
	private float mMoveFromX, mMoveFromY, mMoveToX, mMoveToY, mTranslateDuration, mTranslateCurrentTime;
	private boolean mCancellable;
	
	private float mWidth, mHeight;
	
	private float mZoomFrom,mZoomTo, mZoomDuration, mZoomCurrentTime;
	
	public PGameTableCamera( PMap xMap ){
		
		mWidth = xMap.mapWidth() * PMap.mTileWidth;
		mHeight = xMap.mapHeight() * ( PMap.mTileSize + PMap.mTileLean );
		
	}
	
	@Override
	public void translate( float x, float y ){

		if( !isMoving() )
			super.translate( x, y );
	
	}
	
	public void moveBy( float xX, float xY, float xDuration, boolean canCancel ){
		
		if( !mCancellable && !canCancel )
			return;
		moveTo( position.x + xX, position.y + xY, xDuration, true );

	}
	
	public void moveTo( float xX, float xY, float xDuration, boolean xCancellable ){
		
		mMoveToX = xX;
		mMoveToY = xY;
		mMoveFromX = position.x;
		mMoveFromY = position.y;
		mCancellable = xCancellable;
		
		mTranslateDuration = xDuration;
		mTranslateCurrentTime = 0;
		
	}
	
	public void moveTo( Vector2 xPos, float xDuration, boolean xCancellable ){
		
		moveTo( xPos.x, xPos.y, xDuration, xCancellable );
		
	}
	
	public void moveTo( GraphicalPlayable<?> xPlayable, float xTranslateDuration, boolean xCancellable ){
		
		moveTo( xPlayable.getCenter(), xTranslateDuration, xCancellable );
		
	}
	
	public boolean isMoving(){
		
		return mTranslateCurrentTime < mTranslateDuration;
		
	}
	
	public void makeCancellable(){
		
		mCancellable = true;
		
	}
	
	public void cancel(){
		
		if( mCancellable )
			mTranslateCurrentTime = mTranslateDuration + 1;
		
	}
	
	public void zoom( float xZoomTo ){
		
		zoom( xZoomTo, 0.5f );
		
	}
	
	public void zoom( float xZoomTo, float xZoomDuration ){
		
		if( xZoomTo > ZOOM_MAX )
			xZoomTo = ZOOM_MAX;
		if( xZoomTo < ZOOM_MIN )
			xZoomTo = ZOOM_MIN;
		
		mZoomFrom = zoom;
		mZoomTo = xZoomTo;
		mZoomDuration = xZoomDuration;
		mZoomCurrentTime = 0.0f;
		
	}
	
	@Override
	public void update(){

		//Zoom
		
		mZoomCurrentTime += Gdx.graphics.getDeltaTime();
		
		float a = mZoomCurrentTime / mZoomDuration;
		
		if( a <= 1.0f ){
			
			zoom = Interpolation.exp5Out.apply( mZoomFrom, mZoomTo, a );
			
		}
		
		//Move
		
		mTranslateCurrentTime += Gdx.graphics.getDeltaTime();
		
		a = mTranslateCurrentTime / mTranslateDuration;
		if( a <= 1.0f ){
		
			position.x = Interpolation.exp5Out.apply( mMoveFromX, mMoveToX, a );
			position.y = Interpolation.exp5Out.apply( mMoveFromY, mMoveToY, a );
		
		}
		
		if( position.x < 0 )
			position.x = 0;
		
		if( position.x > mWidth )
			position.x = mWidth;
		
		if( position.y < 0 )
			position.y = 0;
		
		if( position.y > mHeight )
			position.y = mHeight;
		
		super.update();
		
	}

	@Override
	public Vector3 getPosition() {

		return position;

	}
	
	@Override
	public float getZoom() {

		return zoom;

	}
	
}
