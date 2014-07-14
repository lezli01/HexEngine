package lezli.hexengine.core.playables.building.produce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.XmlWriter;

import lezli.hexengine.core.gametable.script.PProducePlayableScriptable;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.playables.cost.CostPlayable;
import lezli.hexengine.core.structure.entities.building.ProduceEntity;
import lezli.hexengine.core.structure.entities.gametable.Holding;

public abstract class PProducePlayable< T extends ProduceEntity, P extends Playable< ? > > extends CostPlayable< ProduceEntity > implements PProducePlayableScriptable< T, P >{

	private static HashMap< String, Playable< ? > > mPrototypes = new HashMap< String, Playable< ? > >();
	private P mPrototype;
	
	private String mUnit;
	private int mDuration;
	private int mCurrentDuration;
	
	private String mBuilding;
	
	private ArrayList< PProduceListener > mListeners;
	
	@SuppressWarnings("unchecked")
	public PProducePlayable( T xEntity ){
		
		super( xEntity );
		
		mUnit = xEntity.getUnit();
		mDuration = xEntity.getDuration();
		mCurrentDuration = -1;
		mListeners = new ArrayList< PProduceListener >();

		if( !mPrototypes.containsKey( xEntity.getID() ) ){
			
			mPrototypes.put( xEntity.getID(), null );
			mPrototypes.put( xEntity.getID(), createPrototype( xEntity ) );
		
		}
		
		mPrototype = ( P ) mPrototypes.get( xEntity.getID() );
		
	}
	
	public int getCurrentDuration(){
		
		return mCurrentDuration;
		
	}

	public String getUnit(){
		
		return mUnit;
		
	}

	public int getDuration(){
		
		return mDuration;
		
	}

	public P getPrototype(){
		
		return mPrototype;
		
	}
	
	public String getBuilding(){
		
		return mBuilding;
		
	}

	public void addListener( PProduceListener xListener ){
		
		mListeners.add( xListener );
		
	}
	
	public boolean isProducing(){
		
		return mCurrentDuration > 0;
		
	}
	
	public void produce( String xBuilding ){
		
		mBuilding = xBuilding;
		
		if( !isProducing() ){
			
			mCurrentDuration = mDuration;
		
			if( mCurrentDuration == 0 )
				ready();
		
		}
		
	}
	
	@Override
	public void load( Holding holding ){
	
		super.load( holding );
	
		if( hasSaved( holding, "building" ) )
			mBuilding = holding.getValues().get( "building" );
		
		if( hasSaved( holding, "duration" ) )
			mCurrentDuration = Integer.parseInt( holding.getValues().get( "duration" ) );
		
	}

	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
	
		super.save( xmlWriter );
	
		xmlWriter.attribute( "building", getBuilding() );
	
		if( isProducing() ){
			
			xmlWriter.attribute( "duration", getCurrentDuration() );
			
		}
		
		
	}

	protected abstract P createPrototype( T xProduceEntity );

	@Override
	protected void animationEnded( String xID ){
		
	}

	@Override
	public void turn(){
		
		if( isProducing() )
			mCurrentDuration--;
		
		if( mCurrentDuration == 0 ){
			
			ready();
			
		}
		
	}
	
	private void ready(){
		
		mCurrentDuration = -1;
		
		for( PProduceListener listener: mListeners )
			listener.produced( this );
		
	}

	@Override
	public boolean producing(){
	
		return isProducing();
	
	}
	
}
