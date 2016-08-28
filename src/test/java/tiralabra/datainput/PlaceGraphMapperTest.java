package tiralabra.datainput;

import tiralabra.domain.PlaceNode;
import tiralabra.enums.ReadTarget;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PlaceGraphMapperTest {
    
    private PlaceGraphMapper mapper;
    private String[] placeData;
    private String[] placeData1;
    private String[] placeData2;
    private String[] placeData3;
    
    @Before
    public void setUp() {
        mapper = new PlaceGraphMapper();
        placeData = new String[]{"Raatala", "60.532", "23.169"};
        placeData1 = new String[]{"Raatala", "60.532", "23.169", "Kiikala/32.6", "Kruusila/35.3"};
        placeData2 = new String[]{"Kiikala", "60.463", "23.550", "Raatala/32.6", "Kruusila/13.0"};
        placeData3 = new String[]{"Kruusila", "60.373", "23.488", "Raatala/35.3", "Kiikala/13.0"};
    }
    
    @Test
    public void placeGraphMapperIsCreatedProperly() {
        PlaceGraphMapper newMapper = new PlaceGraphMapper();        
        assertNotNull("PlaceGraphMapperilla ei ole paikkalistaa", newMapper.getData());
    }

    @Test
    public void placeDataCanBeAddedIntoPlaceList() {        
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
        assertEquals("Paikkaa ei ole lisätty paikkalistalle", 1, instance.getData().size());
        assertEquals("Paikan nimi on väärin", "Raatala", instance.getData().get(0).getName());
    }


    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfNoName() {        
        placeData = new String[]{" ", "60.532", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }    

    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfWrongLatitude() {        
        placeData = new String[]{"Raatala", "a", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfNoLatitude() {        
        placeData = new String[]{"Raatala", "", "23.169"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }  
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfWrongLongitude() {        
        placeData = new String[]{"Raatala", "60.532", "a"};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void placeDataAddingThrowsExceptionIfNoLongitude() {        
        placeData = new String[]{"Raatala", "60.532", ""};
        PlaceGraphMapper instance = new PlaceGraphMapper();
        
        instance.addPlace(placeData);        
    }     
    
    @Test
    public void neighboursCanBeAdded() {
        PlaceGraphMapper instance = new PlaceGraphMapper();
        instance.addPlace(placeData1);
        instance.addPlace(placeData2);
        instance.addPlace(placeData3);
        
        instance.addNeighbours(placeData1);
        PlaceNode place1 = (PlaceNode)instance.getData().findByName(placeData1[0]);
        assertNotNull("Paikkaa ei ole listalla", place1);
        assertNotNull("Paikalla ei ole naapuritietoja", place1.getNeighbours());
        assertEquals("Paikalla on väärä määrä naapureita", 2, place1.getNeighbours().length);
        assertEquals("Paikan 1. naapuri on väärä", placeData2[0], place1.getNeighbours()[0].getNeighbour().getName());
        assertTrue("Paikan 1. naapurin etäisyys on väärä", (32.6-place1.getNeighbours()[0].getDistance()<0.00001));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neighbourAddingThrowsExceptionIfInvalidDistance() {
        placeData1 = new String[]{"Raatala", "60.532", "23.169", "Kiikala/"};
        placeData2 = new String[]{"Kiikala", "60.463", "23.550", "Raatala/32.6"};
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
    
    @Test
    public void testGetNumberOfEdges() {
        String data = "Raatala;60.532;23.169";
        String data2 = "Kiikala;60.532;23.169;Kruusila/13.0";
        String data3 = "Kruusila;60.532;23.169;Kiikala/13.0";
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        instance.mapData(data, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 1, instance.getData().size());
        instance.mapData(data2, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 2, instance.getData().size());
        instance.mapData(data3, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 3, instance.getData().size());
        instance.mapData(data, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 3, instance.getData().size());
        instance.mapData(data2, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 3, instance.getData().size());
        instance.mapData(data3, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        assertEquals("Verkossa on väärä määrä solmuja", 3, instance.getData().size());
        assertEquals("Verkossa väitetään olevan väärä määrä kaaria", 1, instance.getNumberOfEdges());
    }
    
    @Test
    public void testToString() {
        String data = "Raatala;60.532;23.169;";
        String data2 = "Kiikala;60.532;23.169;Kruusila/13.0";
        String data3 = "Kruusila;60.532;23.169;Kiikala/13.0";
        Integer rowCounter = 1;
        PlaceGraphMapper instance = new PlaceGraphMapper();
        instance.mapData(data, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        assertEquals("Verkon kuvausteksti on väärin", "verkossa on 1 solmu ja 0 kaarta", instance.toString());
        instance.mapData(data2, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        instance.mapData(data3, rowCounter++, ReadTarget.NODE_BASIC_DATA);
        instance.mapData(data, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        instance.mapData(data2, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        instance.mapData(data3, rowCounter++, ReadTarget.NODE_NEIGHBOUR_DATA);
        assertEquals("Verkon kuvausteksti on väärin", "verkossa on 3 solmua ja 1 kaari", instance.toString());
    }
    
    @Test
    public void testResetMapper() {
        mapper.addPlace(placeData1);
        mapper.addPlace(placeData2);
        mapper.resetMapper();
        assertEquals("Verkkoon on vielä jäänyt solmuja", 0, mapper.getData().size());
        assertEquals("Verkossa väitetään olevan kaaria", 0, mapper.getNumberOfEdges());
    }
}
