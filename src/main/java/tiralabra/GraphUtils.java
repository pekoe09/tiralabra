package tiralabra;

import java.util.List;

public class GraphUtils {
    
    public static PlaceNode findPlace(List<PlaceNode> graph, String name) {
        for(int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getName().equals(name)) {
                return graph.get(i);
            }
        }
        throw new IllegalArgumentException(String.format("Paikkaa %s ei löydy", name));
    }
}
