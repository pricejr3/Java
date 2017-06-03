

import java.io.Serializable;

public class ServerBoard implements Serializable {

    private Boolean shipLocations[][] = new Boolean[10][10];
    public static int numberPlaces;
  
    public ServerBoard() {

        for (int i = 0; i < shipLocations.length; i++) {
            for (int j = 0; j < shipLocations[i].length; j++) {
                shipLocations[i][j] = null;
            }
        }
        
    
        numberPlaces = 17;
    }



    public void setHit(TargetCoordinates target) {
        shipLocations[target.one][target.two] = true;
    }

   
    public void setMiss(TargetCoordinates target) {
        shipLocations[target.one][target.two]  = false;
    }

}
