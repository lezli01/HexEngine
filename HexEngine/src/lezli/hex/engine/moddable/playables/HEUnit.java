package lezli.hex.engine.moddable.playables;

import java.util.HashMap;

public interface HEUnit extends HELivingPlayable{

	public HashMap< String, HEBuildingReg > buildingRegs();
	
	public HashMap< String, HESkill > allSkills();
	
}
