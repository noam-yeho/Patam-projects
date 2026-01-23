package test;

import test.TopicManagerSingleton.TopicManager;
import test.Agent;
import java.util.HashMap;

public class PlusAgent implements Agent{
    private final String[] inputs;
    private final String[] outputs;
    private final HashMap<String, Double> values = new HashMap<>();

    public PlusAgent(String[] inputs, String[] outputs){
        this.inputs = inputs;
        this.outputs = outputs;
        for (String s : inputs) {
            values.put(s, 0.0);
            TopicManagerSingleton.get().getTopic(s).subscribe(this);
        }
    }
    @Override
    public void callback(String topic, Message msg){
        values.put(topic, msg.asDouble);
        double sum = 0;
        for (Double v : values.values()) sum += v;
        for (String out : outputs) TopicManagerSingleton.get().getTopic(out).publish(new Message(sum));
    }

    @Override public String getName() { return "PlusAgent"; }
    @Override public void reset() { values.keySet().forEach(k -> values.put(k, 0.0)); }
    @Override public void close() {}
}
