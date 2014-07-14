package lezli.hexengine.core.structure.entities.text;

import lezli.hexengine.core.structure.entities.EntityHasher;

public class Texts extends EntityHasher< Text >{
	
	public Texts( String xFileName ){
		
		super( xFileName );
		
	}

	public Text findText( TextIdentifier xTextIdentifier ){
		
		return get( xTextIdentifier.getSecond() );
		
	}

}
