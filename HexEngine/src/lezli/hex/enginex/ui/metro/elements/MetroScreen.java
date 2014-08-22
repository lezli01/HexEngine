package lezli.hex.enginex.ui.metro.elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MetroScreen extends MetroElement{

	private ArrayList< ArrayList< MetroElement > > mElements;
	private int mCol, mRow, mSize;
	
	private int i, j;
	
	public MetroScreen( String xId, int xCol, int xRow ){
		
		super( xId );
		
		mCol = xCol;
		mRow = xRow;
		
		mSize = Math.min( Gdx.graphics.getWidth() / mCol, Gdx.graphics.getHeight() / mRow );
		
		mElements = new ArrayList< ArrayList< MetroElement > >();
		
	}

	public void add( MetroElement xElement ){
		
		for( i = 0; i < mCol; i++ ){
		
			if( mElements.size() < i + 1 )
				mElements.add( i, new ArrayList< MetroElement >() );
			
			ArrayList< MetroElement > column = mElements.get( i );
			
			for( j = 0; j < mRow; j++ ){
			
				if( column.size() < j + 1 ){
				
					xElement.setSize( mSize, mSize );
					xElement.setPosition( mSize * i, mSize * j );
					
					column.add( j, xElement );

					return;
					
				}
				
			}
		
		}
		
	}

	@Override
	public void draw( Batch batch, float parentAlpha ){

		super.draw( batch, parentAlpha );
	
		for( i = 0; i < mCol; i++ )
			if( i < mElements.size() )
			for( j = 0; j < mRow; j++ )
				if( j < mElements.get( i ).size() )
				mElements.get( i ).get( j ).draw( batch, parentAlpha );
		
	}
	
}
