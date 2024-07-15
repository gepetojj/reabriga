package application.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CSV {
    // FORMATO CSV: type,name,description,quantity,clothing_type,clothing_size,unit,expiration
    List<String[]> read() throws IOException, Exception;
}
