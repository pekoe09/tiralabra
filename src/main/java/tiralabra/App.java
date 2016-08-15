package tiralabra;

import java.util.ArrayList;
import java.util.List;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.GraphFileHandler;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;
import java.util.Scanner;
import tiralabra.datainput.IDataMapper;
import tiralabra.domain.PathSearchResult;

public class App 
{
    private static List<PathSearchResult[]> allResults;
    
    public static void main( String[] args )
    {        
        runInputLoop();
        System.exit(0);
    }
    
    /**
     * Suorittaa input-loopia, jossa käyttäjältä kysytään seuraavaa toimenpidettä.
     * Inputilla "=" näytetään kaikki ohjelman suorituksen aikana saadut lyhin 
     * polku -tulokset. Inputilla "q" ohjelman ajo lopetetaan. Muulla inputilla
     * lähdetään suorittamaan lyhimmän polun etsintää annettujen parametrien mukaan.
     */
    public static void runInputLoop() {
        allResults = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        Messenger.printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                showAllResults();
            } else {
                handleShortestPathQuery(input);
            }
            Messenger.printPrompt();
            input = in.nextLine().trim().toLowerCase();
        }      
        Messenger.printGoodbye();
    }
    
    /**
     * Käsittelee käyttäjän tekemän pyynnön lyhimmän polun selvittämisestä.
     * @param input Käyttäjän syöte; muotoa "[tiedostopolku] [lähtöpaikka] [maalipaikka]";
     *              [tiedostopolku] -osan mukainen tiedosto luetaan sisään paikkatietojen
     *              muodostamaksi verkoksi, josta etsitään [lähtöpaikan] ja [maalipaikan]
     *              välinen lyhin polku. [tiedostopolku]-osa voidaan korvata pelkällä +-merkillä; 
     *              tällöin polkua etsitään edellisessä polunetsinnässä käytetystä verkosta.
     */
    public static void handleShortestPathQuery(String input) {
        String[] params = input.split(" ");
        if(params.length != 3) {
            Messenger.printParamError();
        } else if(params[0].equals("+")) {
            Messenger.printMessage("toimintoa ei ole vielä toteutettu");
        } else {  
            String startPlaceName = params[1];
            String endPlaceName = params[2];
            IDataMapper mapper = readDataFile(params[0], startPlaceName, endPlaceName);
            if (mapper != null) {
                Messenger.printMessage("Tiedosto luettu; " + mapper.toString());            
                try{
                    PathSearchResult[] results = runAlgos(mapper, startPlaceName, endPlaceName, params[0]);
                    allResults.add(results);
                    showResults(results);
                } catch (Exception exc) {
                    Messenger.printMessage(exc.getMessage());
                }
            }
        }
    }
    
    /**
     * Lukee paikkadataa sisältävän tiedoston  sisään ja muodostaa siitä verkon.
     * @param filePath          Datatiedoston tiedostopolku.
     * @param startPlaceName    Etsittävän polun lähtöpaikka.
     * @param endPlaceName      Etsittävän polun maalipaikka.
     * @return                  IDataMapper-olio, joka sisältää paikkojen ja niiden välisten yhteyksien
     *                          muodostaman verkon.
     */
    public static IDataMapper readDataFile(String filePath, String startPlaceName, String endPlaceName) {
        PlaceGraphMapper mapper = new PlaceGraphMapper();
        try {
            GraphFileHandler.readGraphFile(filePath, mapper);
        } catch (IllegalArgumentException exc) {
            Messenger.printFileError(exc.getMessage(), filePath);
            return null;
        }    
        return mapper;
    }

    /**
     * Tämä metodi vastaa algoritmien ajamisesta, niiden tulosten tallentamisesta sekä esittämisestä.
     * Metodi ajaa sekä Dijkstra- että A*-algoritmit.
     * @param mapper            IDataMapper-rajapinnan toteuttava olio, joka sisältää verkon tiedot.
     * @param startPlaceName    Polun aloituspaikan nimi.
     * @param endPlaceName      Polun lopetuspaikan nimi.
     * @param filePath          Verkon muodostamiseen käytetyn datatiedoston polku.
     */
    public static PathSearchResult[] runAlgos(IDataMapper mapper, String startPlaceName, String endPlaceName, String filePath) {
        PlaceNode startPlace = GraphUtils.findPlace(mapper.getData(), startPlaceName);
        PlaceNode endPlace = GraphUtils.findPlace(mapper.getData(), endPlaceName);
        
        AlgorithmAlternative[] alternatives = new AlgorithmAlternative[]{AlgorithmAlternative.ASTAR, AlgorithmAlternative.DIJKSTRA};
        PathSearchResult[] results = new PathSearchResult[alternatives.length];
        for(int i = 0; i < alternatives.length; i++) {
            PathSearchResult result = new PathAlgorithm().run(mapper.getData(), startPlace, endPlace, alternatives[i]);
            result.setEdgeCount(mapper.getNumberOfEdges());
            result.setNodeCount(mapper.getData().size());
            result.setFilePath(filePath);
            results[i] = result;
        }
        
        return results;
    }
    
    public static void showResults(PathSearchResult[] results) {
        for(PathSearchResult result : results) {
            Messenger.printShortestPath(
                    result.getShortestPath(), 
                    result.getStartPlace(), 
                    result.getEndPlace(), 
                    result.getRunTimeNanoSecs(), 
                    result.getAlgorithm());
        }
    }
    
    public static void showAllResults() {
        Messenger.printAllResults();
    }
}