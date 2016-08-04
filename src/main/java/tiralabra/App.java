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
                    String filePath = params[0];
                    String startPlaceName = params[1];
                    String endPlaceName = params[2];
                    boolean errorsEncountered = false;

                    try {
                        GraphFileHandler.readGraphFile(filePath, mapper);
                    } catch (IllegalArgumentException exc) {
                        Messenger.printFileError(exc.getMessage(), filePath);
                        errorsEncountered = true;
                    }                   
                    
                    if (!errorsEncountered) {
                        try {
                            GraphUtils.findPlace(mapper.getData(), startPlaceName);
                            GraphUtils.findPlace(mapper.getData(), endPlaceName); 
                        } catch (Exception exc) {
                            Messenger.printError(exc.getMessage());
                            errorsEncountered = true;
                        }
                        if (!errorsEncountered) {
                            runAlgos(mapper.getData());
                        }
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
