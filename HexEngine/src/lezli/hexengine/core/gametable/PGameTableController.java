package lezli.hexengine.core.gametable;

import lezli.hexengine.core.playables.building.PBuildingReg;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.moddable.interfaces.HEGameTableController;

public class PGameTableController implements HEGameTableController{

	public void chooseSkill( PSkill xSkill ){};
	public void chooseBuilding( PBuildingReg xBuilding ){};
	public void produce( PProducePlayable< ?, ? > xProduce ){};
	public void castSkill(){};
	public void clearHighlights(){}
	
}
