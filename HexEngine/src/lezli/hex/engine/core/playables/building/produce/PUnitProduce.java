package lezli.hex.engine.core.playables.building.produce;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.gametable.script.PSkillScriptable;
import lezli.hex.engine.core.gametable.scriptable.PBuildingScriptable;
import lezli.hex.engine.core.gametable.scriptable.PUnitScriptable;
import lezli.hex.engine.core.playables.unit.PUnit;
import lezli.hex.engine.core.structure.entities.building.UnitProduce;
import lezli.hex.engine.moddable.playables.HEUnitProduce;

public class PUnitProduce extends PProducePlayable< UnitProduce, PUnit > implements PUnitScriptable, HEUnitProduce{

	public PUnitProduce( UnitProduce xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		setGraphicsID( engine().entitiesHolder().getUnitManager().get( getUnit() ).getGraphics() );
		
	}
	
	@Override
	public String getName() {

		return engine().entitiesHolder().getUnitManager().get( getUnit() ).getName();
		
	}
	
	@Override
	public String getDescription(){

		return engine().entitiesHolder().getTextsManager().findText( engine().entitiesHolder().getUnitManager().get( getUnit() ).getDescription() );
		
	}
	
	@Override
	protected PUnit createPrototype( UnitProduce xProduceEntity ){

		return new PUnit( this, engine() );
		
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
