package tiralabra;

import java.util.List;

public class Dijkstra {
    
    PlaceNode[] solvedNodes;
    int solvedNodeIndex;
    
    public void run(List<PlaceNode> graph, PlaceNode startNode) {
        initialize(graph, startNode);
        solvedNodes = new PlaceNode[graph.size()]; 
        solvedNodeIndex = 0;
        MinHeap heap = new MinHeap(graph.size());
        for (PlaceNode node : graph) {
            heap.insert(node, node.getStartDistance());
        }
        while (!heap.isEmpty()) {
            solveNode(heap);
        }
    }
    
    public void initialize(List<PlaceNode> graph, PlaceNode startNode) {
        for(PlaceNode node : graph){
            node.setStartDistance(Double.MAX_VALUE);
            node.setPathPredecessor(null);
        }
        startNode.setStartDistance(0.0);
    }
    
    public void relax(PlaceNode node, PlaceNode neighbour) {
        double distanceToNeighbour = node.getDistanceToNeighbour(neighbour.getName());
        if (neighbour.getStartDistance() > node.getStartDistance() + distanceToNeighbour) {
            neighbour.setStartDistance(node.getStartDistance() + distanceToNeighbour);
        }
    }
    
    public void solveNode(MinHeap heap) {
        PlaceNode nearestNode = heap.del_min();
        solvedNodes[solvedNodeIndex] = nearestNode;
        solvedNodeIndex++;
        for(NeighbourNode neighbour : nearestNode.getNeighbours()) {
            relax(nearestNode, neighbour.getNeighbour());
            heap.decrease_key(neighbour.getNeighbour(), neighbour.getNeighbour().getStartDistance());
        }
    }
}
