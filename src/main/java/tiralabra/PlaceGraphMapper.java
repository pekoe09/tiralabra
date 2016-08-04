package tiralabra;

import java.util.ArrayList;
import java.util.List;

public class PlaceGraphMapper implements IDataMapper {
    
    private List<PlaceNode> graph;
    private final int BASIC_FIELDS = 3;
    
    public PlaceGraphMapper() {
        this.graph = new ArrayList<>();
    }

    @Override
    public void mapData(String data, Integer rowCounter, ReadTarget target) {   
        if(data == null) {
            throw new IllegalArgumentException("Dataa ei ole!");
        }
        String[] placeData = data.split(";");
        if(placeData.length < BASIC_FIELDS) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d on liian vähän tietoa:\n %s", rowCounter, data));
        }        
        try {
            if(target == ReadTarget.NODE_BASIC_DATA) {
                addPlace(placeData); 
            } else {
                addNeighbours(placeData);
            }
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d epäkuranttia dataa:\n%s\n%s", rowCounter, exc.getMessage(), data));
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

    public void addNeighbours(String[] placeData) {
        PlaceNode place = GraphUtils.findPlace(graph, (String)placeData[0]);        
        NeighbourNode[] neighbours = new NeighbourNode[placeData.length - BASIC_FIELDS];
        for(int i = BASIC_FIELDS; i < placeData.length; i++) {
            String[] neighbourData = placeData[i].split("/");
            PlaceNode neighbour = GraphUtils.findPlace(graph, neighbourData[0]);
            try {
                double distance = Double.parseDouble(neighbourData[1]);
                neighbours[i - BASIC_FIELDS] = new NeighbourNode(neighbour, distance);   
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exc) {
                throw new IllegalArgumentException(String.format("Naapurin %s etäisyys on virheellinen", neighbourData[0]));
            }                     
        }
        place.setNeighbours(neighbours);
    }
}
