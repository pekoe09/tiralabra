package tiralabra;

import java.util.List;

public class Messenger {
    
    public static void printAllResults() {
       System.out.println("Kaikki tulokset...");
    }

    public static void printParamError() {
        System.out.println("Parametrivirhe!");
    }
    
    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printPrompt() {
        System.out.println("Ohje: [tiedostopolku] [lähtöpaikka] [kohdepaikka] hakee polun, q lopettaa, = näyttää kaikki tulokset");
    }  

    public static void printFileError(String message, String filepath) {
        System.out.printf("Datatiedoston %s luku ei onnistunut:\n%s\n", filepath, message);
    }
    
    public static void printGoodbye() {
        System.out.println("Tack och välkommen åter!");
    }

}
