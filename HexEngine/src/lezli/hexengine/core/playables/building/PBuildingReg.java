package lezli.hexengine.core.playables.building;

import java.util.ArrayList;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PBuildingScriptable;
import lezli.hexengine.core.gametable.script.PProducePlayableScriptable;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.gametable.script.PStatEntriesScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;
import lezli.hexengine.core.structure.entities.building.BuildingReg;
import lezli.hexengine.core.structure.entities.map.MapTile;

public class PBuildingReg extends PProducePlayable< BuildingReg, PBuilding > implements PBuildingScriptable{

	private String mBuilding;
	
	public PBuildingReg( BuildingReg xEntity ){
		
		super( xEntity );
		
		setGraphicsID( HexEngine.EntitiesHolder.getBuildingManager().get( xEntity.getBuilding() ).getGraphics() );
		
		mBuilding = xEntity.getBuilding();
		
	}

	public String getBuilding(){
		
		return mBuilding;
		
	}

	@Override
	public String getDescription() {

		return HexEngine.EntitiesHolder.getTextsManager().findText( HexEngine.EntitiesHolder.getBuildingManager().get( getBuilding() ).getDescription() );
		
	}
	
	@Override
	public String getName() {

		return HexEngine.EntitiesHolder.getBuildingManager().get( getBuilding() ).getName();
		
	}
	
	public PBuilding createBuilding( String xPlayer ){
		
		PBuilding building = new PBuilding( HexEngine.EntitiesHolder.getBuildingManager().get( getBuilding() ), xPlayer );
		
		return building;
		
	}
	
	public ArrayList< String > getAcceptedTilesID(){
		
		ArrayList< String > tileIDs = new ArrayList< String >();
		
		for( MapTile tile: HexEngine.EntitiesHolder.getBuildingManager().get( getBuilding() ).getAcceptedTiles().getAll() )
			tileIDs.add( tile.getTile() );
		
		return tileIDs;
		
	}
	
	@Override
	public void turn(){
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}

	@Override
	protected PBuilding createPrototype( BuildingReg xProduceEntity ){

		return new PBuilding( HexEngine.EntitiesHolder.getBuildingManager().get( xProduceEntity.getBuilding() ), "PROTOTYPE" );
	
	}

	@Override
	public int stat( String xStat ){
	
		return getPrototype().stat( xStat );
	
	}

	@Override
	public int hp(){
	
		return getPrototype().hp();
	
	}

	@Override
	public int maxHp(){
	
		return getPrototype().maxHp();
	
	}

	@Override
	public ArrayList< PSkillScriptable > skills(){

		return getPrototype().skills();
		
	}

	@Override
	public ArrayList<PStatEntriesScriptable> stats(){
	
		return getPrototype().stats();

	}

	@Override
	public ArrayList<PUnitScriptable> units(){

		return getPrototype().units();
	
	}
	
	@Override
	public ArrayList< PProducePlayableScriptable< ?, ? > > produces(){
	
		return getPrototype().produces();
		
	}

	@Override
	public boolean constructing(){

		return getPrototype().constructing();
		
	}
	
	@Override
	public int maxSpeed(){
		
		return getPrototype().maxSpeed();
		
	}
	
	@Override
	public int speed(){
		
		return getPrototype().speed();
		
	}
	
}
