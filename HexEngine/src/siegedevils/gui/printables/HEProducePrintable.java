package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.building.produce.PProducePlayable;
import lezli.hex.engine.moddable.playables.HEProduce;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class HEProducePrintable extends HECostPrintable< HEProduce >{

	public HEProducePrintable( HEProduce xPlayable ){
		
		super( xPlayable );
		
	}

	@Override
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ) {
	
		Table skillTable = super.getListElementTable( xSkin, xEngine );
		
		if( ( ( HEProduce ) getPlayable() ).isProducing() ){
			
			skillTable.row();
			
			Label cooldownLabel = new Label( "Cooldown: " + ( ( PProducePlayable< ?, ? > ) getPlayable() ).getCurrentDuration() + " turn", xSkin );
			cooldownLabel.setAlignment( Align.center, Align.center );
			skillTable.add( cooldownLabel ).padTop( 5 ).expandX().fillX().colspan( 2 );
			skillTable.setColor( 1.0f, 0.7f, 0.7f, 1.0f );
			
		}
		
		return skillTable;
	
	}

	
}
