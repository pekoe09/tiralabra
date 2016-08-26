package tiralabra.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.datastructures.PathStack;
import tiralabra.domain.PathSearchResultSet;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;

public class MessengerTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream oldErr;
    
    private PathSearchResultSet resultSet;
    private PathStack path;
    private PlaceNode startNode;
    private PlaceNode endNode;
    private long runTimeNanoSecs;
    private AlgorithmAlternative algorithm;
    private String filePath = "c://joku/tiedosto.data";
    private Messenger messenger = new Messenger();
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        oldErr = System.err;
        System.setErr(new PrintStream(errContent));
        
        resultSet = Mockito.mock(PathSearchResultSet.class);        
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
        
    }

    @Test
    public void testPrintResultMetaData() {
        String expected = "Tack och välkommen åter!\n";
        messenger.printGoodbye();
        assertEquals("Tulostettu hyvästely on väärin", expected, outContent.toString());
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
        String expected = "Anna seuraava komento!\nOhje: [tiedostopolku] [lähtöpaikka] [maalipaikka] hakee polun, q lopettaa, = näyttää kaikki tulokset\n> ";
        messenger.printPrompt();
        assertEquals("Tulostettu ohje/kehote on väärin", expected, outContent.toString());
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
