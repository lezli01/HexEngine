package lezli.hexengine.core.gametable.scriptable;

import java.util.ArrayList;

import lezli.hexengine.core.gametable.script.PMapScriptable;


public interface PGameTableScriptable extends PGraphicalPlayableScriptable{

	public PMapScriptable map();
	public int distance( PGraphicalPlayableScriptable x, PGraphicalPlayableScriptable y );
	
	public ArrayList< PUnitScriptable > units();
	public ArrayList< PBuildingScriptable > buildings();
	
	public ArrayList< PUnitScriptable > enemyUnits();
	public ArrayList< PBuildingScriptable > enemyBuildings();
	public ArrayList< PLivingPlayableScriptable > enemies();
	
}
