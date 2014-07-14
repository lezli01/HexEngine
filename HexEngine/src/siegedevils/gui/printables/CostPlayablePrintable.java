package siegedevils.gui.printables;

import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.cost.CostPlayable;

public class CostPlayablePrintable< T extends CostPlayable< ? > > extends GraphicalPlayablePrintable< CostPlayable< ? > >{

	public CostPlayablePrintable( T xPlayable ){
		
		super( xPlayable );
		
	}
	
	@Override
	public Table getListElementTable( Skin xSkin ){

		Table table = super.getListElementTable( xSkin );
	
		for( Entry< String, Integer > cost: getPlayable().getCost().getResourceCosts().entrySet() ){
			
			table.row();
			
			Table costTable = new Table( xSkin );
			
			Image resIcon = new Image( HexEngine.COMMON.getResources().get( cost.getKey() ).getLargeIcon() );
			resIcon.setScaling( Scaling.fit );
			
			Label resVal = new Label( Integer.toString( cost.getValue() ), xSkin );
			
			costTable.add( resIcon ).width( 15 ).left().padRight( 5 ).expandY();
			costTable.add( resVal ).left().expandX();
			
			table.add( costTable ).expandX().fillX().left().height( 15 );
			
		}
		
		return table;
		
	}

}
