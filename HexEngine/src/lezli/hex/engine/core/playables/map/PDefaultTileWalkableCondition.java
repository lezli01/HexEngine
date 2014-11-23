package lezli.hex.engine.core.playables.map;

import lezli.hex.engine.core.playables.map.tile.PTile;

public class PDefaultTileWalkableCondition implements PTileWalkableCondition{

	@Override
	public boolean isTileWalkable( PTile xTile ){

		if( !xTile.canWalk() )
			return false;
		
		if( xTile.getPlayables().size() != 0 )
			return false;
		
		return true;
	
	}

}
