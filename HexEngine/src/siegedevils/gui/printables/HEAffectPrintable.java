package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.moddable.playables.HEAffect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class HEAffectPrintable extends HEPlayablePrintable< HEAffect >{

	public HEAffectPrintable( HEAffect xPlayable ){
		
		super( xPlayable );
		
	}
	
	@Override
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ){

		Table table = super.getListElementTable( xSkin, xEngine );

		
		Image statIcon = new Image( xEngine.common().getStats().get( getPlayable().getStat() ).getLargeIcon() );
		statIcon.setScaling( Scaling.fill );
		table.add( statIcon ).expandX().center().size( 50 );
		table.row();
		
		Label damageLabel = new Label( getPlayable().getAffectValue(), xSkin, "fnt-small" , Color.WHITE );
		
		try{
		
			if( Integer.parseInt( damageLabel.getText().toString() ) < 0 )
				damageLabel.setColor( Color.RED );
			
			if( Integer.parseInt( damageLabel.getText().toString() ) > 0 )
				damageLabel.setColor( Color.GREEN );
		
		}catch( Exception e ){
			
		}
		
		damageLabel.setWrap( true );
		damageLabel.setAlignment( Align.center );
		table.add( damageLabel ).width( 270 );
		table.row();
		
		if( getPlayable().getWhen() == 0 )
			table.add( "Now").expandX().left();
		else
			table.add( "In turn " + getPlayable().getWhen() ).expandX().left();
		
		table.row();
		
		return table;
		
	}

}
