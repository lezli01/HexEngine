package lezli.hex.engine.core.structure.entities.building;

import lezli.hex.engine.core.structure.entities.EntityHasher;
import lezli.hex.engine.core.structure.entities.map.MapTile;

import com.badlogic.gdx.utils.XmlReader.Element;

public class AcceptedTiles extends EntityHasher< MapTile >{

	public AcceptedTiles( Element xElement ){
		
		super( xElement );
		
	}
	
}
