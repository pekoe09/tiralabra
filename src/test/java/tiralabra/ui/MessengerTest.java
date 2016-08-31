package tiralabra.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import tiralabra.datastructures.PathStack;
import tiralabra.domain.NeighbourNode;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PathSearchResultSet;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;

public class MessengerTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream oldErr;
    
    private PathSearchResultSet resultSet;
    private PathSearchResult[] resultsA;
    private PathSearchResult result1;
    private PathSearchResult result2;
    private PathStack path;
    private PlaceNode startNode;
    private PlaceNode endNode;
    private PlaceNode newPlace, nextPlace, lastPlace;
    private long runTimeNanoSecs;
    private AlgorithmAlternative algorithm;
    private String filePath = "c://joku/tiedosto.data";
    private Messenger messenger = new Messenger();
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        oldErr = System.err;
        System.setErr(new PrintStream(errContent));
        
        result1 = new PathSearchResult();
        result1.setAlgorithm(AlgorithmAlternative.ASTAR);
        result1.setFilePath("data/metropolitan.data");
        result1.setStartPlace(new PlaceNode("Helsinki", 0.0, 0.0));
        result1.setEndPlace(new PlaceNode("Vantaa", 0.0, 0.0));
        result1.setNodeCount(5);
        result1.setEdgeCount(7);
        result2 = new PathSearchResult();
        result2.setAlgorithm(AlgorithmAlternative.DIJKSTRA);
        result2.setFilePath("data/metropolitan.data");
        result2.setStartPlace(new PlaceNode("Helsinki", 0.0, 0.0));
        result2.setEndPlace(new PlaceNode("Vantaa", 0.0, 0.0));
        resultsA = new PathSearchResult[]{result1, result2};
        resultSet = new PathSearchResultSet(resultsA);
        
        startNode = new PlaceNode("alku", -1.0, -1.0);        
        newPlace = new PlaceNode("joku", 0.0, 0.0);        
        nextPlace = new PlaceNode("muu", 1.0, 1.0);        
        lastPlace = new PlaceNode("jossain", 2.0, 2.0);         
        endNode = new PlaceNode("loppu", 3.0, 3.0);
        startNode.setNeighbours(new NeighbourNode[]{new NeighbourNode(newPlace, 0.5)});
        newPlace.setNeighbours(new NeighbourNode[]{
            new NeighbourNode(startNode, 1.0),
            new NeighbourNode(nextPlace, 2.0)});
        nextPlace.setNeighbours(new NeighbourNode[]{
            new NeighbourNode(newPlace, 2.0),
            new NeighbourNode(lastPlace, 3.0)});
        lastPlace.setNeighbours(new NeighbourNode[]{
            new NeighbourNode(nextPlace, 3.0),
            new NeighbourNode(endNode, 4.0)});
        endNode.setNeighbours(new NeighbourNode[]{new NeighbourNode(lastPlace, 4.0)});
        path = new PathStack(3);
        path.push(lastPlace);
        path.push(nextPlace);        
        path.push(newPlace);        
        runTimeNanoSecs = 850000L;
        algorithm = AlgorithmAlternative.DIJKSTRA;
    }
    
    @After
    public void tearDown() {
        System.setOut(null);
        System.setErr(oldErr);
    }


    @Test
    public void testShowResults() {
        
    }

    @Test
    public void testShowAllResults() {
        
    }

    @Test
    public void testPrintShortestPath() {
        String expected = "----------------------------------------------------------------------------\n";
        expected += "Lyhin polku paikasta alku paikkaan loppu dijkstra-algoritmin mukaan\n";
        expected += "0. alku\n";
        expected += "1. joku, välimatka 1.0 km\n";
        expected += "2. muu, välimatka 2.0 km\n";
        expected += "3. jossain, välimatka 3.0 km\n";
        expected += "4. loppu, välimatka 4.0 km\n";
        expected += "Kokonaisvälimatka: 10.0 km\n";
        expected += "Algoritmin suoritukseen meni 0.850 millisekuntia.\n";
        expected += "----------------------------------------------------------------------------\n";
        messenger.printShortestPath(path, startNode, endNode, runTimeNanoSecs, algorithm);
        assertEquals("Tulostettu lyhin polku on väärin", expected, outContent.toString());
    }

    @Test
    public void testPrintResultMetaData() {        
        String expected = "Datatiedosto: data/metropolitan.data\nVerkossa on 5 solmua ja 7 kaarta\n";
        messenger.printResultMetaData(resultSet);
        assertEquals("Tulostettu metadata on väärin", expected, outContent.toString());
    }

    @Test
    public void testPrintParamError() {
        String expected = "Anna polun etsintäkäsky muodossa [tiedostopolku] [lähtöpaikka] [maalipaikka]!\n";
        expected += "Jos haluat käyttää edellistä datatiedostoa, voit korvata tiedostopolun +-merkillä.\n";
        expected += "Voit tulostaa kaikki tähänastiset tulokset '=':lla ja lopettaa 'q':lla.\n";
        messenger.printParamError();
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testPrintMessage() {
        String expected = "Joku odotettu viesti.";
        messenger.printMessage(expected);
        assertEquals("Tulostettu viesti on väärin", expected  + "\n", outContent.toString());
    }

    @Test
    public void testPrintPrompt() {
        String expected = "Anna seuraava komento! (q lopettaa, ? näyttää ohjeen)\n> ";
        messenger.printPrompt();
        assertEquals("Tulostettu kehote on väärin", expected, outContent.toString());
    }
    
    @Test
    public void testPrintHelp() {
        String expected = "Tällä ohjelmalla voit etsiä lyhimmän polun sisään luettavan verkkotiedoston määrittelemästä maantieteellisten paikkojen verkosta.\n";
        expected += "Ohjelma etsii polun sekä Dijkstra- että A*-algoritmilla ja näyttää molemmista myös algoritmin suoritusajan.\n\n";
        expected += "Käytettävissäsi ovat seuraavat komennot:\n";
        expected += "[tiedostopolku] [lähtöpaikka] [maalipaikka] : hakee lyhimmän polun [lähtöpaikasta] [maalipaikkaan] käyttäen [tiedostopolun] verkkotiedostoa\n";
        expected += "+ [lähtöpaikka] [maalipaikka] : hakee lyhimmän polun [lähtöpaikasta] [maalipaikkaan] käyttäen viimeisintä sisään luettua verkkotiedostoa\n";
        expected += "* [tiedostopolku] [toistokertojen lkm] : suorittaa [tiedostopolun] mukaisen skriptitiedoston, toistaen algoritmien ajot [toistokertojen lkm] -kertaan\n";
        expected += "= : näyttää kaikki tähänastiset polunetsintätulokset\n";
        expected += "q : lopettaa ohjelman\n";
        messenger.printHelp();
        assertEquals("Tulostettu ohje on väärin", expected, outContent.toString());
    }
    
    @Test
    public void testPrintSeparator() {
        String expected = "----------------------------------------------------------------------------\n";
        messenger.printSeparator();
        assertEquals("Tulostettu erotin on väärin", expected, outContent.toString());
    }

    @Test
    public void testPrintFileError() {
        String message = "Tiedostoa ei voitu avata.";        
        String expected = "Datatiedoston c://joku/tiedosto.data luku ei onnistunut:\nTiedostoa ei voitu avata.\n";
        messenger.printFileError(message, filePath);
        assertEquals("Tulostettu tiedostovirheilmoitus on väärin", expected, outContent.toString());
    }

    @Test
    public void testPrintGoodbye() {
        String expected = "Tack och välkommen åter!\n";
        messenger.printGoodbye();
        assertEquals("Tulostettu hyvästely on väärin", expected, outContent.toString());
    }
    
}
