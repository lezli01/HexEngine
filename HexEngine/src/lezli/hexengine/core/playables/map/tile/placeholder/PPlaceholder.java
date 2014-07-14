package lezli.hexengine.core.playables.map.tile.placeholder;

import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.structure.entities.map.tile.placeholder.Placeholder;

public class PPlaceholder extends GraphicalPlayable< Placeholder >{

	public PPlaceholder( Placeholder xEntity ){

		super( xEntity );
	
		setDefaultAnimation( "@IDLE" );
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}
	
	@Override
	public void turn(){
		
	}

}
