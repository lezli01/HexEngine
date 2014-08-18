package lezli.hex.engine.moddable.interfaces;

import lezli.hex.engine.core.playables.building.PBuildingReg;
import lezli.hex.engine.core.playables.building.produce.PProducePlayable;
import lezli.hex.engine.core.playables.unit.skills.PSkill;

public interface HEGameTableController {

	public void clearHighlights();
	public void chooseSkill( PSkill xSkill );
	public void chooseBuilding( PBuildingReg xBuilding );
	public void produce( PProducePlayable< ?, ? > xProduce );
	public void castSkill();

}
