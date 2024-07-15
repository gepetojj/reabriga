package application.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CSV {
    List<String[]> read(Path path) throws IOException, Exception;
}
