package tiralabra;

import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.ReadTarget;
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
    public void neighboursCanBeAdded() {
        String[] placeData1 = {"Raatala", "60.532", "23.169", "Kiikala/32.6", "Kruusila/35.3"};
        String[] placeData2 = {"Kiikala", "60.463", "23.550", "Raatala/32.6", "Kruusila/13.0"};
        String[] placeData3 = {"Kruusila", "60.373", "23.488", "Raatala/35.3", "Kiikala/13.0"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        instance.addPlace(placeData1);
        instance.addPlace(placeData2);
        instance.addPlace(placeData3);
        
        instance.addNeighbours(placeData1);
        PlaceNode place1 = GraphUtils.findPlace(instance.getData(), placeData1[0]);
        assertNotNull("Paikkaa ei ole listalla", place1);
        assertNotNull("Paikalla ei ole naapuritietoja", place1.getNeighbours());
        assertEquals("Paikalla on väärä määrä naapureita", 2, place1.getNeighbours().length);
        assertEquals("Paikan 1. naapuri on väärä", placeData2[0], place1.getNeighbours()[0].getNeighbour().getName());
        assertTrue("Paikan 1. naapurin etäisyys on väärä", (32.6-place1.getNeighbours()[0].getDistance()<0.00001));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neighbourAddingThrowsExceptionIfInvalidDistance() {
        String[] placeData1 = {"Raatala", "60.532", "23.169", "Kiikala/"};
        String[] placeData2 = {"Kiikala", "60.463", "23.550", "Raatala/32.6"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        instance.addPlace(placeData1);
        instance.addPlace(placeData2);
        
        instance.addNeighbours(placeData1);
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
