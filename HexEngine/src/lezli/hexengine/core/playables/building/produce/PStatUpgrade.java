package lezli.hexengine.core.playables.building.produce;

import java.util.HashMap;

import lezli.hexengine.core.gametable.script.PStatEntriesScriptable;
import lezli.hexengine.core.playables.unit.stats.PStatEntries;
import lezli.hexengine.core.structure.entities.building.StatUpgrade;
import lezli.hexengine.core.structure.entities.stat.StatReg;

public class PStatUpgrade extends PUpgradeProduce< StatUpgrade, PStatEntries > implements PStatEntriesScriptable{

	private HashMap< String, StatReg > mStatRegs;
	
	public PStatUpgrade( StatUpgrade xEntity ){
		
		super( xEntity );

		mStatRegs = new HashMap< String, StatReg >();
		for( StatReg statReg: xEntity.getStatEntries().getAll() )
			mStatRegs.put( statReg.getStat(), statReg );
		
	}
	
	public HashMap< String, StatReg > getStatRegs(){
		
		return mStatRegs;
		
	}

	@Override
	protected PStatEntries createPrototype( StatUpgrade xProduceEntity ){

		return new PStatEntries( xProduceEntity.getStatEntries() );
		
	}
	
}
