package tiralabra;

import java.util.List;

/**
 * Dijkstra-luokka sisältää Dijkstra-algoritmin logiikan.
 */
public class Dijkstra {
    
    private PlaceNode[] solvedNodes;
    private int solvedNodeIndex;
    private int nodeAmount;
    
    public Dijkstra() {
        this.solvedNodes = new PlaceNode[10];
    }
    
    /**
     * Run-metodilla suoritetaan Dijkstra-algoritmin mukainen lyhimmän polun etsintä
     * annetulla verkolla ja aloitussolmulla.
     * @param graph     Verkko kuvattuna PlaceNode-olioita sisältävänä List-oliona.
     * @param startNode Polun alkupisteenä oleva paikka PlaceNode-oliona.
     */
    public void run(List<PlaceNode> graph, PlaceNode startNode) {
        initialize(graph, startNode);
        solvedNodes = new PlaceNode[graph.size()]; 
        solvedNodeIndex = 0;
        nodeAmount = graph.size();
        MinHeap heap = new MinHeap(graph.size());
        for (PlaceNode node : graph) {
            heap.insert(node, node.getStartDistance());
        }
        while (!heap.isEmpty()) {
            solveNode(heap);
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
    public void initialize(List<PlaceNode> graph, PlaceNode startNode) {
        if(graph == null) {
            throw new IllegalArgumentException("Verkko ei voi olla null");
        }
        if(startNode == null) {
            throw new IllegalArgumentException("Aloitussolmu ei voi olla null");
        }
        for(PlaceNode node : graph){
            node.setStartDistance(Double.MAX_VALUE);
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
     * @throws IllegalArgumentException Jos keko on null.
     */
    public void solveNode(MinHeap heap) {
        if(heap == null) {
            throw new IllegalArgumentException("Keko ei voi olla null");
        }
        PlaceNode nearestNode = heap.del_min();
        solvedNodes[solvedNodeIndex] = nearestNode;
        solvedNodeIndex++;
        for(NeighbourNode neighbour : nearestNode.getNeighbours()) {
            relax(nearestNode, neighbour.getNeighbour());
            heap.decrease_key(neighbour.getNeighbour(), neighbour.getNeighbour().getStartDistance());
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
    
}
