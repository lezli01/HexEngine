package lezli.hexengine.core.structure.utils;

import lezli.hexengine.core.structure.entities.common.Races;
import lezli.hexengine.core.structure.entities.common.Resources;
import lezli.hexengine.core.structure.entities.common.Stats;
import lezli.hexengine.core.structure.entities.common.Teams;

public class Common extends Util{

	private Races mRaces;
	private Teams mTeams;
	private Resources mResources;
	private Stats mStats;

	public Common(){
		
	}

	public Races getRaces(){
		
		return mRaces;
	
	}

	public void setRaces( Races xRaces ){
		
		mRaces = xRaces;
		log( "Races set (" + mRaces + ")" );
	
	}

	public Teams getTeams(){
		
		return mTeams;
		
	}

	public void setTeams( Teams xTeams ){
		
		mTeams = xTeams;
		log( "Teams set (" + mTeams + ")" );
		
	}

	public Resources getResources(){
		
		return mResources;
	
	}

	public void setResources( Resources xResources ){
		
		mResources = xResources;
		log( "Resources set (" + mResources + ")" );
		
	}

	public Stats getStats(){
		
		return mStats;
	
	}

	public void setStats( Stats xStats ){
	
		mStats = xStats;
		log( "Stats set (" + mStats + ")" );
		
	}
	
}
