package configs;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import test.Agent;
import test.ParallelAgent;
import java.lang.reflect.Constructor;


public class GenericConfig {
    private String conf_file;
    private final List<Agent> agents = new ArrayList<>();

    public void setConfFile(String conf_file_name) {
        this.conf_file = conf_file_name;
    }

    public void create(){
        try (BufferedReader reader = new BufferedReader(new FileReader(conf_file))) {
        String className;
        while ((className = reader.readLine()) != null) {
            String lineInputs = reader.readLine();
            String lineOutputs = reader.readLine();
            if (lineInputs == null || lineOutputs == null) break;
            String[] inputs = lineInputs.split(",");
            String[] outputs = lineOutputs.split(",");
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(String[].class, String[].class);
            Agent agent = (Agent) constructor.newInstance((Object) inputs, (Object) outputs);
            ParallelAgent pa = new ParallelAgent(agent);
            agents.add(pa);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void close(){
        this.agents.forEach(Agent::close);
    }
}
