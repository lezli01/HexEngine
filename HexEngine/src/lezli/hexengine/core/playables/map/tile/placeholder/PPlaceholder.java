package lezli.hexengine.core.playables.map.tile.placeholder;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.structure.entities.map.tile.placeholder.Placeholder;

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
