package lezli.hex.engine.core.structure.entities.text;

import lezli.hex.engine.core.structure.entities.EntityHasher;

public class Texts extends EntityHasher< Text >{
	
	public Texts( String xFileName ){
		
		super( xFileName );
		
	}

	public Text findText( TextIdentifier xTextIdentifier ){
		
		return get( xTextIdentifier.getSecond() );
		
	}

}
