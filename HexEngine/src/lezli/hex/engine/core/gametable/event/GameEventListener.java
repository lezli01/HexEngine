package lezli.hex.engine.core.gametable.event;

import lezli.hex.engine.core.playables.Playable;

public interface GameEventListener {

	public boolean newEvent( Playable<?> xPlayable, GameEvent xEvent );
	
}
