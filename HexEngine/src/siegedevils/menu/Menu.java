package siegedevils.menu;

import java.util.ArrayList;

import lezli.hex.enginex.ui.MetroUI;
import lezli.hex.enginex.ui.metro.elements.MetroButton;
import lezli.hex.enginex.ui.metro.elements.MetroScreen;
import siegedevils.utils.SavedGames;
import siegedevils.utils.Skirmish;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu extends MetroUI{

	private int PADDING = 25;
	private int COL = 1;
	private int ROW = 3;
	
	public static final String SCR_BLANK		= "SCR_BLANK";
	public static final String SCR_MAIN 		= "SCR_MAIN";
	public static final String SCR_SKIRMISH 	= "SCR_SKIRMISH";
	public static final String SCR_LOAD 		= "SCR_LOAD";
	public static final String SCR_SAVE			= "SCR_SAVE";
	public static final String SCR_INGAME		= "SCR_INGAME";
	
	private String mDataPath;
	private ArrayList< MenuListener > mListeners;
	
	public Menu(){
	
		mDataPath = "hex.engine/";
		
		if( Gdx.app.getType() == ApplicationType.Desktop )
			mDataPath = "./bin/" + mDataPath;
		
		mListeners = new ArrayList< MenuListener >();
		
		initScreens();
		
		
	}
	
	public void addListener( MenuListener xListener ){
		
		mListeners.add( xListener );
		
	}
	
	private void initScreens(){
		
		/*
		 * BLANK
		 */
		MetroScreen scr_blank = new MetroScreen( SCR_BLANK, COL, ROW, PADDING );
		addScreen( scr_blank );
		
		/*
		 * MAIN MENU
		 */
		MetroScreen scr_main = new MetroScreen( SCR_MAIN, COL, ROW, PADDING );
		
		MetroButton campaign_btn = new MetroButton( "campaign_btn", "Campaign" );
		MetroButton skirmish_btn = new MetroButton( "skirmish_btn", "Skirmish" );
		MetroButton quit_btn = new MetroButton( "quit_btn", "Quit" );
		MetroButton load_btn = new MetroButton( "load_btn", "Load" );

		skirmish_btn.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				setActive( SCR_SKIRMISH );
				
			}
			
		});
		
		quit_btn.addListener( new ClickListener(){

			@Override
			public void clicked( InputEvent event, float x, float y ){

				Gdx.app.exit();
				
			}
			
		});

		load_btn.addListener( new ClickListener(){

			@Override
			public void clicked( InputEvent event, float x, float y ){

				refreshLoadScreen();
				setActive( SCR_LOAD );
				
			}
			
		});
		
		scr_main.add( campaign_btn );
		scr_main.add( skirmish_btn );
		scr_main.add( quit_btn );
		scr_main.add( load_btn );
		
		addScreen( scr_main );
		
		/*
		 * SKIRMISH SCREEN
		 */
		MetroScreen scr_skirmish = new MetroScreen( SCR_SKIRMISH, COL, ROW, PADDING );
		
		MetroButton to_scr_main_btn = new MetroButton( "to_src_main", "Back" );
		to_scr_main_btn.addListener( new ClickListener(){

			@Override
			public void clicked( InputEvent event, float x, float y ){
			
				setActive( SCR_MAIN );
				
			}
			
		});

		scr_skirmish.add( to_scr_main_btn );
		
		for( String mapName: Skirmish.getInstance().getAll().keySet() ){
			
			final MetroButton mapButton = new MetroButton( mapName + "_btn", mapName );
			
			mapButton.addListener( new ClickListener(){
			
				public void clicked( InputEvent event, float x, float y ) {
					
					for( MenuListener listener: mListeners )
						listener.mapStarted( Skirmish.getInstance().getAll().get( mapButton.getCaption() ) );
					
					setActive( SCR_BLANK );
					
				};
				
			});
			
			scr_skirmish.add( mapButton );
			
		}
		
		//should make list of skirmish gametable ids
		
		addScreen( scr_skirmish );
		
		/*
		 * LOAD SCREEN
		 */
		refreshLoadScreen();
		
		/*
		 * INGAME SCREEN
		 */
		MetroScreen scr_ingame = new MetroScreen( SCR_INGAME, COL, ROW, PADDING );
		scr_ingame.setBackground( "alpha-background" );
		scr_ingame.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		MetroButton btn_resume = new MetroButton( "resume_btn", "Resume" );
		btn_resume.addListener( new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {

				setActive( SCR_BLANK );
			
			}
			
		});
		
		MetroButton btn_save = new MetroButton( "save_btn", "Save" );
		
		btn_save.addListener( new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {

				refreshSaveScreen();
				setActive( SCR_SAVE );
			
			}
			
		});
		
		MetroButton btn_end = new MetroButton( "btn_end", "End Game" );
		btn_end.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				for( MenuListener listener: mListeners )
					listener.mapEnded();
				
			}
			
		});
		
		scr_ingame.add( btn_resume );
		scr_ingame.add( btn_save );
		scr_ingame.add( btn_end );

		addScreen( scr_ingame );

		/*
		 * SAVE SCREEN
		 */
		refreshSaveScreen();
		
		setActive( SCR_MAIN );
		
	}
	
	private void refreshLoadScreen(){
		
		MetroScreen scr_load = new MetroScreen( SCR_LOAD, COL, ROW, PADDING );
		
		MetroButton to_scr_main_btn = new MetroButton( "to_src_main", "Back" );
		to_scr_main_btn.addListener( new ClickListener(){

			@Override
			public void clicked( InputEvent event, float x, float y ){
			
				setActive( SCR_MAIN );
				
			}
			
		});
		
		scr_load.add( to_scr_main_btn );
		addLoadGames( scr_load );
		
		addScreen( scr_load );

		
	}
	
	private void refreshSaveScreen(){
		
		MetroScreen scr_save = new MetroScreen( SCR_SAVE, COL, ROW, PADDING );
		scr_save.setBackground( "alpha-background" );
		scr_save.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		MetroButton btn_back = new MetroButton( "btn_back", "Back" );
		btn_back.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){

				setActive( SCR_INGAME );
				
			}
			
		});

		MetroButton btn_save = new MetroButton( "btn_save", "New save" );
		btn_save.addListener( new ClickListener(){
			
			@Override
			public void clicked( InputEvent event, float x, float y ){
			
				save( null );
				setActive( SCR_INGAME );
				
			}
			
		});
		
		scr_save.add( btn_back );
		scr_save.add( btn_save );
		addSaveGames( scr_save );
		
		addScreen( scr_save );
		
	}
	
	private void addSaveGames( MetroScreen xScreen ){
		
		for( final String fileName: SavedGames.getInstance().getSavedGames() ){
			
			MetroButton btn_saved = new MetroButton( fileName, fileName );
			
			btn_saved.addListener( new ClickListener(){
				
				@Override
				public void clicked( InputEvent event, float x, float y ){

					save( fileName );
					
				}
				
			});
			
			xScreen.add( btn_saved );
			
		}
		
	}
	
	private void addLoadGames( MetroScreen xScreen ){
		
		for( final String fileName: SavedGames.getInstance().getSavedGames() ){
			
			MetroButton btn_saved = new MetroButton( fileName, fileName );
			
			btn_saved.addListener( new ClickListener(){
				
				@Override
				public void clicked( InputEvent event, float x, float y ){

					for( MenuListener listener: mListeners )
						listener.mapStarted( SavedGames.getInstance().createPath( fileName ) );
					
					setActive( SCR_BLANK );
					
				}
				
			});
			
			xScreen.add( btn_saved );
			
		}
		
	}
	
	private void save( String xFileName ){
		
		if( xFileName == null ){
			
			xFileName = SavedGames.getInstance().getNewName();
			
		}

		for( MenuListener listener: mListeners )
			listener.save( SavedGames.getInstance().createPath( xFileName ) );

	}
	
}
