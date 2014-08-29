package siegedevils.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class Skirmish{

	private static Skirmish instance = null;
	
	private HashMap< String, String > mGameTables;
	
	public static Skirmish getInstance(){
		
		if( instance == null )
			instance = new Skirmish();
		
		return instance;
		
	}
	
	private Skirmish(){

		String data = "siegedevils/skirmish/skirmish.list";

		if( Gdx.app.getType() == ApplicationType.Desktop )
			data = "./bin/" + data;
		
		Properties props = new Properties();
		
		try {

			props.load( Gdx.files.internal( data ).read() );
		
			mGameTables = new HashMap< String, String >();

			for( Object key: props.keySet() ){

				mGameTables.put( ( String ) key, props.getProperty( ( String ) key ) );
				
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}

	}

	public HashMap< String, String > getAll(){
		
		return mGameTables;
		
	}
	
}
