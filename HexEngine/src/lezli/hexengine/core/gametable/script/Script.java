package lezli.hexengine.core.gametable.script;

import lezli.hexengine.core.HexEngine;

public class Script {

	private HexEngine mEngine;
	
	public Script( HexEngine xEngine ){
		
		mEngine = xEngine;
		
	}
	
	protected HexEngine engine(){
		
		return mEngine;
		
	}
	
}
