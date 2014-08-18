package lezli.hex.engine.core.gametable.script;

import lezli.hex.engine.core.HexEngine;

public class Script {

	private HexEngine mEngine;
	
	public Script( HexEngine xEngine ){
		
		mEngine = xEngine;
		
	}
	
	protected HexEngine engine(){
		
		return mEngine;
		
	}
	
}
