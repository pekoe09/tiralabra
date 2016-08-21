package tiralabra.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathSearchResultSetTest {
    
    public PathSearchResultSetTest() {
    }
    private PathSearchResultSet testSet;
    private PathSearchResult result1;
    private PathSearchResult result2;
    private PathSearchResult[] results;
    
    @Before
    public void setUp() {
        PlaceNode startPlace = new PlaceNode("jostain", 10.0, 20.0);
        PlaceNode endPlace = new PlaceNode("jonnekin", 30.0, 40.0);
        
        result1 = new PathSearchResult();
        result1.setFilePath("eka tulos");
        result1.setStartPlace(startPlace);
        result1.setEndPlace(endPlace);
        result2 = new PathSearchResult();
        result2.setFilePath("toka tulos");
        results = new PathSearchResult[]{result1, result2};
        testSet = new PathSearchResultSet(results);        
    }
    
    @Test
    public void pathSearchResultSetIsConstructedProperly() {
        PathSearchResultSet newSet = new PathSearchResultSet(results);
        assertNotNull("Tulostaulukkoa ei saa ulos", newSet.getResultArray());
        assertEquals("Tulostaulukko on väärän mittainen", results.length, newSet.getResultArray().length);
    }

    @Test
    public void testGet() {
        assertEquals("Get palauttaa väärän tuloksen", result2.getFilePath(), testSet.get(1).getFilePath());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getThrowsExceptionWhenGivenNegativeIndex() {
        testSet.get(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getThrowsExceptionWhenGivenTooLargeIndex() {
        testSet.get(2);
    }

    @Test
    public void testGetName() {
        String expectedName = String.format("%s - paikasta %s paikkaan %s",
                result1.getFilePath(),
                result1.getStartPlace().getName(),
                result1.getEndPlace().getName());
        assertEquals("Saatu nimi ei ole odotetunlainen", expectedName, testSet.getName());
    }
    
    @Test
    public void getNameReturnsNullWhenResultArrayHasNoFirstElement() {
        PathSearchResultSet newSet = new PathSearchResultSet(new PathSearchResult[1]);
        assertNull("Ei palauta nullia vaikka tulostaulukossa ei 1. elementtiä", newSet.getName());
    }
    @Test
    public void getNameReturnsNullWhenResultArrayIsNull() {
        PathSearchResultSet newSet = new PathSearchResultSet(null);
        assertNull("Ei palauta nullia vaikka tulostaulukko on null", newSet.getName());
    }

    @Test
    public void testSize() {
        assertEquals("Koko on väärä", results.length, testSet.size());
    }
    
    @Test
    public void sizeReturnsZeroIfResultArrayIsNull() {
        PathSearchResultSet newSet = new PathSearchResultSet(null);
        assertEquals("Koon väitetään olevan muuta kuin nolla kun tulostaulukko on null", 0, newSet.size());
    }
    
    @Test
    public void testGetResultArray() {
        assertNotNull("Tulostaulukkoa ei voi saada", testSet.getResultArray());
        assertEquals("Tulostaulukko on väärän kokoinen", results.length, testSet.getResultArray().length);
    }
}
