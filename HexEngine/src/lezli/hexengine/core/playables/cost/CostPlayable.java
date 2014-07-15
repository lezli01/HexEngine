package lezli.hexengine.core.playables.cost;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PCostPlayableScriptable;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.structure.entities.cost.CostEntity;

public abstract class CostPlayable< T extends CostEntity > extends GraphicalPlayable< T > implements PCostPlayableScriptable{

	private PCost mCost;
	
	public CostPlayable( T xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mCost = new PCost( xEntity.getCost(), engine() );
		
	}
	
	public PCost getCost(){
		
		return mCost;
		
	}
	
}
