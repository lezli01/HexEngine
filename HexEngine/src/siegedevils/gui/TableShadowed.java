package siegedevils.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TableShadowed extends Table{

	private static final int SHDW_SIZE = 15;
	
	private Image i;
	
	public TableShadowed( Skin skin ){

		super( skin );
		
		i = new Image( skin, "shadow" );
		
	}

	@Override
	public void draw( Batch batch, float parentAlpha ){

		i.setBounds( getX() - SHDW_SIZE, getY() - SHDW_SIZE, getWidth() + SHDW_SIZE * 2, getHeight() + SHDW_SIZE * 2 );
		i.draw( batch, 0.8f );
		super.draw(batch, 1.0f );
	
	}
	
}
