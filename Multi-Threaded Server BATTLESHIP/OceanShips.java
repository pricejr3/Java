

import java.io.Serializable;


public class OceanShips implements Serializable {

    Boat[] arsenal = { new Boat("PT Boat", 2), new Boat("Destroyer", 3), new Boat("Submarine",3),
            new Boat("Battleship", 4), new Boat("Carrier",5) };

    private ServerBoard board = new ServerBoard();
    
    public int x = 0;
    public int y = 0;




    public Boat isHit(TargetCoordinates shot) {
        Boat wasHit = null;
        x = shot.one;
        y = shot.two;
 
        Boat.Rotation rotation;
        int[] pos;
        int facing;
        int[] coord = { x, y };

        for (int i = 0; i < arsenal.length; i++) {
            rotation = arsenal[i].getRotation();
            pos = arsenal[i].getPosition();
            if (rotation == Boat.Rotation.HORIZ) {
                facing = 0;
            } else {
                facing = 1;
            }

            if (coord[Math.abs(facing- 1)] == pos[Math
                    .abs(facing - 1)]) {
                for (int b = 0; b < arsenal[i].getSize(); b++) {
                    if (x == pos[0] && y == pos[1]) {
                        wasHit = arsenal[i];
                       
                    }
                    pos[facing]++;
                }
            }
        }

        return wasHit;
    }


    public void positionShip(int ship, int x, int y, Boat.Rotation rotation) {
        arsenal[ship].setPosition(x, y);
        arsenal[ship].setRotation(rotation);
    }

    public Boat getShip(int index) {
        return arsenal[index];
    }
}