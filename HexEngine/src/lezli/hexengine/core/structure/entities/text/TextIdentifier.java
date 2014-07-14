package lezli.hexengine.core.structure.entities.text;

import java.util.StringTokenizer;

public class TextIdentifier {

	private String mFirst;
	private String mSecond;
	
	public TextIdentifier( String xTextToFind ){
		
		init( xTextToFind );
		
	}
	
	private void init( String xTextToFind ){
		
		StringTokenizer tokenizer = new StringTokenizer( xTextToFind, "/" );
		
		mFirst = tokenizer.nextToken();
		mSecond = tokenizer.nextToken();
		
	}
	
	public String getFirst(){
		
		return mFirst;
		
	}
	
	public String getSecond(){
		
		return mSecond;
		
	}
	
}
