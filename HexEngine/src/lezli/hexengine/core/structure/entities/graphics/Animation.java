package lezli.hexengine.core.structure.entities.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * This object holds data for an animation.
 * @author Lezli
 *
 */
public class Animation extends GraphicsElement{

	private ArrayList< Frame > mFrames;
	
	private int mXstart,
				mYstart;
	
	private long mDuration;

	public Animation( Element xElement ){
		
		super( xElement );
		
	}
	
	public Animation( String xFileName ){
		
		super( xFileName );
		
	}
	
	public int getXStart(){
		
		return mXstart;
		
	}
	
	public int getYStart(){
		
		return mYstart;
		
	}
	
	public long getDuration(){
		
		return mDuration;
		
	}
	
	/**
	 * Get all the <b>Frame</b>s from this <b>Animation</b>.
	 * @return
	 * A list of <b>Frame</b>s.
	 */
	public ArrayList< Frame > getFrames(){
		
		return mFrames;
	
	}
	
	private void addFrame( Frame frame ){
		
		mFrames.add( frame );
		log( "New frame added (" + mFrames.size() + ")" );
		
	}

	private void setParameters( int xXstart, int xYstart, long xDuration ){
		
		mXstart = xXstart;
		mYstart = xYstart;
		mDuration = xDuration;
		
		log( "Parameters set to (" + mXstart + ";" + mYstart + ";" + mDuration + ")" );
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		int x_start = xElement.getInt( "x_start" );
		int y_start = xElement.getInt( "y_start" );
		long duration = Long.parseLong( xElement.getAttribute( "duration" ) );
		
		setParameters( x_start, y_start, duration );
		
		int frameCount = 0;
		for( frameCount = 0; frameCount < xElement.getChildCount(); frameCount++ ){
			
			addFrame( new Frame( xElement.getChild( frameCount ) ) );
			
		}
		
	}

	@Override
	protected void init(){

		super.init();
		
		mFrames = new ArrayList< Frame >();
		
	}


}
