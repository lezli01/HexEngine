package siegedevils.gui.printables;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.building.produce.PProducePlayable;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PProducePrintable extends CostPlayablePrintable< PProducePlayable< ?, ? > >{

	public PProducePrintable( PProducePlayable< ?, ? > xPlayable ){
		
		super( xPlayable );
		
	}

	@Override
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ) {
	
		Table skillTable = super.getListElementTable( xSkin, xEngine );
		
		if( ( ( PProducePlayable< ?, ? > ) getPlayable() ).isProducing() ){
			
			skillTable.row();
			
			Label cooldownLabel = new Label( "Cooldown: " + ( ( PProducePlayable< ?, ? > ) getPlayable() ).getCurrentDuration() + " turn", xSkin );
			cooldownLabel.setAlignment( Align.center, Align.center );
			skillTable.add( cooldownLabel ).padTop( 5 ).expandX().fillX().colspan( 2 );
			skillTable.setColor( 1.0f, 0.7f, 0.7f, 1.0f );
			
		}
		
		return skillTable;
	
	}

	
}
