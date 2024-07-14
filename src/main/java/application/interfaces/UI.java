package application.interfaces;

import java.util.List;

public interface UI {
    void println(String message);
    void clear();
    void hold();
    int userChoice(List<String> choices);
    String textInput(String placeholder);
}
