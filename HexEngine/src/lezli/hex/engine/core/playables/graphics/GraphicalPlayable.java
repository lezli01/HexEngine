package lezli.hex.engine.core.playables.graphics;

import java.io.IOException;
import java.util.LinkedList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.PGameTableCamera;
import lezli.hex.engine.core.gametable.scriptable.PGraphicalPlayableScriptable;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.gametable.Holding;
import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;
import lezli.hex.engine.moddable.interfaces.HECamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.XmlWriter;

public abstract class GraphicalPlayable<T extends GraphicalEntity> extends Playable< T > implements PGraphicalPlayableScriptable{

	private PGraphics mGraphics;
	private Rectangle mRect;
	private float mRotation;
	private Vector2 mCenter;
	
	private int mTileX, mTileY;
	private float mWidthScale, mHeightScale;
	
	private LinkedList< String > mAnimationsList;
	private String mDefAnimation;
	private String mCurrentAnimation;
	
	private boolean mCastShadow;
	private static float SH_X, SH_Y;
	private float mSH_X, mSH_Y, mShadowX, mShadowY;
	
	public float x,y,w,h;
	
	public GraphicalPlayable( T xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mRect = new Rectangle();
		
		mRotation = 0.0f;
		
		mWidthScale = xEntity.getWidth();
		mHeightScale = xEntity.getHeight();
		
		mCastShadow = xEntity.getCastShadow();
		
		String graphicsID = xEntity.getGraphics();
		
		x = xEntity.x;
		y = xEntity.y;
		w = xEntity.w;
		h = xEntity.h;
		
		if( graphicsID != null ){
			
			mGraphics = new PGraphics( engine().entitiesHolder().getGraphicsManager().get( graphicsID ) );
			log( "Loaded graphics (" + graphicsID + ")" );
		
			getGraphics().addAnimationListener( new PAnimationListener() {
				
				@Override
				public void onAnimationStopped( String xID ){

					animationEnded( xID );
					
					if( mAnimationsList.size() > 0 )
						playNextAnimation();
					else
						mCurrentAnimation = null;
					
				}
				
				@Override
				public void onAnimationStarted( String xID ){
				}
				
				@Override
				public void onAnimationLooped( String xID, int xNum ){
				}
				
			});
			
		}
		else
			log( "No [graphics] set for this entity (" + xEntity + ")" );
		
		mAnimationsList = new LinkedList< String >();
		
		
	}
	
	public int getScreenX(){
		
		int x = ( int ) getX();
		
		HECamera xCamera = engine().getGameTable().getCamera();
		
		return ( int ) ( ( x - xCamera.getPosition().x ) / xCamera.getZoom() );
	
	}
	
	public int getScreenY(){
		
		int y = ( int ) getY();
		
		HECamera xCamera = engine().getGameTable().getCamera();
		
		return ( int ) ( ( y - xCamera.getPosition().y ) / xCamera.getZoom() );
		
	}
	
	public float getX(){
		
		return mRect.getX();
		
	}

	public float getY(){
		
		return mRect.getY();
		
	}

	public float getWidth(){
		
		return mRect.getWidth();
		
	}

	public float getHeight(){
		
		return mRect.getHeight();
		
	}

	public Vector2 getCenter(){
		
//		if( mCenter == null )
			mCenter = new Vector2();
		
		mCenter.x = getX() + getWidth() / 2.0f;
		mCenter.y = getY() + getHeight() / 2.0f;
		
		return mCenter;
		
	}

	public int getTileX(){
		
		return mTileX;
		
	}

	public int getTileY(){
		
		return mTileY;
		
	}

	public float getWidthScale(){
		
		return mWidthScale;
		
	}

	public float getHeightScale(){
		
		return mHeightScale;
		
	}

	public SpriteDrawable getLargeIcon(){
		
		SpriteDrawable largeIcon = new SpriteDrawable( getGraphics().getSprite( "@LARGE_ICON" ) );
		
		if( largeIcon.getSprite().isFlipY() )
			largeIcon.getSprite().flip( false, true );
		
		return largeIcon;
		
	}

	public static void setShadowAngleVals( float x, float y ){
		
		SH_X = x;
		SH_Y = y;
		
	}
	
	public void setCastShadow( boolean xCastShadow ){
		
		mCastShadow = xCastShadow;
		
	}
	
	public void setX( float xX ){
		
		mRect.setX( xX );
		getGraphics().setPosition( xX, getY() );
		
	}

	public void setY( float xY ){
		
		mRect.setY( xY );
		getGraphics().setPosition( getX(), xY );
		
		
	}

	public void setWidth( float xWidth ){
		
		mRect.setWidth( xWidth );
		
	}

	public void setHeight( float xHeight ){
		
		mRect.setHeight( xHeight );
		
	}
	
	public void setCoordinates( float xX, float xY, float xWidth, float xHeight  ){
		
		setX( xX );
		setY( xY );
		setWidth( xWidth );
		setHeight( xHeight );
		
	}

	public void setTileX( int xTileX ){
		
		mTileX = xTileX;
		
	}

	public void setTileY( int xTileY ){
		
		mTileY = xTileY;
		
	}

	public void setGraphicsID( String xGraphicsID ){
		
		if( xGraphicsID != null ){
			
			mGraphics = new PGraphics( engine().entitiesHolder().getGraphicsManager().get( xGraphicsID ) );
			log( "Loaded graphics (" + xGraphicsID + ")" );
		
			getGraphics().addAnimationListener( new PAnimationListener() {
				
				@Override
				public void onAnimationStopped( String xID ){
					
					if( xID.equals( mAnimationsList.getFirst() ) ){
						
						animationEnded( xID );
						playNextAnimation();
						
					}
					
				}
				
				@Override
				public void onAnimationStarted( String xID ){
					
				}
				
				@Override
				public void onAnimationLooped( String xID, int xNum ){
					
				}
				
			});
			
		}
		
	}

	public int distance( GraphicalPlayable<?> xPlayable ){
		
		int fromX = getTileX() + getTileY() / 2;
		int fromY = getTileY();
		
		int toX = xPlayable.getTileX() + xPlayable.getTileY() / 2;
		int toY = xPlayable.getTileY();
		
		int dx = toX - fromX;
		int dy = toY - fromY;
		
		if( Math.signum( dx ) != Math.signum( dy ) )
			return Math.abs( dx ) + Math.abs( dy );
		else
			return Math.max( Math.abs( dx ), Math.abs( dy ) );
		
	}

	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
	
		super.save( xmlWriter );
	
		xmlWriter.attribute( "x", getTileX() ).
				  attribute( "y", getTileY() );
		
	}

	@Override
	public void load( Holding holding ){
	
		super.load( holding );
	
		if( hasSaved( holding, "x" ) )
			setTileX( Integer.parseInt( holding.getValues().get( "x" ) ) );
		
		if( hasSaved( holding, "y" ) )
			setTileY( Integer.parseInt( holding.getValues().get( "y" ) ) );
		
	}

	@Override
	public void update(){
	
		if( getGraphics() != null )
			getGraphics().update();
		
	}

	public boolean render( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		if( currentAnimation() != null )
			return getGraphics().render( xSpriteBatch, currentAnimation(), getX(), getY(), getWidth(), getHeight(), mRotation, xCamera );

		return false;
		
	}
	
	public boolean castShadow( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){
		
		if( !mCastShadow )
			return false;
		
		calcShadow();
		
		boolean ret = false;
		
		xSpriteBatch.setColor( 0.0f, 0.0f, 0.0f, 0.65f );
		
		if( currentAnimation() != null )
			ret = getGraphics().render( xSpriteBatch, currentAnimation(), getX(), getY(), getWidth(), getHeight(), mShadowX, mShadowY, mRotation, xCamera );
		
		xSpriteBatch.setColor( 1.0f, 1.0f, 1.0f, 1.0f );
		
		return ret;
		
	}
	
	private void calcShadow(){
		
		if( mSH_X != SH_X ){
			
			mSH_X = SH_X;
			
			mShadowX = getWidth() * mSH_X;
			
		}
		
		if( mSH_Y != SH_Y ){
			
			mSH_Y = SH_Y;
			
			mShadowY = getHeight() * mSH_Y;
			
		}
			
	}

	protected PGraphics getGraphics(){
		
		return mGraphics;
		
	}

	protected void setRotation( float xRotation ){
		
		getGraphics().setRotation( xRotation );
		
		mRotation = xRotation;
		
	}

	protected String currentAnimation(){
		
		String toRender = mDefAnimation;
		
		if( mCurrentAnimation != null )
			toRender = mCurrentAnimation;
		
		if( mAnimationsList.size() > 0 )
			toRender = mAnimationsList.getFirst();
		
		return toRender;
		
	}

	protected abstract void animationEnded( String xID );
	
	protected void setDefaultAnimation( String xDefAnimation ){
		
		if( mDefAnimation != null && xDefAnimation != null ){
			
			if( mDefAnimation.equals( xDefAnimation ) )
				return;
			
		}
		
		mDefAnimation = xDefAnimation;

		if( xDefAnimation != null )
			getGraphics().start( mDefAnimation, true );
		
	}
	
	protected void setCurrentAnimation( String xCurrentAnimation ){
		
		if( !getGraphics().hasAnimation( xCurrentAnimation ) )
			return;
		
		if( mCurrentAnimation != xCurrentAnimation ){
			mCurrentAnimation = xCurrentAnimation;
			getGraphics().start( mCurrentAnimation );
		}
		
	}
	
	protected void addAnimation( String xAnim ){
		
		mAnimationsList.add( xAnim );

		if( mAnimationsList.size() == 1 )
			startAnimation();
		
	}
	
	private void startAnimation(){
		
		getGraphics().setPosition( getX(), getY() );
		
		getGraphics().start( mAnimationsList.getFirst(), false );
		
	}
	
	private void playNextAnimation(){
		
		mAnimationsList.pop();
		
		if( mAnimationsList.size() > 0 ){
			
			getGraphics().start( mAnimationsList.getFirst(), false );
			
		}
		
	}
	
	@Override
	public int x(){

		return getTileX();
	
	}
	
	@Override
	public int y(){

		return getTileY();
	
	}
	
}
