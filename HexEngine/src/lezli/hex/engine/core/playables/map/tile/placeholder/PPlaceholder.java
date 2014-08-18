package lezli.hex.engine.core.playables.map.tile.placeholder;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import lezli.hex.engine.core.structure.entities.map.tile.placeholder.Placeholder;

public class PPlaceholder extends GraphicalPlayable< Placeholder >{

	public PPlaceholder( Placeholder xEntity, HexEngine xEngine ){

		super( xEntity, xEngine );
	
		setDefaultAnimation( "@IDLE" );
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}
	
	@Override
	public void turn(){
		
	}

}
