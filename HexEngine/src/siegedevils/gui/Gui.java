package siegedevils.gui;

import java.util.ArrayList;

import siegedevils.gui.minimap.Minimap;
import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.gametable.PGameTable;
import lezli.hexengine.core.gametable.event.GameEvent;
import lezli.hexengine.core.gametable.player.Player;
import lezli.hexengine.core.gametable.player.RemotePlayer;
import lezli.hexengine.core.gametable.player.ai.AIPlayer;
import lezli.hexengine.core.playables.LivingPlayable;
import lezli.hexengine.core.playables.Logger;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.cost.PCost;
import lezli.hexengine.core.playables.map.tile.PTile;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.playables.unit.skills.PAffect;
import lezli.hexengine.core.playables.unit.skills.PSkill;
import lezli.hexengine.core.structure.entities.skill.affect.Affect;
import lezli.hexengine.moddable.interfaces.HEGameTableController;
import lezli.hexengine.moddable.listeners.HEGameTableEventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Gui {

	private Skin skin;
	private Stage mMainStage;
	private Table mTable;
	
	private TextButton mTurnButton;
	private TextButton s;
	
	private PlayerPane mPlayerPane;
	
	private ArrayList< HEGameTableController > mListeners;
	private UnitDetails mUnitDetails;
	private PUnit mUnit;
	
	private BuildingDetails mBuildingDetails;
	
	private GameLog mLog;
	
	private PSkill mSkill;
	private ArrayList< DamageBox > mDamageBoxes;
	
	private Player mCurrentPlayer;
	private ScrollPane remoteTable;
	
	private HexEngine mEngine;
	
	private Minimap mm;
	
	public Gui( HexEngine e ){

		mEngine = e;
		
		mListeners = new ArrayList< HEGameTableController >();
		
		mDamageBoxes = new ArrayList< DamageBox >();
		
		mMainStage = new Stage( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true );
		skin = new Skin( Gdx.files.internal( "siegedevils/ui/uiskin.json" ) );

		mTable = new Table();
		mMainStage.addActor( mTable );
		mTable.setFillParent( true );
		mTable.setBounds( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		
		mPlayerPane = new PlayerPane( mTable, skin );
		
		mTurnButton = new TextButton( "Turn", skin );
		mTurnButton.setSize( 150, 50 );
		mTurnButton.setPosition( 0, Gdx.graphics.getHeight() - 50 );
		
		mUnitDetails = new UnitDetails( mTable, skin, mEngine );
		
		mBuildingDetails = new BuildingDetails( mTable, skin, mEngine );
		
		mTable.addActor( mTurnButton );
		
		mTurnButton.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				mEngine.turn();
				
			}
			
		});
		
		s = new TextButton( "Save", skin );
		s.setSize( 150,  50 );
		s.setPosition( 150, Gdx.graphics.getHeight() - 50 );
		
		mTable.addActor( s );
		
		s.addListener( new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {

				mEngine.save( "save.xml" );
			
			}
			
		});
		
		remoteTable = new ScrollPane( null );
		remoteTable.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		remoteTable.setColor( 0.0f, 0.0f, 0.0f, 1.0f );
		remoteTable.setTouchable( Touchable.enabled );
		remoteTable.setWidget( new Label( "Remote", skin, "fnt-medium", Color.WHITE ) );
		
		mTable.addActor( remoteTable );
		
		mLog = new GameLog( mTable, skin );
		
		mm = new Minimap( mEngine.getGameTable() );
		mm.setPosition( 0, 0 );
		mm.setSize( 400, 220 );
		mTable.addActor( mm );
		
	}
	
	public void remoteOn(){
		
		remoteTable.addAction( Actions.moveTo( 0, 0, 1.0f, Interpolation.exp5Out ) );
		
	}
	
	public void remoteOff(){
		
		remoteTable.addAction( Actions.moveTo( Gdx.graphics.getWidth(), 0, 1.0f, Interpolation.exp5Out ) );
		
	}

	public Logger getLogger(){
		
		return mLog;
		
	}
	
	public void addGuiEventListener( HEGameTableController xListener ){
		
		mListeners.add( xListener );
		
		mUnitDetails.addListener( xListener );
		mBuildingDetails.addListener( xListener );
		
	}
	
	public void resize( int w, int h ){
		
		mMainStage.setViewport( w, h, true );
		
	}
	
	public HEGameTableEventListener getGameTableListener(){
		
		return new HEGameTableEventListener() {

			@Override
			public boolean localPlayerTurn( Player remotePlayer ){
				remoteOff();
				return false;
			}
			
			@Override
			public boolean remotePlayerTurn( RemotePlayer remotePlayer, ArrayList<GameEvent> xEvents ){
				remoteOn();
				return false;
			}
			
			@Override
			public boolean ready(){
				
				if( mCurrentPlayer instanceof AIPlayer ){
					
					s.setTouchable( Touchable.disabled );
					s.setText( "Busy" );
				
				}
				else{
					
					s.setTouchable( Touchable.enabled );
					s.setText( "Save" );
					
				}
				
				return true;
			}
			
			@Override
			public boolean busy(){
				
				s.setTouchable( Touchable.disabled );
				s.setText( "Busy" );

				return true;
			}
			
			@Override
			public void assigned( PGameTable xGameTable ){

				clearDamageLabels();
				
				mCurrentPlayer = xGameTable.getCurrentPlayer();
				
				mPlayerPane.show( xGameTable.getCurrentPlayer() );
				
				mUnitDetails.hide();
				mBuildingDetails.hide();
				
				if( mCurrentPlayer instanceof RemotePlayer )
					remoteOn();
				else
					remoteOff();
				
			}
			
			@Override
			public boolean turned( PGameTable xMap ){
			
				clearDamageLabels();
				
				mCurrentPlayer = xMap.getCurrentPlayer();

				mPlayerPane.show( xMap.getCurrentPlayer() );
			
				mUnitDetails.hide();
				mBuildingDetails.hide();
				
				return true;
				
			}
			
			@Override
			public boolean tileClicked( PTile xTile, PGameTable xMap ){
				
				clearDamageLabels();
				
				mUnitDetails.hide();
				mBuildingDetails.hide();
				
				return true;
				
			}

			@Override
			public boolean unitSelected( PUnit xUnit ){
				
				clearDamageLabels();
				
				mUnit = xUnit;
				
				if( mCurrentPlayer.unitBelongs( xUnit ) )
					mUnitDetails.show( UnitDetails.TAB_INFO, xUnit );
				else
					mUnitDetails.show( UnitDetails.TAB_ONLY_INFO, xUnit );
				
				return true;
				
			}
			
			@Override
			public boolean unitHovered( PUnit xUnit ){
				return false;
			}
			
			@Override
			public boolean skillAreaSelected( PSkill xSkill, ArrayList< PTile > mTiles ){

				mSkill = xSkill;
				updateDamageLabels( mTiles );
				
				return true;
			
			}
			
			@Override
			public boolean skillCasted( PSkill xSkill ){
				
				clearDamageLabels();
				
				mUnitDetails.hide();
				
				return true;
				
			}
			
			@Override
			public boolean buildingSelected( PBuilding xBuilding ){
				
				clearDamageLabels();
				
				if( mCurrentPlayer.belongs( xBuilding ) )
					mBuildingDetails.show( BuildingDetails.TAB_INFO, xBuilding );
				else
					mBuildingDetails.show( BuildingDetails.TAB_INFO_ONLY, xBuilding );
				
				return true;
				
			}
			
			@Override
			public boolean payed( PCost xCost ){
				
				clearDamageLabels();
				
				mPlayerPane.show( mCurrentPlayer );
				
				return true;
				
			}
			
			@Override
			public boolean canceled(){
				
				clearDamageLabels();
				
				mUnitDetails.hide();
				mBuildingDetails.hide();
				
				return true;
				
			}
			
		};
		
	}
	
	private void clearDamageLabels(){
		
		for( DamageBox damageBox: mDamageBoxes )
			damageBox.clear();
		mDamageBoxes.clear();
		
	}
	
	private void updateDamageLabels( ArrayList< PTile > xTiles ){

		clearDamageLabels();
		
		ArrayList< LivingPlayable< ? > > livings = new ArrayList< LivingPlayable< ? > >();
		
		for( PTile tile: xTiles ){
			
			if( tile.hasPlayable() ){
				
				if( tile.getPlayable() instanceof LivingPlayable<?> )
					livings.add( ( LivingPlayable< ? > ) tile.getPlayable() );
				
			}
			
		}
		
		for( Affect affect: mSkill.getAffects() ){
			
			if( affect.getStat().equals( "@HIT_POINTS" ) ){
				
				for( LivingPlayable< ? > living: livings ){
				
					PAffect a = new PAffect( affect, mEngine );
					a.init( mSkill, mUnit, living );

					System.out.println( "When: " + a.getWhen() );
					
					if( a.getAffectValue().equals( "0" ) || a.getWhen() != 1 )
						continue;
					
					DamageBox b = new DamageBox( mTable, skin );
					b.setDamage( Integer.parseInt( a.getAffectValue() ) );
					b.setLiving( living );
					b.show();
					
					mDamageBoxes.add( b );
					
				}
				
			}
			
		}
		
		
	}
	
	int i;
	
	public void update(){
		
		for( i = 0; i < mDamageBoxes.size(); i++ )
			mDamageBoxes.get( i ).update();
		
	}
	
	public InputProcessor getInputProcessor(){
		
		return mMainStage;
		
	}
	
	public void render(){

		mMainStage.act( Gdx.graphics.getDeltaTime() );
		mMainStage.draw();
		
	}
	
	public void log( String xMsg ){
		
	}
	
}
