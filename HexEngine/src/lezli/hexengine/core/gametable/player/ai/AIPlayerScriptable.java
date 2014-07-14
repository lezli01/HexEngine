package lezli.hexengine.core.gametable.player.ai;

import lezli.hexengine.core.gametable.script.PBuildingScriptable;
import lezli.hexengine.core.gametable.script.PLivingPlayableScriptable;
import lezli.hexengine.core.gametable.script.PProducePlayableScriptable;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;

public interface AIPlayerScriptable {

	public abstract void endTurn();
	
	public abstract void move( PUnitScriptable xUnit, int xX, int xY );
	public abstract void skill( PLivingPlayableScriptable xUnitFrom, int xX, int xY, PSkillScriptable xSkill );
	public abstract void build( int xX, int xY, PUnitScriptable xUnit, PBuildingScriptable xBuilding );
	public abstract void produce( PProducePlayableScriptable< ?, ? > xUnit, PBuildingScriptable xBuilding );
	
}
