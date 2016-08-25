package tiralabra.ui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import tiralabra.datainput.DataFileHandler;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.ScriptMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.search.PathSearcher;

public class UserInputHandlerTest {
    
    private PathSearcher pathSearcher;
    private DataFileHandler graphFileHandler;
    private DataFileHandler scriptFileHandler;
    private PlaceGraphMapper placeGraphMapper;
    private ScriptMapper scriptMapper;
    private Messenger messenger;
    private InputStream inputStream;
    private UserInputHandler handler;
    
    @Before
    public void setUp() {
        pathSearcher = Mockito.mock(PathSearcher.class);
        graphFileHandler = Mockito.mock(DataFileHandler.class);
        scriptFileHandler = Mockito.mock(DataFileHandler.class);
        placeGraphMapper = Mockito.mock(PlaceGraphMapper.class);
        scriptMapper = Mockito.mock(ScriptMapper.class);
        messenger = Mockito.mock(Messenger.class);
        System.setIn(inputStream);
        handler = new UserInputHandler(pathSearcher, graphFileHandler, placeGraphMapper, scriptFileHandler, scriptMapper, messenger);
    }

    @Test
    public void testRunInputLoop() {
//        handler.runInputLoop();
//        inputStream = new ByteArrayInputStream("=".getBytes());
//        verify(messenger).showAllResults(any(NamedArrayList.class));        
//        inputStream = new ByteArrayInputStream("data/metropolitan.data Helsinki Vantaa".getBytes());
//        
//        inputStream = new ByteArrayInputStream("+ Helsinki Vantaa".getBytes());
//        
//        inputStream = new ByteArrayInputStream("* data/scriptfile.scr 10".getBytes());
//        
//        inputStream = new ByteArrayInputStream("q".getBytes());
    }

    @Test
    public void testHandleShortestPathQuery() {
        
    }
    
}
