package tiralabra.search;

import tiralabra.datainput.IGraphMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;

public class PathSearcher {
    
    private AlgorithmAlternative[] alternatives = new AlgorithmAlternative[]{AlgorithmAlternative.ASTAR, AlgorithmAlternative.DIJKSTRA};
    
    /**
     * TÃ¤mÃ¤ metodi vastaa algoritmien ajamisesta, niiden tulosten tallentamisesta sekÃ¤ esittÃ¤misestÃ¤.
     * Metodi ajaa sekÃ¤ Dijkstra- ettÃ¤ A*-algoritmit.
     * @param mapper            IGraphMapper-rajapinnan toteuttava olio, joka sisÃ¤ltÃ¤Ã¤ verkon tiedot.
     * @param startPlaceName    Polun lähtöpaikan nimi.
     * @param endPlaceName      Polun maalipaikan nimi.
     * @param filePath          Verkon muodostamiseen käytetyn datatiedoston polku.
     * @return                  Polun etsinnän tulokset eri algoritmivaihtoehdoilla.
     */
    public PathSearchResult[] runAlgos(IGraphMapper mapper, String startPlaceName, String endPlaceName, String filePath) {        
        return runAlgos(mapper.getData(), startPlaceName, endPlaceName, filePath, mapper.getNumberOfEdges(), mapper.getData().size());
    }
    
    /**
     * Tämä metodi vastaa algoritmien ajamisesta, niiden tulosten tallentamisesta sekä esittämisestä.
     * Metodi ajaa sekjä Dijkstra- että A*-algoritmit.
     * @param graph             Etsinnässä käytettävä verkko PlaceNode-olioita sisältävänä NamedArrayList-oliona.
     * @param startPlaceName    Polun lähtöpaikan nimi.
     * @param endPlaceName      Polun maalipaikan nimi.
     * @param filePath          Verkon muodostamiseen käytetyn datatiedoston polku.
     * @param edgeCount         Verkon kaarien lukumäärä.
     * @param nodeCount         Verkon solmujen lukumäärä.
     * @return                  Polun etsinnän tulokset eri algoritmivaihtoehdoilla.
     */
    public PathSearchResult[] runAlgos(NamedArrayList graph, String startPlaceName, 
            String endPlaceName, String filePath, long edgeCount, int nodeCount) {
        PlaceNode startPlace = (PlaceNode)graph.findByName(startPlaceName);
        PlaceNode endPlace = (PlaceNode)graph.findByName(endPlaceName);
        
        
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
    
    /**
     * Ajaa polunetsintäalgoritmit annetun määrän kertoja, ja palauttaa tulokset, joissa algoritmien
     * käyttämät ajat ovat keskiarvo-aikoja.
     * @param mapper            IGraphMapper-rajapinnan toteuttava olio, joka sisältää verkon tiedot.
     * @param startPlaceName    Polun lähtöpaikan nimi.
     * @param endPlaceName      Polun maalipaikan nimi.
     * @param filePath          Verkon muodostamiseen käytetyn datatiedoston polku.
     * @param repeatTimes       Haluttu algoritmien toistokertojen määrä.
     * @return                  Polun etsinnän tulokset eri algoritmivaihtoehdoilla; ajoaikatiedot ovat
     *                          eri toistokertojen käyttämien aikojen keskiarvot.
     */
    public PathSearchResult[] runRepeatedAlgos(IGraphMapper mapper, String startPlaceName, String endPlaceName,
        String filePath, int repeatTimes) {
        PathSearchResult[] averageResults = new PathSearchResult[alternatives.length];
        for(int i = 0; i < repeatTimes; i++) {
            if(i == 0) {
                averageResults = runAlgos(mapper, startPlaceName, endPlaceName, filePath);
            } else {
                PathSearchResult[] results = runAlgos(mapper, startPlaceName, endPlaceName, filePath);
                for (int j = 0; j < alternatives.length; j++) {
                    averageResults[j].setRunTimeNanoSecs(averageResults[j].getRunTimeNanoSecs() + results[j].getRunTimeNanoSecs());
                }                
            }
        }
        for (int j = 0; j < alternatives.length; j++) {
            averageResults[j].setRunTimeNanoSecs(averageResults[j].getRunTimeNanoSecs() / repeatTimes);
        }   
        return averageResults;
    }
}
