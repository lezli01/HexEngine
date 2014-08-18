package lezli.hex.engine.core.structure.entities.map.tile;

import java.util.ArrayList;

import lezli.hex.engine.core.structure.entities.graphics.GraphicalEntity;
import lezli.hex.engine.core.structure.entities.skill.affect.Affect;
import lezli.hex.engine.core.structure.entities.skill.affect.AffectCollector;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Tile extends GraphicalEntity{

	private boolean mCanWalk;
	
	private AffectCollector onEnterAffects;
	private AffectCollector onExitAffects;
	private AffectCollector onStayAffects;
	
	public Tile( String xFileName ){
		
		super( xFileName );
		
	}
	
	public boolean canWalk(){
		
		return mCanWalk;
		
	}

	public ArrayList< Affect > getOnEnterAffects(){
		
		return onEnterAffects.getAll();
		
	}
	
	public ArrayList< Affect > getOnExitAffects(){
		
		return onExitAffects.getAll();
		
	}
	
	public ArrayList< Affect > getOnStayAffects(){
		
		return onStayAffects.getAll();
		
	}

	private void setCanWalk( String xCanWalk ){
		
		if( xCanWalk.equals( "true" ) )
			mCanWalk = true;
		else
			mCanWalk = false;
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setCanWalk( xElement.getAttribute( "can_walk" ) );

		onEnterAffects = new AffectCollector( xElement.getChildByName( "OnEnter" ) );
		onExitAffects = new AffectCollector( xElement.getChildByName( "OnExit" ) );
		onStayAffects = new AffectCollector( xElement.getChildByName( "OnStay" ) );
		
	}

	@Override
	protected void init() {
		
		mCanWalk = true;
	
	}

}