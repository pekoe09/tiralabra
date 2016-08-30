package tiralabra.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import tiralabra.datainput.DataFileHandler;
import tiralabra.datainput.IGraphMapper;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.ScriptMapper;
import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.Command;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PathSearchResultSet;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.AlgorithmAlternative;
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
    private NamedArrayList commands;
    private Command command1;
    private Command command2;
    private PathSearchResult[] resultsA;
    private PathSearchResult[] resultsB;
    private PathSearchResult result1;
    private PathSearchResult result2;
    private PathSearchResult result3;
    private PathSearchResult result4;
    
    @Before
    public void setUp() {
        commands = new NamedArrayList();
        command1 = new Command("metropolitan.data", "Helsinki", "Vantaa");
        command2 = new Command("turku.data", "Turku", "Masku");
        commands.add(command1);
        commands.add(command2);
        
        result1 = new PathSearchResult();
        result1.setAlgorithm(AlgorithmAlternative.ASTAR);
        result1.setFilePath("data/metropolitan.data");
        result1.setStartPlace(new PlaceNode("Helsinki", 0.0, 0.0));
        result1.setEndPlace(new PlaceNode("Vantaa", 0.0, 0.0));
        result2 = new PathSearchResult();
        result2.setAlgorithm(AlgorithmAlternative.DIJKSTRA);
        result2.setFilePath("data/metropolitan.data");
        result2.setStartPlace(new PlaceNode("Helsinki", 0.0, 0.0));
        result2.setEndPlace(new PlaceNode("Vantaa", 0.0, 0.0));
        result3 = new PathSearchResult();
        result3.setAlgorithm(AlgorithmAlternative.ASTAR);
        result3.setFilePath("data/turku.data");
        result3.setStartPlace(new PlaceNode("Turku", 0.0, 0.0));
        result3.setEndPlace(new PlaceNode("Masku", 0.0, 0.0));
        result4 = new PathSearchResult();
        result4.setAlgorithm(AlgorithmAlternative.DIJKSTRA);
        result4.setFilePath("data/turku.data");
        result4.setStartPlace(new PlaceNode("Turku", 0.0, 0.0));
        result4.setEndPlace(new PlaceNode("Masku", 0.0, 0.0));
        resultsA = new PathSearchResult[]{result1, result2};
        resultsB = new PathSearchResult[]{result3, result4};
        
        pathSearcher = Mockito.mock(PathSearcher.class);
        when(pathSearcher.runRepeatedAlgos(
            any(IGraphMapper.class), 
            eq("Helsinki"), 
            eq("Vantaa"), 
            eq("metropolitan.data"),
            eq(10))).thenReturn(resultsA);
        when(pathSearcher.runRepeatedAlgos(
            any(IGraphMapper.class), 
            eq("Turku"), 
            eq("Masku"), 
            eq("turku.data"),
            eq(10))).thenReturn(resultsB);
        when(pathSearcher.runAlgos(
            any(IGraphMapper.class),
            eq("Helsinki"),
            anyString(),
            anyString())).thenReturn(resultsA);
        when(pathSearcher.runAlgos(
            any(IGraphMapper.class), 
            endsWith("irhe"), 
            anyString(), 
            anyString())).thenThrow(new IllegalArgumentException("Virhe algoritmiajossa"));
        placeGraphMapper = Mockito.mock(PlaceGraphMapper.class);
        when(placeGraphMapper.toString()).thenReturn("data/metropolitan.data 3 solmua");
        graphFileHandler = Mockito.mock(DataFileHandler.class);
        when(graphFileHandler.readDataFile(eq("virhe.data"))).thenThrow(new IllegalArgumentException("Virheviesti"));
        when(graphFileHandler.readDataFile(eq("data/metropolitan.data"))).thenReturn(placeGraphMapper);
        scriptFileHandler = Mockito.mock(DataFileHandler.class);        
        scriptMapper = Mockito.mock(ScriptMapper.class);
        when(scriptMapper.getData()).thenReturn(commands);
        messenger = Mockito.mock(Messenger.class);        
        handler = new UserInputHandler(pathSearcher, graphFileHandler, placeGraphMapper, scriptFileHandler, scriptMapper, messenger);
    }

    @Test
    public void testRunInputLoop() {
        inputStream = new ByteArrayInputStream("+ Helsinki Vantaa\n* scriptfile.data 10\n=\nq\n".getBytes());
        System.setIn(inputStream);
        handler.runInputLoop();
        verify(messenger, times(4)).printPrompt();
        verify(messenger).showAllResults(any(NamedArrayList.class));
        verify(messenger).printGoodbye();
    }
    
    @Test
    public void testRunInputLoopQuittingImmediately() {
        inputStream = new ByteArrayInputStream("q\n".getBytes());
        System.setIn(inputStream);
        handler.runInputLoop();
        verify(messenger).printPrompt();
        verify(messenger).printGoodbye();
        verifyNoMoreInteractions(messenger);
    }
    
    @Test
    public void testRunInputLoopShowAllResultsThenQuit() {
        inputStream = new ByteArrayInputStream("=\nq\n".getBytes());
        System.setIn(inputStream);
        handler.runInputLoop();
        verify(messenger, times(2)).printPrompt();
        ArgumentCaptor<NamedArrayList> arrayArg = ArgumentCaptor.forClass(NamedArrayList.class);
        verify(messenger).showAllResults(arrayArg.capture());
        assertEquals(0, arrayArg.getValue().size());
        verify(messenger).printGoodbye();
        verifyNoMoreInteractions(messenger);
    }
    
    @Test
    public void testRunInputLoopRunScriptThenQuit() {
        String input = "* data/testscript.scr 10";
        commands = new NamedArrayList();
        command1 = new Command("virhe.data", "Virhe", "Virhe");
        commands.add(command1);     
        when(scriptMapper.getData()).thenReturn(commands);
        inputStream = new ByteArrayInputStream((input + "\nq\n").getBytes());
        System.setIn(inputStream);
        
        handler.runInputLoop();
        
        verify(messenger, times(2)).printPrompt();
        verify(messenger).printMessage("Virheviesti");
        verify(messenger).printGoodbye();
        verifyNoMoreInteractions(messenger);
    }
    
    @Test
    public void testRunInputLoopRunPathQueryThenQuit() {
        String input = "data/metropolitan.data Virhe Vantaa";
        inputStream = new ByteArrayInputStream((input + "\nq\n").getBytes());
        System.setIn(inputStream);
        
        handler.runInputLoop();
        
        verify(messenger, times(2)).printPrompt();
        verify(pathSearcher).runAlgos(eq(placeGraphMapper), eq("virhe"), eq("vantaa"), eq("data/metropolitan.data"));
        ArgumentCaptor<String> messageArg = ArgumentCaptor.forClass(String.class);
        verify(messenger, times(2)).printMessage(messageArg.capture());
        assertEquals("", "Tiedosto luettu; data/metropolitan.data 3 solmua", messageArg.getAllValues().get(0));
        assertEquals("", "Virhe algoritmiajossa", messageArg.getAllValues().get(1));
        verify(messenger).printGoodbye();
    }

    @Test
    public void testHandleShortestPathQuery() {
        String input = "data/metropolitan.data Helsinki Vantaa";
        handler.handleShortestPathQuery(input);
        
        ArgumentCaptor<String> fileArg = ArgumentCaptor.forClass(String.class);
        verify(graphFileHandler).readDataFile(fileArg.capture());
        assertEquals("GraphFileHandleria kutsutaan väärällä tiedostolla", "data/metropolitan.data", fileArg.getValue());
        ArgumentCaptor<String> mapperArg = ArgumentCaptor.forClass(String.class);
        verify(messenger).printMessage(mapperArg.capture());
        assertEquals("Tiedostoilmoitus on väärin", "Tiedosto luettu; data/metropolitan.data 3 solmua", mapperArg.getValue());
        verify(pathSearcher).runAlgos(eq(placeGraphMapper), eq("Helsinki"), eq("Vantaa"), eq("data/metropolitan.data"));
        verify(messenger).showResults(any(PathSearchResultSet.class));
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(pathSearcher);
        verifyNoMoreInteractions(graphFileHandler);
    }
    
    @Test
    public void handleShortestPathQueryShowsErrorMessageIfAlgoRunThrowsException() {
        String input = "data/metropolitan.data Virhe Vantaa";
        handler.handleShortestPathQuery(input);
        verify(graphFileHandler).readDataFile(anyString());
        verify(pathSearcher).runAlgos(eq(placeGraphMapper), eq("Virhe"), eq("Vantaa"), eq("data/metropolitan.data"));
        ArgumentCaptor<String> messageArg = ArgumentCaptor.forClass(String.class);
        verify(messenger, times(2)).printMessage(messageArg.capture());
        assertEquals("Tiedostoilmoitus on väärin", "Tiedosto luettu; data/metropolitan.data 3 solmua", messageArg.getAllValues().get(0));
        assertEquals("Virheilmoitus on väärin", "Virhe algoritmiajossa", messageArg.getAllValues().get(1));
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
        verifyNoMoreInteractions(messenger);
    }
    
    @Test
    public void handleShortestPathQueryShowsErrorMessageIfInputTooShort() {
        String input = "joku syöte";
        handler.handleShortestPathQuery(input);
        verify(messenger).printParamError();
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }
    
    @Test
    public void handleShortestPathQueryShowsErrorMessageIfInputTooLong() {
        String input = "joku toinen syöte lisää";
        handler.handleShortestPathQuery(input);
        verify(messenger).printParamError();
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }
    
    @Test
    public void handleShortestPathQueryShowsMessageIfNoPreviousData() {
        String input = "+ joku syöte";
        handler.handleShortestPathQuery(input);
        ArgumentCaptor<String> messageArg = ArgumentCaptor.forClass(String.class);
        verify(messenger).printMessage(messageArg.capture());
        assertEquals("Väärä viesti tulostettu", "Aiempia sisäänluettuja datatiedostoja ei ole.", messageArg.getValue());
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }
    
    @Test
    public void handleShortestPathQueryWorksWithPreviousData() {
        String input = "data/metropolitan.data Helsinki Vantaa";
        handler.handleShortestPathQuery(input);
        input = "+ Espoo Sipoo";
        handler.handleShortestPathQuery(input);
        
        ArgumentCaptor<String> fileArg = ArgumentCaptor.forClass(String.class);
        verify(graphFileHandler, times(1)).readDataFile(fileArg.capture());
        assertEquals("GraphFileHandleria kutsutaan ensin väärällä tiedostolla", "data/metropolitan.data", fileArg.getAllValues().get(0));
        
        ArgumentCaptor<String> mapperArg = ArgumentCaptor.forClass(String.class);
        verify(messenger).printMessage(mapperArg.capture());
        verify(pathSearcher).runAlgos(eq(placeGraphMapper), eq("Helsinki"), eq("Vantaa"), eq("data/metropolitan.data"));
        verify(pathSearcher).runAlgos(eq(resultsA[0].getGraph()), eq("Espoo"), eq("Sipoo"), eq("data/metropolitan.data"), eq(0L), eq(0));
        verify(messenger).showResults(any(PathSearchResultSet.class));
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(pathSearcher);
        verifyNoMoreInteractions(graphFileHandler);
    }
    
    @Test
    public void handleShortestPathQueryWithOldDataShowsErrorMessageIfAlgoRunThrowsException() {
        
    }
    
    @Test
    public void testHandleScript() {
        String input = "* data/testscript.scr 10";
        handler.handleScript(input);
        
        ArgumentCaptor<String> fileArgument = ArgumentCaptor.forClass(String.class);
        verify(scriptFileHandler).readDataFile(fileArgument.capture());
        assertEquals("ScriptFileHandlerin kutsussa ei ole tiedostopolkua", "data/testscript.scr", fileArgument.getValue());
        verify(graphFileHandler, times(2)).readDataFile(fileArgument.capture());
        assertEquals("GraphFileHandlerin 2. kutsussa ei ole tiedostopolkua", command2.getFilePath(), fileArgument.getValue());
        verify(pathSearcher, times(2)).runRepeatedAlgos(eq(placeGraphMapper), anyString(), anyString(), anyString(), anyInt());
        ArgumentCaptor<PathSearchResultSet> resultSetArgument = ArgumentCaptor.forClass(PathSearchResultSet.class);
        verify(messenger, times(2)).showResults(resultSetArgument.capture());
        assertEquals("Toinen näytettävä tulossetti on väärä", 
            String.format("%s - paikasta %s paikkaan %s", "data/turku.data", "Turku", "Masku"), 
            resultSetArgument.getValue().getName());
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(scriptFileHandler);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }
    
    @Test
    public void handleScriptShowsErrorMessageWhenReceivesException() {
        String input = "* data/testscript.scr 10";
        commands = new NamedArrayList();
        command1 = new Command("virhe.data", "Virhe", "Virhe");
        commands.add(command1);
        when(scriptMapper.getData()).thenReturn(commands);
        
        handler.handleScript(input);
        
        verify(scriptFileHandler).readDataFile(anyString());
        verify(graphFileHandler).readDataFile("virhe.data");
        verify(messenger).printMessage("Virheviesti");
        verifyNoMoreInteractions(scriptFileHandler);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
        verifyNoMoreInteractions(messenger);
    }
    
    @Test
    public void handleScriptShowsErrorMessageIfInputIsTooShort() {
        String input = "joku syöte";
        handler.handleScript(input);
        verify(messenger).printParamError();
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(scriptFileHandler);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }    
    
    @Test
    public void handleScriptShowsErrorMessageIfInputIsTooLong() {
        String input = "joku toinen syöte lisää";
        handler.handleScript(input);
        verify(messenger).printParamError();
        verifyNoMoreInteractions(messenger);
        verifyNoMoreInteractions(scriptFileHandler);
        verifyNoMoreInteractions(graphFileHandler);
        verifyNoMoreInteractions(pathSearcher);
    }
}
