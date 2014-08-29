package siegedevils.gui;

import java.util.ArrayList;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.moddable.gametable.HEGameTableFeatures;
import lezli.hex.engine.moddable.playables.HEBuildingReg;
import lezli.hex.engine.moddable.playables.HESkill;
import lezli.hex.engine.moddable.playables.HEUnit;
import siegedevils.gui.printables.HEBuildingRegPrintable;
import siegedevils.gui.printables.HESkillPrintable;
import siegedevils.gui.printables.HEUnitPrintable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class UnitDetails extends Actor{

	private static final float BTNS_HEIGHT = 50;
	private static final float WIDTH = 300.0f;
	private static final float HEIGHT = Gdx.graphics.getHeight() - BTNS_HEIGHT;
	
	public static final int TAB_INFO = 1;
	public static final int TAB_SKILLS = 2;
	public static final int TAB_BUILDINGS = 3;
	public static final int TAB_SKILL = 4;
	public static final int TAB_BUILDING = 5;
	public static final int TAB_ONLY_INFO = 6;
	
	private Table  mTable;
	private ScrollPaneShadowed mScrollPane;
	private Skin mSkin;
	
	private Table mButtonsTable;
	private ScrollPane mButtonsPane;
	
	private int mCurrentTab;
	private HEUnit mCurrentUnitTo;
	private HEUnit mCurrentUnit;
	private HESkill mCurrentSkill;
	private HEBuildingReg mCurrentBuilding;
	private int mBtn1Tab;
	private int mBtn2Tab;
	
	private ArrayList< HEGameTableFeatures > mListeners;
	
	private HexEngine e;
	
	public UnitDetails( Table xStage, Skin xSkin, HexEngine xEngine ){
		
		e = xEngine;
		
		mSkin = xSkin;

		mCurrentTab = TAB_INFO;
		mBtn1Tab = TAB_INFO;
		mBtn2Tab = TAB_INFO;
		
		mTable = new Table( xSkin );

		mTable.setBackground( new TiledDrawable( xSkin.getRegion( "scroll-bg-tiled" ) ) );

		mScrollPane = new ScrollPaneShadowed( mTable, xSkin );
		mScrollPane.setPosition( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - HEIGHT );
		mScrollPane.setSize( WIDTH, HEIGHT );
		mScrollPane.setScrollingDisabled( true, false );
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
	
	public int getCurrentTab(){
		
		return mCurrentTab;
		
	}
	
	public HESkill getCurrentSkill(){
		
		return mCurrentSkill;
		
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
		
		mCurrentTab = 0;
		
	}
	
	public void show( int xTab, HEUnit xUnit, HESkill xSkill, HEBuildingReg xBuilding ){
		
		mScrollPane.clearActions();
		mScrollPane.setPosition( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - HEIGHT );
		mScrollPane.addAction( Actions.parallel( Actions.moveTo( Gdx.graphics.getWidth() - WIDTH, Gdx.graphics.getHeight() - HEIGHT, 0.5f, Interpolation.exp10Out ) ) );
		
		mButtonsPane.clearActions();
		mButtonsPane.addAction( Actions.sequence( Actions.moveTo( Gdx.graphics.getWidth() - WIDTH, 0, 0.5f, Interpolation.exp10Out ) ) );

		mCurrentUnit = xUnit;
		mCurrentUnitTo = null;
		mCurrentSkill = xSkill;
		mCurrentBuilding = xBuilding;
		
		if( mCurrentTab == TAB_SKILL )
			for( HEGameTableFeatures listener: mListeners )
				listener.clearHighlights();
		
		mCurrentTab = xTab;
		
		update( xUnit, xSkill );

		if( xTab == TAB_ONLY_INFO )
			return;
		
	}
	
	public void show( int xTab, HEUnit xUnit ){
		
		show( xTab, xUnit, null, null );
		
	}
	
	private void update( HEUnit xUnit, HESkill xSkill ){
		
		mTable.clear();
		
		mTable.align( Align.top );

		switch( mCurrentTab ){
		
			case TAB_INFO: case TAB_ONLY_INFO: updateInfo( xUnit ); break;
			case TAB_SKILLS: updateSkills( xUnit ); break;
			case TAB_BUILDINGS: updateBuildings( xUnit ); break;
			case TAB_SKILL: updateSkill(); break;
			case TAB_BUILDING: updateBuilding(); break;
			
		}
		
		addButtons();
		
	}
	
	private void updateInfo( HEUnit xUnit ){

		new HEUnitPrintable( xUnit ).fillTable( mTable, mSkin, e );
		
	}
	
	private void updateSkill(){
		
		for( HEGameTableFeatures listener: mListeners )
			listener.chooseSkill( mCurrentSkill );

		new HESkillPrintable( mCurrentSkill ).fillTable( mTable, mSkin, mCurrentUnitTo, e );

		if( mCurrentSkill.getRange() == 0 ){
		
			Button castButton = new TextButton( "Cast", mSkin, "default-medium" );
			
			castButton.addListener( new ClickListener(){
				
				@Override
				public void clicked( InputEvent event, float x, float y ){
					
					for( HEGameTableFeatures listener: mListeners )
						listener.castSkill();
					
				}
				
			});
			
			mTable.left().add( castButton ).height( WIDTH / 4.0f ).width( 200 );
			
		}
	}
	
	private void updateSkills( HEUnit xUnit ){
		
		for( final HESkill skill: xUnit.allSkills().values() ){
			
			mTable.row();

			Table skillTable = new HESkillPrintable( skill ).getListElementTable( mSkin, e );
			
			if( !skill.isCooldown() ){
			
				skillTable.addListener( new ClickListener(){
					
					@Override
					public void clicked( InputEvent event, float x, float y ){
	
						show( TAB_SKILL, mCurrentUnit, skill, mCurrentBuilding );
						
					}
					
				});
			
			}
			
			mTable.left().add( skillTable ).padBottom( 10 );
			
		}
		
	}
	
	private void updateBuilding(){
		
		for( HEGameTableFeatures listener: mListeners )
			listener.chooseBuilding( mCurrentBuilding );

		new HEBuildingRegPrintable( mCurrentBuilding ).fillTable( mTable, mSkin, e );
		
	}
	
	private void updateBuildings( HEUnit xUnit ){
		
		for( final HEBuildingReg buildingReg: xUnit.buildingRegs().values() ){
			
			mTable.row();

			Table buildingTable = new HEBuildingRegPrintable( buildingReg ).getListElementTable( mSkin, e );
		
			buildingTable.addListener( new ClickListener(){
				
				@Override
				public void clicked( InputEvent event, float x, float y ){

					System.out.println( buildingReg );
					
					show( TAB_BUILDING, mCurrentUnit, mCurrentSkill, buildingReg );
					
				}
				
			});
			
			mTable.left().add( buildingTable ).padBottom( 10 );
			
		}
		
	}
	
	private void addButtons(){
		
		mButtonsTable.clear();
		
		TextButton btn1 = null;
		TextButton btn2 = null;

		String btn1Title = "Title";
		String btn2Title = "Title";
		
		int colSpan = 2;
		
		switch( mCurrentTab ){
		
			case TAB_INFO:
				
				btn1Title = "Skills";
				btn2Title = "Build";
				
				mBtn1Tab = TAB_SKILLS;
				mBtn2Tab = TAB_BUILDINGS;
				
			break;
			
			case TAB_ONLY_INFO:
				return;
			
			case TAB_SKILLS:
			
				btn1Title = "Info";
				btn2Title = "Build";
				
				mBtn1Tab = TAB_INFO;
				mBtn2Tab = TAB_BUILDINGS;
				
				colSpan = 4;
				
			break;
			
			case TAB_BUILDINGS:
				
				btn1Title = "Skills";
				btn2Title = "Info";
				
				mBtn1Tab = TAB_SKILLS;
				mBtn2Tab = TAB_INFO;
				
			break;
			
			case TAB_SKILL:
				
				btn1Title = "Back";
				btn2Title = "Info";
				
				mBtn1Tab = TAB_SKILLS;
				mBtn2Tab = TAB_INFO;
				
			break;
			
			case TAB_BUILDING:
				
				btn1Title = "Back";
				btn2Title = "Info";
				
				mBtn1Tab = TAB_BUILDINGS;
				mBtn2Tab = TAB_INFO;
				
			break;
		
		}
		
		btn1 = new TextButton( btn1Title, mSkin, "default-medium" );
		btn1.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){
				
				show( mBtn1Tab, mCurrentUnit );
				
			}
			
		});
		
		btn2 = new TextButton( btn2Title, mSkin, "default-medium" );
		btn2.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				show( mBtn2Tab, mCurrentUnit );
				
			}
			
		});
		
		Table buttonsTable = new Table();
		buttonsTable.row();
		
		buttonsTable.add( btn1 ).width( WIDTH / 2.0f ).expandY().fillY();
		buttonsTable.add( btn2 ).width( WIDTH / 2.0f ).expandY().fillY();
	
		if( btn2Title.equals( "Build" ) && mCurrentUnit.buildingRegs().size() == 0 ){
		
			btn2.setTouchable( Touchable.disabled );
			btn2.setColor( 0.25f, 0.25f, 0.25f, 1.0f );
		
		}
		
		mButtonsTable.row();
		
		mButtonsTable.add( buttonsTable ).colspan( colSpan ).expandX().fillX().expandY().bottom().expandY().fillY();
		
	}
	
}
