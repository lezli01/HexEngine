package lezli.hexengine.core.gametable.scriptable;

import java.util.ArrayList;

import lezli.hexengine.core.gametable.script.PSkillScriptable;

public interface PUnitScriptable extends PLivingPlayableScriptable{

	public ArrayList< PSkillScriptable > skills();
	public ArrayList< PBuildingScriptable > buildings();
	
}
