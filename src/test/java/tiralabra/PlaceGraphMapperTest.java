package tiralabra;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceGraphMapperTest {
    
    public PlaceGraphMapperTest() { }    
    
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
    public void placeDataAddingThrowsExceptionIfNoName() {        
        String[] placeData = {" ", "60.532", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }    

    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfWrongLatitude() {        
        String[] placeData = {"Raatala", "a", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfNoLatitude() {        
        String[] placeData = {"Raatala", "", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }  
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfWrongLongitude() {        
        String[] placeData = {"Raatala", "60.532", "a"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfNoLongitude() {        
        String[] placeData = {"Raatala", "60.532", ""};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }     
    
    @Test
    public void mapDataCanAddPlace() {
        String data = "Raatala;60.532;23.169";
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.mapData(data, rowCounter, ReadTarget.NODE_BASIC_DATA);
        assertEquals("Paikkaa ei ole lisätty paikkalistalle", 1, instance.getData().size());
        assertEquals("Paikan nimi on väärin", "Raatala", instance.getData().get(0).getName());
    }    
        
    @Test(expected = IllegalArgumentException.class)
    public void mapDataThrowsErrorIfDataIsNull() {
        String data = null;
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.mapData(data, rowCounter, ReadTarget.NODE_BASIC_DATA);
    }
         
    @Test(expected = IllegalArgumentException.class)
    public void mapDataThrowsErrorIfDataIsTooSparse() {
        String data = "Raatala;60.532";
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.mapData(data, rowCounter, ReadTarget.NODE_BASIC_DATA);
    }   
 
    @Test(expected = IllegalArgumentException.class)
    public void mapDataThrowsErrorIfDataIsInvalid() {
        String data = "Raatala;60.532;180.1";
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.mapData(data, rowCounter, ReadTarget.NODE_BASIC_DATA);
    }     
}
