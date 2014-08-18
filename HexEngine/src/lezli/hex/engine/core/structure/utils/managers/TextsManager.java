package lezli.hex.engine.core.structure.utils.managers;

import lezli.hex.engine.core.structure.entities.text.TextIdentifier;
import lezli.hex.engine.core.structure.entities.text.Texts;

public class TextsManager extends Manager< Texts >{

	public String findText( String xTextIdentifier ){
		
		return findText( new TextIdentifier( xTextIdentifier ) );
		
	}
	
	public String findText( TextIdentifier xTextIdentifier ){
		
		return get( xTextIdentifier.getFirst() ).
				findText( xTextIdentifier ).getText();
		
	}
	
	@Override
	protected void init(){
		
	}

}
