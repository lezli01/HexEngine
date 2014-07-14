package lezli.hexengine.core.playables.cost;

import java.util.HashMap;

import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.structure.entities.cost.Cost;
import lezli.hexengine.core.structure.entities.cost.ResourceCost;

public class PCost extends Playable< Cost >{

	private HashMap< String, Integer > mResourceCosts;
	
	public PCost( Cost xEntity ){
		
		super( xEntity );
		
		mResourceCosts = new HashMap< String, Integer >();
		
		for( ResourceCost rCost: xEntity.getAll() )
			mResourceCosts.put( rCost.getResource(), rCost.getValue() );
		
	}
	
	public HashMap< String, Integer > getResourceCosts(){
		
		return mResourceCosts;
		
	}

	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
	}

}
