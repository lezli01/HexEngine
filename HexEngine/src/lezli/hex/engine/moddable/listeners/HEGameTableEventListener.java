package lezli.hex.engine.moddable.listeners;

import java.util.ArrayList;

import lezli.hex.engine.core.gametable.PGameTable;
import lezli.hex.engine.core.gametable.event.GameEvent;
import lezli.hex.engine.core.gametable.player.Player;
import lezli.hex.engine.core.gametable.player.RemotePlayer;
import lezli.hex.engine.core.playables.building.PBuilding;
import lezli.hex.engine.core.playables.cost.PCost;
import lezli.hex.engine.core.playables.map.tile.PTile;
import lezli.hex.engine.core.playables.unit.PUnit;
import lezli.hex.engine.core.playables.unit.skills.PSkill;


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

	public boolean lose( Player player ){ return false; }
	
	public boolean clearedHighlights(){ return false; }
	
}
