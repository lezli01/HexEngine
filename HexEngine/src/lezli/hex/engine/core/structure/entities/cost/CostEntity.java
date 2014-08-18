package lezli.hex.engine.core.structure.entities.cost;

import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;

import com.badlogic.gdx.utils.XmlReader.Element;

public class CostEntity extends GraphicalEntity{

	private Cost mCost;
	
	public CostEntity( String xFileName ){
		
		super( xFileName );
		
	}
	
	public CostEntity( Element xElement ){
		
		super( xElement );
		
	}

	public Cost getCost(){
		
		return mCost;
		
	}
	
	@Override
	protected void parse( Element xElement ){

		super.parse(xElement);
	
		setCost( new Cost( xElement.getChildByName( "Cost" ) ) );
		
	}

	@Override
	protected void init(){
		
	}

	private void setCost( Cost xCost ){
		
		mCost = xCost;
		log( "Cost set (" + mCost + ")" );
		
	}

}
