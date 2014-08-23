package lezli.hex.engine.core.structure;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.files.FileHandle;

public class Values extends Properties{

	private static final long serialVersionUID = -3051804732977149984L;

	public static final String AI_TIMEOUT = "AI_TIMEOUT";
	
	public Values( FileHandle xFileHandle ){
		
		try {
			
			load( xFileHandle.read() );
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		
	}
	
	public long getLong( String xId ){
		
		return Long.parseLong( getProperty( xId ) );
		
	}
	
}
