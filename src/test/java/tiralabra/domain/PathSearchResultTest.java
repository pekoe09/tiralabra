package tiralabra.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.datastructures.PathStack;
import tiralabra.enums.AlgorithmAlternative;

public class PathSearchResultTest {
    
    private PathSearchResult testResult;
    private PlaceNode startPlace;
    private PlaceNode endPlace;
    private PlaceNode otherPlace;
    private Long runtime;
    private int edgeCount;
    private int nodeCount;
    private NamedArrayList graph;
    private PathStack shortestPath;
    private AlgorithmAlternative algorithm;
    private String filePath;
    
    
    @Before
    public void setUp() {
        startPlace = new PlaceNode("jostain", 10.0, 20.0);
        endPlace = new PlaceNode("jonnekin", 30.0, 40.0);
        runtime = 350000L;
        edgeCount = 25;
        nodeCount = 10;
        graph = new NamedArrayList();
        graph.add(startPlace);
        graph.add(endPlace);
        shortestPath = new PathStack(nodeCount);
        shortestPath.push(endPlace);
        algorithm = AlgorithmAlternative.ASTAR;  
        filePath = "data/jokufile.data";
        
        testResult = new PathSearchResult();
        testResult.setAlgorithm(algorithm);
        testResult.setEdgeCount(edgeCount);
        testResult.setNodeCount(nodeCount);
        testResult.setStartPlace(startPlace);
        testResult.setEndPlace(endPlace);
        testResult.setRunTimeNanoSecs(runtime);
        testResult.setGraph(graph);
        testResult.setShortestPath(shortestPath);
        testResult.setFilePath(filePath);
        
        otherPlace = new PlaceNode("muu paikka", -1.0, -1.0);
    }
    
    @Test
    public void pathSearchResultIsConstructedProperly() {
        PathSearchResult result = new PathSearchResult();
        assertNotNull("Konstruktori ei aseta graph-muuttujaa", result.getGraph());
        assertEquals("Graph-listan pituus ei ole nolla", 0, result.getGraph().size());
    }
    
    @Test
    public void testGetFilePath() {
        assertEquals("Tiedostopolkua ei saa ulos", filePath, testResult.getFilePath());
    }

    @Test
    public void testSetFilePath() {
        String newFilePath = "uusi polku";
        testResult.setFilePath(newFilePath);
        assertEquals("Tiedostopolkua ei voi asettaa", newFilePath, testResult.getFilePath());
    }

    @Test
    public void testGetGraph() {
        assertNotNull("Graafia ei saa ulos", testResult.getGraph());
        assertEquals("Graafi on väärän mittainen", graph.size(), testResult.getGraph().size());
    }

    @Test
    public void testSetGraph() {
        NamedArrayList newGraph = new NamedArrayList();
        newGraph.add(endPlace);
        testResult.setGraph(newGraph);
        assertEquals("Graafi on väärän mittainen", newGraph.size(), testResult.getGraph().size());
        assertEquals("Graafissa on väärä solmu", endPlace.getName(), testResult.getGraph().get(0).getName());
    }

    @Test
    public void testGetNodeCount() {
        assertEquals("Solmujen lukumäärää ei saa ulos", nodeCount, testResult.getNodeCount());
    }

    @Test
    public void testSetNodeCount() {
        int newNodeCount = 100;
        testResult.setNodeCount(newNodeCount);
        assertEquals("Solmujen lukumäärää ei voi asettaa", newNodeCount, testResult.getNodeCount());
    }

    @Test
    public void testGetEdgeCount() {
        assertEquals("Kaarien lukumäärää ei saa ulos", edgeCount, testResult.getEdgeCount());
    }

    @Test
    public void testSetEdgeCount() {
        int newEdgeCount = 100;
        testResult.setEdgeCount(newEdgeCount);
        assertEquals("Kaarien lukumäärää ei voi asettaa", newEdgeCount, testResult.getEdgeCount());
    }

    @Test
    public void testGetStartPlace() {
        assertNotNull("Aloituspaikkaa ei saa ulos", testResult.getStartPlace());
        assertEquals("Aloituspaikka ei ole oikea", startPlace.getName(), testResult.getStartPlace().getName());
    }

    @Test
    public void testSetStartPlace() {
         testResult.setStartPlace(otherPlace);
         assertEquals("Aloituspaikkaa ei voi asettaa", otherPlace.getName(), testResult.getStartPlace().getName());
    }

    @Test
    public void testGetEndPlace() {
        assertNotNull("Maalipaikkaa ei saa ulos", testResult.getEndPlace());
        assertEquals("Maalipaikka ei ole oikea", endPlace.getName(), testResult.getEndPlace().getName());
    }

    /**
     * Test of setEndPlace method, of class PathSearchResult.
     */
    @Test
    public void testSetEndPlace() {
        testResult.setEndPlace(otherPlace);
        assertEquals("Maalipaikkaa ei voi asettaa", otherPlace.getName(), testResult.getEndPlace().getName());
    }

    @Test
    public void testGetShortestPath() {
        assertNotNull("Lyhintä polkua ei saa ulos", testResult.getShortestPath());
        assertEquals("Polku on vääränmittainen", shortestPath.getTop(), testResult.getShortestPath().getTop());
        assertEquals("Polulla on väärä solmu", endPlace.getName(), testResult.getShortestPath().getPlaces()[0].getName());
    }

    @Test
    public void testSetShortestPath() {
        PathStack newPath = new PathStack(1);
        newPath.push(otherPlace);
        testResult.setShortestPath(newPath);
        assertEquals("Polku on vääränmittainen", newPath.getTop(), testResult.getShortestPath().getTop());
        assertEquals("Polulla on väärä solmu", otherPlace.getName(), testResult.getShortestPath().getPlaces()[0].getName());
    }

    @Test
    public void testGetAlgorithm() {
        assertEquals("Algoritmia ei saa ulos", algorithm, testResult.getAlgorithm());
    }

    @Test
    public void testSetAlgorithm() {
        AlgorithmAlternative newAlgorithm = AlgorithmAlternative.DIJKSTRA;
        testResult.setAlgorithm(newAlgorithm);
        assertEquals("Algoritmia ei voi asettaa", newAlgorithm, testResult.getAlgorithm());
    }

    @Test
    public void testGetRunTimeNanoSecs() {
        assertEquals("Ajoaikaa ei saa ulos", (long)runtime, (long)testResult.getRunTimeNanoSecs());
    }

    @Test
    public void testSetRunTimeNanoSecs() {
        long newRunTime = 25000;
        testResult.setRunTimeNanoSecs(newRunTime);
        assertEquals("Ajoaikaa ei voi asettaa", (long)newRunTime, (long)testResult.getRunTimeNanoSecs());
    }

    @Test
    public void testGetName() {
        String expectedName = String.format("%s, paikasta %s paikkaan %s", 
                testResult.getFilePath(), 
                testResult.getStartPlace().getName(),
                testResult.getEndPlace().getName());
        assertEquals("Odotettua nimeä ei saa ulos", expectedName, testResult.getName());
    }
    
}
