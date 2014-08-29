package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.unit.skills.PAffect;
import lezli.hex.engine.core.structure.entities.skill.affect.Affect;
import lezli.hex.engine.moddable.playables.HEAffect;
import lezli.hex.engine.moddable.playables.HESkill;
import lezli.hex.engine.moddable.playables.HEUnit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class HESkillPrintable extends HEPlayablePrintable< HESkill >{

	public HESkillPrintable( HESkill xPlayable ){
		
		super( xPlayable );
		
	}

	@Override
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ){
		
		final Table table = new Table( xSkin );
		
		table.setBackground( "top-bg" );
		table.pad( 5 );
		table.row();
		
		Image i = new Image( getPlayable().getLargeIcon() );
		i.setScaling( Scaling.fill );

		Label l = new Label( getPlayable().getName(), xSkin, "fnt-medium", Color.WHITE );
		l.setWrap( true );
		table.add( l ).width( 200 ).expandX();
		table.add( i ).expandX().size( 75 );
		
		table.setTouchable( Touchable.enabled );

		table.addListener( new InputListener(){
			
			@Override
			public void enter( InputEvent event, float x, float y, int pointer, Actor fromActor ){

				table.setBackground( "scroll-bg" );
				table.pad( 5 );
				
			}
			
			@Override
			public void exit( InputEvent event, float x, float y, int pointer, Actor toActor ){
				
				table.setBackground( "top-bg" );
				table.pad( 5 );
				
			}
			
		});
		
		if( getPlayable().isCooldown() ){
			
			table.row();
			
			Label cooldownLabel = new Label( "Cooldown: " + getPlayable().getCooldownStatus() + " turn", xSkin );
			cooldownLabel.setAlignment( Align.center, Align.center );
			table.add( cooldownLabel ).padTop( 5 ).expandX().fillX().colspan( 2 );
			table.setColor( 1.0f, 0.7f, 0.7f, 1.0f );
			
		}
		
		return table;
		
	}
	
	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
	
		for( Affect eAffect: getPlayable().getAffects() ){
			
			HEAffect affect = new PAffect( eAffect, xEngine );
			affect.apply( getPlayable(), getPlayable().getHolder(), null );
			
			xTable.left().add( new HEAffectPrintable( affect ).getListElementTable( xSkin, xEngine ) ).padBottom( 5 ).width( 280 );
			xTable.row();
			
		}
		
	}
	
	public void fillTable( Table xTable, Skin xSkin, HEUnit xUnitTo, HexEngine xEngine ){

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
