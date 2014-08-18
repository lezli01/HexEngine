package lezli.hex.engine.core.playables.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PShearedSprite extends Sprite{

	private float sx, sy;
	
	public PShearedSprite( TextureRegion mTexRegion ){
		
		super( mTexRegion );
		
		sx = sy = 0;
		
	}
	
	public void shear( float x, float y ){
		
		sx = x;
		sy = y;
		
	}
	
	@Override
	public float[] getVertices(){

		float[] verts = super.getVertices();
		
		verts[ 0 ] += sx;
		verts[ 1 ] += sy;
		verts[ 15 ] += sx;
		verts[ 16 ] += sy;
		
		return verts;
	
	}

	@Override
	public void draw( Batch batch ){

		setColor( batch.getColor() );
		
		super.draw( batch );
	
	}
	
}
