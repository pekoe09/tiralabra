package tiralabra.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NeighbourNodeTest {
    
    private NeighbourNode testNeighbour;
    private PlaceNode testPlace = new PlaceNode("Raatala", 60.532, 23.169);  
    private double testDistance = 36.2;
    
    @Before
    public void setUp() {
        testNeighbour = new NeighbourNode(testPlace, testDistance);
    }
    
    @Test
    public void neighbourNodeCanBeConstructed() {
        NeighbourNode newNode = new NeighbourNode(testPlace, testDistance);
        assertNotNull("Naapuria ei ole", newNode.getNeighbour());
        assertTrue("Naapurin etäisyys on väärä", Math.abs(testDistance - newNode.getDistance()) < 0.00001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neighbourNodeConstructorThrowsErrorIfNeighbourNotValid() {
        NeighbourNode newNode = new NeighbourNode(null, testDistance);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neighbourNodeConstructorThrowsErrorIfDistanceNotValid() {
        NeighbourNode newNode = new NeighbourNode(testPlace, -3.0);
    }
    
    @Test
    public void testGetNeighbour() {
        assertNotNull("Naapuria ei voi katsoa", testNeighbour.getNeighbour());
    }

    @Test
    public void testSetNeighbour() {
        PlaceNode newNeighbour = new PlaceNode("muu", 12.0, 13.0);
        testNeighbour.setNeighbour(newNeighbour);
        assertEquals(newNeighbour, testNeighbour.getNeighbour());
    }

    @Test
    public void testGetDistance() {
        assertTrue("Etäisyyttä ei voi katsoa",  Math.abs(testDistance - testNeighbour.getDistance()) < 0.00001);
    }

    @Test
    public void testSetDistance() {
        double newDistance = 2.5;
        testNeighbour.setDistance(newDistance);
        assertTrue("Naapurin etäisyys on väärä", Math.abs(newDistance - testNeighbour.getDistance()) < 0.00001);
    }
    
}
