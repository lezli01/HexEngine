package lezli.hexengine.core.structure.entities.graphics;

import lezli.hexengine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;


/**
 * Extended <b>Entity</b> which has <b>Graphics</b>
 * and <b>Description</b> fields.
 * @author Lezli
 *
 */
public abstract class GraphicalEntity extends Entity{

	private String mGraphics;
	private float mWidth;
	private float mHeight;
	private boolean mCastShadow;
	
	public float x,y,w,h;
	
	public GraphicalEntity( Element xElement ){
		
		super( xElement );
		
	}

	public GraphicalEntity( String xFileName ){
		
		super( xFileName );
		
	}
	
	/**
	 * Returns the <b>Graphics</b> of the <b>ExtendedEntity</b>.
	 * @return
	 * The <b>Graphics</b> of the <b>ExtendedEntity</b>.
	 */
	public String getGraphics(){
		
		return mGraphics;
		
	}
	
	public float getWidth(){
		
		return mWidth;
		
	}
	
	public float getHeight(){
		
		return mHeight;
		
	}
	
	public boolean getCastShadow(){
		
		return mCastShadow;
		
	}
	
	protected void setGraphicsId( String xGraphicsId ){
		
		mGraphics = xGraphicsId;
		log( "Graphics set to(" + mGraphics + ")" );
		
	}
	
	private void setWidth( float xWidth ){
		
		mWidth = xWidth;
		log( "Width set (" + mWidth + ")" );
		
	}

	private void setHeight( float xHeight ){
		
		mHeight = xHeight;
		log( "Height set (" + mHeight + ")" );
		
	}

	
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		try{
			
			setGraphicsId( xElement.getAttribute( "graphics" ) );
		
		}catch( Exception e ){
			
			log( "No attribute [graphics]" );
			
		}
		
		try{

			setWidth( xElement.getFloat( "width" ) );
		
		}catch( Exception e ){
			
			log( "No attribute [width]" );
			
		}
		
		try{

			setHeight( xElement.getFloat( "height" ) );
		
		}catch( Exception e ){
			
			log( "No attribute [height]" );
			
		}
		
		try{
			
			mCastShadow = xElement.getBoolean( "shadow", true );
			
		}catch( Exception e ){
			
		}
		
		try{ x = xElement.getFloat( "x" ); }catch( Exception e ){};
		try{ y = xElement.getFloat( "y" ); }catch( Exception e ){};
		try{ w = xElement.getFloat( "w" ); }catch( Exception e ){};
		try{ h = xElement.getFloat( "h" ); }catch( Exception e ){};
	}
	
}
