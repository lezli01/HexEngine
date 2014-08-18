package lezli.hex.engine.moddable.interfaces;

import java.util.ArrayList;

public interface HEGameEvent {

	public static final int UNIT_MOVED		=	1 << 0;
	public static final int SKILL_CASTED	=	1 << 1;
	public static final int PRODUCED		=   1 << 2;
	public static final int BUILDING_ADDED	=	1 << 3;
	public static final int TURNED			= 	1 << 4;

	public int getType();
	public String getPlayer();
	public String getStat();
	public int getValue();
	
	public ArrayList< Integer > getX();
	public ArrayList< Integer > getY();
	
}
