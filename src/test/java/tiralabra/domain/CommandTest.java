package tiralabra.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {
    
    private Command command;
    private String filePath;
    private String startPlaceName;
    private String endPlaceName;
    
    @Before
    public void setUp() {
        filePath = "c://joku/jossain";
        startPlaceName = "Jostain";
        endPlaceName = "Jonnekin";
        command = new Command(filePath, startPlaceName, endPlaceName);
    }
    
    @Test
    public void commandConstructedProperly() {
        Command newCommand = new Command(filePath, startPlaceName, endPlaceName);
        assertEquals("Tiedostopolkua ei ole asetettu oikein", filePath, newCommand.getFilePath());
        assertEquals("Lähtöpaikan nimeä ei ole asetettu oikein", startPlaceName, newCommand.getStartPlaceName());
        assertEquals("Maalipaikan nimeä ei ole asetettu oikein", endPlaceName, newCommand.getEndPlaceName());
    }

    @Test
    public void testGetFilePath() {
        assertEquals("Tiedostopolkua ei saa ulos", filePath, command.getFilePath());
    }

    @Test
    public void testGetStartPlaceName() {
        assertEquals("Lähtöpaikan nimeä ei saa ulos", startPlaceName, command.getStartPlaceName());
    }

    @Test
    public void testGetEndPlaceName() {
        assertEquals("Maalipaikan nimeä ei saa ulos", endPlaceName, command.getEndPlaceName());
    }

    @Test
    public void testGetName() {
        String expectedName = String.format("%s %s %s", filePath, startPlaceName, endPlaceName);
        assertEquals("Nimi ei muodostu oikein", expectedName, command.getName());
    }
    
}
