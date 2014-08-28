package lezli.hex.engine.core.gametable;

import lezli.hex.engine.moddable.gametable.HEGameTable;
import lezli.hex.engine.moddable.gametable.HEGameTableFeatures;


public abstract class PGameTableController{

	private HEGameTable mGameTable;
	
	public void init( HEGameTable xGameTable ){
		
		mGameTable = xGameTable;
		
	}
	
	protected HEGameTable gametable(){
		
		return mGameTable;
		
	}
	
	protected HEGameTableFeatures features(){
		
		return gametable().getFeatures();
		
	}
	
	public boolean handleTap( float x, float y, int count, int button ){
		
		return tap( x, y, count, button );
		
	}
	
	protected abstract boolean tap( float x, float y, int count, int button );
	
}
