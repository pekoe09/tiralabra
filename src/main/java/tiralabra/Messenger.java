package tiralabra;

import tiralabra.datastructures.PathStack;
import tiralabra.domain.PlaceNode;
import java.util.List;
import tiralabra.enums.AlgorithmAlternative;

/**
 * Apuluokka, jonka metodit esittävät käyttäjälle näytettävän informaation.
 */
public class Messenger {
    
    /**
     * Tulostaa lyhimmän polun tiedot; jokaisesta paikasta näytetään nimi ja etäisyys
     * edelliseen paikkaan ja lopuksi koko polun pituus.
     * @param path              PathStack-pino, joka sisältää lyhimmän polun paikat.
     * @param startNode         Polun lähtöpaikka.
     * @param endNode           Polun maalipaikka.
     * @param runTimeNanoSecs   Algoritmin suoritusaika nanosekunteina.
     * @param algorithm         Polun etsimisessä käytetty algoritmi (AlgorithmAlternative.DIJKSTRA
     *                          tai AlgorithmiAlternative.ASTAR)
     */
    public static void printShortestPath(PathStack path, PlaceNode startNode, 
            PlaceNode endNode, long runTimeNanoSecs, AlgorithmAlternative algorithm) {
        printSeparator();
        System.out.println(String.format("Lyhin polku paikasta %s paikkaan %s %s-algoritmin mukaan", 
                startNode.getName(),
                endNode.getName(),
                algorithm.toString().toLowerCase()));
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
            System.out.println(String.format("%d. %s, välimatka %.1f km", stageCounter, nextPlace.getName(), distance));
        }
        distance = endNode.getDistanceToNeighbour(previousPlaceName);
        System.out.println(String.format("%d. %s, välimatka %.1f km", ++stageCounter, endNode.getName(), distance));
        totalDistance += distance;
        System.out.println(String.format("Kokonaisvälimatka: %.1f km", totalDistance));
        System.out.println(String.format("Algoritmin suoritukseen meni %,.1f millisekuntia.", runTimeNanoSecs / 10000.0));
        printSeparator();
    }
    
    /**
     * Näyttää kaikki polunetsintätulokset ohjelman käynnistyksestä lähtien.
     * (Ei vielä toteutettu)
     */
    public static void printAllResults() {
       System.out.println("Kaikki tulokset...");
    }

    /**
     * Näyttää ilmoituksen ajoparametrejä koskevasta virheestä.
     */
    public static void printParamError() {
        System.out.println("Anna polun etsintäkäsky muodossa [tiedostopolku] [lähtöpaikka] [maalipaikka]!");
        System.out.println("Jos haluat käyttää edellistä datatiedostoa, voit korvata tiedostopolun +-merkillä.");
        System.out.println("Voit tulostaa kaikki tähänastiset tulokset '=':lla ja lopettaa 'q':lla.");
    }
    
    /**
     * Näyttää annetun viestin. 
     * @param message Käyttäjälle näytettävä viesti merkkijonona,
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Näyttää käyttäjälle ohjelman käyttöohjeen.
     */
    public static void printPrompt() {
        System.out.println("Anna seuraava komento!\nOhje: [tiedostopolku] [lähtöpaikka] [maalipaikka] hakee polun, q lopettaa, = näyttää kaikki tulokset");
    }  

    public static void printSeparator() {
        System.out.println("----------------------------------------------------------------------------");
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
