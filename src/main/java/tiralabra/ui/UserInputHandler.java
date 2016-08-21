package tiralabra.ui;

import java.util.Scanner;
import tiralabra.search.PathSearcher;
import tiralabra.datainput.GraphFileHandler;
import tiralabra.datainput.IDataMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PathSearchResultSet;

public class UserInputHandler {
    
    private NamedArrayList allResults;
        
    /**
     * Suorittaa input-loopia, jossa käyttäjältä kysytään seuraavaa toimenpidettä.
     * Inputilla "=" näytetään kaikki ohjelman suorituksen aikana saadut lyhin 
     * polku -tulokset. Inputilla "q" ohjelman ajo lopetetaan. Muulla inputilla
     * lähdetään suorittamaan lyhimmän polun etsintää annettujen parametrien mukaan.
     */
    public void runInputLoop() {
        allResults = new NamedArrayList();
        Scanner in = new Scanner(System.in);
        Messenger.printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                Messenger.showAllResults(allResults);
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
    public void handleShortestPathQuery(String input) {
        String[] params = input.split(" ");
        if(params.length != 3) {
            Messenger.printParamError();
            return;
        } 
        String filePath = params[0];
        String startPlaceName = params[1];
        String endPlaceName = params[2];
        PathSearchResult[] results = null;
        PathSearcher searcher = new PathSearcher();
        
        if(filePath.equals("+")) {
            int resultCount = allResults.size();
            if(resultCount == 0) {
                Messenger.printMessage("Aiempia sisäänluettuja datatiedostoja ei ole.");
                return;
            }
            try {                
                PathSearchResult latestResult = ((PathSearchResultSet)allResults.get(resultCount - 1)).get(0);
                results = searcher.runAlgos(latestResult.getGraph(), startPlaceName, endPlaceName, 
                        latestResult.getFilePath(), latestResult.getEdgeCount(), latestResult.getNodeCount());
            } catch (Exception exc) {
                Messenger.printMessage(exc.getMessage());
            }
        
        } else {              
            IDataMapper mapper = GraphFileHandler.readDataFile(filePath, startPlaceName, endPlaceName);
            if (mapper != null) {
                Messenger.printMessage("Tiedosto luettu; " + mapper.toString());            
                try {
                    results = searcher.runAlgos(mapper, startPlaceName, endPlaceName, filePath);                    
                } catch (Exception exc) {
                    Messenger.printMessage(exc.getMessage());
                }
            }
        }
        PathSearchResultSet resultSet = new PathSearchResultSet(results);
        allResults.add(resultSet);
        Messenger.showResults(resultSet);
    }
    
}
