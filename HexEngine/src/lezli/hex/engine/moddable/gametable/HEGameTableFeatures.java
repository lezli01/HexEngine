package lezli.hex.engine.moddable.gametable;

import lezli.hex.engine.core.playables.building.PBuildingReg;
import lezli.hex.engine.core.playables.building.produce.PProducePlayable;
import lezli.hex.engine.core.playables.unit.skills.PSkill;
import lezli.hex.engine.moddable.interfaces.HETile;

public interface HEGameTableFeatures {

	public void clearHighlights();
	public void chooseSkill( PSkill xSkill );
	public void chooseBuilding( PBuildingReg xBuilding );
	public void produce( PProducePlayable< ?, ? > xProduce );
	public void castSkill();
	
	public void select( HETile xTile );

}
