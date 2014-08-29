package lezli.hex.engine.core.playables.common;

import java.io.IOException;

import com.badlogic.gdx.utils.XmlWriter;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.graphics.PGraphicalPlayable;
import lezli.hex.engine.core.structure.entities.common.Resource;

public class PResource extends PGraphicalPlayable< Resource >{

	private int mQuantity;
	
	public PResource( Resource xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mQuantity = 0;
		
	}

	public int getQuantity(){
		
		return mQuantity;
		
	}

	public void add( int xQuantity ){
		
		mQuantity += xQuantity;
		
	}
	
	@Override
	public void save(XmlWriter xmlWriter) throws IOException {

		super.save( xmlWriter );
	
		xmlWriter.attribute( "value", getQuantity() );
		
	}

	@Override
	public void turn(){
		
	}

	@Override
	protected void animationEnded( String xID ){
		
	}
	
}
