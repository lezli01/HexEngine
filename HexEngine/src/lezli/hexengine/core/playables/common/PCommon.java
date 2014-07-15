package lezli.hexengine.core.playables.common;

import java.util.HashMap;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.structure.entities.common.Resource;
import lezli.hexengine.core.structure.entities.common.Stat;
import lezli.hexengine.core.structure.utils.Common;

public class PCommon{

	private HashMap< String, PStat > mStats;
	private HashMap< String, PResource > mResource;
	
	public PCommon( Common xCommon, HexEngine xEngine ){
		
		mStats = new HashMap< String, PStat >();
		for( Stat stat: xCommon.getStats().getAll() )
			mStats.put( stat.getID(), new PStat( stat, xEngine ) );
		
		mResource = new HashMap< String, PResource >();
		for( Resource resource: xCommon.getResources().getAll() )
			mResource.put( resource.getID(), new PResource( resource, xEngine ) );
		
	}
	
	public HashMap< String, PStat > getStats(){
		
		return mStats;
		
	}
	
	public HashMap< String, PResource > getResources(){
		
		return mResource;
		
	}
	
}
