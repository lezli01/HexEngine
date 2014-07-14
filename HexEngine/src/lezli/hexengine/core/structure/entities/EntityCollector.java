package lezli.hexengine.core.structure.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import lezli.hexengine.core.structure.entities.graphics.GraphicalEntity;

import com.badlogic.gdx.utils.XmlReader.Element;

public abstract class EntityCollector < T extends Entity > extends GraphicalEntity{

	public EntityCollector( String xFileName ){
		
		super( xFileName );
		
	}
	
	public EntityCollector( Element xElement ){
		
		super( xElement );
		
	}
	
	public abstract T get( String xID );
	public abstract ArrayList< T > getAll();
	public abstract int getSize();
	
	protected abstract void add( T xEntity );

	@SuppressWarnings("unchecked")
	protected Class< T > getClazz(){
		
		return ( ( Class< T > ) ( ( ParameterizedType ) this.getClass().getGenericSuperclass() ).getActualTypeArguments()[ 0 ] );
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
		
		for( Element element: xElement.getChildrenByName( getClazz().getSimpleName() ) )
			try {
				
				add( getClazz().getConstructor( Element.class ).newInstance( element ) );
			
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		
	}
	
}
