package lezli.hexengine.core.playables.building.produce;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.core.structure.entities.building.SkillUpgrade;

public class PSkillUpgrade extends PUpgradeProduce< SkillUpgrade, PSkill > implements PSkillScriptable{

	private String mSkill;
	
	public PSkillUpgrade( SkillUpgrade xEntity ){
		
		super( xEntity );
		
		mSkill = xEntity.getSkill();
		
		setGraphicsID( HexEngine.EntitiesHolder.getSkillManager().get( getSkill() ).getGraphics() );
		
	}
	
	@Override
	public String getDescription(){

		return HexEngine.EntitiesHolder.getTextsManager().findText( HexEngine.EntitiesHolder.getSkillManager().get( getSkill() ).getDescription() );
		
	}
	
	public String getSkill(){
		
		return mSkill;
		
	}

	@Override
	protected PSkill createPrototype( SkillUpgrade xProduceEntity ){
	
		return new PSkill( HexEngine.EntitiesHolder.getSkillManager().get( xProduceEntity.getSkill() ) );
	
	}

	@Override
	public int cooldown() {
	
		return getPrototype().cooldown();

	}

	@Override
	public int range(){

		return getPrototype().range();
		
	}
	
}