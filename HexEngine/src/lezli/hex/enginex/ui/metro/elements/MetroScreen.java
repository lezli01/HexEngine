package lezli.hex.enginex.ui.metro.elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MetroScreen extends MetroElement{

	private ArrayList< ArrayList< MetroElement > > mElements;
	private int mCol, mRow, mSize, mPadding;
	
	private int i, j;
	
	public MetroScreen( String xId, int xCol, int xRow, int xPadding ){
		
		super( xId );
		
		mElements = new ArrayList< ArrayList< MetroElement > >();

		mCol = xCol;
		mRow = xRow;
		mPadding = xPadding;
		mSize = Math.min( Gdx.graphics.getWidth() / mCol, Gdx.graphics.getHeight() / mRow ) - mPadding;
		
		setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		
		setTouchable( Touchable.enabled );
		
	}

	@Override
	public void setSize( float width, float height ){

		super.setSize( width, height );
	
		mSize = ( int ) Math.min( width / mCol, height / mRow ) - mPadding;
		
		for( i = 0; i < mElements.size(); i++ ){
			
			ArrayList< MetroElement > column = mElements.get( i );
			
			for( j = 0; j < column.size(); j++ ){

				MetroElement element = column.get( j );
				
				element.setSize( mSize, mSize );
				element.setPosition( mSize * i + mPadding / 2 * ( i + 1 ), getHeight() - mSize * ( j + 1 ) - mPadding / 2 * ( j + 1 ) );

			}
		
		}
		
	}
	
	public void add( MetroElement xElement ){
		
		for( i = 0; i <= mElements.size(); i++ ){
		
			if( mElements.size() < i + 1 )
				mElements.add( i, new ArrayList< MetroElement >() );
			
			ArrayList< MetroElement > column = mElements.get( i );
			
			for( j = 0; j < mRow; j++ ){
			
				if( column.size() < j + 1 ){
				
					xElement.setSize( mSize, mSize );
					xElement.setPosition( mSize * i + mPadding / 2 * ( i + 1 ), getHeight() - mSize * ( j + 1 ) - mPadding / 2 * ( j + 1 ) );

					super.addActor( xElement );
					column.add( j, xElement );

					return;
					
				}
				
			}
		
		}
		
	}

}
