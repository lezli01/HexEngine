package lezli.hexengine.core.playables.building.produce;

import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.structure.entities.building.ProduceEntity;

public abstract class PUpgradeProduce< T extends ProduceEntity, P extends Playable< ? > > extends PProducePlayable< T, P >{

	public PUpgradeProduce( T xEntity ){
		
		super( xEntity );
		
	}

}
