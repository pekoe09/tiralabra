package tiralabra;

import tiralabra.datainput.GraphFileHandler;
import tiralabra.datainput.IDataMapper;
import tiralabra.datainput.PlaceGraphMapper;
import tiralabra.search.PathSearcher;
import tiralabra.ui.UserInputHandler;

public class App 
{
    public static void main( String[] args )
    {        
        PathSearcher pathSearcher = new PathSearcher();
        IDataMapper mapper = new PlaceGraphMapper();
        GraphFileHandler graphFileHandler = new GraphFileHandler(mapper);        
        
        UserInputHandler handler = new UserInputHandler(pathSearcher, graphFileHandler, mapper);
        handler.runInputLoop();
        System.exit(0);
    }
}