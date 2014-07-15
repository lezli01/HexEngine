package lezli.hexengine.core.gametable.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import lezli.hexengine.core.HexEngine;
import lezli.hexengine.core.playables.building.PBuilding;
import lezli.hexengine.core.playables.building.produce.PSkillUpgrade;
import lezli.hexengine.core.playables.building.produce.PStatUpgrade;
import lezli.hexengine.core.playables.building.produce.PUpgradeProduce;
import lezli.hexengine.core.playables.common.PResource;
import lezli.hexengine.core.playables.cost.CostPlayable;
import lezli.hexengine.core.playables.graphics.GraphicalPlayable;
import lezli.hexengine.core.playables.unit.PUnit;
import lezli.hexengine.core.structure.entities.common.Resource;
import lezli.hexengine.core.structure.entities.gametable.Holding;

import com.badlogic.gdx.utils.XmlWriter;


public class Player{

	private String mName;
	private ArrayList< PUnit > mUnits;
	private ArrayList< PBuilding > mBuildings;
	
	private HashMap< String, PResource > mResources;
	
	private ArrayList< PUpgradeProduce< ?, ? > > mAlreadyUpgraded;
	
	private HexEngine mEngine;
	
	public Player( String xName, HexEngine xEngine ){
		
		mEngine = xEngine;
		
		mName = xName;
		mUnits = new ArrayList< PUnit >();
		mBuildings = new ArrayList< PBuilding >();
		
		mResources = new HashMap< String, PResource >();
		
		for( Resource resource: mEngine.entitiesHolder().getCommon().getResources().getAll() )
			mResources.put( resource.getID(), new PResource( resource, engine() ) );
		
		mAlreadyUpgraded = new ArrayList< PUpgradeProduce< ?, ? > >();
		
	}
	
	protected HexEngine engine(){
		
		return mEngine;
		
	}
	
	public void save( XmlWriter xmlWriter ) throws IOException{
		
		xmlWriter.element( getClass().getSimpleName() ).
				  attribute( "name", getName() );
		
		for( PUnit unit: mUnits )
			unit.savep( xmlWriter );
		
		for( PBuilding building: mBuildings )
			building.savep( xmlWriter );
	
		for( PUpgradeProduce< ?, ? > upgrade: mAlreadyUpgraded )
			upgrade.savep( xmlWriter );
		
		for( PResource resource: mResources.values() )
			resource.savep( xmlWriter );
		
		xmlWriter.pop();
		
	}
	
	public void load( ArrayList< Holding > holding ){
		
		for( Holding h: holding ){

			if( h.getType().equals( "PUnit" ) ){
				
				PUnit unit = new PUnit( mEngine.entitiesHolder().getUnitManager().get( h.getValues().get( "id" ) ), engine() );
				unit.load( h );
				mUnits.add( unit );
				
			}
			
			if( h.getType().equals( "PBuilding" ) ){
				
				PBuilding building = new PBuilding( mEngine.entitiesHolder().getBuildingManager().get( h.getValues().get( "id" ) ), getName(), engine() );
				building.load( h );
				mBuildings.add( building );
				
			}

			if( h.getType().equals( "PResource" ) ){

				PResource resource = new PResource( mEngine.entitiesHolder().getCommon().getResources().get( h.getValues().get( "id" ) ), engine() );
				resource.add( Integer.parseInt( h.getValues().get( "value" ) ) ); 
				earn( resource );
				
			}
			
		}
		
		for( Holding h: holding ){

			if( h.getType().equals( "PStatUpgrade" ) ){
				
				PStatUpgrade u = new PStatUpgrade( mEngine.entitiesHolder().getBuildingManager().get( h.getValues().get( "building" ) ).getStatUpgrade( h.getValues().get( "id" ) ), engine() );
				u.load( h );
				mAlreadyUpgraded.add( u );
				
			}

			if( h.getType().equals( "PSkillUpgrade" ) ){
				
				PSkillUpgrade u = new PSkillUpgrade( mEngine.entitiesHolder().getBuildingManager().get( h.getValues().get( "building" ) ).getSkillUpgrade( h.getValues().get( "id" ) ), engine() );
				u.load( h );
				applyUpgrade( u );

			}
			
		}

		for( PBuilding building: mBuildings )
			building.filterPlayer( this );
		
	}

	public String getName(){
		
		return mName;
		
	}
	
	public void pay( CostPlayable<?> xPlayable ){
		
		for( Entry< String, Integer > rCost: xPlayable.getCost().getResourceCosts().entrySet() )
			mResources.get( rCost.getKey() ).add( -rCost.getValue() );
		
	}
	
	public void earn( PResource xResource ){
		
		mResources.get( xResource.getEntityID() ).add( xResource.getQuantity() );
		
	}
	
	public boolean canAfford( CostPlayable<?> xPlayable ){
		
		for( Entry< String, Integer > rCost: xPlayable.getCost().getResourceCosts().entrySet() )
			if( mResources.get( rCost.getKey() ).getQuantity() < rCost.getValue() )
				return false;
		
		return true;
		
	}
	
	public ArrayList< ? extends GraphicalPlayable<?> > getPlayables(){

		ArrayList< GraphicalPlayable<?> > allPlayables = new ArrayList< GraphicalPlayable< ? > >();
		
		allPlayables.addAll( getUnits() );
		allPlayables.addAll( getBuildings() );
		
		return allPlayables;
		
	}
	
	public void addUnit( PUnit xUnit ){
		
		for( PUpgradeProduce< ?, ? > upgrade: mAlreadyUpgraded )
			xUnit.applyUpgrade( upgrade );
		
		mUnits.add( xUnit );
		
	}
	
	public void removeUnit( PUnit xUnit ){
		
		mUnits.remove( xUnit );
		
	}
	
	public void addBuilding( PBuilding xBuilding ){
		
		xBuilding.filterPlayer( this );
		
		mBuildings.add( xBuilding );
		
	}
	
	public void removeBuilding( PBuilding xBuilding ){
		
		mBuildings.remove( xBuilding );
		
	}
	
	public ArrayList< PUnit > getUnits(){
		
		return mUnits;
		
	}
	
	public ArrayList< PUnit > getLivingUnits(){
		
		ArrayList< PUnit > units = new ArrayList< PUnit >();
		
		for( PUnit unit: getUnits() )
			if( !unit.isDead() && !unit.isDying() ) units.add( unit );
		
		return units;
		
	}
	
	public ArrayList< PBuilding > getBuildings(){
		
		return mBuildings;
		
	}
	
	public ArrayList< PBuilding > getLivingBuildings(){
		
		ArrayList< PBuilding > buildings = new ArrayList< PBuilding >();
		
		for( PBuilding building: getBuildings() )
			if( !building.isDead() && !building.isDying() ) buildings.add( building );
		
		return buildings;
		
	}
	
	public HashMap< String, PResource > getResources(){
		
		return mResources;
		
	}
	
	public boolean belongs( GraphicalPlayable<?> xPlayable ){
		
		if( xPlayable instanceof PUnit )
			return unitBelongs( ( PUnit ) xPlayable );
		
		if( xPlayable instanceof PBuilding )
			return buildingBelongs( ( PBuilding ) xPlayable );
		
		return false;
		
	}
	
	public ArrayList< PUpgradeProduce< ?, ? > > getAlreadyUpgraded(){
		
		return new ArrayList<PUpgradeProduce<?,?>>( mAlreadyUpgraded );
		
	}
	
	public boolean unitBelongs( PUnit xUnit ){
		
		return mUnits.contains( xUnit );
		
	}
	
	public boolean buildingBelongs( PBuilding xBuilding ){
		
		return mBuildings.contains( xBuilding );
		
	}
	
	@Override
	public boolean equals( Object xObj ){

		if( xObj instanceof Player )
			return ( ( Player ) xObj ).getName().equals( getName() );
		
		return super.equals( xObj );
	
	}
	
	public void turn(){
		
		for( PBuilding building: getLivingBuildings() ){
		
			building.turn();
		
			if( building.getResourceProduces() != null ){
			
				for( PResource resProduce: building.getResourceProduces() )
					earn( resProduce );
			
			}
			
		}

		for( PUnit unit: getLivingUnits() )
			unit.turn();
		
	}
	
	public void continueTurn(){
		
	}
	
	public void applyUpgrade( PUpgradeProduce< ?, ? > xUpgrade ){
		
		for( PUnit unit: getUnits() )
			unit.applyUpgrade( xUpgrade );
		
		mAlreadyUpgraded.add( xUpgrade );

		for( PBuilding building: mBuildings )
			building.filterPlayer( this );
		
	}
	
	@Override
	public String toString() {

		return getName();
	
	}
	
}
