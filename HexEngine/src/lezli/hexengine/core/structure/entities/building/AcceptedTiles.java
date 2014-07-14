package lezli.hexengine.core.structure.entities.building;

import lezli.hexengine.core.structure.entities.EntityHasher;
import lezli.hexengine.core.structure.entities.map.MapTile;

import com.badlogic.gdx.utils.XmlReader.Element;

public class AcceptedTiles extends EntityHasher< MapTile >{

	public AcceptedTiles( Element xElement ){
		
		super( xElement );
		
	}
	
}
