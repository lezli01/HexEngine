package lezli.hex.engine.core.playables.cost;

import java.util.HashMap;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.cost.Cost;
import lezli.hex.engine.core.structure.entities.cost.ResourceCost;

public class PCost extends Playable< Cost >{

	private HashMap< String, Integer > mResourceCosts;
	
	public PCost( Cost xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
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
