package tiralabra;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceNodeTest {
    
    public PlaceNodeTest() { }

    PlaceNode testNode;
    String name = "Raatala";
    Double latitude = 60.532;
    Double longitude = 23.169;
    
    @Before
    public void setUp() {        
        testNode = new PlaceNode(name, latitude, longitude);
    }
    
    @Test
    public void placeNodeCanBeConstructed() {
        String newName = "Kiikala";
        Double newLatitude = 64.25;
        Double newLongitude = 42.5;
        PlaceNode newPlace = new PlaceNode(newName, newLatitude, newLongitude);
        assertNotNull("Paikkaa ei pystytty luomaan", newPlace);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeConstructorThrowsExceptionIfNameIsNotValid() {
        String newName = " ";
        Double newLatitude = 64.25;
        Double newLongitude = 42.5;
        PlaceNode newPlace = new PlaceNode(newName, newLatitude, newLongitude);
    }    
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeConstructorThrowsExceptionIfLatitudeIsNotValid() {
        String newName = "Kiikala";
        Double newLatitude = 90.1;
        Double newLongitude = 42.5;
        PlaceNode newPlace = new PlaceNode(newName, newLatitude, newLongitude);
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeConstructorThrowsExceptionIfLongitudeIsNotValid() {
        String newName = "Kiikala";
        Double newLatitude = 64.25;
        Double newLongitude = 180.1;
        PlaceNode newPlace = new PlaceNode(newName, newLatitude, newLongitude);
    }

    @Test
    public void nameCanBeGot() {
        assertEquals("Paikan nimeä ei voi katsella", name, testNode.getName());
    }

    @Test
    public void nameCanBeSet() {
        String newName = "Kiikala";
        testNode.setName(newName);
        assertEquals("Paikan nimeä ei voi asettaa", newName, testNode.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfNameIsNotValid() {
        String newName = " ";
        testNode.setName(newName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfNameIsNull() {
        String newName = null;
        testNode.setName(newName);
    }
    
    @Test
    public void latitudeCanBeGot() {
        assertEquals("Leveysastetta ei voi katsella", latitude, testNode.getLatitude());
    }
    
    @Test
    public void latitudeCanBeSet() {
        Double newLatitude = -90.0;
        testNode.setLatitude(newLatitude);
        assertEquals("Leveysastetta ei voi asettaa", newLatitude, testNode.getLatitude());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLatitudeIsNull() {
        testNode.setLatitude(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLatitudeLessThanMinimum() {
        Double newLatitude = -90.1;
        testNode.setLatitude(newLatitude);
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLatitudeGreaterThanMaximum() {
        Double newLatitude = 90.1;
        testNode.setLatitude(newLatitude);
    }
    
    @Test
    public void longitudeCanBeGot() {
        assertEquals("Pituusastetta ei voi katsella", longitude, testNode.getLongitude());
    }
    
    @Test
    public void longitudeCanBeSet() {
        Double newLongitude = -180.0;
        testNode.setLongitude(newLongitude);
        assertEquals("Pituusastetta ei voi asettaa", newLongitude, testNode.getLongitude());
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLongitudeIsNull() {
        testNode.setLongitude(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLongitudeLessThanMinimum() {
        Double newLatitude = -180.1;
        testNode.setLatitude(newLatitude);
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfLongitudeGreaterThanMaximum() {
        Double newLatitude = 180.1;
        testNode.setLatitude(newLatitude);
    }
}
