package siegedevils.gui;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.building.produce.PSkillUpgrade;
import lezli.hex.engine.core.playables.building.produce.PStatUpgrade;
import lezli.hex.engine.core.playables.building.produce.PUnitProduce;
import lezli.hex.engine.core.playables.unit.PUnit;
import lezli.hex.engine.moddable.gametable.HEGameTableFeatures;
import lezli.hex.engine.moddable.playables.HEBuilding;
import lezli.hex.engine.moddable.playables.HEProduce;
import lezli.hex.engine.moddable.playables.HESkillProduce;
import lezli.hex.engine.moddable.playables.HEStatProduce;
import lezli.hex.engine.moddable.playables.HEUnitProduce;
import siegedevils.gui.printables.HEPlayablePrintable;
import siegedevils.gui.printables.HEBuildingPrintable;
import siegedevils.gui.printables.HEProducePrintable;
import siegedevils.gui.printables.HEStatProducePrintable;
import siegedevils.gui.printables.HEUnitPrintable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BuildingDetails extends Actor{

	private static final float BTNS_HEIGHT = 50;
	private static final float WIDTH = 300;
	private static final float HEIGHT = Gdx.graphics.getHeight() - BTNS_HEIGHT;
	
	public static final int TAB_INFO = 1;
	public static final int TAB_INFO_ONLY = 2;
	public static final int TAB_UNIT = 3;
	public static final int TAB_STAT_UPGRADE = 4;
	public static final int TAB_SKILL_UPGRADE = 5;
	
	private Table  mTable;
	private ScrollPaneShadowed mScrollPane;
	private Skin mSkin;
	
	private Table mButtonsTable;
	private ScrollPane mButtonsPane;
	
	private int mCurrentTab;
	
	private HEBuilding mCurrentBuilding;
	private HEProduce mCurrentProduce;
	
	private int mBtn1Tab;
	private int mBtn2Tab;
	
	private ArrayList< HEGameTableFeatures > mListeners;
	
	private HexEngine e;
	
	public BuildingDetails( Table xStage, Skin xSkin, HexEngine xEngine ){
		
		e = xEngine;
		
		mSkin = xSkin;

		mCurrentTab = TAB_INFO;
		mBtn1Tab = TAB_INFO;
		mBtn2Tab = TAB_INFO;
		
		mTable = new Table( xSkin );
		
		
		mScrollPane = new ScrollPaneShadowed( mTable, xSkin );
		mScrollPane.setPosition( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - HEIGHT );
		mScrollPane.setSize( WIDTH, HEIGHT );
		mScrollPane.addListener( new InputListener(){
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {

				if( toActor == null || !mScrollPane.isAscendantOf( toActor ) )
					mScrollPane.getStage().setScrollFocus( null );
				
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {

				mScrollPane.getStage().setScrollFocus( mScrollPane );
				
			}
			
		});
		
		xStage.addActor( mScrollPane );
		
		mButtonsTable = new Table( mSkin );
		mButtonsPane = new ScrollPane( mButtonsTable );
		mButtonsPane.setPosition( Gdx.graphics.getWidth(), 0 );
		mButtonsPane.setSize( WIDTH, BTNS_HEIGHT );
		mButtonsPane.setScrollingDisabled( true, true );
		xStage.addActor( mButtonsPane );
		
		mListeners = new ArrayList< HEGameTableFeatures >();
		
	}
	
	public void addListener( HEGameTableFeatures xListener ){
		
		mListeners.add( xListener );
		
	}
	
	public void hide(){
		
		mScrollPane.clearActions();
		mScrollPane.addAction( Actions.sequence( Actions.moveTo( Gdx.graphics.getWidth() + 100, Gdx.graphics.getHeight() - HEIGHT, 0.5f, Interpolation.exp10Out ) ) );
		
		mButtonsPane.clearActions();
		mButtonsPane.addAction( Actions.moveTo( Gdx.graphics.getWidth(), 0, 0.5f, Interpolation.exp10Out ) );
		
		mButtonsPane.getStage().setScrollFocus( null );
		mScrollPane.getStage().setScrollFocus( null );
		
	}
	
	public void show( int xTab, HEBuilding xBuilding ){
		
		mScrollPane.clearActions();
		mScrollPane.setPosition( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - HEIGHT );
		mScrollPane.addAction( Actions.parallel( Actions.moveTo( Gdx.graphics.getWidth() - WIDTH, Gdx.graphics.getHeight() - HEIGHT, 0.5f, Interpolation.exp10Out ) ) );
		
		
		mCurrentTab = xTab;
		mCurrentBuilding = xBuilding;
		
		mButtonsPane.clearActions();
		mButtonsPane.addAction( Actions.sequence( Actions.moveTo( Gdx.graphics.getWidth() - WIDTH, 0, 0.5f, Interpolation.exp10Out ) ) );
		
		update();
		
	}
	
	private void update(){
		
		mTable.clear();
		
		mTable.align( Align.top );

		switch( mCurrentTab ){
		
			case TAB_INFO: case TAB_INFO_ONLY: updateInfo(); break;
			case TAB_UNIT: updateUnit(); break;
			case TAB_SKILL_UPGRADE: updateSkill(); break;
			case TAB_STAT_UPGRADE: updateStat(); break;
			
		}
		
		addButtons();
		
	}
	
	private void updateInfo(){

		new HEBuildingPrintable( mCurrentBuilding ).fillTable( mTable, mSkin, e );
		
		if( mCurrentTab != TAB_INFO_ONLY )
			addProduces();
		
	}
	
	public void addProduces(){
		
		if( mCurrentBuilding.unitProduces().size() > 0 ){
		
			mTable.row();
			Label unitsLabel = new Label( "Units", mSkin, "fnt-medium", Color.WHITE );
			mTable.add( unitsLabel ).expandX().padBottom( 5 );
			
			for( HEUnitProduce unitProduce: mCurrentBuilding.unitProduces().values() )
				addProduce( mTable, unitProduce );
		
		}
		
		if( mCurrentBuilding.statUpgrades().size() > 0 ){
		
			mTable.row();
			Label upgradesLabel = new Label( "Upgrades", mSkin, "fnt-medium", Color.WHITE );
			mTable.add( upgradesLabel ).expandX().padTop( 10 ).padBottom( 5 );
			
			for( HEStatProduce statUpgrade: mCurrentBuilding.statUpgrades().values() )
				addProduce( mTable, statUpgrade );
		
		}
		
		if( mCurrentBuilding.skillUpgrades().size() > 0 ){
		
			mTable.row();
			Label skillsLabel = new Label( "Skills", mSkin, "fnt-medium", Color.WHITE );
			mTable.add( skillsLabel ).expandX().padTop( 10 ).padBottom( 5 );
			
			for( HESkillProduce skillUpgrade: mCurrentBuilding.skillUpgrades().values() )
				addProduce( mTable, skillUpgrade );
		
		}
		
	}
	
	public void addProduce( Table xTable, final HEProduce xProduce ){
		
		mTable.row();

		Table skillTable = new HEProducePrintable( xProduce ).getListElementTable( mSkin, e );
		
		if( !xProduce.isProducing() && mCurrentBuilding.isConstructed() ){
			
			skillTable.addListener( new ClickListener(){
				
				@Override
				public void clicked( InputEvent event, float x, float y ){

					mCurrentProduce = xProduce;

					if( xProduce instanceof PUnitProduce )
						show( TAB_UNIT, mCurrentBuilding );
					
					if( xProduce instanceof PSkillUpgrade )
						show( TAB_SKILL_UPGRADE, mCurrentBuilding );
					
					if( xProduce instanceof PStatUpgrade )
						show( TAB_STAT_UPGRADE, mCurrentBuilding );
					
				}
				
			});
			
		}
		
		xTable.add( skillTable ).left().padBottom( 10 );
		
	}
	
	private void updateUnit(){
		
		PUnit xUnit = new PUnit( ( PUnitProduce ) mCurrentProduce, e );
		new HEUnitPrintable( xUnit ).fillTable( mTable, mSkin, e );
		
	}
	
	private void updateSkill(){
		
		new HEPlayablePrintable< HESkillProduce >( ( ( PSkillUpgrade ) mCurrentProduce ) ).fillTable( mTable, mSkin, e );
		
	}
	
	private void updateStat(){
		
		new HEStatProducePrintable( ( ( PStatUpgrade ) mCurrentProduce ) ).fillTable( mTable, mSkin, e );
		
	}
	
	private void addButtons(){
		
		mButtonsTable.clear();
		
		TextButton btn1 = null;
		TextButton btn2 = null;

		String btn1Title = "Title";
		String btn2Title = "Title";
		
		int colSpan = 2;
		
		switch( mCurrentTab ){
		
			case TAB_INFO: case TAB_INFO_ONLY:
				return;
		
			case TAB_UNIT: case TAB_SKILL_UPGRADE: case TAB_STAT_UPGRADE:
				
				btn1Title = "Back";
				btn2Title = "Produce";
				
				mBtn1Tab = TAB_INFO;
				mBtn2Tab = TAB_INFO;
				
			break;
		
		}
		
		btn1 = new TextButton( btn1Title, mSkin, "default-medium" );
		btn1.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){
				
				show( mBtn1Tab, mCurrentBuilding );
				
			}
			
		});
		
		btn2 = new TextButton( btn2Title, mSkin, "default-medium" );
		btn2.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				for( HEGameTableFeatures listener: mListeners )
					listener.produce( mCurrentProduce );
				
				show( mBtn2Tab, mCurrentBuilding );
				
			}
			
		});
		
		Table buttonsTable = new Table();
		buttonsTable.row();
		
		buttonsTable.add( btn1 ).width( WIDTH / 2.0f ).expandY().fillY();
		buttonsTable.add( btn2 ).width( WIDTH / 2.0f ).expandY().fillY();
	
		mButtonsTable.row();
		
		mButtonsTable.add( buttonsTable ).colspan( colSpan ).expandX().fillX().expandY().bottom().expandY().fillY();
		
	}
	
}
