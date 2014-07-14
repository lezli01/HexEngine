package lezli.hexengine.core.playables.common;

import java.io.IOException;

import com.badlogic.gdx.utils.XmlWriter;

import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.structure.entities.common.Resource;

public class PResource extends GraphicalPlayable< Resource >{

	private int mQuantity;
	
	public PResource( Resource xEntity ){
		
		super( xEntity );
		
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
