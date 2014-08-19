package lezli.hex.engine.core.structure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.badlogic.gdx.files.FileHandle;

public class Values {

	public static final String AI_TIMEOUT = "AI_TIMEOUT";
	
	private HashMap< String, String > mValues;
	
	public Values( FileHandle xFileHandle ){
		
		mValues = new HashMap< String, String >();
		
		BufferedReader re = new BufferedReader( new InputStreamReader( xFileHandle.read() ) );

		String line;
		
		try {
			
			while( ( line = re.readLine() ) != null ){
		
				StringTokenizer tokenizer = new StringTokenizer( line, "=" );
				
				mValues.put( tokenizer.nextToken(), tokenizer.nextToken() );
				
			}
		
		}catch( IOException e ){

			e.printStackTrace();
		
		}
		
	}
	
	public String getString( String xId ){
		
		return mValues.get( xId );
		
	}
	
	public long getLong( String xId ){
		
		return Long.parseLong( getString( xId ) );
		
	}
	
}
