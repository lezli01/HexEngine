package lezli.hex.engine.core.playables.map.tile;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.PGameTableCamera;
import lezli.hex.engine.core.playables.LivingPlayable;
import lezli.hex.engine.core.playables.building.PBuilding;
import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import lezli.hex.engine.core.playables.map.tile.placeholder.PPlaceholder;
import lezli.hex.engine.core.playables.unit.skills.PAffect;
import lezli.hex.engine.core.structure.entities.map.tile.Tile;
import lezli.hex.engine.core.structure.entities.map.tile.TilePlaceholder;
import lezli.hex.engine.core.structure.entities.skill.affect.Affect;
import lezli.hex.engine.moddable.interfaces.HETile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PTile extends GraphicalPlayable< Tile > implements HETile{

	public static final int DEF_SIZE = ( int ) ( Gdx.graphics.getHeight() / 5.0f );
	
	private boolean mSelected;
	private boolean mPathHighlighted;
	private boolean mWayHighlighted;
	private boolean mSkillRangeHighlighted;
	private boolean mSkillAreaHighlighted;
	private boolean mBuildHighlight;
	private boolean mCanWalk;

	private boolean mFog, mHeed;
	
	private float mHeight;
	
	private GraphicalPlayable<?> mPlayable;
	
	private ArrayList< Affect > mOnEnterAffects;
	private ArrayList< Affect > mOnExitAffects;
	private ArrayList< Affect > mOnStayAffects;
	
	private ArrayList< PPlaceholder > mPlaceholders;
	
	public PTile( Tile xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mSelected = false;
		mPathHighlighted = false;
		mWayHighlighted = false;
		mSkillRangeHighlighted = false;
		mSkillAreaHighlighted = false;
		mBuildHighlight = false;
		mCanWalk = xEntity.canWalk();
		
		mHeight = xEntity.getHeight();
		
		mOnEnterAffects = xEntity.getOnEnterAffects();
		mOnExitAffects = xEntity.getOnExitAffects();
		mOnStayAffects = xEntity.getOnStayAffects();
		
		mPlaceholders = new ArrayList< PPlaceholder >();
		
		getGraphics().start( "@IDLE" );
		
	}

	public GraphicalPlayable<?> getPlayable(){
		
		return mPlayable;
		
	}
	
	public float getHeightMultiplier(){

		return mHeight;
		
	}

	public Vector2 getUnitCoordinates( GraphicalPlayable<?> xUnit ){
		
		float unitHeight = xUnit.getHeightScale() * getHeight();
		
		return new Vector2( getX() + xUnit.x * getWidth(),
							getY() + xUnit.y * getHeight() - unitHeight );
		
	}

	public void addPlaceholders( ArrayList< TilePlaceholder > xPlaceholders ){
		
		for( TilePlaceholder placeholder: xPlaceholders ){
			
			PPlaceholder pph = new PPlaceholder( engine().entitiesHolder().getPlaceholderManager().get( placeholder.placeholder ), engine() );
			pph.setWidth( getWidth() * placeholder.w );
			pph.setHeight( getHeight() * placeholder.h );
			pph.setY( getY() + getHeight() * placeholder.y - pph.getHeight() / 2 );
			pph.setX( getX() + getWidth() * placeholder.x - pph.getWidth() / 2 );
			mPlaceholders.add( pph );
			
		}
		
	}
	
	public void setPlayable( GraphicalPlayable<?> xPlayable, boolean xResize ){
		
		mPlayable = xPlayable;
		
		if( xResize )
			resizePlayable( mPlayable );
		
	}
	
	public void releasePlayable(){
		
		mPlayable = null;
		
	}
	
	public boolean hasPlayable(){
		
		return mPlayable != null;
		
	}
	
	public boolean canWalk(){
		
		return mCanWalk;
		
	}
	
	@Override
	public boolean isEmpty(){

		return !hasPlayable();
	
	}
	
	public void setWalkable( boolean xWalkable ){
		
		mCanWalk = xWalkable;
		
	}
	
	public void affectOnEnter( LivingPlayable< ? > xLiving ){
		
		for( Affect affect: mOnEnterAffects ){
			
			PAffect pAffect = new PAffect( affect, engine() );
			pAffect.apply( null, null, xLiving );
			
		}
		
	}

	public void affectOnExit( LivingPlayable< ? > xLiving ){
		
		for( Affect affect: mOnExitAffects ){
			
			PAffect pAffect = new PAffect( affect, engine() );
			pAffect.apply( null, null, xLiving );
			
		}
		
	}
	
	public void affectOnStay( LivingPlayable< ? > xLiving ){
		
		for( Affect affect: mOnStayAffects ){
			
			PAffect pAffect = new PAffect( affect, engine() );
			pAffect.apply( null, null, xLiving );
			
		}
		
	}
	
	public void resizePlayable( GraphicalPlayable<?> xUnit ){

		Vector2 coords = getUnitCoordinates( xUnit );
		
		xUnit.setX( coords.x );
		xUnit.setY( coords.y );
		xUnit.setWidth( getWidth() * xUnit.getWidthScale() );
		xUnit.setHeight( getHeight() * xUnit.getHeightScale() );
		
	}
	
	public boolean isSelected(){
		
		return mSelected;
		
	}
	
	public void select(){
		
		mSelected = true;
		
	}
	
	public void deSelect(){
		
		mSelected = false;
		
	}
	
	public boolean isPathHighlighted(){
		
		return mPathHighlighted;
		
	}
	
	public void pathHighlight(){
		
		mPathHighlighted = true;
		
	}
	
	public void pathUnHighlight(){
		
		mPathHighlighted = false;
		
	}
	
	public boolean isWayHighlighted(){
		
		return mWayHighlighted;
		
	}
	
	public void wayHighlight(){
		
		mWayHighlighted = true;
		
	}
	
	public void wayUnHighlight(){
		
		mWayHighlighted = false;
		
	}
	
	public boolean isSkillRangeHighlighted(){
		
		return mSkillRangeHighlighted;
		
	}
	
	public void skillRangeHighlight(){
	
		mSkillRangeHighlighted = true;
		
	}
	
	public void skillRangeUnHighlight(){
		
		mSkillRangeHighlighted = false;
		
	}
	
	public boolean isSkillAreaHighlighted(){
		
		return mSkillAreaHighlighted;
		
	}
	
	public void skillAreaHighlight(){
	
		mSkillAreaHighlighted = true;
		
	}
	
	public void skillAreaUnHighlight(){
		
		mSkillAreaHighlighted = false;
		
	}
	
	public boolean isBuildHighlighted(){
		
		return mBuildHighlight;
		
	}
	
	public void buildHighlight(){
	
		mBuildHighlight = true;
		
	}
	
	public void buildUnHighlight(){
		
		mBuildHighlight = false;
		
	}
	
	public boolean isFog(){
		
		return mFog;
		
	}
	
	public void fog(){
	
		mFog = true;
		
	}
	
	public void unFog(){
		
		mFog = false;
		
	}
	
	public boolean isHeed(){
		
		return mHeed;
		
	}
	
	public void heed(){
	
		mHeed = true;
		
	}
	
	public void unHeed(){
		
		mHeed = false;
		
	}
	
	int i;
	
	@Override
	public void update(){
		
		getGraphics().update();
		
		for( i = 0; i < mPlaceholders.size(); i++ ){
		
			mPlaceholders.get( i ).update();
			
		}
		
	}

	@Override
	public void turn(){
		
	}

	public boolean render( SpriteBatch xSpriteBatch, float x, float y, PGameTableCamera xCamera ){
		
		return getGraphics().render( 
			xSpriteBatch, 
			"@IDLE", 
			x,
			y, 
			getWidth(), 
			getHeight(), 
			xCamera );
		
	}
	
	@Override
	public boolean render( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		boolean retVal = true;
		
		if( !isFog() )
			retVal = getGraphics().render( xSpriteBatch, "@IDLE", getX(), getY(), getWidth(), getHeight(), xCamera );
		
		return retVal;
		
	}
	
	public void renderOnTileItems( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		if( hasPlayable() ){
			
			renderPlayable( xSpriteBatch, xCamera );

			if( getPlayable() instanceof PBuilding )
				return;
		}
		
		renderPlaceholders( xSpriteBatch, xCamera );
		
	}
	
	public boolean renderPlaceholders( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){
		
		if( isFog() || isHeed() )
			return true;
		
		for( i = 0; i < mPlaceholders.size(); i++ )
			mPlaceholders.get( i ).render( xSpriteBatch, xCamera );

		return true;
		
	}
	
	@Override
	public boolean castShadow( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		for( PPlaceholder placeholder: mPlaceholders ){
			placeholder.castShadow( xSpriteBatch, xCamera );
		}
		
		if( hasPlayable() ) getPlayable().castShadow( xSpriteBatch, xCamera );
		
		return true;
	
	}
	
	public boolean castPlayableShadow( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		if( isFog() || isHeed() )
			return true;
		
		if( hasPlayable() ) return getPlayable().castShadow( xSpriteBatch, xCamera );
		
		return true;
	
	}
	
	public boolean castPlaceholderShadow( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){

		if( isFog() || isHeed() )
			return true;
		
		for( i = 0; i < mPlaceholders.size(); i++ )
			mPlaceholders.get( i ).castShadow( xSpriteBatch, xCamera );

		return true;
	
	}
	
	public boolean renderPlayable( SpriteBatch xSpriteBatch, PGameTableCamera xCamera ){
		
		if( isFog() || isHeed() )
			return true;
		
		if( hasPlayable() )
			return getPlayable().render( xSpriteBatch, xCamera );
		
		return false;
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}

}
