package lezli.hex.engine.core.playables.common;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import lezli.hex.engine.core.structure.entities.common.Stat;

import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PStat extends GraphicalPlayable< Stat >{

	public PStat( Stat xEntity, HexEngine xEngine ) {
		
		super( xEntity, xEngine );
		
	}

	public SpriteDrawable getLargeIcon(){
		
		SpriteDrawable largeIcon = new SpriteDrawable( getGraphics().getSprite( "@LARGE_ICON" ) );
		
		if( largeIcon.getSprite().isFlipY() )
			largeIcon.getSprite().flip( false, true );
		
		largeIcon.setMinHeight( 100 );
		largeIcon.setMinWidth( 100 );
		
		return largeIcon;
		
	}
	
	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}

}
