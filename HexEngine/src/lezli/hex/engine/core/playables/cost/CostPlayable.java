package lezli.hex.engine.core.playables.cost;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.scriptable.PCostPlayableScriptable;
import lezli.hex.engine.core.playables.graphics.PGraphicalPlayable;
import lezli.hex.engine.core.structure.entities.cost.CostEntity;
import lezli.hex.engine.moddable.playables.HECost;

public abstract class CostPlayable< T extends CostEntity > extends PGraphicalPlayable< T > implements PCostPlayableScriptable, HECost{

	private PCost mCost;
	
	public CostPlayable( T xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mCost = new PCost( xEntity.getCost(), engine() );
		
	}
	
	public PCost getCost(){
		
		return mCost;
		
	}
	
}
