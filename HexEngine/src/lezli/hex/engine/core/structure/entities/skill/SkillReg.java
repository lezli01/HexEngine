package lezli.hex.engine.core.structure.entities.skill;

import com.badlogic.gdx.utils.XmlReader.Element;

import lezli.hex.engine.core.structure.entities.Entity;

public class SkillReg extends Entity{

	private String mSkill;
	
	public SkillReg( Element xElement ){
		
		super( xElement );
		
	}

	@Override
	protected void parse( Element xElement ){
		
		super.parse( xElement );
		
		setSkill( xElement.getAttribute( "skill" ) );
		
	}

	public String getSkill(){
		
		return mSkill;
		
	}

	@Override
	public String toString() {
	
		return mSkill;
		
	}

	private void setSkill( String xSkill ){
		
		mSkill = xSkill;
		log( "Skill set to (" + mSkill + ")" );
		
	}
	
	@Override
	protected void init(){
		
	}

}
