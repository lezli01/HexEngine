package lezli.hex.engine.core.structure.entities;

import java.io.IOException;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.structure.entities.id.IDGenerator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * The most basic element of the engine. Everything
 * the engine has is basically an <b>Entity</b>.
 * @author Lezli
 *
 */
public abstract class Entity{

	private String mLogTag = getTypeString() + "undefined";
	private static int PARSE_LOG_DEPTH = 0;
	
	private String mID;
	private String mName;
	private String mDescription;
	private String mFileName;

	private static HexEngine ENGINE;
	
	public Entity( Element xElement ){

		PARSE_LOG_DEPTH++;
		init();
		parse( xElement );
		PARSE_LOG_DEPTH--;
		
	}
	
	/**
	 * Loads the Entity from an existing Xml file.
	 * @param xFileName
	 */
	public Entity( String xFileName ){
		
		PARSE_LOG_DEPTH++;
		mFileName = xFileName;
		init();
		parseFromXml( xFileName );
		PARSE_LOG_DEPTH--;
		
	}
	
	public String getFileName(){
		
		return mFileName;
		
	}
	
	public static void setEngine( HexEngine xEngine ){
		
		ENGINE = xEngine;
		
	}
	
	protected HexEngine engine(){
		
		return ENGINE;
		
	}
	
	/**
	 * Returns the <b>id</b> of the <b>Entity</b>.
	 * @return
	 * String containing the id.
	 */
	public String getID(){
		
		return mID;
		
	}
	
	/**
	 * Returns the name of the <b>Entity</b>.
	 * @return
	 * String containing the name.
	 */
	public String getName(){
		
		return mName;
		
	}
	
	/**
	 * Returns the <b>Description</b> of the <b>Entity</b>.
	 * @return
	 * The <b>Description</b> of the <b>Entity</b>.
	 */
	public String getDescription(){
		
		return mDescription;
		
	}

	@Override
	public String toString() {
	
		return getID();
	
	}

	private boolean parseFromXml( String xFileName ){
		
		log( "Starting parsing from (" + xFileName + ")" );
		
		XmlReader mXmlReader = new XmlReader();
		Element mElement;
		
		try {
			
			mElement = mXmlReader.parse( Gdx.files.internal( xFileName ) );
			
		}catch( Exception e ){
			
			log( "Error parsing internal file (" + xFileName + ")", e );
			
			try {
			
				if( Gdx.files.local( xFileName ).exists() )
					mElement = mXmlReader.parse( Gdx.files.local( xFileName ) );
				else
					return false;
				
			} catch ( IOException e1 ){

				log( "Error parsing local file (" + xFileName + ")", e );
				PARSE_LOG_DEPTH--;

				return false;
				
			}
			
			
		}
		
		parse( mElement );
		
		log( "Parsing completed." );
	
		return true;
		
	}

	private void setID( String xId ){
		
		mID = xId;
		setLogTag( getTypeString() + mID );
		log( "Entity ID: " + mID );
		
	}
	
	private String getTypeString(){
		
		String typeString = "";
		
		Class<?> clazz = getClass();
		typeString = clazz.getSimpleName();
		
		while( !clazz.getSimpleName().equals( "Object" ) ){
			clazz = clazz.getSuperclass();
			if( !clazz.getSimpleName().equals( "Object" ) )
				typeString = typeString + ":" + clazz.getSimpleName();
		}
		
		typeString = "[" + typeString + "]";
		return typeString;
		
	}
	
	private void setName( String xName ){
		
		mName = xName;

		mName = mName.replace( "\\n", "\n" ); 
		
		log( "Entity name: " + mName );
		
	}
	
	private void setDescription( String xDescription ){
		
		mDescription = xDescription;
		log( "Changed description(" + mDescription + ")" );
		
	}
	
	private void setLogTag( String xLogTag ){
		
		mLogTag = xLogTag;
		
	}
	
	private void generateID(){
		
		setID( IDGenerator.getInstance().generateID( this ) );
		
	}

	public String getXMLTag(){

		return getClass().getSimpleName();
		
	}
	
	protected void log( String xMessage ){

		engine().logger().log( mLogTag + ": " + xMessage, PARSE_LOG_DEPTH );
		
	}
	
	protected void log( String xMessage, Throwable xException ){
		
		engine().logger().log( mLogTag + ": " + xMessage + "\n" + xException.toString(), PARSE_LOG_DEPTH );
		
	}
	
	protected  void parse( Element xElement ){
		
		try{
			setID( xElement.getAttribute( "id" ) );
		}catch( GdxRuntimeException e ){
			log( "No attribute [id] generating automatically!" );
			generateID();
		}

		try{
			setName( xElement.getAttribute( "name" ) );
		}
		catch( GdxRuntimeException e ){
			log( "No attribute [name]!" );
		}
		
		try{
			setDescription( xElement.getAttribute( "description" ) );
		}catch( GdxRuntimeException e ){
			log( "No attribute [description]!" );
			
		}
		
	}
	
	protected abstract void init();
	
}
