package tiralabra;

import tiralabra.datainput.DataFileHandler;
import tiralabra.datainput.IDataMapper;
import tiralabra.datainput.IGraphMapper;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.datainput.ScriptMapper;
import tiralabra.search.PathSearcher;
import tiralabra.ui.UserInputHandler;

public class App 
{
    public static void main( String[] args )
    {        
        PathSearcher pathSearcher = new PathSearcher();
        IGraphMapper graphMapper = new PlaceGraphMapper();
        IDataMapper scriptMapper = new ScriptMapper();
        DataFileHandler graphFileHandler = new DataFileHandler(graphMapper);  
        DataFileHandler scriptFileHandler = new DataFileHandler(scriptMapper);
        
        UserInputHandler handler = new UserInputHandler(
            pathSearcher, 
            graphFileHandler, 
            graphMapper, 
            scriptFileHandler,
            scriptMapper);
        handler.runInputLoop();
        System.exit(0);
    }
}