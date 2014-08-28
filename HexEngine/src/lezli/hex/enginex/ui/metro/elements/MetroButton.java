package lezli.hex.enginex.ui.metro.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MetroButton extends MetroElement{

	private Label mCaption;
	private int TEXT_H_SPACE = 5;
	
	public MetroButton( String xId, String xCaption ){
		
		super( xId );
		
		setBackground( "metro-button" );

		setCaption( xCaption );
		
		setTouchable( Touchable.enabled );

	}

	@Override
	public void setSize( float width, float height ){

		super.setSize( width, height );
		
		mCaption.setWidth( width );
		mCaption.setPosition( TEXT_H_SPACE, mCaption.getTextBounds().height / 2 );
		
	}
	
	public String getCaption(){
		
		return mCaption.getText().toString();
		
	}
	
	public void setCaption( String xCaption ){

		if( mCaption == null ){
		
			mCaption = new Label( xCaption, skin(), "fnt-medium", Color.WHITE );
			mCaption.setPosition( TEXT_H_SPACE, mCaption.getTextBounds().height / 2 );
			mCaption.setWrap( true );
			addActor( mCaption );
			
			return;
			
		}
		
	}
	
}
