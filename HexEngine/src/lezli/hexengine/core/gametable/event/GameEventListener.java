package lezli.hexengine.core.gametable.event;

import lezli.hexengine.core.playables.Playable;

public interface GameEventListener {

	public boolean newEvent( Playable<?> xPlayable, GameEvent xEvent );
	
}
