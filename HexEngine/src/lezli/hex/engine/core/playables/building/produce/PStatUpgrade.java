package lezli.hex.engine.core.playables.building.produce;

import java.util.HashMap;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.scriptable.PStatEntriesScriptable;
import lezli.hex.engine.core.playables.unit.stats.PStatEntries;
import lezli.hex.engine.core.structure.entities.building.StatUpgrade;
import lezli.hex.engine.core.structure.entities.stat.StatReg;
import lezli.hex.engine.moddable.playables.HEStatProduce;

public class PStatUpgrade extends PUpgradeProduce< StatUpgrade, PStatEntries > implements PStatEntriesScriptable, HEStatProduce{

	private HashMap< String, StatReg > mStatRegs;
	
	public PStatUpgrade( StatUpgrade xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );

		mStatRegs = new HashMap< String, StatReg >();
		for( StatReg statReg: xEntity.getStatEntries().getAll() )
			mStatRegs.put( statReg.getStat(), statReg );
		
	}
	
	public HashMap< String, StatReg > getStatRegs(){
		
		return mStatRegs;
		
	}

	@Override
	protected PStatEntries createPrototype( StatUpgrade xProduceEntity ){

		return new PStatEntries( xProduceEntity.getStatEntries(), engine() );
		
	}
	
}
