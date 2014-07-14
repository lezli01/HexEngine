package lezli.hexengine.core.gametable.script;

import java.util.ArrayList;

public interface PUnitScriptable extends PLivingPlayableScriptable{

	public ArrayList< PSkillScriptable > skills();
	public ArrayList< PBuildingScriptable > buildings();
	
}
