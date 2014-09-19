package siegedevils.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SavedGames {

	private static SavedGames instance = null;
	
	public static SavedGames getInstance(){
		
		if( instance == null )
			instance = new SavedGames();
		
		instance.refresh();
		
		return instance;
		
	}
	
	private static final String PATH = "siegedevils/saves/";
	private static final String EXT = ".sav";
	private static final String PREFIX = "SAVE_";
	
	private ArrayList< String > mSavedGames;
	
	private SavedGames(){
	
		refresh();
		
	}
	
	private void refresh(){
		
		if( !Gdx.files.local( PATH ).exists() )
			Gdx.files.local( PATH ).mkdirs();
		
		mSavedGames = new ArrayList< String >();
		
		for( FileHandle fileHandle:	Gdx.files.local( PATH ).list() )
			mSavedGames.add( fileHandle.file().getName() );

	}
	
	public ArrayList< String > getSavedGames(){
		
		return mSavedGames;
		
	}
	
	public String getNewName(){
		
		int i = 0;
		String name = PREFIX + "0" + EXT;
		
		while( mSavedGames.contains( name ) ){
			
			i++;
			name = PREFIX + i + EXT;
			
		}
		
		return name;
		
	}
	
	public String createPath( String xFileName ){
		
		return PATH + xFileName;
		
	}
	
}
