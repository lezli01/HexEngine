package lezli.hexengine.core.gametable.script;

import lezli.hexengine.core.gametable.scriptable.PGraphicalPlayableScriptable;
import lezli.hexengine.core.gametable.scriptable.PUnitScriptable;

public interface PMapScriptable extends PGraphicalPlayableScriptable{

	public boolean isTherePath( PUnitScriptable xUnit, int xX, int xY );
	
}
