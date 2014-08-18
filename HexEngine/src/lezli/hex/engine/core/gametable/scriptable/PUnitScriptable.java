package lezli.hex.engine.core.gametable.scriptable;

import java.util.ArrayList;

import lezli.hex.engine.core.gametable.script.PSkillScriptable;

public interface PUnitScriptable extends PLivingPlayableScriptable{

	public ArrayList< PSkillScriptable > skills();
	public ArrayList< PBuildingScriptable > buildings();
	
}
