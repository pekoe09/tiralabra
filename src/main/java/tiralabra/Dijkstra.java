package tiralabra;

import java.util.List;

/**
 * DIJKSTRA-luokka sisältää DIJKSTRA-algoritmin logiikan.
 */
public class Dijkstra {
    
    private PlaceNode[] solvedNodes;
    private int solvedNodeIndex;
    private int nodeAmount;
    
    public Dijkstra() {
        this.solvedNodes = new PlaceNode[10];
    }
    
    /**
     * Run-metodilla suoritetaan DIJKSTRA-algoritmin mukainen lyhimmän polun etsintä
 annetulla verkolla ja aloitussolmulla.
     * @param graph     Verkko kuvattuna PlaceNode-olioita sisältävänä List-oliona.
     * @param startNode Polun alkupisteenä oleva paikka PlaceNode-oliona.
     * @param endNode   Polun päätepisteenä oleva paikka PlaceNode-oliona.
     * @param algorithm Algoritmivaihtoehto, joka halutaan ajaa 
     * (AlgorithmAlternative.DIJKSTRA tai AlgorithmAlternative.ASTAR).
     */
    public void run(List<PlaceNode> graph, PlaceNode startNode, PlaceNode endNode, AlgorithmAlternative algorithm) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Aloituspaikka ei voi olla null");
        }
        if(endNode == null && algorithm == AlgorithmAlternative.ASTAR) {
            throw new IllegalArgumentException("Maalipaikka ei voi olla null A*-algoritmilla");
        }
        initialize(graph, startNode, endNode, algorithm);
        solvedNodes = new PlaceNode[graph.size()]; 
        solvedNodeIndex = 0;
        nodeAmount = graph.size();
        MinHeap heap = new MinHeap(graph.size());
        
        if(algorithm == AlgorithmAlternative.DIJKSTRA) {
            for (PlaceNode node : graph) {
                heap.insert(node, node.getStartDistance());
            }
            while (!heap.isEmpty()) {
                solveNode(heap, AlgorithmAlternative.DIJKSTRA);
            }
        } else if(algorithm == AlgorithmAlternative.ASTAR){
            for(PlaceNode node : graph) {
                heap.insert(node, node.getStartDistance() + node.getEndDistance());
            }        
            while(!nodeIsSolved(endNode)) {
                solveNode(heap, AlgorithmAlternative.ASTAR);
            }
        }
    }
    
    /**
     * Initialize-metodi alustaa verkon solmujen tiedot: kaikkien solmujen etäisyys
     * aloitussolmusta asetetaan Double.MAX_VALUE:ksi lukuunottamatta aloitussolmua,
     * jonka etäisyydeksi itsestään asetetaan 0.0. Lisäksi kaikkiin solmuihin asetetaan
     * edeltäjäsolmutieto nulliksi.
     * @param graph     Verkko ilmaistuna PlaceNode-olioita sisältävänä List-oliona.
     * @param startNode Aloitussolmuna käytettävä PlaceNode.
     * @throws IllegalArgumentException Jos verkko tai aloitussolmu on null.
     */
    public void initialize(List<PlaceNode> graph, PlaceNode startNode, PlaceNode endNode, AlgorithmAlternative algorithm) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Lähtösolmu ei voi olla null");
        }
        if(endNode == null && algorithm == AlgorithmAlternative.ASTAR) {
            throw new IllegalArgumentException("Maalisolmu ei voi olla null A*-algoritmissa");
        }
        for(PlaceNode node : graph){
            node.setStartDistance(Double.MAX_VALUE);
            if(algorithm == AlgorithmAlternative.ASTAR) {
               node.setEndDistance(calculateHeuristic(node, endNode)); 
            }
            node.setPathPredecessor(null);
        }
        startNode.setStartDistance(0.0);
    }
    
    /**
     * Relax-metodi käy läpi annetun solmun vierussolmut ja jos naapurin etäisyystieto aloitussolmuun 
     * nähden on suurempi kuin annetun solmun etäisyys aloitussolmuun + annetun solmun ja naapurin
     * välinen etäisyys, tämä summa asetetaan naapurin aloitussolmuetäisyydeksi.
     * @param node      Tutkittava solmu.
     * @param neighbour Tutkittavan solmun vierussolmu.
     * @throws IllegalArgumentException Jos tutkittava solmu tai sen vierussolmu on null.
     */
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
    
    /**
     * Metodi, joka tutkii minimikeon kärjessä olevaa solmua ja selvittää sen naapureiden etäisyyden aloitussolmusta.
     * @param heap Minimikeko, joka sisältää verkon solmut aloitussolmuetäisyyden mukaisessa järjestyksessä.
     * @param algorithm Algoritmivaihtoehto, jota ollaan ajamassa (AlgorithmAlternative.DIJKSTRA tai .ASTAR)
     * @throws IllegalArgumentException Jos keko on null.
     */
    public void solveNode(MinHeap heap, AlgorithmAlternative algorithm) {
        if(heap == null) {
            throw new IllegalArgumentException("Keko ei voi olla null");
        }
        PlaceNode nearestNode = heap.del_min();
        solvedNodes[solvedNodeIndex] = nearestNode;
        solvedNodeIndex++;
        for(NeighbourNode neighbour : nearestNode.getNeighbours()) {
            relax(nearestNode, neighbour.getNeighbour());
            double newDistance = neighbour.getNeighbour().getStartDistance();
            if(algorithm == AlgorithmAlternative.ASTAR) {
                newDistance += neighbour.getNeighbour().getEndDistance();
            }
            heap.decrease_key(neighbour.getNeighbour(), newDistance);
        }
    }
    
    /**
     * Tutkii, onko annettu solmu jo ratkaistujen joukossa.
     * @param node Solmu, jota etsitään ratkaistujen joukosta.
     * @return True, jos annettu solmu on jo ratkaistu, muutoin false.
     * @throws IllegalArgumentException Jos parametrina annetaan null.
     */
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
    
    /**
     * Palauttaa lyhimmän polun solmut PathStack-muotoisena pinona.
     * @param startNode Polun aloituspaikka.
     * @param endNode   Polun maalipaikka.
     * @return          Polun sisältämät solmut PathStack-pinona.
     * @throws          IllegalArgumentException Jos jompi kumpi parametrina
     * annettavista solmuista on null.
     */
    public PathStack getShortestPath(PlaceNode startNode, PlaceNode endNode) {
        if(startNode == null || endNode == null) {
            throw new IllegalArgumentException("Solmu ei saa olla null");
        }
        PathStack path = new PathStack(nodeAmount);
        PlaceNode predecessorNode = endNode.getPathPredecessor();
        while (predecessorNode != startNode) {
            path.push(predecessorNode);
            predecessorNode = predecessorNode.getPathPredecessor();
        }
        return path;
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
    
    public PlaceNode[] getSolvedNodes() {
        return solvedNodes;
    }

    public int getSolvedNodeIndex() {
        return solvedNodeIndex;
    }
    
}
