package tiralabra;

import java.util.ArrayList;
import java.util.List;

public class PlaceGraphMapper implements IDataMapper {
    
    private List<PlaceNode> graph;
    
    public PlaceGraphMapper() {
        this.graph = new ArrayList<>();
    }

    @Override
    public void mapData(String data, Integer rowCounter) {   
        if(data == null) {
            throw new IllegalArgumentException("Dataa ei ole!");
        }
        String[] placeData = data.split(";");
        if(placeData.length < 3) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d on liian vähän tietoa:\n %s", rowCounter, data));
        }        
        try {
            addPlace(placeData); 
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d epäkuranttia dataa:\n %s", rowCounter, data));
        }
    }

    public void addPlace(String[] placeData) {
        PlaceNode newPlace = null;
        String placeName = placeData[0];
        try {
            Double latitude = Double.parseDouble(placeData[1]);
            Double longitude = Double.parseDouble(placeData[2]);
            newPlace = new PlaceNode(placeName, latitude, longitude);
        } catch(Exception exc) {
            throw new IllegalArgumentException("Paikkadatassa virhe");
        }
        graph.add(newPlace);       
    }
    
    @Override
    public List<PlaceNode> getData() {
        return this.graph;
    }    
}
