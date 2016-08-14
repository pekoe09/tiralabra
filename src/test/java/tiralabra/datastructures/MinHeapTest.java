package tiralabra.datastructures;

import tiralabra.datastructures.MinHeap;
import tiralabra.domain.NeighbourNode;
import tiralabra.domain.PlaceNode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {
    
    MinHeap testHeap;
    int testSize;
    PlaceNode node1, node2, node3, node4, node5;
    
    public MinHeapTest() { }
    
    @Before
    public void setUp() {
        testSize = 3;
        testHeap = new MinHeap(testSize);
        testHeap.insert(new PlaceNode("joku", 0.0, 0.0), 1.0);
        testHeap.insert(new PlaceNode("toinen", 1.0, 1.0), 2.0);
        testHeap.insert(new PlaceNode("muu", 2.0, 2.0), 3.0);
        
        node1 = new PlaceNode("paikka1", 1.0, 1.0);
        node2 = new PlaceNode("paikka2", 2.0, 2.0);
        node3 = new PlaceNode("paikka3", 3.0, 3.0);
        node4 = new PlaceNode("paikka4", 4.0, 4.0);
        node5 = new PlaceNode("paikka5", 5.0, 5.0);       
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionForNegativeSize() {
        MinHeap heap = new MinHeap(-1);
    }
    
    @Test
    public void leftReturnsLeftChildIndex() {
        int currentIndex = 17;
        int leftChildIndex = testHeap.left(currentIndex);
        assertEquals("Left-metodi palauttaa väärän indeksin", 35, leftChildIndex);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void leftThrowsExceptionForNegativeIndex() {
        int leftChildIndex = testHeap.left(-1);
    }

    @Test
    public void rightReturnsRightChildIndex() {
        int currentIndex = 17;
        int rightChildIndex = testHeap.right(currentIndex);
        assertEquals("Right-metodi palauttaa väärän indeksin", 36, rightChildIndex);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void rightThrowsExceptionForNegativeIndex() {
        int rightChildIndex = testHeap.right(-1);
    }
    
    @Test
    public void parentReturnsParentIndex() {
        int currentIndex = 17;
        int parentIndex = testHeap.parent(currentIndex);
        assertEquals("Parent-metodi palauttaa väärän indeksin", 8, parentIndex);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void parentThrowsExceptionForNegativeIndex() {
        int parentIndex = testHeap.parent(-1);
    }
    
    @Test
    public void elementCanBeInsertedIntoHeap() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node2, 2.0);
        
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 1, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 0, node2.getHeapindex());               
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void elementInsertThrowsExceptionIfDistanceIsNegative() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node2, -2.0);
    }
    
    @Test
    public void twoElementsInsertedInRightOrderEndUpInRightOrder() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node2, 2.0);
        heap.insert(node5, 5.0);

        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 2, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 0, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 1, node5.getHeapindex());  
    }
    
    @Test
    public void twoElementsInsertedInReverseOrderEndUpInRightOrder() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);

        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 2, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 0, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 1, node5.getHeapindex());  
    }
    
    @Test
    public void threeElementsInsertedEndUpInRightOrder() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);

        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 3, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 1, node5.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());  
    }
    
    @Test
    public void fourElementsInsertedEndUpInRightOrder() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);

        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 4, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 1", "paikka4", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 1, node4.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());  
    }
    
    @Test
    public void fiveElementsInsertedEndUpInRightOrder() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);
        heap.insert(node3, 3.0);

        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 4", "paikka4", heapArray[4].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 4, node4.getHeapindex());  
    }

    @Test
    public void exchangeSwitchesNodes() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);
        heap.insert(node3, 3.0);
        
        heap.exchange(1, 3);
        
        assertEquals("Indeksissä 1 on väärä alkio", "paikka5", heap.getHeap()[1].getNeighbour().getName());
        assertEquals("Indeksissä 3 on väärä alkio", "paikka3", heap.getHeap()[3].getNeighbour().getName());
        assertEquals("Indeksin 1 alkiolla on väärä indeksitieto", 1, node5.getHeapindex());
        assertEquals("Indeksin 3 alkiolla on väärä indeksitieto", 3, node3.getHeapindex());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void exchangeThrowsExceptionIfFirstIndexIsNegative() {
        testHeap.exchange(-1, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void exchangeThrowsExceptionIfSecondIndexIsNegative() {
        testHeap.exchange(0, -1);
    }

    @Test
    public void isEmptyReturnsTrueForEmptyHeap() {
        MinHeap newHeap = new MinHeap(0);
        assertTrue("Keon tyhjyyttä ei tunnisteta", newHeap.isEmpty());
    }
    
    @Test
    public void isEmptyReturnsFalseForNonEmptyHeap() {
        MinHeap newHeap = new MinHeap(1);
        newHeap.insert(node1, 1.0);
        assertTrue("Kekoa väitetään tyhjäksi", !newHeap.isEmpty());
    }
    
    @Test
    public void heapifyFixesHeapWhenFixableIndexHasRightChild() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);
        heap.insert(node3, 3.0);  
        // rikotaan kekoehto, jotta on jotain korjattavaa
        heap.exchange(1, 3);
        
        heap.heapify(1);
        
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 4", "paikka4", heapArray[4].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 4, node4.getHeapindex()); 
    }
    
    @Test
    public void heapifyFixesHeapWhenFixableIndexDoesntHaveRightChild() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);          
        // rikotaan kekoehto, jotta on jotain korjattavaa
        heap.exchange(1, 3);
        
        heap.heapify(1);
        
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 4, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 1", "paikka4", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 1, node4.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());  
    }
        
    @Test
    public void heapifyFixesHeapFromRoot() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0); 
        // rikotaan kekoehto, jotta on jotain korjattavaa
        heap.exchange(0, 2);
        
        heap.heapify(0);
        
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 4", "paikka4", heapArray[4].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 4, node4.getHeapindex());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void heapifyThrowsExceptionIfIndexIsNegative() {
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0); 
        
        heap.heapify(-1);
    }
    
    @Test
    public void delMinReturnsClosestPlace() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0); 
        
        PlaceNode returnedNode = heap.del_min();
        assertEquals("Muu kuin lähin paikka palautettu", node1, returnedNode);
    }
    
    @Test
    public void delMinMaintainsHeapCondition() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0); 
        
        PlaceNode returnedNode = heap.del_min();
        
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 4, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 2", "paikka4", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 0, node2.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex());   
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 2, node4.getHeapindex());
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void delMinThrowsExceptionIfHeapIsEmpty() {
        MinHeap heap = new MinHeap(5); 
        PlaceNode returnedNode = heap.del_min();
    }

    @Test
    public void elementKeyCanBeDecreased() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0);  
        
        heap.decrease_key(node5, 0.5);
        
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 0", "paikka5", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 1", "paikka1", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 3", "paikka3", heapArray[3].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 4", "paikka4", heapArray[4].getNeighbour().getName());
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 0, node5.getHeapindex());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 1, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());  
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 3, node3.getHeapindex()); 
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 4, node4.getHeapindex());
        assertTrue("Alkion 'paikka5' etäisyystieto ei ole oikein", Math.abs(heapArray[0].getDistance() - 0.5) < 0.00001);
    }
    
    @Test
    public void elementKeyNotDecreasedIfNewKeyNotSmaller() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0);  
        
        heap.decrease_key(node5, 5.0);      
        NeighbourNode[] heapArray = heap.getHeap();
        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka3' ei ole kekotaulukon indeksissä 1", "paikka3", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 4", "paikka4", heapArray[4].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + node1.getHeapindex(), 0, node1.getHeapindex()); 
        assertEquals("Alkion 'paikka3' indeksitieto on " + node3.getHeapindex(), 1, node3.getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + node2.getHeapindex(), 2, node2.getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + node5.getHeapindex(), 3, node5.getHeapindex());
        assertEquals("Alkion 'paikka4' indeksitieto on " + node4.getHeapindex(), 4, node4.getHeapindex()); 
        assertTrue("Alkion 'paikka5' etäisyystieto ei ole oikein", Math.abs(heapArray[3].getDistance() - 5.0) < 0.00001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyThrowsExceptionIfNodeIsNull() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0);  
        
        heap.decrease_key(null, 5.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyThrowsExceptionIfDistanceIsNegative() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0); 
        heap.insert(node3, 3.0);  
        
        heap.decrease_key(node5, -5.0);
    }
}
