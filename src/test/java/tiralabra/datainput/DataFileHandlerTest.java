package tiralabra.datainput;

import java.io.BufferedReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import tiralabra.enums.ReadTarget;
import tiralabra.ui.Messenger;

public class DataFileHandlerTest {
    
    private DataFileHandler handler;
    private BufferedReader bufferedReader;
    private PlaceGraphMapper mapper;
    private Messenger messenger;

    @Before
    public void setUp() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Raatala;60.532;23.169").thenReturn(null);    
        
        mapper = Mockito.mock(PlaceGraphMapper.class);
        messenger = Mockito.mock(Messenger.class); 
        handler = new DataFileHandler(mapper, new Messenger());               
    }

    @Test
    public void readLinesTest() throws IOException {
        handler.readLines(bufferedReader, mapper, ReadTarget.NODE_BASIC_DATA);
        verify(mapper).mapData("Raatala;60.532;23.169", 1, ReadTarget.NODE_BASIC_DATA);
        verify(bufferedReader, times(2)).readLine();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void readLinesThrowsExceptionWhenReceivesIOException() throws IOException { 
        doThrow(new IOException()).when(bufferedReader).readLine();
        handler.readLines(bufferedReader, mapper, ReadTarget.NODE_BASIC_DATA);
    }
    
//    @Test
//    public void readDataFileShowsErrorWhenReceivesIllegalArgumentException() {
//        String filePath = "c://data.metropolitan.data";
//        handler.readDataFile(filePath);
//        ArgumentCaptor<String> exceptionArgument = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> filePathArgument = ArgumentCaptor.forClass(String.class);
//        verify(messenger).
//    }
    
}
