package tiralabra.domain;

public class NeighbourNode {
    
    private PlaceNode neighbour;
    private double distance;
    
    public NeighbourNode(PlaceNode neighbour, double distance) {
        setNeighbour(neighbour);
        setDistance(distance);
    }

    public PlaceNode getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(PlaceNode neighbour) {
        if(neighbour == null) {
            throw new IllegalArgumentException("Naapuri ei voi olla tyhjä!");
        }
        this.neighbour = neighbour;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if(distance < 0) {
            throw new IllegalArgumentException("Välimatka ei voi olla negatiivinen!");
        }
        this.distance = distance;
    }
}
