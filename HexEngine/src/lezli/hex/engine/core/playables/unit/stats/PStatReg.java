package lezli.hex.engine.core.playables.unit.stats;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.XmlWriter;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.stat.StatReg;
import lezli.hex.engine.moddable.playables.HEStatReg;

public class PStatReg extends Playable< StatReg > implements HEStatReg{

	private String mStat;
	private int mValue;
	
	public PStatReg( StatReg xEntity, HexEngine xEngine ){

		super( xEntity, xEngine );
	
		mStat = xEntity.getStat();
		mValue = xEntity.getValue();
		
	}
	
	public String getStat(){
		
		return mStat;
		
	}
	
	public String getStatName(){
		
		return engine().entitiesHolder().getCommon().getStats().get( getStat() ).getName();
		
	}
	
	public int getValue(){
		
		return mValue;
		
	}

	public void setValue( int xValue ){
		
		mValue = xValue;
		
	}
	
	public void addValue( int xValue ){
		
		mValue += xValue;
		
	}
	
	@Override
	public void save( XmlWriter xmlWriter ) throws IOException{
	
		super.save( xmlWriter );
		
		xmlWriter.attribute( "stat", getStat() ).
				  attribute( "value", getValue() );
	
	}

	@Override
	public void update(){
		
	}

	@Override
	public void turn(){
		
	}

	@Override
	public SpriteDrawable getLargeIcon() {

		return null;
	
	}

}
