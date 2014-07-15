package siegedevils.gui.printables;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.Playable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayablePrintable< T extends Playable< ? > > implements Printable{

	private T mPlayable;
	
	public PlayablePrintable( T xPlayable ){
		
		mPlayable = xPlayable;
		
	}
	
	public T getPlayable(){
		
		return mPlayable;
		
	}
	
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ){
		
		final Table table = new Table( xSkin );
		
		table.setBackground( "top-bg" );
		table.pad( 5 );
		table.row();
		
		Label l = new Label( getPlayable().getName(), xSkin, "fnt-small", Color.WHITE );
		l.setWrap( true );
		table.add( l ).width( 250 );
		
		table.row();
		
		return table;
		
	}

	@Override
	public void fillTable(Table xTable, Skin xSkin, HexEngine xEngine ) {
		
	}
	
}
