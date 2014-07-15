package lezli.hexengine.core.playables.building.produce;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.script.PSkillScriptable;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.core.structure.entities.building.SkillUpgrade;

public class PSkillUpgrade extends PUpgradeProduce< SkillUpgrade, PSkill > implements PSkillScriptable{

	private String mSkill;
	
	public PSkillUpgrade( SkillUpgrade xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mSkill = xEntity.getSkill();
		
		setGraphicsID( engine().entitiesHolder().getSkillManager().get( getSkill() ).getGraphics() );
		
	}
	
	@Override
	public String getDescription(){

		return engine().entitiesHolder().getTextsManager().findText( engine().entitiesHolder().getSkillManager().get( getSkill() ).getDescription() );
		
	}
	
	public String getSkill(){
		
		return mSkill;
		
	}

	@Override
	protected PSkill createPrototype( SkillUpgrade xProduceEntity ){
	
		return new PSkill( engine().entitiesHolder().getSkillManager().get( xProduceEntity.getSkill() ), engine() );
	
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