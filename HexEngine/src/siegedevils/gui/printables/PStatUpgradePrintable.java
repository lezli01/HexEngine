package siegedevils.gui.printables;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.building.produce.PStatUpgrade;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.structure.entities.stat.StatReg;

public class PStatUpgradePrintable extends GraphicalPlayablePrintable< PStatUpgrade >{

	public PStatUpgradePrintable( PStatUpgrade xPlayable ){
		
		super( xPlayable );
		
	}

	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
		
		Table unitTable = new Table( xSkin );
		unitTable.row();
		
		Image unitIcon = new Image( new PUnit( xEngine.entitiesHolder().getUnitManager().get( getPlayable().getUnit() ), xEngine ).getLargeIcon() );
		unitIcon.setScaling( Scaling.fill );
		
		Label unitNameLabel = new Label( xEngine.entitiesHolder().getUnitManager().get( getPlayable().getUnit() ).getName(), xSkin );
		
		unitTable.add( unitIcon ).size( 30 ).padRight( 5 );
		unitTable.add( unitNameLabel );
		
		xTable.row();
		xTable.add( unitTable ).colspan( 2 ).size( 30 ).padBottom( 5 );
		xTable.row();
		
		for( StatReg statReg: getPlayable().getStatRegs().values() ){
			
			Table statTable = new Table( xSkin );
			statTable.row();
			
			Image icon = new Image( xEngine.common().getStats().get( statReg.getStat() ).getLargeIcon() );
			icon.setScaling( Scaling.fill );
			statTable.add( icon ).size( 15 ).padRight( 10 );
			
			Label nameLabel = new Label( xEngine.common().getStats().get( statReg.getStat() ).getName(), xSkin );
			statTable.add( nameLabel ).width( 75 );
			
			Label valueLabel = new Label( Integer.toString( statReg.getValue() ), xSkin );
			valueLabel.setAlignment( Align.right, Align.right );
			statTable.add( valueLabel ).width( 75 ).right();
			
			xTable.add( statTable ).colspan( 2 ).padBottom( 5 );
			xTable.row();
			
		}
		
	}
	
}
