package tiralabra.datastructures;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.domain.INamedObject;
import tiralabra.domain.PlaceNode;

public class NamedArrayListTest {
    
    public NamedArrayListTest() {
    }
    
    private NamedArrayList testList;
    private PlaceNode node1;
    private PlaceNode node2;
    private PlaceNode node3;
    private PlaceNode node4;
    PlaceNode[] nodes;
    
    @Before
    public void setUp() {
        node1 = new PlaceNode("joku", 0.0, 0.0);
        node2 = new PlaceNode("muu", 1.0, 1.0);
        node3 = new PlaceNode("jossain", 2.0, 2.0);
        node4 = new PlaceNode("uusi", 3.0, 3.0);
        nodes = new PlaceNode[]{node1, node2, node3, node4};
        
        testList = new NamedArrayList();
        testList.add(node1);
        testList.add(node2);
        testList.add(node3);
    }
    
    @Test
    public void testGetArray() {
        assertNotNull("Taulukko on null", testList.getArray());        
    }

    @Test
    public void testSize() {
        assertEquals("Taulukon koko on väärä", 3, testList.size());
    }

    @Test
    public void namedArrayListIsConstructedProperly() {
        NamedArrayList newList = new NamedArrayList();
        assertNotNull("Taulukko on null", newList.getArray());
        assertEquals("Taulukon pituus on väärä", 100, newList.getArray().length);
        assertEquals("Taulukon ilmoitettu koko on väärä", 0, newList.size());
    }
    
    @Test
    public void testAdd() {
        testList.add(node4);
        
        boolean allNodesFound = true;
        for(PlaceNode node : nodes) {
            boolean nodeFound = false;
            for(INamedObject arrayNode : testList.getArray()) {
                if(arrayNode.getName().equals(node.getName())) {
                    nodeFound = true;
                    break;
                }
            }
            if(!nodeFound) {
                allNodesFound = false;
                break;
            }
        }
        
        assertEquals("Taulukon ilmoitettu koko on väärä", 4, testList.size());
        assertTrue("Kaikkia solmuja ei löydy taulukosta", allNodesFound);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addThrowsExceptionWhenAddingNull() {
        testList.add(null);
    }

    @Test
    public void testGet() {
        INamedObject[] array = testList.getArray();
        boolean itemsGotMatchIndex = true;
        for(int i = 0; i < testList.size(); i++) {
            INamedObject namedObject = array[i];
            INamedObject testObject = testList.get(i);
            if(!namedObject.getName().equals(testObject.getName())) {
                itemsGotMatchIndex = false;
                break;
            }
        }
        assertTrue("Get tuottaa jollakin indeksillä väärän olion", itemsGotMatchIndex);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getThrowsExceptionWhenGivenNegativeIndex() {
        testList.get(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getThrowsExceptionWhenGivenIndexLargerThanBound() {
        testList.get(testList.size());
    }

    @Test
    public void testFindByName() {
        PlaceNode foundNode = (PlaceNode)testList.findByName(node2.getName());
        
        assertEquals("Nimellä haettuna palautui väärä solmu", node2.getName(), foundNode.getName());
    }
    
    @Test
    public void findByNameReturnsNullWhenObjectNotFound() {
        PlaceNode foundNode = (PlaceNode)testList.findByName("random nimi");
        assertNull("Olemattomalla nimellä tehty haku palautti solmun", foundNode);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void findByNameThrowsExceptionWhenGivenNullName() {
        testList.findByName(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void findByNameThrowsExceptionWhenGivenWhitespaceName() {
        testList.findByName("   ");
    }
    
    @Test
    public void arrayIsGrownAsNeeded() {
        NamedArrayList newList = new NamedArrayList();
        for(int i = 0; i < 100; i++) {
            newList.add(node1);
        }
        assertEquals("Taulukko ei ole pysynyt alkuperäisen kokoisena", 100, newList.getArray().length);
        newList.add(node1);
        assertEquals("Taulukkoa ei ole kasvatettu", 200, newList.getArray().length);
    }
    
}
