package tiralabra.datainput;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.Command;
import tiralabra.enums.ReadTarget;

public class ScriptMapperTest {
    
    private ScriptMapper mapper;
    private String commandString1;
    private String filePath1;
    private String startPlaceName1;
    private String endPlaceName1;
    private int rowCounter1;
    private String commandString2;
    private String filePath2;
    private String startPlaceName2;
    private String endPlaceName2;
    private int rowCounter2;
    private ReadTarget target;
    
    @Before
    public void setUp() {
        mapper = new ScriptMapper();
        commandString1 = "data/metropolitan.data Helsinki Sipoo";
        filePath1 = "data/metropolitan.data";
        startPlaceName1 = "Helsinki";
        endPlaceName1 = "Sipoo";
        rowCounter1 = 1;
        commandString2 = "data/turku.data Turku Masku";
        filePath2 = "data/turku.data";
        startPlaceName2 = "Turku";
        endPlaceName2 = "Masku";
        rowCounter2 = 2;
        target = ReadTarget.SCRIPT;
    }

    @Test
    public void testGetData() {
        assertNotNull("ScriptMapperin komentolistaa ei saa ulos", mapper.getData());
    }
    
    @Test
    public void scriptMapperIsCreatedProperly() {
        ScriptMapper newMapper = new ScriptMapper();
        assertNotNull("ScriptMapperilla ei ole komentolistaa", newMapper.getData());
    }
    
    @Test
    public void mapDataCanAddCommand() {        
        mapper.mapData(commandString1, rowCounter1, target);
        mapper.mapData(commandString2, rowCounter2, target);
        NamedArrayList arrayList = mapper.getData();
        assertNotNull("Komentolistaa ei ole", arrayList);
        assertEquals("Komentolistan koko on väärä", 2, arrayList.size());
        Command command1 = (Command)arrayList.get(0);
        assertEquals("Komentolistan 1. komennon tiedostopolku on väärin", filePath1, command1.getFilePath());
        assertEquals("Komentolistan 1. komennon lähtöpaikka on väärin", startPlaceName1, command1.getStartPlaceName());
        assertEquals("Komentolistan 1. komennon maalipaikka on väärin", endPlaceName1, command1.getEndPlaceName());
        Command command2 = (Command)arrayList.get(1);
        assertEquals("Komentolistan 2. komennon tiedostopolku on väärin", filePath2, command2.getFilePath());
        assertEquals("Komentolistan 2. komennon lähtöpaikka on väärin", startPlaceName2, command2.getStartPlaceName());
        assertEquals("Komentolistan 2. komennon maalipaikka on väärin", endPlaceName2, command2.getEndPlaceName());
    }

    @Test
    public void testResetMapper() {
        mapper.resetMapper();
        assertNotNull("Komentolistaa ei ole", mapper.getData());
        assertEquals("Komentolistan pituus ei ole nolla", 0, mapper.getData().size());
    }
    
}
