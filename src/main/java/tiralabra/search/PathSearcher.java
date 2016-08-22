package tiralabra.search;

import tiralabra.datainput.IDataMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;

public class PathSearcher {
    
    /**
     * Tämä metodi vastaa algoritmien ajamisesta, niiden tulosten tallentamisesta sekä esittämisestä.
     * Metodi ajaa sekä Dijkstra- että A*-algoritmit.
     * @param mapper            IDataMapper-rajapinnan toteuttava olio, joka sisältää verkon tiedot.
     * @param startPlaceName    Polun aloituspaikan nimi.
     * @param endPlaceName      Polun lopetuspaikan nimi.
     * @param filePath          Verkon muodostamiseen käytetyn datatiedoston polku.
     */
    public PathSearchResult[] runAlgos(IDataMapper mapper, String startPlaceName, String endPlaceName, String filePath) {        
        return runAlgos(mapper.getData(), startPlaceName, endPlaceName, filePath, mapper.getNumberOfEdges(), mapper.getData().size());
    }
    
    public PathSearchResult[] runAlgos(NamedArrayList graph, String startPlaceName, 
            String endPlaceName, String filePath, long edgeCount, int nodeCount) {
        PlaceNode startPlace = (PlaceNode)graph.findByName(startPlaceName);
        PlaceNode endPlace = (PlaceNode)graph.findByName(endPlaceName);
        
        AlgorithmAlternative[] alternatives = new AlgorithmAlternative[]{AlgorithmAlternative.ASTAR, AlgorithmAlternative.DIJKSTRA};
        PathSearchResult[] results = new PathSearchResult[alternatives.length];
        for(int i = 0; i < alternatives.length; i++) {
            PathSearchResult result = new PathAlgorithm().run(graph, startPlace, endPlace, alternatives[i]);
            result.setEdgeCount(edgeCount);
            result.setNodeCount(nodeCount);
            result.setFilePath(filePath);
            results[i] = result;
        }
        
        return results;
    }
}
