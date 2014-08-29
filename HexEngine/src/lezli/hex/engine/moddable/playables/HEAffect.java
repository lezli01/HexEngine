package lezli.hex.engine.moddable.playables;

public interface HEAffect extends HEPlayable{

	public void init( HESkill xSkill, HEUnit xUnit, HELivingPlayable xPlayable );

	public void apply( HESkill xSkill, HEUnit xUnit, HELivingPlayable xPlayable );
	
	public String getAffectValue();
	
	public String getStat();
	
	public int getWhen();
	
}
