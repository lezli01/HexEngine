package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.moddable.playables.HEBuilding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HEBuildingPrintable extends HELivingPlayablePrintable< HEBuilding >{

	public HEBuildingPrintable( HEBuilding xPlayable ){

		super( xPlayable );
		
	}

	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
	
		xTable.row();

		Table statTable = new Table( xSkin );
		statTable.row();
		Image hpi = new Image( xEngine.common().getStats().get( "@HIT_POINTS" ).getLargeIcon() );
		statTable.add( hpi ).left().size( 60 ).padRight( 5 );
		Label hl = new Label( getPlayable().getHitPoints() + "/" + getPlayable().getMaxHitPoints(), xSkin, "fnt-small", Color.WHITE );
		hl.setWrap( true );
		statTable.add( hl ).expandX().left().width( 100 );
		
		xTable.add( statTable ).expandX().colspan( 2 ).fillX().left();
		
	}
	
}
