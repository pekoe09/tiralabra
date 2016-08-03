package tiralabra;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceGraphMapperTest {
    
    public PlaceGraphMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void placeGraphMapperIsCreatedProperly() {
        PlaceGraphMapper mapper = new PlaceGraphMapper();        
        assertNotNull("PlaceGraphMapperilla ei ole paikkalistaa", mapper.getData());
    }

    @Test
    public void placeDataCanBeAddedIntoPlaceList() {        
        String[] placeData = {"Raatala", "60.532", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
        assertEquals("Paikkaa ei ole lisätty paikkalistalle", 1, instance.getData().size());
        assertEquals("Paikan nimi on väärin", "Raatala", instance.getData().get(0).getName());
    }


    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingErrorsIfNoName() {        
        String[] placeData = {" ", "60.532", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }    

    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingErrorsIfWrongLatitude() {        
        String[] placeData = {"Raatala", "a", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingErrorsIfNoLatitude() {        
        String[] placeData = {"Raatala", "", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }  
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingErrorsIfWrongLongitude() {        
        String[] placeData = {"Raatala", "60.532", "a"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingErrorsIfNoLongitude() {        
        String[] placeData = {"Raatala", "60.532", ""};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    } 
//    @Test
//    public void jotain() {
//        System.out.println("mapData");
//        String data = "";
//        Integer rowCounter = null;
//        PlaceGraphMapper instance = new PlaceGraphMapper();
//        instance.mapData(data, rowCounter);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetData() {
//        System.out.println("getData");
//        PlaceGraphMapper instance = new PlaceGraphMapper();
//        List<PlaceNode> expResult = null;
//        List<PlaceNode> result = instance.getData();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
