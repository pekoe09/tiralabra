package tiralabra;

import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.GraphFileHandler;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {        
        Scanner in = new Scanner(System.in);
        Messenger.printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                Messenger.printAllResults();
            } else {
                String[] params = input.split(" ");
                if(params.length != 3) {
                    Messenger.printParamError();
                } else {  
                    PlaceGraphMapper mapper = new PlaceGraphMapper();
                    String filePath = params[0];
                    String startPlaceName = params[1];
                    String endPlaceName = params[2];
                    boolean errorsEncountered = false;

                    try {
                        GraphFileHandler.readGraphFile(filePath, mapper);
                    } catch (IllegalArgumentException exc) {
                        Messenger.printFileError(exc.getMessage(), filePath);
                        errorsEncountered = true;
                    }                   
                    
                    if (!errorsEncountered) {
                        try {
                            GraphUtils.findPlace(mapper.getData(), startPlaceName);
                            GraphUtils.findPlace(mapper.getData(), endPlaceName); 
                        } catch (Exception exc) {
                            Messenger.printError(exc.getMessage());
                            errorsEncountered = true;
                        }
                        if (!errorsEncountered) {
                            runAlgos(mapper.getData(), startPlaceName, endPlaceName);
                        }
                    }
                }
            }
            Messenger.printPrompt();
            input = in.nextLine().trim().toLowerCase();
        }      
        Messenger.printGoodbye();
        System.exit(0);
    }

    /**
     * Tämä metodi vastaa algoritmien ajamisesta, niiden tulosten tallentamisesta sekä esittämisestä.
     * 
     * @param graphData Paikkatiedot List-rakenteessa, jonka elementteinä on PlaceNode-
     * olioita.
     * @param startPlaceName Polun aloituspaikan nimi.
     */
    public static void runAlgos(List<PlaceNode> graphData, String startPlaceName, String endPlaceName) {
        System.out.println("Suoritetaan algoritmit...");
        PlaceNode startPlace = GraphUtils.findPlace(graphData, startPlaceName);
        PlaceNode endPlace = GraphUtils.findPlace(graphData, endPlaceName);
        
        PathAlgorithm dijkstra = new PathAlgorithm();
        dijkstra.run(graphData, startPlace, endPlace, AlgorithmAlternative.DIJKSTRA);
        PathStack shortestDijkstraPath = dijkstra.getShortestPath(startPlace, endPlace);
        
        PathAlgorithm aStar = new PathAlgorithm();
        aStar.run(graphData, startPlace, endPlace, AlgorithmAlternative.ASTAR);
        PathStack shortestAStarPath = aStar.getShortestPath(startPlace, endPlace);
        
        Messenger.printShortestPath(shortestDijkstraPath, startPlace, endPlace);
        Messenger.printShortestPath(shortestAStarPath, startPlace, endPlace);
    }
}