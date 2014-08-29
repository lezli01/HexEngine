package lezli.hex.engine.moddable.playables;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public interface HEBuilding extends HELivingPlayable{

	public ConcurrentHashMap< String, HESkillProduce > skillUpgrades();
	public ConcurrentHashMap< String, HEStatProduce > statUpgrades();
	public HashMap< String, HEUnitProduce > unitProduces();
	
	public boolean isConstructed();
	
}
