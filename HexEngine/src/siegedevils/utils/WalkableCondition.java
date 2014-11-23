package siegedevils.utils;

import lezli.hex.engine.core.playables.map.PTileWalkableCondition;
import lezli.hex.engine.core.playables.map.tile.PTile;

public class WalkableCondition implements PTileWalkableCondition{

	@Override
	public boolean isTileWalkable( PTile xTile ){

		if( !xTile.canWalk() )
			return false;
		
		return true;
	
	}

}
