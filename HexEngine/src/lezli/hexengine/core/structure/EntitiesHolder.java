package lezli.hexengine.core.structure;

import java.util.ArrayList;
import java.util.HashMap;

import lezli.hexengine.core.structure.entities.building.Building;
import lezli.hexengine.core.structure.entities.common.Races;
import lezli.hexengine.core.structure.entities.common.Resources;
import lezli.hexengine.core.structure.entities.common.Stats;
import lezli.hexengine.core.structure.entities.common.Teams;
import lezli.hexengine.core.structure.entities.gametable.GameTable;
import lezli.hexengine.core.structure.entities.graphics.Graphics;
import lezli.hexengine.core.structure.entities.map.Map;
import lezli.hexengine.core.structure.entities.map.MapTile;
import lezli.hexengine.core.structure.entities.map.tile.Tile;
import lezli.hexengine.core.structure.entities.map.tile.placeholder.Placeholder;
import lezli.hexengine.core.structure.entities.skill.Skill;
import lezli.hexengine.core.structure.entities.text.Texts;
import lezli.hexengine.core.structure.entities.unit.Unit;
import lezli.hexengine.core.structure.utils.Common;
import lezli.hexengine.core.structure.utils.Util;
import lezli.hexengine.core.structure.utils.managers.BuildingManager;
import lezli.hexengine.core.structure.utils.managers.GameTableManager;
import lezli.hexengine.core.structure.utils.managers.GraphicsManager;
import lezli.hexengine.core.structure.utils.managers.MapManager;
import lezli.hexengine.core.structure.utils.managers.MapTileManager;
import lezli.hexengine.core.structure.utils.managers.PlaceholderManager;
import lezli.hexengine.core.structure.utils.managers.SkillManager;
import lezli.hexengine.core.structure.utils.managers.TextsManager;
import lezli.hexengine.core.structure.utils.managers.TileManager;
import lezli.hexengine.core.structure.utils.managers.UnitManager;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class EntitiesHolder extends Util{

	private String mDataPath;
	
	private Common mCommon;
	private BuildingManager mBuildingManager;
	private GraphicsManager mGraphicsManager;
	private MapManager mMapManager;
	private GameTableManager mGameTableManager;
	private SkillManager mSkillManager;
	private TextsManager mTextsManager;
	private TileManager mTileManager;
	private MapTileManager mMapTileManager;
	private PlaceholderManager mPlaceholderManager;
	private UnitManager mUnitManager;

	private HashMap< String, byte[] > mScripts;
	
	public EntitiesHolder( String xDataPath ){
		
		mDataPath = xDataPath;

		if( Gdx.app.getType() == ApplicationType.Desktop )
			mDataPath = "./bin/" + mDataPath;
		
		loadEntities();
		
	}
	
	public Common getCommon(){
		
		return mCommon;
		
	}
	
	public BuildingManager getBuildingManager(){
		
		return mBuildingManager;
		
	}
	
	public GraphicsManager getGraphicsManager(){
		
		return mGraphicsManager;
		
	}
	
	public MapManager getMapManager(){
		
		return mMapManager;
		
	}
	
	public GameTableManager getGameTableManager(){
		
		return mGameTableManager;
		
	}
	
	public SkillManager getSkillManager(){
		
		return mSkillManager;
		
	}
	
	public TextsManager getTextsManager(){
		
		return mTextsManager;
		
	}
	
	public TileManager getTileManager(){
		
		return mTileManager;
		
	}
	
	public MapTileManager getMapTileManager(){
		
		return mMapTileManager;
		
	}
	
	public PlaceholderManager getPlaceholderManager(){
		
		return mPlaceholderManager;
		
	}
	
	public UnitManager getUnitManager(){

		return mUnitManager;
		
	}
	
	public HashMap< String, byte[] > getScripts(){
		
		return mScripts;
		
	}
	
	private void loadEntities(){
		
		log( "Loading entities..." );
		
		mBuildingManager = new BuildingManager();
		mGraphicsManager = new GraphicsManager();
		mMapManager = new MapManager();
		mGameTableManager = new GameTableManager();
		mSkillManager = new SkillManager();
		mTextsManager = new TextsManager();
		mTileManager = new TileManager();
		mMapTileManager = new MapTileManager();
		mPlaceholderManager = new PlaceholderManager();
		mUnitManager = new UnitManager();
		mScripts = new HashMap<String, byte[] >();

		log( "Loading Buildings..." );
		
		for( FileHandle f: Gdx.files.internal( mDataPath + "/buildings/" ).list() )
			mBuildingManager.add( new Building( f.path() ) );
		
		log( "Loading Common..." );
		
		mCommon = new Common();
		mCommon.setRaces( new Races( mDataPath + "/common/races.xml" ) );
		mCommon.setTeams( new Teams( mDataPath + "/common/teams.xml" ) );
		mCommon.setResources( new Resources( mDataPath + "/common/resources.xml" ) );
		mCommon.setStats( new Stats( mDataPath + "/common/stats.xml" ) );
		
		log( "Loading Graphics..." );
		
		for( FileHandle f: getF( mDataPath + "/graphics/" ) )
				mGraphicsManager.add( new Graphics( f.path() ) );
		
		log( "Loading Maps..." );
		
		for( FileHandle f: getF( mDataPath + "/maps/" ) )
			mMapManager.add( new Map( f.path() ) );
		
		log( "loading gametables..." );
		
		for( FileHandle f: getF( mDataPath + "/gametables/" ) )
			mGameTableManager.add( new GameTable( f.path() ) );
		
		log( "Loading Skills..." );
		
		for( FileHandle f: getF( mDataPath + "/skills/" ) )
			mSkillManager.add( new Skill( f.path() ) );
		
		log( "Loading Texts..." );
		
		for( FileHandle f: getF( mDataPath + "/texts/" ) )
			mTextsManager.add( new Texts( f.path() ) );
		
		log( "Loading Tiles..." );
		
		for( FileHandle f: getF( mDataPath + "/tiles/" ) )
			mTileManager.add( new Tile( f.path() ) );
		
		log( "Loading MapTiles..." );
		
		for( FileHandle f: getF( mDataPath + "/maptiles/" ) )
			mMapTileManager.add( new MapTile( f.path() ) );
		
		log( "Loading placeholders..." );
		
		for( FileHandle f: getF( mDataPath + "/placeholders/" ) )
			mPlaceholderManager.add( new Placeholder( f.path() ) );
		
		log( "Loading Units..." );
		
		for( FileHandle f: getF( mDataPath + "/units/" ) )
			mUnitManager.add( new Unit( f.path() ) );

		log( "loading scripts..." );
		
		for( FileHandle f: getF( mDataPath + "/scripts" ) )
			mScripts.put( f.name(), f.readBytes() );
		
		log( "Loading completed!" );
		
	}
	
	private ArrayList< FileHandle > getF( String path ){
		
		ArrayList< FileHandle > fs = new ArrayList< FileHandle >();
		
		for( FileHandle f: Gdx.files.internal( path ).list() ){
			
			if( f.isDirectory() && !f.name().equals( "_noload" ) )
				fs.addAll( getF( f.path() ) );
			else
				fs.add( f );
			
		}
	
		return fs;
		
	}
	
}
