package siegedevils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class FloatingActor {

	private Table mTable;
	private ScrollPane mScrollPane;
	
	private int x, y;
	
	public FloatingActor( Table xTable, Skin xSkin ){
		
		mTable = new Table( xSkin );

		mScrollPane = new ScrollPane( mTable, xSkin, "floating" );
		
		xTable.add( mScrollPane );
		
	}
	
	public void setDestination( int x, int y ){
		
		this.x = x;
		this.y = y;
		
		show();
		
	}
	
	public Table getTable(){
		
		return mTable;
		
	}
	
	public ScrollPane getScrollPane(){
		
		return mScrollPane;
		
	}
	
	public void hide(){
		
		mScrollPane.clearActions();
		mScrollPane.addAction( Actions.sequence( Actions.moveTo( x, Gdx.graphics.getHeight() + 100, 0.5f, Interpolation.exp10Out ) ) );
		
	}
	
	public void show(){
		
		mScrollPane.clearActions();
		mScrollPane.addAction( Actions.sequence( Actions.moveTo( x, y, 0.5f, Interpolation.exp10Out ) ) );
		
	}
	
	public void clear(){
		
		mScrollPane.clear();
		mScrollPane.remove();
		
	}
	
}
