package tiralabra.search;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import tiralabra.datainput.IDataMapper;
import tiralabra.datainput.IGraphMapper;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.NeighbourNode;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PlaceNode;

public class PathSearcherTest {
    
    private PathSearcher testSearcher;
    private IGraphMapper mapper;
    private NamedArrayList graph;
    private String startPlaceName;
    private String endPlaceName;
    private String filePath;
    private long edgeCount;
    private int nodeCount;
    PlaceNode helsinki, espoo, vantaa, kauniainen, sipoo;

    @Before
    public void setUp() {
        testSearcher = new PathSearcher();
        
        helsinki = new PlaceNode("Helsinki", 60.171160, 24.932580);
        espoo = new PlaceNode("Espoo", 60.206780, 24.655780);
        vantaa = new PlaceNode("Vantaa", 60.289348, 25.029676);
        kauniainen = new PlaceNode("Kauniainen", 60.211642, 24.729048);
        sipoo = new PlaceNode("Sipoo", 60.376721, 25.260191);
        helsinki.setNeighbours(new NeighbourNode[] {
                                new NeighbourNode(espoo, 15.805679),
                                new NeighbourNode(vantaa, 14.193181),
                                new NeighbourNode(sipoo, 29.133081)});
        espoo.setNeighbours(new NeighbourNode[] {
                                new NeighbourNode(helsinki, 15.805679),
                                new NeighbourNode((vantaa), 22.582160),
                                new NeighbourNode(kauniainen, 4.083670)});
        vantaa.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(espoo, 22.582160),
                                new NeighbourNode(helsinki, 14.193181),
                                new NeighbourNode(sipoo, 15.979494)});
        kauniainen.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(espoo, 4.083670)});
        sipoo.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(helsinki, 29.133081),
                                new NeighbourNode(vantaa, 15.979494)});
        
        graph = new NamedArrayList();
        graph.add(helsinki);
        graph.add(espoo);
        graph.add(vantaa);
        graph.add(kauniainen);
        graph.add(sipoo);
        
        startPlaceName = helsinki.getName();
        endPlaceName = espoo.getName();
        edgeCount = 7;
        nodeCount = 5;
        filePath = "joku/polku";
        
        mapper = Mockito.mock(PlaceGraphMapper.class);
        when(mapper.getData()).thenReturn(graph);
        when(mapper.getNumberOfEdges()).thenReturn(edgeCount);
    }

    @Test
    public void testRunAlgosWithGraph() {
        PathSearchResult[] testResults = testSearcher.runAlgos(
                graph, 
                startPlaceName, 
                endPlaceName, 
                filePath, 
                edgeCount, 
                nodeCount);
        assertNotNull("Algoritmiajo ei palauta lainkaan tuloksia", testResults);
        assertEquals("Algoritmiajo ei tuottanut täsmälleen kahta tulosta", 2, testResults.length);
        assertEquals("Ensimmäisen tuloksen kaariluku on väärin", (long)edgeCount, (long)testResults[0].getEdgeCount());
        assertEquals("Toisen tuloksen kaariluku on väärin", (long)edgeCount, (long)testResults[1].getEdgeCount());
        assertEquals("Ensimmäisen tuloksen solmuluku on väärin", nodeCount, testResults[0].getNodeCount());
        assertEquals("Toisen tuloksen solmuluku on väärin", nodeCount, testResults[1].getNodeCount());
        assertEquals("Ensimmäisen tuloksen tiedostopolku on väärin", filePath, testResults[0].getFilePath());
        assertEquals("Toisen tuloksen tiedostopolku on väärin", filePath, testResults[1].getFilePath());
    }
    
    @Test
    public void testRunAlgosWithMapper() {
    PathSearchResult[] testResults = testSearcher.runAlgos(
                mapper, 
                startPlaceName, 
                endPlaceName, 
                filePath);
        assertNotNull("Algoritmiajo ei palauta lainkaan tuloksia", testResults);
        assertEquals("Algoritmiajo ei tuottanut täsmälleen kahta tulosta", 2, testResults.length);
        assertEquals("Ensimmäisen tuloksen kaariluku on väärin", (long)edgeCount, (long)testResults[0].getEdgeCount());
        assertEquals("Toisen tuloksen kaariluku on väärin", (long)edgeCount, (long)testResults[1].getEdgeCount());
        assertEquals("Ensimmäisen tuloksen solmuluku on väärin", nodeCount, testResults[0].getNodeCount());
        assertEquals("Toisen tuloksen solmuluku on väärin", nodeCount, testResults[1].getNodeCount());
        assertEquals("Ensimmäisen tuloksen tiedostopolku on väärin", filePath, testResults[0].getFilePath());
        assertEquals("Toisen tuloksen tiedostopolku on väärin", filePath, testResults[1].getFilePath());
    }
    
}
