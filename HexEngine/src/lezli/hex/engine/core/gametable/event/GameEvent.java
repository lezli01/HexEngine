package lezli.hex.engine.core.gametable.event;

import java.util.ArrayList;

import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;

import com.badlogic.gdx.utils.Json;


public class GameEvent{

	public static final int UNIT_MOVED		=	1 << 0;
	public static final int SKILL_CASTED	=	1 << 1;
	public static final int PRODUCED		=   1 << 2;
	public static final int BUILDING_ADDED	=	1 << 3;
	public static final int TURNED			= 	1 << 4;
	
	public int type;
	public String playerName;
	public String unitID;
	public String buildingID;
	public String produceID;
	public String skillID;
	public ArrayList< Integer > eventX;
	public ArrayList< Integer > eventY;;
	public String stat;
	public int value;
	
	public GameEvent addCoords( GraphicalPlayable< ? > xPlayable ){

		addCoords( xPlayable.getTileX(), xPlayable.getTileY() );
		
		return this;
		
	}
	
	public GameEvent addCoords( int xX, int xY ){
		
		addX( xX );
		addY( xY );
		
		return this;
		
	}
	
	public GameEvent addX( int xX ){
		
		if( eventX == null )
			eventX = new ArrayList< Integer >();
		
		eventX.add( xX );
		
		return this;
		
	}

	public GameEvent addY( int xY ){
		
		if( eventY == null )
			eventY = new ArrayList< Integer >();
		
		eventY.add( xY );
		
		return this;
		
	}
	
	public String toPrettyString(){
		
		return new Json().prettyPrint( this );
		
	}
	
	@Override
	public String toString() {

		return new Json().toJson( this );
	
	}
	
}
