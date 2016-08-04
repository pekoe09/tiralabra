package tiralabra;

import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {        
        Scanner in = new Scanner(System.in);
        Messenger.printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                Messenger.printAllResults();
            } else {
                String[] params = input.split(" ");
                if(params.length != 3) {
                    Messenger.printParamError();
                } else {  
                    PlaceGraphMapper mapper = new PlaceGraphMapper();
                    boolean fileHasErrors = false;
                    
                    try {
                        GraphFileHandler.readGraphFile(params[0], mapper);
                    } catch (IllegalArgumentException exc) {
                        Messenger.printFileError(exc.getMessage(), params[0]);
                        fileHasErrors = true;
                    }
                    
                    if (!fileHasErrors) {
                        runAlgos(mapper.getData());
                    }
                }
            }
            Messenger.printPrompt();
            input = in.nextLine().trim().toLowerCase();
        }      
        Messenger.printGoodbye();
        System.exit(0);
    }

    public static void runAlgos(List<PlaceNode> graphData) {
        System.out.println("Suoritetaan algoritmit...");
    }

}
