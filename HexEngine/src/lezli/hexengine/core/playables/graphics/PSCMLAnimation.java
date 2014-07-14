package lezli.hexengine.core.playables.graphics;

import lezli.hexengine.core.structure.entities.graphics.GraphicsElement;

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
