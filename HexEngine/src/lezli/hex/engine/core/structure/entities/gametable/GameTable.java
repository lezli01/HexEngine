package lezli.hex.engine.core.structure.entities.gametable;

import java.util.ArrayList;

import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;

import com.badlogic.gdx.utils.XmlReader.Element;

public class GameTable extends GraphicalEntity{

	private String mMap;
	private String mCurrentPlayer;
	
	private ArrayList< Holding > mHoldings;
	
	public GameTable( String xFilePath ){
		
		super( xFilePath );
		
	}

	public String getMap(){
		
		return mMap;
		
	}
	
	public ArrayList< Holding > getHoldings(){
		
		return mHoldings;
		
	}
	
	public String getCurrentPlayer(){
		
		return mCurrentPlayer;
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		setMap( xElement.getAttribute( "map" ) );
		setCurrentPlayer( xElement.getAttribute( "player" ) );

		for( int i = 0; i < xElement.getChildCount(); i++ )
			mHoldings.add( new Holding( xElement.getChild( i ) ) );
		
	}
	
	private void setMap( String xMap ){
		
		mMap = xMap;
		
		log( "Map set (" + mMap + ")" );
		
	}

	private void setCurrentPlayer( String xPlayer ){
		
		mCurrentPlayer = xPlayer;
		
		log( "Player set (" + mCurrentPlayer + ")" );
		
	}
	
	@Override
	protected void init(){
		
		mHoldings = new ArrayList< Holding >();
		
	}

}
