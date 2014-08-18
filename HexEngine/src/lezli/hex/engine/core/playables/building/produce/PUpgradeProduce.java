package lezli.hex.engine.core.playables.building.produce;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.building.ProduceEntity;

public abstract class PUpgradeProduce< T extends ProduceEntity, P extends Playable< ? > > extends PProducePlayable< T, P >{

	public PUpgradeProduce( T xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
	}

}
