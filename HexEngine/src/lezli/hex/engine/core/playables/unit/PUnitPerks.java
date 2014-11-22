package lezli.hex.engine.core.playables.unit;

import lezli.hex.engine.core.HexEngine;
import lezli.hex.engine.core.playables.Playable;
import lezli.hex.engine.core.structure.entities.unit.UnitPerks;

public class PUnitPerks extends Playable< UnitPerks >{

	private UnitPerks mUnitPerks;
	
	public PUnitPerks( UnitPerks xEntity, HexEngine xEngine ){
		
		super( xEntity, xEngine );
		
		mUnitPerks = xEntity;	
		
	}
	
	public boolean isAllowed( String xTile ){

		if( mUnitPerks.getRule() == UnitPerks.ALLOW_ALL ){
		
			if( mUnitPerks.getDisabledTiles().contains( xTile ) )
				return false;
			
			return true;
			
		} else {
			
			if( mUnitPerks.getEnabledTiles().contains( xTile ) )
				return true;
			
			return false;
			
		}
		
	}

	@Override
	public void update(){
	}

	@Override
	public void turn(){
	}

}
