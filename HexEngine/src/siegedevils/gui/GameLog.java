package siegedevils.gui;

import java.text.DateFormat;
import java.util.Date;

import lezli.hex.engine.core.playables.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameLog extends Actor implements Logger{

	private Table mTable;
	private Label mLogLable;
	private ScrollPane mLogScrollPane;
	private Skin mSkin;
	
	private boolean mShow;
	
	public GameLog( Table xTable, Skin xSkin ){
		
		mSkin = xSkin;
		
		mLogLable = new Label( "", xSkin );
		mLogLable.setWrap( true );
		
		mTable = new Table( xSkin );
		mTable.padLeft( 10 ).padRight( 10 );
		mTable.row();
		mTable.add( mLogLable ).expandX().fillX().left().bottom();
		
		mLogScrollPane = new ScrollPane( mTable, xSkin );
		mLogScrollPane.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		mLogScrollPane.setPosition( 0, Gdx.graphics.getHeight() );
		
		mLogScrollPane.setColor( 1.0f, 0.8f, 0.8f, 0.75f );
		
		mShow = false;
		
		mLogScrollPane.setTouchable( Touchable.disabled );
		
		xTable.addActor( mLogScrollPane );
		
	}
	
	private void scrollToEnd(){

		mLogScrollPane.layout();
		mLogScrollPane.scrollTo( 0, 0, mLogScrollPane.getWidth(), mLogScrollPane.getHeight() );
		
	}
	
	public void show(){
		
		mShow = true;
		
		mLogScrollPane.clearActions();
		mLogScrollPane.addAction( Actions.moveTo( 0.0f, 0.0f, 1.0f, Interpolation.exp10Out ) );
		
		mLogScrollPane.getStage().setScrollFocus( mLogScrollPane );
		
		scrollToEnd();
		
	}
	
	public void hide(){
		
		mShow = false;
		
		mLogScrollPane.clearActions();
		mLogScrollPane.addAction( Actions.moveTo( 0.0f, Gdx.graphics.getHeight(), 1.0f, Interpolation.exp10Out ) );
		
		mLogScrollPane.getStage().setScrollFocus( null );
		
		scrollToEnd();
		
	}
	
	public void toggle(){
		
		if( mShow )
			hide();
		else
			show();
		
	}
	
	@Override
	public void log( String xMsg ){

		log( xMsg, 0 );
		
	}
	
	@Override
	public void log( String xMsg, int xDepth ){
		
		System.out.println( xMsg );
		
		if( mTable.getChildren().size > 1000 )
			mTable.getChildren().removeIndex( 0 );
		
		mTable.row();

		Label timestamp = new Label( DateFormat.getTimeInstance().format( new Date( System.currentTimeMillis() ) ) + ":", mSkin, "console", Color.WHITE );
		Label row = new Label( xMsg, mSkin, "console", Color.WHITE );
		row.setWrap( true );
		row.setAlignment( Align.left );
		
		mTable.add( timestamp ).top().right();
		mTable.add( row ).left().width( Gdx.graphics.getWidth() * 0.85f ).expandX().fillX().padLeft( xDepth * 50 );
		
		scrollToEnd();
		
	}
	
}
