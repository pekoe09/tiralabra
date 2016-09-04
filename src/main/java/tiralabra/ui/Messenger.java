package tiralabra.ui;

import tiralabra.datastructures.NamedArrayList;
import tiralabra.datastructures.PathStack;
import tiralabra.domain.PlaceNode;
import tiralabra.domain.PathSearchResult;
import tiralabra.domain.PathSearchResultSet;
import tiralabra.enums.AlgorithmAlternative;

/**
 * Apuluokka, jonka metodit esittävät käyttäjälle näytettävän informaation.
 */
public class Messenger {    
        
    /**
     * Esittää kaikki tulossetissä olevat tulokset.
     * @param results   Tulossetti, jonka tulokset halutaan esittää käyttäjälle.
     */
    public void showResults(PathSearchResultSet results) {
        for(int i = 0; i < results.size(); i++) {
            PathSearchResult result = results.get(i);
            printShortestPath(
                    result.getShortestPath(), 
                    result.getStartPlace(), 
                    result.getEndPlace(), 
                    result.getRunTimeNanoSecs(), 
                    result.getAlgorithm());
        }
    }
    
    /**
     * Esittää kaikki tähän asti saadut tulokset.
     * @param allResults    Kaikki tähän astiset tulokset sisältävä taulukko.
     */
    public void showAllResults(NamedArrayList allResults) {
        if(allResults == null || allResults.size() == 0) {
            printMessage("Et ole tehnyt vielä polunetsintäkyselyitä.");
        } else {
            printMessage("Kaikki tekemäsi polunetsintäkyselyt:");
            for(int i = 0; i < allResults.size(); i++) {
                printResultMetaData((PathSearchResultSet)allResults.get(i));
                showResults((PathSearchResultSet)allResults.get(i));
            }
        }
    }
    
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
    public void printShortestPath(PathStack path, PlaceNode startNode, 
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
        // polkupinon PlaceNode-oliot on otettava talteen ja palautettava polun
        // lukemisen jälkeen takaisin polkupinoon (muuten polkua ei enää 
        // myöhemmin voi lukea uudelleen
        PathStack pathCopy = null;
        if(!path.isEmpty()) {
            pathCopy = new PathStack(path.getTop() + 1);
        }
        while(!path.isEmpty()) {
            stageCounter++;
            PlaceNode nextPlace = path.pop();
            pathCopy.push(nextPlace);
            distance = nextPlace.getDistanceToNeighbour(previousPlaceName);
            previousPlaceName = nextPlace.getName();
            totalDistance += distance;
            System.out.println(String.format("%d. %s, välimatka %.1f km", stageCounter, nextPlace.getName(), distance));
        }
        if(pathCopy != null) {
            while(!pathCopy.isEmpty()) {
                path.push(pathCopy.pop());
            }
        }
        distance = endNode.getDistanceToNeighbour(previousPlaceName);
        System.out.println(String.format("%d. %s, välimatka %.1f km", ++stageCounter, endNode.getName(), distance));
        totalDistance += distance;
        System.out.println(String.format("Kokonaisvälimatka: %.1f km", totalDistance));
        System.out.println(String.format("Algoritmin suoritukseen meni %,.3f millisekuntia.", runTimeNanoSecs / 1000000.0));
        printSeparator();
    }
    
    /**
     * Näyttää annetusta polunetsintätulosten joukosta metatiedot eli mihin
     * datatiedostoon perustuen etsintäpohjana ollut verkko on luotu sekä kuinka
     * monta solmua ja kaarta tässä verkossa on.
     * @param resultSet Polunetsintätulosten joukko.
     */
    public void printResultMetaData(PathSearchResultSet resultSet) {
       System.out.println(String.format("Datatiedosto: %s", resultSet.get(0).getFilePath()));
       System.out.println(String.format("Verkossa on %d solmua ja %d kaarta", resultSet.get(0).getNodeCount(), resultSet.get(0).getEdgeCount()));
    }

    /**
     * Näyttää ilmoituksen ajoparametrejä koskevasta virheestä.
     */
    public void printParamError() {
        System.out.println("Anna polun etsintäkäsky muodossa [tiedostopolku] [lähtöpaikka] [maalipaikka]!");
        System.out.println("Jos haluat käyttää edellistä datatiedostoa, voit korvata tiedostopolun +-merkillä.");
        System.out.println("Voit tulostaa kaikki tähänastiset tulokset '=':lla ja lopettaa 'q':lla.");
    }
    
    /**
     * Näyttää annetun viestin. 
     * @param message Käyttäjälle näytettävä viesti merkkijonona,
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Näyttää käyttäjälle ohjelman käyttöohjeen.
     */
    public void printPrompt() {
        System.out.println("Anna seuraava komento! (q lopettaa, ? näyttää ohjeen)");
        System.out.print("> ");
    }  
    
    public void printHelp() {
        System.out.println("Tällä ohjelmalla voit etsiä lyhimmän polun sisään luettavan verkkotiedoston määrittelemästä maantieteellisten paikkojen verkosta.");
        System.out.println("Ohjelma etsii polun sekä Dijkstra- että A*-algoritmilla ja näyttää molemmista myös algoritmin suoritusajan.");
        System.out.println();
        System.out.println("Käytettävissäsi ovat seuraavat komennot:");
        System.out.println("[tiedostopolku] [lähtöpaikka] [maalipaikka] : hakee lyhimmän polun [lähtöpaikasta] [maalipaikkaan] käyttäen [tiedostopolun] verkkotiedostoa");
        System.out.println("+ [lähtöpaikka] [maalipaikka] : hakee lyhimmän polun [lähtöpaikasta] [maalipaikkaan] käyttäen viimeisintä sisään luettua verkkotiedostoa");
        System.out.println("* [tiedostopolku] [toistokertojen lkm] : suorittaa [tiedostopolun] mukaisen skriptitiedoston, toistaen algoritmien ajot [toistokertojen lkm] -kertaan");
        System.out.println("= : näyttää kaikki tähänastiset polunetsintätulokset");
        System.out.println("q : lopettaa ohjelman");
    }

    /**
     * Tulostaa erotinrivin.
     */
    public void printSeparator() {
        System.out.println("----------------------------------------------------------------------------");
    }
    
    /**
     * Näyttää ilmoituksen tiedoston lukuun liittyvästä virheestä.
     * @param message   Virheeseen liittyviä lisätietoja sisältävä merkkijono, joka
     * näytetään yleisluontoisen virheilmoituksen perässä.
     * @param filepath  Sen tiedoston sijaintipolku, jonka luku aiheutti virheen.
     */
    public void printFileError(String message, String filepath) {
        System.out.printf("Datatiedoston %s luku ei onnistunut:\n%s\n", filepath, message);
    }
    
    /**
     * Näyttää ilmoituksen ohjelman suorituksen päättymisestä.
     */
    public void printGoodbye() {
        System.out.println("Tack och välkommen åter!");
    }

    void printWelcome() {
        System.out.println("************  Tervetuloa polunetsintäohjelmaan! ************");
    }

}
