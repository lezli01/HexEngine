package lezli.hex.engine.core.structure.utils;

import com.badlogic.gdx.Gdx;

import lezli.hex.engine.core.structure.entities.id.IDGenerator;

public class Util {

	private String mLogTag = getTypeString() + IDGenerator.getInstance().generateID( this );
	
	protected void log( String xMessage ){
		
		Gdx.app.debug( mLogTag, xMessage );
		
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
	
}
