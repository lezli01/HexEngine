package lezli.hex.engine.core.gametable.script;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import lezli.hex.engine.core.HexEngine;

import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class IntScript extends Script{

	private LuaClosure mLua;
	
	public IntScript( String xScript, HexEngine xEngine ){
		
		super( xEngine );
		
		try {
			
			Prototype prototype = LuaC.compile( new ByteArrayInputStream( engine().entitiesHolder().getScripts().get( xScript ) ), "script" );
			mLua = new LuaClosure( prototype, JsePlatform.standardGlobals() );
			
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		
	}
	
	public void rawset( String xID, Object xObject ){
		
		mLua.getfenv().rawset( xID, CoerceJavaToLua.coerce( xObject) );
		
	}
	
	public int call(){
		
		return mLua.call().checkint();
		
	}
	
}
