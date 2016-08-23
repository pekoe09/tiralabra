package tiralabra.datainput;

import tiralabra.datastructures.NamedArrayList;
import tiralabra.domain.Command;
import tiralabra.enums.ReadTarget;

public class ScriptMapper implements IDataMapper {
    
    private NamedArrayList array;
    private final int MIN_COMMAND_LENGTH = 3;
    
    public ScriptMapper(){
        this.array = new NamedArrayList();
    }

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

    @Override
    public NamedArrayList getData() {
        return array;
    }

    @Override
    public void resetMapper() {
        array = new NamedArrayList();
    }
    
}
