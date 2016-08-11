package tiralabra;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathTest {
    
    PlaceNode newPlace, nextPlace, lastPlace;
    
    public PathTest() {
    }
    
    @Before
    public void setUp() {
        newPlace = new PlaceNode("joku", 0.0, 0.0);
        nextPlace = new PlaceNode("muu", 1.0, 1.0);
        lastPlace = new PlaceNode("jossain", 2.0, 2.0);        
    }

    @Test
    public void pathConstructedProperly() {
        int size = 27;
        Path path = new Path(size);
        
        assertNotNull("Paikkataulukkoa ei ole luotu", path.getPlaces());
        assertEquals("Paikkataulukon koko on väärä", size, path.getPlaces().length);
        assertEquals("Indeksimuuttujan alkuarvo on väärä", -1, path.getTop());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void pathConstructorThrowsExceptionIfSizeIsZero() {
        Path path = new Path(0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void pathConstructorThrowsExceptionIfSizeIsNegative() {
        Path path = new Path(-1);
    }
    
    @Test
    public void placesCanBePushedIntoPath() {
        Path path = new Path(3);
        
        path.push(newPlace);        
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", 0, path.getTop());
        assertEquals("Paikka ei ole oikea", newPlace.getName(), path.getPlaces()[0].getName());
        
        path.push(nextPlace);
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", 1, path.getTop());
        assertEquals("Paikka ei ole oikea", nextPlace.getName(), path.getPlaces()[1].getName());
        
        path.push(lastPlace);
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", 2, path.getTop());
        assertEquals("Paikka ei ole oikea", lastPlace.getName(), path.getPlaces()[2].getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void pushingNullPlaceThrowsException() {
        Path path = new Path(3);
        path.push(null);
    }

    @Test
    public void placesCanBePoppedFromPath() {
        Path path = new Path(3);
        path.push(newPlace);
        path.push(nextPlace);
        path.push(lastPlace);
        
        PlaceNode popped = path.pop();
        assertNotNull("Paikkasolmua ei saatu ulos", popped);
        assertEquals("Saatiin väärä paikkasolmu", lastPlace.getName(), popped.getName());
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", 1, path.getTop());
        
        popped = path.pop();
        assertNotNull("Paikkasolmua ei saatu ulos", popped);
        assertEquals("Saatiin väärä paikkasolmu", nextPlace.getName(), popped.getName());
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", 0, path.getTop());
        
        popped = path.pop();
        assertNotNull("Paikkasolmua ei saatu ulos", popped);
        assertEquals("Saatiin väärä paikkasolmu", newPlace.getName(), popped.getName());
        assertEquals("Paikkataulukon koko on väärä", 3, path.getPlaces().length);
        assertEquals("Indeksimuuttujan arvo on väärä", -1, path.getTop());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void poppingFromEmptyPathThrowsException() {
        Path path = new Path(3);
        path.pop();
    }

    @Test
    public void isEmptyReturnsTrueForEmptyPath() {
        Path path = new Path(3);
        boolean isEmpty = path.isEmpty();
        assertTrue("Polkua väitetään virheellisesti ei-tyhjäksi", isEmpty);
    }
    
    @Test
    public void isEmptyReturnsFalseForNonEmptyPath() {
        Path path = new Path(3);
        path.push(newPlace);
        boolean isEmpty = path.isEmpty();
        assertTrue("Polkua väitetään virheellisesti tyhjäksi", !isEmpty);
    }
    
}
