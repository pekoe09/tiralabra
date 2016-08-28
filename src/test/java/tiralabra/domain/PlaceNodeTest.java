package tiralabra.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceNodeTest {

    private PlaceNode testNode;
    private String name = "Raatala";
    private Double latitude = 60.532;
    private Double longitude = 23.169;
    private double startDistance = 3.5;
    private double endDistance = 4.5;
    private NeighbourNode neighbour1, neighbour2;
    
    @Before
    public void setUp() {        
        testNode = new PlaceNode(name, latitude, longitude);      
        testNode.setStartDistance(startDistance);
        testNode.setEndDistance(endDistance);
        neighbour1 = new NeighbourNode(new PlaceNode("joku", 20.0, 3.0), 5);
        neighbour2 = new NeighbourNode(new PlaceNode("muu", 10.0, 15.0), 5);
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
    
    @Test
    public void neighboursCanBeSet() {
        NeighbourNode[] newNeighbours = {neighbour1, neighbour2};
        
        testNode.setNeighbours(newNeighbours);
        assertNotNull("Naapuritaulukkoa ei ole", testNode.getNeighbours());
        assertEquals("Naapurien määrä on väärin", newNeighbours.length, testNode.getNeighbours().length);
        assertEquals("Naapuri on väärä", neighbour1.getNeighbour().getName(), testNode.getNeighbours()[0].getNeighbour().getName());
    }
    
    @Test
    public void startDistanceCanGeGot() {
        assertTrue("Etäisyyttä aloitussolmuun ei voi lukea", Math.abs(startDistance - testNode.getStartDistance())  <0.00001);
    }
    
    @Test
    public void startDistanceCanBeSet(){
        double newStartDistance = 12.0;
        
        testNode.setStartDistance(newStartDistance);
        assertTrue("Etäisyyttä aloitussolmuun ei voi asettaa", Math.abs(testNode.getStartDistance() - newStartDistance) < 0.000001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfStartDistanceNegative() {
        testNode.setStartDistance(-0.01);
    }
    
    @Test
    public void endDistanceCanBeGot() {
        assertTrue("Etäisyyttä maalisolmuun ei voi lukea", Math.abs(testNode.getEndDistance() - endDistance) < 0.00001);
    }
    
    @Test
    public void endDistanceCanBeSet() {
        double newEndDistance = 12.0;
        
        testNode.setEndDistance(newEndDistance);
        assertTrue("Etäisyyttä maalisolmuun ei voi asettaa", Math.abs(testNode.getEndDistance() - newEndDistance) < 0.000001);
    }
        
    @Test(expected = IllegalArgumentException.class)
    public void placeNodeThrowsExceptionIfEndDistanceNegative() {
        testNode.setEndDistance(-0.01);
    }
    
    @Test
    public void distanceToNeighbourCanBeGot() {
        NeighbourNode[] newNeighbours = {neighbour1, neighbour2};        
        testNode.setNeighbours(newNeighbours);
        
        double distance = testNode.getDistanceToNeighbour(neighbour1.getNeighbour().getName());
        
        assertTrue("Etäisyys naapuriin on väärä", Math.abs(distance - neighbour1.getDistance()) < 0.00001);
    }
    
    @Test
    public void distanceToNeighbourReturnsMaxValueIfNeighbourNotFound() {
        NeighbourNode[] newNeighbours = {neighbour1, neighbour2};        
        testNode.setNeighbours(newNeighbours);
        
        double distance = testNode.getDistanceToNeighbour("random");
        
        assertTrue("Olemattomalla naapurilla ei palauteta Double.MAX_VALUE:ta", Math.abs(distance - Double.MAX_VALUE) < 0.00001);
    }
}
