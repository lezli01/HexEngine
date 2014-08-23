package lezli.hex.enginex.ui.metro.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MetroButton extends MetroElement{

	private Label mCaption;
	
	public MetroButton( String xId, String xCaption ){
		
		super( xId );
		
		setBackground( "metro-button" );

		setCaption( xCaption );
		
		setTouchable( Touchable.enabled );

	}
	
	public void setCaption( String xCaption ){

		if( mCaption == null ){
		
			mCaption = new Label( xCaption, skin(), "fnt-medium", Color.WHITE );
			mCaption.setPosition( 10, 5 );
			addActor( mCaption );
			
			return;
			
		}
		
	}
	
}
