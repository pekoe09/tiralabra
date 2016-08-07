package tiralabra;

import java.util.List;

/**
 * Apuluokka, jonka metodit esittävät käyttäjälle näytettävän informaation.
 */
public class Messenger {
    
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
