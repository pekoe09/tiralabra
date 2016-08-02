package tiralabra;

import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {        
        Scanner in = new Scanner(System.in);
        printPrompt();
        String input = in.nextLine().trim().toLowerCase();
        while(!input.equals("q")) {
            if(input.equals("="))  {
                printAllResults();
            } else {
                String[] params = input.split(" ");
                if(params.length != 3) {
                    printParamError();
                } else {        
                    List<PlaceNode> graphData = null;
                    try {
                        graphData = FileReader.readGraphFile(params[0]);
                    } catch (IllegalArgumentException exc) {
                        printFileError(exc.getMessage(), params[0]);
                    }
                    runAlgos(graphData);
                }
            }
            printPrompt();
            input = in.nextLine().trim().toLowerCase();
        }      
        System.out.println("Tack och välkommen åter!");
        return;
    }

    private static void printAllResults() {
       System.out.println("Kaikki tulokset...");
    }

    private static void printParamError() {
        System.out.println("Parametrivirhe!");
    }

    private static void printPrompt() {
        System.out.println("Ohje: [tiedostopolku] [lähtöpaikka] [kohdepaikka] hakee polun, q lopettaa, = näyttää kaikki tulokset");
    }  

    private static void printFileError(String message, String filepath) {
        System.out.printf("Datatiedoston %s luku ei onnistunut:", filepath);
    }

    private static void runAlgos(List<PlaceNode> graphData) {
        System.out.println("Suoritetaan algoritmit...");
    }
}
