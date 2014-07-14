package lezli.hexengine.core.playables.graphics;

public interface PAnimationListener {

	public void onAnimationStarted( String xID );
	
	public void onAnimationStopped( String xID );
	
	public void onAnimationLooped( String xID, int xNum );
	
}
