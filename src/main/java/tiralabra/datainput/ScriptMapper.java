package tiralabra.datainput;

import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.Command;
import tiralabra.enums.ReadTarget;

/**
 * IDataMapper-rajapinnan toteuttava luokka, jonka tehtävänä on tulkita skriptitiedostosta
 * sisäänluettuja komentorivejä.
 */
public class ScriptMapper implements IDataMapper {
    
    private NamedArrayList array;
    private final int MIN_COMMAND_LENGTH = 3;
    
    /**
     * Konstruktori asettaa komentoja sisältävän NamedArrayList-olion tyhjäksi listaksi.
     */
    public ScriptMapper(){
        this.array = new NamedArrayList();
    }

    /**
     * IDataMapper-rajapinnan metodi, joka tulkitsee yhden sisäänluetun tietueen tiedot.
     * @param commandString Sisäänluettu komentorivi merkkijonona.
     * @param rowCounter    Laskuri sisäänluettujen rivien määrälle.
     * @param target        ReadTarget-enumin arvo: skriptitiedostoa luettaessa
     *                      aina SCRIPT.
     */
    @Override
    public void mapData(String commandString, Integer rowCounter, ReadTarget target) {
        if(commandString == null || commandString.trim().length() == 0) {
            throw new IllegalArgumentException("Komentorivi on tyhjä!");
        }
        String[] commandParts = commandString.split(" ");
        if(commandParts.length < MIN_COMMAND_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: komentorivillä %d on liian vähän tietoa:\n %s", rowCounter, commandString));
        }
        Command newCommand = new Command(commandParts[0], commandParts[1], commandParts[2]);
        array.add(newCommand);
    }

    /**
     * IDataMapper-rajapinnan metodi, joka palauttaa luetut tiedot.
     * @return Luetut komennot Command-olioita sisältävänä NamedArrayList-oliona.
     */
    @Override
    public NamedArrayList getData() {
        return array;
    }

    /**
     * IDataMapper-rajapinnan metodi, joka tyhjentää luetut tiedot.
     */
    @Override
    public void resetMapper() {
        array = new NamedArrayList();
    }
    
}
