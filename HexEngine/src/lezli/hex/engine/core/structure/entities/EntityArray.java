package lezli.hex.engine.core.structure.entities;

import java.util.ArrayList;

import com.badlogic.gdx.utils.XmlReader.Element;

public class EntityArray< T extends Entity > extends EntityCollector< T >{

	private ArrayList< T > mEntities;

	public EntityArray( Element xElement ){
		
		super( xElement );
		
	}
	
	public EntityArray( String xFileName ){
		
		super( xFileName );
		
	}

	
	@Override
	public T get( String xID ){

		for( T entity: mEntities )
			if( entity.getID().equals( xID ) )
				return entity;
		
		return null;
		
	}
	
	public T get( int xIdx ){
		
		return mEntities.get( xIdx );
		
	}

	@Override
	public ArrayList<T> getAll() {

		return mEntities;
	
	}
	
	@Override
	public int getSize() {
	
		return mEntities.size();
		
	}

	@Override
	protected void add( T xEntity ){

		mEntities.add( xEntity );
		log( "Added (" + xEntity + ")" );
		
	}

	@Override
	protected void init() {

		mEntities = new ArrayList< T >();
		
	}

}
