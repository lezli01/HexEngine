package lezli.hex.engine.core.structure.entities.text;

import lezli.hex.engine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Text extends Entity{

	private String mText;
	
	public Text( Element xElement ){
		
		super( xElement );

	}

	@Override
	protected void parse(Element xElement) {

		super.parse(xElement);
	
		mText = xElement.getText();
		log( "Text (" + mText + ")" );
		
	}
	
	public String getText(){
		
		return mText;
		
	}
	
	@Override
	protected void init() {
		
	}
	
}
