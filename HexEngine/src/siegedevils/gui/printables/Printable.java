package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface Printable {

	public Table getListElementTable( Skin xSkin, HexEngine xEngine );
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine );
	
}
