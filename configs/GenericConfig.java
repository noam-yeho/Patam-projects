package configs;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GenericConfig {
    private String conf_file;

    public void setConfFile(String conf_file_name) {
        this.conf_file = conf_file_name;
    }

    public void create(){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(conf_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines.size() % 3 != 0) {
            throw new IllegalArgumentException("Configuration file format is incorrect.");
        }
        Class<?> cls = Class.forName(conf_file);
    }

    public void close(){
        ConfigManager.get().closeConfig();
    }
}
