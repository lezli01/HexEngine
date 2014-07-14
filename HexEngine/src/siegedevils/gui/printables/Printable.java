package siegedevils.gui.printables;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface Printable {

	public Table getListElementTable( Skin xSkin );
	public void fillTable( Table xTable, Skin xSkin );
	
}
