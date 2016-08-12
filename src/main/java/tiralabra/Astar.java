package tiralabra;

import java.util.List;

/**
 * Astar-luokka sisältää A* -algoritmin logiikan.
 * @author juha.kangas_vaakapar
 */
public class Astar {
    
    private PlaceNode[] solvedNodes;
    private int solvedNodeIndex;
    private int nodeAmount;
    
    public Astar() {
        this.solvedNodes = new PlaceNode[10];
    }
    
    //ERO: endNode parametrina
    public void run(List<PlaceNode> graph, PlaceNode startNode, PlaceNode endNode) {
        //ERO: endNode parametrina
        initialize(graph, startNode, endNode);
        solvedNodes = new PlaceNode[graph.size()];
        solvedNodeIndex = 0;
        nodeAmount = graph.size();
        MinHeap heap = new MinHeap(graph.size());
        for(PlaceNode node : graph) {
            //ERO: keon avaimena etäisyys alkuun + etäisyys loppuun
            heap.insert(node, node.getStartDistance() + node.getEndDistance());
        }        
        while(!nodeIsSolved(endNode)) {
            solveNode(heap);
        }
    }
    
    //ERO: tätä ei ole Dijkstrassa lainkaan [pitäisikö olla?]
    public boolean nodeIsSolved(PlaceNode node) {
        if(node == null) {
            throw new IllegalArgumentException("Etsittävä solmu ei voi olla null");
        }
        for(int i = 0; i < solvedNodes.length; i++) {
            if(solvedNodes[i].getName().equals(node.getName())) {
                return true;
            }
        }
        return false;
    }
    //ERO: endNode parametrina
    public void initialize(List<PlaceNode> graph, PlaceNode startNode, PlaceNode endNode) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Lähtösolmu ei voi olla null");
        }
        if(endNode == null) {
            throw new IllegalArgumentException("Maalisolmu ei voi olla null");
        }
        for(PlaceNode node : graph) {
            node.setStartDistance(Double.MAX_VALUE);
            //ERO: asetetaan myös endDistance
            node.setEndDistance(calculateHeuristic(node, endNode));
            node.setPathPredecessor(null);
        }
        startNode.setStartDistance(0.0);
    }
    
    public void relax(PlaceNode node, PlaceNode neighbour) {
        if(node == null || neighbour == null) {
            throw new IllegalArgumentException("Solmu ei voi olla null");
        }
        double distanceToNeighbour = node.getDistanceToNeighbour(neighbour.getName());
        if (neighbour.getStartDistance() > node.getStartDistance() + distanceToNeighbour) {
            neighbour.setStartDistance(node.getStartDistance() + distanceToNeighbour);
            neighbour.setPathPredecessor(node);
        }
    }
    
    public void solveNode(MinHeap heap) {
        if(heap == null) {
            throw new IllegalArgumentException("Keko ei voi olla null");
        }
        PlaceNode nearestNode = heap.del_min();
        solvedNodes[solvedNodeIndex] = nearestNode;
        solvedNodeIndex++;
        for(NeighbourNode neighbour : nearestNode.getNeighbours()) {
            relax(nearestNode, neighbour.getNeighbour());
            //ERO: keon avaimena startDistance + endDistance
            heap.decrease_key(neighbour.getNeighbour(), 
                neighbour.getNeighbour().getStartDistance() + neighbour.getNeighbour().getEndDistance());
        }
    }
    
    public Path getShortestPath(PlaceNode startNode, PlaceNode endNode) {
        Path path = new Path(nodeAmount);
        PlaceNode predecessorNode = endNode.getPathPredecessor();
        while (predecessorNode != startNode) {
            path.push(predecessorNode);
            predecessorNode = predecessorNode.getPathPredecessor();
        }
        return path;
    }
    
    public PlaceNode[] getSolvedNodes() {
        return solvedNodes;
    }
    
    public int getSolvedNodeIndex() {
        return solvedNodeIndex;
    }

    /**
     * Laskee kahden paikkasolmun sijaintikoordinaattien välimatkan kilometreinä.
     * (https://rosettacode.org/wiki/Haversine_formula#Java)
     * @param node1
     * @param node2
     * @return Annettujen paikkasolmujen välimatka kilometreinä.
     * @throws IllegalArgumentException Jos jompi kumpi paikkasolmuparametreista on null.
     */
    public double calculateHeuristic(PlaceNode node1, PlaceNode node2) {
        if(node1 == null || node2 == null) {
            throw new IllegalArgumentException("Paikkasolmu ei voi olla null");
        }
        double node1Latitude = node1.getLatitude();
        double node1Longitude = node1.getLongitude();
        double node2Latitude = node2.getLatitude();
        double node2Longitude = node2.getLongitude();
        
        final double R = 6371.0; // In kilometers
        double deltaLatitude = Math.toRadians(node2Latitude - node1Latitude);
        double deltaLongitude = Math.toRadians(node2Longitude - node1Longitude);
        node1Latitude = Math.toRadians(node1Latitude);
        node2Latitude = Math.toRadians(node2Latitude);
 
        double a = Math.pow(Math.sin(deltaLatitude / 2),2) 
            + Math.pow(Math.sin(deltaLongitude / 2),2) 
            * Math.cos(node1Latitude) 
            * Math.cos(node2Latitude);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
