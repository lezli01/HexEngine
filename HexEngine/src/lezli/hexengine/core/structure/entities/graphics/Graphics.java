package lezli.hexengine.core.structure.entities.graphics;

import java.util.HashMap;

import lezli.hexengine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Holds graphical data for an atlas.
 * @author Lezli
 *
 */
public class Graphics extends Entity{

	private String mSrc;
	private String mScml;
	
	private HashMap< String, Sprite > mSprites;
	private HashMap< String, Animation > mAnimations;
	private HashMap< String, SCMLAnimation > mScmlAnimations;
	private String mFilter;
	
	public Graphics( String xFileName ){
		
		super( xFileName );
		
	}
	
	public String getSrc(){
		
		return mSrc;
		
	}
	
	public String getScml(){
		
		return mScml;
		
	}
	
	public Sprite getSprite( String xID ){
		
		return mSprites.get( xID );
		
	}
	
	public HashMap< String, Sprite > getSprites(){
		
		return mSprites;
		
	}
	
	public Animation getAnimation( String xID ){
		
		return mAnimations.get( xID );
		
	}
	
	public HashMap< String, Animation > getAnimations(){
		
		return mAnimations;
		
	}
	
	public HashMap< String, SCMLAnimation > getScmlAnimations(){
		
		return mScmlAnimations;
		
	}
	
	public String getFilter(){
		
		return mFilter;
		
	}
	
	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setSrc( xElement.getAttribute( "src" ) );
		setFilter( xElement.getAttribute( "filter" ) );
		
		try{
			
			setScml( xElement.getAttribute( "scml" ) );
			
		}catch( Exception e ){
			
		}
		
		int childIndex = 0;
		
		for( childIndex = 0; childIndex < xElement.getChildCount(); childIndex++ ){

			Element graphicsElement = xElement.getChild( childIndex );
			loadGraphicsElement( graphicsElement );
			
		}
		
	}
	
	private void loadGraphicsElement( Element xGraphicsElement ){
		
		String graphicsType = xGraphicsElement.getName();
		
		String id = xGraphicsElement.getAttribute( "id" );
		String src = xGraphicsElement.getAttribute( "src" );

		if( graphicsType.equals( GraphicsElement.TYPE_SPRITE ) ){
			
			addSprite( id, src, xGraphicsElement );
			
		}
		
		if( graphicsType.equals( GraphicsElement.TYPE_ANIMATION ) ){
			
			addAnimation( id, src, xGraphicsElement );
			
		}
		
		if( graphicsType.equals( GraphicsElement.TYPE_SCML_ANIMATION ) ){
			
			addSCMLAnimation( id, src, xGraphicsElement );
			
		}
		
	}
	
	private void setSrc( String xSrc ){
		
		mSrc = xSrc;
		log( "src set to(" + mSrc + ")" );
		
	}
	
	private void setScml( String xScml ){
		
		mScml = xScml;
		log( "scml set to (" + mScml + ")" );
		
	}

	private void setFilter( String xFilter ){
		
		mFilter = xFilter;
		
		log( "filter set to (" + mFilter + ")" );
		
	}
	
	private void addSprite( String xId, String xFileName, Element xGraphicsElement ){
		
		Sprite newSprite = new Sprite( xFileName );
		
		for( Element particle: xGraphicsElement.getChildrenByName( "Particle" ) )
			newSprite.addParticle( particle.getAttribute( "file" ) );

		mSprites.put( xId, newSprite );
		log( "Added new Sprite (" + xId + ")" );
		
		
	}
	
	private void addAnimation( String xId, String xFileName, Element xGraphicsElement ){
		
		Animation newAnimation = new Animation( xFileName );
		newAnimation.progress( xGraphicsElement );
		mAnimations.put( xId, newAnimation );
		
		log( "Added new Animation (" + xId + ";" + newAnimation.getID() + ")" );
		
	}
	
	private void addSCMLAnimation( String xID, String xAnimID, Element xGraphicsElement ){

		SCMLAnimation newAnimation = new SCMLAnimation( xGraphicsElement );
		newAnimation.progress( xGraphicsElement );
		mScmlAnimations.put( xID, newAnimation );
		
		log( "Added new SCMLAnimation (" + xID + ";" + newAnimation.getID() + ")" );
		
	}
	
	@Override
	protected void init(){

		mSprites = new HashMap< String, Sprite >();
		mAnimations = new HashMap< String, Animation >();
		mScmlAnimations = new HashMap< String, SCMLAnimation >();
		
	}
	
}
