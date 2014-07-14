package lezli.hexengine.core.structure.entities.graphics;

import java.util.ArrayList;

import lezli.hexengine.core.structure.entities.Entity;

import com.badlogic.gdx.utils.XmlReader.Element;

public abstract class GraphicsElement extends Entity{

	public static String TYPE_SPRITE = "Sprite";
	public static String TYPE_ANIMATION = "Animation";
	public static String TYPE_SCML_ANIMATION = "SCMLAnimation";
	
	private ArrayList< String > mParticles;
	private ArrayList< String > mSoundEffects;
	
	private String src;
	
	public GraphicsElement( Element xElement ){
		
		super( xElement );
		
	}
	
	public GraphicsElement( String xFileName ){
		
		super( xFileName );
		
		src = xFileName;
		
	}
	
	public void progress( Element xGraphicsElement ){
		
		for( Element particle: xGraphicsElement.getChildrenByName( "Particle" ) )
			addParticle( particle.getAttribute( "file" ) );
		
		for( Element sfx: xGraphicsElement.getChildrenByName( "Sfx" ) )
			addSoundEffect( sfx.getAttribute( "file" ) );
		
	}

	public void addParticle( String xFileName ){
		
		mParticles.add( xFileName );
		
	}
	
	public ArrayList< String > getParticles(){
		
		return mParticles;
		
	}
	
	public void addSoundEffect( String xFileName ){
		
		mSoundEffects.add( xFileName );
		
	}
	
	public ArrayList< String > getSoundEffects(){
		
		return mSoundEffects;
		
	}
	
	public String getSrc(){
		
		return src;
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse( xElement );
	
		try{
			src = xElement.get( "src" );
		}catch( Exception e ){
			
		}
	}
	
	@Override
	protected void init(){

		mParticles = new ArrayList< String >();
		mSoundEffects = new ArrayList< String >();
		
	}
	
}
