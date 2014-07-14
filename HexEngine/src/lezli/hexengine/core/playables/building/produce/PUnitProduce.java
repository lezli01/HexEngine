package lezli.hexengine.core.playables.building.produce;

import java.util.ArrayList;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PBuildingScriptable;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.gametable.script.PUnitScriptable;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.structure.entities.building.UnitProduce;

public class PUnitProduce extends PProducePlayable< UnitProduce, PUnit > implements PUnitScriptable{

	public PUnitProduce( UnitProduce xEntity ){
		
		super( xEntity );
		
		setGraphicsID( HexEngine.EntitiesHolder.getUnitManager().get( getUnit() ).getGraphics() );
		
	}
	
	@Override
	public String getName() {

		return HexEngine.EntitiesHolder.getUnitManager().get( getUnit() ).getName();
		
	}
	
	@Override
	public String getDescription(){

		return HexEngine.EntitiesHolder.getTextsManager().findText( HexEngine.EntitiesHolder.getUnitManager().get( getUnit() ).getDescription() );
		
	}
	
	@Override
	protected PUnit createPrototype( UnitProduce xProduceEntity ){

		return new PUnit( this );
		
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
	public int speed(){
		
		return getPrototype().speed();
		
	}

	@Override
	public int maxSpeed(){
	
		return getPrototype().maxSpeed();
	
	}

	@Override
	public ArrayList< PSkillScriptable > skills(){
	
		return getPrototype().skills();
	
	}

	@Override
	public ArrayList< PBuildingScriptable > buildings() {
	
		return getPrototype().buildings();
	
	}

}
