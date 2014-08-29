package lezli.hex.engine.moddable.gametable;

import lezli.hex.engine.moddable.interfaces.HETile;
import lezli.hex.engine.moddable.playables.HEBuildingReg;
import lezli.hex.engine.moddable.playables.HEProduce;
import lezli.hex.engine.moddable.playables.HESkill;

public interface HEGameTableFeatures {

	public void clearHighlights();
	public void chooseSkill( HESkill xSkill );
	public void chooseBuilding( HEBuildingReg xBuilding );
	public void produce( HEProduce xProduce );
	public void castSkill();
	
	public void select( HETile xTile );

}
