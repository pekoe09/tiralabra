package tiralabra.search;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.datainput.IDataMapper;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PlaceNode;

public class PathSearcherTest {
    
    private PathSearcher testSearcher;
    private List<PlaceNode> graph;
    private String startPlaceName;
    private String endPlaceName;
    private String filePath;
    private long edgeCount;
    private int nodeCount;

    @Before
    public void setUp() {
    }

    @Test
    public void testRunAlgosWithGraph() {
//        PathSearchResult[] testResults = testSearcher.runAlgos(
//                graph, 
//                startPlaceName, 
//                endPlaceName, 
//                filePath, 
//                edgeCount, 
//                nodeCount);
//        assertNotNull("Algoritmiajo ei palauta lainkaan tuloksia", testResults);
//        assertEquals("Algoritmiajo ei tuottanut täsmälleen kahta tulosta", 2, testResults.length);
    }
    
    @Test
    public void testRunAlgosWithMapper() {

    }
    
}
