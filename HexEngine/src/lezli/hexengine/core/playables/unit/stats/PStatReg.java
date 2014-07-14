package lezli.hexengine.core.playables.unit.stats;

import java.io.IOException;

import com.badlogic.gdx.utils.XmlWriter;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.Playable;
import lezli.hexengine.core.structure.entities.stat.StatReg;

public class PStatReg extends Playable< StatReg >{

	private String mStat;
	private int mValue;
	
	public PStatReg( StatReg xEntity ){

		super( xEntity );
	
		mStat = xEntity.getStat();
		mValue = xEntity.getValue();
		
	}
	
	public String getStat(){
		
		return mStat;
		
	}
	
	public String getStatName(){
		
		return HexEngine.EntitiesHolder.getCommon().getStats().get( getStat() ).getName();
		
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

}
