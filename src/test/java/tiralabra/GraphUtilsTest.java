package tiralabra;

import tiralabra.domain.PlaceNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class GraphUtilsTest {
    
    private List<PlaceNode> testGraph;
    private String testName;
    private PlaceNode testPlace1 = new PlaceNode("joku", 10.1, 20.5);
    private PlaceNode testPlace2 = new PlaceNode("muu", 35.1, 126.3);
    
    public GraphUtilsTest() { }
    
    @Before
    public void setUp() {
        testGraph = new ArrayList<>();
        testGraph.add(testPlace1);
        testGraph.add(testPlace2);
    }

    @Test
    public void placeCanBeFound() {
        PlaceNode place1 = GraphUtils.findPlace(testGraph, testPlace1.getName());
        PlaceNode place2 = GraphUtils.findPlace(testGraph, testPlace2.getName());
        assertEquals("Ensimmäistä paikkaa ei löydy", testPlace1, place1);
        assertEquals("Toista paikkaa ei löydy", testPlace2, place2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void findPlaceThrowsExceptionIfPlaceNotFound() {
        PlaceNode place = GraphUtils.findPlace(testGraph, "oku");
    }
}
