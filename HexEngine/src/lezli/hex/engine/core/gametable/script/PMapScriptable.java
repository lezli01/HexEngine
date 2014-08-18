package lezli.hex.engine.core.gametable.script;

import lezli.hex.engine.core.gametable.scriptable.PGraphicalPlayableScriptable;
import lezli.hex.engine.core.gametable.scriptable.PUnitScriptable;

public interface PMapScriptable extends PGraphicalPlayableScriptable{

	public boolean isTherePath( PUnitScriptable xUnit, int xX, int xY );
	
}
