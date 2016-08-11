package tiralabra;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DijkstraTest {
    
    Dijkstra dijkstra;
    PlaceNode node0, node1, node2, node3, node4, node5;
    
    public DijkstraTest() { }
    
    @Before
    public void setUp() {
        dijkstra = new Dijkstra(); 
        
        node0 = new PlaceNode("paikka0", 0.0, 0.0);
        node1 = new PlaceNode("paikka1", 1.0, 1.0);        
        node2 = new PlaceNode("paikka2", 2.0, 2.0);
        node3 = new PlaceNode("paikka3", 3.0, 3.0);
        node4 = new PlaceNode("paikka4", 4.0, 4.0);
        node5 = new PlaceNode("paikka5", 5.0, 5.0); 
        
        node0.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node1, 1.0),
                                new NeighbourNode(node2, 2.0),
                                new NeighbourNode(node3, 3.0),
                                new NeighbourNode(node4, 4.0),
                                new NeighbourNode(node5, 5.0)});
        node1.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node0, 1.0), 
                                new NeighbourNode(node4, 1.0)});
        node2.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node0, 2.0)});
        node3.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node0, 3.0)});
        node4.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node4, 4.0), 
                                new NeighbourNode(node5, 1.0)});
        node5.setNeighbours(new NeighbourNode[]{
                                new NeighbourNode(node0, 5.0), 
                                new NeighbourNode(node4, 1.0)});
    }

    @Test
    public void testInitialize() {
        List<PlaceNode> testGraph = new ArrayList<>();
        testGraph.add(node1);
        testGraph.add(node2);
        testGraph.add(node3);
        testGraph.add(node4);
        
        dijkstra.initialize(testGraph, node2);
        for(PlaceNode node : testGraph) {
            if(node == node2) {
                assertTrue("Aloitussolmulla etäisyystieto ei ole nolla", Math.abs(node.getStartDistance()) < 0.00001);
            } else {
                assertTrue(String.format("Solmun %s etäisyystieto ei ole Double.MAX_VALUE", node.getName()), Math.abs(node.getStartDistance() - Double.MAX_VALUE) < 0.00001);
            }
            assertNull("Edeltäjäsolu ei ole tyhjä", node.getPathPredecessor());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void initializeThrowsExceptionIfGraphIsNull() {
        dijkstra.initialize(null, node2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void initializeThrowsExceptionIfStartNodeIsNull() {
        List<PlaceNode> testGraph = new ArrayList<>();
        testGraph.add(node1);
        testGraph.add(node2);
        testGraph.add(node3);
        testGraph.add(node4);
        dijkstra.initialize(testGraph, null);
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
    
    @Test(expected = IllegalArgumentException.class)
    public void relaxThrowsExceptionIfNodeIsNull() {
        dijkstra.relax(null, node1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void relaxThrowsExceptionIfNeighbourNodeIsNull() {
        dijkstra.relax(node1, null);
    }
    
    @Test
    public void solveNodeTransfersNearestNodeToSolvedNodesAndMaintainsHeap() {
        MinHeap heap = new MinHeap(5); 
        node1.setStartDistance(1.0);
        node2.setStartDistance(2.0);
        node3.setStartDistance(3.0);
        node4.setStartDistance(4.0);
        node5.setStartDistance(5.0);
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);
        heap.insert(node3, 3.0);
        
        NeighbourNode[] heapArray = heap.getHeap();
        System.out.println("Kierros 0:");
        for(int i = 0; i < heap.getHeapsize(); i++) {
            NeighbourNode neighbour = heapArray[i];
            PlaceNode place = neighbour.getNeighbour();
            System.out.println("Kekoindeksi " + i + ": " + place.getName() + " " + place.getHeapindex() +
                " paikan etäisyys: " + place.getStartDistance() + " naapurin etäisyys: " + neighbour.getDistance());
        }
        
        dijkstra.solveNode(heap);
        
        System.out.println("Kierros 1:");
        for(int i = 0; i < heap.getHeapsize(); i++) {
            NeighbourNode neighbour = heapArray[i];
            PlaceNode place = neighbour.getNeighbour();
            System.out.println("Kekoindeksi " + i + ": " + place.getName() + " " + place.getHeapindex() +
                " paikan etäisyys: " + place.getStartDistance() + " naapurin etäisyys: " + neighbour.getDistance());
        }
        dijkstra.solveNode(heap);
        System.out.println("Kierros 2:");
        for(int i = 0; i < heap.getHeapsize(); i++) {
            NeighbourNode neighbour = heapArray[i];
            PlaceNode place = neighbour.getNeighbour();
            System.out.println("Kekoindeksi " + i + ": " + place.getName() + " " + place.getHeapindex() +
                " paikan etäisyys: " + place.getStartDistance() + " naapurin etäisyys: " + neighbour.getDistance());
        }
        
        assertEquals("Ratkaistujen solmujen taulukon pituus on väärä", 10, dijkstra.getSolvedNodes().length);
        assertEquals("Ratkaistujen solmujen lukumäärä on väärin", 2, dijkstra.getSolvedNodeIndex());
        assertEquals("Ensimmäinen ratkaistu solmu on väärä", "paikka1", dijkstra.getSolvedNodes()[0].getName());
        assertEquals("Toinen ratkaistu solmu on väärä", "paikka2", dijkstra.getSolvedNodes()[1].getName());
        assertEquals("Minimikeossa ei ole tasan kolmea alkiota jäljellä", 3, heap.getHeapsize());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 0", "paikka4", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 2", "paikka5", heapArray[2].getNeighbour().getName());
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 0, node4.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 2, node5.getHeapindex());
        assertTrue("Alkion 'paikka4' etäisyystieto on " + heapArray[0].getDistance(), Math.abs(heapArray[0].getDistance() - 2.0) < 0.00001); 
        assertTrue("Alkion 'paikka3' etäisyystieto on " + heapArray[1].getDistance(), Math.abs(heapArray[1].getDistance() - 3.0) < 0.00001);   
        assertTrue("Alkion 'paikka5' etäisyystieto on " + heapArray[2].getDistance(), Math.abs(heapArray[2].getDistance() - 5.0) < 0.00001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void solveNodeThrowsExceptionIfHeapIsNull() {
        dijkstra.solveNode(null);
    }
    
    @Test
    public void shortestPathCanBeGot() {
        List<PlaceNode> graph = new ArrayList<>();
        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        dijkstra.run(graph, node2);
        
//        for(PlaceNode node : dijkstra.getSolvedNodes()) {
//            System.out.println(node.getName() + " " + node.getStartDistance()
//            + " edeltäjä: " + node.getPathPredecessor().getName());
//        }
        
        Path path = dijkstra.getShortestPath(node2, node4);
        assertNotNull("Polkua ei saatu", path);
//        while(!path.isEmpty()) {
//            PlaceNode nextPlace = path.pop();
//            System.out.println(nextPlace.getName() + " " + nextPlace.getStartDistance());
//        }
    }
}
