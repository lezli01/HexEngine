package lezli.hexengine.core.structure.entities.building;

import com.badlogic.gdx.utils.XmlReader.Element;

public class SkillUpgrade extends ProduceEntity{

	private String mSkill;
	
	public SkillUpgrade( Element xElement ){
		
		super( xElement );
		
	}

	public String getSkill(){
		
		return mSkill;
		
	}
	
	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setSkill( xElement.getAttribute( "skill" ) );
		
	}
	
	private void setSkill( String xSkill ){
		
		mSkill = xSkill;
		log( "Skill set (" + mSkill + ")" );
		
	}
	
	@Override
	protected void init() {
		
	}

}
