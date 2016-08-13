package tiralabra;

import java.util.List;

/**
 * Apuluokka, jonka metodit esittävät käyttäjälle näytettävän informaation.
 */
public class Messenger {
    
    public static void printShortestPath(PathStack path, PlaceNode startNode, PlaceNode endNode) {
        System.out.println("Lyhin polku paikasta " + startNode.getName() + " paikkaan " + endNode.getName() + ":");
        System.out.println("0. " + startNode.getName());
        int stageCounter = 0;
        double totalDistance = 0.0;
        double distance = 0.0;
        String previousPlaceName = startNode.getName();
        while(!path.isEmpty()) {
            stageCounter++;
            PlaceNode nextPlace = path.pop();
            distance = nextPlace.getDistanceToNeighbour(previousPlaceName);
            previousPlaceName = nextPlace.getName();
            totalDistance += distance;
            System.out.println(stageCounter + ". " + nextPlace.getName() + " välimatka " + distance);
        }
        distance = endNode.getDistanceToNeighbour(previousPlaceName);
        System.out.println((stageCounter + 1) + ". " + endNode.getName() + " välimatka " + distance);
        totalDistance += distance;
        System.out.println("Kokonaisvälimatka: " + totalDistance);
    }
    
    /**
     * Näyttää kaikki polunetsintätulokset ohjelman käynnistyksestä lähtien.
     */
    public static void printAllResults() {
       System.out.println("Kaikki tulokset...");
    }

    /**
     * Näyttää ilmoituksen ajoparametrejä koskevasta virheestä.
     */
    public static void printParamError() {
        System.out.println("Parametrivirhe!");
    }
    
    /**
     * Näyttää annetun virheilmoituksen. 
     * @param message Käyttäjälle näytettävä virheilmoitus merkkijonona,
     */
    public static void printError(String message) {
        System.out.println(message);
    }

    /**
     * Näyttää käyttäjälle ohjelman käyttöohjeen.
     */
    public static void printPrompt() {
        System.out.println("Ohje: [tiedostopolku] [lähtöpaikka] [kohdepaikka] hakee polun, q lopettaa, = näyttää kaikki tulokset");
    }  

    /**
     * Näyttää ilmoituksen tiedoston lukuun liittyvästä virheestä.
     * @param message   Virheeseen liittyviä lisätietoja sisältävä merkkijono, joka
     * näytetään yleisluontoisen virheilmoituksen perässä.
     * @param filepath  Sen tiedoston sijaintipolku, jonka luku aiheutti virheen.
     */
    public static void printFileError(String message, String filepath) {
        System.out.printf("Datatiedoston %s luku ei onnistunut:\n%s\n", filepath, message);
    }
    
    /**
     * Näyttää ilmoituksen ohjelman suorituksen päättymisestä.
     */
    public static void printGoodbye() {
        System.out.println("Tack och välkommen åter!");
    }

}
