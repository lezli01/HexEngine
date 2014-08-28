package lezli.hex.enginex.utils.controller;

import com.badlogic.gdx.Input.Buttons;

import lezli.hex.engine.moddable.gametable.HEGameTableController;
import lezli.hex.engine.moddable.interfaces.HETile;

public class DefaultGameController extends HEGameTableController{

	@Override
	protected boolean tap( float x, float y, int count, int button) {

		if( button == Buttons.RIGHT ){

			features().clearHighlights();
			return true;
			
		}
		
		if( button == Buttons.LEFT ){
			
			HETile tile = gametable().getTile( x, y );
			
			if( tile != null ){
				
				features().select( tile );
				return true;
				
			}
			
		}
			
		return false;
	
	}
	
}
