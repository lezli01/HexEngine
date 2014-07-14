package siegedevils.multiplayer;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import lezli.hexengine.core.gametable.event.GameEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class Bartender implements HttpResponseListener{

	private String mPlayerName, mGameID;
	private BartenderListener mListener;
	
	public static abstract class BartenderListener{
		
		public abstract void events( ArrayList< GameEvent > xEvents );
		public abstract void noevents();
		public abstract void unknown();
		
	}
	
	public Bartender( String xPlayerName, String xGameID, BartenderListener xListener ){
		
		mGameID = xGameID;
		mListener = xListener;
		mPlayerName = xPlayerName;
		
	}
	
	public void tell( String xNextPlayerName, ArrayList< GameEvent > xEvents ){
		
		if( xEvents.size() == 0 )
			return;
		
		System.setProperty( "http.proxyHost", "proxy.vekoll.vein.hu" );
		System.setProperty( "http.proxyPort", "3128" );
		
		HttpRequest request = new HttpRequest( Net.HttpMethods.POST );
		request.setUrl( "http://siegedevils-server.appspot.com/bartender" );
		request.setHeader( "action", "tell" );
		request.setHeader( "game-id", mGameID );
		request.setHeader( "player-name", xNextPlayerName );

		String json = GameEvenJSON.toJson( xEvents );
		request.setContent( new ByteArrayInputStream( json.getBytes() ), json.getBytes().length );
		
		Gdx.net.sendHttpRequest( request, Bartender.this );
		
	}
	
	public void ask(){

		System.setProperty( "http.proxyHost", "proxy.vekoll.vein.hu" );
		System.setProperty( "http.proxyPort", "3128" );
		
		HttpRequest request = new HttpRequest( Net.HttpMethods.GET );
		request.setUrl( "http://siegedevils-server.appspot.com/bartender" );
		request.setHeader( "game-id", mGameID );
		request.setHeader( "action", "ask" );
		request.setHeader( "player-name", mPlayerName );
		Gdx.net.sendHttpRequest( request, Bartender.this );
		
	}

	@Override
	public void handleHttpResponse( HttpResponse httpResponse ){

		if( httpResponse.getHeader( "action" ) == null )
			return;
		
		if( httpResponse.getHeader( "action" ).equals( "unknown" ) ){
			mListener.unknown();
			return;
		}
		
		if( httpResponse.getHeader( "action" ).equals( "no-events" ) ){
			mListener.noevents();
			return;
		}
		
		if( httpResponse.getHeader( "action" ).equals( "received" ) ){
			System.out.println( "received" );
			return;
		}
		
		if( httpResponse.getHeader( "action" ).equals( "events" ) ){
		
			
			mListener.events( GameEvenJSON.fromJson( httpResponse.getResultAsString() ) );
		
		}
		
	}

	@Override
	public void failed( Throwable t ){

		t.printStackTrace();
		
	}

	@Override
	public void cancelled(){
		
		System.out.println( "cancel" );
		
	}
	
}
