package lezli.hexengine.moddable.interfaces;

import lezli.hexengine.core.playables.building.PBuildingReg;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.playables.unit.skills.PSkill;

public interface HEGameTableController {

	public void clearHighlights();
	public void chooseSkill( PSkill xSkill );
	public void chooseBuilding( PBuildingReg xBuilding );
	public void produce( PProducePlayable< ?, ? > xProduce );
	public void castSkill();

}
