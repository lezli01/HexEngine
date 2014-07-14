package lezli.hexengine.core.structure.entities.building;

import com.badlogic.gdx.utils.XmlReader.Element;

public class BuildingReg extends ProduceEntity{

	private String mBuilding;
	
	public BuildingReg( Element xElement ){
		
		super( xElement );
	
	}
	
	public String getBuilding(){
		
		return mBuilding;
		
	}

	@Override
	public String toString() {
	
		return mBuilding;
		
	}

	@Override
	protected void parse( Element xElement ){

		super.parse(xElement);
	
		setBuilding( xElement.getAttribute( "building" ) );
		
	}
	
	@Override
	protected void init() {
		
	}

	private void setBuilding( String xBuilding ){
		
		mBuilding = xBuilding;
		log( "Building set to (" + mBuilding + ")" );
		
	}
	
}
