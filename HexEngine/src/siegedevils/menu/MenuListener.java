package siegedevils.menu;

public interface MenuListener {

	public void mapStarted( String xGameTableId );
	public void save( String xFileName );
	public void mapEnded();
	
}
