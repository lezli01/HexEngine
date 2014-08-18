package lezli.hex.engine.core.gametable.script;

import lezli.hex.engine.core.gametable.scriptable.PGraphicalPlayableScriptable;

public interface PSkillScriptable extends PGraphicalPlayableScriptable{

	public int cooldown();
	public int range();
	
}
