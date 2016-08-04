package tiralabra;

public class PlaceNode {
    
    private String name;
    private Double latitude;
    private Double longitude;    
    private NeighbourNode[] neighbours;
    
    public PlaceNode(String name, Double latitude, Double longitude) {
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Paikan nimi on tyhjä");
        }
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        if(latitude == null) {
            throw new IllegalArgumentException("Leveysaste on tyhjä");
        }
        if(Math.abs(latitude) > 90) {
            throw new IllegalArgumentException(String.format("Leveysaste (%f) on suurempi kuin 90", latitude));
        }
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        if(longitude == null) {
            throw new IllegalArgumentException("Pituusaste on tyhjä");
        }
        if(Math.abs(longitude) > 180) {
            throw new IllegalArgumentException(String.format("Pituusaste (%f) on suurempi kuin 180", longitude));
        }
        this.longitude = longitude;
    }

    public NeighbourNode[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(NeighbourNode[] neighbours) {
        this.neighbours = neighbours;
    }
    
}
