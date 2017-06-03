

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Test;

import static org.junit.Assert.*;

public class Testing {
	
	
	/*Test the creation of the patrol boat
	 * */
    @Test
    public void testPTCreation() {
        OceanShips me = new OceanShips();
        String result = me.arsenal[0].name;
        assertEquals("PT Boat", result);

    }
    
    /*Test the creation of the destroyer
	 * */
    @Test
    public void testDestroyerCreation() {
        OceanShips me = new OceanShips();
        String result = me.arsenal[1].name;
        assertEquals("Destroyer", result);

    }
    
    /*Test the creation of the submarine
 	 * */
    @Test
    public void testSubmarineCreation() {
        OceanShips me = new OceanShips();
        String result = me.arsenal[2].name;
        assertEquals("Submarine", result);

    }
    
    /*Test the creation of the battleship
 	 * */
    @Test
    public void testBattleshipCreation() {
        OceanShips me = new OceanShips();
        String result = me.arsenal[3].name;
        assertEquals("Battleship", result);

    }
    
    /*Test the creation of the carrier
 	 * */
    @Test
    public void testCarrierCreation() {
        OceanShips me = new OceanShips();
        String result = me.arsenal[4].name;
        assertEquals("Carrier", result);

    }
    
    /*Test the size of the patrol boat
 	 * */
    @Test
    public void testPTSize() {
        OceanShips me = new OceanShips();
        int result = me.arsenal[0].length;
        assertEquals(2, result);

    }
    
    /*Test the size of the destroyer
   	 * */
    @Test
    public void testDestroyerSize() {
        OceanShips me = new OceanShips();
        int result = me.arsenal[1].length;
        assertEquals(3, result);

    }
    
    /*Test the size of the submarine
   	 * */
    @Test
    public void testSubmarineSize() {
        OceanShips me = new OceanShips();
        int result = me.arsenal[2].length;
        assertEquals(3, result);

    }
    
    /*Test the size of the battleships
   	 * */
    @Test
    public void testBattleshipSize() {
        OceanShips me = new OceanShips();
        int result = me.arsenal[3].length;
        assertEquals(4, result);

    }
    
    /*Test the size of the carrier
   	 * */
    @Test
    public void testCarrierSize() {
        OceanShips me = new OceanShips();
        int result = me.arsenal[4].length;
        assertEquals(5, result);

    }   
    
    
    @Test
    public void testTargetCoordinates() {
    	int x = 5;
    	int y = 6;
        TargetCoordinates pos = new TargetCoordinates(x,y);
        int resultX = pos.getOne();
        int resultY = pos.getTwo();
        assertEquals(5, resultX);
        assertEquals(6, resultY);

    }  
}
