package tiralabra;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {
    
    MinHeap testHeap;
    int testSize;
    
    public MinHeapTest() { }
    
    @Before
    public void setUp() {
        testSize = 3;
        testHeap = new MinHeap(testSize);
        testHeap.insert(new PlaceNode("joku", 0.0, 0.0), 1.0);
        testHeap.insert(new PlaceNode("toinen", 1.0, 1.0), 2.0);
        testHeap.insert(new PlaceNode("muu", 2.0, 2.0), 3.0);
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
        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
        
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 1, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + heapArray[0].getNeighbour().getHeapindex(), 0, heapArray[0].getNeighbour().getHeapindex());               
    }
    
    @Test
    public void twoElementsInsertedInRightOrderEndUpInRightOrder() {
        System.out.println("Testi alkaa");
        PlaceNode node2 = new PlaceNode("paikka2", 2.0, 2.0);
        PlaceNode node5 = new PlaceNode("paikka5", 5.0, 5.0);
        
        MinHeap heap = new MinHeap(5);        
        heap.insert(node2, 2.0);
        heap.insert(node5, 5.0);
        System.out.println("Testi päättyy");
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 2, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + heapArray[0].getNeighbour().getHeapindex(), 0, heapArray[0].getNeighbour().getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + heapArray[1].getNeighbour().getHeapindex(), 1, heapArray[1].getNeighbour().getHeapindex());  
    }
    
    @Test
    public void twoElementsInsertedInReverseOrderEndUpInRightOrder() {
        System.out.println("Testi alkaa");
        PlaceNode node2 = new PlaceNode("paikka2", 2.0, 2.0);
        PlaceNode node5 = new PlaceNode("paikka5", 5.0, 5.0);
        
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        System.out.println("Testi päättyy");
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 2, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 0", "paikka2", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkion 'paikka2' indeksitieto on " + heapArray[0].getNeighbour().getHeapindex(), 0, heapArray[0].getNeighbour().getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + heapArray[1].getNeighbour().getHeapindex(), 1, heapArray[1].getNeighbour().getHeapindex());  
    }
    
    @Test
    public void threeElementsInsertedEndUpInRightOrder() {
        System.out.println("Testi alkaa");
        PlaceNode node2 = new PlaceNode("paikka2", 2.0, 2.0);
        PlaceNode node5 = new PlaceNode("paikka5", 5.0, 5.0);
        PlaceNode node1 = new PlaceNode("paikka1", 1.0, 1.0);
        
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        System.out.println("Testi päättyy");
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 3, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 1", "paikka5", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + heapArray[0].getNeighbour().getHeapindex(), 0, heapArray[0].getNeighbour().getHeapindex()); 
        assertEquals("Alkion 'paikka5' indeksitieto on " + heapArray[1].getNeighbour().getHeapindex(), 1, heapArray[1].getNeighbour().getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + heapArray[2].getNeighbour().getHeapindex(), 2, heapArray[2].getNeighbour().getHeapindex());   
         
    }
    
        @Test
    public void fourElementsInsertedEndUpInRightOrder() {
        System.out.println("Testi alkaa");
        PlaceNode node2 = new PlaceNode("paikka2", 2.0, 2.0);
        PlaceNode node5 = new PlaceNode("paikka5", 5.0, 5.0);
        PlaceNode node1 = new PlaceNode("paikka1", 1.0, 1.0);
        PlaceNode node4 = new PlaceNode("paikka4", 4.0, 4.0);
        
        MinHeap heap = new MinHeap(5); 
        heap.insert(node5, 5.0);
        heap.insert(node2, 2.0);
        heap.insert(node1, 1.0);
        heap.insert(node4, 4.0);
        System.out.println("Testi päättyy");
        NeighbourNode[] heapArray = heap.getHeap();
        
        assertEquals("Keon kokotieto on virheellinen", 4, heap.getHeapsize());
        assertEquals("Kekotaulukon pituus on väärä", 5, heapArray.length);
        assertEquals("Alkio 'paikka1' ei ole kekotaulukon indeksissä 0", "paikka1", heapArray[0].getNeighbour().getName());
        assertEquals("Alkio 'paikka4' ei ole kekotaulukon indeksissä 1", "paikka4", heapArray[1].getNeighbour().getName());
        assertEquals("Alkio 'paikka2' ei ole kekotaulukon indeksissä 2", "paikka2", heapArray[2].getNeighbour().getName());
        assertEquals("Alkio 'paikka5' ei ole kekotaulukon indeksissä 3", "paikka5", heapArray[3].getNeighbour().getName());
        assertEquals("Alkion 'paikka1' indeksitieto on " + heapArray[0].getNeighbour().getHeapindex(), 0, heapArray[0].getNeighbour().getHeapindex()); 
        assertEquals("Alkion 'paikka4' indeksitieto on " + heapArray[1].getNeighbour().getHeapindex(), 1, heapArray[1].getNeighbour().getHeapindex()); 
        assertEquals("Alkion 'paikka2' indeksitieto on " + heapArray[2].getNeighbour().getHeapindex(), 2, heapArray[2].getNeighbour().getHeapindex());   
        assertEquals("Alkion 'paikka5' indeksitieto on " + heapArray[3].getNeighbour().getHeapindex(), 3, heapArray[3].getNeighbour().getHeapindex());  
    }

//    @Test
//    public void elementCanBeInsertedIntoHeap() {
//        MinHeap heap = new MinHeap(5);        
//        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
//        heap.insert(new PlaceNode("paikka5", 5.0, 5.0), 5.0);
//        heap.insert(new PlaceNode("paikka3", 3.0, 3.0), 3.0);
//        heap.insert(new PlaceNode("paikka1", 1.0, 1.0), 1.0);
//        heap.insert(new PlaceNode("paikka4", 4.0, 4.0), 4.0);
//        
//        String[] expectedOrder = new String[]{"paikka1", "paikka2", "paikka4", "paikka3", "paikka5"};
//        NeighbourNode[] heapArray = heap.getHeap();
//        
//        assertEquals("Keon kokotieto on virheellinen", 5, heap.getHeapsize());
//        assertEquals("Keossa ei ole oikeaa määrää alkioita", 5, heapArray.length);
//        for(int i = 0; i < heapArray.length; i++) {
//            assertEquals("Alkio " + heapArray[i].getNeighbour().getName() + " on kekotaulukon indeksissä " + i, 
//                    expectedOrder[i], 
//                    heapArray[i].getNeighbour().getName());
//            System.out.println(heapArray[i].getNeighbour().getName() + ": " + heapArray[i].getNeighbour().getHeapindex());
//            assertEquals(("Alkion " + heapArray[i].getNeighbour().getName() + " indeksitieto on " + heapArray[i].getNeighbour().getHeapindex()),
//                    i, heapArray[i].getNeighbour().getHeapindex());
//        }        
//    }

//    @Test
//    public void exchangeSwitchesNodes() {
//        MinHeap heap = new MinHeap(5);        
//        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
//        heap.insert(new PlaceNode("paikka5", 5.0, 5.0), 5.0);
//        heap.insert(new PlaceNode("paikka3", 3.0, 3.0), 3.0);
//        heap.insert(new PlaceNode("paikka1", 1.0, 1.0), 1.0);
//        heap.insert(new PlaceNode("paikka4", 4.0, 4.0), 4.0);        
//        
//        heap.exchange(1, 2);
//        
//        assertEquals("Indeksissä 1 on väärä alkio", "paikka4", heap.getHeap()[1].getNeighbour().getName());
//        assertEquals("Indeksissä 2 on väärä alkio", "paikka2", heap.getHeap()[2].getNeighbour().getName());
//        assertEquals("Indeksin 1 alkiolla on väärä indeksitieto", 1, heap.getHeap()[1].getNeighbour().getHeapindex());
//        assertEquals("Indeksin 2 alkiolla on väärä indeksitieto", 2, heap.getHeap()[2].getNeighbour().getHeapindex());
//    }
    
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
        newHeap.insert(new PlaceNode("joku", 0.0, 0.0), 1.0);
        assertTrue("Kekoa väitetään tyhjäksi", !newHeap.isEmpty());
    }

    @Test
    public void delMinReturnsClosestPlace() {
        MinHeap heap = new MinHeap(5);        
        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
        heap.insert(new PlaceNode("paikka5", 5.0, 5.0), 5.0);
        heap.insert(new PlaceNode("paikka3", 3.0, 3.0), 3.0);
        PlaceNode closest = new PlaceNode("paikka1", 1.0, 1.0);
        heap.insert(closest, 1.0);
        heap.insert(new PlaceNode("paikka4", 4.0, 4.0), 4.0); 
        
        PlaceNode returnedNode = heap.del_min();
        assertEquals("Muu kuin lähin paikka palautettu", closest, returnedNode);
    }

//    @Test
//    public void elementKeyCanBeDecreased() {
//        MinHeap heap = new MinHeap(5);        
//        PlaceNode node3 = new PlaceNode("paikka3", 3.0, 3.0);
//        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
//        heap.insert(new PlaceNode("paikka5", 5.0, 5.0), 5.0);
//        heap.insert(node3, 3.0);
//        heap.insert(new PlaceNode("paikka1", 1.0, 1.0), 1.0);
//        heap.insert(new PlaceNode("paikka4", 4.0, 4.0), 4.0);   
//        
//        heap.decrease_key(node3, 0.5);
//        
//        System.out.println(heap.getHeap().length);
//        for(int i = 0; i < heap.getHeap().length; i++) {
//            System.out.println(heap.getHeap()[i].getNeighbour().getName() + ": " + heap.getHeap()[i].getDistance());
//        }
//        
//        //assertEquals("Paikka 3 ei ole juurielementtinä", node3, heap.getHeap()[0].getNeighbour());
//    }
    
    @Test
    public void elementKeyNotDecreasedIfNewKeyNotSmaller() {
        MinHeap heap = new MinHeap(5);        
        PlaceNode node3 = new PlaceNode("paikka3", 3.0, 3.0);
        heap.insert(new PlaceNode("paikka2", 2.0, 2.0), 2.0);
        heap.insert(new PlaceNode("paikka5", 5.0, 5.0), 5.0);
        heap.insert(node3, 3.0);
        heap.insert(new PlaceNode("paikka1", 1.0, 1.0), 1.0);
        heap.insert(new PlaceNode("paikka4", 4.0, 4.0), 4.0);  
        
        double nodeOriginalKey = heap.getHeap()[node3.getHeapindex()].getDistance();
        heap.decrease_key(node3, 10.0);
        assertTrue("", Math.abs(nodeOriginalKey - heap.getHeap()[node3.getHeapindex()].getDistance())< 0.00001);
        
    }
}
