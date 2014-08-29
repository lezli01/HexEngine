package lezli.hex.engine.moddable.playables;

import java.util.ArrayList;
import java.util.HashMap;

public interface HELivingPlayable extends HEPlayable{

	public int getHitPoints();
	public int getMaxHitPoints();
	
	public boolean hasAffects();
	
	public ArrayList< HEAffect > affects();
	public HashMap< String, HEStatReg > statRegs();
	
}
