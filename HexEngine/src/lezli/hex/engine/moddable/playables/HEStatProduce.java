package lezli.hex.engine.moddable.playables;

import java.util.HashMap;

import lezli.hex.engine.core.structure.entities.stat.StatReg;

public interface HEStatProduce extends HEProduce{

	public HashMap< String, StatReg > getStatRegs();
	
	public String getUnit();
	
}
