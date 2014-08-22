package lezli.hex.enginex.ui.metro.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MetroElement extends Table{

	private static final Skin SKIN = new Skin( Gdx.files.internal( "hex.engine/enginex/metroui/uiskin.json" ) );

	private String mId;
	
	public MetroElement( String xId ){
		
		setSkin( SKIN );
		
		mId = xId;
		
	}
	
	public String getId(){
		
		return mId;
		
	}
	
	public void setWidthPercent( float xWidth ){
		
		setWidth( ( ( float ) Gdx.graphics.getWidth() ) * xWidth );
		
	}
	
	public void setHeightPercent( float xHeight ){
		
		setHeight( ( ( float ) Gdx.graphics.getHeight() ) * xHeight );
		
	}

	protected Skin skin(){
		
		return SKIN;
		
	}
	
}
