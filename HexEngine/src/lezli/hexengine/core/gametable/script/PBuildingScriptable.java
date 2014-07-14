package lezli.hexengine.core.gametable.script;

import java.util.ArrayList;

public interface PBuildingScriptable extends PLivingPlayableScriptable{

	public boolean constructing();
	
	public ArrayList< PProducePlayableScriptable< ?, ? > > produces();
	public ArrayList< PSkillScriptable > skills();
	public ArrayList< PStatEntriesScriptable > stats();
	public ArrayList< PUnitScriptable > units();
	
}
