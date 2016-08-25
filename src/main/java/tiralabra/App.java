package tiralabra;

import tiralabra.datainput.DataFileHandler;
import tiralabra.datainput.IDataMapper;
import tiralabra.datainput.IGraphMapper;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.ScriptMapper;
import tiralabra.search.PathSearcher;
import tiralabra.ui.Messenger;
import tiralabra.ui.UserInputHandler;

public class App 
{
    public static void main( String[] args )
    {        
        PathSearcher pathSearcher = new PathSearcher();
        IGraphMapper graphMapper = new PlaceGraphMapper();
        IDataMapper scriptMapper = new ScriptMapper();
        Messenger messenger = new Messenger();
        DataFileHandler graphFileHandler = new DataFileHandler(graphMapper, messenger);  
        DataFileHandler scriptFileHandler = new DataFileHandler(scriptMapper, messenger);
        
        
        UserInputHandler handler = new UserInputHandler(
            pathSearcher, 
            graphFileHandler, 
            graphMapper, 
            scriptFileHandler,
            scriptMapper,
            messenger);
        handler.runInputLoop();
        System.exit(0);
    }
}