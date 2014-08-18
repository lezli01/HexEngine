package siegedevils.gui.printables;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.graphics.GraphicalPlayable;
import siegedevils.gui.ImageShadowed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class GraphicalPlayablePrintable< T extends GraphicalPlayable< ? > > extends PlayablePrintable< T >{

	public GraphicalPlayablePrintable( T xPlayable ){
		
		super( xPlayable );
		
	}
	
	public Table getListElementTable( Skin xSkin, HexEngine xEngine ){
		
		final Table table = new Table( xSkin );
		
		table.setBackground( "top-bg" );
		table.pad( 5 );
		table.row();
		
		Image i = new Image( getPlayable().getLargeIcon() );
		i.setScaling( Scaling.fill );

		Label l = new Label( getPlayable().getName(), xSkin, "fnt-medium", Color.WHITE );
		l.setWrap( true );
		table.add( l ).width( 200 ).expandX();
		table.add( i ).expandX().size( 75 );
		
		table.setTouchable( Touchable.enabled );

		table.addListener( new InputListener(){
			
			@Override
			public void enter( InputEvent event, float x, float y, int pointer, Actor fromActor ){

				table.setBackground( "scroll-bg" );
				table.pad( 5 );
				
			}
			
			@Override
			public void exit( InputEvent event, float x, float y, int pointer, Actor toActor ){
				
				table.setBackground( "top-bg" );
				table.pad( 5 );
				
			}
			
		});
		
		return table;
		
	}
	
	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		Table nameTable = new Table( xSkin );
		nameTable.row();
		
		ImageShadowed i = new ImageShadowed( getPlayable().getLargeIcon(), xSkin );
		i.setScaling( Scaling.fill );
		
		Label nameLabel = new Label( getPlayable().getName(), xSkin, xTable.hasChildren() ? "fnt-small" : "fnt-medium", Color.WHITE );
		nameLabel.setWrap( true );
		nameLabel.setAlignment( Align.center );
		
		Stack s = new Stack();
		Image ribbon = new Image( xSkin, "banner-red" );
		ribbon.setScaling( Scaling.stretch );
		s.add( ribbon );
		s.add( nameLabel );
		
		if( xTable.hasChildren() ){
			
			nameTable.add( i ).size( 50 );
			nameTable.row();
			nameTable.add( s ).height( 50 ).width( 200 ).padTop( 5 ).top();
			xTable.add( nameTable ).expandX().fillX();
			
		}
		else{
		
			
			nameTable.add( i ).left().size( xTable.getParent().getWidth() / 3.0f - 5 );
			
			nameTable.add( s ).expandX().fillX().width( 2.0f * xTable.getParent().getWidth() / 3.0f - 10 ).left().top();
			xTable.add( nameTable ).expandX().fillX().colspan( 2 );

		}
		
		
		Label descriptionLabel = new Label( getPlayable().getDescription(), xSkin );
		descriptionLabel.setWrap( true );
		xTable.row();
		xTable.add( descriptionLabel ).colspan( 2 ).left().width( 250 ).padBottom( 10 ).padTop( 10 );
		xTable.row();
		
		
		
	}

}
