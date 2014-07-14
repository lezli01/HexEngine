package siegedevils.gui;

import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.playables.common.PResource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

public class PlayerPane extends Actor{

	private Skin mSkin;
	private TableShadowed mNameTable;
	
	public PlayerPane( Table xTable, Skin xSkin ){
		
		mSkin = xSkin;
		
		mNameTable = new TableShadowed( mSkin );
		mNameTable.setPosition( 0, Gdx.graphics.getHeight() - 50 );
		mNameTable.setSize( Gdx.graphics.getWidth(), 50 );

		xTable.addActor( mNameTable );
		
	}
	
	public void show( Player xPlayer ){
	
		mNameTable.clear();
		mNameTable.setBackground( "top-bg" );
		mNameTable.row();
		
		Label nameLabel = new Label( xPlayer.getName(), mSkin, "fnt-medium", Color.WHITE );
		mNameTable.add( nameLabel ).padRight( 10 );

		for( PResource res: xPlayer.getResources().values() ){

			Image icon = new Image( res.getLargeIcon() );
			icon.setScaling( Scaling.fit );
			mNameTable.add( icon ).padRight( 5 ).padLeft( 5 ).size( 20 );
			
			Label value = new Label( Integer.toString( res.getQuantity() ), mSkin );
			mNameTable.add( value );
			
		}
		
		
	}
	
}
