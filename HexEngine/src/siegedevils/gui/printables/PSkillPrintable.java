package siegedevils.gui.printables;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.playables.unit.skills.PAffect;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.core.structure.entities.skill.affect.Affect;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PSkillPrintable extends GraphicalPlayablePrintable< PSkill >{

	public PSkillPrintable( PSkill xPlayable ){
		
		super( xPlayable );
		
	}

	@Override
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ){
		
		Table skillTable = super.getListElementTable( xSkin, xEngine );
	
		if( getPlayable().isCooldown() ){
			
			skillTable.row();
			
			Label cooldownLabel = new Label( "Cooldown: " + getPlayable().getCooldownStatus() + " turn", xSkin );
			cooldownLabel.setAlignment( Align.center, Align.center );
			skillTable.add( cooldownLabel ).padTop( 5 ).expandX().fillX().colspan( 2 );
			skillTable.setColor( 1.0f, 0.7f, 0.7f, 1.0f );
			
		}
		
		return skillTable;
		
	}
	
	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
	
		for( Affect eAffect: getPlayable().getAffects() ){
			
			PAffect affect = new PAffect( eAffect, xEngine );
			affect.apply( getPlayable(), getPlayable().getHolder(), null );
			
			xTable.left().add( new PAffectPrintable( affect ).getListElementTable( xSkin, xEngine ) ).padBottom( 5 ).width( 280 );
			xTable.row();
			
		}
		
	}
	
	public void fillTable( Table xTable, Skin xSkin, PUnit xUnitTo, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
	
//		for( Affect eAffect: getPlayable().getAffects() ){
//			
//			PAffect affect = new PAffect( eAffect );
//			affect.init( getPlayable(), getPlayable().getHolder(), xUnitTo );
//			
//			xTable.add( new PAffectPrintable( affect ).getListElementTable( xSkin ) ).padBottom( 5 ).width( 170 );
//			xTable.row();
//			
//		}
		
	}
	
}
