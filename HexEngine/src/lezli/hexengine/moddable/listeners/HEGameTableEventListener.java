package lezli.hexengine.moddable.listeners;

import java.util.ArrayList;

import lezli.hexengine.core.gametable.PGameTable;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.gametable.player.RemotePlayer;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.cost.PCost;
import lezli.hexengine.core.playables.map.tile.PTile;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.playables.unit.skills.PSkill;


public class HEGameTableEventListener {

	public void assigned( PGameTable xGameTable ){}
	
	public boolean ready(){ return false; }
	public boolean busy(){ return false; }
	
	public boolean tileClicked( PTile xTile, PGameTable xMap ){ return false; }
	
	public boolean unitSelected( PUnit xUnit ){ return false; }
	public boolean unitHovered( PUnit xUnit ){ return false; }
	
	public boolean skillCasted( PSkill xSkill ){ return false; }
	public boolean skillAreaSelected( PSkill xSkill, ArrayList< PTile > xTiles ){ return false; };
	
	public boolean built( PBuilding xBuilding ){ return false; }
	public boolean buildingSelected( PBuilding xBuilding ){ return false; }
	public boolean buildingHovered( PBuilding xBuilding ){ return false; }
	
	public boolean payed( PCost xCost ){ return false; }
	
	
	public boolean turned( PGameTable xMap ){ return false; }
	public boolean remotePlayerTurn( RemotePlayer remotePlayer, ArrayList< GameEvent > xEvents ){ return false; }
	public boolean localPlayerTurn( Player remotePlayer ){ return false; }
	public boolean playerRemoved( Player xPlayer ){ return false; }

	public boolean canceled(){ return false; }
	
}
