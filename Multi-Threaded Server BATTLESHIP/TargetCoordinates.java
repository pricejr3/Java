
 */
import java.io.Serializable;

public class TargetCoordinates implements Serializable {
    public int one;
    public int two;
    
   

    public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
	}

	public TargetCoordinates(int one, int two) {
        this.one = one;
        this.two = two;
    }

    public TargetCoordinates(int[] coords) {
        //this.two = coords[1];
        this.two = coords[1];
        this.one = coords[0];
    
    }
}
