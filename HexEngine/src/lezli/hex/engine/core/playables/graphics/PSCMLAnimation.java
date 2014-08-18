package lezli.hex.engine.core.playables.graphics;

import lezli.hex.engine.core.structure.entities.graphics.GraphicsElement;

public class PSCMLAnimation extends PGraphicsElement{

	private String mSrc;
	
	public PSCMLAnimation( GraphicsElement xEntity ){
		
		super( xEntity );
		
		mSrc = xEntity.getSrc();
		
	}
	
	public String getSrc(){
		
		return mSrc;
		
	}

}
