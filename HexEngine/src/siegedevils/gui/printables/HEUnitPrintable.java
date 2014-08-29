package siegedevils.gui.printables;

import java.util.Iterator;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.moddable.playables.HEAffect;
import lezli.hex.engine.moddable.playables.HEStatReg;
import lezli.hex.engine.moddable.playables.HEUnit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HEUnitPrintable extends HELivingPlayablePrintable< HEUnit >{

	public HEUnitPrintable( HEUnit xPlayable ){
		
		super( xPlayable );
		
	}
	
	@Override
	public void fillTable( Table xTable, Skin xSkin, HexEngine xEngine ){

		super.fillTable( xTable, xSkin, xEngine );
	
		xTable.row();

		Table statTable = new Table( xSkin );
		statTable.row();
		Image hpi = new Image( xEngine.common().getStats().get( "@HIT_POINTS" ).getLargeIcon() );
		statTable.add( hpi ).left().size( 60 ).padRight( 5 );
		Label hl = new Label( getPlayable().statRegs().get( "@HIT_POINTS" ).getValue() + "/" + getPlayable().statRegs().get( "@MAX_HIT_POINTS" ).getValue(), xSkin, "fnt-small", Color.WHITE );
		hl.setWrap( true );
		statTable.add( hl ).expandX().left().width( 80 );
		
		Image spi = new Image( xEngine.common().getStats().get( "@SPEED" ).getLargeIcon() );
		statTable.add( spi ).left().size( 60 ).padRight( 5 );
		Label sl = new Label( getPlayable().statRegs().get( "@SPEED" ).getValue() + "/" + getPlayable().statRegs().get( "@MAX_SPEED" ).getValue(), xSkin, "fnt-small", Color.WHITE );
		sl.setWrap( true );
		statTable.add( sl ).expandX().left().width( 80 );
		
		xTable.add( statTable ).expandX().fillX().left().colspan( 2 ).padBottom( 5 );
		xTable.row();
		
		statTable = new Table( xSkin );
		statTable.row();
		Image fvi = new Image( xEngine.common().getStats().get( "@FIELD_OF_VIEW" ).getLargeIcon() );
		statTable.add( fvi ).left().size( 60 ).padRight( 5 );
		Label fvl = new Label( Integer.toString( getPlayable().statRegs().get( "@FIELD_OF_VIEW" ).getValue() ), xSkin, "fnt-small", Color.WHITE );
		fvl.setWrap( true );
		statTable.add( fvl ).expandX().left().width( 80 );;
		
		Image fhi = new Image( xEngine.common().getStats().get( "@FIELD_OF_HEED" ).getLargeIcon() );
		statTable.add( fhi ).left().size( 60 ).padRight( 5 );
		Label fhl = new Label( Integer.toString( getPlayable().statRegs().get( "@FIELD_OF_HEED" ).getValue() ), xSkin, "fnt-small", Color.WHITE );
		fhl.setWrap( true );
		statTable.add( fhl ).expandX().left().width( 80 );
		
		xTable.add( statTable ).expandX().fillX().left().colspan( 2 ).padBottom( 10 );
		
		Iterator< HEStatReg > statIter = getPlayable().statRegs().values().iterator();
		
		while( statIter.hasNext() ){
			
			HEStatReg statReg = statIter.next();
			
			if( statReg.getStat().equals( "@HIT_POINTS" ) ||
				statReg.getStat().equals( "@MAX_HIT_POINTS" ) ||
				statReg.getStat().equals( "@SPEED" ) ||
				statReg.getStat().equals( "@MAX_SPEED" ) ||
				statReg.getStat().equals( "@FIELD_OF_VIEW" ) ||
				statReg.getStat().equals( "@FIELD_OF_HEED" ) )
				continue;
			
			xTable.row();
			
			statTable = new Table( xSkin );
			
			Image i1 = new Image( xEngine.common().getStats().get( statReg.getStat() ).getLargeIcon() );
			statTable.add( i1 ).left().size( 40 ).padRight( 5 );
			
			Label n1 = new Label( statReg.getStatName(), xSkin, "fnt-small", Color.WHITE );
			n1.setWrap( true );
			statTable.add( n1 ).padRight( 5 ).width( 100 );
			
			Label v1 = new Label( Integer.toString( statReg.getValue() ), xSkin, "fnt-small", Color.WHITE );
			statTable.add( v1 ).expandX().left();
			
			xTable.add( statTable ).colspan( 3 ).expandX().fillX().padTop( 5 ).left();
			
		}
		
		if( getPlayable().hasAffects() ){
		
			Label affectsLabel = new Label( "Affects", xSkin, "fnt-small", Color.WHITE );
			xTable.row();
			xTable.add( affectsLabel ).padTop( 10 ).padBottom( 5 );
			
			for( HEAffect affect: getPlayable().affects() ){
				
//				if( !affect.finished() ){
				
					xTable.row();
					xTable.add( new HEAffectPrintable( affect ).getListElementTable( xSkin, xEngine ) ).padBottom( 5 );
				
//				}
				
			}
		
		}

		xTable.row();
		
	}

}
