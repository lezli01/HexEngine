package siegedevils.multiplayer;

import java.util.ArrayList;
import java.util.StringTokenizer;

import lezli.hex.engine.core.gametable.event.GameEvent;

import com.badlogic.gdx.utils.Json;

public class GameEvenJSON {

	public static String toJson( GameEvent xGameEvent ){

		Json json = new Json();
		
		return json.toJson( xGameEvent );
		
	}
	
	public static String toJson( ArrayList< GameEvent > xGameEvents ){

		String jsonString = new String();

		for( GameEvent event: xGameEvents )
			jsonString += toJson( event ) + "||";
		
		return jsonString;
		
	}
	
	public static ArrayList< GameEvent > fromJson( String jsonString ){
		
		StringTokenizer t = new StringTokenizer( jsonString, "||" );
		
		Json json = new Json();

		ArrayList< GameEvent > events = new ArrayList< GameEvent >();
		
		while( t.hasMoreTokens() ){
			
			events.add( json.fromJson( GameEvent.class, t.nextToken() ) );
			
		}
		
		return events;
		
	}
	
}
