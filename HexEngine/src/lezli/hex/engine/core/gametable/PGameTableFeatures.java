package lezli.hex.engine.core.gametable;

import lezli.hex.engine.core.playables.graphics.PGraphicalPlayable;
import lezli.hex.engine.moddable.gametable.HEGameTableFeatures;
import lezli.hex.engine.moddable.interfaces.HETile;
import lezli.hex.engine.moddable.playables.HEBuildingReg;
import lezli.hex.engine.moddable.playables.HEProduce;
import lezli.hex.engine.moddable.playables.HESkill;

public class PGameTableFeatures implements HEGameTableFeatures{

	public void chooseSkill( HESkill xSkill ){}
	public void chooseBuilding( HEBuildingReg xBuilding ){}
	public void produce( HEProduce xProduce ){}
	public void castSkill(){}
	public void clearHighlights(){}
	public void select( HETile xTile ){}
	public void selectPlayable( PGraphicalPlayable<?> xPlayable ){}
	
}
