package service;

import java.io.IOException;

public interface FileHandler {
    void loadFromFile() throws IOException;
    void saveToFile() throws IOException;
}

