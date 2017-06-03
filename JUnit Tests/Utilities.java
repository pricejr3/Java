/* This class was written by Dr. Sobel for use in illustrating a class that
 * acts as a container of related methods that don't require maintaining
 * state (e.g. Math class)
 */
public class Utilities {
	public static int max( int x, int y )   {
		int maximum = x;
		if ( y > x )
			maximum = y;
		return maximum;
	}

}
