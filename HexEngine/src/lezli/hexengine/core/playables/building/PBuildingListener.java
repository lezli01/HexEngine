package lezli.hexengine.core.playables.building;

import lezli.hexengine.core.playables.building.produce.PUnitProduce;
import lezli.hexengine.core.playables.building.produce.PUpgradeProduce;

public interface PBuildingListener {

	public void unitProduced( PBuilding xBuilding, PUnitProduce xProduce );
	public void upgradeProduced( PBuilding xBuilding, PUpgradeProduce< ?, ? > xProduce );
	
}
