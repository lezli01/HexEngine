package siegedevils.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;

public class Bartender extends HttpServlet{

	private static final long serialVersionUID = -9123302667572989544L;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

		resp.setHeader( "action", "unknown" );
		
		if( req.getHeader( "action" ).equals( "ask" ) ){
			
			ask( req, resp );
			
		}
		
	}
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )	throws ServletException, IOException {
	
		resp.setHeader( "action", "unknown" );
		
		if( req.getHeader( "action" ).equals( "tell" ) ){
			
			tell( req, resp );
			
		}
		
	}

	private void tell( HttpServletRequest req, HttpServletResponse resp ){
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity eventReg = new Entity( "event_reg" );
		
		String events = "";
		try {
			BufferedReader r = new BufferedReader( new InputStreamReader( req.getInputStream() ) );
			String line;
			while( ( line = r.readLine() ) != null ){
				events += line;
			}
			
			log( r.readLine() );
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		eventReg.setProperty( "events", new Text( events ) );
		eventReg.setProperty( "game-id", req.getHeader( "game-id" ) );	
		eventReg.setProperty( "player", req.getHeader( "player-name" ) );
		eventReg.setProperty( "checked", false );
		
		datastore.put( eventReg );
		
		resp.setHeader( "action", "received" );
		
	}
	
	private void ask( HttpServletRequest req, HttpServletResponse resp ){
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		FilterPredicate filterGameID = new FilterPredicate( "game-id", FilterOperator.EQUAL, req.getHeader( "game-id" ) );
		FilterPredicate filterPlayerName = new FilterPredicate( "player", FilterOperator.EQUAL, req.getHeader( "player-name" ) );
		FilterPredicate filterChecked = new FilterPredicate( "checked", FilterOperator.EQUAL, false );
		Filter filter = CompositeFilterOperator.and( filterGameID, filterPlayerName, filterChecked );

		Query query = new Query( "event_reg" );
		query.setFilter( filter );
		PreparedQuery preparedQuery = datastore.prepare( query );

		Iterator< Entity > iterator = preparedQuery.asIterator();
		
		if( iterator.hasNext() ){
			//Return events
			
			Entity entity = iterator.next();
			
			resp.setHeader( "action", "events" );
			
			try {
			
				resp.getOutputStream().write( ( ( Text ) entity.getProperty( "events" ) ).getValue().getBytes() );
			
			} catch (IOException e) {

				e.printStackTrace();
			
			}
			
			entity.setProperty( "checked", true );
			datastore.put( entity );
			
			return;
			
		}
		
		//Return no events
		resp.setHeader( "action", "no-events" );
		
	}
	
}
