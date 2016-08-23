package tiralabra.ui;

import java.util.Scanner;
import tiralabra.search.PathSearcher;
import tiralabra.datainput.DataFileHandler;
import tiralabra.datainput.IDataMapper;
import tiralabra.datainput.IGraphMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.Command;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PathSearchResultSet;

public class UserInputHandler {
    
    private NamedArrayList allResults;
    private PathSearcher pathSearcher;
    private DataFileHandler graphFileHandler;
    private DataFileHandler scriptFileHandler;
    private IGraphMapper graphMapper;
    private IDataMapper scriptMapper;
    
    public UserInputHandler(PathSearcher pathSearcher, DataFileHandler graphFileHandler, 
        IGraphMapper graphMapper, DataFileHandler scriptFileHandler, IDataMapper scriptMapper) {
        this.pathSearcher = pathSearcher;
        this.graphFileHandler = graphFileHandler;
        this.graphMapper = graphMapper;
        this.scriptFileHandler = scriptFileHandler;
        this.scriptMapper = scriptMapper;
    }
        
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
            } else if(input.startsWith("*")) {
                handleScript(input);
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
        
        if(filePath.equals("+")) {
            int resultCount = allResults.size();
            if(resultCount == 0) {
                Messenger.printMessage("Aiempia sisäänluettuja datatiedostoja ei ole.");
                return;
            }
            try {                
                PathSearchResult latestResult = ((PathSearchResultSet)allResults.get(resultCount - 1)).get(0);
                results = pathSearcher.runAlgos(latestResult.getGraph(), startPlaceName, endPlaceName, 
                        latestResult.getFilePath(), latestResult.getEdgeCount(), latestResult.getNodeCount());
            } catch (Exception exc) {
                Messenger.printMessage(exc.getMessage());
            }
        
        } else {              
            IDataMapper mapper = graphFileHandler.readDataFile(filePath);
            if (mapper != null) {
                Messenger.printMessage("Tiedosto luettu; " + mapper.toString());            
                try {
                    results = pathSearcher.runAlgos((IGraphMapper)mapper, startPlaceName, endPlaceName, filePath);                    
                } catch (Exception exc) {
                    Messenger.printMessage(exc.getMessage());
                }
            }
        }
        PathSearchResultSet resultSet = new PathSearchResultSet(results);
        allResults.add(resultSet);
        Messenger.showResults(resultSet);
    }

    public void handleScript(String input) {
        String[] params = input.split(" ");
        if(params.length != 3) {
            Messenger.printParamError();
            return;
        } 
        String filePath = params[1];
        int algorithmRepeatTimes = Integer.parseInt(params[2]);
        
        scriptFileHandler.readDataFile(filePath);
        for(Command command : (Command[])scriptMapper.getData().getArray()) {
            PathSearchResult[] results = null;
            try {
                graphFileHandler.readDataFile(command.getFilePath());
                results = pathSearcher.runRepeatedAlgos(
                                            graphMapper, 
                                            command.getStartPlaceName(), 
                                            command.getEndPlaceName(), 
                                            command.getFilePath(), 
                                            algorithmRepeatTimes);
            } catch (Exception exc) {
                Messenger.printMessage(exc.getMessage());
                break;
            }
            PathSearchResultSet resultSet = new PathSearchResultSet(results);
            allResults.add(resultSet);
            Messenger.showResults(resultSet);
        }
    }    
}
