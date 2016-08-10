package tiralabra;

public class Path {
    
    PlaceNode[] places;
    int top;
    
    public Path(int size) {
        top = -1;
        places = new PlaceNode[size];
    }
    
    public void push(PlaceNode place) {
        top++;
        places[top] = place;
    }
    
    public PlaceNode pop() {
        PlaceNode nextPlace = places[top];
        top--;
        return nextPlace;
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
}
