package tiralabra;

import tiralabra.datastructures.PathStack;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.GraphFileHandler;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;
import java.util.List;
import java.util.Scanner;
import tiralabra.datainput.IDataMapper;

public class App 
{
    public static void main( String[] args )
    {        
        runInputLoop();
        System.exit(0);
    }
    
    /**
     * Suorittaa input-loopia, jossa käyttäjältä kysytään seuraavaa toimenpidettä.
     * Inputilla "[tiedostopolku] [paikka1] [paikka2]" luetaan sisään tiedostopolku-parametrin
     * määrittämä tiedosto ja haetaan sitten lyhin reitti paikka1:stä paikka2:een. 
     * Inputilla "+ [paikka1] [paikka2]" käytetään viimeksi sisään luetun tiedoston
     * määrittämää verkkoa ja haetaan lyhin reitti siitä. Inputilla "=" näytetään kaikki
     * ohjelman suorituksen aikana saadut lyhin polku -tulokset. Inputilla "q" ohjelman ajo 
     * lopetetaan.
     */
    public static void runInputLoop() {
        Scanner in = new Scanner(System.in);
        Messenger.printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                Messenger.printAllResults();
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
     * @param input Käyttäjän syöte; muotoa "[tiedostopolku] [lähtöpaikka] [maalipaikka]",
     *              missä [tiedostopolku]-osa voidaan korvata pelkällä +-merkillä; 
     *              tällöin polkua etsitään viimeksi sisään luetun datatiedoston 
     *              muodostamasta verkosta.
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
            String fileMessage = String.format("Tiedosto luettu; verkossa on %d solmua ja %d kaarta.",
                                                mapper.getData().size(), mapper.getNumberOfEdges());
            Messenger.printMessage(fileMessage);
            if (mapper != null) {
                try{
                    runAlgos(mapper.getData(), startPlaceName, endPlaceName);
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
     * @param graphData         Paikkatiedot List-rakenteessa, jonka elementteinä on PlaceNode-
     *                          olioita.
     * @param startPlaceName    Polun aloituspaikan nimi.
     * @param endPlaceName      Polun lopetuspaikan nimi.
     */
    public static void runAlgos(List<PlaceNode> graphData, String startPlaceName, String endPlaceName) {
        System.out.println("Suoritetaan algoritmit...");
        PlaceNode startPlace = GraphUtils.findPlace(graphData, startPlaceName);
        PlaceNode endPlace = GraphUtils.findPlace(graphData, endPlaceName);
        
        PathAlgorithm dijkstra = new PathAlgorithm();
        dijkstra.run(graphData, startPlace, endPlace, AlgorithmAlternative.DIJKSTRA);
        PathStack shortestDijkstraPath = dijkstra.getShortestPath(startPlace, endPlace);
        long dijkstraRunTime = dijkstra.getRunTime();
        
        PathAlgorithm aStar = new PathAlgorithm();
        aStar.run(graphData, startPlace, endPlace, AlgorithmAlternative.ASTAR);
        PathStack shortestAStarPath = aStar.getShortestPath(startPlace, endPlace);
        long aStarRunTime = aStar.getRunTime();

        Messenger.printShortestPath(shortestDijkstraPath, startPlace, endPlace, dijkstraRunTime, AlgorithmAlternative.DIJKSTRA);
        Messenger.printShortestPath(shortestAStarPath, startPlace, endPlace, aStarRunTime, AlgorithmAlternative.ASTAR);
    }
}