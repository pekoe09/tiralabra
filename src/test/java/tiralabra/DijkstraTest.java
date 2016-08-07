package tiralabra;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DijkstraTest {
    
    Dijkstra dijkstra;
    
    public DijkstraTest() { }
    
    @Before
    public void setUp() {
        dijkstra = new Dijkstra();        
    }

    @Test
    public void testInitialize() {
        List<PlaceNode> testGraph = new ArrayList<>();
        PlaceNode testStartNode = new PlaceNode("alku", 0.0, 0.0);
        PlaceNode otherNode1 = new PlaceNode("joku", 1.0, 1.0);
        PlaceNode otherNode2 = new PlaceNode("joku", 2.0, 2.0);
        PlaceNode otherNode3 = new PlaceNode("joku", 3.0, 3.0);
        testGraph.add(testStartNode);
        testGraph.add(otherNode1);
        testGraph.add(otherNode2);
        testGraph.add(otherNode3);
        
        dijkstra.initialize(testGraph, testStartNode);
        for(PlaceNode node : testGraph) {
            if(node == testStartNode) {
                assertTrue("Aloitussolmulla etäisyystieto ei ole nolla", Math.abs(node.getStartDistance()) < 0.00001);
            } else {
                assertTrue(String.format("Solmun %s etäisyystieto ei ole Double.MAX_VALUE", node.getName()), Math.abs(node.getStartDistance() - Double.MAX_VALUE) < 0.00001);
            }
            assertNull("Edeltäjäsolu ei ole tyhjä", node.getPathPredecessor());
        }
    }

    @Test
    public void testRelaxWhenRelaxationNeeded() {
        PlaceNode node = new PlaceNode("joku", 60.0, 23.0);
        node.setStartDistance(5.0);
        PlaceNode neighbour = new PlaceNode("toinen", 61.0, 24.0);
        neighbour.setStartDistance(10.0);
        NeighbourNode nodeNeighbour = new NeighbourNode(neighbour, 3.0);
        node.setNeighbours(new NeighbourNode[]{nodeNeighbour});
        
        dijkstra.relax(node, neighbour);
        assertTrue("Naapurin aloitusetäisyys ei päivity", Math.abs(neighbour.getStartDistance() - 8.0) < 0.000001);
    }
    
    @Test
    public void testRelaxWhenRelaxationNotNeeded() {
        PlaceNode node = new PlaceNode("joku", 60.0, 23.0);
        node.setStartDistance(7.0);
        PlaceNode neighbour = new PlaceNode("toinen", 61.0, 24.0);
        neighbour.setStartDistance(10.0);
        NeighbourNode nodeNeighbour = new NeighbourNode(neighbour, 3.0);
        node.setNeighbours(new NeighbourNode[]{nodeNeighbour});
        
        dijkstra.relax(node, neighbour);
        assertTrue("Naapurin aloitusetäisyys päivittyy", Math.abs(neighbour.getStartDistance() - 10.0) < 0.000001);
    }
    
}
