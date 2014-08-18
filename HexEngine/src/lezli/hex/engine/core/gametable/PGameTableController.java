package lezli.hex.engine.core.gametable;

import lezli.hex.engine.core.playables.building.PBuildingReg;
import lezli.hex.engine.core.playables.building.produce.PProducePlayable;
import lezli.hex.engine.core.playables.unit.skills.PSkill;
import lezli.hex.engine.moddable.interfaces.HEGameTableController;

public class PGameTableController implements HEGameTableController{

	public void chooseSkill( PSkill xSkill ){};
	public void chooseBuilding( PBuildingReg xBuilding ){};
	public void produce( PProducePlayable< ?, ? > xProduce ){};
	public void castSkill(){};
	public void clearHighlights(){}
	
}
