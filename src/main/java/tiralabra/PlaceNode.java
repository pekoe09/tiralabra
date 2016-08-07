package tiralabra;

public class PlaceNode {
    
    private String name;
    private Double latitude;
    private Double longitude;    
    private NeighbourNode[] neighbours;
    private double startDistance;
    private double endDistance;
    private PlaceNode pathPredecessor;
    private int heapindex;
    
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

    public double getStartDistance() {
        return startDistance;
    }

    public void setStartDistance(double startDistance) {
        if(startDistance < 0) {
            throw new IllegalArgumentException(String.format("Etäisyys (%f) on negatiivinen", startDistance));
        }
        this.startDistance = startDistance;
    }

    public double getEndDistance() {
        return endDistance;
    }

    public void setEndDistance(double endDistance) {
        if(endDistance < 0) {
            throw new IllegalArgumentException(String.format("Etäisyys (%f) on negatiivinen", endDistance));
        }
        this.endDistance = endDistance;
    }

    public PlaceNode getPathPredecessor() {
        return pathPredecessor;
    }

    public void setPathPredecessor(PlaceNode pathPredecessor) {
        this.pathPredecessor = pathPredecessor;
    }  

    public int getHeapindex() {
        return heapindex;
    }

    public void setHeapindex(int heapindex) {
        this.heapindex = heapindex;
    }   
    
    public double getDistanceToNeighbour(String neighbourName) {
        for (int i = 0; i < this.neighbours.length; i++) {
            if(this.neighbours[i].getNeighbour().getName().equals(neighbourName)){
                return this.neighbours[i].getDistance();
            }
        }
        return Integer.MAX_VALUE;
    }
}
