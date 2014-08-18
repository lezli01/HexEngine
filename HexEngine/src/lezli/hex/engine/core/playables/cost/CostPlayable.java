package lezli.hex.engine.core.playables.cost;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.scriptable.PCostPlayableScriptable;
import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import lezli.hex.engine.core.structure.entities.cost.CostEntity;

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
