package lezli.hex.engine.core.structure.entities.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;
import lezli.hex.engine.core.structure.entities.skill.affect.Affect;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Skill extends GraphicalEntity{

	public static class Affects{

		public static final HashMap< String, Integer > AFFECTS = new HashMap< String, Integer >();
		
		public static final int ENEMY = 1;
		public static final int FRIENDLY = 2;
		public static final int SELF = 3;
		public static final int BUILDING = 4;
		
		static{
			
			AFFECTS.put( "ENEMY", 		ENEMY );
			AFFECTS.put( "SELF", 		SELF );
			AFFECTS.put( "FRIENDLY", 	FRIENDLY );
			AFFECTS.put( "BUILDING",	BUILDING );
			
		}
		
	}
	
	private int mRange;
	private int mArea;
	
	private static HashMap< String, Affect > AFFECTS;
	private ArrayList< String > mAffects;
	
	private String mUnitAnimation;
	private float mSpeed;
	private float mProjectileWidth;
	private float mProjectileHeight;
	private int mCooldown;
	
	private ArrayList< Integer > mAffectsOn;
	
	public Skill( String xFileName ){
		
		super( xFileName );
	
	}
	
	public int getRange(){
		
		return  mRange;
		
	}
	
	public int getArea(){
		
		return mArea;
		
	}
	
	public ArrayList< Affect > getAffects(){
		
		ArrayList< Affect > affects = new ArrayList< Affect >();
		
		for( String affectId: mAffects )
			affects.add( AFFECTS.get( affectId ) );
		
		return affects;
		
	}
	
	public Affect getAffect( String xID ){
		
		return AFFECTS.get( xID );
		
	}
	
	public float getSpeed(){
		
		return mSpeed;
		
	}
	
	public float getProjectileWidth(){
		
		return mProjectileWidth;
		
	}
	
	public float getProjectileHeight(){
		
		return mProjectileHeight;
		
	}
	
	public String getUnitAnimation(){
		
		return mUnitAnimation;
		
	}
	
	public ArrayList< Integer > getAffectsOn(){
		
		return mAffectsOn;
		
	}
	
	private void addNewAffect( Affect xAffect ){
		
		AFFECTS.put( xAffect.getID(), xAffect );
		log( "Affect added (" + xAffect + ")" );
		
	}
	
	private void setRange( int xRange ){
		
		mRange = xRange;
		log( "Range set (" + mRange + ")" );
		
	}

	private void setArea( int xArea ){
		
		mArea = xArea;
		log( "Area set (" + mArea + ")" );
		
	}
	
	private void setSpeed( float xSpeed ){
		
		mSpeed = xSpeed;
		log( "Speed set (" + mSpeed + ")" );
		
	}
	
	private void setProjectileWidth( float xWidth ){
		
		mProjectileWidth = xWidth;
		log( "Projectile width set (" + mProjectileWidth +")" );
		
	}
	
	private void setProjectileHeight( float xHeight ){
		
		mProjectileHeight = xHeight;
		log( "Projectile height set (" + mProjectileHeight + ")" );
		
	}
	
	private void setUnitAnimation( String xAnim ){
		
		mUnitAnimation = xAnim;
		log( "Animations set (" + mUnitAnimation + ")" );
		
	}
	
	private void setCooldown( int xCooldown ){
		
		mCooldown = xCooldown;
		log( "Cooldown set (" + mCooldown + ")" );
		
	}
	
	public int getCooldown(){
		
		return mCooldown;
		
	}
	
	@Override
	protected void parse( Element xElement ){
	
		super.parse( xElement );
	
		try{
		
		setRange( xElement.getInt( "range" ) );
		setArea( xElement.getInt( "area" ) );
		setSpeed( xElement.getFloat( "speed" ) );
		setProjectileWidth( xElement.getFloat( "projectile_width" ) );
		setProjectileHeight( xElement.getFloat( "projectile_height" ) );
		setUnitAnimation( xElement.get( "animation" ) );
		setCooldown( xElement.getInt( "cooldown" ) );
		
		StringTokenizer affectsTokenizer = new StringTokenizer( xElement.get( "affects_on" ), "|" );
		while( affectsTokenizer.hasMoreElements() ){
			mAffectsOn.add( Affects.AFFECTS.get( affectsTokenizer.nextToken() ) );
		}
		
		}catch( Exception e ){
			
			log( e.toString() );
			log( "Error parsing skill." );
			
		}
		
		int affectCount = 0;
		for( affectCount = 0; affectCount < xElement.getChildCount(); affectCount++ ){
		
			Element element = xElement.getChild( affectCount );
			mAffects.add( element.get( "id" ) );

			//If declared Affect then parse it
			
			try{
				
				if( !AFFECTS.containsKey( element.get( "id" ) ) ){
					addNewAffect( new Affect( xElement.getChild( affectCount ) ) );
				}
				
			}catch( Exception e ){
				
				log( "Affect reflects already declared." );
				
			}
			

			
		}
		
	}

	@Override
	protected void init() {
		
		if( AFFECTS == null )
			AFFECTS = new HashMap< String, Affect >();
		
		mAffects = new ArrayList< String >();
		mAffectsOn = new ArrayList< Integer >();
		
	}

}
