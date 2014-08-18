package lezli.hex.engine.core.playables.graphics;

import lezli.hex.engine.core.brashmonkey.spriter.Animation;
import lezli.hex.engine.core.brashmonkey.spriter.Data;
import lezli.hex.engine.core.brashmonkey.spriter.LibGdxDrawer;
import lezli.hex.engine.core.brashmonkey.spriter.LibGdxLoader;
import lezli.hex.engine.core.brashmonkey.spriter.Player;
import lezli.hex.engine.core.brashmonkey.spriter.SCMLReader;
import lezli.hex.engine.core.brashmonkey.spriter.Mainline.Key;
import lezli.hex.engine.core.brashmonkey.spriter.Player.PlayerListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PScmlHandler {

	private Player player;
	private LibGdxDrawer drawer;

	public PScmlHandler( String xFileName ){
		
		ShapeRenderer renderer = new ShapeRenderer();
		SpriteBatch batch = new SpriteBatch();
		FileHandle handle = Gdx.files.internal( xFileName );
		Data data = new SCMLReader(handle.read()).getData();

		LibGdxLoader loader = new LibGdxLoader(data);
		loader.load(handle.file());

		drawer = new LibGdxDrawer(loader, batch, renderer);
		player = new Player(data.getEntity(0));

		player.flip( false, true );
		
	}
	
	public boolean hasAnimation( String anim ){
		
		return player.getEntity().getAnimation( anim ) != null;
		
	}
	
	public void addListener( final PAnimationListener xListener ){
		
		player.addListener( new PlayerListener() {
			
			@Override
			public void preProcess(Player player) {
			}
			
			@Override
			public void postProcess(Player player) {
			}
			
			@Override
			public void mainlineKeyChanged(Key prevKey, Key newKey) {
			}
			
			@Override
			public void animationFinished(Animation animation) {
				xListener.onAnimationStopped( player.getAnimation().name );
			}
			
			@Override
			public void animationChanged(Animation oldAnim, Animation newAnim) {
			}

		});
		
	}
	
	public void update(){
		
		player.speed = ( int ) ( Gdx.graphics.getDeltaTime() * 1000.f );
		player.update();
		
	}
	
	public void render( SpriteBatch xSpriteBatch, String xID, float x, float y, float w, float h ){

		player.setAnimation( xID );
		player.setPosition( x, y );
		drawer.draw( player, xSpriteBatch );
		
	}
	
}
