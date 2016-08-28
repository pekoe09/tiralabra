package tiralabra.search;

import tiralabra.datastructures.MinHeap;
import tiralabra.datastructures.PathStack;
import tiralabra.domain.NeighbourNode;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.PathSearchResult;

/**
 * Luokan tehtävä on etsiä lyhin polku annetusta paikkatietoverkosta. 
 * Sisältää sekä Dijkstra- että A*-algoritmien logiikan.
 */
public class PathAlgorithm {
    
    private PlaceNode[] solvedNodes;
    private int solvedNodeIndex;
    private int nodeAmount;
    private long runTimeNanoSecs;
    
    /**
     * Konstruktori alustaa ratkaistujen solmujen taulukon.
     */
    public PathAlgorithm() {
        this.solvedNodes = new PlaceNode[10];
    }
    
    /**
     * Run-metodilla suoritetaan valitun algoritmin mukainen lyhimmän polun etsintä
     * annetulla verkolla ja aloitussolmulla.
     * @param graph     Verkko kuvattuna PlaceNode-olioita sisältävänä List-oliona.
     * @param startNode Polun alkupisteenä oleva paikka PlaceNode-oliona.
     * @param endNode   Polun päätepisteenä oleva paikka PlaceNode-oliona.
     * @param algorithm Algoritmivaihtoehto, joka halutaan ajaa 
     *                  (AlgorithmAlternative.DIJKSTRA tai AlgorithmAlternative.ASTAR).
     * @return          Algoritmiajon tulos.
     * @throws          IllegalArgumentException Jos verkko tai aloitussolmu on null tai jos
     *                  lopetussolmu on null kun yritetään suorittaa A*-algoritmia.
     */
    public PathSearchResult run(NamedArrayList graph, PlaceNode startNode, PlaceNode endNode, AlgorithmAlternative algorithm) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Aloituspaikka ei voi olla null");
        }
        if(endNode == null) {
            throw new IllegalArgumentException("Maalipaikka ei voi olla null");
        }
        
        initialize(graph, startNode, endNode, algorithm);
        solvedNodes = new PlaceNode[graph.size()]; 
        solvedNodeIndex = 0;
        nodeAmount = graph.size();
        MinHeap heap = new MinHeap(graph.size());
        long startTime = System.nanoTime();
        if(algorithm == AlgorithmAlternative.DIJKSTRA) {
            for (Object object : graph) {
                PlaceNode node = (PlaceNode)object;
                heap.insert(node, node.getStartDistance());
            }
            while (!heap.isEmpty()) {
                solveNode(heap, AlgorithmAlternative.DIJKSTRA);
            }
        } else if(algorithm == AlgorithmAlternative.ASTAR){
            for(Object object : graph) {
                PlaceNode node = (PlaceNode)object;
                heap.insert(node, node.getStartDistance() + node.getEndDistance());
            }        
            while(!nodeIsSolved(endNode)) {
                solveNode(heap, AlgorithmAlternative.ASTAR);
            }
        }
        runTimeNanoSecs = System.nanoTime() - startTime;
        
        PathSearchResult result = new PathSearchResult();
        result.setGraph(graph);
        result.setNodeCount(graph.size());
        result.setStartPlace(startNode);
        result.setEndPlace(endNode);
        result.setShortestPath(getShortestPath(startNode, endNode));
        result.setAlgorithm(algorithm);
        result.setRunTimeNanoSecs(runTimeNanoSecs);
        return result;
    }
    
    /**
     * Initialize-metodi alustaa verkon solmujen tiedot: kaikkien solmujen etäisyys
     * aloitussolmusta asetetaan Double.MAX_VALUE:ksi lukuunottamatta aloitussolmua,
     * jonka etäisyydeksi itsestään asetetaan 0.0. Lisäksi kaikkiin solmuihin asetetaan
     * edeltäjäsolmutieto nulliksi. Jos ollaan ajamassa A*-algoritmia, kaikkien solmujen
     * etäisyysarvioksi maalisolmusta asetetaan ko. solmun ja maalisolmun heuristinen
     * etäisyys.
     * @param graph     Verkko ilmaistuna PlaceNode-olioita sisältävänä List-oliona.
     * @param startNode Lähtösolmuna käytettävä PlaceNode.
     * @param endNode   Maalisolmuna käytettävä PlaceNode.
     * @param algorithm Ajettava algoritmi.
     * @throws          IllegalArgumentException Jos verkko tai lähtösolmu on null tai
     *                  jos maalisolmu on null ja algoritmi on A*.
     */
    public void initialize(NamedArrayList graph, PlaceNode startNode, PlaceNode endNode, AlgorithmAlternative algorithm) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Lähtösolmu ei voi olla null");
        }
        if(endNode == null && algorithm == AlgorithmAlternative.ASTAR) {
            throw new IllegalArgumentException("Maalisolmu ei voi olla null A*-algoritmissa");
        }
        for(Object object : graph){  
            PlaceNode node = (PlaceNode)object;
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
     * @param heap      Minimikeko, joka sisältää verkon solmut aloitussolmuetäisyyden mukaisessa järjestyksessä.
     * @param algorithm Algoritmivaihtoehto, jota ollaan ajamassa (AlgorithmAlternative.DIJKSTRA tai .ASTAR)
     * @throws          IllegalArgumentException Jos keko on null.
     */
    public void solveNode(MinHeap heap, AlgorithmAlternative algorithm) {
        if(heap == null) {
            throw new IllegalArgumentException("Keko ei voi olla null");
        }
        PlaceNode nearestNode = heap.delMin();
        solvedNodes[solvedNodeIndex] = nearestNode;
        solvedNodeIndex++;
        for(NeighbourNode neighbour : nearestNode.getNeighbours()) {
            relax(nearestNode, neighbour.getNeighbour());
            double newDistance = neighbour.getNeighbour().getStartDistance();
            if(algorithm == AlgorithmAlternative.ASTAR) {
                newDistance += neighbour.getNeighbour().getEndDistance();
            }
            heap.decreaseKey(neighbour.getNeighbour(), newDistance);
        }
    }
    
    /**
     * Tutkii, onko annettu solmu jo ratkaistujen joukossa.
     * @param node  Solmu, jota etsitään ratkaistujen joukosta.
     * @return      True, jos annettu solmu on jo ratkaistu, muutoin false.
     * @throws      IllegalArgumentException Jos parametrina annetaan null.
     */
    public boolean nodeIsSolved(PlaceNode node) {
        if(node == null) {
            throw new IllegalArgumentException("Etsittävä solmu ei voi olla null");
        }
        for(int i = 0; i < solvedNodes.length; i++) {
            if(solvedNodes[i] != null && 
                    solvedNodes[i].getName().equals(node.getName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Palauttaa lyhimmän polun solmut PathStack-muotoisena pinona.
     * @param startNode Polun lähtöpaikka.
     * @param endNode   Polun maalipaikka.
     * @return          Polun sisältämät solmut PathStack-pinona.
     * @throws          IllegalArgumentException Jos jompi kumpi parametrina
     *                  annettavista solmuista on null.
     */
    public PathStack getShortestPath(PlaceNode startNode, PlaceNode endNode) {
        if(startNode == null || endNode == null) {
            throw new IllegalArgumentException("Solmu ei saa olla null");
        }
        PathStack path = new PathStack(nodeAmount);
        PlaceNode predecessorNode = endNode.getPathPredecessor();
        while (!predecessorNode.getName().equalsIgnoreCase(startNode.getName())) {
            path.push(predecessorNode);
            predecessorNode = predecessorNode.getPathPredecessor();
        }
        return path;
    }
        
    /**
     * Laskee kahden paikkasolmun sijaintikoordinaattien välimatkan kilometreinä.
     * (lähde: https://rosettacode.org/wiki/Haversine_formula#Java)
     * @param node1 Ensimmäinen paikkasolmu.
     * @param node2 Toinen paikkasolmu.
     * @return      Annettujen paikkasolmujen välimatka kilometreinä.
     * @throws      IllegalArgumentException Jos jompi kumpi paikkasolmuparametreista on null.
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
    
    /**
     * Palauttaa ratkaistujen solmujen taulukon.
     * @return      Taulukko, joka sisältää ratkaistut solmut.
     */
    public PlaceNode[] getSolvedNodes() {
        return solvedNodes;
    }

    public int getSolvedNodeIndex() {
        return solvedNodeIndex;
    }

    /**
     * Palauttaa algoritmin suoritukseen käytetyn ajan.
     * @return      Algoritmin suoritusaika nanosekunteina.
     */
    public long getRunTimeNanoSecs() {
        return runTimeNanoSecs;
    }    
}
