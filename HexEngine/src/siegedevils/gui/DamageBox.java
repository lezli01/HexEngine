package siegedevils.gui;

import lezli.hex.engine.core.playables.LivingPlayable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DamageBox extends FloatingActor{

	private LivingPlayable< ? > mLiving;
	
	private String mText;
	private Skin mSkin;
	
	public DamageBox( Table xTable, Skin xSkin ){

		super( xTable, xSkin );
	
		mSkin = xSkin;
		
		getScrollPane().toBack();
		
	}
	
	public void setLiving( LivingPlayable< ? > xUnit ){
		
		mLiving = xUnit;
		
	}
	
	public void update(){

		setDestination( Gdx.graphics.getWidth() / 2 + mLiving.getScreenX(), Gdx.graphics.getHeight() / 2 - mLiving.getScreenY() );
		
	}
	
	public void setDamage( int value ){
		
		mText = Integer.toString( value );
		
		Table table = getTable();
		table.clearChildren();
		
		Label damageLabel = new Label( mText, mSkin, "fnt-medium", value > 0 ? Color.GREEN : Color.WHITE );
		damageLabel.setFillParent( true );

		table.row();
		table.add( damageLabel );
		
	}

}
