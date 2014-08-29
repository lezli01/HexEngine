package lezli.hex.engine.moddable.playables;

import java.util.ArrayList;

import lezli.hex.engine.core.structure.entities.skill.affect.Affect;

public interface HESkill extends HEPlayable{

	public ArrayList< Affect > getAffects();
	
	public int getCooldownStatus();
	
	public HEUnit getHolder();
	
	public boolean isCooldown();
	
	public int getRange();
	
}
