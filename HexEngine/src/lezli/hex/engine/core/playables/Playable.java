package lezli.hex.engine.core.playables;

import java.io.IOException;
import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.event.GameEvent;
import lezli.hex.engine.core.gametable.event.GameEventListener;
import lezli.hex.engine.core.gametable.scriptable.PlayableScriptable;
import lezli.hex.engine.core.structure.entities.Entity;
import lezli.hex.engine.core.structure.entities.gametable.Holding;
import lezli.hex.engine.core.structure.entities.id.IDGenerator;

import com.badlogic.gdx.utils.XmlWriter;

public abstract class Playable< T extends Entity > implements PlayableScriptable{

	private static final boolean DEBUG_MODE = true;
	
	private String mEntityID;
	private String mName;
	private String mDescription;

	private HexEngine mEngine;
	
	private ArrayList< GameEventListener > mListeners;
	private boolean mDoEvents;
	
	private String mPID = IDGenerator.getInstance().generateID( this );

	public abstract void update();

	public abstract void turn();

	public Playable( T xEntity, HexEngine xEngine ){

		mEngine = xEngine;
		
		mListeners = new ArrayList< GameEventListener >();
		
		if( xEntity.getID() != null )
			mEntityID = new String( xEntity.getID() );
		
		if( xEntity.getName() != null )
			mName = new String( xEntity.getName() );
		
		if( xEntity.getDescription() != null && engine() != null )
			mDescription = engine().entitiesHolder().getTextsManager().findText( xEntity.getDescription() );
			
		mDoEvents = true;
		
	}
	
	protected HexEngine engine(){
		
		return mEngine;
		
	}

	public void addListener( GameEventListener xListener ){
		
		mListeners.add( xListener );
		
	}
	
	public void addListeners( ArrayList< GameEventListener > xListeners ){
		
		for( GameEventListener listener: xListeners )
			addListener( listener );
		
	}
	
	public ArrayList< GameEventListener > getListeners(){
		
		return mListeners;
		
	}
	
	public String getEntityID(){
		
		return mEntityID;
		
	}
	
	public void overwriteEntityID( String xID ){
		
		mEntityID = xID;
		
	}
	
	public String getName(){
		
		return mName;
		
	}
	
	public String getDescription(){
		
		return mDescription;
		
	}
	
	@Override
	public String toString() {

		return mPID;
	
	}

	public String getPID(){
		
		return mPID;
		
	}
	public void save( XmlWriter xmlWriter ) throws IOException{
		
		xmlWriter.element( getClass().getSimpleName() ).
				  attribute( "id", getEntityID() ).
		  		  attribute( "pid", getPID() );
		
	}
	public void savep( XmlWriter xmlWriter ) throws IOException{
		
		save( xmlWriter );
		xmlWriter.pop();
		
	}
	public void load( Holding holding ){
		
		if( hasSaved( holding, "pid" ) )
			mPID = IDGenerator.getInstance().reserveID( holding.getValues().get( "pid" ) );
		
	}
	@Override
	public boolean equals( Object xObj ){
	
		if( xObj instanceof Playable )
			return ( ( Playable<?> ) xObj ).getPID().equals( getPID() );
			
		return super.equals( xObj );
	
	}
	protected void eventsOff(){
		
		mDoEvents = false;
		
	}
	protected void eventsOn(){
		
		mDoEvents = true;
		
	}
	protected void event( GameEvent xEvent ){
		
		if( !mDoEvents )
			return;
		
		for( GameEventListener listener: mListeners )
			listener.newEvent( this, xEvent );
		
		
	}
	protected void log( String xMessage ){
		
//		HexEngine.LOGGER.log( getPID() + ": " + xMessage );
		
	}
	
	protected void log( String xMessage, int xDepth ){
		
		engine().logger().log( getPID() + ": " + xMessage, xDepth );
		
	}
	
	protected boolean hasSaved( Holding holding, String attribute ){
		
		return holding.getValues().get( attribute ) != null;
		
	};
	
	protected void debugMessage( String msg ){
		
		if( !DEBUG_MODE )
			return;
		
		log( msg );
		
	}
	
}
