package tiralabra;

import tiralabra.ui.UserInputHandler;

public class App 
{
    public static void main( String[] args )
    {        
        UserInputHandler handler = new UserInputHandler();
        handler.runInputLoop();
        System.exit(0);
    }
}