package lezli.hexengine.core.structure.entities.stat;

import lezli.hexengine.core.structure.entities.EntityHasher;

import com.badlogic.gdx.utils.XmlReader.Element;

public class StatEntries extends EntityHasher< StatReg >{

	public StatEntries( Element xElement ){
		
		super( xElement );
		
	}
	
	@Override
	public StatReg get( String xId ){

		for( StatReg reg: getAll() ){

			if( reg.getStat().equals( xId ) )
				return reg;
			
		}
		
		return super.get( xId );
		
	}

}
