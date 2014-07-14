package lezli.hexengine.core.structure.entities.id;

import java.util.ArrayList;
import java.util.HashMap;

public class IDGenerator {

	private static IDGenerator INSTANCE = null;
	
	private HashMap< String, Integer > mIDs;
	private ArrayList< String > mAllIDs;
	
	public IDGenerator(){

		mIDs = new HashMap< String, Integer >();
		mAllIDs = new ArrayList< String >();
		
	}
	
	public static IDGenerator getInstance(){
		
		if( INSTANCE == null )
			INSTANCE = new IDGenerator();
		
		return INSTANCE;
		
	}
	
	public String reserveID( String id ){
		
		mAllIDs.add( id );
		return id;
		
	}
	
	public String generateID( Object xObject ){
		
		String id = "@" + xObject.getClass().getSimpleName() + "_" + getCount( xObject );
		
		while( mAllIDs.contains( id ) )
			id = "@" + xObject.getClass().getSimpleName() + "_" + getCount( xObject );
		
		return id;
		
	}

	private int getCount( Object xObject ){
		
		String name = xObject.getClass().getCanonicalName();
		
		if( !mIDs.containsKey( name ) ){
			
			
			mIDs.put( name, 0 );
			return 0;
			
		}
		else{

			return mIDs.put( name, mIDs.get( name ) + 1 ) + 1;
			
		}
		
	}
	
}
