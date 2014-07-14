package lezli.hexengine.core.structure.utils.managers;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hexengine.core.structure.entities.Entity;
import lezli.hexengine.core.structure.utils.Util;

public abstract class Manager<T extends Entity> extends Util{

	private HashMap< String, T > mEntities = new HashMap< String, T >();

	public Manager(){

		init();
		
	}
	
	public void add( T xEntity ){
		
		mEntities.put( xEntity.getID(), xEntity );
		log( "New entity added (" + xEntity + ";" + mEntities.size() + ")" );
		
	}
	
	public T get( String xId ){
		
		return mEntities.get( xId );
		
	}
	
	public ArrayList< T > getAll(){
		
		return new ArrayList< T >( mEntities.values() );
		
	}
	
	protected abstract void init();
	
}
