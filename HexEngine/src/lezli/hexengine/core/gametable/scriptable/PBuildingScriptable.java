package lezli.hexengine.core.gametable.scriptable;

import java.util.ArrayList;

import lezli.hexengine.core.gametable.script.PSkillScriptable;

public interface PBuildingScriptable extends PLivingPlayableScriptable{

	public boolean constructing();
	
	public ArrayList< PProducePlayableScriptable< ?, ? > > produces();
	public ArrayList< PSkillScriptable > skills();
	public ArrayList< PStatEntriesScriptable > stats();
	public ArrayList< PUnitScriptable > units();
	
}
