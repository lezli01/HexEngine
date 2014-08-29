package lezli.hex.enginex.utils.log;

import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import lezli.hex.engine.core.playables.Logger;

public class FileLogger implements Logger{

	private FileHandle mFile;
	private PrintWriter mWriter;
	
	public FileLogger( String xFileName ){
		
		try {
			
			if( Gdx.app.getType() == ApplicationType.Desktop )
				mFile = Gdx.files.internal( xFileName );
			else
				mFile = Gdx.files.local( xFileName );
			
			mFile.file().createNewFile();
		
			mWriter = new PrintWriter( mFile.file() );
			
		}catch( IOException e ){

			e.printStackTrace();
		
		}
		
	}
	
	@Override
	public void log( String xMsg ){
		
		mWriter.println( xMsg );
		mWriter.flush();
		
	}

	@Override
	public void log( String xMsg, int xDepth ){
		
		while( ( xDepth-- ) > 0 )
			xMsg = "\t" + xMsg;
		
		log( xMsg );
		
	}

}
