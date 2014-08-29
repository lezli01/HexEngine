package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.building.PBuilding;
import lezli.hex.engine.core.playables.building.PBuildingReg;
import lezli.hex.engine.core.playables.building.produce.PSkillUpgrade;
import lezli.hex.engine.core.playables.building.produce.PStatUpgrade;
import lezli.hex.engine.core.playables.building.produce.PUnitProduce;
import lezli.hex.engine.moddable.playables.HEBuildingReg;
import lezli.hex.engine.moddable.playables.HESkillProduce;
import lezli.hex.engine.moddable.playables.HEUnitProduce;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HEBuildingRegPrintable extends HEProducePrintable{

	public HEBuildingRegPrintable( HEBuildingReg xPlayable ){
		
		super( xPlayable );
		
	}
	
	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){
		
		super.fillTable( xTable, xSkin, xEngine );
		
		PBuilding building = ( ( PBuildingReg ) getPlayable() ).getPrototype();
		
		if( building.getUnitProduces().size() > 0 ){
			
			Label catLabel = new Label( "Units", xSkin, "fnt-medium", Color.WHITE );
			xTable.add( catLabel ).colspan( 2 ).padBottom( 5 ).padTop( 10 );
			xTable.row();
			
			for( PUnitProduce unit: building.getUnitProduces().values() )
				new HEPlayablePrintable< HEUnitProduce >( unit ).fillTable( xTable, xSkin, xEngine );
		
		}
		
		if( building.getStatUpgrades().size() > 0 ){
		
			Label catLabel = new Label( "Upgrades", xSkin, "fnt-medium", Color.WHITE );
			xTable.add( catLabel ).colspan( 2 ).padBottom( 5 ).padTop( 10 );
			xTable.row();
			
			for( PStatUpgrade stat: building.getStatUpgrades().values() )
				new HEStatProducePrintable( stat ).fillTable( xTable, xSkin, xEngine );
			
		}
		
		if( building.getSkillUpgrades().size() > 0 ){
		
			Label catLabel = new Label( "Skills", xSkin, "fnt-medium", Color.WHITE );
			xTable.add( catLabel ).colspan( 2 ).padBottom( 5 ).padTop( 10 );
			xTable.row();
			
			for( PSkillUpgrade skill: building.getSkillUpgrades().values() )
				new HEPlayablePrintable< HESkillProduce >( skill ).fillTable( xTable, xSkin, xEngine );
		
		}
			
		
	}

}
