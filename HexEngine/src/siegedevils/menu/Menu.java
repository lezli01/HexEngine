package siegedevils.menu;

import lezli.hex.enginex.ui.MetroUI;
import lezli.hex.enginex.ui.metro.elements.MetroButton;
import lezli.hex.enginex.ui.metro.elements.MetroScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu extends MetroUI{

	private int PADDING = 25;
	private int COL = 1;
	private int ROW = 3;
	
	private static final String SCR_MAIN 		= "SCR_MAIN";
	private static final String SCR_SKIRMISH 	= "SCR_SKIRMISH";
	private static final String SCR_LOAD 		= "SCR_LOAD";
	
	private String mDataPath;
	
	public Menu(){
	
		mDataPath = "hex.engine/";
		
		if( Gdx.app.getType() == ApplicationType.Desktop )
			mDataPath = "./bin/" + mDataPath;
		
		initScreens();
		
	}
	
	private void initScreens(){
		
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
		
		//should make list of skirmish gametable ids
		
		addScreen( scr_skirmish );
		
		/*
		 * LOAD SCREEN
		 */
		MetroScreen scr_load = new MetroScreen( SCR_LOAD, COL, ROW, PADDING );
		
		to_scr_main_btn = new MetroButton( "to_src_main", "Back" );
		to_scr_main_btn.addListener( new ClickListener(){

			@Override
			public void clicked( InputEvent event, float x, float y ){
			
				setActive( SCR_MAIN );
				
			}
			
		});
		
		scr_load.add( to_scr_main_btn );
		
		addScreen( scr_load );
		
		setActive( SCR_MAIN );
		
	}
	
}