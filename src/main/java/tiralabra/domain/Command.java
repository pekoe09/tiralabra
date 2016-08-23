package tiralabra.domain;

public class Command implements INamedObject{
    
    private String commandText;
    private String filePath;
    private String startPlaceName;
    private String endPlaceName;
    
    public Command(String commandText) {
        this.commandText = commandText;
    }

    public String getFilePath() {
        return filePath;
    }
    
    public String getStartPlaceName() {
        return startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }
    
    @Override
    public String getName() {
        return commandText;
    }    
}
