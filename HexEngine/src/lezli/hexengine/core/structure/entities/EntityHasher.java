package lezli.hexengine.core.structure.entities;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.XmlReader.Element;

public class EntityHasher< T extends Entity > extends EntityCollector< T >{

	public EntityHasher( Element xElement ){
	
		super( xElement );

	}
	
	public EntityHasher( String xFileName ){
		
		super( xFileName );
		
	}

	private HashMap< String, T > mEntities;
	
	public T get( String xId ){
		
		return mEntities.get( xId );
		
	}
	
	public ArrayList< T > getAll(){
		
		return new ArrayList< T >( mEntities.values() );
		
	}
	
	@Override
	public int getSize() {
	
		return mEntities.size();
		
	}

	protected void add( T xEntity ){
		
		mEntities.put( xEntity.getID(), xEntity );
		log( "Added (" + xEntity.getID() + ";" + mEntities.size() + ")" );
		
	}
	
	@Override
	protected void init(){
		
		mEntities = new HashMap<String, T>();
		
	}
	
}
