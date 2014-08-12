package lezli.hexengine.core.gametable.scriptable;

public interface PLivingPlayableScriptable extends PGraphicalPlayableScriptable{

	public int speed();
	public int maxSpeed();
	public int stat( String xStat );
	public int hp();
	public int maxHp();
	
}
